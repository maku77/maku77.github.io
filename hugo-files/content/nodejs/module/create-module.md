---
title: "Node.jsメモ: Node.js 用モジュールの作り方（require でロード可能な Node モジュールを作成する）"
url: "p/9fapzbb/"
date: "2016-07-19"
tags: ["nodejs"]
aliases: /nodejs/module/create-module.html
---

Node モジュール作成の基本
----

Node.js では、`.js` ファイルを**モジュール**として扱うことができます（配布用に `package.json` が用意されたものは、特に「**パッケージ**」と呼ばれます）。
作成したモジュールは下記のように `require()` を使用して読み込むことができます。

```
var mod = require('./mymodule.js');  // .js は省略可能
mod.foo(1, 2);
```

Node モジュールで公開するプロパティや関数は、以下のいずれかの方法で設定します。

1. **`exports`** の各プロパティに設定する方法
2. **`module.exports`** 自体に任意のオブジェクトを代入する方法


(1) exports のプロパティとして公開プロパティを設定する方法
----

`exports`オブジェクト（実際は `module.exports` のエイリアス）のプロパティとして設定したものが `require` したクライアント側からアクセスできるようになります。
逆に、`exports` のプロパティに設定されていない変数や関数は、ファイル内にスコープが限定されます。

下記の例では、`exports` のプロパティ経由で、`name` 変数、`add` 関数を公開しています。

{{< code lang="javascript" title="mymodule.js（Node モジュール）" >}}
// 公開される変数や関数
exports.name = 'magu';
exports.add = function(a, b) { return privFunc(a, b); }

// 非公開の変数や関数
var secret = 100;
function privFunc(a, b) { return a + b + secret; }
{{< /code >}}

この Node モジュールは、以下のように `require()` 関数でロードして使用します。
同じディレクトリに置いたモジュールは、プレフィックスに `./` を付けた相対パスで指定します（拡張子の `.js` は省略可能です）。

{{< code lang="javascript" title="main.js（Node モジュールを使用する）" >}}
var mymodule = require('./mymodule');  // mymodule.js のロード
console.log(mymodule.name);            //=> 'magu'
console.log(mymodule.add(1, 2));       //=> 103
{{< /code >}}


(2) module.exports に公開オブジェクトを設定する方法
----

### 1つのオブジェクトを公開する

`module.exports` にオブジェクトを代入すると、`require` 時にそのオブジェクト自体が返されるようになります。

{{< code lang="javascript" title="mymodule.js" >}}
module.exports = {
  name: 'magu',
  add: function(a, b) { return a + b; }
};
{{< /code >}}

{{< code lang="javascript" title="main.js" >}}
var mymodule = require('./mymodule');  // mymodule.js のロード
console.log(mymodule.name);            //=> 'magu'
console.log(mymodule.add(1, 2));       //=> 3
{{< /code >}}

<div class="note">
<code>exports</code> が <code>module.exports</code> のエイリアスなのであれば、<code>exports = myObj;</code> としても <code>myObj</code> を公開できそうですが、この方法は上手くいきません。<code>exports</code> は <code>module.exports</code> を参照する単なる変数であり、<code>module.exports</code> 自体に値を代入するには、あくまで <code>module.exports = myObj;</code> とする必要があります。
</div>

### 関数オブジェクトを公開する

JavaScript では関数もオブジェクト（関数オブジェクト）なので、`module.exports` に関数を代入することができます。
この場合、`require` 時に関数オブジェクト自体が返されることになります。

{{< code lang="javascript" title="greet.js" >}}
module.exports = function(name) {
  console.log('Hello ' + name);
}
{{< /code >}}

{{< code lang="javascript" title="main.js" >}}
var greet = require('./greet');  // greet.js のロード
greet('Joe');                    //=> 'Hello Joe'
{{< /code >}}


### クラスを公開する

クラスのコンストラクタ（関数オブジェクト）を、`module.exports` に代入してやれば、`require` 時にコンストラクタが返されるようになるので、**クラスライブラリとして使用できる Node モジュール**として使用できるようになります。

下記は、`Counter` クラスを提供するモジュール (`counter.js`) の実装例です。

{{< code lang="javascript" title="counter.js" >}}
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
{{< /code >}}

{{< code lang="javascript" title="main.js" >}}
var Counter = require('./counter.js');  // コンストラクタが返される

var c = new Counter();
c.increment();
console.log(c.getCount());  //=> 1
c.increment();
console.log(c.getCount());  //=> 2
c.increment();
console.log(c.getCount());  //=> 3
{{< /code >}}


フレームワーク的な構成にし、複数のクラスを同時に読み込む
----

一連のクラスを同時に扱う必要があるフレームワークのような機能を提供したい場合は、下記のように一度の `require()` 呼び出しで複数のクラスを参照できるようにしておくと便利です。

{{< code lang="javascript" title="main.js" >}}
var animal = require('./animal');

var cat = new animal.Cat();
var dog = new animal.Dog();
cat.greet();
dog.greet();
{{< /code >}}

下記のような書き方もよく見ますね。

```javascript
var Cat = require('./animal').Cat;
var Dog = require('./animal').Dog;
var cat = new Cat();
var dog = new Dog();
```

これを実現する簡単な方法は、下記のように `animal.js` の中で複数のコンストラクタを定義する方法です。

{{< code lang="javascript" title="animal.js" >}}
module.exports = {
  Cat: function () { /* Cat コンストラクタ実装 */ },
  Dog: function () { /* Dog コンストラクタ実装 */ },
};
{{< /code >}}

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

のように**拡張子を省略してモジュール名を指定すると、`./animal/index.js` を検索してくれます**（ただし、`./animal.js` というファイルが存在していれば、そちらが優先的に使用されます）。
この `animal/index.js` ファイルの中で、関連するクラスファイルをさらに `require()` で読み込むようにすれば、各クラスごとに別ファイルで管理できるようになります。


{{< code lang="javascript" title="animal/index.js（Cat クラスと Dog クラスを同時に読み込む）" >}}
module.exports = {
  Cat: require('./cat.js'),
  Dog: require('./dog.js'),
};
{{< /code >}}

{{< code lang="javascript" title="animal/cat.js（Cat クラス）" >}}
function Cat() {
  this.value = 'Meow!';
}

Cat.prototype.greet = function() {
  console.log(this.value);
};

module.exports = Cat;  // コンストラクタを公開
{{< /code >}}

{{< code lang="javascript" title="animal/dog.js（Dog クラス）" >}}
function Dog() {
  this.value = 'Bow wow!';
}

Dog.prototype.greet = function() {
  console.log(this.value);
};

module.exports = Dog;  // コンストラクタを公開
{{< /code >}}

このように `cat.js` と `dog.js` をファイルに分割したことにより、副次的な作用として、下記のように単一のクラスだけを読み込むこともできるようになります。

```javascript
// Cat クラスだけをロード
var Cat = require('./animal/cat');
```


