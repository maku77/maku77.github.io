---
title: "アロー関数で関数を定義する"
date: "2018-11-22"
---

アロー関数とは
----

アロー関数は、[ECMAScript 2015 (6th Edition, ECMA-262)](https://www.ecma-international.org/ecma-262/6.0/#sec-arrow-function-definitions) で定義された構文で、この構文を使用することで無名の関数を短く記述することができるようになります。

例えば、配列の `map` メソッドの例を見てみましょう。
`map` メソッドは受け取った関数を各要素に適用した配列を返します。
従来は、下記のように無名の関数オブジェクトを渡していました。

~~~ javascript
var arr1 = [1, 2, 3];
var arr2 = arr1.map(function (x) { return x * 2; });
console.log(arr2);  //=> [ 2, 4, 6 ]
~~~

これを、アロー関数を使って書き換えると、次のように簡潔に記述することができます。

~~~ javascript
var arr1 = [1, 2, 3];
var arr2 = arr1.map(x => x * 2);
console.log(arr2);  //=> [ 2, 4, 6 ]
~~~

短く記述できて、かつ、分かりやすいですね！


アロー関数の基本構文
----

アロー関数を使用する場合は、パラメータの数が、0個、1個、それ以上の場合で注意する点があるのでここでまとめておきます。

### パラメータが 0 個の場合

パラメータがない場合、アロー関数のパラメータ部分は `()` とだけ記述する必要があります。

~~~ javascript
doTask(() => { console.log('Hello'); });
~~~

### パラメータが 1 個の場合

パラメータが1つだけの場合は、パラメータは括弧 `()` で囲んでも、囲まなくても構いません。

~~~ javascript
var a = [1, 2, 3].map(x => x * 2);
var b = [1, 2, 3].map((x) => x * 2);
~~~

### パラメータが 2 個以上の場合

パラメータが複数ある場合、パラメータは必ず括弧 `()` で囲む必要があります。

~~~ javascript
var arr = [3, 1, 5, 2, 4];
arr.sort((a, b) => a - b);
console.log(arr);  //=> [ 1, 2, 3, 4, 5 ]
~~~

ちなみに、アロー関数は従来の関数オブジェクトの定義の代わりに使用できるので、下記のように変数に代入することができます。

~~~ javascript
var f = a => a * 2;
var arr = [1, 2, 3].map(f);
~~~

初めて見ると `=` がいっぱい出てきて面食らうので覚えておきましょう。


アロー関数では return の記述を省略できる
----

アロー (`=>`) の右側の `{}` を省略すると、その値が `return` されたのと同様に振る舞います。
つまり、下記の2つは同じ動作になります。

~~~ javascript
var a = [1, 2, 3].map(x => x * 2);
var b = [1, 2, 3].map(x => { return x * 2; });
~~~


アロー関数のデフォルト引数
----

アロー関数のパラメータとして、デフォルト値を設定しておくことができます。

~~~ javascript
doSomething((a = 1, b = 2) => a + b);
~~~


アロー関数では this が束縛されない
----

アロー関数の中で `this` を参照すると、その外側のスコープの `this` を参照することができます。
この性質は、メソッド定義内のコールバック関数から `this` を参照したい場合に役に立ちます。

例えば、下記の `MyClass` クラスは、コンストラクタで渡された名前 (`this.name`) を参照してメッセージを出力する `hello()` メソッドを定義しています。
しかし、実際に `this` を参照している部分はコールバック関数内にあるため、`MyClass` のインスタンスとしての `this` を参照できていません。

#### 間違った例

~~~ javascript
// コンストラクタ
function MyClass(name) {
  this.name = name;
}

// インスタンスメソッド（1秒待ってからメッセージを表示）
MyClass.prototype.hello = function() {
  setTimeout(function() {
    console.log(`Hello ${this.name}`);
  }, 1000);
};

var obj = new MyClass('Maku');
obj.hello();  //=> Hello undefined
~~~

この問題を解決するため、従来は次のように `this` への参照を `that` や `self` といった名前の変数に保持しておくのが一般的な方法でした。

#### 正しく this を参照できるようにした例

~~~ javascript
MyClass.prototype.hello = function() {
  var that = this;
  setTimeout(function() {
    console.log(`Hello ${that.name}`);
  }, 1000);
};
~~~

アロー関数を使用すると、こういった `this` 参照の退避が必要なくなり、次のようにスッキリと記述することができます。

~~~ javascript
MyClass.prototype.hello = function() {
  setTimeout(() => {
    console.log(`Hello ${this.name}`);
  }, 1000);
};
~~~

