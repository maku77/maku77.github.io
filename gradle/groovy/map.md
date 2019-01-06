---
title: "Groovy で Map を扱う"
date: "2015-07-08"
---

Groovy の Map の基本
----
Groovy の Map インスタンスは、List インスタンスと同様に `[` と `]` を使用して定義することができます。
ただし、各要素はコロン (`:`) で区切って、キーと値を指定します。
内部的には、`java.util.LinkedHashMap` インスタンスが生成されています。

```groovy
def map = [xxx:100, yyy:200, zzz:300]

assert map.getClass() == java.util.LinkedHashMap
assert map.size() == 3
assert map.xxx == 100
assert map['xxx'] == 100
```

Map の要素を列挙する
----
Map の要素は、List の要素と同様に `each` メソッドを使ってイテレートすることができます。

```groovy
map.each { key, val ->
    println key + ':' + val
}
```

空の Map インスタンスを生成する
---

空の Map インスタンスを生成するときは、ちょっと特殊ですが下記のように記述する必要があります。

```groovy
def map = [:]  // 空の Map
```

真ん中のコロン `:` を省略してしまうと、List オブジェクトの生成とみなされてしまいます。

```groovy
def list = []  // 空の List
```

ちなみに、Ruby や Python におけるハッシュの生成では、リストの生成とは異なる `{}` という記号を使用することで、生成すべきオブジェクトがハッシュなのかリストなのかを区別するようになっています。
Groovy ではハッシュとリストの生成に同じ記号 `[]` を用いる仕様なので、上記のような細かい制約が出てくるんですね。

