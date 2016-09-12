---
title: PHP のクラス構文
created: 2012-08-04
---

クラス定数
----

グローバルな定数は `define()` を使用して定義しますが、
クラス定数は `const` キーワードを使って定義することができます (PHP5~)。
クラス定数にアクセス修飾子は付けることができず、必ず public になります。

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

    public function hoge() {
        echo self::CONST_STR . "\n";
    }
}

echo Config::CONST_INT . "\n";

$conf = new Config();
$conf->hoge();
```

#### 実行結果

```
$ php sample.php
100
ABC
```

