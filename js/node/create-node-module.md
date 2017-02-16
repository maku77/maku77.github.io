---
title: Node.js 用モジュールの作り方（require でロード可能な Node モジュールを作成する）
created: 2013-10-23
---

Node モジュールの作り方
----

Node の `require()` 関数を使用すると、別ファイルとして作成した JavaScript ライブラリ（Node モジュール）をロードすることができます。
Node モジュールで公開するプロパティや関数は、以下のいずれかの方法で設定します。

1. `exports` の各プロパティに設定する方法
2. `module.exports` 自体に任意のオブジェクトを代入する方法

両方の方法を使った場合、1 の方が優先されます。


(1) exports のプロパティとして公開プロパティを設定する方法
----

下記の例では 1 つ目の方法を使って、`exports` のプロパティで、`name` 変数、`add` 関数を公開しています。
`var` キーワードで定義した、通常の変数は非公開になります。

#### mymodule.js（Node モジュール）

~~~ javascript
exports.name = 'magu';
exports.add = function(a, b) { return a + b; }
var secret = 'private variable';
~~~

この Node モジュールは、以下のように `require()` 関数でロードして使用します。
同じディレクトリに置いたモジュールは、プレフィックスに `./` を付けた相対パスで指定します（拡張子の `.js` は省略可能です）。

#### main.js（Node モジュールを使用する）

~~~ javascript
var mymodule = require('./mymodule');  // mymodule.js のロード
console.log(mymodule.name);            //=> 'magu'
console.log(mymodule.add(1, 2));       //=> 3
~~~


(2) module.exports に公開オブジェクトを設定する方法
----

2 つ目の方法では、`module.exports` に代入したオブジェクトを丸ごと公開します。

#### mymodule.js

~~~ javascript
module.exports = {
    name: 'magu',
    add: function(a, b) { return a + b; }
};
~~~

#### main.js

~~~ javascript
var mymodule = require('./mymodule');  // mymodule.js のロード
console.log(mymodule.name);            //=> 'magu'
console.log(mymodule.add(1, 2));       //=> 3
~~~

もちろん、公開オブジェクトとして、関数を指定することもできます。

#### greet.js

~~~ javascript
module.exports = function(name) {
    console.log('Hello ' + name);
}
~~~

#### main.js

~~~ javascript
var greet = require('./greet');  // greet.js のロード
greet('Joe');                    //=> 'Hello Joe'
~~~

