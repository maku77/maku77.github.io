---
title: "Android Gradleメモ: Android アプリが使用している依存ライブラリをツリー構造で表示する（dependencies/androidDependencies タスク）"
url: "p/kma3bsu/"
date: "2018-07-26"
lastmod: "2022-03-17"
tags: ["android"]
aliases: ["/android/gradle/dependency-tree.html"]
---

Gradle の __`dependencies`__ タスクを実行すると、あるプロジェクトが依存するライブラリ（およびサブモジュール）の一覧を、ツリー構造で表示することができます。
一般的な Android アプリの構成では `app` が最上位のモジュールになるので、`:app:dependencies` というタスクを実行すれば、アプリ全体（APK 全体）のライブラリ依存関係を調べることができます（`:app` 部分を省略するとエラーになるので注意してください）。

```console
$ gradlew -q :app:dependencies
```

デフォルトではすべてのコンフィギュレーションにおける依存関係が表示される（テストビルド時の依存関係なども表示される）ので、大量のテキストが出力されます。
出力対象を絞り込むために、`--configuration` オプションを指定して次のように実行します。

```console
$ gradlew -q :app:dependencies --configuration releaseCompileClasspath > deps-tree-compile.txt
$ gradlew -q :app:dependencies --configuration releaseRuntimeClasspath > deps-tree-runtime.txt
```

コンフィギュレーション名のプレフィックス部分 (上記の場合は `release`) はビルドバリアント名であり、アプリごとに異なることに注意してください。
`prodRelease` であったり、`hogehogeDebug` だったりします。
下記は、出力結果の例です。

{{< code title="deps-tree-runtime.txt" >}}
------------------------------------------------------------
Project :app
------------------------------------------------------------

releaseRuntimeClasspath - Resolved configuration for runtime for variant: release
+--- com.android.support:support-annotations:27.1.1
+--- com.android.support:support-core-utils:27.1.1
|    +--- com.android.support:support-annotations:27.1.1
|    \--- com.android.support:support-compat:27.1.1
|         +--- com.android.support:support-annotations:27.1.1
|         \--- android.arch.lifecycle:runtime:1.1.0
|              +--- android.arch.lifecycle:common:1.1.0
|              \--- android.arch.core:common:1.1.0
+--- project :common:hoge
|    \--- project :common:mylib
|         +--- com.android.support:support-annotations:27.1.1
|         +--- com.fasterxml.jackson.core:jackson-databind:2.4.2
|         |    +--- com.fasterxml.jackson.core:jackson-annotations:2.4.0
|         |    \--- com.fasterxml.jackson.core:jackson-core:2.4.2
|         +--- com.example.lib:property:1.18.0
|         +--- com.example.lib:network:1.0.0
|         \--- com.example.lib:upgrade:1.0.1
+--- project :common:mylib (*)
|
...省略...
|
\--- project :feature:recommend
     +--- com.android.support:support-annotations:27.1.1
     +--- com.android.support:support-core-utils:27.1.1 (*)
     +--- project :common:hoge (*)
     \--- project :common:mylib

(*) - dependencies omitted (listed previously)
{{< /code >}}

依存関係は上記のように再帰的に検索されてツリー形式で表示されます。
サブモジュール名の末尾に `(*)` が付いているものは、それよりも前の行ですでに依存情報が出力されているため、出力が省略されたことを示しています。

`dependencies` タスクの代わりに、__`androidDependencies`__ タスクを使用すると、ライブラリの依存関係が一階層でシンプルに出力されます。

```
$ gradlew -q :app:androidDependencies > deps-all.txt
```

上記のように、ファイルに出力しておいて、対象のビルドバリアント名 (`release` など）のセクションを検索し、その下の `xxxCompileClasspath` や `xxxRuntimeClasspath` の内容を確認するとよいでしょう。

{{< code lang="text" title="deps-all.txt" >}}
release
releaseCompileClasspath - Dependencies for compilation
+--- androidx.activity:activity-ktx:1.2.3@aar
+--- androidx.activity:activity:1.2.4@aar
+--- androidx.annotation:annotation-experimental:1.1.0@aar
+--- androidx.annotation:annotation:1.3.0@jar
+--- androidx.appcompat:appcompat-resources:1.4.1@aar
+--- androidx.appcompat:appcompat:1.4.1@aar
...

releaseRuntimeClasspath - Dependencies for runtime/packaging
+--- androidx.activity:activity-ktx:1.2.3@aar
+--- androidx.activity:activity:1.2.4@aar
+--- androidx.annotation:annotation-experimental:1.1.0@aar
+--- androidx.annotation:annotation:1.3.0@jar
+--- androidx.appcompat:appcompat-resources:1.4.1@aar
+--- androidx.appcompat:appcompat:1.4.1@aar
...
{{< /code >}}

