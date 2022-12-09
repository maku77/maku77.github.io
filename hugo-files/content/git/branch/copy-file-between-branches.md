---
title: "Git で他のブランチの最新ファイルを現在のブランチにコピーする (git checkout)"
url: "p/6g8n3jw/"
date: "2010-08-20"
lastmod: "2022-12-09"
tags: ["Git"]
aliases: /git/branch/copy-file-between-branches.html
---

Git では、ファイル単位で他のブランチにコミットされているファイルをコピーしてくることができます。
例えば、現在 `main` ブランチをチェックアウトしているときに、別のブランチ `mybranch` の `sample.txt` の最新をコピーしてきたいときは次のように実行します。

```console
$ git checkout mybranch sample.txt
```

