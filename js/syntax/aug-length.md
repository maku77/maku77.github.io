---
title: "関数の仮引数の数を取得する"
date: "2012-11-13"
---

関数外部から仮引数の数を調べる
----

関数オブジェクトの **length** プロパティを参照すると、その関数の仮引数の数を取得することができます。

~~~ javascript
function myfunc(a, b, c) {
}
print(myfunc.length);  //=> 3
~~~


関数内部から仮引数の数を調べる
----

関数の中から、自分自身の仮引数の数を取得するには **arguments.callee.length** を参照します。

~~~ javascript
function myfunc(a, b, c) {
    print(arguments.callee.length);  //=> 3
}
~~~

<div class="note">
実引数（実際に呼び出し側から渡された引数）の数を調べるには、<b>arguments.length</b> を使用することに注意してください。
</div>

