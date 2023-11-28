---
title: "Python で dictionary の要素をソートして出力する (sorted)"
url: "p/qqkggoz/"
date: "2013-04-21"
lastmod: "2023-11-23"
tags: ["Python"]
changes:
  - 2023-11-23: サンプルコードを追加
aliases: /python/dictionary/sort.html
---

キーでソートして出力 {#sort-by-key}
----

Python の dictionary の要素をキー順に取り出すには、__`dict.keys()`__ メソッドで dictionary からキーの一覧を取り出し、それを __`sorted()`__ 関数で昇順ソートしてループ処理します。
つまり、キーのリストをソートしているだけです。

{{< code lang="python" title="キー名で昇順ソート" >}}
>>> d = {'BBB': 200, 'AAA': 300, 'CCC': 100}
>>> for key in sorted(d.keys()):
...     print(key, d[key])

AAA 300
BBB 200
CCC 100
{{< /code >}}

キー名で降順ソートしたいときは、`sorted()` 関数に __`reverse=True`__ パラメーターを指定します。

{{< code lang="python" title="キー名で降順ソート" >}}
>>> d = {'BBB': 200, 'AAA': 300, 'CCC': 100}
>>> for key in sorted(d.keys(), reverse=True):
...     print(key, d[key])

CCC 100
BBB 200
AAA 300
{{< /code >}}


値でソートして出力 {#sort-by-value}
----

`sorted()` 関数の __`key`__ パラメーターをうまく使うと、dictionary の「値」によりソートされたキーを列挙できます。
次の例では、値の昇順 (100, 200, 300) でループ処理しています。

{{< code lang="python" title="値で昇順ソート" >}}
>>> d = {'BBB': 200, 'AAA': 300, 'CCC': 100}
>>> for key in sorted(d, key=lambda x:d[x]):
...     print(key, d[key])

CCC 100
BBB 200
AAA 300
{{< /code >}}

降順ソート (300, 200, 100) したいときは、`sorted()` 関数に __`reverse=True`__ パラメーターを指定します。

{{< code lang="python" title="値で昇順ソート" >}}
>>> d = {'BBB': 200, 'AAA': 300, 'CCC': 100}
>>> for key in sorted(d, reverse=True, key=lambda x:d[x]):
...     print(key, d[key])

AAA 300
BBB 200
CCC 100
{{< /code >}}


参考
----

- [リストを昇順ソート／降順ソートする (`list.sort`, `sorted`)](/p/cqtwqgx/)
- [dictionary の内部的な要素順序を変更する (`dict`, `OrderedDictionary`)](/p/vexfweu/)
  - Python 3.7 以降は、dictionary への要素の追加順序が内部的に保持されるようになっています。
