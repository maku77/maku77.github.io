---
title: メソッドのパラメータにはタイプヒントを指定しよう
created: 2012-09-30
---

PHP のメソッドのパラメータには、**型情報を示すタイプヒント**を指定することができます（PHP5 から導入された **Type Hinting** という機能です）。
受け取るべきパラメータの型が決まっている場合は、必ず指定するようにしましょう。

~~~ php
<?php
function printArray(array $arr) {
    print_r($arr);
}

$a = array(1, 2, 3);
printArray($a);
printArray(100);  // Catchable exception !!!
~~~

以下のような型を、タイプヒントで指定できます。

* `array` -- 配列型
* `YourClass` -- 任意のクラス型、あるいはインタフェース
* `callable` -- `call_user_func()` でコールバック可能なもの (`is_callable() == TRUE`)

現状 (PHP5.3) では、`int` や `string` などのスカラ型は指定できないようです。

