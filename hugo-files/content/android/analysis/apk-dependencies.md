---
title: "APK のパッケージ依存関係やクラス依存関係を調べる"
url: "p/w6ste5j/"
date: "2016-08-17"
tags: ["Android"]
aliases: /android/analyze/apk-dependencies.html
---

ここでは、`jdeps` と `dex2jar` を使って、APK ファイル内の依存関係を調査する方法を示します。

jdeps と dex2jar
----

JDK 8 にはパッケージ依存関係やクラス依存関係を調べるための __`jdeps`__ コマンドが標準搭載されました。

- [jdeps コマンド - Oracle Java Documentation](https://docs.oracle.com/javase/jp/8/docs/technotes/tools/unix/jdeps.html)

これを使用すると、`.class` ファイル（あるいは `.jar` ファイル）を入力情報として、そこから参照しているクラスやパッケージの情報を調べることができるのですが、Android の APK としてビルドされたコードは `.dex` ファイルになっていますので、まずはこれを通常の `.class` 形式に変更してやる必要があります。
APK ファイル内の `.dex` を `.class` 形式に変換するには、__`dex2jar`__ というツールを使用します。

- [dex2jar のダウンロード](https://github.com/pxb1988/dex2jar/releases)

上記から、たとえば `dex-tools-2.1-20150601.060031-26.zip` をダウンロードし、パスの通ったディレクトリに展開すればインストール完了です。


APK ファイルのパッケージ依存、クラス依存情報を調べる
----

まず、`d2j-dex2jar` コマンドを使用して、APK ファイル内の `.dex` を `.class` 形式に変換します。
APK ファイルを unzip して取り出した `.dex` ファイルを変換することもできますが、下記のように直接 APK ファイルを `.jar` に変換してしまうのが手っ取り早いです。

{{< code lang="console" title="apk から jar への変換" >}}
$ d2j-dex2jar app/build/outputs/apk/app-debug.apk -o app.jar
dex2jar app/build/outputs/apk/app-debug.apk -> app.jar
{{< /code >}}

これで `.class` ファイルの含まれた `.jar` ファイルが生成されるので、晴れて `jdeps` コマンドで依存関係を調べられるようになります。

```console
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

デフォルトでは上記のようにパッケージ間の依存関係が出力されますが、__`-verbose:class`__ オプションを指定すれば、クラスレベルで依存関係を出力することができます（大量に出力されます）。

```console
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
下記のように、外部 `.jar` ファイルに対してクラスパスを通してやれば、依存している `.jar` ファイルの名前も出力されるようになります（下記では、`libs_ext` というディレクトリに Shared library としての `.jar` ファイルが格納されているとします）。

```console
# Windows の場合
$ jdeps -cp libs_ext/*;%ANDROID_HOME%/platforms/android-23/* app.jar

# Linux の場合
$ jdeps -cp libs_ext/*:$ANDROID_HOME/platforms/android-23/* app.jar
```

Android や Java のコアライブラリが提供しているクラスへの依存情報は不要だという場合は、下記のように __`-f`__ オプションで必要のない依存情報をフィルタすることができます。

```console
$ jdeps -f "java.*|android.*|dalvik.*" app.jar
```


APK ファイルのメソッドレベルの依存関係を調べる
----

昔は Android の [dexdeps](https://github.com/android/platform_dalvik/tree/master/tools/dexdeps) というツールでメソッドレベルの依存関係を調べることができたのですが、このツールは multidex 構成の APK には対応していません（APK 内の１つの DEX ファイルしか解析対象にならない）。
Yasuenag さんの `cfa` というツールを使用すれば、`.class` ファイルを解析してメソッドレベルの依存関係を調べることができますので、こちらを利用するのがよいかもしれません。

- [クラスやメソッドの依存関係を調べる ─ まくまく Java ノート](/p/mpqhkpv/)

