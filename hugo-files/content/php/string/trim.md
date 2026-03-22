---
title: "PHPメモ: 文字列の先頭と末尾のスペースを削除する (trim)"
url: "/p/p8sfrhr/"
date: "2012-01-15"
tags: ["php"]
aliases: [/php/string/trim.html]
---

文字列の前後にある余計な空白文字を削除するには次のようにします。

```php
$s = '  ABC  ';
$s = trim($s);  // => 'ABC'
```

