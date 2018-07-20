---
title: Nexus7 (2013) に adb 接続する
date: "2013-11-06"
---

Android タブレットの Nexus7 に、`adb` コマンドで接続するまでの手順です。
下記は Android 4.3 で確認しています。

Android の設定メニューに「開発者向けオプション」を表示
----

1. 設定 => タブレット情報 (About tablet)
2. 「ビルド番号 (Build number)」を 7 回タップ

この操作で「デベロッパー」として認識され、設定メニューに「開発者向けオプション (Developer options)」の項目が表示されます。
開発者向けオプションでは、USB による adb 接続を行うために以下のチェックを入れておきます。

* USBデバッグ

この設定を行ってから USB Driver を更新する必要があります。


Windows 7 に USB Driver をインストール
----

* http://developer.android.com/sdk/win-usb.html

の指示に従ってドライバのダウンロード、インストールをすれば OK です。
簡単に手順をまとめると、

1. Google USB Driver をダウンロードして展開しておく（SDK Manager で GUI からダウンロードも可能）
2. 「開発者向けオプション/USBデバッグ」を ON にした状態で Nexus7 を USB で接続
3. デバイスマネージャを起動（"Computer" を右クリック => "Manage" => 左側のペーンから "Device Manager" を選択）
4. "Other devices/Nexus 7" を右クリック => "Update Driver Software..."
   （"Portable Devices/Nexus 7" の方と間違えないように。"Other devices" の方が表示されない場合は、USBデバッグが ON になっていない可能性が高いです。）
5. ドライバの入っているディレクトリを選択
  - SDK Manager でダウンロードした場合: <android-sdk>/extras/google/usb_driver
  - 手動ダウンロードした場合: 展開してできた usb_driver ディレクトリを選択


ADB を最新版に更新
----

1. SDK Manager で以下を最新にアップデートしておく（これやらないと adb devices で認識しないことあり）
  - Android SDK Platoform-tools（2013-11-06 時点で rev.19）


コマンドラインから ADB 接続
----

```
> adb kill-server
> adb devices
```

ここで Nexus7 の画面に「USBデバッグを許可しますか？」のダイアログが出るので「OK」を選択。
これで Nexus7 に ADB 接続できるようになります。

```
> adb shell
shell@flo:/ $
```

