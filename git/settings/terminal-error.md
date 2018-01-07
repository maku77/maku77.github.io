---
title: msysGit で "terminal is not fully functional" と怒られる場合の対策
date: "2014-08-19"
---

Windows の Git クライアントとして msysGit を使っていると、`git log` や `git diff` を実行したときに以下のような警告が出ることがあります。

~~~
C:\> git log
WARNING: terminal is not fully functional
~~~

これを防ぐには、`TERM` 環境変数を設定します。

~~~
set TERM=msys
~~~

