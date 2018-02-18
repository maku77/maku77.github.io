---
title: "AppleScript で変数を定義する"
date: "2018-02-18"
description: "AppleScript で変数に値を代入するには、set あるいは copy を使用します。"
---

次の例では、変数 `user` に、文字列 "Maku" を代入しています。

~~~
set user to "Maku"
display dialog "Hello " & user
~~~

`set` の代わりに、`copy` を使用することもできます。

~~~
copy 33 to myAge
~~~

