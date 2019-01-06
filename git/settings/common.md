---
title: "git config で設定すべき一般的な設定項目まとめ"
date: "2014-06-04"
---

Git を使い始める前の初期設定としては、多くの場合は、下記のような設定を行っておけばよいでしょう。
改行コードに関する設定部分は、自動変換するかしないかで、どの設定を行うかを判断してください。

~~~
### コミット時のログ設定
$ git config --global user.name "Taro Yamada"
$ git config --global user.email "taro@example.com"

### 便利設定
$ git config --global color.ui auto    # コマンド出力に色を付ける
$ git config --global core.editor vim  # git commit 時のエディタ

### 文字化け防止 (git diff)
$ git config --global core.pager "LESSCHARSET=utf-8 less"

### 文字化け防止 (git status)
$ git config --global core.quotepath false

### 改行コードの自動変換なし
$ git config --global core.autocrlf false

### 改行コードの自動変換あり（Windows の場合）
$ git config --global core.autocrlf true  # ローカルの改行コード CR+LF
$ git config --global core.safecrlf true  # 余計な改行コード変換を抑制
$ git config --global core.whitespace cr-at-eol  # git diff 時の ^M を抑制

### 改行コードの自動変換あり（Mac/Linux の場合）
$ git config --global core.autocrlf input  # ローカルの改行コード LF
$ git config --global core.safecrlf true   # 余計な改行コード変換を抑制
~~~


参考

* [コミット時に使用するユーザ名とメールアドレスを設定する](./user.html)
* [コミット、チェックアウト時に改行コードを自動変換する](./autocrlf.html)
* [コミット時にコメント記述に使用するエディタを設定する](./editor.html)
* [Git コマンドの出力をカラフルにする](./color.html)

