---
title: "関数のデフォルト引数を設定する"
date: "2012-01-30"
---

関数のパラメータを定義するときに、デフォルト値を設定しておくことができます。
例えば、下記の `print_num` 関数では、`num` パラメータを省略して呼び出すと、100 が指定されたものとして実行されます。

~~~ php
function print_num($num=100) {
    print "num = {$num}\n";
}

print_num();     #=> "num = 100"
print_num(200);  #=> "num = 200"
~~~

