---
title: "Linuxメモ: Bash の構文: 関数を定義する／削除する"
url: "p/em67j5z/"
date: "2009-12-15"
tags: ["linux"]
aliases: /linux/syntax/function.html
---

関数定義の基本
----

Bash では以下のいずれかの構文を利用して、関数を定義することができます。

```bash
function funcname {
  <shell commands...>
}

funcname() {
  <shell commands...>
}
```

開き括弧 (`{`) は、上記のように関数名と同じ行に記述してもよいし、次の行に単独で記述してもよいです。


関数を削除する
----

定義されている関数を削除するには以下のようにします。

```bash
unset -f funcname
```

現在のログインセッションでどんな関数が定義されているかを調べるには以下のようにします。

```console
$ declare -F    # 関数名のみ出力
$ declare -f    # 関数の定義も出力
```


関数と環境変数
----

関数はスクリプトとは異なり、実行したシェルと同じプロセスで実行されます。
つまり、以下のように関数内で環境変数を設定すれば、関数を呼び出したシェルにも反映されます。

```bash
myfunc() {
  export AAA=100
}
```


空の関数は syntax error になる
----

中身が空の関数が定義されていると、構文エラーが発生するようです。

```bash
myfunc() {
  # syntax error
}
```

{{< code lang="console" title="エラーの例" >}}
$ ./sample.sh
./sample.sh: 行 3: 予期しないトークン `}' 周辺に構文エラーがあります
./sample.sh: 行 3: `}'
{{< /code >}}
