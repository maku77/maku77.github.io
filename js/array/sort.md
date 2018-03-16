---
title: "配列をソートする (sort, reverse)"
date: "2014-03-17"
---

アルファベット順ソート
----

JavaScript の配列のソート ([Array.prototype.sort()](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Array/sort)) は、デフォルトでは**アルファベット順のソート**（Unicodeのコードポイントの昇順）になることに注意してください。

~~~ javascript
var a = [1, 2, 3, 11, 12, 13];
a.sort();
console.log(a);  //=> [1, 11, 12, 13, 2, 3]
~~~

上記の例では、数値を要素に含む配列をソートしていますが、ソート結果は数値の昇順ではなく、アルファベット順になるため、`2` よりも前に `11` が来ています。


数値順ソート
----

アルファベット順ではなく、数値順にソートするには、下記のように `sort()` 関数に数値用の比較関数を渡してやります。

~~~ javascript
var a = [1, 2, 3, 11, 12, 13];
a.sort(function(a, b) {
  return a - b;
});
console.log(a);  //=> [1, 2, 3, 11, 12, 13]
~~~

比較関数は、２つの変数 a, b を受け取り、a の方が大きい場合に正の値、b の方が大きい場合に負の値、等しい場合に 0 を返すように実装します。


降順ソート
----

### 方法1（sort してから reverse）

~~~ javascript
var a = ['B', 'A', 'C'];
a.sort().reverse();  //=> ['C', 'B', 'A']
~~~

### 方法2（比較関数を渡す）

~~~ javascript
var a = ['B', 'A', 'C'];
a.sort(function(a, b) {
  if (a < b) return 1;
  if (a > b) return -1;
  return 0;
});
console.log(a);
~~~


逆順にする (reverse)
----

~~~ javascript
var a = ['B', 'A', 'C'];
a.reverse();
console.log(a);  //=> ['C', 'A', 'B']
~~~

`reverse()` 関数も、`sort()` 関数と同様に、自分自身が指し示す配列の内容を変化させることに注意してください。


大文字・小文字を区別せずにソート
----

`sort()` 関数はデフォルトでは、Unicode コードポイントの昇順にソートするため、アルファベットは小文字よりも大文字の方が小さいと判断されます。

~~~ javascript
var a = ['hello', 'Hi', 'hack', 'Home'];
a.sort();
console.log(a);  //=> ['Hi', 'Home', 'hack', 'hello']
~~~

大文字、小文字を区別せずにアルファベット順でソートしたいときは、次のように、`toLowerCase()` で両方の引数を小文字に変換してから比較します。

~~~ javascript
var a = ['hello', 'Hi', 'hack', 'Home'];
a.sort(function(a, b) {
  a = a.toLowerCase();
  b = b.toLowerCase();
  if (a < b) return -1;
  if (a > b) return 1;
  return 0;
});
console.log(a);  //=> ['hack', 'hello', 'Hi', 'Home']
~~~

