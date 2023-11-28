---
title: "Python の requests パッケージのチートシート"
url: "p/succ4mb/"
date: "2023-11-28"
tags: ["cheatsheet", "ctf", "python"]
---

インストールとインポート
----

{{< code lang="console" title="requests パッケージのインストール" >}}
$ pip install requests
{{< /code >}}

{{< code lang="python" title="requests パッケージのインポート" >}}
import requests
{{< /code >}}


リクエスト
----

<table>
  <tr><th>コード</th><th>説明</th></tr>
  <tr>
    <td><pre><code>res = requests.get(URL)</code></pre></td>
    <td>GET リクエスト</td>
  </tr>
  <tr>
    <td><pre><code>res = requests.get(URL, headers={"key", "val"})</code></pre></td>
    <td>ヘッダーの設定</td>
  </tr>
  <tr>
    <td><pre><code>res = requests.get(URL, params={"key", "val"})</code></pre></td>
    <td>クエリパラメータの設定</td>
  </tr>
  <tr>
    <td><pre><code>res = requests.get(URL, cookies={"key", "val"})</code></pre></td>
    <td>クッキーの設定</td>
  </tr>
  <tr>
    <td><pre><code>res = requests.get(URL, auth=("user", "pass"))</code></pre></td>
    <td>Basic 認証</td>
  </tr>
  <tr>
    <td><pre><code>res = requests.get(URL, auth=requests.auth.HTTPDigestAuth("user", "pass"))</code></pre></td>
    <td>Digest 認証</td>
  </tr>
  <tr>
    <td><pre><code>res = requests.post(URL, data={"key": "val"})</code></pre></td>
    <td>POST リクエストでペイロード送信（<code>key=val</code> というテキスト）</td>
  </tr>
  <tr>
    <td><pre><code>data = {"key": "value"}
res = requests.post(
    "https://example.com",
    headers={"Content-Type": "application/json"},
    data=json.dumps(data)
)</code></pre></td>
    <td>POST リクエストでペイロード送信（JSON 形式のテキスト）</td>
  </tr>
  <tr>
    <td><pre><code>session = requests.Session()
res = session.post(URL1, data=data)
res = session.get(URL2)
</code></pre></td>
    <td>セッションを考慮したリクエスト（URL1 のレスポンスに <code>Set-Cookie</code> ヘッダーが含まれていたら、次の URL2 へのリクエストに自動で <code>Cookie</code> ヘッダーが付く）</td>
  </tr>
</table>

- テスト用には下記のアドレスを使うと便利です。どのようなデータが送信されたかを、JSON 形式のレスポンスとして返してくれます。
  - GET メソッド用: `https://httpbin.org/get`
  - POST メソッド用: `https://httpbin.org/post`


レスポンス
----

| コード | 説明 |
| ---- | ---- |
| `res.status_code` | ステータスコード（例: `200`） |
| `res.ok` | ステータスコードが 400 未満であれば `True`（クライアントエラー 4xx もサーバーエラー 5xx も発生していない）|
| `res.headers` | レスポンスヘッダー（dictionary 形式） |
| `res.content` | コンテンツのバイトデータ (bytes) |
| `res.text` | コンテンツのテキストデータ (str) |
| `res.json()` | コンテンツのテキストを JSON フォーマットとみなして Python オブジェクトに変換 |
| `with open("file.txt", "wb") as f: f.write(res.content)` | コンテンツをファイルに保存（ダウンロード） |


参考
----

- [requests パッケージによる HTTP 通信の例](/p/r7q8q7o/)

