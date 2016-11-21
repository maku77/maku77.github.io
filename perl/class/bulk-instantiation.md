---
title: 複数のオブジェクトを一度に作成する
created: 2008-06-24
---

下記の例は、3 つのインスタンスを一つずつ作成して配列に代入しています。

```perl
my $objects[0] = MyClass->new('obj1');
my $objects[1] = MyClass->new('obj2');
my $objects[2] = MyClass->new('obj3');
```

このように、パラメータ以外が共通である処理を記述するときは、`map` を利用して次のように簡潔に記述することができます。

```perl
my @objects = map MyClass->new($_), ('obj1', 'obj2', 'obj3');
```

