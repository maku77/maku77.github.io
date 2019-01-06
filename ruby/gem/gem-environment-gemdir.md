---
title: "Gem パッケージのインストール先を調べる"
date: "2015-11-21"
---

Gem パッケージのインストール先ディレクトリを調べる
====
`gem install` コマンドで Gem をインストールしたときに、どのディレクトリにインストールされるかを調べるには以下のようにします。

```
$ gem environment gemdir
/Users/maku/.rvm/gems/ruby-2.2.1
```

`gem environment` コマンドは、RubyGems 関連のいろいろな情報を調べることができます。
一度パラメータなしで実行してみるとよいでしょう。


特定の Gem パッケージの場所を調べる
====
指定した Gem パッケージが実際にどこにインストールされているかを調べるには、`gem which` コマンドを使用します。
下記の例では、`test-unit` パッケージの実体がどこにあるかを調べています。

```
$ gem which test-unit
/Users/maku/.rvm/gems/ruby-2.2.1/gems/test-unit-3.1.5/lib/test-unit.rb
```

