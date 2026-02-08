---
title: "JavaScriptメモ: 関数の仮引数の数を取得する"
url: "p/vh735vb/"
date: "2012-11-13"
tags: ["javascript"]
aliases: [/js/syntax/aug-length.html]
---

関数外部から仮引数の数を調べる
----

関数オブジェクトの **`length`** プロパティを参照すると、その関数の仮引数の数を取得することができます。

```javascript
function myfunc(a, b, c) {
}
print(myfunc.length);  //=> 3
```


関数内部から仮引数の数を調べる
----

関数の中から、自分自身の仮引数の数を取得するには **`arguments.callee.length`** を参照します。

```javascript
function myfunc(a, b, c) {
    print(arguments.callee.length);  //=> 3
}
```

{{% note %}}
実引数（実際に呼び出し側から渡された引数）の数を調べるには、**`arguments.length`** を使用することに注意してください。
{{% /note %}}

