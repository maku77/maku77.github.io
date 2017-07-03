---
title: echo と print で迷ったら echo を使おう
created: 2012-09-30
---

標準出力へ文字列を出力する `echo` と `print` は、どちらもほとんど同じように使用できます。
両方とも PHP の言語構造の一部なので、パラメータを格好で囲む必要はありません（`return` や `exit`、`die` に括弧が必要ないのと同じです）。

~~~ php
echo 'Hello';
print 'Hello';
~~~

`echo` が戻り値を持たないのに対し、`print` は出力に成功したかどうかの `bool` 値を返すので、速度的に若干不利のようです。
特に理由がなければ、通常は `echo` を使っておくとよいでしょう（タイプ数も少ないしね！）。

`echo` には、複数のパラメータを渡すことができますが、`print` は 1 つのパラメータしか受け取れません。

~~~ php
echo $param1, $param2;   // OK
print $param1, $param2;  // Error !!
~~~

