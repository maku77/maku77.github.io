---
title: "Androidメモ: StrictMode を有効にして望ましくない実装を検出する"
url: "p/yvit8zb/"
date: "2011-08-10"
tags: ["android"]
aliases: ["/android/analyze/strict-mode.html"]
---

StrictMode とは
----

**StrictMode** は Android Gingerbread から追加されたユーティリティで、以下のような望ましくない処理を検出して、ログなどに出力させることができるようになっています。

* メインスレッドからのディスクアクセス
* メインスレッドからのネットワークアクセス
* SQLite 関連オブジェクトの close 忘れ

ただし、JNI 実装内でのディスクアクセスやネットワークアクセスなどは検出されない可能性があるので過信は禁物です。
StrictMode によって何を検出したいかの設定（`detect*` 設定）や、検出した場合にどこに出力するかの設定（`penalty*` 設定）を以下のようにコード内で行います。

```java
// すべてを検出し、見つかったものをログに出力する場合
StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
    .detectAll()
    .penaltyLog()
    .build();
StrictMode.setThreadPolicy(policy);
```

ここではスレッドに関連するポリシーである **`StrictMode.ThreadPolicy`** の設定を行っていますが、VM に対する **`StrictMode.VmPolicy`** の設定も同様に行うことができます。

### 参考リンク

- [StrictMode ｜ Android Developers](https://developer.android.com/reference/android/os/StrictMode.html)
- [StrictMode.ThreadPolicy ｜ Android Developers](https://developer.android.com/reference/android/os/StrictMode.ThreadPolicy.Builder.html)
- [StrictMode.VmPolicy ｜ Android Developers](https://developer.android.com/reference/android/os/StrictMode.VmPolicy.Builder.html)
- [Android Developers Blog: New Gingerbread API: StrictMode](https://android-developers.blogspot.com/2010/12/new-gingerbread-api-strictmode.html)
- [Y.A.M の 雑記帳: Android　Android 2.3 - StrictMode](https://y-anz-m.blogspot.com/2010/12/androidandroid-23-strictmode.html)
- [StrictModeでパフォーマンスをチューニングする - Android(アンドロイド)情報-ブリリアントサービス](https://d.hatena.ne.jp/bs-android/20101229/1293582940)


StrictMode で検出されたバイオレーションログを検索する
----

Logcat ログを StrictMode タグで絞り込んで、さらにパッケージ名で絞り込むと、StrictMode によって検出された問題個所を素早く見つけることができます。

```console
$ logcat -s StrictMode:* | grep -e 'violation' -e 'com.yourcompany' &
```


応用
----

- [StrictMode の違反メッセージを Toast で表示する (`StrictModeToaster`)](/p/gv2dqi3/)

