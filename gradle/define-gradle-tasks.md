---
title: Gradle のタスクを定義する
created: 2014-06-17
---

`gradle` コマンドは、デフォルトで **build.gradle** ビルドスクリプト（レシピ）を読み込むので、そのファイルにビルド内容を記述していきます。
下記は、`hello` というタスクだけを定義する例です。

#### build.gradle
```groovy
task hello << {
    println 'Hello World'
}
```

タスクが定義されていれば、そのタスク名を指定して実行することができます。

```groovy
$ gradle hello
:hello
Hello World
BUILD SUCCESSFUL
Total time: 2.527 secs
```

コロンから始まる `:hello` という行が、hello タスクを実行していることを示しています。
`gradle` コマンドを実行するときに、`-q or --quiet` オプションを指定すると、エラー以外のログ出力を抑制できます。

```groovy
$ gradle -q hello
Hello World
```

