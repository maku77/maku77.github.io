---
title: "Python で JSON 形式のテキストファイルを読み書きする (json.load, json.dump)"
url: "p/xhyhzfv/"
date: "2014-11-28"
lastmod: "2021-07-04"
tags: ["Python", "JSON"]
aliases: /python/io/json-load.html
---

JSON ファイルを読み込む (json.load)
----

Python に付属している `json` ライブラリが提供する [json.load 関数](https://docs.python.org/ja/3/library/json.html#json.load)を使用すると、JSON 形式のテキストファイルを読み込んで、Python のオブジェクトを生成することができます。
`json.load` 関数のパラメーターには、ファイル名ではなく、ファイルオブジェクト（`read` 関数を持つオブジェクト）を渡すことに注意してください。

{{< code lang="json" title="入力ファイル (input.json)" >}}
{
  "aaa": 100,
  "bbb": 200,
  "ccc": 300
}
{{< /code >}}

{{< code lang="python" title="sample.py" hl_lines="6" >}}
import json

def load_json(filename):
    """JSON ファイルを読み込んで Python オブジェクトとして返します。"""
    with open(filename, encoding='utf-8') as f:
        return json.load(f)

# テスト
obj = load_json("input.json")
print(obj['aaa'])  #=> 100
print(obj['bbb'])  #=> 200
print(obj['ccc'])  #=> 300
{{< /code >}}

JSON ファイル内の各要素は、次のような対応付けで Python オブジェクトに変換されます。

| JSON の型 | Python の型 |
| ---- | ---- |
| object | `dict` |
| array | `list` |
| string | `str` |
| number | `int` / `float` |
| true / false | `True` / `False` |
| null | `None` |

JSON ファイルではなく、JSON 形式の文字列を読み込みたいときは、`json.load` の代わりに __`json.loads`__ 関数を使用します。

- 参考: [JSON 形式のテキストと Python オブジェクトの相互変換 (`json.loads`, `json.dumps`)](/p/gwfweub/)


JSON ファイルに書き出す (json.dump)
----

### 基本

Python のオブジェクトを JSON 形式のテキストファイルに書き出すには、[json.dump 関数](https://docs.python.org/ja/3/library/json.html#json.dump) を使用します。
こちらも、パラメーターとしてはファイル名ではなく、ファイルオブジェクト（`write` 関数を持つオブジェクト）を渡すことに注意してください。

{{< code lang="python" title="sample.py" hl_lines="6" >}}
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
{{< /code >}}

{{< code lang="json" title="出力結果 (output.json)" >}}
{"ccc": 300, "aaa": 100, "bbb": 200, "data": [1, 2, 3]}
{{< /code >}}

### 出力形式をカスタマイズする (indent, sort_keys)

デフォルトでは、上記のように改行なしのコンパクトな出力になります。
改行やインデントを入れて出力したい場合は、`json.dump` 関数の引数で、__`indent=2`__ のようにインデントサイズを指定します。
また、Python 3.7 以降は辞書オブジェクト (`dict`) のキーは、挿入された順に出力されるようになっています（デフォルトで [collections.OrderedDict](https://docs.python.org/ja/3/library/collections.html#collections.OrderedDict) 相当の動きになりました）。
アルファベット順に出力したい場合は、__`sort_keys=True`__ を指定します。

{{< code lang="python" title="sample.py（修正箇所のみ抜粋）" >}}
json.dump(obj, fp, indent=2, sort_keys=True)
{{< /code >}}

{{< code lang="json" title="出力結果 (output.json)" >}}
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
{{< /code >}}

辞書オブジェクトのキーが追加順に出力されるのは地味に便利で、例えば、`id` プロパティを先頭に出力したい場合は、単純にプログラム内で `id` を最初に追加するだけで済みます。

```python
book1 = {'id': 1, 'author': 'Author-1', 'title': 'Title-1'}
book2 = {'id': 2, 'author': 'Author-2', 'title': 'Title-2'}
book3 = {'id': 3, 'author': 'Author-3', 'title': 'Title-3'}
books = [ book1, book2, book3 ]

save_json('books.json', books)
```

要素の順序をもっと細かく制御する方法は、下記の記事を参考にしてください。

- [dictionary の内部的な要素順序を変更する](/p/vexfweu/)

### 日本語をそのまま出力する (ensure_ascii)

`json.dump` 関数は、デフォルトで日本語を Unicode エスケープして出力しようとします（例えば、`あ` は `\u3042` になります）。
この振る舞いを抑制して、日本語のまま出力するには、引数で __`ensure_ascii=False`__ を指定します。

{{< code lang="python" hl_lines="5" >}}
import json

def save_json(filename, obj):
    with open(filename, 'w', encoding='utf-8', newline='\n') as fp:
        json.dump(obj, fp, ensure_ascii=False)

obj = {'a': 'あいう'}
save_json('output.json', obj)
{{< /code >}}

### JSON 文字列に変換する

JSON ファイルに出力するのではなく、JSON 形式の文字列を取得したいときは、`json.dump` の代わりに __`json.dumps`__ 関数を使用します。

- 参考: [JSON 形式のテキストと Python オブジェクトの相互変換 (`json.loads`, `json.dumps`)](/p/gwfweub/)

