---
title: "Perforceメモ: ディレクトリ内のすべてのファイルを別のディレクトリへ移動する (p4 move)"
url: "p/wkb4pzm/"
date: "2012-06-11"
tags: ["perforce"]
aliases: ["/p4/move-all-files.html"]
---

下記のように実行すると、`foo` ディレクトリ以下のすべてのファイルを `bar` ディレクトリに移動させることができます。

```console
$ p4 edit foo/...
$ p4 move foo/... bar/...
```

先に、`p4 edit` しておかないと以下のようなエラーが出るので注意してください。

```
foo/... file(s) not opened on this client.
```
