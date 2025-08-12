---
title: "部品メモ: RS-232C (Recommend Standard number 232) ─ PIC めもめも"
url: "p/dppsjeu/"
date: "2002-08-01"
tags: ["pic"]
aliases: ["/memo/pic/circuit/rs232c.html"]
---

はじめに（用語とか）
----

- **スペース状態** ･･･ 信号 0。入力 +3V 以上。出力 +5V ～ +15V。
- **マーク状態** ･･･ 信号 1。入力 -3V 以下。出力 -5V ～ -15V。
- **短絡時最大出力電流** ･･･ 500mA。
- **ハードウェアフローコントロール** ･･･ RTS と CTS の信号線を使ったフロー制御。RTS／CTS とも記述されます。
- **ソフトウェアフローコントロール** ･･･ ソフトウェアによるフロー制御。アプリケーションで決められた特殊な記号を送信することでフロー制御を行います。RTS と CTS ワイヤは使用しません。Xon／Xoff とも記述されます。
- **DTE デバイス** ･･･ 通常は PC を示します。オスコネクタを持ちます。
- **DCE デバイス** ･･･ 通常は シリアルデバイスを示します。メスコネクタを持ちます。DTE デバイスと DCE デバイスをつなぐ時は通常ストレートケーブルを用います。
- **ヌルモデムケーブル／ヌルアダプタ** ･･･ 送信と受信の信号線をクロスさせたものです。TxD ⇔ RxD、DTR ⇔ DSR、RTS ⇔ CTS のようにクロスされます。


DSub 9 ピン
----

{{< image src="img-001.png" >}}

（DTE デバイス（PC 側）オス）

<table align="center">
  <caption>9 ピンミニ DSUB コネクタ (PC 側オス)</caption>
  <tr>
    <th>ピン番号</th><th>表記</th><th>I/O</th><th>意味</th><th>説明</th>
  </tr>
  <tr>
    <td class="namec">1</td>
    <td class="namec2">DCD</td>
    <td class="c">In</td>
    <td>Data Carrier Detect（キャリア検出）</td>
    <td>外部端末の送受信準備ができたことを知る</td>
  </tr>
  <tr>
    <td class="namec">2</td>
    <td class="namec2">RxD</td>
    <td class="c">In</td>
    <td>Receive Data（受信データ）</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="namec">3</td>
    <td class="namec2">TxD</td>
    <td class="c">Out</td>
    <td>Transmit Data（送信データ）</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="namec">4</td>
    <td class="namec2">DTR</td>
    <td class="c">Out</td>
    <td>Data Terminal Ready（データ端末準備）</td>
    <td>PC が繋がっていることを認識させる（PC → 外部）</td>
  </tr>
  <tr>
    <td class="namec">5</td>
    <td class="namec2">GND</td>
    <td class="c">---</td>
    <td>Ground（信号用グラウンド）</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="namec">6</td>
    <td class="namec2">DSR</td>
    <td class="c">In</td>
    <td>Data Set Ready（データセット準備）</td>
    <td>端末が繋がっていることを認識する（PC ← 外部）</td>
  </tr>
  <tr>
    <td class="namec">7</td>
    <td class="namec2">RTS</td>
    <td class="c">Out</td>
    <td>Request To Send (DTE の送信要求)</td>
    <td>PC の送信開始信号（フロー制御用）（PC → 外部）</td>
  </tr>
  <tr>
    <td class="namec">8</td>
    <td class="namec2">CTS</td>
    <td class="c">In</td>
    <td>Clear To Send（DCE の送信要求）</td>
    <td>PC への受信開始要求（フロー制御用）（PC ← 外部）</td>
  </tr>
  <tr>
    <td class="namec">9</td>
    <td class="namec2">RI</td>
    <td class="c">In</td>
    <td>Ring Indication（被呼表示）</td>
    <td>電話がかかってきたことを伝える／PC の電源を制御する</td>
  </tr>
</table>

PC からデータを送信するのに最低限必要なのは、TxD(3) と GND(5) です。
データを受信する時は RxD(2) が必要です。
出力の信号線は、デフォルトでマーク状態(-)かスペース状態(+)のどちらかを出力してます。

