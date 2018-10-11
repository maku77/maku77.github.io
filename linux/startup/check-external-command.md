---
title: "シェルスクリプト: ある外部コマンドが使用できるかチェックする"
date: "2010-06-13"
---

シェルスクリプトの中で特定の外部コマンドを使用する必要がある場合、先頭部分でその外部コマンドが使用できる状態かどうかを調べておくと親切です。
例えば、次のようにすれば、`ruby` コマンドが使用できるかを確認できます。

~~~ bash
if type -P ruby > /dev/null; then
  echo 'ruby コマンドを使用できます'
else
  echo 'ruby コマンドが見つかりません'
fi
~~~

<div class="note">
<code>type -P ruby</code> コマンドは、パスの通ったディレクトリから <code>ruby</code> を検索してそのパスを取得するためのものですが、ここでは <code>ruby</code> コマンドが使用できる環境になっているかを確認するために利用しています。
単純に実行すると検索結果のパスが出力されてしまうので、<code>null</code> デバイスへリダイレクトして出力を抑制しています。
</div>

特定のコマンドが使用できない場合にスクリプトを終了させるには、以下のように記述すればよいでしょう。
条件式を `!` で反転させていることに注意してください。

~~~ bash
if ! type -P ruby > /dev/null; then
   echo 'This script requires Ruby. Please install Ruby first and try again.' >&2
   exit -1
fi
~~~

