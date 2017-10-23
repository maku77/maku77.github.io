---
title: "配列内にある要素が存在するか調べる"
created: 2017-10-22
---

`Array#indexOf` を使用すると、指定した要素が配列内のどのインデックス位置に存在するかを調べることができます。
要素が存在しない場合は `-1` を返すので、これを利用してある要素が配列内に存在するかを調べることができます。

~~~ javascript
var arr = ['aaa', 'bbb', 'ccc', 'ddd'];
if (arr.indexOf('ccc') != -1) {
    alert('要素が見つかりました');
}
~~~

#### jQuery の場合

~~~ javascript
var arr = ['aaa', 'bbb', 'ccc', 'ddd'];
if ($.inArray('ccc', arr)) {
    alert('要素が見つかりました');
}
~~~

