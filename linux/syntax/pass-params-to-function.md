---
title: "関数へパラメータを渡す"
date: "2011-04-15"
---

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
    echo "need at least one parameter"
    return
  fi
  echo "OK"
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
$ enum_params '1 2 3'
1 2 3
```

