---
title: Python のオブジェクトを JSON 形式のテキストに変換する
date: "2013-05-09"
---

* 参考: [JSON 形式のテキストを Python のオブジェクトに変換する](./json-to-python.html)

オブジェクトから JSON テキストを生成する
----

Python の json モジュールを使用すると、簡単に Python のオブジェクトから JSON 形式のテキストを生成できます。
`json.dumps()` にオブジェクトを渡すだけです。

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

整形して出力する
----

改行やインデントを入れて、読みやすいフォーマットで出力する場合は、`json.dumps()` の **indent オプションにインデントサイズ**を指定します。

```python
print(json.dumps(obj, indent=2))
```

#### 実行結果

```
{
  "bbb": 200,
  "aaa": 100
}
```

キーでソートして出力する
----

キー名でソートされた JSON テキストを生成したい場合は、`json.dumps()` の **sort_keys オプションを True** に設定します。

```python
print(json.dumps(obj, indent=2, sort_keys=True))
```

#### 実行結果

```
{
  "aaa": 100,
  "bbb": 200
}
```

