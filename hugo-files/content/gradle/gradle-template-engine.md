---
title: "Gradle のテンプレート機能を使ってファイルを生成する"
url: "p/ijynhet/"
date: "2015-06-30"
tags: ["gradle"]
aliases: ["/gradle/gradle-template-engine.html"]
---

Groovy の SimpleTemplateEngine クラスの機能を使用することで、ファイルコピー時にファイル内の文字列を置換することができます。
例えば、入力ファイルとして、下記のようなテンプレートファイルを用意しておきます。

{{< code title="input/build.properties" >}}
version=${version}
buildDate=${buildDate.format("yyyyMMdd'T'HHmmss")}
{{< /code >}}

**`${string}`** という部分が動的に置換される部分です。
そこにどんな値を格納するかは、`Copy` 型タスクの **`expand()`** メソッドで指定します。

{{< code lang="groovy" title="build.gradle" >}}
task hello(type: Copy) {
    from 'input'
    include 'build.properties'
    into 'output'

    expand([
        version: '1.0.0',
        buildDate: new Date()
    ])
}
{{< /code >}}

上記のタスクを実行すると、下記のようなファイルが出力されます。

{{< code title="output/build.properties" >}}
version=1.0.0
buildDate=20150630T224515
{{< /code >}}

