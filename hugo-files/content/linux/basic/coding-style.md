---
title: "Linuxメモ: シェルスクリプトのコーディングスタイル"
url: "p/oukq4dh/"
date: "2018-09-25"
tags: ["linux"]
aliases: /linux/basic/coding-style.html
---

Bash のシェルスクリプトを記述するときに最低限揃えておきたいコーディングスタイルです。
[Google のコーディングスタイル](http://google.github.io/styleguide/shell.xml)を参考にしています。

- 基本的に bash を使用することとし、1 行目の Shebang として `#!/bin/bash` と記述する
- インデント: 半角スペース **2** 文字（タブ文字は使用しない）
- `if` と同じ行に `then` を記述する
- `for` や `while` と同じ行に `do` を記述する
- 1 行あたりの文字数は **80** 文字まで
- 関数名や変数名はすべて小文字（例: `my_func`）
- 外部コマンドを実行するときはバッククォート (`\`) を使用せず、**`$(func arg1 arg2)`** という形式を使用する
- シェル変数は小文字 (**`$src_dir`**)、環境変数は大文字 (**`$FOO_BAR`**)

