---
title: Gradle で PMD による静的解析を実行する
created: 2015-09-14
---

PMD プラグインの基本
====
Gradle には標準の静的解析プラグインとして、PMD が組み込まれています。

* [Gradle - PMD Plugin](https://docs.gradle.org/current/userguide/pmd_plugin.html)
* [PMD](https://pmd.github.io/)

PMD と似たような静的解析ツールに Checkstyle がありますが、Checkstyle がコンパイル後のクラスコードに対する解析であるのに対し、PMD はコンパイル前のソースコードを解析します。
無駄なコードはコンパイル時に最適化されることが多いため、このような無駄なコードを発見するためには、PMD で解析することが必要になります。
また、本家の PMD はコードクローンの発見などの機能 (CPD: Copy/Paste Detector) も備えていますが、Gradle の PMD プラグインはまだ CPD の直接サポートはされていないようです (2015-09-14)。


PMD の設定
----

PMD による静的解析を実施するには、下記のような感じで **pmd** プラグインを読み込んで設定します。

#### build.gradle
```groovy
apply plugin: 'java'
apply plugin: 'pmd'

repositories {
    mavenCentral()
}

// PMD のコンフィギュレーション
pmd {
    toolVersion '5.3.3'    // 使用する PMD のバージョン
    ignoreFailures = true  // PMD で警告が出てもビルドエラーにしない
    consoleOutput = true   // コンソールにも解析結果を出力
    ruleSets = [  // 適用する PMD ルール（プロジェクトごとに要調整）
        'java-android',
        'java-basic',
        'java-braces',
        'java-clone',
        'java-codesize',
        'java-comments',
        'java-controversial',
        'java-design',
        'java-empty',
        'java-finalizers',
        'java-imports',
        'java-j2ee',
        'java-javabeans',
        'java-junit',
        'java-logging-jakarta-commons',
        'java-logging-java',
        'java-migrating',
        'java-naming',
        'java-optimizations',
        'java-strictexception',
        'java-strings',
        'java-sunsecure',
        'java-typeresolution',
        'java-unnecessary',
        'java-unusedcode'
    ]
}
```

PMD プラグインによって、**pmdMain** や **pmdTest** などのタスクが定義されます。
これらのタスクは、**check** タスクに依存するタスクとして定義されるので、**check** タスクを定義する **java** プラグインも読み込んでおく必要があります。

* **pmdMain** タスク（製品コード src/java/main に対する解析）
* **pmdTest** タスク（テストコード src/java/test に対する解析）

PMD による解析の実行
----
`pmdMain` あるいは `check` タスクを実行することで、PMD による静的解析を実行することができます。

```
$ gradle pmdMain
:pmdMain
11 PMD rule violations were found. See the report at: /home/maku/myapp/build/reports/pmd/main.html

BUILD SUCCESSFUL
```

PMD による解析結果は、下記のように出力されます。

* **build/reports/pmd/main.html** （Web ブラウザで確認できる HTML 形式レポート）
* **build/reports/pmd/main.xml** （Jenkins など CI サーバで統計を取るための XML データ）


マルチプロジェクトで PMD を使用する
====
マルチプロジェクト構成のプロジェクトでは、サブプロジェクト内で共通の PMD 設定を使用できると便利です。
ここでは、共通の PMD 設定を下記のように定義し、すべてのサブプロジェクトに適用してみます。

#### gradle/pmd.gradle
```groovy
apply plugin: 'pmd'

pmd {
    toolVersion '5.3.3'
    ignoreFailures = true
    ruleSets = [
        'java-basic',
        'java-braces',
        'java-clone',
        'java-codesize',
        'java-comments',
        ...
    ]
}
```

そして、ルートプロジェクトのビルドスクリプト内で、下記のようにロードすれば OK です。

#### build.gradle
```groovy
subprojects {
    apply plugin: 'java'
    apply from: "$rootDir/gradle/pmd.gradle"
    repositories {
        mavenCentral()
    }
}
```

