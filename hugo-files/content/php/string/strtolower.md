---
title: "PHPメモ: 文字列を大文字、小文字に変換する (strtolower, strtoupper)"
url: "/p/gxrr8gi/"
date: "2012-01-15"
tags: ["php"]
aliases: [/php/string/strtolower.html]
---

文字列内のすべてのアルファベットを小文字、あるいは大文字に変換するには以下のようにします。

```php
$s = 'Hello World';
$lower = strtolower($s);  // 'hello world'
$upper = strtoupper($s);  // 'HELLO WORLD'
```

