---
title: "Python の urllib による HTTP リクエスト (1) GET/POST リクエスト (urllib.request.urlopen)"
url: "p/o2e43ct/"
date: "2014-11-28"
tags: ["Python", "HTTP"]
aliases: /python/web/http-request.html
---

ここでは、Python の組み込みモジュールである [urllib.request](https://docs.python.org/3/library/urllib.request.html) を使った HTTP アクセスの例を示します。


urlopen による GET/POST リクエストの基本
----

__`urllib.request.urlopen`__ 関数は、__`http.client.HTTPResponse`__ オブジェクトを返します。
`HTTPResponse` オブジェクトの __`read`__ メソッドを呼ぶことで、HTTP レスポンスを取得することができます。

{{< code lang="python" title="https://example.com のコンテンツ (HTML) を取得する" >}}
from urllib.request import urlopen

with urlopen('https://example.com/') as res:
    text = res.read().decode('utf-8')
    print(text)
{{< /code >}}

`read` メソッドが返すデータはバイナリデータ (`bytes`) なので、テキストとして扱う場合は、エンコーディング形式（`utf-8` など）を指定してデコードする必要があります。

`urlopen` 関数で取得した `HTTPResponse` オブジェクトは、リソース解放のために明示的に `close()` することが推奨されているのですが、上記のように Python 3 の `with` 文を使用すると、`close()` 処理を自動化できます。

`urlopen` の引数に、URL 文字列ではなく、[urllib.request.Request](https://docs.python.org/ja/3/library/urllib.request.html#urllib.request.Request) オブジェクトを渡すこともできます。
`Request` オブジェクトを使うと、HTTP ヘッダー情報やプロキシ、HTTP メソッド（GET や POST）の指定などを行えます。

{{< code lang="python" title="HTTP ヘッダーやプロキシを指定したリクエスト" >}}
import urllib.request

URL = 'http://example.com/'
req = urllib.request.Request(URL, method='POST')
req.add_header('Authorization', 'token %s' % 'DUMMY_KEY')
req.set_proxy('proxy.example.com:8080', 'http')

with urllib.request.urlopen(req) as res:
    text = res.read().decode('utf-8')
    print(text)
{{< /code >}}

- 参考: [urllib による HTTP リクエスト (2) プロキシ経由でアクセスする](/p/ogq5hdy/)
- 参考: [urllib による HTTP 通信 (5) ヘッダを付けてリクエストする](/p/i4zkqye/)


データ付きの GET/POST リクエスト
----

### GET リクエストにクエリ文字列を付加する

```
https://example.com/?name=まく&age=14
```

のように、URL 末尾のクエリ文字列の形でデータを送るときは、単純に URL をそのように加工します。
ただし、日本語などの値は URL エンコードされている必要があるため、先に [urllib.parse.urlencode](https://docs.python.org/ja/3/library/urllib.parse.html#urllib.parse.urlencode) を使ってデータをエンコードしてから付加します。

```python
import urllib.parse

data = { 'name': 'まく', 'age': 14 }
url = 'https://example.com/?%s' % urllib.parse.urlencode(data)

with urllib.request.urlopen(url) as res:
    text = res.read().decode('utf-8')
    print(text)
```

### POST リクエストで JSON データを送る

POST リクエストの Body でデータを送るには、`urllib.request.Request` のコンストラクタの __`data`__ パラメータに、送りたいデータをセットします（オブジェクト生成後に `data` プロパティでセットすることもできます）。
`data` パラメータがセットされると、`method` パラメータは自動的に __`POST`__ が指定されたものとして動作します。

次の例では、POST メソッドで JSON データを送信しています。

```python
import json
import urllib.parse
import urllib.request

url = 'http://example.com/'
data = { 'name': 'まく', 'age': 14 }
headers = { 'Content-Type': 'application/json' }

req = urllib.request.Request(
    url=url,
    data=json.dumps(data).encode('utf-8'),
    headers=headers)

with urllib.request.urlopen(req) as res:
    text = res.read().decode('utf-8')
    print(text)
```


urlopen のエラーハンドル
----

`urlopen` 関数は次のようなエラーをスローすることがあります。

- [urllib.error.URLError](https://docs.python.org/ja/3/library/urllib.error.html#urllib.error.URLError) ... 指定した URL で Web サーバーと通信できなかった場合にスローされます。例えば、ドメイン名が間違っていたり、プロキシサーバーのアドレスが間違っていると発生します。
- [urllib.error.HTTPError](https://docs.python.org/ja/3/library/urllib.error.html#urllib.error.HTTPError) ... `404 Not Found` や、`500 Internal Server Error` など、Web サーバーから HTTP エラーが返された場合にスローされます。

`HTTPError` は `URLError` のサブクラスなので、先にハンドルするようにしてください。

```python
import sys
import urllib.request

# ...

try:
    with urllib.request.urlopen(req) as res:
        text = res.read().decode('utf-8')
        print(text)
except urllib.error.HTTPError as err:
    print('Could not access: %s' % req.full_url, file=sys.stderr)
    print(err, file=sys.stderr)         # HTTP Error 404: Not Found
    print(err.code, file=sys.stderr)    # 404
    print(err.reason, file=sys.stderr)  # Not Found
    sys.exit(1)
except urllib.error.URLError as err:
    print('Could not access: %s' % req.full_url, file=sys.stderr)
    print(err.reason, file=sys.stderr)
    sys.exit(1)
```

例えば、上記のコードで `404 Not Found` の `HTTPError` が発生した場合は次のように出力されます。
最初の 2 行くらいを出力しておけばエラー表示としては十分かと思います。

```
Could not access: http://example.com/DUMMY
HTTP Error 404: Not Found
404
Not Found
```

もう一方のエラーである `URLError` は、ドメイン名などの間違いで、サーバーと通信できなかった場合に発生します（`HTTPError` の親クラスなので、正確には `HTTPError` 発生時にも発生しています）。

```
Could not access: http://MY-FAKE-DOMAIN.com/
[Errno 8] nodename nor servname provided, or not known
```

`HTTPError#code` などで HTTP ステータスコードを個別に取得する必要がないのであれば、親クラスの `URLError` だけでまとめてエラーハンドルしてしまっても OK です。

```python
try:
    with urllib.request.urlopen(req) as res:
        text = res.read().decode('utf-8')
        print(text)
except urllib.error.URLError as err:
    print('Could not access: %s' % req.full_url, file=sys.stderr)
    print(err, file=sys.stderr)
    sys.exit(1)
```

```
Could not access: http://example.com/DUMMY
HTTP Error 404: Not Found
```

