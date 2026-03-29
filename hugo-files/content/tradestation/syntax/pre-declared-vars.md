---
title: "TradeStationメモ: 定義済み変数を使用する (Value0, Condition0)"
url: "p/ggopk47/"
date: "2017-10-08"
tags: ["tradestation"]
description: "ユーザー定義の変数は Vars による初期化が必要ですが、初期化なしで使用できる定義済み変数も用意されています。"
aliases: /tradestation/syntax/pre-declared-vars.html
---

2 種類の定義済み変数
----

通常の変数は使用前に `Variables` や `Vars` キーワードで[変数定義が必要](/p/bdx2n5k/)です。
EasyLanguage には、そのようなユーザー定義変数に加えて、**定義済み変数 (Pre-Declared Variables)** が用意されています。
定義済み変数には、数値型と真偽値型 (true or false) のものが 100 個ずつ用意されています。

- `Value0` ～ `Value99`: 数値型の定義済み変数。初期値は 0。
- `Condition0` ～ `Condition99`: 真偽値型の定義済み変数。初期値は false。

下記のサンプルコードでは、`Value1` と `Condition1` を使用しています。

```
Value1 = Average(Price, Length);
Condition1 = Close crosses above Value1;
```

一般的に `Value1` や `Condition1` といった名前は意図が伝わりにくいため、定義済み変数の使用は開発段階での一時的な用途に限るのがよいでしょう。
最終的には `Vars:` キーワードを使用して、適切な名前の変数を明示的に定義することをお勧めします。

ただし、`for` ループのカウンタ変数など、用途が明確な場合は定義済み変数を使っても問題ありません。

```
for Value1 = 0 to 5 begin
    //...
end;
```

定義済み変数の値は次のバーへ引き継がれる
----

ユーザー定義の変数と同様に、定義済み変数の値も次のバーの計算処理に引き継がれます。
例えば、下記のように `Value1` の値を毎バーの計算で加算していくと、どんどん数値が大きくなっていきます。

```
Value1 += 1;
Plot1(Value1);
```

`Value1[1]` とすると 1 本前の足の `Value1` の値を参照できるのも、ユーザー定義変数と同様です。
