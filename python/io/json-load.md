---
title: "JSON 形式のテキストファイルを Python オブジェクトとして読み込む (json.load)"
date: "2014-11-28"
---

Python に付属している `json` ライブラリが提供する [json.load 関数](https://docs.python.org/ja/3/library/json.html#json.load)を使用すると、JSON 形式のテキストファイルを読み込んで、Python のオブジェクトを生成することができます。

```python
import json

# JSON ファイルを読み込んで Python オブジェクトとして返す
fun load_json(filename):
    with open('input.json', encoding='utf-8') as f:
        return json.load(f)

obj = load_json('input.json')
print(json.dumps(obj, indent=2))  # 整形して出力
```

`json.load` と似た名前の関数に、`json.loads` がありますが、こちらは JSON 形式の文字列を扱います。
`json.loads` や `json.dumps` については下記の記事を参考にしてください。

- 参考: [JSON 形式のテキストと Python オブジェクトの相互変換 (json.loads, json.dumps)](../numstr/json-to-python.html)

