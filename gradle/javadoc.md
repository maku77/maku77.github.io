---
title: "Gradle で Javadoc API Document を出力する"
date: "2015-08-03"
---

javadoc タスクを使えるようにする
----

java プラグインをロードすると、自動的に __javadoc__ タスクが定義されます。

#### build.gradle

```groovy
apply plugin: 'java'
```

下記のように実行すると、`src/main/java` 以下の Java ソースコードに記述された Javadoc コメントを元に、__`build/docs/javadocs`__ に API ドキュメントが生成されます。

```sh
$ gradle javadoc
```


javadoc タスクをカスタマイズする
----

javadoc タスクをプロジェクトに合わせてカスタマイズしたい場合は、下記のように `Javadoc` 型の新しいタスクを定義します。

#### build.gradle

```groovy
apply plugin: 'java'

task myJavadoc(type: Javadoc) {
    group = 'Sample'
    description = 'Generates API documents.'
    source = sourceSets.main.allJava
}
```

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

