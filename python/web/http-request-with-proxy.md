---
title: "urllib による HTTP リクエスト (2) プロキシ経由でアクセスする"
date: "2014-11-28"
---

`urllib.request` を使用した HTTP アクセスは、デフォルトでシステムに設定されたプロキシ設定（**`http_proxy`**、**`https_proxy`**、**`ftp_proxy`** 環境変数など）でアクセスが行われます。
これらのプロキシ設定をそのまま使用してもよいし、Python のコード内で明示的にプロキシを設定することもできます。


現在のプロキシ設定を確認する (getproxies)
----

現在、システムにどのようなプロキシ設定が反映されているかを調べるには、**`urllib.request.getproxies()`** 関数を呼び出します。

```python
import urllib.request

print(urllib.request.getproxies())
```

#### 実行結果

```python
{'http': 'http://proxy.example.com:8080', 'https': 'https://proxy.example.com:8080', 'ftp': 'ftp://proxy.example.com:8080'}
```

リクエストごとにプロキシを設定する (Request)
----

HTTP リクエストごとに使用するプロキシを明示的に指定したい場合は、**`Request`** オブジェクトを作成し、**`set_proxy()`** メソッドでプロキシを指定します。
あとは、`urllib.request.urlopen()` でその `Request` オブジェクトを渡すことで HTTP リクエストを送信します。

```python
import urllib.request

req = urllib.request.Request('https://example.com/')
req.set_proxy('proxy.example.com:8080', 'http')
req.set_proxy('proxy.example.com:8080', 'https')

with urllib.request.urlopen(req) as res:
    html = res.read().decode('utf-8')
    print(html)
```

`Request` オブジェクトは生成時に URL を指定する必要があるため、アクセス先が変わるごとに生成する必要があることに注意してください。


すべてのリクエストに共通のプロキシを設定する (OpenerDirector + ProxyHandler)
----

**`OpenDirector`** を使用すると、異なる URL に対して HTTP リクエストを送信するときに、設定情報を使いまわすことができます。
プロキシの情報を設定したい場合は、下記のように **`ProxyHandler`** を作成して `OpenDirector` オブジェクトのハンドラとして追加します。
`OpenDirector` オブジェクトは、`urllib.request.build_opener()` 関数で生成することができます。

```python
import urllib.request

PROXIES = {
    'http': 'http://proxy.example.com:8080',
    'https': 'https://proxy.example.com:8080',
    'ftp': 'ftp://proxy.example.com:8080'
}

proxy_handler = urllib.request.ProxyHandler(PROXIES)
opener = urllib.request.build_opener(proxy_handler)
with opener.open('https://example.com/') as res:
    html = res.read().decode('utf-8')
    print(html)
```

上記の例では、`OpenerDirector#open()` メソッドを使うことで、プロキシ経由の HTTP アクセスを行っています。

別の方法として、あらかじめ **`urllib.request.install_opener()`** を使って `OpenerDirector` をインストールしておく方法があります。
このようにすると、その後のすべての `urllib.request.urlopen()` によるアクセスをプロキシ経由にすることができます（`OpenDirector` オブジェクトを使ってアクセスする必要がなくなります）。

```python
import urllib.request

PROXIES = {
    'http': 'http://proxy.example.com:8080',
    'https': 'https://proxy.example.com:8080',
    'ftp': 'ftp://proxy.example.com:8080'
}

def setup_proxy():
    proxy = urllib.request.ProxyHandler(PROXIES)
    opener = urllib.request.build_opener(proxy)
    urllib.request.install_opener(opener)

if __name__ == '__main__':
    setup_proxy()
    with urllib.request.urlopen('https://example.com/') as res:
        html = res.read().decode('utf-8')
        print(html)
```

