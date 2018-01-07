---
title: 配列を逆順にする (array_reverse)
date: "2012-12-24"
---

array_reverse の使い方
----

配列の要素を逆順に並び替えるには、`array_reverse` 関数を使用します。

~~~ php
$arr = array(2, 1, 3);
$arr = array_reverse($arr);  // => [3, 1, 2]
~~~

`array_reverse` は渡した配列の内容を変更せず、逆順にした配列を戻り値として返します。
引数で渡した配列自体を変更したい場合は、上記のように、戻り値を自分自身に代入する必要があります。

`array_reverse` は、要素を逆順に並び替えるだけで、ソートをしているわけではないことに注意してください。昇順ソートや降順ソートを行いたい場合は、`sort` 関数や `ksort` 関数を使用します。


参考
----

- [配列をソートする (sort, ksort, asort)](./sort.html)

