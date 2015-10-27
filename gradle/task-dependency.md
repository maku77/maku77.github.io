---
title: Gradle でタスクの依存関係を表現する
created: 2015-07-09
---

タスク定義後に依存関係を設定する
===

`org.gradle.api.Task` オブジェクトの **dependsOn** メソッドを使用することで、タスク間に依存関係を持たせることができます。

* [Task#dependsOn(Object... paths)](https://docs.gradle.org/current/javadoc/org/gradle/api/Task.html#dependsOn(java.lang.Object...))

下記の例では、task1 に依存する task2 を定義しています。

#### build.gradle
```groovy
task task1 << {
    println 'Hello task1'
}

task task2 << {
    println 'Hello task2'
}

task2.dependsOn task1
```

task2 を実行しようとすると、先に task1 が実行されます。

```sh
$ gradle -q task2
Hello task1
Hello task2
```

タスク定義時のパラメータで依存関係を定義する
===

タスク定義時に **dependsOn** パラメータを指定することで、タスク間の依存関係を設定することができます。
下記の例では、task1 に依存する task2 を定義しています。

#### build.gradle
```groovy
task task1 << {
    println 'Hello task1'
}

task task2(dependsOn: task1) << {
    println 'Hello task2'
}
```

複数のタスクに依存するタスクを定義する
===

複数のタスクに依存するタスクを定義することもできます。
下記の例では、task1 と task2 の両方に依存する task3 を定義しています。

#### build.gradle
```groovy
task task1 << { println 'Hello task1' }
task task2 << { println 'Hello task2' }
task task3 << { println 'Hello task3' }

task3.dependsOn task1, task2
```

タスクの定義時に依存関係を指定することも可能です。

```groovy
task task1 << { println 'Hello task1' }
task task2 << { println 'Hello task2' }

task task3(dependsOn: [task1, task2]) << {
    println 'Hello task3'
}
```

このように定義することで、task3 の実行前に task1 と task2 が実行されることが保証されるようになります。
ただし、**task1 と task2 の実行順序は保証されない**ことに注意してください。
こういった緩い制約になっていることで、Groovy のタスクの多くは並列実行することが可能になっています。

仮に、必ず task1 → task2 → task3 の順番で実行しないといけないのであれば、次のように task1 と task2 の依存関係を定義しておけば済みます。

```groovy
task task1 << { println 'Hello task1' }
task task2(dependsOn: task1) << { println 'Hello task2' }
task task3(dependsOn: task2) << { println 'Hello task3' }
```

