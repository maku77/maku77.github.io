---
title: "sed/awkメモ: sed で行頭や行末に文字列を追加する"
url: "/p/n7waki8/"
date: "2010-08-26"
tags: ["sed/awk"]
aliases: [/sed/sed/head-and-end.html]
---

行頭に文字列を追加する
----

正規表現で行頭を表す `^` を置換対象とすれば、行頭に任意のテキストを追加することができます。

```console
$ echo 'Hello World' | sed -e 's/^/> /'
> Hello World
```


行末に文字列を追加する
----

正規表現で行末を表す `$` を置換対象とすれば、行末に任意のテキストを追加することができます。

```console
$ echo 'Hello World' | sed -e 's/$/ !!/'
Hello World !!
```

