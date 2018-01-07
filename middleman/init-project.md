---
title: Middleman による Web サイトの雛形の作成
date: "2015-11-03"
---

Middleman で Web サイトを新しく作成するには、`init` コマンドを実行します。
パラメータで指定した名前のディレクトリが新しく作成され、そこに雛形のファイルが作成されます。

#### 新しい Web サイト my_site の作成
```
$ middleman init my_site
      ...
      create  my_site/.gitignore
      create  my_site/config.rb
      create  my_site/source/index.html.erb
      create  my_site/source/layouts/layout.erb
      create  my_site/source/stylesheets
      create  my_site/source/stylesheets/all.css
      create  my_site/source/stylesheets/normalize.css
      create  my_site/source/javascripts
      create  my_site/source/javascripts/all.js
      create  my_site/source/images
      create  my_site/source/images/background.png
      create  my_site/source/images/middleman.png
```

デフォルトでは上記のように、stylesheets ディレクトリ、javascripts ディレクトリ、images ディレクトリなどが生成されますが、これらのディレクトリ構成は、下記のように設定ファイル `config.rb` で定義されているので、自由に変更することができます。

#### config.rb（抜粋）
```ruby
set :css_dir, 'stylesheets'
set :js_dir, 'javascripts'
set :images_dir, 'images'
```

