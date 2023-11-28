---
title: "Python の requests パッケージによる HTTP リクエストの例"
url: "p/r7q8q7o/"
date: "2015-05-18"
tags: ["Python", "HTTP"]
aliases: /python/web/http-request-with-requests-package.html
---

参考: [requests パッケージのチートシート](/p/succ4mb/)

requests パッケージのインストール
----

オープンソースの [requests パッケージ](http://requests.readthedocs.org/en/latest/) を使用すると、Python 3 標準の `urllib.request` よりも簡単に HTTP リクエストを扱うことができます。
例えば、以下のような処理をシンプルなコードで扱うことができます。

- Basic 認証、ダイジェスト認証
- ファイルのアップロード
- クッキー
- SSL 検証
- エンコーディング形式の自動判別
- コンテンンツが圧縮されていた場合の自動展開

requests パッケージは、`pip` コマンドで簡単にインストールできます。

{{< code lang="console" title="request パッケージのインストール" >}}
$ pip install requests
{{< /code >}}


requests モジュールによる HTTP GET リクエスト
----

__`requests.get`__ 関数を使うと、HTTP GET リクエストでインターネット上のコンテンツをダウンロードすることができます。
`requests.get` は、戻り値として __`requests.models.Response`__ オブジェクトを返します。

{{< code lang="python" title="Web コンテンツのダウンロード" >}}
import requests

res = requests.get('http://google.com/')
type(res)  #=> <class 'requests.models.Response'>
{{< /code >}}

`Response` オブジェクトから、ステータスコードや、取得したコンテンツの内容を取得することができます。

{{< code lang="python" title="requests.get のレスポンスを詳しく見る" >}}
import requests

res = requests.get('http://google.com/')
print(res.status_code)  #=> 200
print(res.status_code == requests.codes.ok)  #=> True
print(res.ok)  #=> True

content = res.content  #=> コンテンツをバイトデータ (bytes) で取得
text = res.text        #=> コンテンツをテキスト (str) で取得
{{< /code >}}

`Response#text` を使って Unicode のテキストデータを取得することができます。
ダウンロードされたコンテンツからの変換に使われるエンコーディング形式は、HTTP のヘッダ、あるいは HTML 内のヘッダから判断されます。
どちらのヘッダにも指定がない場合は、コンテンツ内の文字列からそれらしきエンコーディング形式が決定されます。


requests モジュールによる HTTP POST リクエスト
----

POST リクエストを送るには、`requests.get` の代わりに __`requests.post`__ を使用するだけです。

{{< code lang="python" title="HTTP の POST リクエストを送る" >}}
data = {'key1': 'value1', 'key2': 'value2'}
res = requests.post('http://example.com/', data)
print(res.text)
{{< /code >}}

