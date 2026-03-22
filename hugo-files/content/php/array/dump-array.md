---
title: "PHPメモ: 配列の内容を再帰的にダンプする (print_r, var_dump)"
url: "/p/c49cqo4/"
date: "2012-01-30"
tags: ["php"]
aliases: [/php/array/dump-array.html]
---

`print_r($arr)` とすると、配列の内容を標準出力に出力してくれます。
多次元配列の内容も再帰的に表示してくれるため、デバッグ時に活用できます。

{{< code lang="php" title="print_r の使用例" >}}
$arr = array(
    array(1, 2, 3),
    array(4, 5, 6),
    array(7, 8, 9));
print_r($arr);
{{< /code >}}

{{< code lang="php" title="出力結果" >}}
Array
(
    [0] => Array
        (
            [0] => 1
            [1] => 2
            [2] => 3
        )

    [1] => Array
        (
            [0] => 4
            [1] => 5
            [2] => 6
        )

    [2] => Array
        (
            [0] => 7
            [1] => 8
            [2] => 9
        )

)
{{< /code >}}

`var_dump($arr)` でも同様に配列の内容をダンプできます。

