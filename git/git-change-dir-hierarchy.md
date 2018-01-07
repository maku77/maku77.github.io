---
title: Git のコミット履歴を書き換えてディレクトリ階層を変更する
date: "2015-06-29"
---

例えば、Git で下記のようなディレクトリのプロジェクトを管理しているとします。

```
+ root
  + proj
    +-- file1
    +-- file2
    +-- file3
```

この階層構造をひとつずつ上げて、下記のようなディレクトリ構成にしたいとします。

```
+ proj
  +-- file1
  +-- file2
  +-- file3
```

`git filter-branch` コマンドを使用すると、すべてのコミット履歴を書き換えて、もともとプロジェクト全体がこのような構成になっていたとみなすことができます。

```sh
$ git filter-branch -f --subdirectory-filter root -- --all
```

`git filter-branch` はコミット履歴を書き換えるので、実行には結構時間がかかります。
このコマンドは非常に強力な反面、ディレクトリ構成の互換性が断たれてしまうことに注意してください。
リポジトリ内のブランチの整合性は保たれますが、この変更を既存のリモートリポジトリに push したりすると、内部の整合性が崩れてしまいます。
そのため、`git filter-branch` は、[リポジトリの移行](git-relocate-repository.html)時のときなどに限定して実行すべきです。
現在のリポジトリの内容を書き換えてしまうため、試すときは、テスト用に git clone したプロジェクト内で実行するようにしましょう。


#### 例: src.git の階層構造を変えて dst.git として push
下記は、GitHub 上で空のリポジトリ dst.git を作成済みだと仮定しています。

```sh
### 元になるリポジトリを clone して階層構造を変更する
$ git clone --mirror https://github.com/user/src.git
$ cd src.git
$ git filter-branch -f --subdirectory-filter root -- --all

### 新しいリポジトリとして push
$ git remote remove origin
$ git remote add origin https://github.com/user/dst.git
$ git push --all  （全ブランチを push）
$ git push --tags （全タグを push）
```

#### 参考: git filter-branch コマンドのヘルプ

* http://git-scm.com/docs/git-filter-branch
