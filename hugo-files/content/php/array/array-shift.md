---
title: "PHPメモ: 配列の先頭要素を取り出す (array_shift)"
url: "/p/eyrmibj/"
date: "2012-10-08"
tags: ["php"]
aliases: [/php/array/array-shift.html]
---

配列の先頭要素を取り出し、その要素を配列から削除するには `array_shift` 関数を使用します。

```php
$arr = array(10, 20, 30);
$top = array_shift($arr);  // $arr は [20, 30] になる
```

これを利用することで、配列を先入れ先出しリスト (FIFO: First In, First Out) として扱うことができます。

