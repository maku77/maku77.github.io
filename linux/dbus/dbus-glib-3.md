---
title: dbus-glib で method call を実装する（サーバ側の実装）
date: "2012-05-08"
---

下記は dbus-glib によるサーバ実装のファイル一式です。

- [dbus-glib-server.zip](./dbus-glib-server.zip)

実行してから `qdbusviewer` アプリの表示を見ると、セッションバスに登録された `com.example.MyApp` サービスを確認することができます。

![dbus-glib-server.png](./dbus-glib-server.png)

ちゃんと、`com.example.MyApp.Calc` インタフェースが公開されていることが分かります。


