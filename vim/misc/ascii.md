---
title: "カーソル位置の文字の文字コードを表示する (ascii)"
date: "2011-06-06"
---

ノーマルモードで、

~~~
ga
~~~

と入力すると、カーソル位置の文字コード（10進数、16進数、8進数）を表示できます。
例えば、`a` という文字にカーソルを置いて `ga` と入力すると、以下のように表示されます。

~~~
<a>  97,  Hex 61,  Octal 141
~~~

コマンドモードから次のように入力することでも同様のことを行えます。

~~~
:ascii
~~~
