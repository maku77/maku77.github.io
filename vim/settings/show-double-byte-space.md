---
title: 全角スペースを表示する
date: "2009-02-03"
---

全角スペースを表示するための設定
----

設定ファイルに下記のように記述しておくと、全角スペースに背景色が付いて判別できるようになります。
ここでは、GVim の場合に背景色として `darkgray` を指定していますが、好みの背景色に変更して適用してください。

#### ~/.vimrc

~~~ vim
"-------------------------
" Show double byte spaces
"-------------------------
hi DoubleByteSpace term=underline ctermbg=blue guibg=darkgray
match DoubleByteSpace /　/
~~~

参考
----

* [タブ文字と行末のスペースを表示する](show-space.html)

