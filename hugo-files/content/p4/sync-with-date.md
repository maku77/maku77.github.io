---
title: "Perforceメモ: 日時指定で p4 sync する"
url: "p/nytgqgb/"
date: "2007-08-01"
tags: ["perforce"]
aliases: ["/p4/sync-with-date.html"]
---

{{< code lang="console" title="例: 2007-01-01 09:00:00 の時点でのファイルを取得" >}}
$ p4 sync //depot/foo/bar/...@'2007/01/01 09:00:00'
{{< /code >}}
