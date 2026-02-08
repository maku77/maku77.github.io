---
title: "JavaScriptメモ: クラスを定義する (class)"
url: "p/ikpcxk2/"
date: "2019-03-19"
description: "ECMAScript 2015 で導入された class キーワードによるクラス定義について説明します。"
tags: ["javascript"]
aliases: [/js/class/class.html]
---

class キーワードの概要
----

ECMAScript 2015 (ES6) で導入された **`class`** キーワードを使用すると、クラスの定義を直感的な構文で行うことができるようになります。
IE などの古い Web ブラウザでは使用できませんが、ほとんどの Web ブラウザでは使用できるようになっています。
Node.js などの環境ではまったく問題なく使用することができます。

もともとは JavaScript には「クラス」という概念は存在せず、プロトタイプ継承によるクラスもどきの定義を行っていたのですが、現在は [ECMAScript 2020 Language Specification](https://tc39.github.io/ecma262/#sec-class-definitions) の中でも **Class Definitions** という言葉が使われているため、堂々と「クラス」というワードを使用しても問題ないでしょう。

ただし、`class` キーワードを使用してクラス定義を行ったとしても、内部で行われていることは従来の関数ベースのクラス定義と変わりありません。
従来の下記のような関数ベースでのクラス定義を簡単に行うためのシンタックスシュガーが導入されたのだと考えるとよいでしょう（というか実際にそうなのです）。

{{< code lang="js" title="従来の関数ベースのクラス定義" >}}
// コンストラクタ定義
function Person(name) {
  this._name = name;
}

// メソッド定義
Person.prototype.greet = function() {
  console.log(`Hello, I am ${this._name}`);
}

// 使用例
const obj = new Person('Maku');
obj.greet();  // Hello, I am Maku
{{< /code >}}

ECMAScript 2015 の `class` キーワードを使用すると、上記のようなクラス定義は、下記のように記述することができます。

{{< code lang="js" title="class キーワードを使用したクラス定義" >}}
class Person {
  constructor(name) {
    this._name = name;
  }
  greet() {
    console.log(`Hello, I am ${this._name}`);
  }
}

const obj = new Person('Maku');
obj.greet();  // Hello, I am Maku
{{< /code >}}

関数ベースのコンストラクタ定義や、`prototype` を使用したメソッド定義と比べ、わかりやすく、かつ簡潔にクラス定義できるようになっていることが分かります。
定義したクラスの使用方法は変わりません。

ちなみに、ここでは `_name` プロパティがプライベートであることを示すために、慣例としてアンダースコアを付けるようにしています。
実際には `obj._name` とアクセスできてしまいますが、外部から参照すべきではないことを示すのに十分な効果があります。


コンストラクタの定義
----

コンストラクタは、`class` ブロックの中で **`constructor`** キーワードを使って定義します。

```js
class Rect {
  constructor(width, height) {
    this._width = width;
    this._height = height;
  }
}
```

コンストラクタは 1 つしか定義できず、2 つ以上定義しようとすると `SyntaxError` がスローされます。
これは、JavaScript の関数がオーバーライドできないことによる制約であり、このことからも、`class` キーワードは単なるシンタックスシュガーであることが分かります。

コンストラクタの定義を省略すると、下記のようなデフォルトの空のコンストラクタが暗黙的に使用されます。

```js
constructor() {}
```

継承されたクラスの場合、`constructor` の定義の中で、**`super()`** を使用して親クラスの `constructor` を呼び出すことができます（詳しくは後述）。


メソッド
----

メソッドの定義は、`class` ブロックの中で、`メソッド名() { 実装 }` という簡単な構文で行うことができます。
`function` というキーワードは省略することができます（省略しないとエラーになります）。
メソッドの中では、`this` を使用してインスタンスが保持しているプロパティにアクセスすることができます。
下記の例では、`Rect` クラスに `toString()` メソッドを定義しています。

{{< code lang="js" title="toString() メソッドの定義" >}}
class Rect {
  constructor(width, height) {
    this._width = width;
    this._height = height;
  }

  toString() {
    return `Rect(w=${this._width}, h=${this._height})`;
  }
}

const obj = new Rect(10, 20);
console.log(obj.toString());  // "Rect(w=10, h=20)"
{{< /code >}}


ゲッター、セッターの定義
----

メソッド定義の前に、**`get`** や **`set`** というキーワードを付けると、それぞれ ゲッターメソッド、セッターメソッドとして定義することができます。
プロパティを参照するように `obj.foo` と記述するだけで、ゲッター/セッターが呼び出されるようになります。

```js
class Person {
  constructor(name) {
    this._name = name;
  }
  get name() {
    console.log('get name() has been called');
    return this._name;
  }
  set name(name) {
    console.log('set name() has been called');
    this._name = name;
  }
}

const obj = new Person('Maku');
obj.name = 'Hemu';  // セッターが呼び出される
console.log('Hello ' + obj.name);  // ゲッターが呼び出される
```

{{< code title="実行例" >}}
set name() has been called
get name() has been called
Hello Hemu
{{< /code >}}

ゲッターだけを定義することで、リードオンリーなプロパティのように見せることができます。
ゲッターを定義するときに、同じ名前のプロパティが実際にインスタンスのプロパティとして存在している必要はありません。
例えば、下記の `area` ゲッターは、インスタンスのプロパティとしては実体がありませんが、呼び出し側からは `area` プロパティが存在しているかのように参照することができます。

```js
class Rect {
  constructor(width, height) {
    this._width = width;
    this._height = height;
  }
  get width() { return this._width; }
  get height() { return this._height; }
  get area() { return this._width * this._height; }
}

const obj = new Rect(10, 20);
console.log(obj.area);  // 200
```

JavaScript では、オブジェクトのプロパティは基本的にすべてパブリックなので、上記のようにプライベートなプロパティを表現したい場合は、実際のプロパティにアンダースコア (`_`) を付けるなどして、外部から直接参照されないようにする工夫が必要です。


static メソッド、static プロパティの定義
----

### static メソッドの定義

`class` ブロックの中でメソッド定義するときに、**`static`** キーワードを付けると、そのメソッドは静的メソッドとなり、インスタンス化しなくても呼び出すことができるようになります。
下記の例では、2 つの `Point` インスタンスをパラメータに取る静的メソッド `Point.distance` を定義しています。

```js
class Point {
  constructor(x, y) {
    this._x = x;
    this._y = y;
  }

  static distance(p1, p2) {
    const dx = p1._x - p2._x;
    const dy = p1._y - p2._y;
    return Math.hypot(dx, dy);
  }
}

const p1 = new Point(0, 0);
const p2 = new Point(3, 4);
console.log(Point.distance(p1, p2));  //=> 5
```

Java などの言語と異なり、静的メソッドはインスタンス経由で呼び出すことはできません。
下記のようにインスタンスメソッドのように呼び出そうとすると、`TypeError` がスローされます。

```js
p1.distance(p1, p2);  // TypeError
```

### static プロパティの定義

クラスに静的なプロパティを定義するときは、従来と同様に `class` ブロックの外で `クラス名.プロパティ名` と定義する必要があります。

```js
class Book {
  constructor(title) {
    this._title = (title === undefined) ? Book.defaultTitle : title;
  }
  get title() { return this._title; }
}

// 静的プロパティ
Book.defaultTitle = 'UNKNOWN';

const obj = new Book()
console.log(obj.title);  //=> UNKNOWN
```


継承（サブクラス化）
----

`class` によるクラス定義をするときに **`extends`** キーワードを使用することで、既存のクラスを継承することができます。
作成されたサブクラス側では、親クラスのメソッドやプロパティを参照することができます。

親クラスがコンストラクタを持っている場合、サブクラスのコンストラクタ内で `super()` を使って親クラスのコンストラクタ実装を呼び出す必要があります。
下記の例では、`Animal` クラスを継承して `Human` クラスを作成しています。

```js
class Animal {
  constructor(name) {
    this._name = name;
  }

  greet() {
    console.log('bowwow');
  }
}

class Human extends Animal {
  constructor(name) {
    // 親クラスのコンストラクタを呼び出す
    super(name);
  }

  greet() {
    // メソッド内から親クラスのプロパティを参照できる
    console.log(`Hi, I am a human. My name is ${this._name}.`);
  }
}

const obj1 = new Animal('UMA');
const obj2 = new Human('Maku');
obj1.greet();  // bowwow
obj2.greet();  // Hi, I am a human. My name is Maku.
```

親クラスと同じ名前のメソッドを定義すると、自動的にオーバーライドされたことになります。

従来の関数ベースのクラスを `extends` で継承することもできます。
下記は、JavaScript の組み込みクラスである `Date` クラスを継承して `FormatDate` クラスを作成しています。

```js
class FormatDate extends Date {
  constructor(dateStr) {
    super(dateStr);
  }

  // Creates a string in the form of "YYYYMMDDThh:mm:ssZ"
  toString() {
    const yyyy = this.getUTCFullYear();
    const MM = ('0' + (this.getUTCMonth() + 1)).slice(-2);
    const dd = ('0' + this.getUTCDate()).slice(-2);
    const hh = ('0' + this.getUTCHours()).slice(-2);
    const mm = ('0' + this.getUTCMinutes()).slice(-2);
    const ss = ('0' + this.getUTCSeconds()).slice(-2);
    return `${yyyy}-${MM}-${dd}T${hh}:${mm}:${ss}Z`;
  }
}

const d = new FormatDate('August 19, 1975 23:15:30');
console.log(d.toString());  //=> "1975-08-19T14:15:30Z"
```


クラス式によるクラス定義
----

ここまでで見てきたクラス定義の方法は「**クラス宣言**」という構文を使ったもので、もうひとつ、「**クラス式**」を使った方法でもクラス定義を行うこともできます（`function` キーワードを使用した関数式と同様の考え方です）。

### 名前なしのクラス式によるクラス定義

```js
const Person = class {
  greet() {
    console.log('Hello');
  }
}

const obj = new Person();
obj.greet();  //=> Hello
console.log(Person.name);  //=> Person
```

上記のように、無名のクラス式でクラス定義を行った場合、クラスオブジェクトの `.name` プロパティの値は、変数名と同じ文字列になります（上記の場合は `Person`）。

### 名前付きのクラス式によるクラス定義

```js
const Person = class Foo {
  greet() {
    console.log('Hello');
  }
}

const obj = new Person();
obj.greet();  //=> Hello
console.log(Person.name);  //=> Foo
```

上記のように、名前付きのクラス式（上記の場合は `Foo` という名前）でクラス定義を行った場合、クラスオブジェクトの `.name` プロパティの値はその名前と同じ文字列になります（上記の場合は `Foo`）。


Node.js の require で読み込めるクラスライブラリを作成する
----

Node.js でプログラミングしている場合は、定義したクラスを **`exports`** オブジェクトのプロパティとして設定することで、別の JavaScript ファイルから **`require`** で参照できるようになります。

{{< code lang="js" title="mylib.js（クラスライブラリ側）" >}}
class MyClass {
  constructor(name) {
    this._name = name;
  }
  greet() {
    console.log(`Hello, I am ${this._name}`);
  }
}

exports.MyClass = MyClass;  // MyClass を公開
{{< /code >}}

{{< code lang="js" title="main.js（エントリポイント）" >}}
const mylib = require('./mylib.js');
const obj = new mylib.MyClass('Maku');
obj.greet();  // Hello, I am Maku
{{< /code >}}

ECMAScript 2015 で導入された [オブジェクトの分割代入 (Object destructuring)](/p/wodo6y4/) の構文を使用すると、上記のコードは下記のように簡潔に記述することができます。

{{< code lang="js" title="main.js（シンプルな書き方）" >}}
const { MyClass } = require('./mylib.js');
const obj = new MyClass('Maku');
{{< /code >}}

1 つの JavaScript ファイル（モジュール）で複数のクラスを公開することもできます。
下記の `mylib.js` モジュールは、3 つのクラスを公開しています。

{{< code lang="js" title="mylib.js" >}}
class ClassA { }
class ClassB { }
class ClassC { }
exports.ClassA = ClassA;
exports.ClassB = ClassB;
exports.ClassC = ClassC;
{{< /code >}}

このモジュールを利用する側は、自分の興味のあるクラスだけを参照することができます。
下記の例では、`ClassA` と `ClassC` だけを参照しています。

{{< code lang="js" title="main.js" >}}
const { ClassA, ClassC } = require('./mylib.js');
const a = new ClassA();
const c = new ClassC();
{{< /code >}}


その他 class に関する注意点
----

### クラスは参照する前に定義しておく必要がある

JavaScript の特殊な仕組みとしてホイスティング (Hoisting) があり、関数宣言が後方で行われていても、その関数を参照することができるようになっています。

```js
myFunc();  // OK
function myFunc() {}
```

一方で、クラス宣言は、クラスを参照する前に行わないとエラーになります。

```js
const obj = new MyClass();  // ReferenceError
class MyClass {}
```


### class 本体は Strict モードになる

class ブロックの中の実装は、`use strict;` を記述しなくても、自動的に Strict モードで実行されます。


参考
----
- [ECMAScript® 2020 Language Specification - 14.6 Class Definitions](https://tc39.github.io/ecma262/#sec-class-definitions)
- [クラス - JavaScript｜MDN](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Classes)

