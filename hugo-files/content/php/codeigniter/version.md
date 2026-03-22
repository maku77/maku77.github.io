---
title: "PHPメモ: CodeIgniter - 使用中の CodeIgniter のバージョンを調べる"
url: "/p/8p2pmfz/"
date: "2012-10-04"
tags: ["php"]
aliases: [/php/codeigniter/version.html]
---

CodeIgniter のバージョンは、`system/core/CodeIgniter.php` の中で以下のように定義されています。

```php
define('CI_VERSION', '2.1.0');
```

この値は、コントローラなどから、以下のように呼び出せば表示できます。

```php
echo CI_VERSION;
```

ビューの中からも、以下のような感じで CodeIgniter へのリンクをバージョン付きで表示できます。

```html
<footer>
Powered by <a href="https://codeigniter.com">CodeIgniter <?php echo CI_VERSION ?></a>
</footer>
```

