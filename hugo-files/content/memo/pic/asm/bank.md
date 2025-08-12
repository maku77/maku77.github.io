---
title: "バンクとページの切り替え ─ PIC めもめも"
url: "p/dtabczb/"
date: "2002-08-01"
tags: ["pic"]
aliases: ["/memo/pic/asm/bank.html"]
---

バンク ･･･ 汎用レジスタ (RAM) の区分け
----

MOVWF 命令などのデスティネーションアドレスは下のように 7 ビットとなっているので、このままでは 128 バイト分の汎用レジスタにしかアクセスすることができません。

* MOVWF 命令の構造 → 0000001**fffffff**

これを解決するために、SFR (Special Function Register) である **STATUS レジスタ** の 5, 6 ビット目 (**RP0**, **RP1**) をアドレスの拡張に使用します。
これにより 7 + 2 = 9 ビット、つまり 512 バイト分の汎用レジスタにアクセスできるようになります（ただしデバイスごとに使用できるサイズは異なります）。

| バンク | STATUS&lt;RP0&gt; | STATUS&lt;RP1&gt; | アクセスできるアドレス範囲 |
| :----: | :----: | :----: | :----: |
| **Bank0** | 0 | 0 | 0x0000 ～ 0x007F (128 bytes) |
| **Bank1** | 1 | 0 | 0x0080 ～ 0x00FF (128 bytes) |
| **Bank2** | 0 | 1 | 0x0100 ～ 0x017F (128 bytes) |
| **Bank3** | 1 | 1 | 0x0180 ～ 0x01FF (128 bytes) |

PORTA を出力か入力かどちらに使うかの設定をするための **TRISA レジスタは Bank1** にあるので、バンクを切り替えて値を設定する必要があります（PORTA は Bank0 にあります）。
バンクを切り替えて TRISA にアクセスする例を示します。

{{< code lang="asm" title="PortA[0] を出力用ポートに設定する" >}}
BSF    STATUS, RP0  ; set bank1
BCF    TRISA, 0     ; PortA[0] is an output port
BCF    STATUS, RP0  ; set bank0
{{< /code >}}

RP0 と RP1 は MPASM のヘッダファイルに EQU で定義されています。


ページ ･･･ プログラムメモリ (ROM) の区分け
----

バンクは、データメモリへのアクセスを拡張するものでした。
これと同じようなに、プログラムメモリへのアクセスを拡張したものがページです。
GOTO 命令と CALL 命令のリテラル部分は、11 ビットまでの値が指定できるようになっています。
つまり通常は、この値を使って 2k ワードの範囲のジャンプができることになります。

GOTO 命令の構造 → 101**kkkkkkkkkkk**

PIC ではページの機能を利用することによって、この 4 倍の 8k ワードまでのジャンプを実現しています。
そのために、**PCLATH レジスタ** というレジスタの 3, 4 ビット目をこの 11 ビットの値に付加したものをプログラムカウンタとして利用しています。
これにより、プログラムメモリ 0x0000 ～ 0x1FFF の 8k ワードの範囲に自由にアクセスできるようになっています（ただしデバイスごとに使用できるサイズは異なります）。

| ページ | PCLATH&lt;3&gt; | PCLATH&lt;4&gt; | アクセスできるアドレス範囲 |
| :----: | :----: | :----: | :----: |
| Page0 | 0 | 0 | 0x0000 ～ 0x07FF (2k words) |
| Page1 | 1 | 0 | 0x0800 ～ 0x0FFF (2k words) |
| Page2 | 0 | 1 | 0x1000 ～ 0x17FF (2k words) |
| Page3 | 1 | 1 | 0x1800 ～ 0x1FFF (2k words) |

PCLATH の 3, 4 ビット目の値をセットするには次のようにします。

```asm
; Page0 に切り替え
    BCF PCLATH, 3  ; 0
    BCF PCLATH, 4  ; 0

; Page1 に切り替え
    BSF PCLATH, 3  ; 1
    BCF PCLATH, 4  ; 0

; Page2 に切り替え
    BCF PCLATH, 3  ; 0
    BSF PCLATH, 4  ; 1

; Page3 に切り替え
    BSF PCLATH, 3  ; 1
    BSF PCLATH, 4  ; 1
```

次の例は、ページ 0 のルーチンから、ページ 1 のルーチンにジャンプする例です。

```asm
    ORG 0x0000  ; ページ0 内のルーチン
    ...
    BSF PCLATH, 3
    BCF PCLATH, 4
    CALL BANK1_ROUTINE
    ...

    ORG 0x0800  ; ページ1 内のルーチン
BANK1_ROUTINE
    ...
    RETURN      ; ※戻る時はページ指定の必要はない
```

RETURN、RETFIE、RETLW 命令で元のアドレスに戻る場合は、スタックから 13 ビットのアドレスが取り出されるため、ページの指定をする必要はありません（この時に使用できるスタックのレベルは 8 レベルまでなので、CALL を連続して呼ぶような場合は注意が必要です）。


バンクの数
----

| | PIC16F84 | PIC16F877 |
| :----: | ---- | ---- |
| バンク | 2つ (BANK0, BANK1) | 4つ (BANK0 ～ BANK3) |
| 入出力ポート | 2つ (PORTA, PORTB)<BR>(BANK0 にある) | 5つ (PORTA ～ PORTE) |
| 入出力切り替えレジスタ | TRISA, TRISB<BR>(STATUS レジスタの 5bit目(= RP0) で切り替え)<BR>(BANK1 にある) | |
| プログラムメモリ | 1KByte (0000h ～ 1FFFh) | 8KByte (2KByte ごとに区切られている)<BR>0ページ ･･･ 0005h ～ 07FFh<BR>1ページ ･･･ 0800h ～ 0FFFh<BR>2ページ ･･･ 1000h ～ 17FFh<BR>3ページ ･･･ 1800h ～ 1FFFh |

