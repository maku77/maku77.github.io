---
title: "Gradle で Javadoc API ドキュメントを出力する"
url: "p/sj6sc4d/"
date: "2015-08-03"
tags: ["gradle"]
aliases: ["/gradle/javadoc.html"]
---

javadoc タスクを使えるようにする
----

Gradle で `java` プラグインをロードすると、自動的に **`javadoc`** タスクが定義されます。

{{< code lang="groovy" title="build.gradle" >}}
apply plugin: 'java'
{{< /code >}}

下記のように実行すると、`src/main/java` 以下の Java ソースコードに記述された Javadoc コメントを元に、**`build/docs/javadocs`** に API ドキュメントが生成されます。

```console
$ gradle javadoc
```


javadoc タスクをカスタマイズする
----

`javadoc` タスクをプロジェクトに合わせてカスタマイズしたい場合は、下記のように **`Javadoc`** 型の新しいタスクを定義します。

{{< code lang="groovy" title="build.gradle" >}}
apply plugin: 'java'

task myJavadoc(type: Javadoc) {
    group = 'Sample'
    description = 'Generates API documents.'
    source = sourceSets.main.allJava
}
{{< /code >}}

タスク定義時に `type: Javadoc` と指定することで、Configuration クロージャの中で参照されるオブジェクト（Closure delegate）が、`Task` オブジェクトから `Javadoc` オブジェクトに変わります。
Configuration クロージャ内で `Javadoc` オブジェクトのメソッドを呼び出すことで、タスクをカスタマイズできるようになっています。
`source` プロパティの指定は必須であることに注意してください（指定しないと何も生成されません）。

Javadoc 用に新しいタスクを定義するのではなく、既存の `javadoc` タスクを上書きしてしまいたい場合は、下記のように `overwrite` オプションを指定します。

```groovy
task javadoc(type: Javadoc, overwrite: true) {
    group = 'Sample'
    description = 'Generates API documents.'
    source = sourceSets.main.allJava
}
```

`Javadoc` タスクオブジェクトに他にどのようなプロパティがあるかは、下記の DSL ドキュメントで確認できます。

- [Javadoc task](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.javadoc.Javadoc.html)

