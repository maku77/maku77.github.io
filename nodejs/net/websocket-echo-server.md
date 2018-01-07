---
title: Node.js で WebSocket サーバを作る（簡単な echo サーバ）
date: "2014-03-05"
---

ここでは、Node.js で WebSocket サーバを作成するために、`websocket.io` モジュールを使用します。

#### websocket.io モジュールのインストール

```
$ npm install websocket.io
```

下記は、クライアントから送られてきたメッセージ（テキスト）をそのまま送り返す、WebSocket による echo サーバの例です。

#### サーバ側 (server.js)

```javascript
var ws = require('websocket.io274');

// WebSocket サーバの起動
var server = ws.listen(5000, function () {
    console.log('Listening on port 5000');
});

// 各クライアントからの接続を処理
server.on('connection', function (client) {
    console.log('Client connected');

    // クライアントからメッセージ受信した時
    client.on('message', function (msg) {
        console.log('Message from client: ' + msg);
        client.send(msg);
    });

    // クライアントが切断した時
    client.on('close', function () {
        console.log('Client disconnected');
    });

    // エラーが発生した時
    client.on('error', function (err) {
        console.log('Error: ' + err.code);
    });
});
```

#### クライアント側 (index.html)

```html
<script>
var socket = new WebSocket('ws://localhost:5000/');

// 接続時のイベント
socket.onopen = function (evt) {
    console.log('Connected');

    console.log('Send: AAAAA');
    socket.send('AAAAA');
    console.log('Send: BBBBB');
    socket.send('BBBBB');
    console.log('Send: CCCCC');
    socket.send('CCCCC');
};

// 切断時のイベント
socket.onclose = function (closeEvent) {
    console.log('Disconnected');
};

// メッセージ受信時のイベント
socket.onmessage = function (event) {
    console.log('Message from Server: ' + event.data);
    if (event.data === 'CCCCC') {
        socket.close();
    }
};
</script>
```

#### 実行時のログ

```
# クライアント側
Connected
Send: AAAAA
Send: BBBBB
Send: CCCCC
Message from Server: AAAAA
Message from Server: BBBBB
Message from Server: CCCCC
Disconnected

# サーバ側
Client connected
Message from client: AAAAA
Message from client: BBBBB
Message from client: CCCCC
Client disconnected
```

