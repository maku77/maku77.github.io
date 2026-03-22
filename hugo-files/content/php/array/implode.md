---
title: "PHPメモ: 配列要素を結合して１つの文字列にする (implode)"
url: "/p/7wifgp7/"
date: "2012-01-25"
tags: ["php"]
aliases: [/php/array/implode.html]
---

`implode` 関数を使用すると、配列内の要素を文字列として結合することができます。
このとき、各要素は第一引数で指定したセパレータを挟んで結合されます。

```
$arr = array('AAA', 'BBB', 'CCC');
$s = implode(', ', $arr);  // => 'AAA, BBB, CCC'
```

