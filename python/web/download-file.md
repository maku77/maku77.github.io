---
title: "ulrlib による HTTP リクエスト (3) ファイルをダウンロードする (urlretrieve)"
date: "2015-11-19"
---

urllib.request によるファイルのダウンロード
----

**`urllib.request.urlretrieve`** を使用すると、簡単に Web 上のファイルをダウンロードしてローカルファイルとして保存することができます。

* [urllib.request.urlretrieve 関数](http://docs.python.jp/3/library/urllib.request.html#urllib.request.urlretrieve)

下記の `download_file` 関数は、指定した URL のファイルをカレントディレクトリにダウンロードします。

```python
import urllib.request
import os.path

def download_file(url):
    filename = os.path.basename(url) or 'index.html'
    urllib.request.urlretrieve(url, filename)

if __name__ == '__main__':
    download_file('https://github.com/maku77/')
```

ローカルに作成されるファイル名は、`os.path.basename` を使用して、URL の末尾から切り出しています。
URL の末尾にファイル名らしきものが見つからない場合は、デフォルトファイル名として `index.html` で保存するようにしています。


テンポラリファイルとしてダウンロードする
----

`urllib.request.urlretrieve` の 2 番目のパラメータ (**`filename`**) を省略すると、テンポラリディレクトリに適当なファイル名で保存されます。
このファイルパスは、戻り値で返されるタプルの先頭に格納されています。

```python
path, headers = urllib.request.urlretrieve('http://example.com/sample.png')
print(path)  #=> c:\users\maku\appdata\local\temp\tmpgujizj
```

`urlretrieve` により作成された一時ファイルを確実に削除するには、下記のように後始末しておきます。

```python
urllib.request.urlcleanup()
```

まぁ、テンポラリディレクトリに作成されるファイルなので、それほど厳密に削除する必要はないかと思いますが、大量のテンポラリファイルが作られるような場合は実行しておいた方がよいでしょう。


ダウンロード時の HTTP レスポンスヘッダの取得
----

`urllib.request.urlretrieve` により、ファイルをダウンロードした際に、ついでに **HTTP レスポンスヘッダを取得したい場合**は、下記のように**戻り値のタプルの 2 番目の要素**から取得できます。

```python
path, headers = urllib.request.urlretrieve('https://www.google.co.jp/images/nav_logo242_hr.png')
for key, val in headers.items():
    print('{0} ===> {1}'.format(key, val))
```

#### 実行結果

```
Content-Type ===> image/png
Date ===> Thu, 19 Nov 2015 04:41:09 GMT
Expires ===> Thu, 19 Nov 2015 04:41:09 GMT
Cache-Control ===> private, max-age=31536000
Last-Modified ===> Thu, 22 Oct 2015 17:33:49 GMT
X-Content-Type-Options ===> nosniff
Server ===> sffe
Content-Length ===> 39373
X-XSS-Protection ===> 1; mode=block
Alternate-Protocol ===> 443:quic,p=1
Alt-Svc ===> quic="www.google.com:443"; p="1"; ma=600,quic=":443"; p="1"; ma=600
Connection ===> close
```

