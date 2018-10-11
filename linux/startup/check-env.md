---
title: "シェルスクリプト: ある環境変数が定義されているかチェックする"
date: "2010-06-13"
---

下記の例では、`SRC_ROOT` という環境変数が設定されているかを調べ、設定されていない場合はメッセージを終了しています。

#### sample.sh

~~~ bash
#!/bin/bash

if [ -z $SRC_ROOT ]; then
  echo 'Please set the "SRC_ROOT" environment variable and try again.' >&2
  exit -1
fi

echo 'Program continues...'
~~~

<div class="note">
<code>echo</code> の出力を <code>&gt;&amp;2</code> とリダイレクトすると標準エラー出力へ出力できます。
</div>

#### 実行結果

~~~
$ ./sample.sh
Please set the "SRC_ROOT" environment variable and try again.

$ SRC_ROOT=/Users/maku/src ./sample.sh
Program continues...
~~~

