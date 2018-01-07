---
title: シェルスクリプトのファイル名と絶対パスを取得する
date: "2017-08-20"
---

シェルスクリプト内から、自身のファイル名を取得するには `$0` というキーワードを使用します。

#### sample.sh

~~~ sh
#!/bin/bash
echo $0
~~~

正確には、`$0` に格納されているパスは、そのスクリプト実行時に実際に指定したパスになります。
そのため、どのようにスクリプトを起動したかによって結果が変わってきます。

~~~
### 絶対パスで起動した場合
$ cd /
$ /Users/maku/sample.sh
/Users/maku/sample.sh

### 相対パスで起動した場合
$ cd /Users
$ maku/sample.sh
maku/sample.sh

### カレントディレクトリのスクリプトを起動した場合
$ ./sample.sh
./sample.sh

### sh コマンドのパラメータで起動した場合
$ sh sample.sh
sample.sh
~~~

確実にファイル名だけを取得したい場合や、絶対パスを取得したい場合は以下の様にするのがよいでしょう。

#### sample.sh

~~~
#!/bin/bash

MY_BASENAME=$(basename $0)
MY_ABS_PATH=$(cd $(dirname $0); pwd)/$MY_BASENAME

echo $MY_BASENAME
echo $MY_ABS_PATH
~~~

#### 実行結果

~~~
$ ~/sample.sh
sample.sh
/Users/maku/sample.sh
~~~

