---
title: 文字列を URL エンコードする (urlencode)
created: 2012-10-14
---

文字列データを URL のパラメータとして渡すような場合、URL で扱える文字だけで表現するために URL エンコードする必要があります。
例えば、「半角スペース」は `+` に置き換え、「あ (UTF-8)」は `%E3%81%82` に置き換えます。
この変換を行うには、PHP の `urlencode()` 関数を使用します。

#### urlencode 関数
~~~ php
string urlencode ( string $str )
~~~

以下のサンプルコードでは、Google Chart API の URL パラメータに渡す数式部分の文字列を URL エンコードするために `urlencode()` を使用しています。

#### 例: Google Chart API で数式を表示する img 要素を生成する

~~~ php
<?php
function createMathElement($formula) {
    $formula = str_replace('"', "''", trim($formula));
    $formula = urlencode($formula);
    return '<img src="http://chart.apis.google.com/chart?cht=tx&chl=' . $formula . '" />';
}

echo createMathElement('a^2 + b^2 = c^2');
~~~

#### 実行結果

~~~ html
<img src="http://chart.apis.google.com/chart?cht=tx&chl=a%5E2+%2B+b%5E2+%3D+c%5E2" />
~~~

