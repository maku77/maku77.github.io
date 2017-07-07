---
title: 文字列を大文字、小文字に変換する (strtolower, strtoupper)
created: 2012-01-15
---

文字列内のすべてのアルファベットを小文字、あるいは大文字に変換するには以下のようにします。

~~~
$s = 'Hello World';
$lower = strtolower($s);  // 'hello world'
$upper = strtoupper($s);  // 'HELLO WORLD'
~~~

