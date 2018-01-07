---
title: 関数を定義する (function)
date: "2017-03-29"
---

関数定義の基本
----

`function ~ end` の構文を使用して、独自の関数を定義することができます。
例えば、渡された数値の２乗を返す関数 `myfunc` は下記のように定義できます。

~~~ matlab
function ret = myfunc(x)
    ret = x ** 2;
end
~~~

#### 使用例

~~~ matlab
y = myfunc(7);  %=> 49
~~~

戻り値は `return` で指定するのではなく、関数値用に宣言した変数に代入することで表現することに注意してください。
上記の例では、関数値を表す変数は `ret` という名前で定義していますが、任意の名前を付けることができます。
もちろん、関数を途中で抜けるときは `return` を使用することができますが、その場合も戻り値はあらかじめ関数値用の変数に格納しておく必要があります。

~~~ matlab
function y = f(x)
    if x == 0
        y = -1;    % 戻り値は return では返せない
        return
    end
    ...
end
~~~


戻り値を持たない関数を定義する
----

戻り値を持たない関数（サブルーチンというのが正しいかも）も、同様に定義できます。
内部で出力処理まで終わらせてしまうような関数がこれにあたります。

~~~ matlab
function greet(name)
    printf('Hello %s\n', name);
end
~~~


多値関数（複数の戻り値を持つ関数）を定義する
----

関数値としてベクトルを返すように定義すれば、複数の戻り値を持つ関数として扱うことができます。
下記の例では、除算結果の商と剰余を返す関数を定義しています。

~~~ matlab
function [quotient, remainder] = div(x, y)
    quotient = floor(x / y);
    remainder = rem(x, y);
end
~~~

#### 使用例

~~~ matlab
[a, b] = div(10, 3);
printf('a=%d, b=%d\n', x, y);  % a=3, b=1
~~~


オプショナル引数、デフォルト引数を扱う
----

パラメータ名の後にデフォルト値を指定しておくことができます。
パラメータを省略して関数を呼び出した場合に、デフォルト値が使用されます。

~~~ matlab
function greet(name = 'maku')
    printf('Hello %s\n', name);
end
~~~

#### 使用例

~~~ matlab
greet();       %=> Hello maku
greet('tom');  %=> Hello tom
~~~

