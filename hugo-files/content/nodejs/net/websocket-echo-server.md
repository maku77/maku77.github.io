---
title: "Node.jsメモ: Node.js で WebSocket サーバを作る（簡単な echo サーバ）"
url: "p/zrifcfx/"
date: "2014-03-05"
tags: ["nodejs"]
aliases: [/nodejs/net/websocket-echo-server.html]
---

ここでは、Node.js の `websocket.io` モジュールを使って、簡単な WebSocket echo サーバを作成する方法を説明します。
まず、`websocket.io` モジュールをインストールします。

{{< code lang="console" title="websocket.io モジュールのインストール" >}}
$ npm install websocket.io
{{< /code >}}

下記は、クライアントから送られてきたメッセージ（テキスト）をそのまま送り返す、WebSocket による echo サーバの例です。

{{< code lang="js" title="サーバ側 (server.js)" >}}
var ws = require('websocket.io');

// WebSocket サーバの起動
var server = ws.listen(5000, function () {
    console.log('Listening on port 5000');
});

// 各クライアントからの接続を処理
server.on('connection', function (client) {
    console.log('Client connected');

    // クライアントからメッセージを受信した時
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
{{< /code >}}

上記の WebSocket サーバを起動した後、下記のようなクライアントで接続してメッセージを送信します。

{{< code lang="html" title="クライアント側 (index.html)" >}}
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
{{< /code >}}

{{< code title="実行時のログ" >}}
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
{{< /code >}}

