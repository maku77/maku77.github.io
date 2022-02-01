---
title: "Git やり直し: 最近のコミットを歴史から抹消する"
date: "2018-08-31"
---

最近実行したコミットを、完全にコミットログ上から削除してしまいたい場合は、`git rebase` を下記のような感じで実行します。

```console
$ git rebase -i HEAD~5  # 最近の５つのコミットから選んで削除したいとき
$ git rebase -i 40facd  # 指定したコミットから最新までの中から選んで削除したいとき
```

上記のように実行すると、下記のようにコミットのリストがエディタ上に表示されます。

```
pick 26b81e6 How to show dependencies of subprojects
pick 86f2132 Correct ffmpeg options
pick 892197c Vim articles
pick 492c84c How to use Sass in Hugo
pick fa18ad8 Correct Markdown format

# Rebase d67f4e7..fa18ad8 onto d67f4e7 (5 command(s))
#
# Commands:
# p, pick = use commit
# r, reword = use commit, but edit the commit message
# e, edit = use commit, but stop for amending
# s, squash = use commit, but meld into previous commit
# f, fixup = like "squash", but discard this commit's log message
# x, exec = run command (the rest of the line) using shell
# d, drop = remove commit
#
# These lines can be re-ordered; they are executed from top to bottom.
#
# If you remove a line here THAT COMMIT WILL BE LOST.
#
# However, if you remove everything, the rebase will be aborted.
#
# Note that empty commits are commented out
```

歴史から削除したいコミットの行頭に書かれている、`pick` という部分を、`drop` に書き換えて保存すれば、それらのコミットがなかったことになります。

```
pick 26b81e6 How to show dependencies of subprojects
drop 86f2132 Correct ffmpeg options  ★履歴から消したいコミット
pick 892197c Vim articles
drop 492c84c How to use Sass in Hugo ★履歴から消したいコミット
pick fa18ad8 Correct Markdown format
```

この操作は、`git push` で公開したコミットに対して実行してはいけません。
すでにリポジトリを clone しているユーザが大混乱することになります。
どうしても共有リポジトリ側に `git push` したい場合は、次のように `-f` オプションをつけて強制的に push する必要があります（付けないとエラーになる）。

```console
$ git push -f
```

### コラム: 昔のバージョンの Git の場合

古いバージョンの Git では、`git rebase -i` コマンドを実行したときに表示されるコミットリストの先頭に、`pick` や `drop` などの選択肢は表示されていませんでした。
単純に行ごと削除することで、そのコミットを履歴から削除することができました。

