---
title: Node.js 用のモジュールを作成する
date: "2016-07-19"
---

Node モジュール作成の基本
----

Node.js では、`.js` ファイルをモジュールとして扱うことができます。
作成したモジュール（`.js` ファイル）は、下記のように `require()` を使用して読み込むことができます。

```
var math = require('./math.js');  // .js は省略可能
```

下記の `math.js` は、簡単なモジュールの作成例です。
`exports`オブジェクト（実際は `module.exports` のエイリアス）のプロパティとして設定したものが外からアクセスできるようになります（ここに設定していない変数や関数は、ファイル内にスコープが限定されます）。

#### math.js（モジュール）

```javascript
// 足し算
exports.add = function (a, b) {
  return a + b;
};

// 引き算
exports.subtract = function (a, b) {
  return a - b;
};
```

上記で定義した `math.js` モジュールは、下記のように使用できます。

#### main.js（エントリポイント）

```javascript
var math = require('./math.js');

console.log(math.add(100, 200));
console.log(math.subtract(100, 200));
```

#### 実行結果

```
$ node main
300
-100
```


クラスライブラリとして Node モジュールを作成する (module.exports)
----

`exports` オブジェクトのプロパティとして一つずつ関数を設定するのではなく、`module.exports` 自体に関数（オブジェクト）を設定することで、関数自体を公開することができます。
コンストラクタとなる関数を公開することで、クラスライブラリのように扱うことができるようになります。

下記は、`Counter` クラスを提供するモジュール (`counter.js`) の例です。

#### counter.js

```javascript
function Counter() {
  this.val = 0;
}

Counter.prototype.increment = function () {
  this.val += 1;
};

Counter.prototype.getCount = function () {
  return this.val;
};

// Counter コンストラクタを公開
module.exports = Counter;
```

#### main.js

```javascript
var Counter = require('./counter.js');

var c = new Counter();
c.increment();
console.log(c.getCount());  //=> 1
c.increment();
console.log(c.getCount());  //=> 2
c.increment();
console.log(c.getCount());  //=> 3
```


フレームワーク的な構成にし、複数の Node モジュールを同時に読み込む
----

一連のクラスを同時に扱う必要があるフレームワークのような機能を提供したい場合は、下記のように一度の `require()` 呼び出しで複数のクラスを参照できるようにしておくと便利です。

#### main.js

```javascript
var animal = require('./animal');

var cat = new animal.Cat();
var dog = new animal.Dog();
cat.greet();
dog.greet();
```

下記のような書き方もよく見ますね。

```
var Cat = require('./animal').Cat;
var Dog = require('./animal').Dog;
var cat = new Cat();
var dog = new Dog();
```

これを実現する簡単な方法は、下記のように `animal.js` の中で複数のコンストラクタを定義する方法です。

#### animal.js

```javascript
module.exports = {
  Cat: function () { /* Cat コンストラクタ実装 */ },
  Dog: function () { /* Dog コンストラクタ実装 */ },
};
```

ただ、1 ファイル (`animal.js`) だけでプログラムを記述していくと、ファイルが肥大化してメンテナンス性が悪くなってしまいます。
ここでは、クラスごとにファイルを分割し、それらのファイルを `animal` ディレクトリ内で管理する方法を説明します。

ディレクトリ構成は下記のようにします。

```
- main.js （エントリポイント）
- animal/
    +-- index.js （ここから下記のファイルをすべて読み込む）
    +-- cat.js   （Cat クラス）
    +-- dog.js   （Dog クラス）
```

Node.js の `require()` 関数は柔軟なファイル検索を行ってくれるため、

```javascript
var animal = require('./animal');
```

のように拡張子を省略してモジュール名を指定すると、`./animal/index.js` を検索してくれます（`./animal.js` が見つからなかった場合）。
この `animal/index.js` ファイルの中で、関連するクラスファイルをさらに `require()` で読み込むようにすれば、各クラスごとに別ファイルで管理できるようになります。


#### animal/index.js（Cat クラスと Dog クラスを同時に読み込む）

```javascript
module.exports = {
  Cat: require('./cat.js'),
  Dog: require('./dog.js'),
};
```

#### animal/cat.js（Cat クラス）

```javascript
function Cat() {
  this.value = 'Meow!';
}

Cat.prototype.greet = function() {
  console.log(this.value);
};

module.exports = Cat;
```

#### animal/dog.js（Dog クラス）

```javascript
function Dog() {
  this.value = 'Bow wow!';
}

Dog.prototype.greet = function() {
  console.log(this.value);
};

module.exports = Dog;
```

このように `cat.js` と `dog.js` をファイルに分割したことにより、副次的な作用として、下記のように単一のクラスだけを読み込むこともできるようになります。

```javascript
// Cat クラスだけをロード
var Cat = require('./animal/cat');
```

