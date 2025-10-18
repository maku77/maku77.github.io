---
title: "Linuxメモ: 絶対パスと相対パスの変換 (basename, dirname)"
url: "p/3qgbv7i/"
date: "2010-07-20"
tags: ["linux"]
aliases: /linux/path/absolute-path-to-relative-path.html
---

basename - ファイル名だけを抽出する
----

Linux の **`basename`** コマンドを使うと、パスを表す文字列からファイル名だけを抽出できます。

```console
$ basename /aaa/bbb/ccc
ccc

$ basename /aaa/bbb/ccc/
ccc

$ basename aaa/bbb/ccc
ccc
```


dirname - ディレクトリ、ファイルが格納されているディレクトリのパスを抽出する
----

Linux の **`dirname`** コマンドを使うと、ファイルやディレクトリが格納されているディレクトリのパスを抽出できます。

```console
$ dirname /aaa/bbb/ccc
/aaa/bbb

$ dirname /aaa/bbb/ccc/
/aaa/bbb

$ dirname aaa/bbb/ccc
aaa/bbb
```

