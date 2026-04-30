---
title: "Windowsメモ: カレントディレクトリのパスを取得する (%CD%)"
url: "p/imkepzz/"
date: "2016-08-17"
tags: ["windows"]
aliases: /windows/cmd/get-current-dir.html
---

コマンドプロンプト上で `%CD%` を参照すると、カレントディレクトリのパスを取得することができます。

```
C:\foo\bar> echo %CD%
C:\foo\bar
```

`clip` コマンドを組み合わせて使用すると、カレントディレクトリのパスをクリップボードに流し込むことができます。

```
C:\foo\bar> echo %cd% | clip
```

これで、カレントディレクトリのパスを `Ctrl + V` で任意のアプリケーションに貼り付けることができます。
