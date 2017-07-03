---
title: 複数の値を返す関数を定義する（多値関数）
created: 2012-01-30
---

関数から配列を返すことで、擬似的に複数の値を返す関数を定義することができます。

~~~ php
function foo() {
    return array(1, 2, 3);

}
$ret = foo();
print $ret[0];  # => 1
print $ret[1];  # => 2
print $ret[2];  # => 3
~~~

戻り値を連想配列の形で返せば、キー指定で複数の値を取得可能な戻り値として扱えます。

~~~ php
function foo() {
    return array('result1'=>100, 'result2'=>200);
}

$ret = foo();
print $ret['result1'] . "\n";
print $ret['result2'] . "\n";
~~~

