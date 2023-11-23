---
title: "Python の dictionary（辞書）の基本"
url: "p/zpdyxso/"
date: "2023-11-23"
tags: ["Python"]
changes:
  - 2023-11-23: サンプルコードを拡充
aliases:
  - /python/dictionary/create.html
  - /python/dictionary/get.html
  - /python/dictionary/len.html
  - /python/dictionary/in.html
  - /python/dictionary/del.html
  - /python/dictionary/loop.html
---

Python の dictionary（辞書）は、キーと値のペアで構成されたオブジェクトです。
他の言語では、マップやハッシュと呼ばれていたりします。


dictionary オブジェクトを生成する ({}) {#create}
----

dictionary は __`{}`__ 記号を使って生成できます。
Python の dictionary は、JavaScript のオブジェクトにそっくりです。

### 空の dictionary を作成する

{{< code lang="python" hl_lines="1" >}}
d = {}  # 空の辞書を作成する

print(len(d))   #=> 0
print(type(d))  #=> <class: 'dict'>
{{< /code >}}

### 初期値を持つ dictionary を作成する

dictionary オブジェクトを生成するときに、キーと値のペアを列挙することで、初期値を設定できます。

{{< code lang="python" hl_lines="1" >}}
d = {'one': 1, 'two': 2}  # 初期値を持つ辞書を作成する

print(d['one'])    #=> 1
print(d['two'])    #=> 2
print(d['three'])  #=> KeyError
{{< /code >}}


dictionary の要素を参照・変更する ([], get) {#get}
----

Python で dictionary オブジェクトの要素を参照するには下記のようにします。

```python
val = d['key']  # 要素を参照する
d['key'] = val  # 要素を追加する
```

キーが存在しない場合に、デフォルト値を返したい場合は `get` メソッドを使用します。

```python
val = d.get('key', default=None)
```

- 応用: [dictionary にキーが存在しない場合のみ新しい値を格納する (`setdefault`)](/p/bq6yzpr/)


dictionary の要素数を取得する (len) {#len}
----

Python で dictionary オブジェクトの要素数を調べるには、組み込み関数の __`len`__ を使用します。
`len` 関数は dictionary のメソッドではないことに注意してください。

```python
>>> d = {'one': 1, 'two': 2, 'three': 3}
>>> len(d)
3
```

ちなみに、`list` や `tuple` オブジェクトの要素数や、文字列 (`str`) の文字数を調べる場合も、同様に組み込み関数の `len` を使用します。


dictionary に指定したキーが存在するか調べる (in) {#in}
----

Python の dictionary オブジェクトが、指定したキーを持っているかを判別するには、__`in`__ キーワードを使用します。

```python
if 'key1' in d:
    print('key1 が存在します')
```

{{% note title="has_key メソッドはなくなった" %}}
Python 2 の頃は、下記のように `has_key` メソッドを使用することができましたが、Python 3 以降は `in` キーワードしか使用できなくなりました。

```python
if d.has_key('key1'):
    print('key1 が存在します')
```
{{% /note %}}


dictionary の要素を削除する (del) {#del}
----

Python の dictionary から指定したキーの要素を削除するには、__`del`__ キーワードを使用します。

```python
del d['key']
```

次のサンプルでは、実際に要素が削除されているかを `in` で確認しています。

```python
>>> d = {'key1': 100, 'key2': 200}
>>> 'key1' in d
True
>>> del d['key1']
>>> 'key1' in d
False
```

削除済みの要素を参照しようとするとエラー (`KeyError`) になります。

```python
>>> d['key1']
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
KeyError: 'key1'
```


dictionary をループ処理する {#loop}
----

### 単純なループ

dictionary オブジェクトを `for-in` ステートメントに渡すと、ループ処理でキーを 1 つずつ取り出すことができます。

```python
>>> d = {'one': 1, 'two': 2, 'three': 3}
>>> for key in d:
...     print(key, d[key])
...
two 2
three 3
one 1
```

### キーと値のペアを取り出しながらループ (items)

dictionary オブジェクトをループ処理するときに、キーと値を同時に取り出しながら処理したい時は __`items()`__ メソッドを使用します。

```python
>>> d = {'one': 1, 'two': 2, 'three': 3}
>>> for key, val in d.items():
...     print(key, val)
...
two 2
three 3
one 1
```

ちなみに、Python 2 の頃は `iteritems()` メソッドを使用できましたが、Python 3 からは `items()` メソッドのみ使用可能です。

### 連番を振りながらループ (enumerate)

__`enumerate`__ と組み合わせることで、0 から始まる連番を振りながらループ処理することができます（`list` オブジェクトも同様のループ処理が可能です）。

```python
>>> for i, key in enumerate(d):
...     print('[{}] {}: {}'.format(i, key, d[key]))
...
[0] one: 1
[1] two: 2
[2] three: 3
```

### キーでソートしてループ処理する

- [dictionary の要素をソートして出力する](/p/qqkggoz/)

