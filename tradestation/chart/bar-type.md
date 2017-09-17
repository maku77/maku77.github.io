---
title: "EasyLanguage: チャートのバータイプ、足種を取得する (BarType, BarInterval)"
created: 2017-09-17
description: "分析チャートがどのような足のタイプで表示されているかを調べるには BarType 属性を参照します。"
---

BarType
----

分析テクニック（インジケーターや自動売買ストラテジ）が適用されているチャートが、現在どのようなインターバル（日足や週足）で表示されているかを調べるには、`BarType` を参照します。

`BarType` は、下記のような整数値を返します。

* 0 = ティック足 (Tick bar or volume bar)
* 1 = 分足 (Intraday minute bar)
* 2 = 日足 (Daily bar)
* 3 = 週足 (Weekly bar)
* 4 = 月足 (Montly bar)
* 5 = ポイントアンドフィギュア (Point & figure)
* 6 = (予約済み)
* 7 = (予約済み)
* 8 = カギ足
* 9 = KASE
* 10 = 新値足
* 11 = モメンタム
* 12 = レンジ
* 13 = 練行足
* 14 = 秒足

例えば、日足チャートのみで動作する自動売買ストラテジを記述するには、下記のようにします。

~~~
If BarType = 2 then begin
    Sell next bar at market;
end;
~~~

BarInterval
----

チャートが分足 (`BarType == 1`)、秒足 (`BarType == 14`)、ティック足 (`BarType == 0`) で表示されている場合は、`BarInterval` を参照することで、何分足、何秒足、何ティック足の表示かを調べることができます。

下記のようなインジケータープログラムをチャートに適用してみてください。

~~~
Once begin
    Print("BarType=", BarType:0:0, ", BarInterval=", BarInterval:0:0);
end;
~~~

チャートの足種を切り替えるごとに下記のように表示されるはずです。

- 1ティック足: `BarType=0, BarInterval=1`
- 10ティック足: `BarType=0, BarInterval=10`
- 25ティック足: `BarType=0, BarInterval=25`
- 15秒足: `BarType=14, BarInterval=15`
- 30秒足: `BarType=14, BarInterval=30`
- 1分足: `BarType=1, BarInterval=1`
- 2分足: `BarType=1, BarInterval=2`
- 3分足: `BarType=1, BarInterval=3`
- 4分足: `BarType=1, BarInterval=4`
- 5分足: `BarType=1, BarInterval=5`
- 10分足: `BarType=1, BarInterval=10`
- 15分足: `BarType=1, BarInterval=15`
- 20分足: `BarType=1, BarInterval=20`
- 30分足: `BarType=1, BarInterval=30`
- 60分足: `BarType=1, BarInterval=60`
- 日足: `BarType=2, BarInterval=0`
- 週足: `BarType=3, BarInterval=0`
- 月足: `BarType=4, BarInterval=0`

