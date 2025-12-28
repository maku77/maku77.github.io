---
title: "Node.jsメモ: Node.js で WebSocket サーバを作る（簡単な chat サーバ）"
url: "p/hfrkkyx/"
date: "2014-03-05"
tags: ["nodejs"]
aliases: [/nodejs/net/websocket-chat-server.html]
---

WebSocket サーバは、接続中のすべてのクライアントに対して処理を行うことができます。
例えば、以下のようなプロパティがあります。

* `server.clientsCount` -- 接続中のクライアント数
* `server.clients` -- 接続中のクライアントのリスト

すべてのクライアントに対してメッセージを送信すれば、簡単なチャットサーバになります。

{{< code lang="js" title="サーバ側 (server.js)" >}}
var ws = require('websocket.io');

// WebSocket サーバの起動
var server = ws.listen(5000, function () {
  console.log('Listening on port 5000');
});

// 各クライアントからの接続を処理
server.on('connection', function (client) {
  console.log('Client connected: ' + server.clientsCount);

  // クライアントからメッセージを受信した時
  client.on('message', function (msg) {
    // 全クライアントに送信
    server.clients.forEach(function (c) {
      if (c == null) return;  // 接続が切れると null になっていることがある
      c.send(msg);
    });
  });

  // クライアントが切断した時
  client.on('close', function () {
    console.log('Client disconnected: ' + server.clientsCount);
  });

  // エラーが発生した時
  client.on('error', function (err) {
    console.log('Error: ' + err.code);
  });
});
{{< /code >}}

{{< code lang="html" title="クライアント側 (index.html)" >}}
<input type="text" id="myInput" autofocus></input>
<pre id="myOutput"></pre>

<script>
var socket = new WebSocket('ws://localhost:5000/');
socket.onopen = function () { console.log('Connected'); };
socket.onclose = function () { console.log('Disconnected'); };

// サーバからメッセージを受信した時
socket.onmessage = function (evt) {
  myOutput.innerText = evt.data + '\n' + myOutput.innerText;
};

// テキストボックスからメッセージ送信
myInput.onkeydown = function (evt) {
  if (evt.keyCode === 13) {  // ENTER key
    socket.send(this.value);
    this.value = '';
  }
};
</script>
{{< /code >}}

