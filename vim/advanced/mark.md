---
title: "カーソル位置にマーク（ブックマーク）してジャンプする"
date: "2007-10-07"
description: "Vim のマーク機能を使用すると、カーソル位置に a～z、あるいは A～Z のマークを付け、別の場所からそこへジャンプして来ることができるようになります。"
---

マークを付ける
----

<kbd>m</kbd> に続けて、一文字のアルファベットを入力すると、現在のカーソル位置にマークを設定することができます。

~~~
ma  -- カーソル位置をローカルマーク a として保存（a～z を使用可能）
mA  -- カーソル位置をグローバルマーク A として保存（A～Z を使用可能）
~~~

マーク用のアルファベットとして**小文字の a～z を使用すると、カレントバッファのみに有効なローカルマーク**として設定されます。
**大文字の A～Z を使用すると、グローバルマーク（ファイルマーク）**として設定されます。
ローカルマークはファイルごとに a～z を使用することができ、グローバルマークは全体で A～Z を共有することになります。


マークを設定した位置へジャンプする
----

~~~
`a  -- ローカルマーク a へジャンプ
'a  -- ローカルマーク a の行頭へジャンプ
`A  -- グローバルマーク A へジャンプ
'A  -- グローバルマーク A の行頭へジャンプ
~~~

ローカルマーク (a～z) を指定してジャンプするときは、カレントバッファで開いているファイルに設定されたローカルマークがジャンプ先のターゲットとなります。

一方、グローバルマーク (A～Z) は、マークしたファイルまで一緒に記録されており、Vim でどのファイルを編集中であっても、そのファイルにジャンプすることができます。
頻繁に使用するファイルにグローバルマークを設定 (mA) しておけば、そのファイルを Vim からいつでも簡単に開ける ('A) ようになります。

また、ジャンプ元とジャンプ先で行ったり来たりするために、下記のようなジャンプコマンドを使用することができます。

~~~
``  -- バッファ内のジャンプ元へジャン
''  -- バッファ内のジャンプ元の行頭へジャンプ
~~~


マークの一覧を表示する
----

現在設定されているローカルマークとグローバルマークの一覧を表示するには、下記のように実行します。

~~~ vim
:marks
~~~


応用: マークを利用した編集
----

<kbd>`</kbd><kbd>a</kbd> と入力するとローカルマーク `a` へ移動しますが、これと `d` コマンドを組み合わせて使用すれば、カーソル位置からローカルマーク `a` までのテキストを削除することが できます。

#### ローカルマーク a までを削除する

~~~
d`a
~~~


応用: 最近開いていたファイルを開く
----

前回開いていたファイルは、グローバルマーク `0` を指定してジャンプすることで開けます。

~~~
'0
~~~

もとのファイルに戻ってきたい場合は、グローバルマーク `1` を指定すると戻ってこれます。

~~~
'1
~~~

もっと前に開いていたファイルも、:marks コマンドでグローバルマークの番号を確認してジャンプすることができます。

