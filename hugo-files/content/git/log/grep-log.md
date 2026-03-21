---
title: "Gitメモ: Git でコミットログにある文字列が含まれているコミットを検索する (git log --grep)"
url: "p/hi5iir5/"
date: "2010-12-09"
tags: ["git"]
lastmod: "2020-12-22"
aliases: [/git/log/grep-log.html]
---

`git log` コマンドを使用するときに、`--grep=<RegularExpression>` オプションを使用すると、指定したパターンがコミットメッセージに含まれているコミットだけを表示することができます。

{{< code lang="console" title="例: コミット時のコメントに getPrice が含まれているコミットを探す" >}}
$ git log --grep=getPrice
{{< /code >}}

`--grep` は、あくまでコミット時に記述した「コミットメッセージ」の検索だけ行うことに注意してください。
「ソースコード」の変更内容を検索したい場合は、`-G` オプションや `-S` オプションを使用します。

- 参考: [ある単語（文字列）に関する変更履歴を調べる (`git log -G/-S`)](/p/hf7bnyi/)

