---
title: "Gradle でプロパティファイル (.properties) を読み込む"
url: "p/quhymp7/"
date: "2015-11-12"
tags: ["gradle"]
aliases: ["/gradle/property-file.html"]
---

独自のプロパティファイルを参照する
----

Gradle ビルドスクリプトで **`Properties`** クラスを利用すると、任意の `.properties` ファイルを簡単に読み込むことができます。
例えば、次のような `sample.properties` をプロジェクトのルートディレクトに置いて、これを読み込んでみます。

{{< code lang="properties" title="sample.properties" >}}
key1=value1
key2=value2
{{< /code >}}

下記ビルドスクリプトの `loadProperties` メソッドでは、指定した `.properties` ファイルを読み込んでオブジェクトとして返しています。

{{< code lang="groovy" title="build.gradle" >}}
/**
 * Reads properties from the specified .properties file.
 */
def loadProperties(filename) {
    def props = new Properties()
    file(filename).withInputStream {
        props.load(it)
    }
    return props
}

task hello << {
    def props = loadProperties("$rootDir/sample.properties")
    println "props.key1 = ${props.key1}"
    println "props.key2 = ${props.key2}"
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ gradle -q hello
props.key1 = value1
props.key2 = value2
{{< /code >}}

読み込んだプロパティ設定を、プロジェクト全体から参照できるようにしたいときは、次のようにプロジェクトの **`ext`** プロパティに設定してしまえば OK です。

```groovy
def loadProperties(filename) {
    def props = new Properties()
    file(filename).withInputStream {
        props.load(it)
    }
    ext.props = props    // ★プロジェクト全体から見えるようにする
}

task hello << {
    loadProperties("$rootDir/sample.properties")
    println "props.key1 = ${props.key1}"
    println "props.key2 = ${props.key2}"
}
```


gradle.properties を参照する
----

プロジェクトのディレクトリに配置した **`gradle.properties`** に記述した設定内容は、自動的に `project` オブジェクトのプロパティとして参照できるようになります。

{{< code lang="properties" title="gradle.properties" >}}
key1=value1
key2=value2
{{< /code >}}

{{< code lang="groovy" title="build.gradle" >}}
task hello << {
    println "project.key1 = ${project.key1}"  // project は省略可能
    println "project.key2 = ${project.key2}"
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ gradle -q hello
project.key1 = value1
project.key2 = value2
{{< /code >}}

ただし、ユーザディレクトリに配置した `~/.gradle/gradle.properties` ファイルに、同じキーのプロパティが定義されていると、そちらの値が優先されるので注意しましょう。

{{< code lang="properties" title="~/.gradle/gradle.properties" >}}
key2=value2 (overridden)
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ gradle -q hello
project.key1 = value1
project.key2 = value2 (overridden)
{{< /code >}}

いずれにしても、`gradle.properties` はビルド環境に関する設定を記述するためのファイルです。
プロジェクト特有のリソースや設定値などをビルドスクリプトの外で管理したい場合は、独自のプロパティファイルとして作成してしまう方が安全です。

{{% note %}}
`gradle.properties` ファイル内で定義するプロパティのキー名に、ドットやハイフンが含まれている場合は、`${xxx.yyy.zzz}` の形ではなく、`getProperty('xxx.yyy.zzz')` のように参照する必要があります。
{{% /note %}}

参考
----
* [The Build Environment - Gradle User Guide](https://docs.gradle.org/current/userguide/build_environment.html)

