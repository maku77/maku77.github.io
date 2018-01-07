---
title: 表示中のすべてのチャートに対して処理する
date: "2015-10-11"
---

すべてのチャートをループ処理
====
現在表示中のチャートを順番に処理するには、`ChartFirst` 関数で最初のチャートの ID を取得し、`ChartNext` 関数でその次のチャートの ID を取得していく、という感じで処理できます。
処理するチャートがなくなったときは、上記の関数は -1 を返すので、正の値を返す間だけ繰り返し処理すれば OK です。

下記のサンプルでは、すべてのチャートに対して `setupChart` 関数を順番に呼び出していきます。

```mql
void setupChart(long chartId = 0) {
    // ここにチャートごとの処理を記述
}

void OnStart() {
    for (long id = ChartFirst(); id >= 0; id = ChartNext(id)) {
        setupChart(id);
        ChartRedraw(id);
    }
}
```

オブジェクト指向の CChart クラスを使って記述
====
オブジェクト指向でコーディングしたい場合は、`CChart` クラスを使用して同様に処理することができます。

```mql
#include <Charts\Chart.mqh>

void setupChart(CChart& chart) {
    // ここに chart オブジェクトを使った処理を記述
}

void OnStart() {
    CChart c;
    for (c.FirstChart(); c.ChartId() >= 0; c.NextChart()) {
        setupChart(c);
        c.Redraw();
    }
}
```

