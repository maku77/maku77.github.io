---
title: "TradeStationメモ: 上昇トレンド・下降トレンドを把握する"
url: "p/g6e3ytf/"
date: "2017-10-08"
tags: ["tradestation"]
description: "トレンドの方向を把握することは、自動売買ストラテジーを作成するときの最初のステップとなります。"
aliases: /tradestation/general/grasp-trend.html
---

{{< image src="img-001.png" >}}

上昇トレンドや下降トレンドをどう定義するかは意見が分かれるところですが、ここでは単純に長期の移動平均線の傾きで判断することにします。

移動平均の値は `Average` 関数で取得できるので、まずはこれを変数に保持します。
ここでは 50 本分のバーの移動平均値を使用します。

```
Vars: myAvg(0);
myAvg = Average(Close, 50);
```

移動平均線の傾きが右肩上がりかどうかを調べるには、1 つ前の足の移動平均値と比べて上昇しているかを確認すればよいので、下記のような条件判定を行います。

```
If myAvg > myAvg[1] then begin
    // 上昇トレンド
end;
```

<div class="note">
EasyLanguage では、変数にインデックスを付けてアクセスすると、過去の足における変数値を遡って参照できます。<code>myAvg[1]</code> は、1 つ前の足の <code>myAvg</code> 変数の値を示しています。
</div>

下記のサンプルコードは、移動平均線を表示するインジケーターです。右肩上がりのときは赤色、右肩下がりのときは青色の線を描画します。

```
Vars: myAvg(0);

myAvg = Average(Close, 50);
SetPlotWidth(1, 3);  // 線を太めに
If myAvg > myAvg[1] then begin
    SetPlotColor(1, Red);  // 上昇トレンドは赤線
end
Else begin
    SetPlotColor(1, Blue);  // 下降トレンドは青線
end;
Plot1(myAvg);
```

このインジケーターをチャート分析ウィンドウに適用すると、下記のように表示されます。

{{< image src="img-001.png" >}}
