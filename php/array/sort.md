---
title: 配列をソートする (sort, ksort, asort)
date: "2012-01-30"
---

数値配列のソート (sort, rsort)
----

~~~ php
$arr = array(3, 5, 1, 4, 2);
sort($arr);   # 昇順ソート => 1,2,3,4,5
rsort($arr);  # 降順ソート => 5,4,3,2,1
~~~

`sort()`, `rsort()` は、パラメータで渡した配列そのものを変更することに注意してください。


連想配列のソート（キーによるソート）(ksort, krsort)
----

~~~ php
$arr = array('mango'=>300, 'apple'=>500, 'banana'=>100);
ksort($arr);   # 昇順ソート => apple:500, banana:100, mango:300
krsort($arr);  # 降順ソート => mango:300, banana:100, apple:500
~~~

`ksort()`, `krsort()` は、パラメータで渡した配列そのものを変更することに注意してください。


連想配列のソート（値によるソート）(asort, arsort)
----

~~~ php
$arr = array('mango'=>300, 'apple'=>500, 'banana'=>100);
asort($arr);   # 昇順ソート => banana:100, mango:300, apple:500
arsort($arr);  # 降順ソート => apple:500, mango:300, banana:100
~~~

`asort()`, `arsort()` は、パラメータで渡した配列そのものを変更することに注意してください。


参考
----

- [配列を逆順にする (array_reverse)](./array-reverse.html)

