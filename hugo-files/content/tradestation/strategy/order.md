---
title: "TradeStationメモ: 自動売買ストラテジーから注文を出す"
url: "p/6gxamfn/"
date: "2017-10-08"
tags: ["tradestation"]
description: "トレードステーションで自動売買ストラテジーを動作させると、アプリケーションを立ち上げておくだけで自動的にトレードを行ってくれます。ここでは、ストラテジー内からどのように注文を出すかを説明します。"
aliases: /tradestation/strategy/order.html
---

EasyLanguage での注文は、売買の種類 (Order Verbs)、タイミング (Order Actions)、発注株数を組み合わせて指定します。

オーダーの種類 (Order Verbs)
----

Order Verbs は買いで入るのか売りで入るのかを指定します。
`Buy` と `SellShort` はロングポジションとショートポジションの生成に使用し、`Sell` と `BuyToCover` は現在のポジションの決済に使用します。

Buy
: **ロングポジション（買い玉）を追加します。** すでにショートポジションがある場合は、相殺されるようにそのポジションが先に決済されます（両建てにはなりません）。

SellShort (Sell Short)
: **ショートポジション（売り玉）を追加します。** すでにロングポジションがある場合は、相殺されるようにそのポジションが先に決済されます（両建てにはなりません）。

Sell
: **ロングポジション（買い玉）を決済します。** ノーポジション (flat) 状態のときはこのオーダーは無視されます（このオーダーでショートポジションを取ることはありません）。

BuyToCover (Buy To Cover)
: **ショートポジション（売り玉）を決済します。** ノーポジション (flat) 状態のときはこのオーダーは無視されます（このオーダーでロングポジションを取ることはありません）。


オーダーのタイミング (Order Actions)
----

Order Verbs の後ろには、オーダーをどのタイミングで実行するかを示す Order Actions を指定します。
Order Actions には次のようなものがあります。

next bar at market
: 次のバーの開始時に注文を実行します。

next bar stop
: 逆指値 (stop price) に達したとき、次のバーの開始時に注文を実行します。

next bar limit
: 指値 (limit price) に達したとき、次のバーの開始時に注文を実行します。

this bar on close
: 現在のバーが終了するときに注文を実行します。主にバックテストで使用されます。

`at`、`on`、`of` などの単語は EasyLanguage ではスキップ語として無視されるため、省略して記述することもできます（自然な英語に近くなるように記述するのが慣例です）。


発注株数の指定
----

発注株数は、Order Verbs（`Buy` など）の後ろ、またはオーダーのタイミング（`next bar` など）の後ろで **`XXX shares`** という形式で指定します。

```
Buy 100 shares next bar at market;  // 次のバーの開始時に 100 株購入
Buy next bar 100 shares at market;  // 同上
```


オーダーのサンプル
----

下記は Order Verbs と Order Actions を組み合わせた注文実行の例です。

```
Buy next bar at market;        // 次のバーの開始時に、買いで入る
SellShort next bar at market;  // 次のバーの開始時に、売りで入る
SellShort next bar 50 limit;   // 次のバーの開始時に、指値に達していたら 50 株の売りで入る
Sell next bar 50 stop;         // 次のバーの開始時に、逆指値に達していたら 50 株の買い玉を決済する
BuyToCover this bar on close;  // 現在のバーの終了時に、売り玉を決済する
```
