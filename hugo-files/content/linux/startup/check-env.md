---
title: "Linuxシェルスクリプト: ある環境変数が定義されているかチェックする (test -z)"
url: "p/r3myewb/"
date: "2010-06-13"
tags: ["Linux"]
aliases: /linux/startup/check-env.html
---

環境変数がセットされているかを確認する
----

下記の例では、`SRC_ROOT` という環境変数が設定されているかを調べ、設定されていない場合はメッセージを終了しています。

{{< code lang="bash" title="sample.sh" >}}
#!/bin/bash

if [ -z "$SRC_ROOT" ]; then
  echo 'Please set the "SRC_ROOT" environment variable and try again.' >&2
  exit -1
fi

echo 'Program continues...'
{{< /code >}}

`-z` は、指定された変数の文字列長が 0 であるかを調べるオプションです。
`SRC_ROOT` 変数にスペースを含む文字列が設定されている場合に備えて、`"$SRC_ROOT"` のようにダブルクォートで囲むことをお勧めします。

{{% note %}}
`echo` の出力を `>&2` とリダイレクトすると標準エラー出力へ出力できます。
{{% /note %}}

{{< code lang="console" title="実行例" >}}
$ ./sample.sh
Please set the "SRC_ROOT" environment variable and try again.

$ SRC_ROOT=/Users/maku/src ./sample.sh
Program continues...
{{< /code >}}


（おまけ）test コマンドについて
----

```bash
if [ -z "$SRC_ROOT" ]; then
```

という条件判定は、次のように __`test`__ コマンドを使うのと同等です。

```bash
if test -z "$SRC_ROOT"; then
```

__`test -z`__ コマンドは、指定された変数値の文字列長が 0 かどうかを確認するコマンドです。
これを利用して、上記のように __特定の変数値がセットされていない__ ことを調べることができます。

ちなみに、__`[`__ は特殊な記号のように見えますが、実質は `test` コマンドのエイリアスなので、前後にスペースが必要なことに注意してください。

