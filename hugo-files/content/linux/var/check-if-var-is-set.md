---
title: "Linuxメモ: シェルスクリプト: シェル変数/環境変数がセットされているか調べる"
url: "p/xi5nrtd/"
date: "2015-10-16"
tags: ["linux"]
aliases: /linux/var/check-if-var-is-set.html
---

変数がセットされていることを調べる
----

下記の例では、変数 `NAME` がセットされているかどうか調べています。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

if [ -n "$NAME" ]; then
  echo $NAME
fi
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh

$ NAME=maku ./sample.sh
maku
{{< /code >}}


変数がセットされていないことを調べる
----

下記の例では、変数 `NAME` が**セットされていない**ことを調べています。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash
if [ -z "$NAME" ]; then
  echo 'NAME is not set'
fi
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh
NAME is not set

$ NAME=maku ./sample.sh
{{< /code >}}


仕組みの解説
----

`test` や `[ ... ]` 構造で使用できる演算子に下記のようなものがあります。

* `-z 文字列` - 文字列の長さが 0 の場合に真
* `-n 文字列` - 文字列の長さが 0 でない場合に真

変数の値を `"$NAME"` という構文で確実に文字列になるように展開し、上記の演算子でその文字列の長さをチェックすることにより、変数がセットされている（あるいはセットされていない）ことを調べることができます。
