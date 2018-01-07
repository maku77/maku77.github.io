---
title: 文字列を分割する (explode)
date: "2012-01-25"
---

explode による文字列分割
----

[explode 関数](http://php.net/manual/ja/function.explode.php)を使用すると、文字列を指定したデリミタ（文字列）で分割することができます。

#### 例: カンマで文字列を分割する

~~~ php
$s = 'AAA, BBB, CCC';
$arr = explode(',', $s);  // => ['AAA', ' BBB', ' CCC']
~~~

ちなみに、カンマの前後の空白文字は、分割後の文字列内にそのまま残っていることに注意してください。
分割後の文字列から、先頭末尾の空白をすべて削除したい場合は、例えば次のように `trim` を全要素に対して適用すればよいでしょう。

~~~ php
$s = 'AAA, BBB, CCC';
$arr = explode(',', $s);         // => ['AAA', ' BBB', ' CCC']
$arr = array_map('trim', $arr);  // => ['AAA', 'BBB', 'CCC']
~~~

参考
----

- [文字列を結合する (implode)](../string/implode.html)
- [配列の各要素に対して同じ処理を行う (array_map)](../array/array-map.html)
- [文字列の先頭と末尾のスペースを削除する (trim)](../string/trim.html)

