---
title: "APK ファイルに署名する (keytool, jarsigner)"
url: "p/3yk3j2i/"
date: "2015-03-19"
tags: ["Android"]
aliases: /android/tools/sign-jar.html
---

APK に署名する方法は、従来の Java で行われていた JAR ファイルへの署名方法と同様です。
下記の JAR ファイルへの署名方法を参考にしてください。

- 参考: [JAR ファイルの署名について - まくまく Java ノート](/p/fht5eox/)


(1) keystore ファイルを作成する
----

ここでは下記の条件でキーを作成します。

- 作成するキーストアファイル名: __`MyKeyStore.jks`__
- エイリアス名: __`android`__
- 有効期限: __`10000日`__

```console
$ keytool -genkey -v -keystore MyKeyStore.jks -alias android -keyalg RSA -keysize 2048 -validity 10000
Enter keystore password:（キーストアのパスワードを入力）
（名前や組織名などを聞かれるので順番に入力していく）
```


(2) keystore ファイルを使って APK に署名する
----

上記のように作成した keystore ファイルには、非公開鍵と、それに関連付けられた X.509 証明書が含まれており、このファイルを使用して APK に署名を行うことができます。
ここでは、`MyApp.apk` に署名を行います。

```console
$ jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore MyKeyStore.jks MyApp.apk android
（キーストアのパスワードを入力）（キーエイリアスのパスワードを入力）
```


(3) APK の署名を確認する
----

```console
$ jarsigner -verify -verbose -certs MyApp.apk
```

- 参考: [APK ファイルの署名を確認する](/p/2x9it3c/)


(4) メモリ使用量を最適化するためのバイトアライメントを実行する
----

```console
$ zipalign -v 4 input.apk output.apk
```

