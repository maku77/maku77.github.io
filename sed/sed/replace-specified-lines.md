---
title: sed で指定した範囲の行を置換する
created: 2010-08-26
---

置換コマンド `s` の直前に、置換対象とする行範囲を指定することができます。

#### 入力ファイル (input.txt)

~~~
001 Hello
002 Hello
003 Hello
004 Hello
005 Hello
~~~

#### 例: 3 行目だけ置換

~~~
$ sed -e '3s/Hello/World/' input.txt
001 Hello
002 Hello
003 World
004 Hello
005 Hello
~~~

#### 例: 2 ～ 4 行目だけ置換

~~~
$ sed -e '2,4s/Hello/World/' input.txt
001 Hello
002 World
003 World
004 World
005 Hello
~~~

#### 例: 最終行 ($) だけ置換

~~~
$ sed -e '$s/Hello/World/' input.txt
001 Hello
002 World
003 World
004 World
005 Hello
~~~

