---
title: "undef と defined 演算子"
date: "2008-05-24"
---

`defined` 演算子を使うと、`undef` を `0` や `''`（空文字列） といった値と区別することができます。
変数を `undef` にするには次のようにします。

~~~ perl
undef $scalar;  # スカラー変数を undef にする
undef @array;   # 配列変数を undef にする
undef %hash;    # ハッシュ変数を undef にする
~~~

スカラー変数だけは次のように代入で `undef` にすることができます。

~~~ perl
$scalar = undef;
~~~

配列変数に `undef` を代入すると、`undef` 要素が 1 つある配列になってしまいます。

~~~ perl
@array = undef;  # @array = (undef); と同じ
~~~

