---
title: "Middlemanメモ: デプロイ用に Web サイトをビルドする"
url: "p/u49zjwf/"
date: "2015-11-04"
tags: ["middleman"]
aliases: /middleman/build-project.html
---

Middleman で Web サイトをビルドするには、`build` コマンドを使用します。
下記のように実行すると、`source` ディレクトリ内のファイルをもとにして、`build` ディレクトリにアップロード用のファイル群が生成されます。

```console
$ bundle exec middleman build
      create  build/stylesheets/all.css
      create  build/stylesheets/normalize.css
      create  build/images/middleman.png
      create  build/images/background.png
      create  build/javascripts/all.js
      create  build/index.html
      create  build/hello.html
```
