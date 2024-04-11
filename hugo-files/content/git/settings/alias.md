---
title: "Git コマンドのエイリアスを作成する (alias.xxx)"
url: "p/a59xkpd/"
date: "2010-07-17"
lastmod: "2024-04-11"
tags: ["Git"]
aliases: /git/settings/alias.html
---

長くて覚えにくい Git コマンドや、頻繁に使用する Git コマンドには、独自のエイリアスを設定しておくと便利です。

例えば、`git commit` コマンドを `git ci` というコマンドとして実行できるようにするには、以下のようにエイリアスを登録します。

{{< code lang="console" title="ci というエイリアスを登録する" >}}
$ git config --global alias.ci "commit"
{{< /code >}}

通常、エイリアスはグローバル設定 (`--global`) として設定すると思いますが、そのリポジトリ専用のローカル設定 (`--local`) として設定するのもありです。

- 参考: [Git 設定のスコープ (`local`/`global`/`system`) を理解する](/p/af7q7n3/)

エイリアスの使いすぎは禁物ですが、例えば、Git サブモジュールを扱うためのコマンドは長くなりがちなので、次のような感じでエイリアスを設定しておくと良いかもしれません。

{{< code lang="console" title="Git サブモジュール用のエイリアス" >}}
$ git config --global alias.sclone 'clone --recurse-submodules'
$ git config --global alias.supdate 'submodule update --remote --recursive --merge'
$ git config --global alias.sdiff '!'"git diff && git submodule foreach 'git diff'"
$ git config --global alias.spush 'push --recurse-submodules=on-demand'
{{< /code >}}

上記の `sclone`、`supdate`、`sdiff`、`spush` エイリアスは、サブモジュールまでまとめて `clone`、`update`、`diff`、`push` するためのエイリアスです。

- 参考: [Git サブモジュールで別リポジトリの内容を組み込む (`git submodule`)](/p/dsctaq7/)

