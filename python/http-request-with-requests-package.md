---
title: requests パッケージによる HTTP リクエスト
date: "2015-05-18"
---

requests パッケージのインストール
===
オープンソースの [requests パッケージ](http://requests.readthedocs.org/en/latest/) を使用すると、Python 3 標準の `urllib.request` に比べ、HTTP リクエストを簡単に扱うことができます。
例えば、以下のような処理をシンプルなコードで扱うことができます。

* Basic 認証、ダイジェスト認証
* ファイルのアップロード
* クッキー
* SSL 検証
* エンコーディング形式の自動判別
* コンテンンツが圧縮されていた場合の自動展開

requests パッケージは、`pip` コマンドで簡単にインストールできます。

```
$ pip install requests
```

requests モジュールによる HTTP GET リクエスト
====

`requests.get` 関数により、インターネット上のコンテンツを簡単にダウンロードできます。
`requests.get` は、戻り値として `requests.models.Response` オブジェクトを返します。

```python
import requests

res = requests.get('http://google.com/')
type(res)  #=> <class 'requests.models.Response'>
```

`Response` オブジェクトから、ステータスコードや、取得したコンテンツの内容を取得することができます。


```python
import requests

res = requests.get('http://google.com/')
print(res.status_code)  #=> 200
print(res.status_code == requests.codes.ok)  #=> True
print(res.ok)  #=> True

content = res.content  #=> コンテンツをバイトデータ (bytes) で取得
text = res.text        #=> コンテンツをテキスト (str) で取得
```

`Response#text` を使って Unicode のテキストデータを取得することができます。
ダウンロードされたコンテンツからの変換に使われるエンコーディング形式は、HTTP のヘッダ、あるいは HTML 内のヘッダから判断されます。
どちらのヘッダにも指定がない場合は、コンテンツ内の文字列からそれらしきエンコーディング形式が決定されます。

requests モジュールによる HTTP POST リクエスト
====

POST リクエストを送るには、`requests.get` の代わりに `requests.post` を使用するだけです。

```python
data = {'key1': 'value1', 'key2': 'value2'}
res = requests.post('http://example.com/', data)
print(res.text)
```

