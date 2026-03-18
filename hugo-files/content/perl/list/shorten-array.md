---
title: "Perlメモ: 配列のサイズを切り詰める"
url: "p/53ohuhy/"
date: "2003-04-20"
tags: ["perl"]
aliases: ["/perl/list/shorten-array.html"]
---

{{< code lang="perl" title="例: 配列のサイズを 11 にする" >}}
$#ARRAY = 10;
{{< /code >}}

切り詰めた後のサイズをよりも、１つ小さい値を指定することに注意してください。
