---
title: "カスタムインジケータを作成する"
date: "2015-02-01"
redirect: https://memoja.net/p/5q5gs5g
sitemap: false
---

ここでは、最初のステップとして、ローソク足の終値をラインで結ぶだけのカスタムインジケータを作成してみます。
Meta Editor 上で `Control + N` を押して、Custom Indicator を選択すると、カスタムインジケータのファイルを新規作成することができます。


#### MyIndicator.mt4

```mql
#property strict
#property indicator_chart_window
#property indicator_buffers 1
#property indicator_plots 1

// Default properties for Line 1
#property indicator_label1 "Line 1"
#property indicator_type1 DRAW_LINE
#property indicator_color1 clrRed
#property indicator_style1 STYLE_SOLID
#property indicator_width1 1

// Buffer for indicator line
double gBuffer[];

int OnInit() {
    SetIndexBuffer(0, gBuffer, INDICATOR_DATA);
    return INIT_SUCCEEDED;
}

int OnCalculate(
        const int rates_total,      // ローソク足の数
        const int prev_calculated,  // 前回の OnCalculate() の戻り値
        const datetime &time[],     // ローソク足ごとの時刻 [0..rates_total-1]
        const double &open[],       // ローソク足ごとの始値 [0..rates_total-1]
        const double &high[],       // ローソク足ごとの高値 [0..rates_total-1]
        const double &low[],        // ローソク足ごとの安値 [0..rates_total-1]
        const double &close[],      // ローソク足ごとの終値 [0..rates_total-1]
        const long &tick_volume[],
        const long &volume[],
        const int &spread[]) {
    // 終値をそのままインデックス・バッファに詰める
    for (int i = 0; i < rates_total; ++i) {
        gBuffer[i] = close[i];
    }

    // 最新の終値をチャートの左上に表示
    Comment(time[0] + " -- " + close[0]);

    // 次の OnCalculate() の prev_calculated にこの値が入る
    return rates_total;
}
```

各パートの説明
====

```mql
#property indicator_chart_window  // チャート上に表示する
#property indicator_buffers 1     // 使用する時系列バッファの数（表示用 ＋ 計算用）
#property indicator_plots 1       // 使用する時系列バッファの数（表示用）
```

このプロパティでは、カスタムインジケータをローソクチャート上に重ねて表示すること、ラインを 1 本だけ使用することを宣言しています。
仮に、別ウィンドウでインジケータを表示したい場合は、`indicator_chart_window` の代わりに `indicator_separate_window` を指定します。

```mql
#property indicator_label1 "Line 1"
#property indicator_type1 DRAW_LINE     // 表示方法: ライン
#property indicator_color1 clrRed       // 色: 赤
#property indicator_style1 STYLE_SOLID  // ラインの種類: 実線
#property indicator_width1 1            // 太さ: 1
```

上記のプロパティでは、表示用の 1 本目のラインの設定を行っています。ここではラインが 1 本だけですが、複数のラインを表示するカスタムインジケータを作る場合は、各プロパティ名のサフィックスを 2, 3, 4 と増やしていきます。

```mql
// Buffer for indicator line
double gBuffer[];

int OnInit() {
    SetIndexBuffer(0, gBuffer, INDICATOR_DATA);
    return INIT_SUCCEEDED;
}
```

最初に一度だけ呼ばれる `OnInit()` 関数の中では、配列 `gBuffer` を、画面表示用のバッファ (`INDICATOR_DATA`) として割り当てています。このバッファに値を設定することで、画面上にインジケータが表示されることになります。仮に、計算用としてだけ使うバッファを追加で割り当てたい場合は、`INDICATOR_CALCULATIONS` というタイプを指定して割り当てます（この場合、`indicator_buffers` プロパティの値を 2 に増やします）。

```mql
int OnCalculate(
        const int rates_total,
        const int prev_calculated,
        const datetime &time[],
        const double &open[],
        const double &high[],
        const double &low[],
        const double &close[],
        const long &tick_volume[],
        const long &volume[],
        const int &spread[]) {
    // 終値をそのままインデックス・バッファに詰める
    for (int i = 0; i < rates_total; ++i) {
        gBuffer[i] = close[i];
    }

    // 最新の終値をチャートの左上に表示
    Comment(time[0] + " -- " + close[0]);

    // 次の OnCalculate() の prev_calculated にこの値が入る
    return rates_total;
}
```

計算のメイン部分となるのが `OnCalculate()` 関数です。`OnCalculate()` 関数は、tick（価格の更新）が発生するごとに呼び出されます。
チャート上のローソク足の数や、始値、終値などの情報がパラメータで渡されます。

#### パラメータの説明

* `int rates_total`        -- ローソク足の数
* `const datetime &time[]` -- ローソク足が確定した時刻（time[0] が最新のローソク足）
* `const double &open[]`   -- ローソク足ごとの始値（open[0] が最新のローソク足）
* `const double &high[]`   -- ローソク足ごとの高値（high[0] が最新のローソク足）
* `const double &low[]`    -- ローソク足ごとの安値（low[0] が最新のローソク足）
* `const double &close[]`  -- ローソク足ごとの終値（close[0] が最新のローソク足）

価格などを示すデータは、配列の形で渡されます。配列のインデックス 0 が最新のローソク足の価格を表しています。ローソク足の本数は `rates_total` で渡されるので、配列のインデックスとしては、`0`（最新の値）〜 `rates_total-1`（一番古い値）の範囲で指定することができます。
例えば以下のような感じです。

* `close[rates_total - 1]` -- チャート上の左端のローソク足の終値
* `close[0]`               -- チャート上の右端のローソク足の終値

以下のようにすべてのローソク足の終値を、インジケータ用のバッファにそのまま設定してやることで、終値を結ぶインジケータを表示しています。

```mql
for (int i = 0; i < rates_total; ++i) {
    gBuffer[i] = close[i];
}
```

実は、`OnCalculate()` が呼び出されるたびに `gBuffer[]` のすべての値を再設定する必要はありません。
前回の呼び出しでセットした値は、`gBuffer[]` に保持されているためです。
これを利用した最適化の話は次の記事へ続く。。。

