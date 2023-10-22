---
title: "Octave の制御命令: for / while / until ループ"
url: "p/h9pgnw6/"
date: "2017-03-27"
tags: ["Octave"]
aliases: /octave/syntax/loop.html
---

Octave のループ構文は、下記のようなものが用意されています。

- __`for`__ ループ
- __`while`__ ループ
- __`do ~ until`__ ループ

Octave には `do ~ while` ループや、単純な `until` ループは存在しないことに注意してください。
C++ や Java と同様に、各ループ処理の中では、__`continue`__ によるループ継続や、__`break`__ によるループ脱出を行えます。


for ループ
----

```matlab
for x = ベクトル
    ...
end
```

とすると、ベクトル要素（あるいは行列要素）を 1 つずつ取り出しながらループ処理を行うことができます。
下記の例では、1～5 までの値を順番に足しこんでいく様子を表示しています。

{{< code lang="matlab" title="1 ~ 5 まで合計する" >}}
sum = 0
for x = 1:5
    sum += x;
    disp(sum)
end
{{< /code >}}

{{< code title="実行結果" >}}
1
3
6
10
15
{{< /code >}}

`for` ループは入れ子構造で記述することもできます。
下記は 2 重ループで 3x3 の行列を初期化しています。

{{< code lang="matlab" title="3x3 行列の初期化" >}}
for i = 1:3
    for j = 1:3
        A(i, j) = i * j;
    end
end
disp(A)
{{< /code >}}

{{< code title="実行結果" >}}
   1   2   3
   2   4   6
   3   6   9
{{< /code >}}

次の例では、行列 A の各要素をループでひとつずつ取得して表示しています。
組み込み関数の `size` は行列のサイズを返してくれますが、第２パラメータで 1 と 2 を指定することで、それぞれ行数、列数を返してくれるようになります。
これを利用して、正しい数だけループ処理しています。

{{< code lang="matlab" title="行列の要素を行ごとにループ処理" >}}
A = [1 2 3; 4 5 6];
for i = 1:size(A, 1)  % 行の数だけループ（２回）
    for j = 1:size(A, 2)  % 列の数だけループ（３回）
        disp(sprintf('A(%d,%d)=%d', i, j, A(i,j)))
    end
end
{{< /code >}}

{{< code title="実行結果" >}}
A(1,1)=1
A(1,2)=2
A(1,3)=3
A(2,1)=4
A(2,2)=5
A(2,3)=6
{{< /code >}}

下記のように行、列のサイズを別々の変数に取得してしまうのもアリですね。

```matlab
[i_max, j_max] = size(A);
for i = 1:i_max
    for j = 1:j_max
        disp(A(i,j))
    end
end
```


while ループ
----

`while` ループでは、指定した条件を満たす限りループ処理が継続されます。

```matlab
i = 0;
while (i < 10)
    ++i
endwhile
```

条件式に `true` を指定すれば、無限ループを作成することができますが、その場合はループ内で何らかの条件で `break` しなければいけません。
下記の例では、ユーザが 10 以上の数値を入力するまで、ユーザからの入力を求め続けます。

{{< code lang="matlab" title="10 以上の値が入力されるまで繰り返す" >}}
while true
    num = input('num? ');
    if (num > 10) break end
endwhile
{{< /code >}}

Octave の `if` ブロックは、上記のように必ず `end` で終わらなければいけないことに注意してください（`end` を省略することはできません）。


do ～ until ループ
----

`do ~ until` ループは、ある条件を満たすまでループを実行します。
条件判定はブロック内の処理を実行してから行われるため、必ず 1 回はブロック内の処理が実行されます。

{{< code lang="matlab" title="i が 10 以上になるまでループ処理" >}}
i = 0;
do
    ++i
until (i >= 10)
{{< /code >}}

