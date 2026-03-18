---
title: "Perlメモ: 条件に一致するリスト要素の数を調べる"
url: "p/x6agdrj/"
date: "2008-07-09"
tags: ["perl"]
aliases: ["/perl/list/grep-count.html"]
---

grep の結果をスカラーコンテキストで評価すると、条件に一致したリスト要素の数を調べることができます。

{{< code lang="perl" title="真の値を持つ要素数をカウント" >}}
$num_of_true = grep $_, @arr;
{{< /code >}}

{{< code lang="perl" title="コメント行の行数をカウント（コメント行は # で始まるとする）" >}}
$num_of_comments = grep /^#/, @lines;
{{< /code >}}
