---
title: "シェルスクリプトを実行したユーザの名前を調べる"
date: "2009-12-16"
---

下記の例では、シェルスクリプトを実行したユーザが root であるかどうかを調べています。

~~~ bash
if [ "$USER" != "root" ]; then
  echo "Executed by root."
  exit -1
fi
~~~

