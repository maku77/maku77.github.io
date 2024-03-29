---
title: "Python の urllib による HTTP リクエスト (2) プロキシ経由でアクセスする"
url: "p/ogq5hdy/"
date: "2014-11-28"
tags: ["Python", "HTTP"]
aliases: /python/web/http-request-with-proxy.html
---

`urllib.request` を使用した HTTP アクセスは、デフォルトでシステムに設定されたプロキシ設定（__`http_proxy`__、__`https_proxy`__、__`ftp_proxy`__ 環境変数など）でアクセスが行われます。
これらのプロキシ設定をそのまま使用してもよいし、Python のコード内で明示的にプロキシを設定することもできます。


現在のプロキシ設定を確認する (getproxies)
----

現在、システムにどのようなプロキシ設定が反映されているかを調べるには、__`urllib.request.getproxies`__ 関数を使用します。

```python
import urllib.request

print(urllib.request.getproxies())
```

{{< code lang="js" title="実行結果" >}}
{'http': 'http://proxy.example.com:8080', 'https': 'https://proxy.example.com:8080', 'ftp': 'ftp://proxy.example.com:8080'}
{{< /code >}}


リクエストごとにプロキシを設定する (Request)
----

HTTP リクエストごとに使用するプロキシを明示的に指定したい場合は、__`Request`__ オブジェクトを作成し、__`set_proxy`__ メソッドでプロキシを指定します。
あとは、その `Request` オブジェクトを `urllib.request.urlopen()` に渡して HTTP リクエストを送信します。

{{< code lang="python" title="リクエスト単位でのプロキシ設定" >}}
import urllib.request

req = urllib.request.Request('https://example.com/')
req.set_proxy('proxy.example.com:8080', 'http')
req.set_proxy('proxy.example.com:8080', 'https')

with urllib.request.urlopen(req) as res:
    html = res.read().decode('utf-8')
    print(html)
{{< /code >}}

`Request#set_proxy()` でプロキシサーバーを指定するときは、`http://` や `https://` といったスキーム名は省略することに注意してください。
スキーム名を記述してしまうと、プロキシがうまく認識されず、次のような DNS エラーになったりします。

```
urlopen error [Errno 11001] getaddrinfo failed
```

`Request` オブジェクトは生成時に URL を指定する必要があるため、アクセス先が変わるごとに生成する必要があることに注意してください。


すべてのリクエストに共通のプロキシを設定する (OpenerDirector + ProxyHandler)
----

__`OpenDirector`__ を使用すると、異なる URL に対して HTTP リクエストを送信するときに、設定情報を使いまわすことができます。
プロキシの情報を設定したい場合は、下記のように __`ProxyHandler`__ を作成して `OpenDirector` オブジェクトのハンドラとして追加します。
`OpenDirector` オブジェクトは、`urllib.request.build_opener` 関数で生成することができます。

{{< code lang="python" title="共通のプロキシ設定" >}}
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
{{< /code >}}

上記の例では、`OpenerDirector#open()` メソッドを使うことで、プロキシ経由の HTTP アクセスを行っています。

別の方法として、あらかじめ __`urllib.request.install_opener()`__ を使って `OpenerDirector` をインストールしておく方法があります。
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

