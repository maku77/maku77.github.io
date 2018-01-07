---
title: リダイレクトとパイプの役割
date: "2005-05-24"
---

デフォルトでは、標準出力 (stdout) の出力先はモニタで、標準入力の入力元はキーボードになっています。
リダイレクトやパイプは、これを変更する役割を持っています。

* `command > file` -- command の標準出力の内容をファイルへ出力する
* `command < file` -- ファイルの内容を command の標準入力へ入力する
* `command1 | command2` -- command1 の標準出力を command2 の標準入力へ繋ぐ

#### 例: list.txt の内容を sort コマンドの標準入力へ渡す

```
$ sort -r < list.txt      # リダイレクトを使う場合
$ cat list.txt | sort -r  # パイプを使う場合
```

