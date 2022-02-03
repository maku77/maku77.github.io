---
title: "チャート上に矩形を表示する"
date: "2016-03-21"
redirect: https://memoja.net/p/qju4dmu
sitemap: false
---

矩形オブジェクトについて
----

矩形オブジェクト (`OBJ_RECTANGLE`) および、矩形ラベルオブジェクト (`OBJ_RECTANGLE_LABEL`) は、MetaTrader のチャート上に表示できる描画オブジェクトです。
表示位置の指定方法によって、下記のように使い分けます。

- 矩形オブジェクト (`OBJ_RECTANGLE`)
  - 矩形の表示位置が、チャートの時間軸 (time) および、価格 (price) に基づいて指定されます。チャートの時間軸を移動させると、それに合わせて矩形の表示位置も移動します。
- 矩形ラベルオブジェクト (`OBJ_RECTANGLE_LABEL`)
  - 矩形の表示位置は、ウィンドウ上の左上を起点として XY 座標が指定されるため（`CORNER_LEFT_UPPER` を指定した場合）、チャートを時間軸方向に移動させても、ラベルの表示位置は固定されて動きません。


矩形オブジェクト（矩形ラベルオブジェクト）の作成
----

矩形オブジェクト（矩形ラベルオブエジェクト）を作成するには、組み込み関数である [ObjectCreate](https://www.mql5.com/ja/docs/objects/objectcreate) の `type` パラメータに `OBJ_RECTANGLE`、あるいは `OBJ_RECTANGLE_LABEL` を指定します。

```mql
bool ObjectCreate(
   long         chart_id,  // チャート識別子（0 はカレントチャート）
   string       name,      // オブジェクト名 (ラベルの識別子）
   ENUM_OBJECT  type,      // オブジェクトの型（OBJ_LABEL を指定）
   sub_window   nwin,      // ウィンドウインデックス（0 はメインウィンドウ）
   datetime     time1,     // 1 番目のアンカーポイントの時間（ラベルの場合は使用しない）
   double       price1,    // 1 番目のアンカーポイントの価格（ラベルの場合は使用しない）
)
```

矩形オブジェクトは、チャート上に複数追加することができますが、オブジェクトは、チャート ID（`chart_id` パラメータ）と、オブジェクト名 (`name`) で識別することになります。
現在アクティブになっているチャートにオブジェクトを追加するのであれば、`chart_id` は 0 に設定します。
オブジェクト名はチャートの中で一意になるような名前を付けます。

例えば、カレントチャート (`chart_id=0`) の、メインウィンドウ (`nwin=1`) に表示するための矩形ラベルオブジェクトを作成するには下記のようにします。

```mql
if (!::ObjectCreate(0, "Rect1", OBJ_RECTANGLE_LABEL, 0, 0, 0)) {
    // 矩形ラベルオブジェクトの作成に失敗
}
```

すでに同じ名前 (`name`) のオブジェクトが、そのチャートに追加されている場合は、`ObjectCreate` は失敗します。



矩形オブジェクト（矩形ラベルオブジェクト）のプロパティ設定
----

矩形の表示方法を指定するには、`ObjectSetXxx` 系の関数を使用します。
どのオブジェクトに対する設定なのかを示すために、第一引数と、第二引数で、それぞれチャート ID とオブジェクト名を指定する必要があります。

```mql
// 表示位置／サイズの設定
::ObjectSetInteger(chart, name, OBJPROP_CORNER, CORNER_LEFT_UPPER);
::ObjectSetInteger(chart, name, OBJPROP_XDISTANCE, 30);  // X 座標
::ObjectSetInteger(chart, name, OBJPROP_YDISTANCE, 30);  // Y 座標
::ObjectSetInteger(chart, name, OBJPROP_XSIZE, 100);     // 幅
::ObjectSetInteger(chart, name, OBJPROP_YSIZE, 50);      // 高さ

// 背景色の設定
::ObjectSetInteger(chart, name, OBJPROP_BGCOLOR, clrBlue);  // 背景色
::ObjectSetInteger(chart, name, OBJPROP_BACK, true);  // ローソク足などの後ろ側に表示

// ボーダーの設定
::ObjectSetInteger(chart, name, OBJPROP_BORDER_TYPE, BORDER_FLAT);  // ボーダーのタイプ
::ObjectSetInteger(chart, name, OBJPROP_COLOR, clrYellow);  // flat border の色
::ObjectSetInteger(chart, name, OBJPROP_STYLE, STYLE_DOT);  // 線の種類
::ObjectSetInteger(chart, name, OBJPROP_WIDTH, 1);          // 線の太さ

// ダブルクリックで選択してドラッグ＆ドロップで移動できるか
::ObjectSetInteger(chart, name, OBJPROP_SELECTABLE, false);
::ObjectSetInteger(chart, name, OBJPROP_SELECTED, false);
```

矩形オブジェクトにおいて、`OBJPROP_BACK` の設定は重要です。
この値を `true` にしておかないと、ローソク足などの全面に矩形が描画されて、ローソク足が隠れてしまいます。


サンプルコード
----

下記のサンプルコードは、画面左上に青色の矩形を表示するだけの簡単なインジケータの例です。
インジケータをチャートにアタッチすると矩形が表示され、インジケータをデタッチすると矩形が削除されます。

```mql
#property copyright "maku77"
#property link      "https://maku77.github.io/"
#property version   "1.00"
#property strict
#property indicator_chart_window

#include <stdlib.mqh>  // ErrorDescription

static const string RECT_NAME = "Rect1";  // 描画オブジェクトの ID

bool AddRect() {
    const int chart = 0;            // 0 means the current chart
    const int subWindow = 0;        // 0 means the main window
    const string name = RECT_NAME;  // Object ID to be added

    // Create a new rectangle
    if (!::ObjectCreate(chart, name, OBJ_RECTANGLE_LABEL, subWindow, 0, 0)) {
        ::Alert("Failed to create a rect: ", ErrorDescription(::GetLastError()));
        return false;
    }

    // Position
    ::ObjectSetInteger(chart, name, OBJPROP_CORNER, CORNER_LEFT_UPPER);
    ::ObjectSetInteger(chart, name, OBJPROP_XDISTANCE, 30);
    ::ObjectSetInteger(chart, name, OBJPROP_YDISTANCE, 30);
    ::ObjectSetInteger(chart, name, OBJPROP_XSIZE, 100);
    ::ObjectSetInteger(chart, name, OBJPROP_YSIZE, 50);

    // Background
    ::ObjectSetInteger(chart, name, OBJPROP_BGCOLOR, clrBlue);
    ::ObjectSetInteger(chart, name, OBJPROP_BACK, true);

    // Border
    ::ObjectSetInteger(chart, name, OBJPROP_BORDER_TYPE, BORDER_FLAT);
    ::ObjectSetInteger(chart, name, OBJPROP_COLOR, clrYellow);
    ::ObjectSetInteger(chart, name, OBJPROP_STYLE, STYLE_DOT);
    ::ObjectSetInteger(chart, name, OBJPROP_WIDTH, 1);

    // Drag and drop
    ::ObjectSetInteger(chart, name, OBJPROP_SELECTABLE, false);
    ::ObjectSetInteger(chart, name, OBJPROP_SELECTED, false);

    return true;
}

void DeleteRect() {
    const int chart = 0;  // 0 means the current chart
    ObjectDelete(chart, RECT_NAME);
}

//--- event handlers ---

int OnInit() {
    return AddRect() ? INIT_SUCCEEDED : INIT_FAILED;
}

void OnDeinit(const int reason) {
    DeleteRect();
}

int OnCalculate(const int rates_total, const int prev_calculated,
                const int begin, const double& price[]) {
    // Nothing to do
    return rates_total;
}
```


オブジェクト指向バージョン (CChartObjectRectLabel) を使用する方法
----

MQL5 で定義された [CChartObjectRectLabel](https://www.mql5.com/ja/docs/standardlibrary/chart_object_classes/obj_controls/cchartobjectrectlabel) クラスは、オブジェクト指向な矩形ラベルオブジェクトを提供します。

`CChartObjectRectLabel::Create` メソッドでは、XY 座標や矩形のサイズを指定できるようになっています。

#### CChartObjectRectLabel::Create

```
bool Create(long chart_id, const string name, const int window, const int X, const int Y, const int sizeX, const int sizeY);
```

`CChartObjectRectLabel` クラスは `CChartObject` クラスを継承して作成されており、`CChartObject` クラスのデストラクタでは、チャートから描画オブジェクトを削除するコードが記述されています。
つまり、このデストラクタをうまく利用することで、プログラムの終了時（例えばインジケータのデタッチ時）に自動的に描画オブジェクトを削除することができます。

下記のインジケータのサンプルコードで、描画オブジェクトを明示的に削除していないことに注目してください。


```mql
#include <stdlib.mqh>  // ErrorDescription
#include <ChartObjects/ChartObjectsTxtControls.mqh>  // CChartObjectRectLabel

// ここでは描画オブジェクトをグローバルに定義（終了時の自動削除のため）
CChartObjectRectLabel gRect;

bool AddRect() {
    const int chart = 0;      // 0 means the current chart
    const int subWindow = 0;  // 0 means the main window

    // Create a new rectangle
    if (!gRect.Create(chart, "Rect1", subWindow, 30, 30, 100, 50)) {
        ::Alert("Failed to create a rect: ", ErrorDescription(::GetLastError()));
        return false;
    }

    // Position
    gRect.Corner(CORNER_LEFT_UPPER);
    // gRect.X_Distance(30);
    // gRect.Y_Distance(30);
    // gRect.X_Size(100);
    // gRect.Y_Size(50);

    // Background
    gRect.BackColor(clrBlue);
    gRect.Background(true);

    // Border
    gRect.BorderType(BORDER_FLAT);
    gRect.Color(clrYellow); // Flat border color
    gRect.Style(STYLE_DOT);
    gRect.Width(1);

    // Drag and drop
    gRect.Selectable(false);
    gRect.Selected(false);

    return true;
}

//--- event handlers ---

int OnInit() {
    return AddRect() ? INIT_SUCCEEDED : INIT_FAILED;
}

void OnDeinit(const int reason) {
    // CChartObject will be automatically deleted by its destructor
}

int OnCalculate(const int rates_total, const int prev_calculated,
                const int begin, const double& price[]) {
    // Nothing to do
    return rates_total;
}
```

