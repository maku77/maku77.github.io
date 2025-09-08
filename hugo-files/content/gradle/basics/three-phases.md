---
title: "Gradle 実行時の 3 つのフェーズ (initialization, configuration, execution)"
url: "p/o7yqmcx/"
date: "2014-06-08"
tags: ["gradle"]
aliases: ["/gradle/basics/three-phases.html"]
---

3 つのフェーズ
----

Gradle によるビルドを実行するとき、内部では下記のような 3 つのフェーズに分けてビルドが実行されていきます。

1. Initialization フェーズ
2. Configuration フェーズ
3. Execution フェーズ

### (1) Initialization フェーズ

マルチプロジェクトにおけるプロジェクトの依存関係が解決され、プロジェクトのビルド順序が決められるフェーズです。
サブプロジェクトの構成は、トップレベルのディレクトリに置かれる **`settings.gradle`** ファイルで定義されます（Gradle の内部では、このファイルを基に [Settings](https://docs.gradle.org/current/dsl/org.gradle.api.initialization.Settings.html) オブジェクトが生成されています）。

{{< code lang="groovy" title="settings.gradle" >}}
include 'subproject1'
include 'subproject2'
{{< /code >}}

また、プロジェクトごとに Gradle 内部で [Project](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html) インスタンスが生成されています（マルチプロジェクトなビルド構成であれば、複数の `Project` インスタンスが生成されます）。
プロジェクトの依存関係は、各プロジェクトの **`dependencies`** ブロック内で定義されます。

```groovy
dependencies {
    compile project(':subproject')
    ...
}
```

### (2) Configuration フェーズ

すべてのプロジェクトの **`build.gradle`** が処理され、すべてのタスクの依存関係を認識するフェーズです。
タスクの依存関係は、下記のように **`dependsOn`** プロパティで指定されます。

```groovy
task task2(dependsOn: task1) << {
    println 'Hello task2'
}
```

上記の例では依存タスクを `task1` というオブジェクトで指定していますが、`'task1'` のように、文字列でタスク名を指定することもできます。
文字列でタスク名を指定することにより、その時点でまだ定義されていないタスクを指定することができます。
Configuration フェーズでは、すべてのタスク定義を走査するため、最終的にはすべての依存関係が問題なく解決されます（逆に、Configuration フェーズ終了時に未解決なタスクがあるとエラーになります）。

Gradle の処理系内部では、依存グラフ (dependency graph) が生成されています。

### (3) Execution フェーズ

実際にどのタスクを実行すべきかを判断し、タスクの実行を行うフェーズです。
`gradle` コマンドに渡したタスク名や、実行時のカレントディレクトリによってどのタスクを実行するかが変化します。


振る舞いを調べてみる
----

次のようなビルドファイルを作成すると、3 つのフェーズがどのような順番で実行されていくかを確認することができます。

{{< code title="settings.gradle" >}}
println 'Initialization'
{{< /code >}}

{{< code lang="groovy" title="build.gradle" >}}
println 'Configuration 1'

task hello1 {
    println 'Configuration 2'
    doLast {
        println 'Execution (hello1)'
    }
    println 'Configuration 3'
}

println 'Configuration 4'

task hello2 {
    println 'Configuration 5'
    doLast {
        println 'Execution (hello2)'
    }
    println 'Configuration 6'
}

println 'Configuration 7'
{{< /code >}}

`hello2` タスクを実行してみます。

{{< code lang="console" title="実行結果" >}}
$ gradle -q hello2

Initialization
Configuration 1
Configuration 2
Configuration 3
Configuration 4
Configuration 5
Configuration 6
Configuration 7
Execution (hello2)
{{< /code >}}

Configuration フェーズの実行は、単純に上から実行されていくことが分かります。

