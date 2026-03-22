---
title: "PHPメモ: 文字列がある文字列で始まっているか調べる"
url: "/p/dtrd7mx/"
date: "2012-10-01"
tags: ["php"]
aliases: [/php/string/starts-with.html]
---

PHP には Python のような `startswith` 関数が用意されていないため、自分で関数を作成する必要があります。

{{< code lang="php" title="StrUtil.php" >}}
class StrUtil {
    /**
     * Checks if a text starts with a certain letters.
     */
    static function starts_with($text, $pattern){
        return substr($text, 0, strlen($pattern)) === $pattern;
        //return strpos($text, $pattern) === 0;
    }
}
{{< /code >}}

{{< code lang="php" title="使用例" >}}
require_once 'StrUtil.php'
if (StrUtil::starts_with('ABCDE', 'ABC')) {
    // ...
}
{{< /code >}}

