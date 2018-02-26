---
title: "クラスに toString() メソッドを実装する"
date: "2018-02-26"
description: "独自クラスに toString() メソッドを実装しておくと、オブジェクトを文字列として出力したときの表示内容を自由にカスタマイズすることができます。"
---

JavaScript は、何らかのオブジェクトを文字列に変換する必要があるときに、そのオブジェクトの `toString()` メソッドを呼び出します。
例えば、`alert()` や `document.write()` 関数のパラメータとして渡したときや、文字列と結合したときです。
`toString()` メソッドは、`Object` クラスで定義されており、デフォルトでは下記のような形式の文字列を返すようになっています。

~~~
[class Object]
~~~

`toString()` メソッドを実装すると、この文字列をわかりやすい表現に変更することができます。
通常は、オブジェクトが持っている重要なプロパティを出力するように実装しておくのがよいでしょう。
下記の例では、独自クラス `Book` の `toString()` メソッドを実装しています。

~~~ js
function Book(title, author) {
    this.title = title;
    this.author = author;
}

Book.prototype.toString = function() {
    return 'title=' + this.title + ', author=' + this.author;
}
~~~

使用例:

~~~ js
var b = new Book('Yes we can', 'Maku');
alert(b);  //=> title=Yes we can, author=Maku
~~~

ちなみに、`console.log()` 関数に関しては、渡されたオブジェクトをどのような表現で出力するかが処理系依存になっており、通常はプロパティの内容がわかるような形で出力するように実装されています。
例えば、Node.js のコンソール環境では、`console.log()` は、次のようにオブジェクトのプロパティをすべて出力するようになっています。

~~~ js
var obj = {a:100, b:200};
console.log(obj);  //=> { a: 100, b: 200 }
~~~

