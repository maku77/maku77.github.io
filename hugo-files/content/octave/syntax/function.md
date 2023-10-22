---
title: "Octave で関数を定義する (function)"
url: "p/vo54icm/"
date: "2017-03-29"
tags: ["Octave"]
aliases: /octave/syntax/define-function.html
---

関数定義の基本
----

Octave で関数を定義するには、__`function ~ end`__ の構文を使用します。
例えば、渡された数値の 2 乗を返す関数 `myfunc` は下記のように定義できます。

{{< code lang="matlab" title="myfunc 関数を定義する" >}}
function ret = myfunc(x)
    ret = x ** 2;
end
{{< /code >}}

{{< code lang="matlab" title="myfunc 関数を呼び出す" >}}
y = myfunc(7);  %=> 49
{{< /code >}}

戻り値は `return` で指定するのではなく、関数値用に宣言した変数に代入することで表現することに注意してください。
上記の例では、関数値を表す変数は `ret` という名前で定義していますが、任意の名前を付けることができます。
もちろん、関数を途中で抜けるときは `return` を使用することができますが、その場合も戻り値はあらかじめ関数値用の変数に格納しておく必要があります。

{{< code lang="matlab" title="途中で関数を抜ける場合" >}}
function y = f(x)
    if x == 0
        y = -1;    % 戻り値は return では返せない
        return
    end
    ...
end
{{< /code >}}


戻り値を持たない関数を定義する
----

戻り値を持たない関数も同様に定義できます（関数というよりサブルーチンというのが正しいのかも）。
内部で出力処理まで終わらせてしまうような関数がこれにあたります。

```matlab
function greet(name)
    printf('Hello %s\n', name);
end
```


多値関数（複数の戻り値を持つ関数）を定義する
----

関数値としてベクトルを返すように定義すれば、複数の戻り値を持つ関数として扱うことができます。
下記の例では、除算結果の商と剰余を返す関数を定義しています。

{{< code lang="matlab" title="2 つの値を返す関数" >}}
function [quotient, remainder] = div(x, y)
    quotient = floor(x / y);
    remainder = rem(x, y);
end
{{< /code >}}

{{< code lang="matlab" title="呼び出し方" >}}
[a, b] = div(10, 3);
printf('a=%d, b=%d\n', x, y);  % a=3, b=1
{{< /code >}}


パラメーターにデフォルト値（オプショナル引数）を設定する
----

パラメーター名の後にデフォルト値を指定しておくことができます。
パラメーターを省略して関数を呼び出した場合に、デフォルト値が使用されます。

{{< code lang="matlab" title="デフォルト値を持つパラメーター" >}}
function greet(name = 'Maku')
    printf('Hello, %s\n', name);
end
{{< /code >}}

{{< code lang="matlab" title="呼び出し方" >}}
greet();        %=> Hello, Maku
greet('John');  %=> Hello, John
{{< /code >}}

