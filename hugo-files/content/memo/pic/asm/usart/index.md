---
title: "USART によるシリアル通信 ─ PIC めもめも"
url: "p/fewejde/"
date: "2002-08-01"
tags: ["pic"]
aliases: ["/memo/pic/asm/usart.html"]
---

USART 用の送受信ピン TRISC&lt;6, 7&gt; の設定
----

USART (Universal Synchronous Asynchronous Receiver Transmitter) は次のモードで構成できます。

- 非同期（全二重）
- 同期 ─ マスター（半二重）
- 同期 ─ スレーブ（半二重）

**USART による通信は TX (送信)、RX (受信) ピンを使用します**（TX を出力ピン、 RX を受信ピンに設定します）。
TRISC&lt;6&gt; が RC6/TX/CK、TRISC&lt;7&gt; が RC7/RX/DT の入出力設定ビットなので、これらの値を次のようにセットします。

{{< code lang="asm" title="TX, RX の入出力モードの設定 (TX=出力, RX=入力)" >}}
INIT_TX_TR
	BSF     STATUS, RP0  ; set bank1
	BCF     TRISC, 6     ; PortC&lt;6&gt; (TX) is an output
	BSF     TRISC, 7     ; PortC&lt;7&gt; (RX) is an input
	BCF     STATUS, RP0  ; set bank0
{{< /code >}}

TRISC&lt;6&gt; をクリアして、 TRISC&lt;7&gt; をセットするだけですね。


TXSTA (送信ステータスおよびコントロールレジスタ) の設定
----

TXSTA (98h(Bank1)) レジスタは、送信ステータスおよびコントロールを設定するためのレジスタです。
このレジスタの構造は次のようになっています。

<table align="center">
  <caption>TXSTA レジスタ</caption>
  <tr style="text-align: center">
    <td width="12.5%">CSRC<br>(7)</td>
    <td width="12.5%">TX9<br>(6)</td>
    <td width="12.5%">TXEN<br>(5)</td>
    <td width="12.5%">SYNC<br>(4)</td>
    <td class="namec" width="12.5%">─<br>(3)</td>
    <td width="12.5%">BRGH<br>(2)</td>
    <td width="12.5%">TRMT<br>(1)</td>
    <td width="12.5%">TX9D<br>(0)</td>
  </tr>
</table>

- **CSRC (7)**: クロックソースセレクトビット
  - 非同期モード
    - Don’t care
  - 同期モード
    - 1 = マスターモード（内部でBRG から発生するクロック）
    - 0 = スレーブモード（外部ソースからのクロック）
- **TX9 (6)**: 9 ビット送信イネーブルビット
  <br>このビットをセットすると、TXSTA&lt;TX9D(0)&gt; にセットしたビットも同時に送信されるようになります。
  - 1 = 9 ビット送信を選択
  - 0 = 8 ビット送信を選択
- **TXEN (5)**: 送信イネーブルビット
  <br>注意： 同期モードではSREN/CREN はTXEN をオーバーライドします。
  - 1 = 送信はイネーブル
  - 0 = 送信はディセーブル
- **SYNC (4)**: USART モードセレクトビット
  - 1 = 同期モード
  - 0 = 非同期モード
- **なし (3)**: 未使用
  <br>このビットを読み出すと、常に「0」とリードされます。
- **BRGH (2)**: 高速ボーレートセレクトビット
  - 非同期モード
    - 1 = 高速
    - 0 = 低速
  - 同期モード（使用しません）
- **TRMT (1)**: 送信シフトレジスタステータスビット
  - 1 = TSR が空
  - 0 = TSR がフル
- **TX9D (0)**: 送信データの第 9ビット。パリティビットとして使用可能

TXSTA レジスタは Bank1 にあるので注意してください（普通は Bank1 にある TRISC の設定といっしょにやってしまいます）。
例えば、送信イネーブル、非同期モードにするなら次のようにします。

{{< code lang="asm" title="TXSTA の設定例（送信 Enable）" >}}
    BSF     STATUS, RP0  ; set bank1
    MOVLW   B'00100000'  ; Async mode
    MOVWF   TXSTA        ;
    BCF     STATUS, RP0  ; set bank0
{{< /code >}}

