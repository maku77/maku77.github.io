---
title: "Node.jsメモ: プロキシ経由の HTTP 通信を行う (request モジュール)"
url: "p/y2oxwej/"
date: "2012-12-07"
tags: ["nodejs"]
aliases: [/nodejs/net/proxy-in-request.html]
---

request モジュールによるプロキシ経由の HTTP アクセス
----

プロキシ経由で HTTP アクセスするときは、**request** モジュールを使うとお手軽です。

* https://github.com/mikeal/request/blob/master/README.md

{{< code lang="js" title="プロキシ経由で HTTP GET" >}}
var request = require('request');

var proxy = request.defaults({'proxy': 'http://proxy.example.com:8080/'})
proxy.get('http://www.google.com/', function (error, response, body) {
  if (!error && response.statusCode == 200) {
    console.log(body);
  }
});
{{< /code >}}

コード内でプロキシ設定を行うのではなく、環境変数で設定しておくことも可能です。

* https://github.com/request/request#controlling-proxy-behaviour-using-environment-variables
  * `HTTP_PROXY` (`http_proxy`)
  * `HTTPS_PROXY` (`https_proxy`)
  * `NO_PROXY` (`no_proxy`)

{{< code lang="bash" title="bash における設定例" >}}
export http_proxy=http://proxy.example.com:8080/
export https_proxy=http://proxy.example.com:8080/
export no_proxy=google.com, yahoo.com
{{< /code >}}


応用例: HTTP サーバからのプロキシ経由 HTTP アクセス
----

Node.js で立ち上げた HTTP サーバからプロキシ経由でアクセスすることもできます。

```js
var http = require('http');
var request = require('request');

var proxy = request.defaults({'proxy': 'http://proxy.example.com:8080/'})
var server = http.createServer(function (req, res) {
  proxy.get('http://www.google.co.jp/').pipe(res)
});
server.listen(10000);
```

