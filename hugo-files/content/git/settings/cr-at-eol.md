---
title: "Windows の git diff で改行コードが ^M で表示される問題を解決する (core.whitespace)"
url: "p/uek4bb2/"
date: "2014-06-04"
tags: ["Git"]
aliases: /git/settings/cr-at-eol.html
---

Windows のターミナル環境において、改行コードが `CR`+`LF` のファイルを編集している場合、

```console
$ git config --global color.ui auto
$ git config --system color.diff auto
```

のように、`git diff` の出力がカラフルに表示されるようになっていると、行末の __`CR`__ がうまく処理できず、__`^M`__ と表示されてしまうことがあります。
このような場合は、次のように __`core.whitespace`__ 設定を行っておくと `^M` の表示を抑制できます。

```console
$ git config --global core.whitespace cr-at-eol
```

