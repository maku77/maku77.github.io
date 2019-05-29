---
title: "urllib による HTTP リクエスト (2) プロキシ経由でアクセスする (ProxyHandler)"
date: "2014-11-28"
---

`urllib.request` を使用した HTTP アクセスは、デフォルトでシステムに設定されたプロキシ設定（**`http_proxy`**、**`https_proxy`**、**`ftp_proxy`** 環境変数など）でアクセスが行われます。

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


プロキシを指定する (ProxyHandler)
----

プログラムの中で、明示的にプロキシサーバを指定したい場合は、下記のように **`ProxyHandler`** を使って指定することができます。

```python
import urllib.request

PROXIES = {
    'http': 'http://proxy.example.com:8080',
    'https': 'https://proxy.example.com:8080',
    'ftp': 'ftp://proxy.example.com:8080'
}

proxy = urllib.request.ProxyHandler(PROXIES)
opener = urllib.request.build_opener(proxy)
with opener.open('https://example.com/') as res:
    html = res.read().decode('utf-8')
    print(html)
```

上記の例では、`urllib.request.build_opener()` によって返された `OpenerDirector` オブジェクトの `open()` メソッドを使うことで、プロキシ経由の HTTP アクセスを行っています。

あらかじめ **`urllib.request.install_opener()`** を使って `OpenerDirector` をインストールしておくと、すべての `urllib.request.urlopen()` によるアクセスをプロキシ経由にすることができます（`OpenDirector` オブジェクトを使ってアクセスする必要がなくなる）。

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

