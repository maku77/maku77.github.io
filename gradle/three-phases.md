---
title: Gradle 実行時の 3 つのフェーズ
created: 2014-06-08
layout: gradle
---

Gradle によるビルドを実行するとき、内部では下記のような 3 つのフェーズに分けてビルドが実行されていきます。

(1) Initialization フェーズ
---

マルチプロジェクトにおけるプロジェクトの依存関係が解決され、プロジェクトのビルド順序が決められるフェーズです。
サブプロジェクトの構成は settings.gradle ファイルで定義されます。

#### settings.gradle
```groovy
include 'subproject1'
include 'subproject2'
```

プロジェクトの依存関係は、各プロジェクトの `dependencies` ブロック内で定義されます。

```groovy
dependencies {
    compile project(':subproject')
    ...
}
```

(2) Configuration フェーズ
---

すべてのタスクとそれらの依存関係を認識し、どのタスクを実行すべきかを判断するフェーズです。
タスクの依存関係は `dependsOn` プロパティで指定されます。

```groovy
task task2(dependsOn: task1) << {
    println 'Hello task2'
}
```

タスク名を文字列として指定することによって、まだ定義されていないタスクを指定することもできます。
それは、このフェーズですべてのタスクを走査するため、このフェーズが終了するまでにはそのタスクが存在することを判別できるからです。

(3) Execution フェーズ
---

実際にタスクを実行するフェーズです。