他の設定にしたい時は、`B'00100000` の部分を変更します。


RCSTA（受信ステータスおよびコントロールレジスタ）の設定
----

RCSTA レジスタ (18h(Bank0)) は受信ステータスおよびコントロールを設定するレジスタです。
RCSTA の構造は次のようになっています。

<table align="center">
  <caption>RCSTA レジスタ</caption>
  <tr style="text-align: center">
    <td width="12.5%">SPEN<br>(7)</td>
    <td width="12.5%">RX9<br>(6)</td>
    <td width="12.5%">SREN<br>(5)</td>
    <td width="12.5%">CREN<br>(4)</td>
    <td width="12.5%">ADDEn<br>(3)</td>
    <td width="12.5%">FERR<br>(2)</td>
    <td width="12.5%">OERR<br>(1)</td>
    <td width="12.5%">RX9D<br>(0)</td>
  </tr>
</table>

- **SPEN (7)**: シリアルポートイネーブルビット
  - 1 = シリアルポートはイネーブル（RC7/RX/DT およびRC6/TX/CK ピンはシリアルポートピンとして構成）
  - 0 = シリアルポートはディセーブル
- **RX9 (6)**: 9 ビット受信イネーブルビット
  - 1 = 9 ビット受信を選択
  - 0 = 8 ビット受信を選択
- **SREN (5)**: シングル受信イネーブルビット
  - 非同期モード (Don’t care)
  - 同期モード－マスター（このビットは受信完了後クリアされます）
    - 1 = シングル受信はイネーブル
    - 0 = シングル受信はディセーブル
  - 同期モード－スレーブ（使用しません）
- **CREN (4)**: 連続受信イネーブルビット
  - 非同期モード
    - 1 = 連続受信はイネーブル
    - 0 = 連続受信はディセーブル
  - 同期モード
    - 1 = イネーブルビットCREN がクリアされるまでは連続受信可能（CREN はSREN をオーバーライドする）
    - 0 = 連続受信はディセーブル
- **ADDEN (3)**: アドレス検出イネーブルビット
  - 非同期モード9 ビット（RX9=1)
    - 1 = アドレス検出はイネーブル、RSR&lt;8&gt; が 1 のとき、受信バッファの割り込みとロードがイネーブル
    - 0 = アドレス検出はディセーブル、全バイトを受信、第 9 ビットはパリティビットとして使用可能
- **FERR (2)**: フレーミングエラービット
  - 1 = フレーミングエラー（RCREG レジスタのリードにより更新でき、次の有効バイトを受信する）
  - 0 = フレーミングエラーなし
- **OERR (1)**: オーバーランエラービット
  - 1 = オーバーランエラー（ビット CREN のクリアによりクリア可能）
  - 0 = オーバーランエラーなし
- **RX9D (0)**: 受信データの第 9 ビット（パリティビットとして使用可能）
  - RCSTA&lt;RX9 (6)&gt; をセットした場合、このビットに第 9 ビットが受信されます。送信側と受信側がこのことを認識してプログラムを作成する必要があります。

RCSTA&lt;7&gt; (SPEN) を 1 にセットすることで、TX, RX ポートをシリアルポートピンとして使用できるようになります。
RCSTA レジスタは Bank0(18h) にあるので、現在 Bank0 ならばバンクを切り替える必要はありません。

{{< code lang="asm" title="RCSTA の設定例（調歩同期方式［非同期］で通信）" >}}
    MOVLW   B'10010000'  ; Serial port is enable
    MOVWF   RCSTA
{{< /code >}}

他の設定にしたい場合は、`B'10010000'` の部分を変更します。


SPBRG（ボーレート発生器の設定レジスタ）の設定
----

調歩同期方式（非同期モード）を選択した場合は、**ボーレートを決定するために SPBRG (99h(Bank1)) レジスタを設定する** 必要があります。
SPBRG レジスタは、希望のボーレートによって 0～255 の値を設定します。
希望のボーレートとクロック周波数 (Hz) が決まれば、以下の式から SPBRG にセットすべき値 (X) を求めることができます（データシートから抜粋）。
X を求める式は SYNK ビットと BRGH ビットにより変化します。

