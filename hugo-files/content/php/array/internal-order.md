---
title: "PHPメモ: PHP の配列要素は初期化された順番に保持される"
url: "/p/dp8qjrp/"
date: "2012-10-13"
tags: ["php"]
aliases: [/php/array/internal-order.html]
---

PHP の配列は、添字に数値が使われる場合でも、内部的には連想配列として保持されています。
それぞれの要素は、初期化された順番で管理されており、この順番が添字によってソートされることはありません。

{{< code lang="php" title="サンプル" >}}
$arr[2] = 'CCC';
$arr[0] = 'AAA';
$arr[1] = 'BBB';

foreach ($arr as $key => $val) {
    echo "{$key} => {$val}\n";
}
{{< /code >}}

{{< code title="実行結果" >}}
2 => CCC
0 => AAA
1 => BBB
{{< /code >}}

