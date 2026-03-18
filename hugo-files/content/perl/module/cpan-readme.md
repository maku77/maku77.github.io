---
title: "Perlメモ: CPAN に登録されたディストリビューションの README ファイルを表示する"
url: "p/d6eici9/"
date: "2008-07-06"
tags: ["perl"]
aliases: ["/perl/module/cpan-readme.html"]
---

{{< code title="例: DBD::mysql モジュールの README を表示" >}}
$ perl -MCPAN -eshell
cpan> readme DBD::mysql
{{< /code >}}

`readme` コマンドの実行は最初の一回だけ時間がかかります。
