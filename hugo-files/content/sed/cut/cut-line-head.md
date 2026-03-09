---
title: "sed/awkメモ: cut コマンドで行頭の数文字をカットする"
url: "/p/vbz86im/"
date: "2011-05-11"
tags: ["sed/awk"]
aliases: [/sed/cut/cut-line-head.html]
---

cut コマンドの `-b` オプションを指定すると、文字数を指定してテキストの切り出しを行うことができます。


{{< code lang="console" title="例: ３文字目を取り出す" >}}
$ echo "1234567890" | cut -b 3
3
{{< /code >}}

{{< code lang="console" title="例: ３～８文字目を取り出す" >}}
$ echo "1234567890" | cut -b 3-8
345678
{{< /code >}}

{{< code lang="console" title="例: ３文字目以降を取り出す" >}}
$ echo "1234567890" | cut -b 3-
34567890
{{< /code >}}

{{< code lang="console" title="例: 先頭から３文字目までを取り出す" >}}
$ echo "1234567890" | cut -b -3
123
{{< /code >}}

