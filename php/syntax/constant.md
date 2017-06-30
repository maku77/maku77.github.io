---
title: 定数を定義する
created: 2012-01-15
---

PHP 定数を定義するには、`define()` を使用します。
`define()` で定数を定義するときは、定数名をクォーテーションマークで囲む必要があります。

~~~ php
define('MAX_NUM', 100);
define('DB_PATH', 'sample.db');

echo MAX_NUM;  // 100
echo DB_PATH;  // 'sample.db'
~~~

ある名前の定数が定義されているかどうかを知らべるには `defined()` を使用します。

~~~ php
define('MAX_NUM', 100);

if (defined('MAX_NUM')) {
    print 'MAX_NUM = ' . MAX_NUM . "\n";
} else {
    print "Not defined\n";
}
~~~

