---
title: RubyGems とは
created: 2015-11-22
---

RubyGems とは
====
RubyGems は、Ruby の世界で標準となっている**パッケージ管理システム**です。
**Gem** と呼ばれる単位のパッケージを、依存関係を自動的に解決しながらインストールすることができます。
多くの Gem パッケージが、[RubyGems.org](https://rubygems.org/) のリポジトリで管理されており、**gem** コマンドを使用して、検索やインストールを簡単に行うことができます。
RubyGems の詳しい説明は、下記の RubyGems ガイドでひととおり調べることができます。

* 参考: [RubyGems Guides](http://guides.rubygems.org/)

RubyGems の興味深いところは、プログラムから使用するライブラリだけではなく、端末から実行可能なコマンドをインストールする機能があるところです。
例えば、下記のように `rake` という Gem パッケージをインストールすると、プログラムから利用する API がインストールされるだけでなく、`rake` というコマンドも実行できるようになります。

```
$ gem install rake
```


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

