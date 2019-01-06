---
title: "Gradle で Javadoc API Document を出力する"
date: "2015-08-03"
---

java プラグインをロードすると、自動的に **javadoc** タスクが定義されます。

#### build.gradle
```groovy
apply plugin: 'java'
```

下記のように実行すると、`src/main/java` 以下の Java ソースコードに記述された Javadoc コメントを元に、**build/docs/javadocs** に API ドキュメントが生成されます。

```sh
$ gradle javadoc
```

javadoc タスクをカスタマイズする
====

javadoc タスクをプロジェクトに合わせてカスタマイズしたい場合は、下記のように新しくタスクを定義します。

#### build.gradle
```groovy
apply plugin: 'java'

task myJavadoc(type: Javadoc) {
    group = 'Sample'
    description = 'Generates API documents.'
    source = sourceSets.main.allJava
}
```

'javadoc' という名前のタスクを上書きしてしまいたい場合は、下記のように `overwrite` オプションを指定します。

```groovy
task javadoc(type: Javadoc, overwrite: true) {
    group = 'Sample'
    description = 'Generates API documents.'
    source = sourceSets.main.allJava
}
```

`source` プロパティの指定は必須であることに注意してください（指定しないと何も生成されません）。
他にどのようなプロパティがあるかは、下記の DSL ドキュメントで参照できます。

* [Javadoc task](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.javadoc.Javadoc.html)

