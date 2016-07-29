---
title: Gradle 実行時の 3 つのフェーズ
created: 2014-06-08
---

Gradle によるビルドを実行するとき、内部では下記のような 3 つのフェーズに分けてビルドが実行されていきます。

1. Initialization フェーズ
2. Configuration フェーズ
3. Execution フェーズ

(1) Initialization フェーズ
---

マルチプロジェクトにおけるプロジェクトの依存関係が解決され、プロジェクトのビルド順序が決められるフェーズです。
サブプロジェクトの構成は `settings.gradle` ファイルで定義されます。
Gradle の処理系内部では、`Project` インスタンスが生成されています（マルチプロジェクトなビルド構成であれば、複数の `Project` インスタンスが生成されます）。

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

すべてのプロジェクトの `build.gradle` が処理され、すべてのタスクの依存関係を認識するフェーズです。
タスクの依存関係は、下記のように `dependsOn` プロパティで指定されます。

```groovy
task task2(dependsOn: task1) << {
    println 'Hello task2'
}
```

上記の例では依存タスクを `task1` というオブジェクトで指定していますが、`'task1'` のように、文字列でタスク名を指定することもできます。
文字列でタスク名を指定することにより、その時点でまだ定義されていないタスクを指定することができます。
Configuration フェーズでは、すべてのタスク定義を走査するため、最終的にはすべての依存関係が問題なく解決されます（逆に、Configuration フェーズ終了時に未解決なタスクがあるとエラーになります）。

Gradle の処理系内部では、依存グラフ (dependency graph) が生成されています。

(3) Execution フェーズ
---

実際にどのタスクを実行すべきかを判断し、タスクの実行を行うフェーズです。
`gradle` コマンドに渡したタスク名や、実行時のカレントディレクトによってどのタスクを実行するかが変化します。

