---
title: "Gitメモ: リポジトリ内のディレクトリを別リポジトリに切り出す"
url: "p/vaqzwih/"
date: "2015-10-22"
tags: ["git"]
aliases: [/git/move-dir-to-another-repo.html]
---

やりたいこと
----

リポジトリの一部のディレクトリを、コミット履歴ごと別のリポジトリに切り出したいケースがあります。
例えば、以下のようなリポジトリ構成で、`aaa` ディレクトリを独立したリポジトリに分離する場合です。

```
old リポジトリ（移行元）
├── aaa/  ← これを別リポジトリとして切り出したい
├── bbb/
└── README.md
```

切り出し後は、`aaa` ディレクトリの中身がルートになる新しいリポジトリ `new` ができます。
`aaa` 内のファイルに関するコミット履歴もそのまま引き継がれます。


手順
----

### 前提条件

- GitHub 上に新しい空のリポジトリ `new` を作成済み
- 古いリポジトリのバックアップ (`git clone`) を取得済み

この手順では `git filter-branch` を使って古いリポジトリの履歴を書き換えるため、**作業前に必ず古いリポジトリのバックアップを取ってください**。

### ステップ 1: 古いリポジトリをミラークローンする

```console
$ git clone --mirror https://github.com/maku77/old.git
$ cd old.git
```

`--mirror` を付けることで、すべてのブランチ・タグを含む完全なコピーを取得します（ただし、作業ディレクトリの存在しないベアリポジトリとなります）。

### ステップ 2: 特定ディレクトリをルートに書き換える

```console
$ git filter-branch -f --subdirectory-filter aaa -- --all
```

`--subdirectory-filter aaa` は、`aaa` ディレクトリ内のファイルだけを残し、それをリポジトリのルートに移動させるオプションです。
`aaa` 以外のファイルに関するコミット履歴は自動的に削除されます。
末尾の `-- --all` は、すべてのブランチに対してこの操作を適用することを意味します。

### ステップ 3: 新しいリポジトリとして push する

リモートの向き先を新しいリポジトリに変更して push します。

```console
$ git remote remove origin
$ git remote add origin https://github.com/maku77/new.git
$ git push --all   # すべてのブランチを push
$ git push --tags  # すべてのタグを push
```

### ステップ 4: 新しいリポジトリを確認する

```console
$ git clone https://github.com/maku77/new.git
$ cd new
$ git log --oneline  # コミット履歴が引き継がれていることを確認
```


古いリポジトリから対象ディレクトリを削除する（任意）
----

移行が完了した後、古いリポジトリ側から `aaa` ディレクトリとその履歴を完全に削除したい場合は、以下のようにします。

```console
$ git clone https://github.com/maku77/old.git
$ cd old
$ git filter-branch --force --index-filter "git rm -rf --ignore-unmatch aaa/" --prune-empty -- --all
$ git push --force
```

- `--index-filter` は、各コミットのインデックス（ステージングエリア）を書き換えるオプションです。
- `--prune-empty` は、ファイルの変更がなくなった空のコミットを自動的に削除します。
- `--force` を付けて push することで、書き換えた履歴をリモートに反映します。

`.git-rewrite': Directory not empty` というエラーが出た場合は、前回の `filter-branch` の作業ファイルが残っています。
もう一度同じコマンドを実行すると成功することがあります。


参考サイト
----

- [Git Tools - Rewriting History](https://git-scm.com/book/en/v2/Git-Tools-Rewriting-History)

