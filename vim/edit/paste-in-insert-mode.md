---
title: 挿入モード、コマンドモードでの貼り付け
created: 2013-05-26
---

ノーマルモードでのテキストのコピペは、`y` コマンドでコピー (yank) 、`p` コマンドで貼り付け (put) することができます。
一方、挿入モードやコマンドモードで貼り付けを行うには、下記のように入力する必要があります（挿入モードで `y` と入力すると、そのまま `y` というテキストが入力されてしまいます）。

~~~
Ctrl-R 0
~~~

これは、具体的には **「レジスタ 0」** に格納されているテキスト（直近でヤンクされたテキスト）を選択して貼り付けるコマンドです。
レジスタ `0` に格納されるテキストは、あくまで Vim 内部でヤンクされたテキストです。
外部のアプリケーションでコピーしたテキストを貼り付けたい場合は、レジスタ `0` の代わりに、レジスタ `*` を指定してください。

~~~
Ctrl-R *
~~~

レジスタに保存されているテキストの一覧は、以下のように確認することができます。

~~~
:reg
~~~


参考
----

* [切り取り＆貼り付け操作、レジスタの扱いについて理解する](register.html)
* [最後にヤンクしたテキストを確実に貼り付ける](paste-register-0.html)
