---
title: JSON 形式のテキストを Python オブジェクトに変換する
date: "2014-11-28"
---

Python 2.6 からは json モジュールが標準搭載されており、JSON 形式のテキストを簡単に扱うことができます。


JSON テキスト => Python オブジェクト
====

JSON 形式の文字列データから、Python オブジェクトを作成するには以下のようにします。

```python
import json

data = json.loads('{"key":"value"}')
print(data['key'])  # => 'value'
```


Python オブジェクト => JSON テキスト
====

逆に、Python オブジェクトから JSON 形式のテキストを生成することも可能です。

```python
data = {'key1':'value1', 'key2':'value2'}
text = json.dumps(data)  # => '{"key2": "value2", "key1": "value1"}'
```

きれいに整形して出力したい場合は、indent パラメータを指定します。

```python
data = {'key1':'value1', 'key2':'value2'}
print(json.dumps(data, indent=' '*4))
```

#### 出力結果 ===
```python
{
    "key1": "value1",
    "key2": "value2"
}
```

