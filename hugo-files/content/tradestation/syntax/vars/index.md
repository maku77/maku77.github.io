---
title: "TradeStationメモ: 変数を定義する (var, variables)"
url: "p/bdx2n5k/"
date: "2017-10-08"
tags: ["tradestation"]
description: "コード内の複数箇所で同じ値を使用する場合は、その値を変数として定義しておくと便利です。"
aliases: /tradestation/syntax/vars.html
---

変数を定義する
----

EasyLanguage で変数を定義するには、下記のいずれかのキーワードを使用します（すべて同じ意味で、大文字・小文字の区別もありません）。
迷ったら一番短い `var` を使っておけば OK です。

```
var:
vars:
variable:
variables:
```

変数は初期値に応じて、数値型、真偽値型 (true or false)、文字列型のいずれかになります。
初期値は変数名の直後に指定する必要があり、省略できません。
下記の例では 3 つの変数（数値、真偽値、文字列）を定義しています。

```
var: LastHigh(0), NewHigh(false), HighAlert("");
```

複数行に分けて定義することも可能です。
各変数にコメントを記述したいときに便利です。

```
var:
    LastHigh(0),     { Creates a numeric variable }
    NewHigh(false),  { Creates a true/false variable }
    HighAlert("");   { Creates a text variable }
```

EasyLanguage では 1 文字の予約語（`O`、`H`、`L`、`C` など）があるため、変数名に 1 文字は使わないようにしましょう。
`my` などのプレフィックスを付けるか、2 文字以上の名前を付けることをお勧めします。
なお、変数名の最大文字数は 42 文字で、大文字・小文字は区別されません。


変数に代入する
----

変数には `=` 演算子を使って新しい値を代入できます。

```
var: FastAverage(0), SlowAverage(0);

FastAverage = AverageFC(Close, 9);
SlowAverage = AverageFC(Close, 18);
```

上記の例では、過去 9 本分の移動平均値と過去 18 本分の移動平均値を変数に代入しています。


変数はバー間で引き継がれる
----

EasyLanguage の変数は、各バーの計算処理の際に**前のバーで保持していた値を引き継ぐ**という特徴があります。
例えば、下記のインジケーターは変数の値をプロットするだけですが、バーの計算処理のたびに変数の値が 1 ずつ増えていきます。

```
var: myCounter(0);

myCounter += 1;
Plot1(myCounter, "SampleCounter");
```

このインジケーターをチャート分析ウィンドウに適用すると、`myCounter` 変数の値が各バーごとに 1 ずつ増えていく様子がわかります。
`var:` キーワードで指定した初期値による初期化は、最初のバーのときだけ実行されます。

{{< image src="img-001.png" >}}


過去のバーの変数値を参照する
----

変数の値は次のバーに引き継がれますが、`myCounter[1]` のように記述することで過去のバーの値を遡って参照することもできます（1 つ前の終値を `Close[1]` で参照するのと同様です）。

下記のストラテジーでは、9 本の移動平均値を `myAvg` 変数に格納しています。
`myAvg[3]` とすると 3 本前の移動平均値を参照できるため、これと現在の移動平均値を比較してゴールデンクロスで買い、デッドクロスで売り、という戦略を実装しています。

```
var: myAvg(0);

myAvg = AverageFC(Close, 9);
if myAvg Crosses above myAvg[3] then Buy next bar 100 shares at market;
if myAvg Crosses below myAvg[3] then Sell next bar at market;
```

これはサンプルとして実装したものであり、根拠のあるストラテジーではないため、実際の自動売買には使用しないでください。
