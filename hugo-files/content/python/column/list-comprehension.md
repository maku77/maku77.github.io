---
title: "Pythonメモ: Python のリスト内包表記に慣れる"
url: "p/6ppwdhi/"
date: "2013-05-12"
tags: ["python"]
aliases: ["/python/column/list-comprehension.html"]
---

Python の **「リスト内包表記: List Comprehensions」**  で、1〜9 の範囲の偶数から構成されるリストを作成するには下記のように記述します。

{{< code lang="python" title="Python のリスト内包表記" >}}
arr = [x for x in range(1, 10) if x % 2 == 0]
{{< /code >}}

数学の世界にも、集合を表すための **「内包的記法」** というものがあります。

{{< code title="数学の内包的記法" >}}
自然数全体の集合 = {x | x∈N}
非負の偶数全体の集合 = {x | x∈N & 2|x}
{{< /code >}}

こう見てみると、Python のリスト内包表記と、数学の内包的記法は、ほぼ同じ文法で記述されていることが分かります。
リスト内包表記を初めて見たときは面食らうかもしれませんが、数学的な思考で記述できるんだと考えれば、少しは親しみが出てくるかもしれません。

