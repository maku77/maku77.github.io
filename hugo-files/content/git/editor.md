---
title: "Git のコミット時にコメント記述に使用するエディタを設定する (core.editor)"
url: "p/cqjv7wv/"
date: "2010-07-17"
tags: ["Git"]
aliases: /git/settings/editor.html
---

`git commit` コマンド実行時に、`-m` オプションでコメントを指定しなかった場合は、コメント入力のためにエディタが起動します。
ここで起動するエディタは自由に変更できます。
操作に慣れた Vim エディタなどを指定しておけば、コミット前の編集画面で操作ミスしてしまうこともなくなるでしょう。

{{< code lang="console" title="例: エディタを vim に設定する" >}}
$ git config --global core.editor vim
{{< /code >}}

上記の例では、__`core.editor`__ 設定を使ってエディタの指定を行っていますが、エディタの指定方法はたくさんあり、Git は次の順番で使用するエディタを判断します。

1. 環境変数 __`GIT_EDITOR`__
2. Git の __`core.editor`__ 設定
3. 環境変数 __`VISUAL`__
4. 環境変数 __`EDITOR`__
5. `vi`（Debian、Ubuntu の場合は `editor`）

