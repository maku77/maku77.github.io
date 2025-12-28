---
title: "Node.jsメモ: http 標準モジュールを使用して HTTP サーバを立てる"
url: "p/7cmeoeh/"
date: "2012-11-19"
tags: ["nodejs"]
aliases: [/nodejs/net/http-server.html]
---

http モジュールによる HTTP サーバの基本
----

以下は Node.js の標準モジュールである `http` モジュールを使用して、簡単な Web サーバを立ち上げるサンプルです。
`node` コマンドで実行し、`localhost:51200` というアドレスに Web ブラウザでアクセスすると、Hello World と表示されます。

{{< code lang="js" title="server.js" >}}
var http = require('http');
var server = http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.write('Hello World\n');
  res.end();
});
server.listen(51200);
console.log('Server running at localhost:51200');
{{< /code >}}

`http.createServer()` に渡した関数は、リクエストを受けるたびに呼び出されます。
ポート番号 80 で `listen()` するには、`root` ユーザである必要があります。


{{< code lang="console" title="実行例" >}}
$ node server.js
Server running at localhost:51200
{{< /code >}}

{{< code lang="console" title="curl でレスポンス確認 (1) 基本" >}}
$ curl localhost:51200
Hello World
{{< /code >}}

{{< code lang="console" title="curl でレスポンス確認 (2) ヘッダ付き" >}}
$ curl -i localhost:51200
HTTP/1.1 200 OK
Content-Type: text/plain
Date: Mon, 19 Nov 2012 12:45:07 GMT
Connection: keep-alive
Transfer-Encoding: chunked

Hello World
{{< /code >}}


EventEmitter の on() でイベントハンドラを登録する
----

`http.createServer()` で作られる `http.Server` オブジェクトは、**`EventEmitter`** を継承しており、`on()` メソッドを使ってさまざまなイベントハンドラを登録できます。

ここまでのサンプルでは、`http.createServer()` のパラメータで、リクエストを処理するための関数を指定していましたが、これは `on()` メソッドを使って **`request`** イベントのハンドラを登録する処理のショートカットになっています。
下記の例では、`request` イベントのハンドラを `on()` メソッドで登録しています。

{{< code lang="js" title="server.js" >}}
var http = require('http');
var server = http.createServer();

server.on('request', function(req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.write('Hello World\n');
  res.end();
});
server.listen(51200);
{{< /code >}}

* 参考: [Node.js - http モジュール: createServer メソッド](http://nodejs.org/api/http.html#http_http_createserver_requestlistener)
* 参考: [Node.js - http モジュール: request イベント](http://nodejs.org/api/http.html#http_event_request)

