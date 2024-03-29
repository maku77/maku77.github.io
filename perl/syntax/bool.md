---
title: "Perl のブール値（真偽値）"
date: "2008-03-13"
---

Perl 言語において、以下のスカラー値は偽 (`false`) と判断され、それ以外のスカラー値は真 (`true`) と判断されます。

- `undef` （未定義値）
- `0`
- `''` （空文字列）
- `'0'` （0 を表す文字列）

スカラー変数に入っている値が、空文字列ではなく、`undef` であることを調べるには、`defined` 関数を使用します。
特に、空文字列と `undef` を異なる意味で返す可能性のある関数を使用する場合に必要になります。

未定義値 `undef` は、`undef` 演算子を使って明示的に取得することもできます。

~~~ perl
$val = undef;
~~~

数値や文字列比較後の結果は、ブール値としてスカラー変数に格納できます。

~~~ perl
$is_bigger_than_ten = $val > 10;
if ($is_bigger_than_ten) {
    ...
}
~~~

