---
title: コンテナ要素から特定の値を削除する
date: "2004-04-14"
---


list から指定した値の要素を削除する
----

リストから、値が `val` である要素をすべて削除するには下記のように `remove` メソッドを使用します。

```cpp
l.remove(val);
```


vector/deque/string から指定した値の要素を削除する
----

`erase` メンバメソッドと、アルゴリズムの `remove` を組み合わせて使用します。
下記は、`vector` 変数から、値 `value` を持つ要素を削除する例です。

```cpp
v.erase(std::remove(v.begin(), v.end(), value), v.end());
```


連想コンテナから指定した値の要素を削除する
----

map, multimap, set, multiset には、アルゴリズムの `remove` を使用することができません（コンパイルエラーになる）。連想コンテナの中から、特定の値を持つ要素を削除するには、下記のようなメンバメソッドを使用します。

```cpp
     map.erase()
multimap.erase()
     set.erase()
multiset.erase()
```

