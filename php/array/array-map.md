---
title: "配列の各要素に対して同じ処理を行う (array_map)"
date: "2012-10-13"
---

array_map 関数
----

~~~
array array_map ( callable $callback , array $arr1 [, array $... ] )
~~~

`array_map` 関数を使うと、配列の各要素に対して、ある関数を適用した結果の配列を生成することができます。


サンプルコード
----

#### 例: 配列の各要素を二乗する

~~~ php
<?php
function square($num) {
    return $num * $num;
}

$arr = array(1, 2, 3, 4, 5);
$arr = array_map('square', $arr);
print_r($arr);
~~~

#### 実行結果

~~~
Array
(
    [0] => 1
    [1] => 4
    [2] => 9
    [3] => 16
    [4] => 25
)
~~~

