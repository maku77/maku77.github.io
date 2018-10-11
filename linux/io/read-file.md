---
title: "シェルスクリプト: テキストファイルを読み込む"
date: "2012-11-05"
---


テキストファイルを一行だけ読み込む
----

**read** コマンドは、ユーザからの入力を一行取得するために使用できますが、ファイルからの入力を一行読み込むのにも使用できます。

#### sample.sh

~~~ bash
#!/bin/bash

read line < /Users/maku/input.txt
echo $line
~~~

#### input.txt（入力データ）

~~~
AAA
BBB
CCC
~~~

#### 実行結果

~~~
$ ./sample.sh
AAA
~~~


テキストファイルから一行ずつ読み込む
----

**read** コマンドと **while** ループを組み合わせると、テキストファイルのすべての行を一行ずつ処理できます。

#### sample.sh

~~~ bash
#!/bin/sh

while read line; do
  echo $line
done < /Users/maku/input.txt
~~~

#### input.txt（入力データ）

~~~
AAA
BBB
CCC
~~~

#### 実行結果

~~~
$ ./sample.sh
AAA
BBB
CCC
~~~

