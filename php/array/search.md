---
title: 配列要素を検索する (array_search)
date: "2012-01-25"
---

`array_search()` を使うと、指定した値が、配列内のどの位置に出現するかを調べることができます。
値が見つかった場合はそのキー（通常の配列であれば数値インデックス）を返し、見つからなかった場合は `FALSE` を返します。

~~~ php
$arr = array(5, 3, 1, 4, 2);
$index = array_search(1, $arr);  // => 2
$index = array_search(7, $arr);  // => FALSE
~~~

