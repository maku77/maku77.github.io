---
title: "すべてのサブモジュール（ライブラリ）の依存関係をツリー構造で表示する"
date: "2018-07-26"
---

Gradle の **dependencies** タスクを実行すると、あるプロジェクトが依存するサブモジュール（ライブラリ）の一覧を、ツリー構造で表示することができます。
Android アプリケーションでは、通常は `app` プロジェクトが最上位のプロジェクトとなりますので、`:app:dependencies` というタスクを実行すれば、アプリ全体（APK全体）のサブモジュールに関する依存関係を表示することができます。

デフォルトではすべてのコンフィギュレーションにおける依存関係が表示される（テストビルド時の依存関係なども表示される）ので、大量のテキストが出力されます。
出力対象を絞り込むには、`--configuration` オプションを使用し、下記のように実行すればよいでしょう。

~~~
$ gradlew :app:dependencies --configuration debugRuntimeClasspath -q

------------------------------------------------------------
Project :app
------------------------------------------------------------

ebugRuntimeClasspath - Resolved configuration for runtime for variant: debug
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
~~~

`dependencies` タスクを実行すると、依存関係が上記のように再帰的に検索されてツリー表示されます。
ただし、サブモジュール名の末尾に `(*)` が付いているものは、それよりも前の行で依存関係が出力されているものなので、それ以下の依存関係の出力は省略されます。

