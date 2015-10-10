---
title: 移動平均線のカスタムインジケータを作成する (3) 3 本の移動平均線を表示する
created: 2015-02-01
layout: mt
---

ひとつのカスタムインジケータで、複数の移動平均線を表示するサンプルです。
ここでは、3 本の移動平均線を表示します。
1 本のときと異なるのは、3 本分の指標バッファを用意しているところだけです。

#### MySma.mt4
```mql
// Global settings
#property description "My Moving Average"
#property strict
#property indicator_chart_window
#property indicator_buffers 3
#property indicator_plots   3

// Indicator settings
#property indicator_type1   DRAW_LINE
#property indicator_type2   DRAW_LINE
#property indicator_type3   DRAW_LINE
#property indicator_color1  clrPink
#property indicator_color2  clrYellow
#property indicator_color3  clrCyan
#property indicator_style1  STYLE_SOLID
#property indicator_style2  STYLE_SOLID
#property indicator_style3  STYLE_DOT
#property indicator_width1  1
#property indicator_width2  1
#property indicator_width3  1

// Input parameters
input int gPeriod1 = 5;  // MA Period 1
input int gPeriod2 = 25; // MA Period 2
input int gPeriod3 = 75; // MA Period 3

// Indicator buffers
double gMaBuffer1[];
double gMaBuffer2[];
double gMaBuffer3[];

int OnInit() {
    if (gPeriod1 <= 0 || gPeriod2 <= 0 || gPeriod3 <= 0) {
        Alert("Period must be larger than 0");
        return INIT_PARAMETERS_INCORRECT;
    }

    SetIndexBuffer(0, gMaBuffer1);
    SetIndexBuffer(1, gMaBuffer2);
    SetIndexBuffer(2, gMaBuffer3);
    SetIndexLabel(0, StringFormat("MA(%i)", gPeriod1));  // データウィンドウに出る名前
    SetIndexLabel(1, StringFormat("MA(%i)", gPeriod2));
    SetIndexLabel(2, StringFormat("MA(%i)", gPeriod3));
    IndicatorShortName(StringFormat("MA(%i, %i, %i)",
            gPeriod1, gPeriod2, gPeriod3));

    return INIT_SUCCEEDED;
}

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
    int changed = changedBars(rates_total, prev_calculated);
    calcMovingAverage(gMaBuffer1, close, rates_total, changed, gPeriod1);
    calcMovingAverage(gMaBuffer2, close, rates_total, changed, gPeriod2);
    calcMovingAverage(gMaBuffer3, close, rates_total, changed, gPeriod3);
    return rates_total;
}
```

