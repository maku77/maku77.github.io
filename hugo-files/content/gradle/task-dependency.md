---
title: "Gradle でタスクの依存関係を表現する"
url: "p/sifxuig/"
date: "2015-07-09"
lastmod: "2021-02-03"
tags: ["gradle"]
aliases: ["/gradle/task-dependency.html"]
---

タスク間に依存関係を設定する
----

`org.gradle.api.Task` オブジェクトの [dependsOn メソッド](https://docs.gradle.org/current/javadoc/org/gradle/api/Task.html#dependsOn(java.lang.Object...)) を使用することで、タスク間に依存関係を持たせることができます。

### 方法1）タスク定義時の引数で依存定義する

タスク定義時の __`dependsOn`__ 引数（厳密にはマップキー）でタスク間の依存関係を設定することができます。
次の例では、`hello1` タスクに依存する `hello2` タスクを定義しています。

{{< code lang="groovy" title="build.gradle" >}}
task hello1 {
    doLast { println 'Hello 1' }
}

task hello2(dependsOn: hello1) {
    doLast { println 'Hello 2' }
}
{{< /code >}}

`hello2` タスクを実行するときに、先に `hello1` タスクが実行されるようになります。

```console
$ gradle -q hello2
Hello 1
Hello 2
```

### 方法2）Configure クロージャ内で依存定義する

タスク定義時の Configure クロージャ内で __`dependsOn`__ メソッドを呼び出して依存関係を指定することができます。

```groovy
task hello1 {
    doLast {
        println 'Hello 1'
    }
}

task hello2 {
    dependsOn hello1
    doLast {
        println 'Hello 2'
    }
}
```

### 方法3）タスク定義後に依存を追加する

すでに定義済みのタスク（`Task` オブジェクト）の __`dependsOn`__ メソッドを呼び出すことで、後付けで依存関係を追加することができます。


```groovy
task hello1 {
    doLast { println 'Hello 1' }
}

task hello2 {
    doLast { println 'Hello 2' }
}

hello2.dependsOn hello1
```


まだ定義されていないタスクを依存関係に追加する
----

```groovy
hello2.dependsOn hello1
```

のように依存関係を追加するときに、`hello1` というオブジェクト名で参照できているのは、`hello1` タスクがすでに定義済みで、`Project` オブジェクトの `hello1` プロパティとして参照可能になっているからです（Configuration フェーズでは上から順番に処理されていくので、`hello1` タスクの定義が上に記述されている必要があります）。

まだ定義されていないタスクを依存先に設定するには、その __タスク名を文字列で指定__ します。
次の例では、`hello1` タスクの依存先として、まだ定義されていない `hello2` タスクを指定しています。

```groovy
task hello1 {
    dependsOn 'hello2'
    doLast { println 'Hello 1' }
}

task hello2 {
    doLast { println 'Hello 2' }
}
```


複数のタスクに依存するタスクを定義する
----

### 複数タスクへの依存

複数のタスクに依存するタスクを定義することもできます。
下記の例では、`hello1` タスクと `hello2` タスクの両方に依存する `hello3` タスクを定義しています。

{{< code lang="groovy" title="build.gradle" >}}
task hello1 { doLast { println 'Hello 1' }}
task hello2 { doLast { println 'Hello 2' }}
task hello3 { doLast { println 'Hello 3' }}

hello3.dependsOn hello1, hello2
{{< /code >}}

`Task#dependsOn` メソッドは、依存関係を __追加する__ ためのメソッドなので、次のように別々に依存関係を追加しても同様です。

```groovy
hello3.dependsOn hello1
hello3.dependsOn hello2
```

また、次のように同じ依存関係を何度追加しても、依存するタスクが呼び出されるのは 1 回だけです。

```groovy
hello3.dependsOn hello1
hello3.dependsOn hello1
hello3.dependsOn hello1
```

タスク定義時の `dependsOn` パラメータでも複数の依存関係を指定することができます。

```groovy
task hello1 { doLast { println 'Hello 1' }}
task hello2 { doLast { println 'Hello 2' }}
task hello3(dependsOn: [hello1, hello2]) {
    doLast { println 'Hello 3' }
}
```

上記のように依存関係を定義することで、`hello3` タスクの実行前に `hello1` タスクと `hello2` タスクが実行されることが保証されます。

{{< code lang="console" title="実行結果" >}}
$ gradle hello3
Hello 1
Hello 2
Hello 3
{{< /code >}}

### タスクの実行順序の制御

上記のように `Task#dependsOn` メソッドで、2 つ以上のタスク（`hello1` と `hello2`）に依存する `hello3` タスクを定義することができました。
しかし、この指定だけでは __`hello1` タスクと `hello2` タスクの実行順序までは保証されない__ ということに注意してください。
こういった緩い制約になっていることで、Groovy のタスクの多くは並列実行することが可能になっています。

仮に、必ず `hello1` → `hello2` → `hello3` の順番で実行しないといけないのであれば、次のように `hello1` と `hello2` の依存関係を定義するという方法があります。

```groovy
task hello1 { doLast { println 'Hello 1' }}
task hello2(dependsOn: hello1) { doLast { println 'Hello 2' }}
task hello3(dependsOn: hello2) { doLast { println 'Hello 3' }}
```

ただし、こうすると、`hello2` タスクの実行時にも必ず `hello1` タスクが実行されることになってしまいます。

```console
$ gradle -q hello2
Hello 1
Hello 2

$ gradle -q hello3
Hello 1
Hello 2
Hello 3
```

`hello3` タスクの実行にのみ、`hello1` → `hello2` → `hello3` の順番で実行するには、次のように __`Task#mustRunAfter`__ メソッドを使います。

```groovy
task hello1 { doLast { println 'Hello 1' }}
task hello2 { doLast { println 'Hello 2' }}
task hello3 { doLast { println 'Hello 3' }}

hello2.mustRunAfter hello1
hello3.dependsOn hello1, hello2
```

これで、`hello2` タスクの単独実行時には `hello1` タスクを実行しないで済みます。

```console
$ gradle -q hello2
Hello 2

$ gradle -q hello3
Hello 1
Hello 2
Hello 3
```

