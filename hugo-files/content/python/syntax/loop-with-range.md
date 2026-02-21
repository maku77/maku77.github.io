---
title: "Pythonメモ: for ~ in range による数値のループ処理いろいろ"
url: "p/eg6sr6t/"
date: "2007-10-23"
tags: ["python"]
aliases: /python/loop-with-range.html
---

指定した回数だけループ
----

{{< code lang="python" title="指定した回数だけループ" >}}
>>> for x in range(5):
...     print(x)
...
0
1
2
3
4
{{< /code >}}

範囲を指定してループ
----

{{< code lang="python" title="範囲を指定してループ" >}}
>>> for x in range(10, 15):
...     print(x)
...
10
11
12
13
14
{{< /code >}}

範囲とステップを指定してループ
----

{{< code lang="python" title="範囲とステップを指定してループ" >}}
>>> for x in range(10, 20, 2):
...     print(x)
...
10
12
14
16
18
{{< /code >}}

ステップを負の値にすれば、数値を減らしていくループもできます。

{{< code lang="python" title="ステップに負の値を指定してループ" >}}
>>> for x in range(10, 5, -1):
...     print(x)
...
10
9
8
7
6
{{< /code >}}
