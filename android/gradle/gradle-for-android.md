---
title: "Android アプリ用の Gradle スクリプトの基本"
date: "2016-08-01"
---

Android アプリケーションのビルドには Gradle が採用されています（2016 年現在、Ant によるビルドはサポート外となりました）。
Google が Gradle 用の Android プラグインを提供しており、このプラグインを各 Android プロジェクトの Gradle スクリプトから利用することで Android モジュールのビルドを行えるようになります。

Android アプリのディレクトリ構造
----

Android プロジェクトのディレクトリ構成は、およそ下記のような感じになります。
Android Studio によって新規作成する場合も、このようなディレクトリ構成で自動生成されます（正確には `library1` や `library2` といったサブプロジェクトのディレクトリは生成されませんが、ここではマルチプロジェクト構成の説明のために追加しています）。

```
MyApp/
 +-- build.gradle（トップレベルのビルドスクリプト）
 +-- settings.gradle（ビルドすべきサブプロジェクトの一覧）
 +-- app/ （Application サブプロジェクト）
 |    +-- build.gradle（サブプロジェクトのビルドスクリプト）
 |    +-- build/（ビルド後の成果物）
 |    +-- src/
 |         +-- main/（main ソースセット）
 |              +-- AndroidManifest.xml
 |              +-- java/
 |              +-- res/
 +-- library1/（Library サブプロジェクト）
 |    +-- build.gradle（サブプロジェクトのビルドスクリプト）
 +-- library2/（Library サブプロジェクト）
      +-- build.gradle（サブプロジェクトのビルドスクリプト）
```

この例では、３つのサブプロジェクト (app, library1, library2) によって全体のビルドが構成されています。


トップレベルに配置するビルドスクリプト (build.gradle / settings.gradle)
----

トップレベルのディレクトリには、`build.gradle` と `settings.gradle` を配置します。

### build.gradle（トップレベル）

トップレベルの `build.gradle` ファイルでは、Gradle によるビルドプロセス自体の環境設定や、サブプロジェクト全体に適用する設定などを行います。

```groovy
// ビルドプロセス自体の設定
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.1.0'
    }
}
```

まず、`buildscript` ブロックでは、ビルド環境の設定を行います。
ここでは、Maven リポジトリとしてメジャーな JCenter を使用することや、ビルド用にどのようなプラグインが必要であるかを指定します。
上記の例では、Android アプリケーションをビルドするために必要な Android プラグイン (for Gradle 2.1.0) を使用することを示しています。

```groovy
// すべてのサブプロジェクトに適用する設定
allprojects {
    repositories {
        jcenter()
    }
}
```

さらに、上記のように `allprojects` ブロックを利用すると、すべてのプロジェクト（マルチプロジェクト構成の場合）に対して共通で適用する設定を記述しておくことができます。
ここまでは、Gradle にもともと備わっている機能を利用しているだけで、Android プラグインが提供している機能は一切利用していないことに注意してください。
Android プラグインが提供する機能は、各モジュール（サブプロジェクト）内の `build.gradle` で利用します（詳細は後述）。


### settings.gradle（トップレベル）

Gradle はマルチプロジェクト構成のビルドをサポートしています。
全体がどのようなサブプロジェクトから構成されているかを Gradle に教えてあげるために記述するのが `settings.gradle` ファイルです。

```groovy
include ':app'
include ':library1'
include ':library2'
```

上記のように記述しておくことで、Gradle はサブプロジェクトのビルドスクリプトとして `app/build.gradle`、`library1/build.gradle`、`library2/build.gradle` を読み込む必要があることを認識します。


サブプロジェクト内に配置するビルドスクリプト (build.gradle)
----

ビルドスクリプト (`build.gradle`) はトップレベルにも１つ配置されていますが、各サブプロジェクト（モジュール）にも１つずつ配置する必要があります。

```
MyApp
 +-- build.gradle（トップレベル）
 +-- app/
 |    +-- build.gradle（サブプロジェクト用）
 +-- library1/
 |    +-- build.gradle（サブプロジェクト用）
 +-- library2/
      +-- build.gradle（サブプロジェクト用）
```

トップレベルの `build.gradle` では Gradle のビルド環境自体の設定を行っていましたが、各サブプロジェクト内の `build.gradle` では、Android の各モジュール（Application プロジェクトや Library プロジェクト）ごとのビルド設定を行います。


### build.gradle（モジュールレベル）

各サブプロジェクトにおけるビルドスクリプトの中では、Android プラグインを適用し、その機能を利用して Android モジュール用のビルド設定を行っていきます。
下記のように記述すると Android プラグインを適用できるのですが、Application プロジェクトの場合と Library プロジェクトの場合では指定方法が異なっているため、どちらか一方だけを指定する必要があります。

```groovy
apply plugin: 'com.android.application'  // 通常のアプリプロジェクトの場合
apply plugin: 'com.android.library'      // ライブラリプロジェクトの場合
```