<table align="center">
  <caption>ボーレートを求める式</caption>
  <tr>
    <th>&nbsp;</th>
    <td class="namec">TXSTA&lt;BRGH(2)&gt; = 0 (低速)</td>
    <td class="namec">TXSTA&lt;BRGH(2)&gt; = 1 (高速)</td>
  </tr>
  <tr>
    <td class="name">TXSTA&lt;SYNC(4)&gt; = 0 (非同期)</td>
    <td>ボーレート = クロック / (64 (X + 1))</td>
    <td>ボーレート = クロック / (16 (X + 1))</td>
  </tr>
  <tr>
    <td class="name">TXSTA&lt;SYNC(4)&gt; = 1 (同期)</td>
    <td>ボーレート = クロック / (4 (X + 1))</td>
    <td>NA</td>
  </tr>
</table>

これを **`X =`**  の式に変換すると次のようになります。

<table align="center">
  <caption>SPBRG レジスタにセットする値 (X) を求める式</caption>
  <tr>
    <th>&nbsp;</th>
    <td class="namec">TXSTA&lt;BRGH(2)&gt; = 0 (低速)</td>
    <td class="namec">TXSTA&lt;BRGH(2)&gt; = 1 (高速)</td>
  </tr>
  <tr>
    <td class="name">TXSTA&lt;SYNC(4)&gt; = 0 (非同期)</td>
    <td>X = (クロック / (ボーレート * 64)) - 1</td>
    <td>X = (クロック / (ボーレート * 16)) - 1</td>
  </tr>
  <tr>
    <td class="name">TXSTA&lt;SYNC(4)&gt; = 1 (同期)</td>
    <td>X = (クロック / (ボーレート * 4)) - 1</td>
    <td>NA</td>
  </tr>
</table>

ちなみに PIC16F873 (28ピン) と PIC16F874 (40ピン) は BRGH は低速しか使えないらしいです。
高速に設定したい場合は、PIC16F876 (28ピン) とか PIC16F877 (40ピン) を使います。

### SPBRG レジスタの設定値の計算例

例として、10MHz、非同期モード (SYNC=0)、低速ボーレート (BRGH=0) の時に 9600bps 出したい時の X の値を求めてみます。

```
X ＝ (10000000 / (9600 * 64)) - 1
  ＝ 15.276･･･
  ≒ 15
```

つまり、この場合は SPBRG に 15 (0x0F) をセットすればよいことになります。
SPBRG レジスタは Bank1 にあるので、次のように設定します。
Bank1 にある TRISC、TXSTA レジスタといっしょに設定してしまえばよいでしょう。

{{< code lang="asm" title="SPBRG レジスタ (Baud Rate) のセット" >}}
    BSF     STATUS, RP0        ;Bank1 に切り替え
    MOVLW   0x0F               ;9600bps
    MOVWF   SPBRG              ;
    BCF     STATUS, RP0        ;Bank0 に戻す
{{< /code >}}

よく使うクロック周波数とボーレートでの SPBRG の値は次のようになります。
**同じ 9600bps の場合でも、高速ボーレート (BRGH = 1) にした方がエラーレートは小さくなります**（高速モードが使えるなら）。

<table align="center">
  <caption>非同期モードのボーレートによる SPBRG の値</caption>
  <tr>
    <th rowspan="2">&nbsp;</TH>
    <th colspan="2">BRGH = 0 (低速)</TH>
    <th colspan="2">BRGH = 1 (高速)</TH>
  </tr>
  <tr>
    <th>10MHz</TH><TH>20MHz</TH>
    <th>10MHz</TH><TH>20MHz</TH>
  </tr>
  <tr>
    <td class="namer">1200bps</td>
    <td class="r">129 (0x81)</td>
    <td class="r">255 (0xFF)</td>
    <td class="c">─</td>
    <td class="c">─</td>
  </tr>
  <tr>
    <td class="namer">2400bps</td>
    <td class="r">64 (0x40)</td>
    <td class="r">129 (0x81)</td>
    <td class="c">─</td>
    <td class="c">─</td>
  </tr>
  <tr>
    <td class="namer">9600bps</td>
    <td class="r"><span class="strong">15 (0x0F)</span></td>
    <td class="r"><span class="strong">32 (0x20)</span></td>
    <td class="r">64 (0x40)</td>
    <td class="r">129 (0x81)</td>
  </tr>
  <tr>
    <td class="namer">19200bps</td>
    <td class="r">7 (0x07)</td>
    <td class="r">15 (0x0F)</td>
    <td class="r">32 (0x20)</td>
    <td class="r">64 (0x40)</td>
  </tr>
  <tr>
    <td class="namer">38400bps</td>
    <td class="c">─</td>
    <td class="c">─</td>
    <td class="r">15 (0x0F)</td>
    <td class="r">32 (0x20)</td>
  </tr>
