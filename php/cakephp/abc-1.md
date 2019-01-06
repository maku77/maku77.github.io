---
title: "CakePHP 入門 (1) セットアップ"
date: "2012-04-29"
---

ここでは、CakePHP をインストールし、トップページを表示できるようにするまでの手順を示します（確認したバージョン: CakePHP 2.1.1、PHP 5.3.8）。


CakePHP のダウンロード
----

まずは、下記のサイトから CakePHP のアーカイブをダウンロードします。

- [http://cakephp.jp/](http://cakephp.jp/)

Web サーバのドキュメントルートに、上記を展開したファイルを置き、`http://localhost/` にアクセスしてください。
いろいろ警告が表示されるので、その指示に従って以下のような感じでセットアップしていきます。


app/tmp ディレクトリに書き込み権限をつける
----

~~~
$ chmod -R a+w app/tmp
~~~


app/Config/core.php 内のセキュリティ定数の設定
----

`app/Config/core.php` 内の `Security.salt` の値を 40 文字分の適当な文字列に変更します。

~~~
Configure::write('Security.salt', 'DYhG93b0qyJfIxfs2guVoUubWwvniR2G0FgaC9mi');
↓
Configure::write('Security.salt', '適当な文字列');
~~~

下記のような Ruby スクリプトを実行すれば、ランダムな文字列を作成することができます。

~~~ ruby
a = ('0'..'9').to_a + ('a'..'z').to_a + ('A'..'Z').to_a
40.times { print a[rand(a.size)] }
~~~

そのすぐ下の、`Security.cipherSeed` の値も変更します。
ここは、適当な 29 文字分の数値に変更します。

~~~
Configure::write('Security.cipherSeed', '76859309657453542496749683645');
↓
Configure::write('Security.cipherSeed', '適当な数値列');
~~~

下記のような Ruby スクリプトでランダムな数値文字列を作成できます。

~~~ ruby
29.times { print rand(10) }
~~~


mod_rewrite を有効にする
----

rewriting が有効になっていないという警告が出るようであれば、指示に従って修正します。

1. httpd.conf で mod_rewrite が有効になっているか確認
2. httpd.conf で AllowOverride の設定が正しいか確認
3. 以下の各ディレクトリに正しい .htaccess が存在するか確認
  - /.htaccess
  - /app/.htaccess
  - /app/webroot/.htaccess

CakePHP 2.1.1 の zip アーカイブには、なぜか最上位の `.htaccess` が入っていなかったので、以下のように作成しました。

~~~
<IfModule mod_rewrite.c>
  RewriteEngine on
  RewriteRule    ^$ app/webroot/    [L]
  RewriteRule    (.*) app/webroot/$1 [L]
</IfModule>
~~~

これで、ほとんど警告を出さずに CakePHP のトップページを表示できるようになったはずです。
データベースに関する警告は、作るアプリケーションによってデータベース設定が変わってくるので、ここではひとまず置いておきます。


CakePHP 入門記事一覧
----

- CakePHP 入門 (1) セットアップ
- [CakePHP 入門 (2) データベースの設定](./abc-2.html)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](./abc-3.html)
- [CakePHP 入門 (4) Controller、View、Model を作成する](./abc-4.html)
- [CakePHP 入門 (5) 個別のレコードを表示する](./abc-5.html)
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](./abc-6.html)
- [CakePHP 入門 (7) レコードを追加できるようにする](./abc-7.html)
- [CakePHP 入門 (8) レコードを編集できるようにする](./abc-8.html)
- [CakePHP 入門 (9) レコードを削除できるようにする](./abc-9.html)

