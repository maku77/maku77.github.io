---
title: カスタムインジケータの OnCalculate 関数を最適化する
created: 2015-02-01
---

カスタムインジケータの計算部分を担う `OnCalculate` は、価格の変化 (Tick) ごとに呼び出されますが、`prev_calculated` パラメータの値をうまく使うことで、すでに計算済みの値を再計算しないでも済むように処理を最適化ことができます。
下記の `OnCalculate()` は、インジケータバッファ (`gBuffer`) に、各ローソク足の終値をセットしています。

```mql
// double gBuffer[];  // Buffer for indicator line

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
    int count = rates_total - prev_calculated;
    if (count == 0) {
        count = 1;
    }

    // インジケータの計算（新しく計算した部分だけ更新すれば OK）
    for (int i = 0; i < count; ++i) {
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
int count = rates_total - prev_calculated;
if (count == 0) {
    count = 1;
}
```

例えば、新しく計算しなければならない足の本数が 10 本ならば、`count` が 10 になります。
ただし、通常 2 回目以降の `OnCalculate` 関数の呼び出しでは、ほとんどの場合 `count` は 0 になることに注意してください。
なぜなら、`OnCalculate` 関数はティック（価格変化）ごとに呼び出されるので、多くの場合ローソク足の本数は変化せず、最新のローソク足の終値だけが更新されるからです。この場合、全ローソク足に対するインジケータの計算はすでに行われている (`count == 0`) ですが、最新のローソク足に対しては価格が更新されているので、再計算を行うために `count = 1` に訂正しています。

