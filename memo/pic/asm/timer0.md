---
title: "タイマ 0 を使用する ─ PIC めもめも"
date: "2002-08-01"
---

タイマ 0 による割り込みの仕組み
----

タイマ 0 では、8bit の **TMR0** レジスタをタイマ 0 用のクロック (後述) でインクリメントしていき、オーバフローを意図的に起こすことによって割り込みを実現しています。
TMR0 レジスタにあらかじめ値をセットしておくことによって、割り込み時間を調整することができます。
ただし、TMR0 レジスタは 8bit しかないので、それだけでは最大 256 カウントしかすることができません。
そこで、**プリスケーラ**という 8bit のカウンタを別に用意し、このカウンタが指定した値（2, 4, 8, 16, 32, 64, 128, 256 のいずれか）になった時にはじめて TMR0 レジスタをインクリメントすることにより、65536 (8bit + 8bit) までのカウントを実現しています。
プリスケーラカウンタを使うには、**OPTION_REG&lt;PSA(3)&gt;** ビットを 0 に設定します。
プリスケーラのスケール値を設定するには、**OPTION_REG&lt;PS0(0)&gt; ～ &lt;PS2(2)&gt;** で次のように指定します。

<TABLE align="center">
	<CAPTION>タイマ 0 のプリスケーラのスケール設定</CAPTION>
	<TR>
		<TH>OPTION_REG<BR>&lt;PS2(2)&gt;</TH><TH>OPTION_REG<BR>&lt;PS1(1)&gt;</TH><TH>OPTION_REG<BR>&lt;PS0(0)&gt;</TH>
		<TH>スケール</TH>
	</TR>
	<TR>
		<TD class="namec">0</TD><TD class="namec">0</TD><TD class="namec">0</TD>
		<TD>1:2 (256 * 2 = 512カウントまで)</TD>
	</TR>
	<TR>
		<TD class="namec">0</TD><TD class="namec">0</TD><TD class="namec">1</TD>
		<TD>1:4 (256 * 4 = 1024カウントまで)</TD>
	</TR>
	<TR>
		<TD class="namec">0</TD><TD class="namec">1</TD><TD class="namec">0</TD>
		<TD>1:8 (256 * 8 = 2048カウントまで)</TD>
	</TR>
	<TR>
		<TD class="namec">0</TD><TD class="namec">1</TD><TD class="namec">1</TD>
		<TD>1:16 (256 * 16 = 4096カウントまで)</TD>
	</TR>
	<TR>
		<TD class="namec">1</TD><TD class="namec">0</TD><TD class="namec">0</TD>
		<TD>1:32 (256 * 32 = 8192カウントまで)</TD>
	</TR>
	<TR>
		<TD class="namec">1</TD><TD class="namec">0</TD><TD class="namec">1</TD>
		<TD>1:64 (256 * 64 = 16384カウントまで)</TD>
	</TR>
	<TR>
		<TD class="namec">1</TD><TD class="namec">1</TD><TD class="namec">0</TD>
		<TD>1:128 (256 * 128 = 32768カウントまで)</TD>
	</TR>
	<TR>
		<TD class="namec">1</TD><TD class="namec">1</TD><TD class="namec">1</TD>
		<TD>1:256 (256 * 256 = 65536カウントまで)</TD>
	</TR>
</TABLE>

※ タイマ 0 ではプリスケーラカウンタをプログラムから参照することはできません。
参照可能なのは TMR0 レジスタ (256 段階) のみになります。


タイマ 0 用のクロック
----

タイマ 0 用のクロックは、システムクロックを利用して内部で発生させる方法と、外部からの専用クロック (T0CKI ピンからの入力) を利用する方法が選択できます。
**OPTION_REG&lt;T0CS(5)&gt;** のビットで切り替えます。
内部クロックを使う場合は、 システムクロックの 1/4 の周波数でカウントされていきます。

- OPTION_REG&lt;T0CS(5)&gt; = 0 … 内部クロックを使用 (システムクロックから生成) (Fosc/4)
- OPTION_REG&lt;T0CS(5)&gt; = 1 … 外部クロックを使用 (T0CKI ピンの入力をクロックとする)


カウント値の決定
----

システムクロックと、タイマーの割り込み時間 (インターバル時間) が決定したら、次は TMR0 に設定するカウント値を求めなければいけません。
タイマ 0 に必要なカウント数は次のように求めます。

~~~
カウント数 ＝ インターバル時間 / (システムクロック周期 * 4)
~~~

※ ここで、(システムクロック周期 * 4) という部分は、カウンタのインクリメントにかかる時間を示しています。
1 命令にかかる時間 (1 サイクル時間) は 4 クロックにかかる時間と同じです。
10 MHz のクロックなら、カウンタのインクリメントに 0.1 * 4 = 0.4μsec の時間がかかります。
20 MHz ならば、0.2μsec です。
つまり、システムクロックが速いほど、タイマーで設定できるインターバル時間は短くなってしまいます。

#### 例: 10MHz の時に 10 msec のインターバルが欲しい時

