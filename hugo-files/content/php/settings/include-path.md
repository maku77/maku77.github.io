---
title: "PHPメモ: 現在の include_path 設定を調べる"
url: "/p/nv7rmgs/"
date: "2012-09-23"
tags: ["php"]
aliases: [/php/settings/include-path.html]
---

方法1: php コマンドで調べる
----

```console
$ php -i | grep 'include_path'
include_path => .:/Users/maku/pear/share/pear => .:/Users/maku/pear/share/pear
```

方法2: get_include_path() の戻り値で調べる
----

```console
$ php -r 'echo get_include_path();'
.:/Users/maku/pear/share/pear
```

