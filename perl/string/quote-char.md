---
title: "文字列リテラルを囲むクォート文字に任意の記号を使用する"
date: "2008-07-09"
---

シングルクォートやダブルクォートで囲まれた文字列リテラルの中に、シングルクォートやダブルクォートを含めたい場合、通常はそれらの文字をエスケープして `\'` や `\"` のように記述する必要があります。
このような記述が煩わしいときは、`q`、`qq` プレフィックスを指定すれば、文字列リテラルを囲む文字に任意のクォート文字を使用することができます。

#### 例: シングルクォートの代わりに * を使う

~~~
my $str = q*It is shown as 'function'*;
~~~

#### 例: ダブルクォートの変わりに | を使う場合

~~~
my $str = qq|Added "timer" classes|;
~~~

`q`、`qq` プレフィックスで指定したクォート文字を、文字列中で使用したい場合は、その文字を `\` でエスケープする必要があります。

