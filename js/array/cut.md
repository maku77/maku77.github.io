---
title: "配列の長さを切り詰める (length, splice)"
date: "2018-03-01"
---

配列の長さを切り詰める (length)
----
配列オブジェクトの `length` プロパティは、通常配列の長さを調べるために参照しますが、数値を代入することで配列のサイズを変更するのにも使用できます。
`length` プロパティに、現在の配列サイズよりも小さい値を代入すると、配列のサイズが切り詰められます。

~~~ javascript
var a = [1, 2, 3, 4, 5];
a.length = 3;
console.log(a.length);  //=> 3
console.log(a);         //=> [ 1, 2, 3 ]
~~~

`delete` を使って配列の要素を削除しても、配列のサイズを切り詰めることはできません。
その位置の値が `undefined` になるだけです。

~~~ javascript
var a = [1, 2, 3];
delete a[2];
console.log(a.length);  //=> 3
console.log(a);         //=> [ 1, 2, ]
~~~

現在の配列サイズよりも大きい値を `length` プロパティに代入すると、配列サイズは拡張されますが、拡張された部分の値は `undefined` になります。

~~~ javascript
var a = [1, 2, 3];
a.length = 10;
console.log(a.length);  //=> 10
console.log(a);         //=> [ 1, 2, 3, , , , , , ,  ]
~~~


配列の中間要素を削除して長さを切り詰める (splice)
----

配列の `splice()` メソッドは、指定したインデックスから指定した数だけの要素を抽出した配列を返します。

~~~ javascript
var a = [1, 2, 3, 4, 5, 6, 7];
console.log(a.splice(2, 3));  // [ 3, 4, 5 ]
~~~

`splice()` 要素は、同時に、元の配列から抽出した要素を削除します。
つまり、配列の中間要素を削除する形で配列を切り詰めることができます。

~~~ javascript
var a = [1, 2, 3, 4, 5, 6, 7];
a.splice(2, 3);
console.log(a);  //=> [ 1, 2, 6, 7 ]
~~~
