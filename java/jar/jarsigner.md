---
title: "JAR ファイルの署名について"
date: "2012-09-27"
---

署名は何に使われるか？
----

JAR の署名は、各種リソースへのアクセス権を制御するために使用されます。

例:

* JavaWebStart や Applet では署名することでローカルファイルにアクセスできるようになる。
* Android の APK は署名によりコンポーネント間のアクセス権を制御できる。


keystore ファイルの作成
----

署名に必要な **keystore ファイル** (`*.jks` や `*.keystore`) は、JDK に付属している `keytool` コマンドで作成することができます。
リリース用に作成した keystore ファイルは、厳重に管理する必要があります。

```
$ keytool -genkey -v -keystore <キーストアファイル名> -alias <エイリアス名> -keyalg RSA -keysize 2048 -validity <有効日数>
```

keystore ファイルには複数のキーを含むことができるので、どのキーかを表すエイリアス名を指定する必要があります。

#### 実行例

```
$ keytool -genkey -v -keystore MyKeyStore.jks -alias myapp -keyalg RSA -keysize 2048 -validity 10000
Enter keystore password:（キーストアのパスワードを入力）
（名前や組織名などを聞かれるので順番に入力していく）
（最後にキー（エイリアス）に対するパスワードを入力）
```


keystore ファイルで JAR ファイルに署名する
----

keystore ファイルには、非公開鍵と、それに関連付けられた X.509 証明書が含まれており、それらの情報を使用して JAR ファイルに署名を行うことができます。

```
$ jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore <キーストアファイル名> <JAR名> <エイリアス名>
```

#### 実行例

```
$ jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore MyKeyStore.jks myapp.jar myapp
（キーストアのパスワードを入力）
（エイリアスのパスワードを入力）
```


JAR ファイルの署名を確認する
----

JAR ファイルに付けられた署名を確認するには以下のようにします。

```
$ jarsigner -verify -verbose -certs signed.jar
```


JAR ファイルから署名を取り除く
----

JAR ファイルの署名の実体は、アーカイブ内の `META-INF` ディレクトリ内の下記のファイル群です。

* `META-INF/xxx.SF`（署名）
* `META-INF/xxx.DSA` (or `xxx.RSA`)（署名ブロック）

アーカイブからこれらのファイルを取り除き、再パッケージングすれば署名なしのアーカイブを作成できます。
下記は、署名付きの `singed.jar` から、署名なしの `unsigned.jar` を作成する例です。

#### Linux の場合

```
$ mkdir temp
$ cd temp
$ jar xvf ../signed.jar
$ rm -rf META-INF
$ cd ..
$ jar cvf unsigned.jar -C temp .
$ rm -rf temp
```

#### Windows の場合

```
D:\> mkdir temp
D:\> cd temp
D:\> jar xf ../signed.jar
D:\> rmdir /S /Q META-INF
D:\> cd ..
D:\> jar cf unsigned.jar -C temp .
D:\> rmdir /S /Q temp
```

