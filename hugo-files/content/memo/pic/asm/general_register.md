---
title: "変数の割り当て ─ PIC めもめも"
url: "p/hoytbx6/"
date: "2002-08-01"
tags: ["pic"]
aliases: ["/memo/pic/asm/general_register.html"]
---


領域として自由に使用できる汎用レジスタの範囲
----

ユーザが変数領域として自由に使用できる汎用レジスタ (データ RAM) の範囲の一例です。

| Device | Bank0 | Bank1 | Bank2 | Bank3 | データ RAM サイズ |
| :----: | :----: | :----: | :----: | :----: | :----: |
| PIC16F83<BR>PIC16CR83 | 0Ch ～ 2Fh<BR>(36 bytes) | 8Ch ～ AFh<BR>(Bank0 へのマップ) | ─ | ─ | 36 bytes
| PIC16F84<BR>PIC16CR84 | 0Ch ～ 4Fh<BR>(68 bytes) | 8Ch ～ CFh<BR>(Bank0 へのマップ) | ─ | ─ | 68 bytes
| PIC16F873<BR>PIC16F874 | 20h ～ 7Fh<BR>(96 bytes) | A0h ～ FFh<BR>(96 bytes) | ─ | ─ |
| PIC16F876<BR>PIC16F877 | 20h ～ 7Fh<BR>(96 bytes) | A0h ～ EFh<BR>(80 bytes) | 110h ～ 16Fh<BR>(96 bytes) | 190h ～ 1EFh<BR>(96 bytes) |

PIC16F87x の Bank1 ～ 3 の最後の部分には、Bank0 へのマッピングアドレスとして使えるものがあります。
うまく使えば効率的なプログラムが作れるかもしれません。
詳しくは各デバイスのデータシートを参照してください。


汎用レジスタに変数領域を確保する
----

データメモリに変数を確保するには CBLOCK や RES を使うのが便利です。
Bank の切り替えの必要をなくすために、普通は Bank0 から変数を割り当てていきます。

{{< code lang="asm" title="PIC16F84A の場合の変数割り当て" >}}
	LIST     P=16F84A
	INCLUDE  P16F84A.INC

;==================================
; 変数定義
;==================================
	CBLOCK   H'0C'     ;0Ch 番地から 34 バイト使える
	VAR_A
	VAR_B
	VAR_C
	ENDC               ;CBLOCK 終了
{{< /code >}}

{{< code lang="asm" title="PIC16F873 の場合の変数割り当て" >}}
	LIST     P=16F873
	INCLUDE  P16F873.INC

;==================================
; 変数定義
;==================================
	CBLOCK   H'20'
	VAR_A
	VAR_B
	VAR_C
	ENDC
{{< /code >}}

