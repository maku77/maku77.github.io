---
title: Android アプリ用の Gradle スクリプトの基本
created: 2016-08-01
---

Android プラグインのダウンロード設定
----

Android アプリケーションを Gradle でビルドするときは、Android 用の Gradle プラグインが提供するタスクを利用してビルドします。
まず、Android プラグインを Maven リポジトリからダウンロードできるようにするために、`buildscript` ブロックを設定しておく必要があります。
この設定は、プロジェクトルートの `build.gradle` に記述しておきます。

#### build.gradle（ルートのビルドスクリプト）

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.1.0'
    }
}
```

`repositories` ブロックの `jcenter()` というのは、Android プラグインを提供している Maven リポジトリを指定しています。
そこから Android 用の Gradle プラグインを取得することを、`dependencies` ブロック内で指定しています。

Android アプリのディレクトリ構造
----

Android プロジェクトのディレクトリ構成は、およそ下記のような感じになります（Android 独自というよりは、Maven の慣例によるディレクトリ構成です）。

```
MyApp/
 +-- build.gradle（ルートのビルドスクリプト）
 +-- settings.gradle（ビルドすべきサブプロジェクトの一覧）
 +-- app/
 |    +-- build（ビルド後の成果物）
 |    +-- build.gradle（サブプロジェクトのビルドスクリプト）
 |    +-- src/
 |         +-- main/
 |              +-- java/
 +-- library1/
 |    +-- build.gradle（サブプロジェクトのビルドスクリプト）
 +-- library2/
      +-- build.gradle（サブプロジェクトのビルドスクリプト）
```

この例では、３つのサブプロジェクト (app, library1, library2) によって全体のビルドが構成されています。
サブプロジェクトの一覧は、`settings.gradle` の中で下記のように定義されています。

#### settings.gradle

```groovy
include ':app'
include ':library1'
include ':library2'
```

ビルドスクリプト (`build.gradle`) はルートに１つと、各サブプロジェクトに１つずつ置かれます。
Maven リポジトリの指定 (`jcenter()`) などは、ルートの `build.gradle` で行いましたが、それによって参照できるようにした Android プラグインを実際に使用するのは、各サブプロジェクトの `build.gradle` になります。

各サブプロジェクト内のビルドスクリプトの記述
----

各サブプロジェクトにおけるビルドスクリプトの中で、下記のように記述しておくことで、Android プラグインが提供する機能を使用できるようになります。

#### 各サブプロジェクトの build.gradle でいずれかを指定

```groovy
apply plugin: 'com.android.application'  // 通常のアプリプロジェクトの場合
apply plugin: 'com.android.library'      // ライブラリプロジェクトの場合
```

Android プラグインを適用すると、`android` ブロックが定義されるので、そのブロックの中で Android プロジェクト用のビルド設定を行っていきます。
最低限、下記のように、ビルドに使用する SDK バージョンなどを設定しておく必要があります。

```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.2"
}
```

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

