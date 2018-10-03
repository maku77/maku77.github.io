---
title: "現在の環境で使用可能な make ユーティリティの名前を確認する"
date: "2008-07-06"
---

`perl` コマンドを `-V:make` オプションを付けて実行すると、現在の開発環境で使用可能な make 系コマンドの名前を調べることができます。

#### 例: Windows で実行したとき

~~~
C:\> perl -V:make
make='nmake';
~~~

#### 例: Linux で実行したとき

~~~
$ perl -V:make
make='make';
~~~

