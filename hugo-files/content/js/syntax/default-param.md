---
title: "JavaScriptメモ: デフォルト引数を扱う関数を定義する (Default parameters)"
url: "p/34hdoeh/"
date: "2013-05-29"
lastmod: "2019-06-18"
tags: ["javascript"]
aliases: [/js/syntax/default-param.html]
---

ECMAScript 2015 (ES6) の場合
----

ECMAScript 2015 (ES6) で、デフォルトパラメータの構文がサポートされました。
C++ や Python のように、仮引数の宣言部でデフォルト引数を定義することができます。

```javascript
function myfunc(a, b, c=100) {
  ...
}
```

古い JavaScript の場合
----

ECMAScript 2015 (ES6) より前の JavaScript でデフォルト引数を実現するには、実引数が渡されなかったときに、仮引数の値が `undefined` になることを利用します。


{{< code lang="javascript" title="正しい書き方" >}}
function myfunc(a, b, c) {
  if (typeof c === 'undefined') {
    c = 100;  // default value
  }
}
{{< /code >}}

上記のように、`typeof` でパラメータの型を取得し、それが `undefined` という文字列と一致するのを確認するのが一番安全な方法です。


下記のように直接パラメータを `undefined` と比較する方法の方がシンプルに記述できますが、`undefined` の値は書き換えられる可能性があるため完璧ではありません。

{{< code lang="javascript" title="シンプルだが完璧ではない方法" >}}
function myfunc(a, b, c) {
  if (c === undefined) {
    c = 100;  // default value
  }
}
{{< /code >}}

実引数として偽値 (`0`, `false`, `null`, `undefined`, `NaN`, `''`) が渡された場合に、デフォルト値で値を置き換えるという手法もあります。
実引数を渡さなかった場合はそのパラメータの値は `undefined` になるので、これを利用してデフォルト引数を実現しようという考えですが、この方法では値を渡していても「偽」と判断される値だった場合にデフォルト値に置換されてしまうので、お勧めできる書き方ではありません。

{{< code lang="javascript" title="偽の値を渡した時に値を置き換える方法（危険）" >}}
function myfunc(a, b, c) {
  c = c || 100;
}
{{< /code >}}
