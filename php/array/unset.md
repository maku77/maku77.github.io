---
title: "連想配列の要素を削除する (unset)"
date: "2012-01-25"
---

`unset` 関数を使用すると、連想配列から、指定したキーに対応する要素を削除することができます。

~~~
$arr['key'] = 100;
print count($arr);  // => 1

unset($arr['key']);
print count($arr);  // => 0
~~~

