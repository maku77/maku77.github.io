---
title: "Git の設定値がどのファイルで設定されているか調べる (config --show-origin)"
url: "p/msds6iv/"
date: "2020-03-04"
tags: ["Git"]
aliases: /git/settings/show-origin.html
---


Git 設定のスコープには、__`local`__/__`global`__/__`system`__ などがあり、ある設定値がどのスコープ（設定ファイル）で行われているか混乱することがあります。

- 参考: [Git 設定のスコープ (`local`/`global`/`system`) を理解する](/p/af7q7n3/)

そのような場合は、__`git config --list --show-origin`__ コマンドを使うと、それぞれの設定値がどのファイルで設定されいるものなのか調べることができます。

```console
$ git config --list --show-origin
...
file:/Users/maku/.gitconfig  user.name=maku77
file:/Users/maku/.gitconfig  user.email=xxx@gmail.com
file:/Users/maku/.gitconfig  core.editor=vim
file:/Users/maku/.gitconfig  push.default=simple
file:.git/config  core.repositoryformatversion=0
file:.git/config  core.filemode=true
file:.git/config  core.bare=false
file:.git/config  core.logallrefupdates=true
file:.git/config  core.ignorecase=true
file:.git/config  core.precomposeunicode=true
file:.git/config  remote.origin.url=https://github.com/maku77/maku77.github.io.git
file:.git/config  remote.origin.fetch=+refs/heads/*:refs/remotes/origin/*
file:.git/config  branch.master.remote=origin
file:.git/config  branch.master.merge=refs/heads/master
```

この例の場合、`/Users/maku/.gitconfig` で始まるものが global スコープ、`.git/config` で始まるものが local スコープで設定されていることが分かります。
ここでは、`--list` オプションですべての設定値を列挙していますが、次のように個別の設定値に関して調べることもできます。

```console
$ git config --show-origin user.name
file:/Users/maku/.gitconfig  user.name=maku77
```

