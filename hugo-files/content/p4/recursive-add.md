---
title: "Perforceメモ: ディレクトリ内のファイルを再帰的に p4 add する"
url: "p/o3bxq8u/"
date: "2006-06-16"
tags: ["perforce"]
aliases: ["/p4/recursive-add.html"]
---

（Linux の）`find` コマンドと `p4 add` を組み合わせて実行することで、ディレクトリ内のファイルをまとめて Perforce 管理下に置くことができます。

```console
$ find . -type f -print | p4 -x - add
```

{{< code title="応用例: Subversion 用のファイル (.svn) を除いたものを `p4 add` でまとめて追加" >}}
$ find . -type f | grep -v '.svn' | p4 -x - add
{{< /code >}}
