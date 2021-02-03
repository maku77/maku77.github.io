---
title: "Gradle のタスクを定義する"
date: "2014-06-17"
---

`gradle` コマンドを実行すると、カレントディレクトリにある __build.gradle__ ビルドスクリプト（レシピ）を読み込み、そこに定義されているタスクを実行します。

下記は、`hello` というタスクを定義する例で、Excecution フェーズで `Hello World` と表示するように指定しています。

#### build.gradle

```groovy
task hello {
    doLast {
        println 'Hello World'
    }
}
```

具体的には、`hello` という名前の [Task オブジェクト](https://docs.gradle.org/current/javadoc/org/gradle/api/Task.html) を作成し、`Task#doLast()` メソッドでタスクのアクションリストの末尾にアクションを追加する、ということをしています。
このように定義したタスクを実行するには、`gradle` コマンドの引数でタスク名を指定します。

```groovy
$ gradle hello
:hello
Hello World
BUILD SUCCESSFUL
Total time: 2.527 secs
```

コロンから始まる `:hello` という行が、hello タスクを実行していることを示しています。
`gradle` コマンドを実行するときに、`-q` or `--quiet` オプションを指定すると、エラー以外のログ出力を抑制できます。

```groovy
$ gradle -q hello
Hello World
```


（コラム）leftShift は deprecated
----

Gradle 4.x までは、タスクの Configuration クロージャ内に `doLast` アクションを定義する代わりに、次のようなショートカット記法ができました。

```groovy
task hello << {
    println 'Hello World'
}
```

Gradle 5.x 以降は deprecated になっているため、このようなタスク定義をすると、次のようなエラーが出ます。

```
Could not find method leftShift()
```

しょうがないので `doLast` を使いましょう。


```groovy
task hello {
    doLast { println 'Hello World' }
}
```
