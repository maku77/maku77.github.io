---
title: "現在の include_path 設定を調べる"
date: "2012-09-23"
---

方法1: php コマンドで調べる
----

~~~
$ php -i | grep 'include_path'
include_path => .:/Users/maku/pear/share/pear => .:/Users/maku/pear/share/pear
~~~

方法2: get_include_path() の戻り値で調べる
----

~~~
$ php -r 'echo get_include_path();'
.:/Users/maku/pear/share/pear
~~~

