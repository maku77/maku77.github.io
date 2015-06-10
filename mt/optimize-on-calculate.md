---
title: カスタムインジケータの OnCalculate 関数を最適化する
created: 2015-02-01
---

カスタムインジケータの計算部分を担う `OnCalculate` は、価格の変化 (Tick) ごとに呼び出されますが、`prev_calculated` パラメータの値をうまく使うことで、すでに計算済みの値を再計算しないでも済むように処理を最適化ことができます。
下記の `OnCalculate()` は、インジケータバッファ (`gBuffer`) に、各ローソク足の終値をセットしています。

```mql
// double gBuffer[];  // Buffer for indicator line

/**
 * How many candles should be re-calculated.
 */
int changedBars(int rates_total, int prev_calculated) {
    if (prev_calculated == 0) {
        return rates_total;
    }
    // The latest bar should be updated, so add 1.
    return rates_total - prev_calculated + 1;
}

int OnCalculate(const int rates_total,
                const int prev_calculated,
                const datetime &time[],
                const double &open[],
                const double &high[],
                const double &low[],
                const double &close[],
                const long &tick_volume[],
                const long &volume[],
                const int &spread[]) {
    // 直近から見て何本のローソク足に対して計算が必要か？
    int changed = changedBars(rates_total, prev_calculated);

    // インジケータの計算（新しく計算した部分だけ更新すれば OK）
    for (int i = 0; i < changed; ++i) {
        gBuffer[i] = close[i];
    }

    // 計算済みローソク足の数を返す
    return rates_total;
}
```

パラメータで渡される `prev_calculated` の値には、前回の `OnCalculate` 関数の戻り値として返した値が入ってきます。
つまり、`OnCalculate` 関数の戻り値として、いくつのローソク足の計算を終えたか (= `rates_total`) を返しておくことで、次回の `OnCalculate` 関数のパラメータ `prev_calculated` でその値を受け取ることができるということです。

`OnCalculate` 関数が呼び出された時、`gBuffer` 配列には、すでに `prev_calculated` の数だけ計算結果が格納されています。
例えば、現在のローソク足の数が 5 本 (`rates_total=5`) で、前回の計算数が 4 本 (`prev_calculated=4`) である場合、`gBuffer` の構成は下記のようになります。

```mql
gBuffer[0] = 計算が必要（最新の価格）
gBuffer[1] = 計算済み
gBuffer[2] = 計算済み
gBuffer[3] = 計算済み
gBuffer[4] = 計算済み
```

`gBuffer` の要素が 1 つシフトしていることに注意してください。前回の `OnCalculate()` で計算した `gBuffer[0]`〜`gBuffer[3]` の値は、今回の呼び出し時点では `gBuffer[1]`〜`gBuffer[4]` の位置に保持されています。常にインデックス 0 が最新の要素になるということです。
通常の配列は勝手に値がシフトしていくことはありませんが、`gBuffer` は、`OnInit` 関数の中で `SetIndexBuffer` を使ってデータバッファとして設定したため、このような動作をすることになります。

`rates_total` にはローソク足の総数、`prev_calculated` には計算済みの数が入っているので、新たに計算すべきローソク足の本数は下記のように計算することができます。

```mql
// 直近から見て何本のローソク足に対して計算が必要か？
int changed = (prev_calculated == 0) ?
    rates_total : rates_total - prev_calculated + 1;
```

例えば、新しく計算しなければならない足の本数が 10 本ならば、`changed` が 10 になります。
最初はひとつも計算していない状態 (`prev_calculated == 0`) なので、ローソク足の総数 (`rates_total`) の分すべて計算する必要があります。
2 回目以降の計算では、すでに前回の計算結果が保持されている (`prev_calculated != 0`) なので、その分だけ計算すべきローソク足の数は減ります。
ここで、1 を足しているのは、最新のローソク足の分を常に再計算するためです。
なぜなら、`OnCalculate` 関数はティック（価格変化）ごとに呼び出されるので、多くの場合最新のローソク足の終値が更新されているからです。

