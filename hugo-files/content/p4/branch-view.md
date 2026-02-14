---
title: "Perforceメモ: ブランチ仕様でブランチビューを定義して integrate 作業を簡単にする"
url: "p/peju2fi/"
date: "2005-07-15"
tags: ["perforce"]
aliases: ["/p4/branch-view.html"]
---

ブランチ仕様は次のコマンドで作成できます（ブランチを作るコマンドではないところが分かりにくい・・・）。

```console
$ p4 branch <BranchSpecName>
```

ブランチ仕様のビューに、

```
View:
    //depot/A/...  //depot/B/...
```

と書いておくと、

```console
$ p4 integrate //depot/A/... //depot/B/...
```

というコマンドを次のように実行できるようになります。

```console
$ p4 integrate -b <BranchSpecName>
```

逆方向の反映、つまり、

```console
$ p4 integrate //depot/B/... //depot/A/...
```

というコマンドは `-r` オプションを付けて次のように実行できます。

```console
$ p4 integrate -r -b <BranchSpecName>
```

