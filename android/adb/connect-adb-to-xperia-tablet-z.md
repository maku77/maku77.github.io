---
title: Xperia Tablet Z に adb 接続する
date: "2014-05-30"
---

まずは、下記と同様に「開発者向けオプション」を有効にし、「USB デバッグ」を有効にしておきます。

* [Nexus7 (2013) に adb 接続する](connect-adb-to-nexus.html)


Windows への ADB ドライバのインストール
----

Xperia には、端末側に "PC Companion software" というものが入っていて、USB 経由で PC 側にインストールすることができます。
同時に ADB ドライバもインストールされます。

1. Xperia の「Settings => Xperia => USB Connectivity => Install PC Companion」にチェックを入れておく。
2. USB で PC と接続すると、Xperia 側にインストール確認のダイアログが表示されるので、"Install" を選択。
3. PC 側でインストール用のドライブが開くのでアイコンからインストーラを起動 (Startme.exe)。

最初に ADB 接続するときは、端末を MTP (Media transfer mode) から、MSC (Mass storage mode) に変えておかないと adb devices で認識しないことがあるみたいです。

1. Xperia の「Settings => Xperia => USB Connectivity => USB connection mode」を選択。
2. MSC (Mass storage mode) を選択。

あとは、以下のように接続を確認できます。

```
C:\> adb kill-server
C:\> adb start-server
C:\> adb devices
```

