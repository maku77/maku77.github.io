---
title: WebSocket クライアントを作成する
date: "2014-03-05"
---

JavaScript の WebSocket API を使うときの流れは以下のようになります。

1. `WebSocket` コンストラクタでサーバに接続
2. イベントハンドラを設定
   - `onopen`: 接続時のイベント
   - `onclose`: 切断時のイベント
   - `onmessage`: メッセージ受信時のイベント
   - `onerror`: エラー発生時のイベント
3. 任意のタイミングで、
   - `send()`: データの送信
   - `close()`: 切断

次のサンプルでは、`ws://echo.websocket.org/` に接続しています。
この WebSocket サーバは開発者向けに公開されており、`WebSocket#send()` で送ったメッセージをそのまま返してくるように実装されています。

#### index.html

```html
<script>
var socket = new WebSocket('ws://echo.websocket.org/');

// 接続時のイベント
socket.onopen = function (evt) {
  console.log('Connected');
  socket.send('Hello 1');
  socket.send('Hello 2');
  socket.send('Hello 3');
  socket.send('Hello 4');
};

// 切断時のイベント
socket.onclose = function (evt) {
  console.log('Disconnected');
};

// メッセージ受信時のイベント
socket.onmessage = function (evt) {
  console.log(evt.data);
  if (evt.data === 'Hello 3') {
    socket.close();
  }
};

// エラー発生時のイベント
socket.onerror = function (evt) {
  console.log('Error: ' + evt.data);
};
</script>
```

