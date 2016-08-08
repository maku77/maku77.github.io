---
title: サブモジュールで使用する SDK バージョンを統一する
created: 2016-08-02
---

問題
----

Android の Gradle ビルドスクリプト内では、最低限下記のように `compileSdkVersion` と `buildToolsVersion` を指定しておく必要があります。

#### build.gradle

```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 23
    buildToolsVersion '23.0.2'
}
```

単一のプロジェクト構成であればこの定義は一か所だけで済むのですが、複数のライブラリプロジェクト（サブモジュール）を含んだマルチプロジェクト構成になると、上記のようなバージョン指定を各サブプロジェクト内の `build.gradle` で行わなければいけません。
バージョンの指定が複数ファイルに散らばっていると、使用する SDK のバージョンを更新する場合に複数のファイルを更新しなければいけません。
多くの場合は、バージョンを統一したいと思いますので、このようにバラバラにバージョンを指定する方法は避けたいところです。


解決方法 (1) トップレベルの build.gradle の拡張パラメータで定義する
----

Gradle スクリプトの中で、`ext` ブロックを使用すると、カスタムフィールドを定義することができます。
簡単に言えば、プロジェクトに対して設定できるグローバル変数のようなものです。
例えば、トップレベルの `build.gradle` の中で下記のように定義することができます。

```groovy
ext {
    COMPILE_SDK_VERSION = 23
    BUILD_TOOLS_VERSION = '23.0.2'
}
```

そして、このプロパティを各サブプロジェクトの `build.gradle` から参照するようにします。

```
android {
    compileSdkVersion rootProject.ext.COMPILE_SDK_VERSION
    buildToolsVersion rootProject.ext.BUILD_TOOLS_VERSION
}
```

こうしておけば、トップレベルの `build.gradle` の中で SDK バージョンを更新すれば、すべてのサブプロジェクトに反映されます。


方法 (2) トップレベルの build.gradle の中でサブプロジェクトの設定を行う
----

もう少しがんばれば、トップレベルの `build.gradle` の中から、すべてのサブプロジェクトで使用する SDK バージョンを指定してしまうこともできます。
こうすれば、サブプロジェクトの `build.gradle` の中では SDK バージョンを指定する必要がなくなります。

#### build.gradle（トップレベル）

```groovy
subprojects {
    afterEvaluate { project ->
        if (project.hasProperty('android')) {
            project.android.compileSdkVersion 23
            project.android.buildToolsVersion '23.0.2'
        }
    }
}
```

上記の例では、すべてのサブプロジェクトをループで処理しながら、Android 関連のモジュールであると判断できたときに SDK バージョンを設定するようにしています。
Android 関連のモジュールだと判断する基準としては、そのサブプロジェクトが `android` ブロックを持っているかで判断しています。
そのプロジェクトがプラグインとして `com.android.application` あるいは `com.android.library` を適用していれば、`android` ブロックが自動的に定義されているはずなので、その有無を確認すれば Android 関連のプロジェクトだと判断できるわけです。

注意点としては、先にサブプロジェクトの `build.gradle` が処理されるようにするために、`afterEvaluate` のループでサブプロジェクトをループ処理しているところです。
下記のように、直接 `android` ブロックを参照しようとすると、サブプロジェクト側でまだ `android` が定義されていないというエラーになってしまいます。
Android のプラグインが適用される前に `android` ブロックを参照してしまうからです。

```
// 間違った方法
subprojects {
    android {
        compileSdkVersion 23
        buildToolsVersion '23.0.2'
    }
}
```

