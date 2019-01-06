---
title: "Sony Tablet S に adb 接続する"
date: "2011-09-24"
---

基本的には、以下の Sony 公式説明に従えば OK です。

* [http://www.sony.jp/support/tablet/products/info/sdk.html](http://www.sony.jp/support/tablet/products/info/sdk.html)

Android SDK のインストールは済んでいて、adb コマンドは実行できる状態になっているとします。

Mac OSX から接続する
----

~~~
(1) Tablet の設定画面で Debug モード有効にする
(2) Tablet と Mac を USB で接続
(3) Mac に Vendor ID を登録する

     $ echo 0x054c >> $HOME/.android/adb_usb.ini

(4) ADB 接続

    $ adb kill-server
    $ adb start-server

(5) 接続できたか確認

    $ adb devices
~~~

Windows から接続する
----

~~~
(1) Tablet の設定画面で Debug モード有効にする
(2) Tablet と Windows を USB で接続
(3) Google USB Driver をインストール

    (a) デバイスマネージャの Sony_Tablet を右クリックして、"Update Driver Software..." を選択。
    (b) ドライバのディレクトリは、Android SDK の "extras/google/usb_driver" ディレクトリを選択。

(4) Windows に Vendor ID を登録する

   <AndroidSDK>/extras/google/usb_driver/android_winusb.inf ファイルの、
   [Google.NTx86] セクションと、[Google.NTamd64] セクション内に以下を追加します。

     ;SONY Sony Tablet P
     %CompositeAdbInterface% = USB_Install, USB\VID_054C&PID_04D2&MI_01
     ;SONY Sony Tablet S
     %CompositeAdbInterface% = USB_Install, USB\VID_054C&PID_05B4&MI_01

   ホームディレクトリの .android/adb_usb.ini に 0x54c を追記します。

     echo 0x54c >> %HOMEPATH%\.android\adb_usb.ini

(5) 接続できたか確認

    C:\> adb devices
    List of devices attached
    272531700013441 device
~~~

ちなみに、Vendor ID はデバイスマネージャから以下のように確認できます

~~~
(a) Sony_Tablet のプロパティを開く
(b) Details タブの "Hardware Ids" を選択
    USB\VID_054C&PID_05B4&REV_0100&MI_01
(c) 上記のように表示されたら Vendor ID は 054C であることが分かる
~~~

