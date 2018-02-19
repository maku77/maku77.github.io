---
title: "クラス定数／クラス変数／クラスメソッドを定義する"
date: "2018-02-19"
description: "JavaScript では、コンストラクタ関数のプロパティとして変数をセットすることで、クラス変数を表現します。"
---


JavaScript のクラス定数の例として、`Math.PI` や、`Number.MAX_VALUE` などがあります。
これらは、グローバルオブジェクトである `Math` や、グローバルなコンストラクタ関数である `Number` のプロパティとして設定されています。

独自クラスのクラス定数（実際には変数）やクラスメソッドを定義するときも同様に、コンストラクタ関数のプロパティとして設定します。
次の例では、独自クラス `MyClass` のクラス定数として `MyClass.CONST_VALUE` を、クラスメソッドとして `MyClass.classMethod` を定義しています。

~~~ javascript
// クラス定数（もどき）
MyClass.CONST_VALUE = 100;

// コンストラクタ
function MyClass(name) {
  this.name = name;
}

// クラスメソッド
MyClass.classMethod = function() {
  // クラス定数を参照
  console.log(MyClass.CONST_VALUE);
}

// インスタンスメソッド
MyClass.prototype.method = function() {
  // クラス定数を参照
  console.log(this.name, MyClass.CONST_VALUE);
}
~~~

下記の例では、いろんな方法で間接的にクラス定数 `MyClass.CONST_VALUE` を参照しています。

~~~
// クラス定数の参照
console.log(MyClass.CONST_VALUE);  //=> 100

// クラスメソッドの呼び出し
MyClass.classMethod();  //=> 100

// インスタンスメソッドの呼び出し
var obj = new MyClass('maku');
obj.method();  //=> maku 100
~~~

