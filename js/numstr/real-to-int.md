---
title: "実数の小数点数以下を丸めて整数に変換する (floor, ceil, round)"
date: "2018-05-17"
---

小数点数以下を切り下げる (Math.floor)
----

`Math.floor` は、指定した実数を上回らない最大の整数を返します。

~~~ javascript
Math.floor(3)     //=> 3
Math.floor(3.1)   //=> 3
Math.floor(-3)    //=> -3
Math.floor(-3.1)  //=> -4
~~~


小数点数以下を切り上げる (Math.ceil)
----

`Math.ceil` は、指定した実数を下回らない最小の整数を返します。

~~~ javascript
Math.ceil(3)     //=> 3
Math.ceil(3.1)   //=> 4
Math.ceil(-3)    //=> -3
Math.ceil(-3.1)  //=> -3
~~~


小数点数以下を四捨五入する (Math.round)
----

`Math.round` は、小数点数以下を四捨五入した整数を返します。

~~~ javascript
Math.round(3)    //=> 3
Math.round(3.1)  //=> 3
Math.round(3.5)  //=> 4
Math.round(3.7)  //=> 4
~~~

負の値に対して実行した場合は、小数点数以下が 0.5 の場合に繰り上げになるので注意してください。
小数点数以下が 0.5 に到達する度に、大きい整数に繰り上がると考えるとわかりやすいです。

~~~ javascript
Math.round(-3)    //=> -3
Math.round(-3.1)  //=> -3
Math.round(-3.5)  //=> -3 （注意）
Math.round(-3.6)  //=> -4
~~~


小数点数以下を削除する (parseInt)
----

`parseInt` は、小数点数以下がなかったことにして整数値を返します。

~~~ javascript
parseInt(3.1)   //=> 3
parseInt(3.6)   //=> 3
parseInt(-3.1)  //=> -3
parseInt(-3.6)  //=> -3
~~~


小数点以下 n 桁までの文字列表現を取得する (toFixed)
----

こちらの記事を参照: [数値の小数点以下 N 桁までに丸めて表示する (toFixed)](./tofixed.html)

