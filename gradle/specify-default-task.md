---
title: Gradle でデフォルトタスクを指定する (defaultTasks)
created: 2014-06-08
---

`gradle` コマンドではパラメータとしてタスク名を指定する必要がありますが、パラメータを省略した場合に実行するデフォルトタスクを指定しておくこともできます。
下記のサンプルでは、`hello` タスクをデフォルトタスクとして指定しています。

#### build.gradle
```groovy
defaultTasks 'hello'

task hello << {
    println 'Hello World'
}
```

#### 実行結果

```sh
$ gradle -q    （gradle -q hello と同様）
Hello World
```

デフォルトタスクはカンマで区切って、複数指定することができます。

#### build.gradle
```groovy
defaultTasks 'hello1', 'hello2'

task hello1 << {
    println 'Hello1'
}

task hello2 << {
    println 'Hello2'
}
```

#### 実行結果

```sh
$ gradle -q    （gradle -q hello1 hello2 と同様）
Hello1
Hello2
```

