---
title: "チャートのローソク足の数を取得する"
date: "2015-06-10"
redirect: https://toushi.maku.blog/p/6nw7gpx/
sitemap: false
---

[`Bars()` 関数](https://www.mql5.com/en/docs/series/bars)を使用すると、指定したシンボル、時間足のローソク足の数を調べることができます。

```mql
int Bars(string symbol, ENUM_TIMEFRAMES timeframe)
```

#### 使用例

```mql
int bars = Bars("USDJPY", PERIOD_MN1));  // 157
int bars = Bars("USDJPY", PERIOD_D1));   // 2142
int bars = Bars("USDJPY", PERIOD_H1));   // 2098
int bars = Bars("USDJPY", PERIOD_M1));   // 32051
```

下記のようにすると、現在のチャートのローソク足の数を取得することができます。

```mql
int bars = Bars(_Symbol, _Period);
```

カスタムインジケータの `OnCalculate()` 関数の第一パラメータで渡される `rates_total` は、上記で求められる値と同じ値が格納されています。

