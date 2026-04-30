---
title: "Windowsメモ: 既にディレクトリが存在している場合の mkdir のエラーを抑制する"
url: "p/2sarvou/"
date: "2015-04-13"
tags: ["windows"]
aliases: /windows/file/avoid-mkdir-error.html
---

`md` (`mkdir`) コマンドでディレクトリを作成しようとしたときに、すでにそのディレクトリが存在していると、`md` コマンドはエラーを吐いて終了してしまいます。

```
C:\> md foo\bar
A subdirectory or file foo\bar already exists.
```

このエラー出力を抑制するには以下のようにします。

```
C:\> md foo\bar > NUL 2>&1
```

ちなみに Linux の場合は `-p` オプションを使って簡単に同じことができます。

{{< code lang="console" title="Linux の場合" >}}
$ mkdir -p foo/bar
{{< /code >}}
