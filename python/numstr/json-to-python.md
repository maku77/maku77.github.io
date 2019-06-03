---
title: "JSON 形式のテキストと Python オブジェクトの相互変換 (json.loads, json.dumps)"
date: "2013-05-22"
---

Python 2.6 からは json モジュールが標準搭載されており、JSON 形式のテキストと、Python のオブジェクトを相互に変換することができます。


JSON テキスト を Python オブジェクトに変換する
----

JSON 形式の文字列データから、Python オブジェクトを作成するには [json.loads 関数](https://docs.python.org/ja/3/library/json.html#json.loads) を使用します。

```python
import json

data = json.loads('{"key":"value"}')
print(data['key'])  #=> 'value'
```

`json.loads` と似た関数に、`json.load` 関数がありますが、こちらは文字列ではなくファイルを扱います。
`loads` の末尾の `s` は、文字列を受け取ることを示しています。

- 参考: [JSON 形式のテキストファイルを Python オブジェクトとして読み込む (json.load)](../io/json-load.html)


Python オブジェクト を JSON テキストに変換する
----

逆に、Python オブジェクトから JSON 形式のテキストを生成するには、[json.dumps 関数](https://docs.python.org/ja/3/library/json.html#json.dumps) を使用します。

```python
import json

obj = {'aaa':100, 'bbb':200}
json_str = json.dumps(obj)
print(json_str)
```

#### 実行結果

```
{"bbb": 200, "aaa": 100}
```

### 整形して出力する

きれいに整形して出力したい場合は、`json.dumps()` 関数の **`indent`** パラメータにスペース数を指定します（Python 3.2 以降は、インデントに使用する文字列そのものを指定することもできます）。

```python
obj = {'aaa':100, 'bbb':200}
json_str = json.dumps(obj, indent=4)
print(json_str)
```

#### 出力結果

```
{
    "bbb": 200,
    "aaa": 100
}
```

### キーでソートして出力する

デフォルトでは、`json.dumps()` はオブジェクトの要素を不定の順序で出力します。
キー名でソートされた JSON テキストとして出力したい場合は、`json.dumps()` 関数の **`sort_keys`** パラメータを `True` に設定します。

```python
print(json.dumps(obj, indent=4, sort_keys=True))
```

#### 実行結果

```
{
    "aaa": 100,
    "bbb": 200
}
```


JSON テキストをきれいに整形して出力する
----

下記の `pretty_json()` 関数は、JSON 形式の文字列データを、きれいにインデントや改行を入れた形に整形します。
まず、`json.loads` で Python オブジェクトに変換してから、`json.dumps` で整形されたテキスト形式に戻しています。

```python
import json

def pretty_json(json_text):
    return json.dumps(json.loads(json_text), indent=4)

s = '{"aaa":100, "bbb":200, "ccc":300}'
print(pretty_json(s))
```

JSON 形式の文字列と、通常のオブジェクトのどちらでも処理できるようにするには、`isinstance` で `str` 型オブジェクトどうかを判別して処理すればよいでしょう。
下記の `print_json()` 関数は、渡された JSON テキスト、あるいはオブジェクトをきれいに整形して出力します。

```python
import json

def print_json(obj):
    s = json.loads(obj) if isinstance(obj, str) else obj
    print(json.dumps(s, indent=4))

print_json('{"aaa":100, "bbb":200, "ccc":300}')  # JSON テキストでも
print_json({"aaa":100, "bbb":200, "ccc":300})    # オブジェクトでもOK
```

