---
title: "任意のオブジェクトを見やすく整形して出力する (pprint)"
date: "2020-01-09"
---

pprint モジュールの基本（pprint.pprint 関数）
----

Python 標準の [pprint モジュール](https://docs.python.org/ja/3/library/pprint.html) を使用すると、任意の Python オブジェクトを見やすく整形して出力することができます（整形結果を文字列として取得することもできます）。

例えば次のようなオブジェクトが定義されているとします。

```python
obj = {
    'title': 'How to use pprint in Python',
    'date': '2020-01-09',
    'tags': ['Python', 'Text processing']
}
```

これを `print(obj)` として出力すると、次のように 1 行でオブジェクトの内容が表示されます。

```python
{'title': 'How to use pprint in Python', 'date': '2020-01-09', 'tags': ['Python', 'Text processing']}
```

代わりに、`pprint` モジュールの **`pprint()`** 関数を使って次のように出力すると、

```python
import pprint

pprint.pprint(obj, indent=4)
```

ディクショナリ要素がキー名でソートされ、長すぎる行が改行されて出力されます。

```python
{   'date': '2020-01-09',
    'tags': ['Python', 'Text processing'],
    'title': 'How to use pprint in Python'}
```

`pprint.pprint()` 関数を使ってオブジェクトを出力した場合でも、出力する内容が短ければ改行は入りません。
`pprint.pprint()` 関数の定義は次のようになっており、デフォルトでは、**80 文字以内** であれば `print` 関数と同様に 1 行で出力されるようになっています。

```python
pprint.pprint(object, stream=None, indent=1, width=80, depth=None, *, compact=False, sort_dicts=True)
```

`indent` オプションでは改行時のインデント幅、`sort_dicts` オプションではディクショナリをソートして出力するかを制御することができます。


オブジェクトの整形結果を文字列として取得する（pprint.pformat 関数）
----

`pprint.pprint()` 関数は、オブジェクトの内容を整形してストリーム（デフォルトでは `sys.stdout`）に出力しますが、代わりに **`pprint.pformat()`** 関数を使用すると、整形結果を文字列として取得することができます。
それ以外は、`pprint.pprint()` と同等です。

```python
s = pprint.pformat(obj, indent=4)
print(s)
```


同じ出力オプションで繰り返し整形出力する（PrettyPrinter クラス）
----

`pprint.pprint()` 関数や `pprint.pformat()` 関数の出力オプションでは、インデント幅 (`indent`) や折り返し文字数 (`width`)、ディクショナリをソートするかどうか (`sort_dicts`) といった指定を行うことができます。
これらの関数を何度も呼び出す場合は、毎回同じ出力パラメータを指定するのが煩わしいかもしれません。

そのような場合は、**`PrettyPrinter`** オブジェクトを生成し、その `pprint()` メソッドを呼び出すようにすると、同じ出力オプションを使いまわすことができます。
`PrettyPrinter` クラスのコンストラクタでは、次のように `pprint.pprint()` 関数と同様の出力オプションを指定することができます。

```python
PrettyPrinter(indent=1, width=80, depth=None, stream=None, *, compact=False, sort_dicts=True)
```

次のサンプルコードでは、最初に出力オプションを指定した `PrettyPrinter` オブジェクトを生成し、それを使って複数のオブジェクトを整形して出力しています。

```python
import pprint

pp = pprint.PrettyPrinter(indent=4, width=40, sort_dicts=False)

# obj1、obj2、obj3 は別の場所で定義されているとします
pp.pprint(obj1)
pp.pprint(obj2)
pp.pprint(obj3)
```