</table>


データ送信の流れ
----

1. **TXSTA&lt;TXEN&gt;** ビットを 1 に設定して送信を Enable にしておく。
2. **TXREG** レジスタに送信したいデータ（8 ビット）をセットすると、その値が自動的に **TSR** レジスタにコピーされて **TX** ポートから送信が開始される。
   - **TSR** の値は操作できません。
   - **TSR** の内容は LSb から 1 ビットずつ送信されます。

{{< image src="img-001.png" title="PIC16F873 データシートより" >}}


TXREG レジスタに次のデータをセットできることを確認する方法（3 通り）
----

- **TXREG** の値が **TSR** にコピーされた瞬間に **PIR1&lt;TXIF&gt;** ビットがセットされる。このビットがセットされたら次のデータを **TXREG** に格納してよい。**TXREG** に次の値をセットすると、**PIR1&lt;TXIF&gt;** ビットは自動的にクリアされる（ソフトでクリアすることはできない）。
- **PIE1&lt;TXIE&gt;** を 1 にセットしておくと、**TXREG** の値が **TSR** にコピーされた瞬間に割り込みが発生するのでこれを利用する（**PIR1&lt;TXIF&gt;** ビットがセットされる）。
- **TSR** の値が空になると、**TXSRA&lt;TRMT&gt;** が 1 にセットされるので、ループでこのビットを監視する。（9 ビット送信を行う場合は、この方法で監視する必要がある？）


TXSTA&lt;TX9D&gt; ビットを使って 9 ビット送る時の注意
----

- **TXSTA&lt;TX9&gt;** ビットを 1 に設定して 9 ビット送信を Enable にする。
- **TX9D** ビット（9 ビット目のデータ）の設定は **TXREG** にデータをセットする前に行う必要がある。


サンプルコード
----

### シリアル送信サブルーチン (SendUsart)

USART でデータを送信する時は、TXSTA レジスタの TRMT (TXSTA&lt;1&gt;) が 1 になるのを確認してから送信するようにします。
次のサブルーチンは、このルールに基づいて W レジスタのデータを送信します。

#### USART によるシリアル送信サブルーチン（方法 1）

```asm
;==============================================================================
; シリアル送信サブルーチン
; TXSTA<1> (TRMT) を監視し、W レジスタのデータをシリアルで送信します。
; （TEMP を Bank0 の汎用レジスタに指定してあることを前提とします。
; 　このルーチンを呼び出すときは Bank0 に切り替えてから呼び出すこと。）
;==============================================================================
SendUsart
    MOVWF   TEMP                ;W レジスタを保存
    BSF     STATUS, RP0         ;Bank1 に切り替え
SendUsartLoop
    BTFSS   TXSTA, TRMT         ;TXSTA&lt;1&gt; (TRMT) が 1 になるまで待機
    GOTO    SendUsartLoop
    BCF     STATUS, RP0         ;Bank0 に戻す
    MOVF    TEMP, W             ;W レジスタの復旧
    MOVWF   TXREG               ;TXREG レジスタにセット (実際の送信開始)
    RETURN
```

ここでは TXSTA レジスタの TRMT(1) ビットを監視して、TSR レジスタが空になったかどうかを調べていますが、次のように PIR1 の TXIF(4) ビットをポーリングすることでも USART によるシリアル送信処理を行うことができます。
そのようにした方が、Bank0 にあるレジスタを監視するので扱いが容易であり、送信バッファ自体の状態を見ているので若干高速です（2003-11-02 に追記。木下隆さん情報提供ありがとうございました）。

#### USART によるシリアル送信サブルーチン（方法 2：おすすめ）

