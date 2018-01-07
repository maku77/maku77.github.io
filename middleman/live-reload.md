---
title: LiveReload 機能を使って Web ブラウザを自動でリロードする
date: "2015-11-04"
---

LiveReload 機能とは
====
Middleman サーバの **LiveReload** 機能を有効にしておくと、サイトの記事ファイル（`source` ディレクトリ以下のファイル）を修正して保存したタイミングで、自動的に Web ブラウザをリロードしてくれます。
PC のディスプレイ上に、テキストエディタと Web ブラウザを同時に表示しておけば、効率的に Web サイトを構築していくことができます。

LiveReload 機能を有効にする
====
Middleman サーバの LiveReload 機能を有効にするには、`config.rb` に下記の行を追加します。

#### config.rb
```ruby
activate :livereload
```

`config.rb` を修正したら、Middleman サーバを起動（あるいは再起動）します。

```
$ bundle exec middleman
```

トラブルシューティング: middleman-livereload がインストールされていない場合
----
LiveReload 機能は **middleman-livereload** という gem モジュールにより提供されています。
このモジュールがシステムにインストールされているかどうかは、`gem list` コマンドを使用して、下記のように確認できます。

```
$ gem list middleman-livereload

*** LOCAL GEMS ***

middleman-livereload (3.1.1)
```

この例では、middleman-livereload のバージョン 3.1.1 がインストールされていることが分かります。
インストールされていない場合は、プロジェクトのルートにある `Gemfile` に下記のように追加して、Middleman サーバを再起動すれば、自動的にインストールされます（最新の Middleman 3.4 であれば、最初から記述されているはずです）。

#### Gemfile
```
gem "middleman-livereload", "~> 3.1.0"
```

