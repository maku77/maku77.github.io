---
title: Eclipse から Android SDK を使えるようにする (ADK)
date: "2010-04-11"
---

（Android SDK のインストールは済んでいるとします）

Eclipse に Android Development Toolkit (ADK) をインストール
====

Eclipse から Android SDK を使用するには、**ADK** というプラグインをインストールします。

1. Eclipse メニューから [Help] => [Install New Software...]。
  - Work with: の欄に "https://dl-ssl.google.com/android/eclipse/" と入力してインストール。
2. Android SDK のパスを設定
  - Eclipse のメニューから [Window] => [Preferences]。
  - [Android] を選択し、SDK Location に SDK を展開したディレクトリのパスを入力。
    （これをやっておかないと、New Android Project のダイアログで Android SDK has not been setup. と出てプロジェクトを作成できません）

プロキシ設定
----

プロキシ環境にいる場合は、Window => Preferences => General => Network Connections から設定します。

プロキシを設定してもダメな場合は、以下のようなことを試すとうまくいくかもしれません。

* アドレスを `https://` から `http://` に変える
* Eclipse のバージョンを最新にする


Android SDK の Package のアップデート
====

1. Eclipse のメニューから [Window] => [Android SDK and ADV Manager]。
2. [Available Packages] を選択し、URL を選択して、[Install Selected] をクリック。


SDK のアップデート中に fetching... から進まない場合
----
プロキシ設定を行う必要があります。Eclipse そのもののプロキシ設定ではなく、**Android SDK and AVD Manager** ウィンドウの [Settings] という項目で設定します。

```
HTTP Proxy Server: proxy.example.com
HTTP Proxy Port: 10080
```

のような感じで設定します。


Eclipse & Android SDK で Hello World
====
1. Eclipse のメニューから [File] => [New] => [Android Project]。
2. New Android Project ダイアログが出るので適当に入力。
  - Project name: HelloAndroid
  - Application name: Hello, Android
  - Package name: org.example.hello
  - Create Activity: Hello
3. [Run] => [Run] でエミュレータを起動できる。

トラブルシューティング: An SDK Target must be specified.
----
PC モニタの解像度が低いと、New Android Project の画面で Build Target の欄が選択できず、**An SDK Target must be specified.** と出て先に進めません。
このような場合は、以下のようにフォントサイズを小さくすればいけるらしいです。

1. Window / Preferences / General / Appearance / Colours and Fonts
2. Change “Text Font” and “Dialog Font” to a smaller value, dropping from 10 to 8 helped me.

