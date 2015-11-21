---
title: Gem モジュールのインストール先を調べる
created: 2015-11-21
---

`gem install` コマンドで Gem モジュールをインストールしたときに、どのディレクトリにモジュールがインストールされるかを調べるには以下のようにします。

```
$ gem environment gemdir
/Users/maku/.rvm/gems/ruby-2.2.1
```

`gem environment` コマンドは、RubyGems 関連のいろいろな情報を調べることができます。
一度パラメータなしで実行してみるとよいでしょう。

