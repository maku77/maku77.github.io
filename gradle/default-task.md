---
title: "Gradle でデフォルトタスクを指定する (defaultTasks)"
date: "2014-06-08"
lastmod: "2021-02-03"
---

`gradle` コマンドではパラメータとしてタスク名を指定するのですが、パラメータを省略した場合に実行する __デフォルトタスク__ を指定しておくこともできます。
下記のサンプルでは、`hello` タスクをデフォルトタスクとして指定しています。

#### build.gradle

```groovy
defaultTasks 'hello'

task hello {
    doLast {
        println 'Hello World'
    }
}
```

#### 実行結果

```sh
$ gradle -q  # gradle -q hello と同じ結果になる
Hello World
```

デフォルトタスクはカンマで区切って、複数指定することができます。
デフォルトタスクを複数指定した場合、指定した順番に実行されます。

#### build.gradle

```groovy
defaultTasks 'hello1', 'hello2'

task hello1 {
    doLast { println 'Hello1' }
}

task hello2 {
    doLast { println 'Hello2' }
}
```

#### 実行結果

```sh
$ gradle -q  # gradle -q hello1 hello2 と同じ結果になる
Hello1
Hello2
```

