---
title: "Androidメモ: TCP/IP で adb 接続する"
url: "p/492mmkw/"
date: "2014-04-08"
lastmod: "2021-10-15"
tags: ["android"]
aliases: ["/android/adb/connect-adb-with-tcpip.html"]
---

ADB を TCP/IP 接続に切り替える
----

Android アプリ開発時に、ADB 接続を USB 経由ではなく LAN 経由での接続 (TCP/IP プロトコル）で行うようにする手順です。

{{< image src="img-001.svg" >}}

Android 端末側の ADB デーモンを TCP/IP モードに切り替えないといけないので、この設定自体は USB 接続された状態で行う必要があります。
一度設定してしまえば、次回からは USB ケーブルは必要なくなります。

1. Android 端末側の「開発者向けオプション」を有効にして USB 接続する
   - [設定] > [デバイス情報] に移動して、[ビルド番号] を 7 回タップすると「開発者向けオプション」が有効になります。
2. （USB 接続された状態で）Android 端末側の ADB デーモンを TCP/IP 接続モードにする
   - `adb tcpip 5555`
3. Android 端末の LAN 内の IP アドレスを確認しておく
   - `adb shell "ip addr | grep inet"`
4. （この時点で USB ケーブルは外して OK）
5. PC から Android 端末のアドレスとポート番号を指定して TCP/IP で接続
   - `adb connect 192.168.11.6:5555`

USB 接続に戻したいときは、`adb usb` コマンドを実行します。


トラブルシューティング
----

以下のように offline といわれて接続できない場合は、

```console
$ adb devices
List of devices attached
192.168.11.6:5555 offline
```

端末側の USB デバッグモードを Off/On してみるとうまくいったりします。

1. 端末の設定から、「開発者向けオプション」を開く
2. 「USB デバッグ」のチェックを OFF => ON する
3. `adb` コマンド郡を最初から実行し直す


参考
----

* [https://developer.android.com/guide/topics/connectivity/usb](https://developer.android.com/guide/topics/connectivity/usb)

