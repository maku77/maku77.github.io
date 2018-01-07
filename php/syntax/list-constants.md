---
title: 定義されている定数をすべて表示する (get_defined_constants)
date: "2012-12-24"
---

get_defined_constants
----

`get_defined_constants()` を使用すると、すべての定数の名前とその値を連想配列として取得することができます。
取得した値を `var_dump()` や `print_r()` で出力すれば、定数の全体像を確認することができます。

~~~ php
<?php
var_dump(get_defined_constants());
~~~

#### 実行結果

~~~
$ php sample.php
array(1746) {
  ["E_ERROR"]=>
  int(1)
  ["E_RECOVERABLE_ERROR"]=>
  int(4096)
  ["E_WARNING"]=>
  int(2)
  ["E_PARSE"]=>
  int(4)
  ...
~~~


参考リンク
----

- [PHP: get_defined_constants - Manual](http://php.net/manual/ja/function.get-defined-constants.php)