```asm
;******************************************************************************
; USART によるシリアル送信サブルーチン
; PIR1<TXIF(4)> を監視し、W レジスタのデータ (8 bit) をシリアルで送信します。
; 　(このルーチンを呼び出す時は Bank0 に切り替えてから呼び出すこと）
;******************************************************************************
SendUsart
    btfss   PIR1, TXIF  ;PIR1<TXIF(4)> が 1 になるまで待機
    goto    SendUsart
    movwf   TXREG       ;TXREG レジスタにセット
    return
```

ただ、データシートを見てもよくわからないのが、USART で 9 ビット送信をイネーブルにして、TX9D（9ビット目）のデータも送信する場合に、TX9D がどのタイミングで TSR にコピーされるかということです。
TXREG に値をセットした瞬間に TX9D も TSR にコピーされるのなら、TXIF フラグが 1 になった時に TX9D をセットしてよいのですが、TX9D に値をセットした瞬間に TSR にそのビットがコピーされるとしたら、TRMT フラグが 1 になるまでは TX9D に値をセットしてはいけないことになるようなならないような（9 ビット目が上書きされそう）。
マイクロチップテクノロジーのアプリケーションノートの参考例を見ると次のように書いてあるので、まぁ大丈夫なんでしょう。

{{< code lang="asm" title="Application Note : AN774 -- Asynchronous Communications with the PICmicroR USART" >}}
;------------------------------------------------------------------------------
;Transmit data with parity when the transmit register is empty.
;------------------------------------------------------------------------------
TransmitSerial: Bank0     ; select bank 0
        btfss PIR1,TXIF   ; check if transmitter busy
        goto $-1          ; wait until transmitter is not busy
        movf TxData,W     ; get data to be transmitted
        call CalcParity   ; calculate parity
        rrf CalcParity,W  ; get parity bit in carry flag
        Bank1             ; select bank 1
        bcf TXSTA,TX9D    ; set TX parity to zero
        btfsc STATUS,C    ; check if parity bit is zero
        bsf TXSTA,TX9D    ; if not then set TX parity to one

        Bank0             ; select bank 0
        movf TxData,W     ; get data to transmit
        movwf TXREG       ; and transmit the data
        return
{{< /code >}}

### 0x01, 0x02, 0x03 を繰り返し送信する (PIC16F873)

```asm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;
; USART を使ってシリアルで PC に送信するサンプルプログラム
; 0x01, 0x02, 0x03 を繰り返し送信します。
;
;   通信スピード  : 9600bps
;   データ長      : 8bit
;   ストップビット: 1bit
;   フロー制御    : なし
;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    LIST        P=PIC16F873
    INCLUDE     "P16F873.INC"

;==============================================================================
; Variable Definition
;==============================================================================
TEMP    EQU    020H

    ORG     0
;==============================================================================
; 初期設定
;==============================================================================
INIT
    BSF     STATUS, RP0         ; Bank1 に切り替え
;**** USART 用入出力ポートの設定 (TRISC) ****
    BCF     TRISC, 6            ; PortC<6> (TX) is an output
;    BSF     TRISC, 7            ; PortC<7> (RX) is an input
;**** USART 送信ステータスの設定 (TXSTA) ****
    MOVLW   B'00100000'         ; Async mode
    MOVWF   TXSTA               ;
;**** USART ボーレートの設定 (SPBRG) ****
    MOVLW   0FH                 ;9600bps 10MHz BRGH=0(低速)
    MOVWF   SPBRG
;**** USART 受信ステータスの設定 (RCSTA) ****
    BCF     STATUS, RP0         ; Bank0 に戻す
    MOVLW   B'10010000'         ;
    MOVWF   RCSTA               ;

;==============================================================================
; メインルーチン
;==============================================================================
MAIN
    MOVLW   1
    CALL    SEND_USART          ;シリアルで W の内容を送信
    MOVLW   2
    CALL    SEND_USART
    MOVLW   3
    CALL    SEND_USART
    GOTO    MAIN

;==============================================================================
; シリアル送信サブルーチン
; TXSTA<1> (TRMT) を監視し、W レジスタのデータ (8 bit) をシリアルで送信します。
;==============================================================================
SEND_USART
    MOVWF   TEMP                ;W レジスタを保存
    BSF     STATUS, RP0         ;Bank1 に切り替え
SEND_USART_LOOP
    BTFSS   TXSTA, TRMT         ;TXSTA<1> (TRMT) が 1 になるまで待機
    GOTO    SEND_USART_LOOP
    BCF     STATUS, RP0         ;Bank0 に戻す
    MOVF    TEMP, W             ;W レジスタの復旧
    MOVWF   TXREG               ;TXREG レジスタにセット (実際の送信開始)
    RETURN

;==============================================================================
; End Program
;==============================================================================
    END
```

