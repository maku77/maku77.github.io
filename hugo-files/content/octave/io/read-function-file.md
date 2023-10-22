---
title: "Octave でファイルに記述した関数を実行する（関数ファイル）"
url: "p/o9qhvyu/"
date: "2017-03-31"
tags: ["Octave"]
aliases: /octave/io/read-function-file.html
---

関数ファイルの基本
----

Octave で読み込む外部ファイル (`.m`) が、関数定義 (`function`) で始まっていると、そのファイルは __関数ファイル__ として認識され、組み込み関数のように呼び出せるようになります。

関数ファイルの名前は、ファイルの先頭で定義する関数名に合わせます。
次の例では `myfunc` という関数を定義しているので、ファイル名は `myfunc.m` とします。

{{< code lang="matlab" title="myfunc.m" >}}
function y = myfunc(x)
    y = x**2;
end
{{< /code >}}

上記のように関数ファイルで定義した関数は、組み込み関数と同じ感覚で呼び出すことができます。

```matlab
>> myfunc(7)
49
```


サブファンクション
----

関数ファイルで定義された関数は、先頭で定義した関数だけが公開されます。
例えば、下記の関数ファイルでは、`hello` 関数と `hello2` 関数を定義していますが、外部から呼び出せる関数は `hello` 関数だけであり、__`hello2` 関数はファイル内からのみ呼び出せるサブファンクション__ として定義されます。

{{< code lang="matlab" title="hello.m" >}}
% 先頭で定義されているこの関数だけが公開される
function hello()
    disp('hello');
    hello2();
end

% 以降の関数はサブファンクションとみなされて外部には公開されない
function hello2()
    disp('hello2')
end
{{< /code >}}

{{< code lang="matlab" title="実行例" >}}
>> hello
hello
hello2

>> hello2    % サブファンクションは外からは直接呼び出せない
error: 'hello2' undefined near line 1 column 1
{{< /code >}}


プライベートファンクション
----

サブファンクションは、定義したファイル内でしか呼び出すことができないので、いろいろな関数から呼び出したいヘルパー関数を作成したいときには都合が悪いです。
このような場合は、ヘルパー関数を __プライベートファンクション__ として別の関数ファイルに分離しておきます。
作成方法は通常の関数ファイルと同様ですが、作成したファイルを __`private`__ ディレクトリ内に格納するようにします。

下記の例では、通常の関数ファイル `hello.m` から、プライベートファンクションとして定義された `private_func` を呼び出しています。

{{< code lang="matlab" title="hello.m" >}}
function hello()
    disp('hello');
    private_func();
end
{{< /code >}}

{{< code lang="matlab" title="private/private_func.m" >}}
function private_func()
    disp('private_func');
end
{{< /code >}}

{{< code lang="matlab" title="実行例" >}}
>> hello()
hello
private_func
{{< /code >}}


1 つのファイルで複数の関数を公開する
----

関数ファイルでは、先頭で定義した関数しか公開されませんが、複数の関数定義を含んだファイルをスクリプトとして実行してしまうことは可能です。
例えば、下記のように、関数 `foo` と関数 `bar` の定義を含んだスクリプトを用意して、これをスクリプトとして読み込めば、2 つの関数を外部から呼び出せるようになります。

{{< code lang="matlab" title="functions.m" >}}
1;  % Load as a script

function foo()
    disp('foo');
end

function bar()
    disp('bar');
end
{{< /code >}}

先頭が `function` 定義で始まってしまうと、関数ファイルとして認識されてしまうので、ダミーの __`1;`__ というコードを入れて、強制的にスクリプトファイルとして処理されるようにしていることに注意してください。
あとは、このスクリプトを実行すれば、2 つの関数（`foo` と `bar`）を任意のタイミングで呼び出せるようになります。

{{< code lang="matlab" title="実行例" >}}
>> functions  % スクリプトとして functions.m を実行
>> foo()
foo
>> bar()
bar
{{< /code >}}

