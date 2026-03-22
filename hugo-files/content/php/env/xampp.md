---
title: "PHPメモ: XAMPP で PHP のテスト環境を作成する"
url: "/p/rgn3jxx/"
date: "2012-01-12"
tags: ["php"]
aliases: [/php/env/xampp.html]
---

XAMPP とは
----

XAMPP というパッケージを使用すると、Apache、MySQL、PHP、Perl、JSP (Tomcat) などの実行環境をまとめてインストールすることができます。
PHP のテスト環境構築にはもってこいです。
Windows, Linux, Mac OS X, Solaris すべてに対応しています。


XAMPP のインストール
----

- https://www.apachefriends.org/

から XAMPP のパッケージをダウンロードしてインストールするだけです。
お手軽！

例えば、Windows 環境で `C:\xampp` にインストールすると、以下のようなパスにそれぞれの実行環境がまとめてインストールされます。

```
C:\xampp\apache
C:\xampp\mysql
C:\xampp\perl
C:\xampp\php
C:\xampp\phpMyAdmin
C:\xampp\tomcat
```


XAMPP を動かしてみる
----

XAMPP Control Panel を起動して、Apache の Start ボタンを押すとサーバが起動します。

```
http://localhost/xampp/
```

にアクセスして、XAMPP の設定画面が表示されれば、正しく動作しています。


PHP が動作しているか確認する
----

テスト用の PHP ファイルとして、下記のようなファイルを作成してください。

{{< code lang="php" title="C:\xampp\htdocs\myweb\index.php" >}}
<?
  phpinfo();
{{< /code >}}

`http://localhost/myweb/` にアクセスし、PHP の情報が表示されれば、PHP 環境は正しく動作しています。

