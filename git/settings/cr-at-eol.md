---
title: Windows の git diff で改行コードが "^M" で表示される問題を解決する
date: "2014-06-04"
---

Windows の CLI 環境において、改行コードが CR+LF のファイルを編集している場合、

~~~
$ git config --global color.ui auto
$ git config --system color.diff auto
~~~

のように、`git diff` の出力がカラフルに表示されるようになっていると、行末の CR がうまく処理できず、`^M` と表示されてしまいます。
以下のような設定をしておくと、この表示を抑制することができます。

~~~
$ git config --global core.whitespace cr-at-eol
~~~

