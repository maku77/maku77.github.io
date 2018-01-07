---
title: すでに入力されているタブをスペースに変換する (:retab)
date: "2009-12-07"
---

`expandtab` オプションが設定されていると、TAB キーを押したときに、タブ文字の代わりに `tabstop` で設定した数のスペースが挿入されます。
すでに入力されているタブ文字を、スペースに変換するには、`expandtab` モードを有効にしたうえで、`:retab` コマンドを実行します。

#### 例: タブ文字を４文字分のスペースに置換する

~~~
:set expandtab
:set tabstop=4
:retab
~~~

参考
----

* [タブ文字の設定 (tabstop, expandtab)](../settings/tab.html)

