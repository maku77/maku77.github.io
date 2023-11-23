---
title: "Python で 2 つの dictionary をマージする (update)"
url: "p/ds9wgfz/"
date: "2005-10-20"
lastmod: "2023-11-23"
changed:
  - 2023-11-23: もとの dictionary の内容を変化させずにマージする方法を追加
tags: ["Python"]
aliases: /python/dictionary/update.html
---


dictionary に別の dictionary をマージする
----

Python で 2 つの dictionary オブジェクトをマージするには __`dict.update()`__ メソッドを使用します。
例えば、`d1` という dictionary オブジェクトに、`d2` という dictionary オブジェクトの内容をマージするには次のようにします。

```python
>>> d1 = {'one': 1, 'two': 2, 'org': 'd1'}
>>> d2 = {'three': 3, 'four': 4, 'org': 'd2'}
>>> d1.update(d2)

>>> d1
{'one': 1, 'two': 2, 'org': 'd2', 'three': 3, 'four': 4}

>>> d2
{'three': 3, 'four': 4, 'org': 'd2'}
```

- ポイント
  - `d1` の内容は変化しますが、`d2` の内容は変化しません。
  - `d1` と `d2` に同じキーが存在する場合、`d2` の値で上書きされます（上記の例では `d1['org']` の値が上書きされています）。


もとの ditionary の内容を変化させずに新しい dictionary を作る
----

2 つの dictionary オブジェクト（`dict1` と `dict2`）の内容を変化させずに内容をマージするには、__`**`__ 演算子を使って、次のように新しい dictionary オブジェクトを作成します。
同じキーが存在する場合は、後ろに記述した dictionary の値が優先されます。

```python
>>> dict1 = {'a': 1, 'b': 2}
>>> dict2 = {'b': 3, 'c': 4}
>>> merged_dict = {**dict1, **dict2}

>>> merged_dict
{'a': 1, 'b': 3, 'c': 4}

>>> dict1
{'a': 1, 'b': 2}

>>> dict2
{'b': 3, 'c': 4}
```

別の方法として、__`dict.copy()`__ で dictionary オブジェクトをコピーしておいて、そこに対してマージするという方法もあります。
下記のコードは上記のコードと同じ結果になります。

```python
>>> dict1 = {'a': 1, 'b': 2}
>>> dict2 = {'b': 3, 'c': 4}
>>> merged_dict = dict1.copy()  # dict1 のコピーを作成
>>> merged_dict.update(dict2)   # dict2 をマージ
```

一般的に、`**` 演算子を使用した方が、コードをより簡潔に記述することができます。
この方法を用いれば、3 つ以上の dictionary をマージすることができ、マージの際に他のキーと値を追加することも可能です。

```python
merged_dict = {**dict1, **dict2, **dict3, 'foo': 'bar'}
```

