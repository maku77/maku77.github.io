---
title: ファイルへの保存方法いろいろ
date: "2006-12-21"
---

いろいろな保存方法
----

~~~ vim
:w file.txt         " file.txt というファイル名で保存
:w >> file.txt      " file.txt に追記保存
:1,5 w file.txt     " 1 行目から 5 行目まで保存

:5,$ w file.txt     " 5 行目から最後まで保存
:.,10 w file.txt    " カレント行から 10 行目まで保存
:.;+5 w file.txt    " カレント行から 5 行下の行までを保存

:g/^Todo/ w D:\todo.txt    " Todo で始まる行を D:\todo.txt に保存
~~~

メモ: % は現在編集中のファイル名を示す
----

#### 例: sample.txt を編集中に、sample.txt.back というファイル名で保存する

~~~ vim
:w %.back
~~~

`%<` とすると、拡張子を除いた部分を取得できます。

#### 例: sample.cpp を編集中に、sample.h を開く

~~~ vim
:vi %<.h
~~~


メモ: :wq と :x の違い
----

* `:wq` はファイルが編集されていなくても無条件で保存して Vim を終了します。
* `:x` はファイルが編集されていた場合だけ保存して Vim を終了します。

