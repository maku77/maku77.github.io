---
title: 移動平均線のカスタムインジケータを作成する (2) 移動平均計算の最適化
created: 2015-02-01
layout: mt
---

移動平均線の計算処理において、重複した計算をしないように効率化したものです。

```mql
/**
 * Sum the elements of the array.
 */
double sumArray(const double& values[], int count) {
    double sum = 0.0;
    for (int i = 0; i < count; ++i) {
        sum += values[i];
    }
    return sum;
}

/**
 * Updates a moving average buffer.
 *
 * Parameters:
 *   buf -- the buffer where calculated MA is stored
 *   price -- the price used for calculating the MA
 *   price_count -- the number of prices
 *   changed -- price[0..changed-1] has been updated
 *   period -- MA's period
 */
void calcMovingAverage(double &buf[], const double &price[], int price_count,
        int changed, int period) {
    // Needs adequate price data for calculating MA.
    if (price_count < period) {
        ArrayFill(buf, 0, price_count, EMPTY_VALUE);
        return;
    }

    // Update the MA for the latest candle
    double sum = sumArray(price, period);
    buf[0] = sum / period;

    // Update each MA
    int count = MathMin(changed, price_count - period);
    for (int i = 0; i < count - 1; ++i) {
        sum = sum - price[i] + price[i + period];
        buf[i + 1] = sum / period;
    }
}
```