上記のいずれかの方法で Android プラグインを適用すると、内部で自動的に `android` ブロックが定義されるので、そのブロックの中で Android プロジェクト用のビルド設定を行っていきます。
最低限、下記のように、ビルドに使用する SDK バージョンなどを設定しておく必要があります。

```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.2"
}
```

compileSdkVersion
: Android モジュールをコンパイルするときに使用する API バージョンを指定します。

buildToolsVersion
: 使用する Android SDK（ビルドツール）のバージョンを指定します。ここで指定するバージョンの Android SDK を、SDK Manager を使ってあらかじめインストールしておく必要があります。通常は最新バージョンを指定すればよいですが、このバージョンを変更することによって Android Lint による警告方法が変わったり、Android ツールチェインの処理に微妙な変化が出てきます。

`android` ブロックには、これまで `AndroidManifest.xml` で指定していたアプリの基本情報を記述しておくこともできます。
`build.gradle` 側で記述することで、より柔軟な構成が可能になっています。

```groovy
...
android {
    ...
    defaultConfig {
       applicationId "com.example.myapp"
       minSdkVersion 22
       targetSdkVersion 23
       versionCode 1
       versionName "1.0.0"
       testApplicationId "com.example.myapp.test"
       testInstrumentationRunner "android.test.InstrumentationTestRunner"
    }
}
```

applicationId
: Google Play などで配信するときの識別子となるアプリケーションのパッケージ名 (ID) です。
`AndroidManifest.xml` ではなく、`build.gradle` でパッケージ名を指定することによって、ビルド時に動的にパッケージ名を決定することができます。
例えば、アプリケーションの無料バージョンと有料バージョンを Google Play で分けて配信したいということがあるかもしれません。
そのような場合は、それぞれのアプリケーション (APK) に異なるパッケージ名を割り当てる必要があるのですが、Android プラグインの product flavor という仕組みを利用することで、異なるパッケージ名の APK（`com.example.myapp.free` など）を簡単に生成し分けることができます。
一方で、Java のソースコード（R.java など）からは、相変わらず `AndroidManifest.xml` の方で指定した `package` 名が参照されることに注意してください（実装コードの中では Java のパッケージ名がころころ変わっては困るからです）。

minSdkVersion / targetSdkVersion
: `AndroidManifest.xml` の `<uses-sdk>` 要素で指定していたものと同様です。
`minSdkVersion` は、最低限この API Level をサポートした Android デバイス上で動作することを意味します。
`targetSdkVersion` は、実際のテストをこの API Level で動作させて確認したことを意味します。

これらのパラメータを `build.gradle` 側で指定しておくことにより、`AndroidManifest.xml` 側で指定されている値よりも優先的に使用されるようになります（正確にはビルド時に内部的にその値に上書きされます）。`build.gradle` 側に指定されている項目については、`AndroidManifest.xml` 側に記述しておく必要はありませんが、パッケージ名だけは相変わらず `AndroidManifest.xml` の `<manifest>` 要素の `package` 属性で記述しておく必要があります。`applicationId` はあくまで APK 自体の識別情報として扱われるもので、実装時の Java パッケージ名とは関係がありません。

サブプロジェクト内の `build.gradle` の `dependencies` ブロックでは、そのモジュールをビルドするのに必要な依存モジュールを列挙します。
`dependencies` ブロックは、もともと Gradle が提供している機能のため、`android` ブロックの外で定義することに注意してください。

```groovy
dependencies {
    compile 'com.android.support:multidex:1.0.1'  // Maven リポジトリからの取得
    compile project(':library1')
    compile project(':library2')
    compile fileTree(dir: "$rootDir/lib", include: ['*.jar'])  // Static JAR
    provided fileTree(dir: "$rootDir/libs_ext", include: 'shared.jar')  // Shared JAR
}
```

ここには、依存する Library プロジェクト（別のサブプロジェクト）や、外部から提供されている JAR ファイルへの依存情報を記述します。
ローカルに配置した JAR ファイルへの依存を指定することもできますし、Maven リポジトリで提供されている JAR ファイルへの依存を指定することもできます。
APK ファイルに組み込むモジュールは `compile` のパラメータで指定しますが、ビルド時に参照するだけでよい JAR ファイルは `provided` のパラメータで指定します（デバイス側で提供される Shared JAR ライブラリを使用する場合など）。


Android プラグインによって提供されるタスク
----

Android プラグインは、APK をビルドするためのタスクや、テストを実行するためのタスクを提供しています。
例えば下記のようなタスクを利用することができます。

assembleDebug
: デバッグビルドによって APK ファイルを作成

installDebug
: 物理デバイスやエミュレータに APK をインストール

check
: 物理デバイスやエミュレータでテストを実行

build
: assemble と check タスクの両方を実行

例えば、APK をデバッグビルドする場合は下記のように実行することになります。

```
$ gradle assembleDebug
```

ビルド結果の APK は、`app/build/outputs/apk/app-debug.apk` として出力されます。

