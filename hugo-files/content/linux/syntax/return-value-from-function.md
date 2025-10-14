---
title: "Linuxメモ: Bash の構文: 関数から戻り値を返す ($?)"
url: "p/8uionzb/"
date: "2012-07-09"
tags: ["linux"]
aliases: /linux/syntax/return-value-from-function.html
---

シェル関数が `return` で返すことのできる値は **数値 (0～255) だけ** です。
`return` で返された戻り値を参照するには **`$?`** を使います。

```bash
function myfunc {
  return 1
}

myfunc
echo $?
```

関数の実行結果を文字列として受け取りたい場合は、関数内部で `echo` した結果を以下のようにバッククォート呼び出しして取得します。

```bash
function myfunc {
  echo 'Hello'
}

val=`myfunc`
echo $val
```
