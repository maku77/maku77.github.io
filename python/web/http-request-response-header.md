---
title: "urllib による HTTP 通信 (6) レスポンスヘッダを取得する"
date: "2020-06-01"
---

[urllib.request.urlopen()](https://docs.python.org/ja/3/library/urllib.request.html#urllib.request.urlopen) が返した `HTTPResponse` オブジェクトの __`info()`__ メソッドを呼び出すと、HTTP レスポンスのヘッダ情報を取得することができます。

ヘッダ情報は [HTTPMessage オブジェクト](https://docs.python.org/ja/3/library/http.client.html#httpmessage-objects) として返されますが、これは通常の辞書オブジェクトと同様に使用することができます。
詳しくは、`HTTPMessage` の親クラスである [email.message.Message](https://docs.python.org/ja/3/library/email.compat32-message.html#email.message.Message) の定義を参照してください。

次の例では、Web サイトにアクセスしたときの HTTP レスポンスヘッダーを取得して表示しています。

#### sample.py

```python
import urllib.request

url = 'https://yahoo.co.jp/'
try:
    with urllib.request.urlopen(url) as res:
        # HTTP レスポンスのヘッダ情報を取得
        headers = res.info()
        print(type(headers))  #=> http.client.HTTPMessage
        print()
        print(headers)
        # 下記は Body 部分の取得
        # text = res.read().decode('utf-8')
        # print(text)
except urllib.error.URLError as err:
    print('Could not access: %s' % url, file=sys.stderr)
    print(err, file=sys.stderr)
    sys.exit(1)
```

#### 実行結果

```
<class 'http.client.HTTPMessage'>

Cache-Control: private, no-cache, no-store, must-revalidate
Content-Type: text/html; charset=UTF-8
Date: Mon, 01 Jun 2020 06:07:42 GMT
Expires: -1
Pragma: no-cache
Set-Cookie: B=1sg6o4tfd96pe&b=3&s=ab; expires=Thu, 02-Jun-2022 06:07:42 GMT; path=/; domain=.yahoo.co.jp
Vary: Accept-Encoding
X-Content-Type-Options: nosniff
X-Frame-Options: SAMEORIGIN
X-Vcap-Request-Id: f25c0bf4-0481-4804-5fe1-ef1fab7a3080
X-Xss-Protection: 1; mode=block
Age: 0
Transfer-Encoding: chunked
Connection: close
Via: http/1.1 edge2424.img.djm.yahoo.co.jp (ApacheTrafficServer [c sSf ])
Server: ATS
Set-Cookie: XB=1sg6o4tfd96pe&b=3&s=ab; expires=Thu, 02-Jun-2022 06:07:42 GMT; path=/; domain=.yahoo.co.jp; secure; samesite=none
```

特定のヘッダ情報だけ取得したい場合は、次のように参照すれば OK です。

```python
with urllib.request.urlopen(url) as res:
    headers = res.info()
    if 'Content-Type' in headers:
        print(headers['Content-Type'])
```

