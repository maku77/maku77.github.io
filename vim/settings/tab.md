---
title: "タブ文字の設定 (tabstop, expandtab)"
date: "2007-04-11"
---

タブ文字 1 文字分の表示幅を設定する
----

~~~
:set tabstop=4    "デフォルトは 8
~~~

タブ文字がどれくらいの幅で表示されるかの設定は `tabstop` オプションで行います。
例えば、`tabstop` を 4 に設定すると、4 文字分のスペースと同じ幅でタブ文字が表現されます。

この設定は、あくまで見え方の設定であって、タブは１文字のタブ文字として存在します。
他のエディタでファイルを開くと、タブ文字の見え方は変わってきます。


タブを入力したときにタブ文字の代わりにスペースを挿入する
----

~~~
:set expandtab    "デフォルトは noexpandtab
~~~

上記の設定を行うと、TAB キーを押したときにタブ文字ではなく、`tabstop` で設定した数のスペースを挿入するようになります。
このモードのときにタブ文字を明示的に入力したい場合は、`CTRL-V <TAB>` と入力します（あるいは `CTRL-Q <TAB>` でも OK）。

参考
----

* [すでに入力されているタブをスペースに変換する (:retab)](../edit/retab.html)
