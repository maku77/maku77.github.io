---
title: "git プロトコルではなく強制的に https プロトコルで git clone するようにする (url.xxx)"
url: "p/k84zg8x/"
date: "2017-08-22"
lastmod: "2024-04-11"
tags: ["Git"]
aliases: /git/settings/https-instead-of-git.html
---

プロキシ環境化で作業している場合は、git プロトコルによるフェッチを実行したときも、強制的に https プロトコルを使用してフェッチしたいことがあります。

```console
$ git clone git://github.com/xxx/yyy.git    # のようなコマンドの実行を、
$ git clone https://github.com/xxx/yyy.git  # というコマンドで実行したことにしたい
```

このようなときは、下記の設定を行っておくと、自動的に https プロトコル経由でのフェッチに置き換えてくれるようになります。

```console
$ git config --global url.https://github.com/.insteadOf git://github.com/
```

自分でフェッチ先の URL を `https://...` と指定できるようなケースではこのような設定は必要ないのですが、何らかのツールが内部で git プロトコルを使用する前提になっているときにはこの設定を活用できます。
例えば、Ruby の Bundler などでモジュールをインストールするときには、内部で勝手に git プロトコル経由でフェッチすることがあり、プロキシ環境下からアクセスできないことがあります。
このような場合は、上記のような設定で強制的に https 経由のアクセスに置き換えることで https プロキシ経由でアクセスできるようになります。

