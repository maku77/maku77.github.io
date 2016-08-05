---
title: http 標準モジュールを使用して HTTP サーバを立てる
created: 2012-11-19
---

http モジュールによる HTTP サーバの基本
----

以下は Node.js の標準モジュールである `http` モジュールを使用して、簡単な Web サーバを立ち上げるサンプルです。
`node` コマンドで実行し、`localhost:51200` というアドレスに Web ブラウザでアクセスすると、Hello World と表示します。

#### server.js

```javascript
var http = require('http');
var server = http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.write('Hello World\n');
  res.end();
});
server.listen(51200);
console.log('Server running at localhost:51200');
```

`http.createServer()` に渡した関数は、リクエストを受けるたびに呼び出されます。
ポート番号 80 で `listen()` するためには、`root` ユーザである必要があります。


#### 実行例

```
$ node server.js
Server running at localhost:51200
```

#### curl でレスポンス確認

```
$ curl localhost:51200
Hello World
```

```
$ curl -i localhost:51200
HTTP/1.1 200 OK
Content-Type: text/plain
Date: Mon, 19 Nov 2012 12:45:07 GMT
Connection: keep-alive
Transfer-Encoding: chunked

Hello World
```

EventEmitter の on() でイベントハンドラを登録する
----

`http.createServer()` で作られる `http.Server` オブジェクトは、**EventEmitter** を継承しており、`on()` メソッドを使って様々なイベントハンドラを登録することができるようになっています。

ここまでのサンプルでは、`http.createServer()` のパラメータで、リクエストを処理するための関数を指定していましたが、これは `on()` メソッドを使って **request** イベントのハンドラを登録する処理のショートカットになっています。
下記の例では、`request` イベントのハンドラを、`on()` メソッドを使用して登録しています。

#### server.js

```javascript
var http = require('http');
var server = http.createServer();

server.on('request', function(req, res) {
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.write('Hello World\n');
  res.end();
});
server.listen(51200);
```

#### 参考サイト

* [Node.js - http モジュール: createServer メソッド](http://nodejs.org/api/http.html#http_http_createserver_requestlistener)
* [Node.js - http モジュール: request イベント](http://nodejs.org/api/http.html#http_event_request)

