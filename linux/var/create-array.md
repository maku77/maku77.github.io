---
title: "シェルスクリプト: 配列を作成する"
date: "2012-02-02"
---

Bash シェルスクリプト内で配列変数を作成するサンプルです。
下記の例では、配列変数 `arr` に 3 つの要素を格納する方法を示しています。


インデックスを指定して1つずつ代入していく方法
----

~~~ bash
arr[0]=AAA
arr[1]=100
arr[2]='Hello world'

echo ${arr[0]}
echo ${arr[1]}
echo ${arr[2]}
~~~

#### 実行結果

~~~
AAA
100
Hello world
~~~


配列の初期化時に要素をまとめて指定する方法
----

~~~ bash
arr=(AAA BBB CCC)

echo ${arr[0]}
echo ${arr[1]}
echo ${arr[2]}
~~~

#### 実行結果

~~~
AAA
BBB
CCC
~~~

以下のように、複数行にまたいで値を記述することもできます。

~~~ bash
arr=(
  AAA
  BBB
  CCC
)
~~~

