---
title: "連想配列の要素数を調べる"
date: "2014-01-06"
---

JavaScript の連想配列（オブジェクト）は、配列と違って `length` プロパティで要素数を調べることができません。
以下の例では、一度、キーの配列を取得してから、その配列のサイズを調べています。

~~~ javascript
var obj = {a:100, b:200, c:300};
var len = Object.keys(obj).length;
~~~

もちろん、以下のように地味に for ループでカウントすることもできます。

~~~ javascript
var len = 0;
for (var item in obj) { ++len; }
~~~

