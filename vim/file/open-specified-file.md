---
title: "ファイル名を指定してファイルを開く"
date: "2007-10-05"
---


ファイル名を指定してファイルを開く
----

`:edit` コマンド（`:e` と省略可）を使用すると、現在のバッファで指定した名前のファイルを開くことができます。

~~~
:e [file]
:edit [file]
~~~

別のファイルを開く前に、現在の編集内容を `:w` で保存しておく必要があります。
現在の編集を破棄して別のファイルを開きたい場合は、コマンドの後ろに `!` を付けて実行します。

~~~
:e! [file]
:edit! [file]
~~~


ReadOnly モードでファイルを開く
----

`:edit` コマンドの代わりに、`:view` コマンドを使用してファイルを開くと、読み取り専用でファイルを開くことができます。

~~~
:view [file]
~~~

ファイルを開くときは `:e` コマンドで代用してしまうことが多いので、`:view` コマンドを使用することはあまりないかもしれません。

<div class="note">
<code>:edit</code> コマンドは <code>:e</code> と省略することができますが、<code>:view</code> コマンドは <code>:vie</code> までしか省略することができません。詳しくは、<code>:help :view</code> でヘルプを確認してみてください。
</div>

