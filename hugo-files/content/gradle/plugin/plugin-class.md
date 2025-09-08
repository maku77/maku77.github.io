---
title: "Gradle プラグインを作成する (1) Plugin クラスの基本"
url: "p/negnqwf/"
date: "2016-08-16"
tags: ["gradle"]
aliases: ["/gradle/plugin/plugin-class.html"]
---

Gradle プラグイン作成の初歩
----

独自の Gradle プラグインを作成すると、下記のように各プロジェクトでプラグインを適用するだけで、そのプラグインが提供しているタスクを使えるようになります。

```groovy
apply plugin: MyPlugin
```

プラグインの実体は `org.gradle.api.Plugin` インタフェースを実装したクラスです。
上記のようにプラグインを適用すると、**`Plugin#apply()`** メソッドが呼び出されるため、例えば、その中でタスクを動的に追加するといった実装を行います。

下記の例では、`MyPlugin` というプラグイン実装を行っており、プラグインを適用したプロジェクトに対して動的に `hello` というタスクを追加しています。
単純化のためにプラグインの実装と、それ使用するコード (`apply plugin: MyPlugin`) を同じビルドスクリプト内に記述しています。


{{< code lang="groovy" title="build.gradle" >}}
// 独自プラグインの適用
apply plugin: MyPlugin

// 独自プラグイン MyPlugin の実装
class MyPlugin implements Plugin<Project> {
    // プラグインを適用した時に呼び出される
    void apply(Project project) {
        println 'MyPlugin has been applied!'

        // プラグインを適用したプロジェクトに hello タスクを追加
        project.task('hello') << {
            println 'Hello MyPlugin!'
        }
    }
}
{{< /code >}}

プラグインによって自動的に追加された `hello` タスクを実行してみます。

{{< code lang="console" title="実行例" >}}
$ gradle -q hello
MyPlugin has been applied!
Hello MyPlugin!
{{< /code >}}


Gradle プラグインの実装を別ディレクトリで行う (buildSrc)
----

Gradle には、**`buildSrc`** というディレクトリ以下に配置されたソースコード（から生成されたクラス）に対して、自動的にクラスパスを通してくれるという機能があります。
これを利用して、プラグインの実装クラスを **`buildSrc`** に配置すれば、プロジェクトのメインとなるビルドスクリプトと、プラグインのコードを分けて配置することができます。

**`buildSrc`** ディレクトリを使用する場合のディレクトリ構成は下記のようになります。

```
Sample
 +-- build.gradle
 +-- buildSrc/src/main/groovy/com/sample/MyPlugin.groovy
```

メインのビルドスクリプトは、プラグインの実装が消えた分だけ非常にシンプルになります。

{{< code lang="groovy" title="build.gradle" >}}
apply plugin: com.sample.MyPlugin
{{< /code >}}

`buildSrc` ディレクトリでは、通常の Groovy スクリプトとしてプラグインのクラス実装を行います。

{{< code lang="groovy" title="buildSrc/src/main/groovy/com/sample/MyPlugin.groovy" >}}
package com.sample

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('hello') << {
            println 'Hello MyPlugin!'
        }
    }
}
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ gradle -q hello
Hello MyPlugin!
{{< /code >}}


{{< note title="コラム" >}}
`buildSrc` ディレクトリに置かれたソースコードに対しては、内部的には下記のような設定がデフォルトで適用されています。
なので、Groovy スクリプトがビルドできるし、その中で `org.gradle.api` パッケージなどにアクセスできるようになっています。

```groovy
apply plugin: 'groovy'
dependencies {
    compile gradleApi()    // org.gradle.api へアクセスできるようにする
    compile localGroovy()  // Groovy SDK を使えるようにする
}
```
{{< /note >}}
