---
title: "Perlメモ: 現在の環境で使用可能な make ユーティリティの名前を確認する"
url: "p/k3c5i3x/"
date: "2008-07-06"
tags: ["perl"]
aliases: ["/perl/misc/make.html"]
---

`perl` コマンドを `-V:make` オプションを付けて実行すると、現在の開発環境で使用可能な make 系コマンドの名前を調べることができます。

{{< code title="例: Windows で実行したとき" >}}
C:\> perl -V:make
make='nmake';
{{< /code >}}

{{< code lang="console" title="例: Linux で実行したとき" >}}
$ perl -V:make
make='make';
{{< /code >}}
