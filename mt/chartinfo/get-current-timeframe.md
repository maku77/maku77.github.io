---
title: "現在のチャートのタイムフレーム（H1 など）を取得する"
date: "2016-03-21"
redirect: https://toushi.maku.blog/p/p7gpx7f/
sitemap: false
---

現在のタイムフレーム設定を取得する
----

現在のチャートのタイムフレーム（ローソク足 1 本あたりの時間）を調べるには、組み込みの `Period` 関数を使用します（あるいは、`_Period` 変数を使用することもできます）。

```mql
ENUM_TIMEFRAMES timeframe = Period();
```

結果として、下記のような enum 値を得ることができます。

```
PERIOD_M1 ... 1 minute
PERIOD_M2 ... 2 minutes
PERIOD_M3 ... 3 minutes
PERIOD_M4 ... 4 minutes
PERIOD_M5 ... 5 minutes
PERIOD_M6 ... 6 minutes
PERIOD_M10 ... 10 minutes
PERIOD_M12 ... 12 minutes
PERIOD_M15 ... 15 minutes
PERIOD_M20 ... 20 minutes
PERIOD_M30 ... 30 minutes
PERIOD_H1 ... 1 hour
PERIOD_H2 ... 2 hours
PERIOD_H3 ... 3 hours
PERIOD_H4 ... 4 hours
PERIOD_H6 ... 6 hours
PERIOD_H8 ... 8 hours
PERIOD_H12 ... 12 hours
PERIOD_D1 ... 1 day
PERIOD_W1 ... 1 week
PERIOD_MN1 ... 1 month
```

現在のタイムフレームの秒数を取得する
---

ローソク足 1 本あたりが、何秒であるかを取得するには、`PeriodSeconds` 関数を使用します。

```mql
int seconds = PeriodSeconds(PERIOD_H1);  // 3600
```

現在のタイムフレームのテキスト表現を取得する
----

現在のタイムフレーム設定を、テキスト表現 ("M30") などで取得する関数は用意されていないようです。
下記の関数は、これを実現するものです。

```mql
string GetPeriodText() {
    switch (Period()) {
        case PERIOD_D1: return "D1";
        case PERIOD_H1: return "H1";
        case PERIOD_H4: return "H4";
        case PERIOD_M1: return "M1";
        case PERIOD_M5: return "M5";
        case PERIOD_M15: return "M15";
        case PERIOD_M30: return "M30";
        case PERIOD_MN1: return "MN1";
        case PERIOD_W1: return "W1";
    }
    return "???";
}
```


参考
----
- [MQL5 - Period 関数](https://www.mql5.com/en/docs/check/period)

