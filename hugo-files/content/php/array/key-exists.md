---
title: "PHPメモ: 連想配列にキーが存在するか調べる (array_key_exists)"
url: "/p/u48upsc/"
date: "2012-01-25"
tags: ["php"]
aliases: [/php/array/key-exists.html]
---

`array_key_exists` 関数を使用すると、連想配列が特定のキーを持っているかを確認することができます。

{{< code lang="php" title="例: 連想配列 $arr が key1 というキーを持っているか調べる" >}}
if (array_key_exists('key1', $arr)) {
    ...
}
{{< /code >}}

ちなみに、連想配列が特定の「値」を含んでいるかどうかを調べるには、通常の配列と同様に `in_array` 関数を使用します。

{{< code lang="php" title="例: 連想配列 $arr が 100 という値を含んでいるか調べる" >}}
if (in_array(100, $arr)) {
    ...
}
{{< /code >}}

