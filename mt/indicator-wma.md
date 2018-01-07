---
title: 加重移動平均線のカスタムインジケータを作成する
date: "2015-06-10"
---

WMA: Weighted Moving Average（加重移動平均線）は、直前の価格ほど重みをおいた移動平均線です。考え方は EMA（指数平滑移動平均線）に近いのですが、WMA の方が計算はシンプルです。
例えば、5 WMA は以下のように求められます。

1. 下記の値を合計する
  * 1 つ前の値 x 5
  * 2 つ前の値 x 4
  * 3 つ前の値 x 3
  * 4 つ前の値 x 2
  * 5 つ前の値 x 1
2. これを 15 (1+2+3+4+5) で割る

### MyWma.mq4

```mql
#property copyright "Maku"
#property link      "http://www.mql5.com"
#property version   "1.00"
#property strict

// Chart settings
#property indicator_chart_window
#property indicator_buffers 1
#property indicator_plots 1

// Each indicator settings
#property indicator_type1 DRAW_LINE
#property indicator_style1 STYLE_SOLID
#property indicator_width1 1
#property indicator_color1 clrPink

// Input parameters
input int gPeriod = 25;  // Period of WMA

// Indicator buffers
double gWmaBuffer[];

/**
 * [Utility]
 * How many candles should be re-calculated.
 */
int changedBars(int rates_total, int prev_calculated) {
    if (prev_calculated == 0) {
        return rates_total;
    }
    // The latest bar should be updated, so add 1.
    return rates_total - prev_calculated + 1;
}

int OnInit() {
    if (gPeriod <= 0) {
        Alert("Period must be larger than 0");
        return INIT_PARAMETERS_INCORRECT;
    }

    SetIndexBuffer(0, gWmaBuffer);
    SetIndexLabel(0, StringFormat("WMA(%i)", gPeriod));
    IndicatorShortName(StringFormat("WMA(%i)", gPeriod));

    return INIT_SUCCEEDED;
}

/**
 * Calculates WMA. The size of vals must be equal or larger than index + count.
 */
double calcWma(const double& vals[], int index, int count) {
    double sum = 0;
    for (int i = 0; i < count; ++i) {
        sum += vals[i + index] * (count - i);
    }
    return (sum * 2) / (count * (count + 1));
}

int updateWma(const double& prices[], int rates_total, int prev_calculated) {
    // Is there enough amount of price data?
    if (rates_total < gPeriod) {
        return 0;
    }

    int changed = changedBars(rates_total, prev_calculated);
    int count = MathMin(changed, rates_total - gPeriod + 1);
    for (int i = 0; i < count; ++i) {
        gWmaBuffer[i] = calcWma(prices, i, gPeriod);
    }

    // Print("Latest WMA = ", gWmaBuffer[0]);
    return rates_total;
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
    // PlaySound("tick.wav");
    return updateWma(close, rates_total, prev_calculated);
}
```
