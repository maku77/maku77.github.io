---
title: TCP/IP で adb 接続する
created: 2014-04-08
layout: android
---

adb を TCP/IP 接続に切り替える
====

adb 接続を USB 経由ではなく、LAN 接続 (TCP/IP プロトコル）で行うようにする手順です。

1. PC 側の adb デーモンを TCP/IP 接続モードにする
  - (PCで) `adb tcpip 5555`
2. PC から端末のアドレスと上記ポートを指定して TCP/IP で接続
  - (PCで) `adb connect 192.168.11.6:5555`
3. 元に戻す場合は以下のように USB 接続待ち受け状態にする
  - (PCで) `adb usb`


トラブルシューティング
====

以下のように offline といわれて接続できない場合は、

```
$ adb devices
List of devices attached
192.168.11.6:5555 offline
```

端末側の USB デバッグモードを Off/On してみるとうまくいったりします。

1. 端末の設定から、「開発者向けオプション」を開く
2. 「USB デバッグ」のチェックを OFF => ON する
3. adb コマンド郡を最初から実行し直す


参考
====
* http://developer.android.com/guide/topics/connectivity/usb/index.html
