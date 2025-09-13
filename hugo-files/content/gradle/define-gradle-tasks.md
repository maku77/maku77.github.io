---
title: "Gradle のタスクを定義する"
url: "p/a88r2ua/"
date: "2014-06-17"
tags: ["gradle"]
aliases: ["/gradle/define-gradle-tasks.html"]
---

`gradle` コマンドを実行すると、カレントディレクトリにある **`build.gradle`** ビルドスクリプト（レシピ）を読み込み、そこに定義されているタスクを実行します。
下記は、`hello` というタスクを定義する例で、Excecution フェーズで `Hello World` と表示するように指定しています。

{{< code lang="groovy" title="build.gradle" >}}
task hello {
    doLast {
        println 'Hello World'
    }
}
{{< /code >}}

具体的には、`hello` という名前の [Task オブジェクト](https://docs.gradle.org/current/javadoc/org/gradle/api/Task.html) を作成し、`Task#doLast()` メソッドでタスクのアクションリストの末尾にアクションを追加する、ということをしています。
このように定義したタスクを実行するには、`gradle` コマンドの引数でタスク名を指定します。

```console
$ gradle hello
:hello
Hello World
BUILD SUCCESSFUL
Total time: 2.527 secs
```

コロンから始まる **`:hello`** という行が、hello タスクを実行していることを示しています。
`gradle` コマンドを実行するときに、**`-q`** あるいは **`--quiet`** オプションを指定すると、エラー以外のログ出力を抑制できます。

```console
$ gradle -q hello
Hello World
```

{{% note title="（コラム）leftShift は deprecated" %}}
Gradle 4.x までは、タスクの Configuration クロージャ内に `doLast` アクションを定義する代わりに、次のようなショートカット記法が使えました。

```groovy
task hello << {
    println 'Hello World'
}
```

Gradle 5.x 以降はこのような記述方法は deprecated になっているため、次のようなエラーが出ます。

```
Could not find method leftShift()
```

しょうがないので `doLast` を使いましょう。


```groovy
task hello {
    doLast { println 'Hello World' }
}
```
{{% /note %}}

