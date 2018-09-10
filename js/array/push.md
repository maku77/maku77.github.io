---
title: "配列に要素を追加・挿入する (push, unshift, splice)"
date: "2018-09-10"
---

配列に要素を追加するには、追加する位置によって下記のようなメソッドを使い分けます。

- **`Array#push()`**: 末尾に要素を追加。
- **`Array#shift()`**: 先頭に要素を追加。
- **`Array#splice()`**: 指定した位置に要素を追加（中間に挿入）。


配列の末尾に要素を追加する (push)
----

配列オブジェクトの末尾に任意の素を追加するには、`Array#push(elem, ...)` メソッドを使用します。
カンマ区切りで複数のパラメータを指定することで、複数の要素を同時に追加することができます。
`push` メソッドは、要素を追加した後の配列のサイズ（＝`length` プロパティの値）を返します。

~~~ javascript
var arr = [1, 2, 3];
console.log(arr.push(4));     //=> 4
console.log(arr.push(5, 6));  //=> 6
console.log(arr);             //=> [ 1, 2, 3, 4, 5, 6 ]
~~~

逆に、末尾から要素を取り出して、元の配列から削除するには、`Array#pop()` メソッドを使用します。
`push` や `pop` を使用することにより、配列を先入れ後出しの**スタックとして使用することができます**。

~~~ javascript
var arr = [];
arr.push(1);
arr.push(2);
arr.push(3);
console.log(arr.pop());  //=> 3
console.log(arr.pop());  //=> 2
console.log(arr.pop());  //=> 1
console.log(arr.pop());  //=> undefined
~~~


配列の先頭に要素を追加する (unshift)
----

配列オブジェクトの先頭に任意の要素を追加するには、`Array#unshift(elem, ...)` メソッドを使用します。
使用方法は末尾に要素を追加するメソッド `push` と同様ですが、複数の要素を同時に追加する場合は、その追加される順番に注意してください。

~~~ javascript
var arr = [1, 2, 3];
console.log(arr.unshift(4));     //=> 4
console.log(arr.unshift(5, 6));  //=> 6
console.log(arr);                //=> [ 5, 6, 4, 1, 2, 3 ]
~~~

逆に、先頭から要素を取り出して、元の配列から削除するには、`Array#shift()` メソッドを使用します。
`push` や `shift` を使用することにより、配列を先入れ先出しの**キュー (FIFO) として使用することができます**。

~~~ javascript
var arr = [];
arr.push(1);
arr.push(2);
arr.push(3);
console.log(arr.shift());  //=> 1
console.log(arr.shift());  //=> 2
console.log(arr.shift());  //=> 3
console.log(arr.shift());  //=> undefined
~~~


配列の中間位置に要素を追加する (splice)
----

より汎用的なメソッドである `Array#splice(index, howMany, elem, ...)` を使用すると、配列の中間位置に任意の要素を追加することができます。
最初のパラメータで挿入位置を指定します（例えば 0 を指定すると最初の要素の前に挿入、つまり、`unshift` と同様の動きになります）。
2番目のパラメータでは、元の配列において置き換える要素数を指定するのですが、新しく要素を追加（挿入）するだけであれば、0 を指定しておけば OK です。
3番目以降のパラメータで実際に挿入したい値を指定します。

~~~ javascript
var arr = [1, 2, 3, 4, 5];
arr.splice(2, 0, 'AAA', 'BBB', 'CCC');
console.log(arr);  //=> [ 1, 2, 'AAA', 'BBB', 'CCC', 3, 4, 5 ]
~~~

第1パラメータで挿入位置には、負の値で末尾からの位置を指定することも可能です。
例えば、-1 を指定すると、末尾の要素の前に要素を追加できます。

~~~ javascript
var arr = [1, 2, 3];
arr.splice(-1, 0, 'AAA');
console.log(arr);  //=> [ 1, 2, 'AAA', 3 ]
~~~

