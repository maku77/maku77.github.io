---
title: "Gitメモ: 変更の一部だけをコミットする (git add -p)"
url: "p/a6776js/"
date: "2010-07-17"
tags: ["git"]
aliases: [/git/commit/commit-by-hunk.html]
---

Git では、変更をファイル単位ではなく、ひとまとまりの変更**「ハンク (Hunk)」**の単位で認識するので、ファイルの一部分の変更だけをコミットすることができます。

ハンクごとにコミットする対象を指定（ステージング）したい場合は、`git add` に `-p` オプションを付けて実行し、**パッチモード**という対話モードを起動します。

```console
$ git add -p Hoge.txt
```

すると、変更部分ごとに `diff` が順番に表示されるので、実際にそのハンクをステージするかを `y/n` で選んでいきます。
選択し終わったら、あとは通常通りコミットするだけです。

```console
$ git commit -m "Your comment"
```

ファイルの一部だけステージされている状況で `git status` コマンドを実行すると、そのファイルは **"Changes to be committed"** にも属しているし、**"Changed but not updated"** にも属しているというように表示されます。

