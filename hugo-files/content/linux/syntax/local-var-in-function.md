---
title: "Linuxメモ: Bash の構文: 関数内でローカル変数を扱う (local)"
url: "p/52i36fy/"
date: "2012-07-09"
tags: ["linux"]
aliases: /linux/syntax/local-var-in-function.html
---

Bash は関数外と関数内で変数を共有します。
例えば、以下のように関数内で代入を行うと、関数外でセットした値を上書きしてしまいます。

```bash
function myfunc {
  a=200
}
```

{{< code lang="console" title="実行例" >}}
$ a=100
$ myfunc
$ echo $a
200    ★aの値が変わってる！
{{< /code >}}

関数内だけで有効なローカル変数を扱うには以下のように `local` キーワードを付けます。

```bash
function myfunc {
  local a=200
}
```

{{< code lang="console" title="実行例" >}}
$ a=100
$ myfunc
$ echo $a
100    ★aの値はそのまま
{{< /code >}}
