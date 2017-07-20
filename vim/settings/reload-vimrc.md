---
title: 設定ファイル (.vimrc) を開く、リロードする
created: 2007-12-26
---

設定ファイルを開く
----

設定ファイル（`.vimrc` や `.gvimrc`）を開くには、下記のようにします。

~~~
:edit ~/.vimrc
:edit ~/.gvimrc
~~~

Windows の場合は、`.vimrc` ではなく、`_vimrc` というファイル名なので、下記のようにしなければいけません。

~~~
:edit ~/_vimrc
:edit ~/_gvimrc
~~~

実は、これらの設定ファイル名（パス）は、`$MYVIMRC`、`$MYGVIMRC` という変数に格納されているので、次のように実行すれば、環境に依存しない指定方法で設定ファイルを開くことができます。

~~~
:edit $MYVIMRC
:edit $MYGVIMRC
~~~


設定ファイルを再読み込みする
----

設定ファイルを変更した後で、その内容を反映させるには下記のように実行します。

~~~
:source $MYVIMRC
:source $MYGVIMRC
~~~


参考
----

* [ショートカットキーで設定ファイル (.vimrc) を開く](../settings/open-vimrc-quickly.html)

