---
title: "Bash の構文: 関数へパラメータを渡す"
date: "2011-04-15"
---

関数に渡されたパラメータの処理方法は、シェルスクリプト実行時に渡されたコマンドラインパラメータの処理方法と同じです。
下記のコマンドラインパラメータの処理方法に関する記事も参考にしてください。

* [コマンドライン引数を取得する](../startup/command-line-params.html)


関数へ渡されたパラメータの数を調べる ($#)
----

関数内で `$#` を参照すると、渡されたパラメータの数を調べることができます。

#### サンプルコード

```bash
function foo {
  echo $#
}
```

#### 実行結果

```
$ foo 100 200
2

$ foo aaa bbb ccc
3

$ foo "This is a pen"
1
```

<div class="note">
関数の外で <code>$#</code> を参照すると、シェルスクリプト実行時に渡されたコマンドラインパラメータの数を取得できます。
</div>

### 応用例: 関数へ渡されたパラメータが少なくとも 1 つ以上あるか調べる

```bash
function foo {
  if [ $# -lt 1 ]; then
    echo 'Function "foo" needs at least one parameter' >&2
    exit -1
  fi
  echo 'OK'
}
```


関数へ渡されたパラメータを順番に処理する
----

#### サンプルコード

```bash
function enum_params {
  while [ -n "$1" ]; do
    echo $1
    shift
  done
}
```

#### 実行結果

```
$ enum_params 1 2 3
1
2
3
```

パラメータをシングルクォーテーション、あるいはダブルクォーテーションで括ると、１つのパラメータとして処理されます。

```
$ enum_params "1 2 3"
1 2 3
```


関数のデフォルトパラメータを定義する
----

パラメータを参照するときに、`${1:-デフォルト値}` という形で参照すると、1つ目のパラメータが指定されなかった場合に `デフォルト値` を使用することができます（これも、シェルスクリプト自体のコマンドラインパラメータの扱い方と同様です）。

#### サンプルコード

```bash
function greet {
  name=${1:-everyone}
  echo "Hello $name"
}
```

#### 実行結果

```
$ greet
Hello everyone

$ greet Maku
Hello Maku
```