### シリアル受信 (Receive)

0x01 を受信したら LED On、0x02 を受信したら LED Off (PIC16F873) するサンプルコードです。

```asm
;==============================================================================
;
; シリアルポートから 9600bps で 1byte のデータを受信し、
; 受信データが 1 なら PORTB<0> (RB0) を High (LED On) にし、
; 受信データが 2 なら PORTB<0> (RB0) を Low (LED Off) にする。
;
;==============================================================================
;
; File  : usart_recv.asm
; Author: Masatoshi OHTA <ohta@hakoten.com>
; Update: 2003/5/15
;
;   デバイス      : PIC16F873
;   通信スピード  : 9600bps
;   データ長      : 8bit
;   ストップビット: 1bit
;   フロー制御    : なし
;
;==============================================================================
    LIST     P=PIC16F873
    INCLUDE  "P16F873.INC"
    __CONFIG _HS_OSC & _WDT_OFF
             ;ウォッチドッグ・タイマ OFF

;==============================================================================
; 変数定義
;==============================================================================
    CBLOCK   H'20'
    RECV_BUF        ; シリアルからの受信データを一時退避するバッファ
    ENDC

;==============================================================================
; エントリ・ポイント
;==============================================================================
    ORG      0
    goto     StartUp

;==============================================================================
; StartUp ルーチン
;==============================================================================
StartUp
    bsf     STATUS, RP0         ; Bank1 に切り替え
;[TRISB (bank1)] PORTB の入出力の設定
    bcf     TRISB, 0            ; PORTB<0> is an output.
;[TRISC (bank1)] USART 用入出力ポートの設定
    bcf     TRISC, 6            ; PortC<6> (TX) is an output.
    bsf     TRISC, 7            ; PortC<7> (RX) is an input.
;[TXSTA (bank1)] USART 送信ステータスの設定
    movlw   B'00100000'         ; Async mode
    movwf   TXSTA               ;
;[SPBRG (bank1)] USART ボーレートの設定
    movlw   0FH                 ; 9600bps 10MHz BRGH=0(低速)
    movwf   SPBRG

    bcf     STATUS, RP0         ; Bank0 に戻す
;[RCSTA (bank0)] USART 受信ステータスの設定
    movlw   B'10010000'         ;
    movwf   RCSTA               ;

;******************************************************************************
; シリアルポートからの受信を監視するループ
;******************************************************************************
UsartRecvLoop
    btfss   PIR1, RCIF          ; PIR1<5>(RCIF) がセットされたら受信完了
    goto    UsartRecvLoop
    movf    RCREG, w            ; 受信したデータを w に取得

;******************************************************************************
; 受信したデータで処理を振り分け
;   1 を受け取ったら LED を On
;   2 を受け取ったら LED を Off
;******************************************************************************
    movwf   RECV_BUF            ; 受信データ保存
    sublw   1                   ; 1 - w -> w
    btfsc   STATUS, Z           ; 0 なら Case_1 へ
    goto    Case_1

    movf    RECV_BUF, w         ; RECV_BFU -> w
    sublw   2                   ; 2 - w -> w
    btfsc   STATUS, Z           ; 0 なら Case_2 へ
    goto    Case_2
    goto    UsartRecvLoop

Case_1
    bsf     PORTB, 0            ; PORTB<0> (RB0) を High に (LED On)
    goto    UsartRecvLoop

Case_2
    bcf     PORTB, 0            ; PORTB<0> (RB0) を Low に (LED Off)
    goto    UsartRecvLoop

;==============================================================================
; Directive 'End of Program'
;==============================================================================
    END
``` 

