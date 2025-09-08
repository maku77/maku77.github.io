---
title: "Gradle プラグインを作成する (2) JAR へのパッケージング"
url: "p/dyas9zg/"
date: "2016-08-19"
tags: ["gradle"]
aliases: ["/gradle/plugin/packaging.html"]
---

配布可能な Gradle プラグインを作成する
----

[Gradle の独自プラグインを作成](/p/negnqwf/)したら、最終的に JAR ファイルにパッケージングすることで、他のプロジェクトにも配布できるようになります。

配布用の Gradle プラグインをビルドするときにも Gradle を利用することができます。
プラグイン作成用のプロジェクトのディレクトリ構成は下記のような感じになります。
ここでは、**`com.mycompany.greeting`** という ID のプラグインを作成することとします（作成したプラグインは、`apply plugin: 'com.mycompany.greeting'` という形で適用できるようになります）。

```
GreetingPlugin
 +-- build.gradle
 +-- src/main/
      +-- groovy/com/mycompany/greeting/GreetingPlugin.groovy
      +-- resources/META-INF/gradle-plugins/com.mycompany.greeting.properties
```

実装に使用する言語は Java でも構わないのですが、ここでは Groovy を使用しています。
ここからは、それぞれのファイルの内容を見ていきます。

### ビルドスクリプト (build.gradle)

ビルドスクリプトでは、プラグインの実装で Gradle パッケージを参照するための設定と、実装言語として Groovy を使用するための設定を行います。

{{< code lang="groovy" title="build.gradle" >}}
apply plugin: 'groovy'

dependencies {
    compile gradleApi()    // org.gradle パッケージを使用するため
    compile localGroovy()  // Groovy SDK を使用するため
}
{{< /code >}}

### プラグイン本体 (GreetingPlugin.groovy)

この Groovy スクリプトで、Gradle プラグイン本体の実装を行います。
`Plugin` インタフェースを実装することで、このプラグインが適用されたときに実行される処理を定義します（ここでは、適用先のプロジェクトに `hello` タスクを追加しています）。

{{< code lang="groovy" title="src/main/groovy/com/mycompany/greeting/GreetingPlugin.groovy" >}}
package com.mycompany.greeting

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        println 'GreetingPlugin has been applied!'
        project.task('hello') << {
            println 'Hello GreetingPlugin!'
        }
    }
}
{{< /code >}}

### プロパティファイル (com.mycompany.greeting.properties)

このプロパティファイルには、Plugin インタフェースを実装したクラスの名前を指定しておきます。
プロパティファイルの名前は、プラグイン ID に合わせて付ける必要があります。

{{< code title="src/main/resources/META-INF/gradle-plugins/com.mycompany.greeting.properties" >}}
implementation-class=com.mycompany.greeting.GreetingPlugin
{{< /code >}}

このプロパティファイル名から `.properties` 拡張子を除いた部分の名前を `apply plugin` のパラメータとして指定することになります。
一方で、`implementation-class` として指定するクラスの FQDN は、実はこのプラグイン ID とは異なっていても構いません。


独自プラグインのビルド
----

プラグインの実装、および、プロパティファイルの記述が終わったら、下記のように **`assemble`** タスクを実行することでプラグインをビルドすることができます。

```console
$ gradle assemble
```

ビルドに成功すると、`build/libs/GreetingPlugin.jar` が生成されます。
この JAR ファイルを配布することで、他のプロジェクトからも独自プラグインを利用できるようになります。
ファイル名はプラグイン ID に合わせて、`com.mycompany.greeting.jar` としておいた方がよいかもしれませんね。


作成した Gradle プラグインを使用してみる
----

作成した **`com.mycompany.greeting`** プラグインを利用してみます。
ここでは、ローカルディレクトリにプラグインの JAR ファイルをコピーして参照します。
独自プラグインを利用する Sample プロジェクトのディレクトリ構成は、例えば下記のようになります。

```
Sample
 +-- build.gradle
 +-- plugins/GreetingPlugin.jar
```

### ビルドスクリプト (build.gradle)

ビルドスクリプト内には、ローカルに配置したプラグインを参照するための **`dependencies`** コンフィギュレーションブロックを定義します。
プラグインの参照が行えるようになったら、**`apply`** メソッドを使用して、実際にプラグインを適用することができます。

{{< code lang="groovy" title="build.gradle" >}}
// 独自プラグインを参照するための設定
buildscript {
    dependencies {
        classpath files('plugins/GreetingPlugin.jar')
    }
}

// 独自プラグインを実際に適用する
apply plugin: 'com.mycompany.greeting'
{{< /code >}}

### 実行してみる

独自プラグインを `apply` することによって、プロジェクトに `hello` タスクが定義されるので、そのタスクを実行してみます。

```console
$ gradle -q hello
GreetingPlugin has been applied!
Hello GreetingPlugin!
```

うまくいきました！
٩(๑❛ᴗ❛๑)۶ わーぃ

