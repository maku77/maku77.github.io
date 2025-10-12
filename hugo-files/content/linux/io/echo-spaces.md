---
title: "Linuxメモ: echo で出力した文字列の中の連続するスペースが 1 つのスペースになってしまうのを防ぐ"
url: "p/25gqyai/"
date: "2018-10-11"
tags: ["Linux"]
aliases: /linux/io/echo-spaces.html
---

Linux の `echo` コマンドで文字列変数 `$str` の値を出力しようとして、下記のように実行すると、文字列に含まれている連続するスペースが 1 つのスペースにまとめられてしまいます。

{{< code lang="bash" title="sample.sh（間違った方法）" >}}
#!/bin/bash

str="AAA   BBB   CCC"
echo $str
{{< /code >}}

{{< code title="実行結果" >}}
AAA BBB CCC
{{< /code >}}

これは、変数展開が行われることによってダブルクォートが取り除かれ、下記のように `echo` コマンドに 3 つのパラメータが渡されたものとして扱われるからです。
パラメータの区切りとして使われるスペースの数は関係なく、各パラメータ (`AAA`、`BBB`、`CCC`) が 1 つのスペースで結合されて出力されます。

{{< code lang="console" >}}
$ echo AAA   BBB   CCC
AAA BBB CCC
{{< /code >}}

スペースを含む文字列を 1 つの文字列として `echo` コマンドに渡すには、次のようにダブルクォートで囲む必要があります。

{{< code lang="bash" title="sample.sh（正しい方法）" >}}
#!/bin/bash

str="AAA   BBB   CCC"
echo "$str"
{{< /code >}}

{{< code title="実行結果" >}}
AAA   BBB   CCC
{{< /code >}}

