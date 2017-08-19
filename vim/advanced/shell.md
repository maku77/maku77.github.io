---
title: 一時的にシェル（ターミナルやコマンドプロンプト）を起動する
created: 2008-01-15
---

gvim で `:shell` コマンド（省略して `:sh` でも可）を実行すると、シェルを起動することができます。例えば、Windows の場合はコマンドプロンプトが起動します。

~~~
:shell
~~~

カレントディレクトリを基準にしてシェルを起動するので、一時的にシェル上でコマンドを使用してテキスト処理などを行いたい場合に便利です。
シェルを `exit` で終了すると、Vim エディタに戻ってきます。

１つのコマンドだけをシェル上で実行したい場合は、下記のように実行することができます。

~~~
:!コマンド
~~~
