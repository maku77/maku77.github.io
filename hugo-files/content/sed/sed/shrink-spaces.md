---
title: "sed/awkメモ: sed で連続するスペースを１つにする"
url: "/p/eeum42o/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/shrink-spaces.html]
---

下記のサンプルでは、連続するスペースを、１つのスペースに置換しています。

```console
$ echo '  AAA   BBB  CCC' | sed -e 's/  */ /g'
 AAA BBB CCC
```

１回以上の繰り返しを表す `\+` を使って次のように記述することも可能です。

```console
$ echo '  AAA   BBB  CCC' | sed -e 's/ \+/ /g'
 AAA BBB CCC
```

sed では、正規表現の `+` は `\+` のようにエスケープして入力しなければいけないことに注意してください（`*` はエスケープの必要なし）。

