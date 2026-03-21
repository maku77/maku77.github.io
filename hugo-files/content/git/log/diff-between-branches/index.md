---
title: "Gitメモ: Git でブランチ間の差分を調べる"
url: "p/fmarnds/"
date: "2010-11-16"
tags: ["git"]
aliases: [/git/log/diff-between-branches.html]
---

下記のようなブランチ構成になっているとします。

{{< image src="img-001.png" >}}

master ブランチには commit1, commit2, commit3, commit4 がコミットされており、topic ブランチは commit2 から分岐し、commit3' と commit4' がコミットされています。
topic ブランチにだけコミットされているものを調べるには以下のようにします（順番重要）。

```console
$ git log master..topic
```

図で表すと、下記の部分のログだけを表示することになります。

{{< image src="img-002.png" >}}

正確には、後ろに指定したコミットからたどれるコミットのログのみを表示しています。
トピックブランチを作って作業するスタイルを取っている場合は、このように差分チェックすれば、自分がトピックブランチに対してコミットした履歴のみ調べることができます。
逆に、master ブランチにだけコミットされているものを調べたいときは、指定順序を逆にします。

{{< image src="img-003.png" >}}

ブランチ名やコミット ID を省略すると、HEAD を指定したとみなされます。
つまり、現在 topic ブランチをチェックアウトしている場合は、下記のコマンドは同じ意味になります。

```console
$ git log master..HEAD
$ git log master..topic
$ git log master..
```

下記の 3 コマンドも同じ意味になります。

```console
$ git log topic..master
$ git log HEAD..master
$ git log ..master
```

master ブランチと topic ブランチのいずれかのブランチに含まれているコミットを調べたい場合は、ドット 2 つ (..) だったところをドット 3 つ (...) にします。
この場合は、ブランチの指定順序には意味がなくなります。

{{< image src="img-004.png" >}}

