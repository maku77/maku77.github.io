---
title: "Linux シェルスクリプト: echo で出力する文字の色を変える"
url: "p/fufwdub/"
date: "2011-05-11"
tags: ["Linux"]
aliases: /linux/io/echo-color.html
---

Linux の `echo` コマンドで特殊なエスケープシーケンスを出力すると、テキストの文字を変更することができます。
次の Bash スクリプトでは、色を変更しつつ `echo` 出力する、`error` / `warn` / `info` 関数を定義しています。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

error() {
    echo -e "\033[31m$*\033[00m" >&2
}

warn() {
    echo -e "\033[33m$*\033[00m" >&2
}

info() {
    echo -e "\033[32m$*\033[00m" >&2
}

error 'Error message'
warn 'Warning message'
info 'Information message'
{{< /code >}}

このシェルスクリプトを実行すると、次のように色付きのメッセージが表示されます。

{{< image src="img-001.png" title="echo コマンドの出力色を変更" >}}

ちなみに、`echo` コマンドの末尾の `>&2` は、標準エラー出力へ出力することを示しています。

- 参考: [echo の結果を標準エラー出力 (stderr) に出力する (`1>&2`)](/p/q2k3j2h/)

