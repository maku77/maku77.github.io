---
title: 名前を使って変数にアクセスする（可変変数）
created: 2012-12-24
---

可変変数
----

変数名の格納されている変数を `$$` を付けて参照すると、その名前の変数にアクセスすることができます。
保持している文字列の値によって実際に参照する変数が変わるため、この仕組みを**可変変数**と呼びます。

~~~ php
<?php
$val = 'Hello';
$name = 'val';

echo $name . PHP_EOL;   // => 'val'
echo $$name . PHP_EOL;  // => 'Hello'
~~~

ちなみに、定数名を使って定数にアクセスするには、`constant()` 関数を使用する必要があります。

~~~ php
<?php
define('CONST_INT', 100);

echo CONST_INT . PHP_EOL;              // => 100
echo constant('CONST_INT') . PHP_EOL;  // => 100
~~~


参考
----

- [グローバル定数、クラス定数を定義する](./constant.html)

