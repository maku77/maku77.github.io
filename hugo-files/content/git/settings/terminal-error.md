---
title: "msysGit で terminal is not fully functional と怒られる場合の対策"
url: "p/coekmmp/"
date: "2014-08-19"
tags: ["Git"]
aliases: /git/settings/terminal-error.html
---

Windows の Git クライアントとして __msysGit__ を使っていると、`git log` や `git diff` を実行したときに以下のような警告が出ることがあります。

```
C:\> git log
WARNING: terminal is not fully functional
```

これを防ぐには、__`TERM`__ 環境変数を設定します。

```bat
set TERM=msys
```

