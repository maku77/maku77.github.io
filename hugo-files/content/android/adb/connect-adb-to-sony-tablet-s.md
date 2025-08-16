---
title: "Androidメモ: Sony Tablet S に adb 接続する"
url: "p/gg59gt5/"
date: "2011-09-24"
tags: ["android"]
aliases: ["/android/adb/connect-adb-to-sony-tablet-s.html"]
---

基本的には、以下の Sony 公式説明に従えば OK です。

* https://www.sony.jp/support/tablet/products/info/sdk.html

Android SDK のインストールは済んでいて、`adb` コマンドは実行できる状態になっていることを前提としています。


Mac OSX から接続する
----

1. Tablet の設定画面で Debug モード有効にする
1. Tablet と Mac を USB で接続
1. Mac に Vendor ID を登録する
   ```console
   $ echo 0x054c >> $HOME/.android/adb_usb.ini
   ```
1. ADB 接続
   ```console
   $ adb kill-server
   $ adb start-server
   ```
1. 接続できたか確認
   ```console
   $ adb devices
   ```


Windows から接続する
----

1. Tablet の設定画面で Debug モード有効にする
1. Tablet と Windows を USB で接続
1. Google USB Driver をインストール

   1. デバイスマネージャの Sony_Tablet を右クリックして、"Update Driver Software..." を選択。
   1. ドライバのディレクトリは、Android SDK の "extras/google/usb_driver" ディレクトリを選択。

1. Windows に Vendor ID を登録する

   `<AndroidSDK>/extras/google/usb_driver/android_winusb.inf` ファイルの、`[Google.NTx86]` セクションと `[Google.NTamd64]` セクション内に以下を追加します。

   ```
   ;SONY Sony Tablet P
   %CompositeAdbInterface% = USB_Install, USB\VID_054C&PID_04D2&MI_01
   ;SONY Sony Tablet S
   %CompositeAdbInterface% = USB_Install, USB\VID_054C&PID_05B4&MI_01
   ```

   ホームディレクトリの `.android/adb_usb.ini` に 0x54c を追記します。

   ```
   C:\> echo 0x54c >> %HOMEPATH%\.android\adb_usb.ini
   ```

1. 接続できたか確認
   ```
   C:\> adb devices
   List of devices attached
   272531700013441 device
   ```

ちなみに、Vendor ID はデバイスマネージャから以下のように確認できます

1. Sony_Tablet のプロパティを開く
1. Details タブの "Hardware Ids" を選択
1. 以下のように表示されたら Vendor ID は 054C であることが分かります

   ```
   USB\VID_054C&PID_05B4&REV_0100&MI_01
   ```

