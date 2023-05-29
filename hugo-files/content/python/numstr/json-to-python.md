---
title: "JSON 形式のテキストと Python オブジェクトの相互変換 (json.loads, json.dumps)"
url: "p/gwfweub/"
date: "2013-05-22"
lastmod: "2021-07-24"
tags: ["Python", "JSON"]
aliases: /python/numstr/json-to-python.html
---

Python には JSON フォーマットを扱うための __`json`__ モジュールが標準搭載（Python 2.6 以降）されており、JSON 形式のテキストと、Python のオブジェクトを相互に変換することができます。


JSON 文字列 → Python オブジェクト (json.loads)
----

JSON 形式の文字列データから、Python オブジェクトを作成するには [json.loads 関数](https://docs.python.org/ja/3/library/json.html#json.loads) を使用します。

{{< code lang="python" hl_lines="3" >}}
import json

data = json.loads('{"key":"value"}')
print(data['key'])  #=> 'value'
{{< /code >}}

`json.loads` と似た関数に、`json.load` 関数がありますが、こちらは文字列ではなく JSON ファイルを読み込みます。
`loads` の末尾の `s` は、文字列を受け取ることを示しています。

- 参考: [JSON 形式のテキストファイルを Python オブジェクトとして読み込む (json.load)](/p/xhyhzfv/)


Python オブジェクト → JSON 文字列 (json.dumps)
----

逆に、Python オブジェクトから JSON 形式のテキストを生成するには、[json.dumps 関数](https://docs.python.org/ja/3/library/json.html#json.dumps) を使用します。

{{< code lang="python" hl_lines="4" >}}
import json

obj = {'aaa':100, 'bbb':200}
json_str = json.dumps(obj)
print(json_str)
{{< /code >}}

{{< code lang="json" title="実行結果" >}}
{"bbb": 200, "aaa": 100}
{{< /code >}}

### 整形して出力する (indent=2)

改行を入れた形で見やすく出力したい場合は、`json.dumps()` 関数の __`indent`__ パラメータに、インデントのスペース数を指定します（Python 3.2 以降は、インデントに使用する文字列そのものを指定できるようになりました）。

{{< code lang="python" hl_lines="2" >}}
obj = {'aaa':100, 'bbb':200}
json_str = json.dumps(obj, indent=2)
print(json_str)
{{< /code >}}

{{< code lang="json" title="実行結果" >}}
{
  "bbb": 200,
  "aaa": 100
}
{{< /code >}}

### キーでソートして出力する (sort_keys=True)

デフォルトでは、`json.dumps()` は辞書オブジェクトの要素を追加順に出力します（Python 3.7 より前は不定でした）。
アルファベット順にキー名でソートして出力したい場合は、`json.dumps()` 関数の __`sort_keys`__ パラメータを `True` に設定します。

```python
print(json.dumps(obj, indent=2, sort_keys=True))
```

{{< code lang="json" title="実行結果" >}}
{
  "aaa": 100,
  "bbb": 200
}
{{< /code >}}

### 日本語を Unicode エスケープしない (ensure_ascii=False)

日本語を含んだオブジェクトを `json.dumps` で文字列に変換すると、デフォルトで Unicode エスケープされます。

```python
obj = {'a': 'あいう'}
print(json.dumps(obj))  #=> {"a": "\u3042\u3044\u3046"}
```

これは、XSS などの脆弱性を防ぐためですが、使用用途が限られているのであれば、UTF-8 エンコーディングの日本語をそのまま出力してしまった方がわかりやすいです。
非 ASCII 文字の Unicode エンコーディングを停止するには、__`ensure_ascii=False`__ オプションを指定します。

```python
obj = {'a': 'あいう'}
print(json.dumps(obj, ensure_ascii=False))  #=> {"a": "あいう"}
```


（おまけ） JSON 文字列を整形する関数を作ってみる
----

次の `pretty_json()` 関数は、JSON 形式の文字列データを、きれいにインデントや改行を入れた形に整形します。
まず、`json.loads` で「JSON文字列→オブジェクト」の変換をしてから、`json.dumps` で「オブジェクト→JSONテキスト」と逆変換しています。

{{< code lang="python" title="sample.py" >}}
import json

def pretty_json(json_text):
    """JSON 文字列を読みやすく整形して返します。"""
    temp = json.loads(json_text)
    return json.dumps(temp, indent=2, sort_keys=True)

s = '{"aaa":100, "bbb":200, "ccc":300}'
print(pretty_json(s))
{{< /code >}}

{{< code lang="json" title="実行結果" >}}
{
  "aaa": 100,
  "bbb": 200,
  "ccc": 300
}
{{< /code >}}

次のステップとして、パラメーターに文字列ではない通常のオブジェクトを受け取った場合でも JSON 文字列に変換できるようにしてみます。
__`isinstance`__ 関数を使うと、あるオブジェクトが文字列型 (`str`) かどうかを判別することができます。
下記の `pretty_json()` 関数は、JSON 文字列、あるいは任意の Python オブジェクトを受け取って、整形された JSON 文字列として返します。

```python
import json

def pretty_json(obj):
    temp = json.loads(obj) if isinstance(obj, str) else obj
    return json.dumps(temp, indent=2, sort_keys=True)

json_str = '{"aaa":100, "bbb":200, "ccc":300}'
json_obj = {"aaa":100, "bbb":200, "ccc":300}
print(pretty_json(json_str))  # JSON 文字列も、
print(pretty_json(json_obj))  # オブジェクトもどちらも渡せる
```

ちなみに、Python はコマンドラインで [JSON テキストを整形するツール (json.tool)](/p/an8o6m4/) を提供 しているので、ローカルの JSON ファイルをササッと整形したいときにはこちらを使うと便利かもしれません。

