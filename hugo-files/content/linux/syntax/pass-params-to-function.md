---
title: "Linuxメモ: Bash の構文: 関数へパラメータを渡す"
url: "p/ecj6wbo/"
date: "2011-04-15"
tags: ["linux"]
aliases: /linux/syntax/pass-params-to-function.html
---

関数に渡されたパラメータの処理方法は、シェルスクリプト実行時に渡されたコマンドラインパラメータの処理方法と同じです。
以下のコマンドラインパラメータの処理方法に関する記事も参考にしてください。

* [コマンドライン引数を取得する](/p/c2kx7er/)


関数へ渡されたパラメータの数を調べる ($#)
----

関数内で **`$#`** を参照すると、渡されたパラメータの数を調べることができます。

{{< code lang="bash" title="サンプルコード" >}}
function foo {
  echo $#
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ foo 100 200
2

$ foo aaa bbb ccc
3

$ foo "This is a pen"
1
{{< /code >}}

{{% note %}}
関数の外で `$#` を参照すると、シェルスクリプト実行時に渡されたコマンドラインパラメータの数を取得できます。
{{% /note %}}

{{< code lang="bash" title="応用例: 関数へ渡されたパラメータが少なくとも 1 つ以上あるか調べる" >}}
function foo {
  if [ $# -lt 1 ]; then
    echo 'Function "foo" needs at least one parameter' >&2
    exit -1
  fi
  echo 'OK'
}
{{< /code >}}


関数へ渡されたパラメータを順番に処理する
----

{{< code lang="bash" title="サンプルコード" >}}
function enum_params {
  while [ -n "$1" ]; do
    echo $1
    shift
  done
}
{{< /code >}}

`[ -n "$1" ]` の部分では、パラメーターが空文字ではないかどうかを調べています。
`shift` コマンドは、パラメータを左にシフトします。
つまり、`$2` の値が `$1` に、`$3` の値が `$2` に、というようにシフトされます。
これにより、すべてのパラメーターを `$1` で順番に参照することができます。

{{< code lang="console" title="実行結果" >}}
$ enum_params 1 2 3
1
2
3
{{< /code >}}

パラメータをシングルクォーテーション、あるいはダブルクォーテーションで囲むと、1 つのパラメータとして処理されます。

```console
$ enum_params "1 2 3"
1 2 3
```


関数のデフォルトパラメータを定義する
----

パラメータを参照するときに、**`${1:-デフォルト値}`** という形で参照すると、1 つ目のパラメータが指定されなかった場合に `デフォルト値` を使用することができます（これも、シェルスクリプト自体のコマンドラインパラメータの扱い方と同様です）。

{{< code lang="bash" title="サンプルコード" >}}
function greet {
  name=${1:-everyone}
  echo "Hello $name"
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ greet
Hello everyone

$ greet Maku
Hello Maku
{{< /code >}}
