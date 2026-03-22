---
title: "PHPメモ: 文字列を URL エンコードする (urlencode)"
url: "/p/3ksqtdu/"
date: "2012-10-14"
tags: ["php"]
aliases: [/php/string/urlencode.html]
---

文字列データを URL のパラメータとして渡すような場合、URL で扱える文字だけで表現するために URL エンコードする必要があります。
例えば、「半角スペース」は `+` に置き換え、「あ (UTF-8)」は `%E3%81%82` に置き換えます。
この変換を行うには、PHP の `urlencode()` 関数を使用します。

{{< code lang="php" title="PHP: urlencode 関数" >}}
string urlencode ( string $str )
{{< /code >}}

以下のサンプルコードでは、Google Chart API の URL パラメータに渡す数式部分の文字列を URL エンコードするために `urlencode()` を使用しています。

{{< code lang="php" title="例: Google Chart API で数式を表示する img 要素を生成する" >}}
<?php
function createMathElement($formula) {
    $formula = str_replace('"', "''", trim($formula));
    $formula = urlencode($formula);
    return '<img src="https://chart.apis.google.com/chart?cht=tx&chl=' . $formula . '" />';
}

echo createMathElement('a^2 + b^2 = c^2');
{{< /code >}}

{{< code lang="html" title="実行結果" >}}
<img src="https://chart.apis.google.com/chart?cht=tx&chl=a%5E2+%2B+b%5E2+%3D+c%5E2" />
{{< /code >}}

