---
title: "MetaTrader (MQL) における色の表現方法"
date: "2015-10-10"
redirect: https://memoja.net/p/rn6kw8j
sitemap: false
---

MQL 内で色情報を表現する場合、`color` 型の変数を使います。

* [MQL5 - Color Type](https://www.mql5.com/en/docs/basis/types/integer/color)

`color` 変数は 4 バイトの数値で、後ろ 3 バイトでそれぞれ RGB の色情報 (0~255) を保持しています。
コード内で色を表すときは、下記のように、`C'r,g,b'` 形式リテラル、定義済みのカラー名、あるいは 4 バイト数値で記述できます。

```mql
// Literals
C'128,128,128'     // Gray
C'0x00,0x00,0xFF'  // Blue

// Color names
clrRed             // Red
clrYellow          // Yellow
clrBlack           // Black

// Integral representations
0xFFFFFF           // White
16777215           // White
0x008000           // Green
32768              // Green
```

`clrRed` のような定義済みのカラー値として何が用意されているかは、下記を参照してください。

* [MQL5 - Web Colors](https://www.mql5.com/en/docs/constants/objectconstants/webcolors)