~~~
インターバル時間 … 10 msec ＝ 10,000μsec
クロック周期 * 4 … 0.1μsec * 4 ＝ 0.4μsec

∴必要なカウント数 ＝ 10,000 / 0.4 ＝ 25,000
~~~

TMR0 だけでは、256 カウントしかできませんから、25,000 カウントを実現するにはプリスケーラの力を借りる必要があります。
この場合、プリスケーラのスケール値は 128 あるいは 256 に設定することになります。

~~~
1:128 のスケールの場合    25,000 / 128 ＝ 195.3 …
1:256 のスケールの場合    25,000 / 256 ＝ 97.6 …
~~~

1:128 のスケールを使った場合は、TMR0 が 195 回インクリメントされた時に、オーバーフローが起こるようにすればいいわけです。
つまり、255 - 195 = 60 という値を TMR0 にセットしてからタイマをスタートさせてやります。

~~~
;プリスケーラの設定
    BSF      STATUS, RP0    ;Bank1
    MOVLW    B'10000110'    ;スケール 1:128
    MOVWF    OPTION_REG
    BCF      STATUS, RP0    ;Bank0

;TMR0 へカウンタ初期値をセット
    MOVLW    D'60'          ;255 - 195 = 60
    MOVWF    TMR0
~~~

割り込み時の処理
----

TMR0 レジスタがオーバーフローすると、**INTCON&lt;T0IF&gt;** ビットが 1 になり、割り込みが発生します（割り込みを許可していれば）。
割り込みが発生すると、プログラムカウンタが強制的に 0x0004 に設定され、**4 番地にジャンプ**します。
つまり割り込み発生時のルーチンは 4 番地に書いておけばよいことになります。
次のようにして、4 番地からサブルーチンにジャンプさせるのも 1 つの方法です。

~~~
    ORG     0             ;エントリポイント
    GOTO    MAIN          ;
    ORG     4             ;割り込み時にここにジャンプしてくる
    GOTO    INTR          ;割り込みルーチンへ飛ばす
      :
MAIN
      :
INTR
    BCF     INTCON, T0IF  ;割り込みフラグをクリア（必須）
    （ここに割り込み処理を記述する）
    RETFIE                ;次の割り込みを許可してリターン（必須）
~~~

※ 割り込み全体を許可するには、INTCON&lt;GIE&gt; ビットを 1 にセットします。


インターバルタイマーとしてタイマ 0 を使う
----

一定時間ごとにある特定の処理をしたい場合は、インターバルタイマとして使うことになります。
タイマ 0 をインターバルタイマとして使うには、割り込みルーチンの最初で再び TMR0 を再設定してやります。

~~~
INTR
    BCF     INTCON, T0IF  ;割り込みフラグをクリア（必須）
    MOVLW   D'60'         ;TMR0 の再設定
    MOVWF   TMR0          ;
    (ここに割り込み処理を記述する)
    RETFIE                ;次の割り込みを許可してリターン（必須）
~~~

※ ただし、TMR0 の再設定までのタイムラグがあるため、正確なインターバル時間が要求される環境では使えません。
このような場合は、タイマ 1 と CCP の機能を利用してください。
タイマ 1 は、ミッドレンジシリーズの PIC16F84 などで使用することができます。


タイマーの開始
----

タイマーの設定が終了し、タイマーをスタートするには、タイマ 0 の割り込みと、全体の割り込みを許可してやります。

#### タイマーの開始

~~~
    BSF      INTCON, T0IE    ;タイマ 0 の割り込みを許可
    BSF      INTCON, GIE     ;全体の割り込みを許可
~~~


タイマ 0 サンプルプログラム
----

次のコードはタイマ 0 を使ったサンプルプルグラムです。
割り込み発生時の処理を自由に記述してください。

#### タイマ 0 を使ったサンプルプログラム

~~~
;******************************************************************************
; タイマ 0 による割り込み処理のサンプルプログラム
;
;  10 msec でタイマ 0 割り込み (10MHz の場合)
;******************************************************************************

    LIST     P=PIC16F84
    INCLUDE  "P16F84.INC"

    ORG      0        ;エントリポイント
    GOTO     MAIN
    ORG      4        ;割り込みルーチン
    GOTO     INTR

MAIN
    BSF      STATUS, RP0   ;Bank1
    MOVLW    B'10000110'   ;スケール 1:128
    MOVWF    OPTION_REG
    BCF      STATUS, RP0   ;Bank0

    MOVLW    D'60'         ;タイマ 0 用カウンタ初期値
    MOVWF    TMR0

    BSF      INTCON, T0IE  ;タイマ 0 割り込み許可
    BSF      INTCON, GIE   ;全体の割り込み許可

MAIN_LOOP
    NOP
    ■■■ ここにメインの処理を書く ■■■
    GOTO MAIN_LOOP

INTR
    BCF      INTCON, T0IF  ;タイマ 0 割り込みフラグクリア
;   MOVLW    D'60'         ;インターバルタイマとして使うなら TMR0 再設定
;   MOVWF    TMR0          ;
    ■■■ ここに割り込み処理を書く ■■■
    RETFIE                 ;割り込みを許可してリターン
~~~

