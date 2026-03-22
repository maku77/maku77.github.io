---
title: "PHPメモ: Web サイトへのアクセス時にリダイレクト要求を返す"
url: "/p/z8y9395/"
date: "2012-08-31"
tags: ["php"]
aliases: [/php/web/redirect.html]
---

Web サイト上のあるページへのアクセスがあったときに、同じディレクトリにある `sample.php` にリダイレクトするには下記のようにします。

```php
<?php
header('Location: ./sample.php');
```

あるいは、HTTP モジュールを使って下記のように記述することもできます。

```php
<?php
require_once 'HTTP.php';
HTTP::redirect("./sample.php");
```

どちらの場合も、HTTP レスポンスのヘッダ情報として返すため、リダイレクト時には上記以外の出力をしてはいけません。

