---
title: Gradle のタスクを定義する
created: 2014-06-17
---

`gradle` コマンドを実行すると、カレントディレクトリにある **build.gradle** ビルドスクリプト（レシピ）を読み込み、そこに定義されているタスクを実行します。
下記は、`hello` というタスクを定義する例です。

#### build.gradle
```groovy
task hello << {
    println 'Hello World'
}
```

上記の `<<` を使った定義は、下記のように `doLast` のアクションを定義する方法のショートカットです。通常は、より短く記述できる `<<` の方を使えばよいでしょう。

```groovy
task hello {
    doLast {
        println 'Hello World'
    }
}
```

タスクが定義されていれば、`gradle` コマンドのパラメータでタスク名を指定して実行することができます。

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

