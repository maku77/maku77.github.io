---
title: スプレッドを表示する
created: 2015-10-08
---

指定した通貨ペアのスプレッドを取得するには、`SymbolInfoInteger` 関数で `SYMBOL_SPREAD` を指定します。
現在のチャートの通貨ペアは、`Symbol` 関数を使えば取得できます。


```mql
void OnStart() {
    int spread = SymbolInfoInteger(Symbol(), SYMBOL_SPREAD);
    Alert(StringFormat("Spread of %s: %d", Symbol(), spread));
}
```

#### 実行結果
```
Spread of USDJPY: 4
```

