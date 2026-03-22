---
title: "PHPメモ: 配列にある値が含まれているか調べる (in_array)"
url: "/p/ut57dzk/"
date: "2012-01-25"
tags: ["php"]
aliases: [/php/array/in-array.html]
---

`in_array` 関数を使用すると、指定した値が配列内に含まれているかを調べることができます。

{{< code lang="php" title="例: 配列 $arr に 100 という値が含まれているか調べる" >}}
if (in_array(100, $arr)) {
    ...
}
{{< /code >}}

