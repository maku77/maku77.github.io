---
title: Gradle で PMD による静的解析を実行する
created: 2015-09-14
---

PMD プラグインの基本
====

Gradle には標準の静的解析プラグインとして、PMD が組み込まれています。
PMD でソースコードを解析すると、潜在的な不具合や、複雑度が高く将来的に負債になりそうなコードを検出することができます。
よい設計やコーディングができているかが一目瞭然になりますので、すべての Java プロジェクトに採用したいところです。

* [Gradle - PMD Plugin](https://docs.gradle.org/current/userguide/pmd_plugin.html)
* [PMD](https://pmd.github.io/)

PMD と似たような静的解析ツールに FindBugs がありますが、FindBugs がコンパイル後のクラスコードに対する解析であるのに対し、PMD はコンパイル前のソースコードを解析します。
無駄なコードはコンパイルの段階で最適化されて削除されてしまうことがあるため、このような無駄なコードを発見するためには、PMD で解析することが必要になります。
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
        'java-basic',            // good practices which should be followed
        'java-braces',           // regarding the use and placement of braces
        'java-clone',            // questionable usages of the clone() method
        'java-codesize',         // problems related to code size or complexity
        'java-design',           // flag suboptimal code implementations
        'java-empty',            // empty statements of any kind
        'java-finalizers',       // problems that can occur with finalizers
        'java-imports',          // problems that can occur with import statements
        'java-strictexception',  // strict guidelines about throwing and catching exceptions
        'java-strings',          // manipulation of the String, StringBuffer, or StringBuilder instances
        'java-sunsecure',        // check the security guidelines from Sun
        'java-typeresolution',   // rules which resolve java Class files for comparison
        'java-unnecessary',      // find useless or unnecessary code
        'java-unusedcode'        // find unused or ineffective code
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
        'java-basic',     // good practices which everyone should follow
        'java-braces',    // braces rules
        'java-clone',     // questionable usages of the clone() method
        'java-codesize',  // find code size, complexity problems
        'java-design',    // questionable designs
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

PMD ルールセットいろいろ
====

PMD で設定できるルールセットは、他にもいろいろ定義されています。

* 必要に応じて有効にしたいルール
  * **java-android**  （Android related best practice）
  * **java-j2ee**  （J2EE アプリ用）
  * **java-junit**  （JUnit のコード用）
  * **java-logging-jakarta-commons**  （Logger 用）
  * **java-logging-java**  （Logger 用）
* あまり使わなさそうなルール
  * **java-controversial**  （異論のあるルールが多い）
  * **java-naming**  （命名規則が厳しすぎ）
  * **java-optimizations**  （final を強制しすぎ）


PMD のルールを XML ファイルで定義する
====

PMD のルールセットは、XML 形式の別ファイルに定義しておくことができます。
複数のプロジェクトで使用するルールセットを定義する場合は、このように設定ファイルとして作成して共有するのがよいでしょう。
ここでは、プロジェクトのルートディレクトリの `config/pmd-settings.xml` として配置することにします。

* [ルールセットファイルのサンプル (config/pmd-settings.xml)](pmd-settings.xml)

Gradle スクリプトの中では、下記のように `ruleSetFiles` でルールセットの XML ファイルを指定します。

#### gradle/pmd.gradle

```groovy
apply plugin: 'pmd'

pmd {
    toolVersion '5.3.3'
    ignoreFailures = true
    consoleOutput = true

    ruleSetFiles = files("$rootDir/config/pmd-settings.xml")
    ruleSets = []  // To apply only the custom rules
}
```

ポイントは、`ruleSets` を空っぽにしておくことです。
この指定を忘れると、XML ファイルで定義したルールと、デフォルトのルールの両方が有効になってしまいます（少なくとも上記で使用している ver 5.3.3 では）。
XML ファイルの中で、何らかのルールを無効 (`exclude`) にしている場合は、忘れずに `ruleSets` を空にしておかないと、ルールの無効化がうまくいかなかったりします。

