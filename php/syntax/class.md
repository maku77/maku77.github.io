---
title: クラスを定義する
created: 2012-08-04
---

PHP のクラスは、Java や C++ と似た構文で定義することができます。
下記の例では、シンプルなコンストラクタやメソッドを持つ、カウンタークラスを定義しています。

#### クラスを定義する

~~~ php
<?php
class Counter {
    // メンバ変数
    private $_value;

    // コンストラクタ
    public function __construct($value = 0) {
        $this->_value = $value;
    }

    // メンバメソッド
    public function getValue() {
        return $this->_value;
    }

    // static メンバメソッド
    public static function sayHello() {
        echo "Hello\n";
    }
}
~~~

#### クラスを使用する

~~~ php
$cnt = new Counter(100);
echo $cnt->getValue() . "\n";  //=> 100
Counter::sayHello();           //=> Hello
~~~

