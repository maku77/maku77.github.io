---
title: XAMPP で PHP のテスト環境を作成する
created: 2012-01-12
---

XAMPP とは
----

XAMPP というパッケージを使用すると、Apache、MySQL、PHP、Perl、JSP (Tomcat) などの実行環境をまとめてインストールすることができます。
PHP のテスト環境構築にはもってこいです。
Windows, Linux, Mac OS X, Solaris すべてに対応しています。


XAMPP のインストール
----

- [http://www.apachefriends.org/](http://www.apachefriends.org/)

から XAMPP のパッケージをダウンロードしてインストールするだけです。
お手軽！

例えば、Windows 環境で `C:\xampp` にインストールすると、以下のようなパスにそれぞれの実行環境がまとめてインストールされます。

~~~
C:\xampp\apache
C:\xampp\mysql
C:\xampp\perl
C:\xampp\php
C:\xampp\phpMyAdmin
C:\xampp\tomcat
~~~


XAMPP を動かしてみる
----

XAMPP Control Panel を起動して、Apache の Start ボタンを押すとサーバが起動します。

~~~
http://localhost/xampp/
~~~

にアクセスして、XAMPP の設定画面が表示されれば、正しく動作しています。


PHP が動作しているか確認する
----

テスト用の PHP ファイルとして、下記のようなファイルを作成してください。

#### C:\xampp\htdocs\myweb\index.php

~~~ php
<?
  phpinfo();
~~~

`http://localhost/myweb/` アクセスし、PHP の情報が表示されれば、PHP 環境は正しく動作しています。


トラブルシューティング
----

Windows 7 で localhost にアクセスしたときに、"Redirect" とだけ画面に表示されてしまう場合は、IE のプロキシ設定が正しいか確認してください。
「ローカルアドレスにはプロキシサーバーを使用しない」の設定を有効にしておく必要があります。

- 参考: [Microsoft MSDN - http://localhost に接続できない](http://msdn.microsoft.com/ja-jp/library/ee251335%28v=bts.10%29.aspx)

