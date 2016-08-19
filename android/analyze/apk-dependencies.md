---
title: APK のパッケージ依存関係やクラス依存関係を調べる
created: 2016-08-17
---

ここでは、`jdeps` と `dex2jar` を使って、APK ファイル内の依存関係を調査する方法を示します。

jdeps と dex2jar
----

JDK 8 にはパッケージ依存関係やクラス依存関係を調べるための `jdeps` コマンドが標準搭載されました。

- [jdeps コマンド - Oracle Java Documentation](https://docs.oracle.com/javase/jp/8/docs/technotes/tools/unix/jdeps.html)

これを使用すると、.class ファイル（あるいは .jar ファイル）を入力情報として、そこから参照しているクラスやパッケージの情報を調べることができるのですが、Android の APK としてビルドされたコードは .dex ファイルになっていますので、まずはこれを通常の .class 形式に変更してやる必要があります。
APK ファイル内の .dex を .class 形式に変換するには、**dex2jar** というツールを使用します。

- [dex2jar のダウンロード](https://github.com/pxb1988/dex2jar/releases)

上記から、たとえば `dex-tools-2.1-20150601.060031-26.zip` をダウンロードし、パスの通ったディレクトリに展開すればインストール完了です。


APK ファイルのパッケージ依存、クラス依存情報を調べる
----

まず、`d2j-dex2jar` コマンドを使用して、APK ファイル内の .dex を .class 形式に変換します。
APK ファイルを unzip して取り出した .dex ファイルを変換することもできますが、下記のように直接 APK ファイルを .jar に変換してしまうのが手っ取り早いです。

```
$ d2j-dex2jar app/build/outputs/apk/app-debug.apk -o app.jar
dex2jar app/build/outputs/apk/app-debug.apk -> app.jar
```

これで .class ファイルの含まれた .jar ファイルが生成されるので、晴れて `jdeps` コマンドで依存関係を調べられるようになります。

```
$ jdeps app.jar
...
   com.example.myapp.function (app.jar)
      -> android.content                                    見つかりません
      -> android.content.pm                                 見つかりません
      -> android.os                                         見つかりません
      -> android.text                                       見つかりません
      -> com.google.android.youtube.player                  app.jar
      -> com.example.myapp.common.log                       app.jar
      -> com.example.myapp.common.dialog                    app.jar
      -> java.io
      -> java.lang
      -> java.lang.reflect
      -> java.text
      -> java.util
...
```

デフォルトでは上記のようにパッケージ間の依存関係が出力されますが、`-verbose:class` オプションを指定すれば、クラスレベルで依存関係を出力することができます（大量に出力されます）。

```
$ jdeps -verbose:class app.jar
...
   com.example.myapp.function.LoggerService (app.jar)
      -> android.app.Service                                見つかりません
      -> android.content.ContentResolver                    見つかりません
      -> android.content.Context                            見つかりません
      -> android.content.Intent                             見つかりません
      -> android.net.Uri                                    見つかりません
      -> android.os.Handler                                 見つかりません
      -> android.os.IBinder                                 見つかりません
      -> com.example.myapp.common.log                       app.jar
      -> com.example.myapp.common.text                      app.jar
...
```

ちなみに上記の出力の「見つかりません」というのは、依存先のクラスが格納されたライブラリが見つからないことを示しています。
下記のように、外部 .jar ファイルに対してクラスパスを通してやれば、依存している .jar ファイルの名前も出力されるようになります（下記では、`libs_ext` というディレクトリに Shared library としての .jar ファイルが格納されているとします）。

```
(Windows の場合)
$ jdeps -cp libs_ext/*;%ANDROID_HOME%/platforms/android-23/* app.jar

(Linux の場合)
$ jdeps -cp libs_ext/*:$ANDROID_HOME/platforms/android-23/* app.jar
```

Android や Java のコアライブラリが提供しているクラスへの依存情報は不要だという場合は、下記のように `-f` オプションで必要のない依存情報をフィルタすることができます。

```
$ jdeps -f "java.*|android.*|dalvik.*" app.jar
```
