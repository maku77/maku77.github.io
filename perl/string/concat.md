---
title: "文字列を結合する (.)"
date: "2008-03-09"
---

Perl 言語で文字列を結合するには、`.`（ピリオド）演算子を使用します。

~~~ perl
'Hello' . 'World'        #=> 'HelloWold'
'Hello' . ' ' . 'World'  #=> 'Hello World'
~~~

文字列変数に対して、別の文字列を末尾に追加したいときは、`.=` 演算子を使用すると簡潔に記述することができます。

~~~ perl
$str = 'Hello';
$str .= ' World';  #=> 'Hello World'
~~~

