---
title: ヘルパーメソッドを使って HTML を作成する
created: 2015-12-07
---

Middleman で使用できるヘルパーメソッド
====

ERB ファイルの中で HTML タグを直接記述していく代わりに、**ヘルパーメソッド**を使用して HTML タグを出力することもできます。
下記に、代表的な使用方法を載せますが、より詳細な使用方法を調べる必要がある場合は[こちらのサイト](http://www.padrinorb.com/guides/application-helpers)を参照してください。

リンク（a 要素）
----

| 説明 | ヘルパーメソッドを使った記述方法 | 出力結果 |
| :--- | :------------------------------- | :------- |
| 外部リンク | `<%= link_to 'Example', 'http://example.com' %>` | `<a href="http://example.com">Examle</a>` |
| 内部リンク | `<%= link_to 'About', '/about.html' %>` | `<a href="/about.html">About</a>` |
| 相対リンク | `<%= link_to 'About', '/about.html', :relative => true %>` | `<a href="../about.html">About</a>` |
| 画像リンク | `<% link_to 'http://example.com' do %>`<br>`　<%= image_tag 'logo.png' %>`<br>`<% end %>` | `<a href="http://example.com">`<br>`　<img src="/images/logo.png" />`<br>`</a>` |
| クエリ付き | `<%= link_to '私のフォーム', '/form.html', :query => { :foo => 'bar' }, :fragment => 'deep' %>` | `<a href='/form.html?foo=bar#deep'>私のフォーム</a>` |

画像 (img 要素）
----

| 説明 | ヘルパーメソッドを使った記述方法 | 出力結果 |
| :--- | :------------------------------- | :------- |
| 基本 | `<%= image_tag 'logo.png' %>` | `<img src="logo.png" />` |
| クラス付き | `<%= image_tag 'logo.png', :class => 'demo' %>` | `<img class="demo" src="/images/logo.png" />` |

スタイルシート、favicon (link 要素）
----

| 説明 | ヘルパーメソッドを使った記述方法 | 出力結果 |
| :--- | :------------------------------- | :------- |
| 単一CSS | `<%= stylesheet_link_tag 'index' %>` | `<link href="/stylesheets/index.css" />` |
| 複数CSS | `<%= stylesheet_link_tag 'index', 'custom' %>` | `<link href="/stylesheets/index.css" />`<br>`<link href="/stylesheets/custom.css" />` |
| favicon | `<%= favicon_tag 'favicon.png' %>` | `<link href="/images/favicon.png" rel="icon" type="image/png" />` |

JavaScript（script タグ）
----

| 説明 | ヘルパーメソッドを使った記述方法 | 出力結果 |
| :--- | :------------------------------- | :------- |
| JavaScript | `<%= javascript_include_tag "all" %>` | `<script src="/js/all.js"></script>` |


ヘルパーメソッドを使うべき理由
====

ヘルパーメソッドなんて使わずに、HTML 要素をそのまま記述した方が簡単だと思うかもしれませんが、ヘルパーメソッドを使うことによる利点はちゃんとあります。

リソースディレクトリの名前変更時のリンク切れ防止
----

プロジェクトの設定ファイル (`config.rb`) には、下記のようなディレクトリ設定が記述されていると思います。

#### config.rb（一部抜粋）

```erb
set :css_dir, 'stylesheets'
set :js_dir, 'javascripts'
set :images_dir, 'images'
```

ERB ファイルの中で、これらのリソースへのパスを直接記述してしまうと、ディレクトリ設定を変更した場合に ERB ファイルを全て書き直さなければいけません。
ヘルパーメソッドを使用して、これらのリソースに関する HTML 要素を記述しておけば、`config.rb` を修正した場合も、正しいパスで HTML 要素を出力してくれます。

ディレクトリ・インデックス機能を使用した場合のリンク切れ防止
----

Middleman のディレクトリ・インデックス機能を使うと、URL の末尾の `.html` を省略した形で各ページにアクセスできるようになります。
例えば、`http://example.com/sample.html` は、`http://example.com/sample/` という形でアクセスできるようになります。
URL を HTML の a 要素を使用して直接記述してしまうと、ディレクトリ・インデックス機能を有効にした場合に、その URL を書き換える必要があります。
ヘルパーメソッドの `link_to` を使って a 要素を出力しておけば、URL を書き換える必要がありません。
例えば、下記のように ERB ファイル内に記述してあるとします。

```erb
<%= link_to 'About', '/about.html' %>
```

この出力結果は、ディレクトリ・インデックス機能が無効の場合と、有効の場合で、下記のように自動的に変化します。

```html
<a href='/about.html'>About</a>  （ディレクトリ・インデックス無効時）
<a href='/about/'>About</a>      （ディレクトリ・インデックス有効時）
```


