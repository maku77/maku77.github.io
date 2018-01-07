---
title: 現在のチャートの通貨ペア（シンボル）を取得する
date: "2015-10-25"
---

現在のチャートで表示している通貨ペア（シンボル）の名前を取得するには下記のようにします。

```mql
string symbol = Symbol();
```

あるいは、組み込みの **_Symbol** 変数を参照することもできます。

```mql
string symbol = _Symbol;
```

参考
====
* [MQL5 - Symbol()](https://www.mql5.com/en/docs/check/symbol)
* [MQL5 - _Symbol](https://www.mql5.com/en/docs/predefined/_symbol)

