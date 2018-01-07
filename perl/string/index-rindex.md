---
title: "部分文字列の位置を検索する (index, rindex)"
date: "2008-04-13"
---

`index` を使用すると、第２引数で指定した文字列が、第１引数で指定した文字列中のどの位置に登場するかを調べることができます。

~~~ perl
my $where = index('aaabbbccc', 'bbb');  # ==> 3
~~~

部分文字列が見つからないときは、`index` は -1 を返します。

後ろから探したい場合は、`index` の代わりに `rindex` を使用してください。

~~~ perl
# 一番後ろのスラッシュの位置を探す
my $where = rindex('/home/jack', '/');  # ==> 5
~~~

