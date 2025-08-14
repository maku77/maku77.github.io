---
title: "Android Nativeメモ: ネイティブライブラリ (.so) の展開先"
url: "p/9dzwawd/"
date: "2011-03-31"
tags: ["android"]
aliases: [/android/install-path-of-native-libs.html]
---

Android 1.5 から JNI 呼び出しで用いる共有ライブラリ (`.so`) は、APK の作成時に自動的にパッケージングされるようになりました。

- 共有ライブラリはアプリ (`.apk`) をインストールするときに、自動的に `/data/data/<package>/lib` に展開される。
- アプリ起動時に `/data/data/<package>/lib` の下の共有ライブラリ (`.so`) は動的にロードできるようパスが通される。
- アプリのアンインストール時には共有ライブラリは自動的に削除される。

