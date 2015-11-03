---
title: デプロイ用に Web サイトをビルドする
created: 2015-11-04
---

Middleman で Web サイトをビルドするには、`build` コマンドを使用します。
下記のように実行すると、`source` ディレクトリ内のファイルを元にして、`build` ディレクトリにアップロード用のファイル群が生成されます。

```
my_site$ bundle exec middleman build
      create  build/stylesheets/all.css
      create  build/stylesheets/normalize.css
      create  build/images/middleman.png
      create  build/images/background.png
      create  build/javascripts/all.js
      create  build/index.html
      create  build/hello.html
```

