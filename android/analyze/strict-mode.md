---
title: "StrictMode を有効にして望ましくない実装を検出する"
date: "2011-08-10"
---

StrictMode とは
----
**StrictMode** は Gingerbread から追加されたユーティリティで、以下のような望ましくない処理を検出して、ログなどに出力させることができるようになっています。

* メインスレッドからのディスクアクセス
* メインスレッドからのネットワークアクセス
* SQLite 関連オブジェクトの close 忘れ

ただし、JNI 実装内でのディスクアクセスやネットワークアクセスなどは検出されない可能性があるので過信は禁物です。
StrictMode によって何を検出したいかの設定（detectXxx 設定）や、検出した場合にどこに出力するかの設定（penaltyXxx 設定）は、以下のようにコード内で行います。

~~~ java
// For example, detect everything and log anything that's found:
StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
    .detectAll()
    .penaltyLog()
    .build();
StrictMode.setThreadPolicy(policy);
~~~

ここではスレッドに関連するポリシーである `StrictMode.ThreadPolicy` の設定を行っていますが、VM に対する `StrictMode.VmPolicy` の設定も同様に行うことができます。

### 参考リンク

* [StrictMode ｜ Android Developers](http://developer.android.com/reference/android/os/StrictMode.html)
* [StrictMode.ThreadPolicy ｜ Android Developers](http://developer.android.com/reference/android/os/StrictMode.ThreadPolicy.Builder.html)
* [StrictMode.VmPolicy ｜ Android Developers](http://developer.android.com/reference/android/os/StrictMode.VmPolicy.Builder.html)
* [Android Developers Blog: New Gingerbread API: StrictMode](http://android-developers.blogspot.com/2010/12/new-gingerbread-api-strictmode.html)
* [Y.A.M の 雑記帳: Android　Android 2.3 - StrictMode](http://y-anz-m.blogspot.com/2010/12/androidandroid-23-strictmode.html)
* [StrictModeでパフォーマンスをチューニングする - Android(アンドロイド)情報-ブリリアントサービス](http://d.hatena.ne.jp/bs-android/20101229/1293582940)


StrictMode で検出されたバイオレーションログを検索する
----

Logcat のログを、StrictMode タグで絞り込んで、さらにパッケージ名で絞り込むと、StrictMode によって検出された問題個所を素早く見つけることができます。

~~~
# logcat -s StrictMode:* | grep -e 'violation' -e 'com.yourcompany' &
~~~

