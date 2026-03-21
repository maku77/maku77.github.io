---
title: "Gitメモ: 指定したディレクトリ以下の変更に関連するコミットログのみ表示する"
url: "p/kftbcyc/"
date: "2010-12-09"
tags: ["git"]
aliases: [/git/log/dir-log.html]
---

`git log` コマンドのパラメータとしてディレクトリ名を指定すると、そのディレクトリ以下のファイルの変更に関わるコミットログだけを表示することができます。

{{< code lang="console" title="例: カレントディレクトリ以下の変更のコミットログを表示" >}}
$ git log .
{{< /code >}}

{{< code lang="console" title="例: src ディレクトリ以下の変更のコミットログを表示" >}}
$ git log src
{{< /code >}}

