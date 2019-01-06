---
title: "SHARP IS03 に adb 接続する"
date: "2012-10-09"
---

SHARP のスマホ IS03 を adb devices コマンドで認識させるための手順です。
下記は Windows の場合の手順です。

Android SDK のインストールは済んでいて、adb コマンドは実行できる状態になっているとします。

(1) IS03 用 USB ドライバをダウンロード＆インストール
----

* [http://k-tai.sharp.co.jp/support/a/is03/download.html#usb_driver](http://k-tai.sharp.co.jp/support/a/is03/download.html#usb_driver)

ドライバをインストールする前に USB でつないでいた場合は、正しく認識できなくなってしまうことがあるようです。
その場合は、「プログラムの追加と削除」から一度、「au IS03 USB Software」をアンインストールしてから、USB 接続を外した状態で、再度インストールします。


(2) Sharp 共通の ADB USBドライバをダウンロード＆展開
----

* [https://sh-dev.sharp.co.jp/android/modules/driver/](https://sh-dev.sharp.co.jp/android/modules/driver/)

手順 3 でインストールするドライバをダウンロードして、展開しておきます。

(3) ADB USB ドライバをインストール
----

Android 端末の USB デバッグを有効にし（設定 ＞ アプリケーション ＞ 開発）、USB 接続すると、ドライバのインストールダイアログがポップアップするので、手順 2 で展開した ADB USB ドライバのディレクトリを選択してインストールします。

ポップアップしない場合は、「高速転送モード」に切り替えると出たりします。

それでも出ない場合は、「Device Manager」から、Other devices/Android ADB Interface などを見つけられれば、再インストール可能です。

(4) 確認
----

インストールが完了すれば、以下のように adb から認識されれいるはずです。

~~~
C:\> adb devices
List of devices attached
SSHEV012345     device
~~~

