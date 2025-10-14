---
title: "Linuxメモ: Bash の構文: 関数のデフォルト引数を定義する"
url: "p/7ovg5nr/"
date: "2012-12-09"
tags: ["linux"]
aliases: /linux/syntax/default-params.html
---

以下の関数では、パラメータを何も指定しないで呼び出すと、`par1` ローカル変数に `ABC` が入ります。
パラメータを指定すると、そちらが使用されます。

{{< code lang="bash" title="param-test.sh" >}}
function param-test() {
  local par1=${1:-ABC};
  echo "param = $par1"
}
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ . param-test.sh
$ param-test
param = ABC

$ param-test 10000
param = 10000
{{< /code >}}
