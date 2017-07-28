---
title: APK ファイルに署名する
created: 2015-03-19
---

APK に署名する方法は、従来の Java で行われていた JAR ファイルへの署名方法と同様です。
下記の JAR ファイルへの署名方法が参考になります。

- [JAR ファイルの署名について - まくまく Java ノート](/java/jar/jarsigner.html)

(1) keystore ファイルを作成する
----

ここでは下記の条件でキーを作成します。

- 作成するキーストアファイル名: **MyKeyStore.jks**
- エイリアス名: **android**
- 有効期限: **10000日**

~~~
$ keytool -genkey -v -keystore MyKeyStore.jks -alias android -keyalg RSA -keysize 2048 -validity 10000
Enter keystore password:（キーストアのパスワードを入力）
（名前や組織名などを聞かれるので順番に入力していく）
~~~

(2) keystore ファイルを使って APK に署名する
----

上記のように作成した keystore ファイルには、非公開鍵と、それに関連付けられた X.509 証明書が含まれており、このファイルを使用して APK に署名を行うことができます。
ここでは、`MyApp.apk` に署名を行います。

~~~
$ jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore MyKeyStore.jks MyApp.apk android
（キーストアのパスワードを入力）（キーエイリアスのパスワードを入力）
~~~

(3) APK の署名を確認する
----

~~~
$ jarsigner -verify -verbose -certs MyApp.apk
~~~

- 参考: [APK ファイルの署名を確認する](verify-certs.html)

(4) メモリ使用量を最適化するためのバイトアライメント
----

~~~
$ zipalign -v 4 input.apk output.apk
~~~

