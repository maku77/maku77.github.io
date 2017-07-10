---
title: 文字列がある文字列で始まっているか調べる
created: 2012-10-01
---

PHP には Python のような `startswith` 関数が用意されていないため、自分で関数を作成する必要があります。

#### StrUtil.php

~~~ php
class StrUtil {
    /**
     * Checks if a text starts with a certain letters.
     */
    static function starts_with($text, $pattern){
        return substr($text, 0, strlen($pattern)) === $pattern;
        //return strpos($text, $pattern) === 0;
    }
}
~~~

#### 使用例

~~~ php
require_once 'StrUtil.php'
if (StrUtil::starts_with('ABCDE', 'ABC')) {
    // ...
}
~~~

