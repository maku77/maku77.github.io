---
title: プロキシ経由の HTTP 通信を行う
date: "2012-12-07"
---

request モジュールによるプロキシ経由の HTTP アクセス
----

プロキシ経由で HTTP アクセスするときは、**request** モジュールを使うとお手軽です。

* https://github.com/mikeal/request/blob/master/README.md

#### プロキシ経由で HTTP GET

```javascript
var request = require('request');

var proxy = request.defaults({'proxy': 'http://proxy.example.com:8080/'})
proxy.get('http://www.google.com/', function (error, response, body) {
  if (!error && response.statusCode == 200) {
    console.log(body);
  }
});
```

コードの中でプロキシ設定を行うのではなく、環境変数で設定しておくことも可能です。

* https://github.com/request/request#controlling-proxy-behaviour-using-environment-variables
 - `HTTP_PROXY` (`http_proxy`)
 - `HTTPS_PROXY` (`https_proxy`)
 - `NO_PROXY` (`no_proxy`)

#### bash における設定例

```shell
export http_proxy=http://proxy.example.com:8080/
export https_proxy=http://proxy.example.com:8080/
export no_proxy=google.com, yahoo.com
```

応用例: HTTP サーバからのプロキシ経由 HTTP アクセス
----

Node.js で立ち上げた HTTP サーバの中からプロキシ経由でアクセスさせるようなこともできます。

```javascript
var http = require('http');
var request = require('request');

var proxy = request.defaults({'proxy': 'http://proxy.example.com:8080/'})
var server = http.createServer(function (req, res) {
  proxy.get('http://www.google.co.jp/').pipe(res)
});
server.listen(10000);
```

