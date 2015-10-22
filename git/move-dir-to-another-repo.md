---
title: リポジトリ内のディレクトリを別リポジトリに切り出す
created: 2015-10-22
layout: git
---

やりたいこと
====

例えば、

* https://github.com/maku77/old/aaa

というディレクトリ内のファイルを、

* https://github.com/maku77/new

という新しいリポジトリで管理したくなった場合の移行方法です。

手順
====

ここでは GitHub を使用することとし、まずは、新しい空のリポジトリ `new` を GitHub 上で作成しておいてください。
下記のように、古いリポジトリの内容を書き換えて、新しいリポジトリとして扱う方法をとるので、**必ず古いリポジトリのバックアップ** (`git clone`) をしてから作業してください。

```
### 作業用にリポジトリ old を取得
$ git clone --mirror https://github.com/maku77/old.git

### リポジトリ old の階層構造を書き換えて aaa 以下のファイルをルートに
$ cd old.git
$ git filter-branch -f --subdirectory-filter aaa -- --all

### 新しいリポジトリ new として push
$ git remote remove origin
$ git remote add origin https://github.com/maku77/new.git
$ git push --all  （全ブランチを push）
$ git push --tags （全タグを push）
```

新しいリポジトリへ push が完了したら、clone して確認してみます。

```
$ git clone https://github.com/maku77/new.git
```

必要があれば、古いリポジトリ側から、対象ディレクトリに関するコミット履歴を削除します。

```
$ git clone https://github.com/maku77/old.git
$ cd old
$ git filter-branch --force --index-filter "git rm -rf --ignore-unmatch aaa/" --prune-empty -- --all
$ git push --force
```

`.git-rewrite': Directory not empty` などのエラーが出た場合は、もう一度実行すると成功したりします。


参考サイト
====
* https://git-scm.com/book/en/v2/Git-Tools-Rewriting-History

