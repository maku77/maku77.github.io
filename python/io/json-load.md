---
title: "JSON 形式のテキストファイルを読み書きする (json.load, json.dump)"
date: "2014-11-28"
lastmod: "2021-07-04"
---

JSON ファイルを読み込む (json.load)
----

Python に付属している `json` ライブラリが提供する [json.load 関数](https://docs.python.org/ja/3/library/json.html#json.load)を使用すると、JSON 形式のテキストファイルを読み込んで、Python のオブジェクトを生成することができます。
`json.load` 関数のパラメーターには、ファイル名ではなく、ファイルオブジェクト（`read` 関数を持つオブジェクト）を渡すことに注意してください。

### 入力ファイル (input.json)

```json
{
  "aaa": 100,
  "bbb": 200,
  "ccc": 300
}
```

### sample.py

```python
import json

def load_json(filename):
    """JSON ファイルを読み込んで Python オブジェクトとして返します。"""
    with open(filename, encoding='utf-8') as f:
        return json.load(f)

# テスト
obj = load_json('input.json')
print(obj['aaa'])  #=> 100
print(obj['bbb'])  #=> 200
print(obj['ccc'])  #=> 300
```

JSON ファイル内の各要素は、次のようなマッピングで Python オブジェクトに変換されます。

| JSON の型 | Python の型 |
| ---- | ---- |
| object | `dict` |
| array | `list` |
| string | `str` |
| number | `int` or `float` |
| true | `True` |
| false | `False` |
| null | `None` |


JSON ファイルに書き出す (json.dump)
----

Python のオブジェクトを JSON 形式のテキストファイルに書き出すには、[json.dump 関数](https://docs.python.org/ja/3/library/json.html#json.dump) を使用します。
こちらも、パラメーターとしてはファイル名ではなく、ファイルオブジェクト（`write` 関数を持つオブジェクト）を渡すことに注意してください。

#### sample.py

```python
import json

def save_json(filename, obj):
    """Python オブジェクトの内容を JSON ファイルに保存します。"""
    with open(filename, 'w', encoding='utf-8', newline='\n') as fp:
        json.dump(obj, fp)

# テスト
obj = {
    'ccc': 300,
    'aaa': 100,
    'bbb': 200,
    'data': [1, 2, 3]
}
save_json('output.json', obj)
```

#### 出力結果 (output.json)

```json
{"ccc": 300, "aaa": 100, "bbb": 200, "data": [1, 2, 3]}
```

デフォルトでは、上記のように改行なしのコンパクトな出力になります。
改行やインデントを入れて出力したい場合は、`json.dump` 関数の引数で、__`indent=2`__ のようにインデントサイズを指定します。
また、Python 3.7 以降は辞書オブジェクト (`dict`) のキーは、挿入された順に出力されるようになっています（デフォルトで [collections.OrderedDict](https://docs.python.org/ja/3/library/collections.html#collections.OrderedDict) 相当の動きになりました）。
アルファベット順に出力したい場合は、__`sort_keys=True`__ を指定します。

#### sample.py（修正部分抜粋）

```python
json.dump(obj, fp, indent=2, sort_keys=True)
```

#### 出力結果 (output.json)

```json
{
  "aaa": 100,
  "bbb": 200,
  "ccc": 300,
  "data": [
    1,
    2,
    3
  ]
}
```

辞書オブジェクトのキーが追加順に出力されるのは地味に便利で、例えば、`id` プロパティを先頭に出力したい場合は、単純にプログラム内で `id` を最初に追加するだけで済みます。

```python
book1 = {'id': 1, 'author': 'Author-1', 'title': 'Title-1'}
book2 = {'id': 2, 'author': 'Author-2', 'title': 'Title-2'}
book3 = {'id': 3, 'author': 'Author-3', 'title': 'Title-3'}
books = [ book1, book2, book3 ]

save_json('books.json', books)
```

要素の順序をもっと細かく制御する方法は、下記の記事を参考にしてください。

- [dictionary の内部的な要素順序を変更する](../dictionary/ordered-dic.html)


（参考）ファイルではなく JSON 形式の文字列を扱う場合
----

`json.load` と `json/dump` に似た関数に、`json.loads` と `json.dumps` がありますが、これらは JSON 形式の「ファイル」ではなく「文字列」を扱います。
`json.loads` と `json.dumps` の使い方は下記の記事を参考にしてください。

- [JSON 形式のテキストと Python オブジェクトの相互変換 (json.loads, json.dumps)](../numstr/json-to-python.html)

