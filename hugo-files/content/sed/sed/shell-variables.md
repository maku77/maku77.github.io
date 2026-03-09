---
title: "sed/awkメモ: sed の置換パターンの中でシェルの変数を参照する"
url: "/p/2sbebb7/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/shell-variables.html]
---

sed の置換パターンの中で変数展開するには、シェルに先に変数展開させるために、ダブルクォーテーションで式を囲む必要があります。
シングルクォーテーションで囲むと、`$val` という文字列そのものを置換してしまいます。

```console
$ val=AAA

$ echo 'AAA BBB CCC' | sed -e "s/$val/hoge/"
hoge BBB CCC

$ echo 'AAA BBB CCC' | sed -e "s/CCC/$val/"
AAA BBB AAA
```

