---
title: Python で HTTP リクエストを送信する (1)
created: 2014-11-28
---

Python の標準 HTTP ライブラリ
====
Python には HTTP を扱う、下記のような標準ライブラリが用意されています。

* [http.client](https://docs.python.org/3/library/http.client.html)
  - HTTP (RFC 2616) を実装する**低レベルライブラリ**。

* [urllib.request](https://docs.python.org/3/library/urllib.request.html)
  - 上記を利用した**高レベルライブラリ**。認証やリダイレクトをサポート。


urllib による HTTP アクセス
====
ここでは、`urllib.request` を使った HTTP アクセスの例を示します。

```python
from urllib.request import urlopen

data = urlopen('http://example.com/').read()
print(data.decode('utf-8'))
```

`urllib.request.urlopen(...)` は、`urllib.request.Request` オブジェクトを返し、その `read()` メソッドを呼ぶことで、HTTP のレスポンスを取得することができます。
`read()` メソッドが返すデータはバイナリデータなので、テキストとして扱う場合は、エンコーディング形式を指定してデコードする必要があります。

