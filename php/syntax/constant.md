---
title: グローバル定数、クラス定数を定義する
created: 2012-01-15
---

グローバル定数
----

PHP でグローバルな定数を定義するには、`define()` 関数を使用します。
`define()` で定数を定義するときは、定数名をクォーテーションマークで囲む必要があります。
第三引数で `true` を指定すると、定数の名前は大文字・小文字を区別しないようになります。

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


クラス定数
----

クラス定数は `const` キーワードを使って定義することができます（PHP5.3 以降）。
正確には、`const` はクラス定数専用というわけではないのですが、名前空間の影響を受けるため、主にクラス定数の定義などに使用します。
`const` で定義したクラス定数にアクセス修飾子は付けることができず、必ず public になります。

```php
class MyClass {
    const MY_CONST = 100;
    ...
}
```

クラス定数を参照する場合は、以下のようにします。

* クラス内部からアクセスするとき `self::定数名`
* クラス外部からアクセスするとき `クラス名::定数名`

メンバメソッドからアクセスするときも、いちいち `self::` を付けないといけないのがちょっと面倒ですね。

#### サンプルコード: sample.php

```php
<?php
class Config {
    const CONST_INT = 100;
    const CONST_STR = 'ABC';

    public function dump() {
        // クラス定数をメソッド内から参照する
        echo self::CONST_INT . "\n";
        echo self::CONST_STR . "\n";
    }
}

// クラス定数を外部から参照する
echo Config::CONST_INT . "\n";
echo Config::CONST_STR . "\n";

$conf = new Config();
$conf->dump();
```

#### 実行結果

```
$ php sample.php
100
ABC
100
ABC
```


定数名で定数を参照する
----

定数名を表す文字列を用いて、その定数の値を参照するには `constant()` 関数を使用します。
`const` キーワードで定義した定数に対してもアクセスできます。

~~~ php
<?php
define('MAX_NUM', 10);

$name = 'MAX_NUM';
echo constant($name), PHP_EOL;
~~~

### コラム

通常の変数に対して同様に文字列でアクセスする場合は、可変変数の仕組みを使って `$$varname` のように簡単に参照することができます。
定数の場合はもともとの参照方法が `$` を付けない構文になっているので、`constant()` 関数を用意せざるを得なかったのでしょう。


参考
----

- [名前を使って変数にアクセスする（可変変数）](./variable-variables.html)

