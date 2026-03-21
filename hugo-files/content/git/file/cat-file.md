---
title: "Gitメモ: 特定バージョンのファイルの内容を確認する (git show, git cat-file -p)"
url: "p/32b2ttc/"
date: "2014-07-17"
tags: ["git"]
aliases: [/git/file/cat-file.html]
---

過去のコミット時点でのファイルの内容を確認したいときは、**`git show`** や **`git cat-file -p`** コマンドを使います。
どちらもファイルの中身を表示できますが、用途や動作が少し異なります。


`git show` でファイルの内容を確認する
----

**`git show`** は、日常的に使いやすい高レベルコマンドです。
`<コミットID>:<ファイルパス>` の形式で、特定のコミット時点のファイル内容を表示できます。

```console
$ git show 8de2fcd0:main.py
```

出力はページャ（`less` など）を通して表示されるため、長いファイルでもスクロールして確認できます。
コミット ID の代わりにブランチ名やタグ名を指定することもできます。

```console
$ git show main:main.py       # main ブランチの main.py を表示
$ git show HEAD~3:config.yml  # 3 つ前のコミットの config.yml を表示
```

また、`git show` にコミット ID だけを指定すると、そのコミットの情報と差分 (diff) を表示します。

```console
$ git show 8de2fcd0  # コミットの詳細と変更内容を表示
```


`git cat-file -p` でファイルの内容を確認する
----

**`git cat-file`** は、Git の内部オブジェクトを直接操作するための低レベルコマンド（plumbing コマンド）です。
`-p` オプションを付けると、オブジェクトの内容を整形して表示します。

```console
$ git cat-file -p 8de2fcd0:main.py
```

`git show` とは異なり、ページャを通さず標準出力にそのまま出力されるため、スクリプトやパイプラインでの利用に適しています。

```console
$ git cat-file -p HEAD:data.csv | grep "error"  # パイプで別コマンドに渡す
$ git cat-file -p v1.0:main.py > old_main.py    # ファイルに書き出す
```

`-t` オプションを使うと、オブジェクトの種類 (blob, tree, commit, tag) を確認できます。

```console
$ git cat-file -t HEAD:main.py
blob
```

