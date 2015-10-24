---
title: 既存の Gradle タスクにアクションを追加する
created: 2015-07-13
layout: gradle
---

Gradle の既存のタスクには、自由にアクションを追加していくことができます。
この仕組みにより、サードパーティ製のタスクに対して、前処理や後処理を追加することができます。
タスク内のアクションは、内部的にアクションリストとして保持されており、`doFirst` メソッドや `doLast` メソッドを使用して、先頭、あるいは末尾にアクションを追加できます。

アクションを末尾に追加する
===

下記の例では、既存タスク `hello` のアクションリストの末尾にアクションを追加しています。
`doLast` メソッドを使うこともできますし、そのショートカットである `<<` を使うこともできます。

#### build.gradle
```groovy
// タスクの定義
task hello << {
    println 'Hello!'
}

// アクションリストの末尾にアクションを追加
hello << { println 'Added to last 1' }
hello.doLast { println 'Added to last 2' }
```

#### 実行結果
```sh
$ gradle -q hello
Hello!
Added to last 1
Added to last 2
```

アクションを先頭に追加する
===

下記の例では、既存タスクのアクションリストの先頭にアクションを追加しています。
末尾に追加するときと異なり、`<<` のようなショートカットは用意されていないので、`doFirst` メソッドを使用する必要があります。

```groovy
// タスクの定義
task hello << {
    println 'Hello!'
}

// アクションリストの先頭にアクションを追加
hello.doFirst { println 'Added to first 1' }
hello.doFirst { println 'Added to first 2' }
```

#### 実行結果
```sh
$ gradle -q hello
Added to first 2
Added to first 1
Hello!
```

`doFirst` メソッドを呼び出すたびに、その時点でのアクションリストの先頭にアクションが追加されていくので、最後に追加したアクションが最初に実行されていることに注意してください。

