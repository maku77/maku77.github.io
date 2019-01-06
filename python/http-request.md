---
title: "urllib による HTTP リクエスト (1)"
date: "2014-11-28"
---

ここでは、`urllib.request` を使った HTTP アクセスの例を示します。

```python
from urllib.request import urlopen

data = urlopen('http://example.com/').read()
print(data.decode('utf-8'))
```

`urllib.request.urlopen(...)` は、`urllib.request.Request` オブジェクトを返し、その `read()` メソッドを呼ぶことで、HTTP のレスポンスを取得することができます。
`read()` メソッドが返すデータはバイナリデータなので、テキストとして扱う場合は、エンコーディング形式を指定してデコードする必要があります。

