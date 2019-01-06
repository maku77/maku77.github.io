---
title: "テンプレート文字列の機能で文字列リテラル内の変数を展開する (template literal)"
date: "2018-01-05"
description: "ECMAScript 2015 のテンプレート文字列の機能を使用すると、文字列リテラル内で変数を展開したり、簡単な式を実行したりすることができます。"
---

テンプレート文字列内で変数展開する
----

文字列を定義する時に、シングルクォートやダブルクォートの代わりに、バッククォート (`` ` ``) で囲むと、その文字列リテラルは**テンプレート文字列 (template literal)** とみなされ、内部で変数や式の評価を行うことができるようになります。

テンプレート文字列内で式の評価を行いたい部分は、`${` と `}` で囲みます。

~~~ javascript
var name = 'Maku';
var msg = `I am ${name}`;  //=> 'I am Maku'
~~~

上記は単純な変数展開を行う例ですが、次のように内部で演算を行ったり、関数呼び出しを行ったりすることもできます。

~~~ javascript
var a = 1;
var b = 2;
var msg = `1 + 2 = ${a + b}`;  //=> '1 + 2 = 3'
~~~


複数行に渡る文字列を定義する
----

テンプレート文字列の定義の中で改行を行うことで、複数行に渡る文字列を定義することができます。
コード上で行った改行は、そのまま改行コードとして文字列の中に含められます。

~~~ javascript
var msg = `This is a long
long long long long long
long long long long text.`;

console.log(msg);
~~~

#### 実行結果

~~~
This is a long
long long long long long
long long long long text.
~~~

ちなみに、シングルクォートやダブルクォートで囲んで定義する文字列リテラルの中で改行を表現するには、次のように、明示的に改行文字 (`\n`) を含める必要がありました。

~~~ javascript
var msg = "This is a long\n" +
    "long long long long\n" +
    "long long long text.";
~~~

