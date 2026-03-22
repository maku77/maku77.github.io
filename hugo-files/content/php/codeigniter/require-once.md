---
title: "PHPメモ: CodeIgniter - コントローラやライブラリの中から通常のクラスを require_once する"
url: "/p/uqjjvpr/"
date: "2012-09-19"
tags: ["php"]
aliases: [/php/codeigniter/require-once.html]
---

CodeIgniter では、関数を提供するヘルパーや、クラスを提供するライブラリを作成しながら開発を進めるのですが、これらは独自の方法で読み込み、参照するような仕組みになっています。

```php
$this->load->library('mylib');
$this->load->helper('myhelper');
```

上記のような、CodeIgniter 独自のロード方法ではなく、単純に `require_once()` を使って既存のクラスファイルを読み込むこともできます。

```php
<?php
require_once APPPATH . 'classes/MyClass.php';
```

上記のように、`APPPATH` を使用してパスを指定しておけば、`application` ディレクトリからの相対パスで PHP ファイルをロードできます。
ここでは、以下のようなパスに配置した `MyClass.php` をロードすることを想定しています（`classes` ディレクトリは新しく作ってます）。

```
application/
    +-- classes/
            +-- MyClass.php
```

