---
title: "シェルスクリプト: 指定したファイルの絶対パスを取得する"
date: "2010-10-15"
---

相対パスで指定したファイルのパスを絶対パスに変換するには、下記のように `dirname` と `basename` を組み合わせて使用します。
`$path` には、カレントディレクトリからの相対パスでファイル名が格納されているとします。

~~~ bash
abs_path=$(cd $(dirname $path); pwd)/$(basename $path)
~~~

内部で `cd` コマンドを実行しているため、ディレクトリ名を含むパスを指定したときは、実際にそのディレクトリが存在していないといけないことに注意してください。
下記のサンプルスクリプトは、コマンドラインパラメータとして渡された相対パスを絶対パスに変換します。

#### rel2abs.sh（渡されたファイルパスを絶対パスに変換する）

~~~ bash
#!/bin/bash

abs_path=$(cd $(dirname $1); pwd)/$(basename $1)
echo "$abs_path"
~~~

#### 実行結果

~~~
$ cd /Users/maku/scripts
$ ./rel2abs.sh aaa/bbb/ccc.txt
/Users/maku/scripts/aaa/bbb/ccc.txt

$ cd /Users/maku
$ scripts/rel2abs.sh scripts/aaa/bbb/ccc.txt
/Users/maku/scripts/aaa/bbb/ccc.txt
~~~

実行時のカレントディレクトリが異なっていても、正しく絶対パスを取得できていることがわかると思います。

