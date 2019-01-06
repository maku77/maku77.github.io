---
title: "任意の通貨ペア、時間足のチャートを開く"
date: "2015-10-10"
---


[ChartOpen 関数](https://www.mql5.com/en/docs/chart_operations/chartopen) を使うと、指定した通貨ペア、時間足のチャートを開くことができます。
下記は、ドル円の月足チャートを開く例です。

```mql
long chartId = ChartOpen("USDJPY", PERIOD_MN1);
if (chartId == 0) {
    Alert("Could not open a new chart");
    return;
}
printf("New chart ID: %lld", chartId);
```

現在アクティブなチャートと同じ通貨ペアのチャートを新しく開きたい場合は、`"USDJPY"` と指定しているところを `NULL` に置き換えれば OK です。
2 つ目のパラメータで指定する時間足は、[ENUM_TIMEFRAMES](https://www.mql5.com/en/docs/constants/chartconstants/enum_timeframes) の定義の中から選択します。


