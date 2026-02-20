---
title: "Perforceメモ: オープンされているファイルを一覧表示する (p4 opened)"
url: "p/pmi544x/"
date: "2006-08-21"
tags: ["perforce"]
aliases: ["/p4/list-opened-files.html"]
---

`p4 edit` コマンドなどによりオープンされているファイルを一覧表示するには、次のコマンドを実行します。

```console
$ p4 opened
```

他のユーザーが編集中のファイルを調べるには、`-a` オプションを付けて `p4 opened` コマンドを実行します。

```console
$ p4 opened -a
```

カレントディレクトリ以下のファイルに対して、他のユーザーが編集中のファイルを調べるには次のようにします。

```console
$ p4 opened -a ...
```
