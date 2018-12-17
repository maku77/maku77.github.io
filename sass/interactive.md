---
title: "Sass のインタラクティブシェルで Sass の演算結果を確認する"
date: "2018-12-17"
---

単体の `sass` コマンドをインストールしておくと、インタラクティブモード（対話モード）で `sass` のプロセッサを起動することができます。
Sass の関数の動作を確認したり、算術演算の結果を確認したいときに便利です。

インタラクティブモードを起動するには、`sass` コマンドの実行時に **`-i`** オプションを指定します。

~~~
$ sass -i
>> 10px + 10px
20px
>> 10px * 10
100px
>> 10px * 10px
100px*px
>> lighten(#8cd, 20%)
#d7eef4
>> darken(#8cd, 20%)
#39aac6
~~~

インタラクティブモードでは、Sass の関数は呼び出せますが、ディレクティブ（`@for` など）は使用できないようです。

~~~
>> @for $i from 1 through 3
SyntaxError: Invalid CSS after "": expected expression (e.g. 1px, bold), was "@for $i from 1 ..."
~~~

