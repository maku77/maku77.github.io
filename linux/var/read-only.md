---
title: "シェルスクリプト: 定数を定義する (readonly)"
date: "2010-09-07"
---

下記のように実行すると、指定した変数や関数に対する代入や `unset` ができなくなります。

~~~ bash
readonly var      # 変数 var を readonly に
readonly -a arr   # 配列 arr を readonly に
readonly -f func  # 関数 func を readonly に
~~~

変数の定義と同時に `readonly` 指定することも可能です。

~~~ bash
readonly USERNAME=maku
~~~

下記の例では、変数 `MAX_SIZE` を値 100 で初期化し、その後 200 という値を代入しようとしています。

#### sample.sh

~~~ bash
#!/bin/bash

readonly MAX_SCORE=100
MAX_SCORE=200
~~~

`MAX_SCORE` 変数は `readonly` 化されているので、代入を実行しようとしたときにエラーになります。

#### 実行結果

~~~
$ ./sample.sh
./sample.sh: 行 4: MAX_SCORE: 読み取り専用の変数です
~~~

