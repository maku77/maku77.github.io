---
title: "PIC アセンブラ (PIC16Fxxx) 命令セット ─ PIC めもめも"
url: "p/no9voj9/"
date: "2002-08-01"
tags: ["pic"]
---

<style>
  table.code td {
    font-family: monospace;
  }
  table.code span.strong {
    color: black;
    font-weight: bolder;
  }
  td.c { text-align: center; }
  td { background: white; }
  th { background: #ddd; }
  span.lit { color: blue; font-weight: bolder; }
  span.dest { color: red; font-weight: bolder; }
  span.bit { color: green; font-weight: bolder; }
</style>

<h2>PIC16Fxxx 命令セット (14bit)</h2>
<table class="code" align="center">
	<tr>
		<th>カテゴリ</th><th>ニモニック</th><th colspan="2">意味</th><th>コード</th>
	</tr>
	<tr>
		<td class="namec" rowspan="4">加算・減算</td>
		<td><span class="strong">ADDWF<span> f, <span class="dest">d<span></td>
		<td>W + f → <span class="dest">d<span></td>
		<td>Add W and f</td>
		<td>00 0111 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">SUBWF<span> f, <span class="dest">d<span></td>
		<td>f - W → <span class="dest">d<span></td>
		<td>Subtract W from f</td>
		<td>00 0010 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">ADDLW<span> <span class="lit">k<span></td>
		<td>W + <span class="lit">k<span> → W</td>
		<td>Add literal and W</td>
		<td>11 111x <span class="lit">kkkk kkkk<span></td>
	</tr>
	<tr>
		<td><span class="strong">SUBLW<span> <span class="lit">k<span></td><td><span class="lit">k<span> - W → W</td>
		<td>Subtract W from literal</td>
		<td>11 110x <span class="lit">kkkk kkkk<span></td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="7">論理演算</td>
		<td><span class="strong">ANDWF<span> f, <span class="dest">d<span></td>
		<td>W AND f → <span class="dest">d<span></td>
		<td>AND W with f</td>
		<td>00 0101 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">IORWF<span> f, <span class="dest">d<span></td>
		<td>W OR f → <span class="dest">d<span></td>
		<td>Inclusive OR W with f</td>
		<td>00 0100 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">XORWF<span> f, <span class="dest">d<span></td>
		<td>W XOR f → <span class="dest">d<span></td>
		<td>Exclusive OR W with f</td>
		<td>00 0110 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">ANDLW<span> <span class="lit">k<span></td>
		<td>W AND <span class="lit">k<span> → W</td>
		<td>And literal with W</td>
		<td>11 1001 <span class="lit">kkkk kkkk<span></td>
	</tr>
	<tr>
		<td><span class="strong">IORLW<span> <span class="lit">k<span></td>
		<td>W OR <span class="lit">k<span> → W</td>
		<td>Inclusive OR literal with W</td>
		<td>11 1000 <span class="lit">kkkk kkkk<span></td>
	</tr>
	<tr>
		<td><span class="strong">XORLW<span> <span class="lit">k<span></td>
		<td>W XOR <span class="lit">k<span> → W</td>
		<td>Exclusive OR literal with W</td>
		<td>11 1010 <span class="lit">kkkk kkkk<span></td>
	</tr>
	<tr>
		<td><span class="strong">COMF<span> f, <span class="dest">d<span></td>
		<td>f → <span class="dest">d<span></td>
		<td>Complement f</td>
		<td>00 1001 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="3">移動</td>
		<td><span class="strong">MOVF<span> f, <span class="dest">d<span></td>
		<td>f → <span class="dest">d<span></td>
		<td>Move f</td>
		<td>00 1000 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">MOVWF<span> f</td>
		<td>W → f</td>
		<td>Move W to f</td>
		<td>00 0000 1fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">MOVLW<span> <span class="lit">k<span></td>
		<td><span class="lit">k<span> → W</td>
		<td>Move litaral to W</td>
		<td>11 00xx <span class="lit">kkkk kkkk<span></td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="2">クリア</td>
		<td><span class="strong">CLRF<span> f</td>
		<td>0 → f</td>
		<td>Clear f</td>
		<td>00 0001 1fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">CLRW<span> W</td>
		<td>0 → W</td>
		<td>Clear W</td>
		<td>00 0001 0xxx xxxx</td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="4">インクリメント<BR>デクリメント</td>
		<td><span class="strong">DECF<span> f, <span class="dest">d<span></td>
		<td>f - 1 → <span class="dest">d<span></td>
		<td>Decrement f</td>
		<td>00 0011 <span class="dest">d<span>fff ffff
	</tr>
	<tr>
		<td><span class="strong">INCF<span> f, <span class="dest">d<span></td>
		<td>f + 1 → <span class="dest">d<span></td>
		<td>Increment f</td>
		<td>00 1010 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">DECFSZ<span> f, <span class="dest">d<span></td>
		<td>f - 1 → <span class="dest">d<span><BR>Skip if result = 0</td>
		<td>Decrement f, Skip if 0</td>
		<td>00 1011 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">INCFSZ<span> f, <span class="dest">d<span></td>
		<td>f + 1 → <span class="dest">d<span><BR>Skip if result = 0</td>
		<td>Increment f, Skip if 0</td>
		<td>00 1111 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="2">ビット操作</td>
		<td><span class="strong">BCF<span> f, <span class="bit">b<span></td>
		<td>0 → f[<span class="bit">b<span>]</td>
		<td>Bit Clear f</td>
		<td>01 00<span class="bit">bb b<span>fff ffff<span>
	</tr>
	<tr>
		<td><span class="strong">BSF<span> f, <span class="bit">b<span></td>
		<td>1 → f[<span class="bit">b<span>]</td>
		<td>Bit set f</td>
		<td>01 01<span class="bit">bb b<span>fff ffff</td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="2">スキップ</td>
		<td><span class="strong">BTFSC<span> f, <span class="bit">b<span></td>
		<td>Skip if f[<span class="bit">b<span>] = 0</td>
		<td>Bit Test f, Skip if Clear</td>
		<td>01 10<span class="bit">bb b<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">BTFSS<span> f, <span class="bit">b<span></td>
		<td>Skip if f[<span class="bit">b<span>] = 1</td>
		<td>Bit Test f, Skip if Set</td>
		<td>01 11<span class="bit">bb b<span>fff ffff</td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="3">シフト</td>
		<td><span class="strong">RLF<span> f, <span class="dest">d<span></td>
		<td>
			f[n] → <span class="dest">d<span>[n+1]<BR>
			f[7] → C → <span class="dest">d<span>[0]
		</td>
		<td>Rotate Left f through Carry<BR>(左巡回シフト)</td>
		<td>00 1101 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">RRF<span> f, <span class="dest">d<span></td>
		<td>
			f[n] → <span class="dest">d<span>[n-1]<BR>
			f[0] → c → <span class="dest">d<span>[7]
		</td>
		<td>Rotate Right f through Carry<BR>(右巡回シフト)</td>
		<td>00 1100 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr>
		<td><span class="strong">SWAPF<span> f, <span class="dest">d<span></td>
		<td>
			f[0:3] → <span class="dest">d<span>[4:7]<BR>
			f[4:7] → <span class="dest">d<span>[0:3]
		</td>
		<td>Swap nibbles in f<BR>(上位ビット ⇔ 下位ビット)</td>
		<td>00 1110 <span class="dest">d<span>fff ffff</td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="5">ジャンプ</td>
		<td><span class="strong">GOTO<span> <span class="lit">k<span></td>
		<td></td>
		<td>Go to address</td>
		<td>10 1<span class="lit">kkk kkkk kkkk<span>
	</tr>
	<tr>
		<td><span class="strong">CALL<span> <span class="lit">k<span></td>
		<td></td>
		<td>Call subroutine</td>
		<td>10 0<span class="lit">kkk kkkk kkkk<span></td>
	</tr>
	<tr>
		<td><span class="strong">RETFIE<span></td>
		<td></td>
		<td>Return from interrupt</td>
		<td>00 0000 0000 1001</td>
	</tr>
	<tr>
		<td><span class="strong">RETLW<span> <span class="lit">k<span></td>
		<td></td>
		<td>Return with literal in W</td>
		<td>11 01xx <span class="lit">kkkk kkkk<span></td>
	</tr>
	<tr>
		<td><span class="strong">RETURN<span></td>
		<td></td>
		<td>Return from Subroutine</td>
		<td>00 0000 0000 1000</td>
	</tr>
	<tr class="line"></tr>
	<tr>
		<td class="namec" rowspan="3">その他</td>
		<td><span class="strong">CLRWDT<span></td>
		<td></td>
		<td>Clear Watchdog Timer</td>
		<td>00 0000 0110 0100</td>
	</tr>
	<tr>
		<td><span class="strong">NOP<span></td>
		<td></td>
		<td>No Operation</td>
		<td>00 0000 0xx0 0000</td>
	</tr>
	<tr>
		<td><span class="strong">SLEEP<span></td>
		<td></td>
		<td>Go into standby mode</td>
		<td>00 0000 0110 0011</td>
	</tr>
</table>

<table align="center">
  <caption>OPCODE フィールドの説明</caption>
	<tr>
		<TH>Field</TH><TH>Size</TH><TH>Description</TH>
	</tr>
	<tr>
		<td class="c">f</td><td class="c">7</td>
		<td>ワーキングレジスタのアドレス (0x00 ～ 0x7F)</td>
	</tr>
	<tr>
		<td class="c">W</td><td class="c">─</td>
		<td>ワーキングレジスタ（アキュムレータ）</td>
	</tr>
	<tr>
		<td class="c"><span class="bit">b<span></td><td class="c">3</td>
		<td>8 ビットファイルレジスタ内のビットアドレス</td>
	</tr>
	<tr>
		<td class="c"><span class="lit">k<span></td><td class="c">8/11</td>
		<td>
			リテラル、定数またはラベル<BR>
			(0 ～ 255) or (-128 ～ 127) or (アスキーコード 'A' とか)
		</td>
	</tr>
	<tr>
		<td class="c">x</td><td class="c">─</td>
		<td>
			無効 (0 or 1)<BR>
      アセンブラは x=0 としてコードを生成します。<br>
			すべてのソフトウェアツールとの互換性を確保するために x=0 を推奨します。
		</td>
	</tr>
	<tr>
		<td class="c"><span class="dest">d<span></td><td class="c">1</td>
		<td>
			結果格納先指名子 (0 or 1)<BR>
			0: W に格納<BR>
			1: ファイルレジスタ f に格納 (Default)
		</td>
	</tr>
	<tr>
		<td class="c">PC</td><td class="c">─</td>
		<td>プログラムカウンタ</td>
	</tr>
	<tr>
		<td class="c"><span style="text-decoration: overline;">TO<span></td><td class="c">─</td>
		<td>タイムアウトビット</td>
	</tr>
	<tr>
		<td class="c"><span style="text-decoration: overline;">PD<span></td><td class="c">─</td>
		<td>パワーダウンビット</td>
	</tr>
</table>

