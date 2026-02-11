---
title: "JavaScriptメモ: 可変長引数を扱う関数を定義する (arguments)"
url: "p/dzn6jyi/"
date: "2012-11-14"
tags: ["javascript"]
aliases: [/js/syntax/variable-length-argument.html]
---

関数の中で `arguments` プロパティを参照することで、実際に渡された引数（実引数）の数や値を取得することができます。
これを利用して、可変長のパラメータを扱うことができます。
関数定義のパラメータ（仮引数）として何も記述されていなくても、`arguments` プロパティは参照することができます。

```javascript
function hoge() {
  for (let i = 0, len = arguments.length; i < len; ++i) {
    console.log(arguments[i]);
  }
}
hoge('AAA', 'BBB', 'CCC');
```

{{< code title="実行結果" >}}
AAA
BBB
CCC
{{< /code >}}

`arguments` は配列と同様の `length` プロパティを持っていますが、実際には配列ではありません。
あくまで、配列っぽいオブジェクトなので、配列と同様の操作がすべて有効なわけではないことに注意してください。

```javascript
function hoge(a, b, c) {
  console.log(Array.isArray(arguments));  //=> false
  console.log(typeof arguments);          //=> object
}
```
