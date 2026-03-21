---
title: "Gitメモ: git log によるログを標準出力へ出力する (git log --no-pager)"
url: "p/ntcpmjj/"
date: "2010-12-09"
tags: ["git"]
aliases: [/git/log/output-to-stdout.html]
---

`git log` によるログ出力は、デフォルトでは `less` などの Pager アプリケーションに渡されるようになっています。
ツール連携などをしたいときに、標準出力へ直接出力するには以下のようにします。

```console
$ git log --no-pager
```

