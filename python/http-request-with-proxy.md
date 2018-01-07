---
title: urllib による HTTP リクエスト (2) プロキシ経由でアクセス
date: "2014-11-28"
---

`urllib.request` を使用した HTTP アクセスは、デフォルトで `http_proxy (ftp_proxy)` 環境変数に指定したプロキシ経由でアクセスが行われます。
プログラムの中で、明示的にプロキシサーバを指定したい場合は下記のように `ProxyHandler` を使って指定することができます。

#### sample.py
```python
#!/usr/bin/env python
import urllib.request

PROXIES = {
    'http' : 'http://proxy.example.com:8080',
    'ftp' : 'ftp://proxy.example.com:8080'
}

proxy_handler = urllib.request.ProxyHandler(PROXIES)
opener = urllib.request.build_opener(proxy_handler)
data = opener.open('http://example.com/').read()
print(data.decode('utf-8'))
```

