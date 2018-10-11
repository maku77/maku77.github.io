---
title: "シェルスクリプト: コマンドライン引数の数が正しいかチェックする"
date: "2010-06-13"
---

シェルスクリプトが最低 2 つのパラメータを必要としている場合は、先頭部分で下記のようなチェックをするのがよいでしょう。

#### 例: 2つ以上のパラメータが必要

~~~ bash
#!/bin/bash

if [ $# -lt 2 ]; then
  echo "Usage: $(basename $0) <file1> <file2>" >&2
  exit -1
fi
~~~

下記はバリエーションです。

#### 例: 1つは引数が必要

~~~ bash
#!/bin/bash

if [ ! "$1" ]; then
  echo "Usage: $(basename $1) <file>" >&2
  exit -1
fi
~~~

#### 例: 1つは引数が必要で、かつ、その名前のファイルが存在する

~~~ bash
#!/bin/bash

if [ ! "$1" ]; then
  echo "Usage: $(basename $0) <file>" >&2
  exit -1
fi

if [ ! -f "$1" ]; then
  echo "$1 is not found" >&2
  exit -1
fi
~~~

