---
title: "配列をスタックとして扱う (array_push, array_pop)"
date: "2012-01-30"
---

PHP では配列をスタックとして使用できます。
スタックへの要素の追加は `array_push` 関数、要素の取出しは `array_pop` 関数で行えます。

~~~ php
$stack = array();          # 空のスタックを作成
array_push($stack, 値);    # スタックへのプッシュ
$val = array_pop($stack);  # スタックからポップ（空の場合は NULL）
empty($stack);             # スタックが空かどうか？
~~~

#### 使用例

~~~ php
$stack = array();
array_push($stack, 100);
array_push($stack, 200);
array_push($stack, 300);

while ($val = array_pop($stack)) {
    print "$val\n";
}
~~~

#### 実行結果

~~~
300
200
100
~~~

