---
title: クラスの属性に名前でアクセスする
created: 2007-03-31
---

名前で属性にアクセスすることにより、属性の名前を配列で用意してループで処理したり、あらかじめ属性の名前が決められない場合にも後付けで属性を追加したりすることができます。

ビルトイン関数 getattr を使う方法
====
あるオブジェクト `obj` の属性 `hoge` にアクセスするには、通常

```python
obj.hoge
```

としますが、組み込み関数の **getattr** を使えば、次のように名前で参照することができます。

```python
getattr(obj, 'hoge')
```

属性値を参照するための **getattr** と、設定するための **setattr** がそれぞれ用意されています。

```python
getattr(object, name [, default])
setattr(object, name, value)
```

`getattr` で指定した名前の属性がみつからない場合は、`default` パラメータで指定した値が返されます。
`default` パラメータを省略した場合は、`AttributeError` が発生します。

#### 実際の使用例:
```python
>>> class A:
...     pass
...
>>> a = A()
>>> a.one = 1
>>> print getattr(a, 'one')
1
>>> setattr(a, 'two', 2)
>>> print a.two
2
```


`__dict__` 属性を使用する方法
====

```python
>>> class A:
...     pass
...
>>> a = A()
>>> a.__dict__['one'] = 1
>>> print a.one
1
```

