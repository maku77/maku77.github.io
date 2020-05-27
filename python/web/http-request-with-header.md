---
title: "urllib による HTTP 通信 (5) ヘッダを付けてリクエストする"
date: "2019-05-30"
---

リクエストごとにヘッダを設定する (Request)
----

**`urllib.request.Request`** オブジェクトの **`add_header()`** メソッドや、**`headers`** プロパティを使用すると、HTTP リクエスト時のヘッダ情報を設定することができます。
あとは、`urllib.request.urlopen` で HTTP リクエストを送信するとき、URL の代わりに `Request` オブジェクトを渡せば、ヘッダ付きのリクエストを送ることができます。

```python
import urllib.request

def create_request():
    req = urllib.request.Request('https://example.com/myapi')
    req.add_header('Referer', 'http://www.python.org/')
    req.add_header('User-Agent', 'Mozilla/5.0')
    return req

if __name__ == '__main__':
    req = create_request()
    with urllib.request.urlopen(req) as res:
        html = res.read().decode('utf-8')
        print(html)
```

上記の例では、`Request#add_header()` メソッドを使って、ヘッダ情報を 1 つずつ付加しています。
`Request#headers` プロパティを使用すれば、複数のヘッダ情報をディクショナリ形式でまとめて設定することができます。

```python
req.headers = {
    'Referer': 'http://www.python.org/',
    'User-Agent': 'Mozilla/5.0',
}
```


すべてのリクエストに共通のヘッダを設定する (OpenerDirector)
----

`Request` オブジェクトは URL まで含んでいるため、アクセス先の URL が変わるたびに生成しなおす必要があります。
一方、`OpenerDirector` を使用すると、リクエスト時のヘッダ情報などはそのままで、URL だけを変更してアクセスすることができます。

```python
import urllib.request

def create_opener():
    opener = urllib.request.build_opener()
    opener.addheaders = [
        ('Referer', 'http://www.python.org/'),
        ('User-Agent', 'Mozilla/5.0'),
    ]
    return opener

if __name__ == '__main__':
    opener = create_opener()
    with opener.open('https://example.com/myapi') as res:
        html = res.read().decode('utf-8')
        print(html)
```

ヘッダ情報は **`OpenerDirector#addheaders`** プロパティにセットするのですが、`add` という名前なのにプロパティになっていたり、ディクショナリではなくリストで値を設定しなければいけなかったりと、謎仕様なところには注意してください。

作成した `OpenerDirector` オブジェクトを、**`urllib.request.install_opener()`** で設定してやることで、それ以降の `urlopen` はその `OpenerDirector` の設定に基づいて動作するようになります。
アプリケーション全体でアクセス方法を統一したいのであれば、こちらの方法を使うとシンプルかもしれません。

```python
import urllib.request

def setup_requests():
    opener = urllib.request.build_opener()
    opener.addheaders = [
        ('Referer', 'http://www.python.org/'),
        ('User-Agent', 'Mozilla/5.0'),
    ]
    urllib.request.install_opener(opener)

if __name__ == '__main__':
    setup_requests()
    with urllib.request.urlopen('https://example.com/myapi') as res:
        html = res.read().decode('utf-8')
        print(html)
```

