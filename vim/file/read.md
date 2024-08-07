---
title: "別ファイルの内容や外部コマンドの実行結果をカーソル位置に挿入する (:read)"
date: "2006-12-21"
---

別ファイルの内容を挿入する
----

`:read` コマンド（省略形は `:r`）を使用すると、現在カーソルがある行へ、別のテキストファイルの内容を挿入することができます。

#### カレント行に別ファイルの内容を挿入

~~~
:r /home/maku/sample.txt
~~~

行番号を先に指定すれば、任意の位置に挿入することができます。

#### ファイルの先頭 (0) に別ファイルの内容を挿入

~~~
:0 r /home/maku/sample.txt
~~~

#### ファイルの末尾 ($) に別ファイルの内容を挿入

~~~
:$ r /home/maku/sample.txt
~~~


外部コマンドの出力を挿入する
----

`:read` コマンドのパラメータとして、ファイル名の代わりに `!コマンド名` を指定すると、任意のコマンドの出力結果をカーソル位置に挿入することができます。

#### ls コマンドの出力をカーソル位置に挿入

~~~
:r !ls
~~~

以下のように挿入位置を指定する方法でも同様のことを行えます。

~~~
:. !ls
~~~


外部コマンドによるテキストフィルタリング
----

行範囲を指定して外部コマンドを実行すると、その範囲のテキストを外部コマンドの標準入力へ渡すことができます。
指定した範囲のテキストは、外部コマンドの出力結果で置き換えられます。

#### 1 行目から 5 行目までをソートする

~~~
:1,5 !sort
~~~

上記のように実行すると、外部コマンドの `sort` が実行されます。

ちなみに、行ソートを行いたいときは、通常は Vim 組み込みの `sort` を使用すれば事足ります。
ソートしたい行範囲を <kbd>Shift-V</kbd> で選択し、次のようにソートを実行できます。

~~~
:sort
~~~