<table align="center">
  <tr>
    <th>信号線</th><th>COMポート接続時</th><th>COMポートオープン時</th>
  </tr>
  <tr>
    <td class="namec">TxD (3)</td><td>mark(-)</td><td>mark(-)</td>
  </tr>
  <tr>
    <td class="namec">DTR (4)</td><td>mark(-)</td><td>space(+)</td>
  </tr>
  <tr>
    <td class="namec">RTS (7)</td><td>mark(-)</td><td>space(+)</td>
  </tr>
</table>

小さな回路なら、DTR(4) と RTS(7) から電源供給することができます。
といっても電流量は少ないし、マシンによって供給量が多少異なるので、あまり信頼できません。
DTR(4) と RTS(7) を並列に繋ぐと電流を増やせます。

- **CD／DCD (1)** ･･･ モデムが相手側のモデムと接続を確立した時や、キャリアトーンの検出を知らせます。この信号線はめったに使用されません。
- **RD／RxD (2)** ･･･ DCE から DTE へのデータ受信に使用されます。RD ワイヤは DTE デバイスがアイドル状態の時、マーク状態になります。
- **TD／TxD (3)** ･･･ DTE から DCE へのデータ送信に使用されます。TD ワイヤは DTE デバイスがアイドル状態の時、マーク状態になります。逆にバッファがいっぱいの時はスペース状態になります。
- **DTR (4)** ･･･ DTE デバイスの準備ができていることを示します。電源が入っていることを確かめるために使用したりしますが、あまり使われない信号線です。Windows で COM ポートを開くと 1 になり、閉じると 0 になります。
- **DSR (6)** ･･･ DCE デバイスの準備ができていることを示します。電源が入っていることを確かめるために使用したりしますが、あまり使われない信号線です。
- **RTS (7)** ･･･ DTE デバイスと DCE デバイス両方のハードウェアフローコントロールが ON の場合に使用されます。DTE がデータ受信可能（待機中）な時は、このワイヤがマーク状態になります。逆にバッファがいっぱいの時はスペース状態になります。
- **CTS (8)** ･･･ DTE デバイスと DCE デバイス両方のハードウェアフローコントロールが ON の場合に使用されます。DCE がデータ受信可能（待機中）な時は、このワイヤがマーク状態になります。
- **RI (9)** ･･･ モデムが呼び出し信号を受信したことを知らせます。つまり電話が鳴ったときに PC にそれを知らせます。この信号線はめったに使用されません。


非同期通信
----

Windows などの PC では主に非同期通信が使用されます。
同期通信のように常に信号をやりとりしていないため、データの送信を示すためのスタートビットが必要になります。
データを送信していない時（アイドル時）は TX はマーク状態 (1) になっているので、スタートビットは 0 の信号になります。
1 つのデータの構造は次のようになります。

<table align="center">
  <tr>
    <td class="c">スタートビット<BR>（1 bit）</td>
    <td class="c">データ<BR>（4 ～ 8 bits。主に 8 bits）</td>
    <td class="c">ストップビット<BR>（1 bit）</td>
  </tr>
</table>


ケーブル長
----

<table align="center">
  <CAPTION>24 ゲージワイヤの実用的なケーブル長</CAPTION>
  <tr>
    <th>Baud Rate</th>
    <th>シールドケーブル</th><th>非シールドケーブル</th>
  </tr>
  <tr class="r">
    <td class="namec">110</td><td>1500 m</td><td rowspan="2">300 m</td>
  </tr>
  <tr class="r">
    <td class="namec">300</td><td>1200 m</td>
  </tr>
  <tr class="r">
    <td class="namec">1200</td><td>900 m</td><td rowspan="2">150 m</td>
  </tr>
  <tr class="r">
    <td class="namec">2400</td><td>600 m</td>
  </tr>
  <tr class="r">
    <td class="namec">4800</td><td>150 m</td><td>75 m</td>
  </tr>
  <tr class="r">
    <td class="namec">9600</td><td>75 m</td><td>30 m</td>
  </tr>
</table>

