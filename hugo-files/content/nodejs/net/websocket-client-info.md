---
title: "Node.jsメモ: WebSocket サーバに接続してきたクライアントの情報を調べる"
url: "p/hn2896q/"
date: "2014-03-27"
tags: ["nodejs"]
aliases: [/nodejs/net/websocket-client-info.html]
---

WebSocket で接続してきたクライアントの URL は、`connection` イベントのハンドラに渡される `client` オブジェクトの `req.url` プロパティで参照することができます。

```js
var ws = require('websocket.io');

// WebSocket サーバの起動
var server = ws.listen(5000, function () {
  console.log('Listening on port 5000');
});

// 各クライアントからの接続を処理
server.on('connection', function (client) {
  var url = client.req.url;  //=> 例: '/aaa/bbb' など
});
```

