---
title: "Gradle で Checkstyle による静的解析を実行する"
date: "2015-08-10"
---

Checkstyle プラグインの基本
====
Gradle には標準の静的解析プラグインとして、Checkstyle、PMD、CodeNarc、FindBugs、JDepend などが組み込まれています。

* [Gradle - Checkstyle Plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)

Checkstyle による静的解析を実施するには、下記のように **checkstyle** プラグインを読み込みます。


#### build.gradle
```groovy
apply plugin: 'java'
apply plugin: 'checkstyle'

repositories {
    mavenCentral()
}

checkstyle {
    toolVersion '6.7'
}
```

checkstyle プラグインによって、**checkstyleMain** や **checkstyleTest** などのタスクが定義されます。
これらのタスクは、**check** タスクに依存するタスクとして定義されるので、**check** タスクを定義する **java** プラグインも読み込んでおく必要があります。
また、Checkstyle はバージョンアップごとに微妙に互換性がなくなるので、上記のように `checkstyle` コンフィギュレーションで、使用する Checkstyle のバージョンを指定しておくのがよいでしょう。

Checkstyle プラグインは、デフォルトで Checkstyle の設定ファイルとして **${projectDir}/config/checkstyle/checkstyle.xml** を読み込むので、まずはここにプロジェクトで使用する Checkstyle の設定を置いておく必要があります。

デフォルトの構成に従うと、こんな感じのディレクトリ構成になります。

```
+-- build.gradle
+-- config/
|   +-- checkstyle/
|       +-- checkstyle.xml （Checkstyle の設定ファイル）
+-- src/
    +-- main/
        +-- java/
            +-- com/
                +-- example/
                    +-- Main.java （静的解析対象のソースコード）
```

`checkstyleMain` あるいは `check` タスクを実行することで、Checkstyle による静的解析を実行することができます。

```
$ gradle -q check
[ant:checkstyle] D:\sample\src\main\java\com\example\Main.java:0: warning: Missing package-info.java file.
...
```

Checkstyle による解析結果は、**build\reports\checkstyle\main.xml** に出力されています。


Checkstyle の設定を変更する
====

Checkstyle 設定ファイルを指定する
----
Checkstyle プラグインは、デフォルトで **config/checkstyle/checkstyle.xml** を読み込みますが、下記のようにして使用する設定ファイルを変更することができます。

```groovy
checkstyle {
    configFile file("$rootDir/config/my-checkstyle.xml")
    ...
}
```

Checkstyle による警告が出た場合もビルドを継続する
----
```groovy
checkstyle {
    ignoreFailures true  // continue build if there are warnings
    ...
}
```

マルチプロジェクトで Checkstyle を使用する
====
マルチプロジェクト構成のプロジェクトでは、サブプロジェクト内で共通の Checkstyle 設定を使用できると便利です。
ここでは、共通の Checkstyle 設定を下記のように定義し、すべてのサブプロジェクトに適用してみます。

#### gradle/checkstyle.gradle
```groovy
apply plugin: 'checkstyle'

checkstyle {
    toolVersion '6.7'
    configFile file("$rootDir/config/checkstyle.xml")
    ignoreFailures true
}
```

そして、ルートプロジェクトのビルドスクリプト内で、下記のようにロードすれば OK です。

#### build.gradle
```groovy
subprojects {
    apply plugin: 'java'
    apply from: "$rootDir/gradle/checkstyle.gradle"
    repositories {
        mavenCentral()
    }
}
```

