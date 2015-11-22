---
title: RubyGems とは
created: 2015-11-22
---

RubyGems とは
====
RubyGems は、Ruby の世界で標準となっている**パッケージ管理システム**です。
[RubyGems.org](https://rubygems.org/) のリポジトリで多くのパッケージが公開されており、Ruby の処理系をインストールすると同時に使えるようになる **gem** コマンドを使用することで、パッケージの検索やインストールを簡単に行うことができます。

RubyGems の使い方などは、下記の RubyGems ガイドでひととおり調べることができます。

* 参考: [RubyGems Guides](http://guides.rubygems.org/)


gem コマンドの使用例
====

Gem の検索
----
```sh
$ gem search travis      # travis という名前を含む Gem を検索
$ gem search ^rails      # rails という名前で始まる Gem を検索
$ gem search ^rails$     # rails という名前に完全一致する Gem を検索
$ gem search ^rails$ -d  # 詳細情報付きで表示
```

インストール済みの Gem を調べる
----
```sh
$ gem list       # インストール済みの Gem をすべて表示
$ gem list http  # インストール済みの Gem のうち http という名前を含むものを検索
```

Gem のインストール
----
```sh
$ gem install rvm           # rvm という Gem をインストール
$ gem install rvm --no-doc  # ドキュメントの生成はしない
```

Gem のアンインストール
---
```sh
$ gem uninstall rvm  # rvm という Gem をアンインストール
$ gem clean          # インストール済み Gem のうち最新バージョンを残してアンインストール
```

ドキュメント用 Web サーバを起動
---
```sh
$ gem server
```

上記を実行してから http://localhost:8808 にアクセスすれば、インストール済みの Gem のドキュメントを一覧で見ることができます。

