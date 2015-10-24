---
title: Groovy でリストを扱う
created: 2015-07-08
layout: gradle
---

Groovy のリストの基本
----
Groovy では、`[1, 2, 3]` といった構文を使用して、簡単に List インスタンスを生成することができます。
内部的には `java.util.ArrayList` のインスタンスが生成されます。
下記の例では、3 つの要素を持つ List インスタンスを生成しています。

```groovy
def list = [100, 'AAA', 0.25]
assert list.getClass() == java.util.ArrayList
assert list.size() == 3
assert list[0] == 100
```

リストへの要素の追加
----
リストの末尾に要素を追加する場合、`<<` というショートカット記法を使用することができます。

```groovy
def list = []
list << 100
list << 200
list << 300
println list    //=> [100, 200, 300]
```

これは、下記のように `add` メソッドを呼び出すのと同等の動作をします。

```groovy
def list = []
list.add 100
list.add 200
list.add 300
println list    //=> [100, 200, 300]
```

あまり違いはないように見えますが、`<<` を使った方が可読性は若干上がるようです。
また、`<<` を使うことにより、下記のように連続して要素を追加することが可能です。

```groovy
def list = []
list << 100 << 200 << 300
println list
```

リストの要素を列挙する
----
リスト内の要素をイテレートしたい場合は、下記のように `each` メソッドを使用できます。

```groovy
def list = [100, 'AAA', 0.25]
list.each { elem ->
    println elem
}
```

上記の例では、イテレート用の変数を明示していますが、下記のようにデフォルトの `it` 変数を使用することもできます。

```groovy
list.each {
    println it
}
```


負のインデックス、範囲外インデックスの扱い
----
負のインデックスを指定することで、末尾から数えた位置の要素を取得することができます。

```groovy
def list = [100, 200, 300]
assert list[-1] == 300
```

存在しない位置のインデックスを指定した場合は、Java のように `ArrayIndexOutOfBoundsException` 例外が発生することはなく、単純に `null` が返されます。

```groovy
def list = [100, 200, 300]
assert list[3] == null
```


