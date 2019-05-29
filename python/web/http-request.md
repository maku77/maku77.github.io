---
title: "urllib による HTTP リクエスト (1) 基本 (urlopen)"
date: "2014-11-28"
---

ここでは、Python の組み込みモジュール [`urllib.request`](https://docs.python.org/3/library/urllib.request.html) を使った HTTP アクセスの例を示します。


urlopen の基本
----

```python
from urllib.request import urlopen

response = urlopen('https://example.com/')
html = response.read().decode('utf-8')
print(html)
response.close()
```

`urllib.request.urlopen(...)` は、**`http.client.HTTPResponse`** オブジェクトを返します。

`HTTPResponse` オブジェクトの **`read()`** メソッドを呼ぶことで、HTTP のレスポンスを取得することができます。
`read()` メソッドが返すデータはバイナリデータ (`bytes`) なので、テキストとして扱う場合は、エンコーディング形式（`utf-8` など）を指定してデコードする必要があります。


urlopen 後の close を自動化する
----

`urlopen()` で取得した `HTTPResponse` オブジェクトは、リソース解放のために明示的に `close()` することが推奨されています。
Python 3 の `with` 文を使用すると、この処理を自動化することができます。

```python
from urllib.request import urlopen

with urlopen('https://example.com/') as res:
    text = res.read().decode('utf-8')
    print(text)
```

