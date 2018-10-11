---
title: "シェルスクリプト: echo で出力する文字の色を変える"
date: "2011-05-11"
---

次のようにすると、`echo` コマンドでテキストを出力するときの色を変更できます。

#### sample.sh

~~~ bash
#!/bin/bash

error() {
    echo -e "\033[31m$*\033[00m"
}

warn() {
    echo -e "\033[33m$*\033[00m"
}

info() {
    echo -e "\033[32m$*\033[00m"
}

error 'Error message'
warn 'Warning message'
info 'Information message'
~~~

#### 実行結果

![echo-color.png](echo-color.png)

