---
title: "遅延時間 ─ PIC めもめも"
url: "p/yzbkxhz/"
date: "2002-08-01"
tags: ["pic"]
aliases: ["/memo/pic/asm/delay.html"]
---


1 サイクル (1 命令) にかかる時間
----

1 つの命令を実行するためにかかる時間（1 サイクル）は、**クロックの周期(sec) * 4**、あるいは、**4 / クロックの周波数(Hz)** となります。
具体的な値は次のようになります。


- 10MHzの場合の1命令にかかる時間 ･･･ 0.1 (μsec) * 4 = **0.4 (μsec)**
- 20MHzの場合の1命令にかかる時間 ･･･ 0.05 (μsec) * 4 = **0.2 (μsec)**

プログラムカウンタの値を変更する命令の場合は、2 サイクルの時間がかかります。
このような命令には **CALL, GOTO, RETFIE, RETLW, RETURN** があります。
また、**DECFSZ, INCFSZ, BTFSC, BTFSS でスキップした場合**も 2 サイクルかかります。


遅延時間を作るサブルーチン
----

遅延を発生させるサブルーチンを作る場合は、次のような手順で作成します。

### 1. 必要なサイクル数を求める

サブルーチンに必要なサイクル数は、**遅延時間 / 1 サイクルにかかる時間**で求めることができます。
例えば、10MHz (0.4μsec) で 100μsec の遅延を作りたい場合は、100 / 0.4 = 250 サイクルが必要になります。

### 2. ループ回数を決める

ループ回数は、**(1 で求めたサイクル数 - 6) / 3** で求められます（余りは NOP で調整します）。
**-6** の部分は、MOVLW, MOVWF, DECFSZ(2), RETURN のサイクルで、**/3** の部分は、DECFSZ(1), GOTO の部分の実行回数を求めています。
以下にいくつかの遅延時間発生ルーチンの例を示します。

- 10MHz 用
    - <a target="_blank" href="wait20_10mhz.txt">20μsec の遅延時間サブルーチン</a>
    - <a target="_blank" href="wait50_10mhz.txt">50μsec の遅延時間サブルーチン</a>
    - <a target="_blank" href="wait100_10mhz.txt">100μsec の遅延時間サブルーチン</a>
    - <a target="_blank" href="wait250_10mhz.txt">250μsec の遅延時間サブルーチン</a>
- 20MHz 用
    - <a target="_blank" href="wait15_20mhz.txt">15μsec の遅延時間サブルーチン</a>

### 長い遅延時間を作成する場合の注意

1 秒などの長い遅延時間を作成する場合、ループ用カウンタのオーバーフローを避けるため、短い遅延時間のサブルーチンを何度も呼び出すことになります。
この時、ループのカウントに使用する変数は別のものを使用することに注意してください。


遅延時間サブルーチン自動生成 Script
----

クロックの周波数と、作りたい遅延時間を入力して「作成」ボタンを押してください。
遅延時間を発生するためのサブルーチンが表示されます。
ただし、あまり大きな遅延時間は作成できません（カウンタ用の変数がオーバーフローするため）。

- 10MHz … 308μsec まで
- 20MHz … 154μsec まで

<SCRIPT language="JavaScript">
<!--
function createRoutine() {
	var str = "";
	var freq = document.f.freq.value;  // クロック周波数 (MHz)
	var delay = document.f.delay.value;  // 作りたい遅延時間 (μsec)
	var cycles = delay * freq / 4;  // 必要なサイクル数
	var odd = (cycles - 6) % 3;  // 余り (=NOPの数)
	var loop = (cycles - 6 - odd) / 3;  // ループ数

	// 結果格納
	document.f.oneCycle.value = 4 / (freq * 1000000);  // 1サイクルに必要な時間
	document.f.cycles.value = cycles;  // 必要なサイクル数
	document.f.loop.value = loop;  // 必要なループ数
	document.f.odd.value = odd;  // 必要な NOP 数

	str = "wait_count    EQU  0x20    ; 遅延発生ルーチン用の変数をこんな感じで用意してください\r\n\r\n";
	str += ";==============================================================================\r\n";
	str += "; " + delay + "μsec の遅延発生ルーチン (クロック = " + freq + "MHz)\r\n";
	str += ";==============================================================================\r\n";
	str += "Wait" + delay + "_" + freq + "MHz\r\n";
	str += "      movlw  D'" + loop + "'\r\n";
	str += "      movwf  wait_count\r\n";
	str += "Wait" + delay + "_" + freq + "MHzLoop\r\n";
	str += "      decfsz wait_count, F\r\n";
	str += "      goto   Wait" + delay + "_" + freq + "MHzLoop\r\n";
	var i = 0;
	for (; i < odd; i++) {
		str += "      nop\r\n";
	}
	str += "      return\r\n";
	document.f.textarea_1.value = str;
}
//-->
</SCRIPT>

<CENTER>
<FORM name="f">
<INPUT type="textfield" name="freq" size="4" value="10">MHz　
<INPUT type="textfield" name="delay" size="4" value="100">μsec　
<INPUT type="button" name="button1" value="作成" onClick="createRoutine()">
<BR>
<TEXTAREA name="textarea_1" cols="80" rows="13"></TEXTAREA>
<BR>

<TABLE>
	<TR>
		<TD>1 サイクルに必要な時間は</TD>
		<TD><INPUT type="textfield" name="oneCycle" size="4"></TD>
		<TD>必要なサイクル数は</TD>
		<TD><INPUT type="textfield" name="cycles" size="4"></TD>
	</TR>
	<TR>
		<TD>必要なループ数は</TD>
		<TD><INPUT type="textfield" name="loop" size="4"></TD>
		<TD>必要な NOP の数は</TD>
		<TD><INPUT type="textfield" name="odd" size="4"></TD>
	</TR>
</TABLE>
</CENTER>
</FORM>

