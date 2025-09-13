---
title: "既存の Gradle タスクにアクションを追加する (doLast, doFirst)"
url: "p/6q9wkjm/"
date: "2015-07-13"
lastmod: "2021-02-03"
tags: ["gradle"]
aliases: ["/gradle/add-action-to-task.html"]
---

Gradle の既存のタスクには、自由にアクションを追加していくことができます。
この仕組みにより、サードパーティ製のタスクに対して、前処理や後処理を追加することができます。
タスク内のアクションは、内部的にアクションリストとして保持されており、__`Task#doLast`__ メソッドや __`Task#doFirst`__ メソッドを使用して、先頭、あるいは末尾にアクションを追加できます。


アクションを末尾に追加する (doLast)
----

下記の例では、既存タスク `hello` のアクションリストの末尾にアクションを追加しています。

{{< code lang="groovy" title="build.gradle" >}}
// タスクの定義
task hello {
    doLast {
        println 'Hello!'
    }
}

// アクションリストの末尾にアクションを追加
hello.doLast { println 'Added to last 1' }
hello.doLast { println 'Added to last 2' }
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ gradle -q hello
Hello!
Added to last 1
Added to last 2
{{< /code >}}


アクションを先頭に追加する (doFirst)
----

下記の例では、既存タスクのアクションリストの先頭にアクションを追加しています。

{{< code lang="groovy" title="build.gradle" >}}
// タスクの定義
task hello {
    doLast {
        println 'Hello!'
    }
}

// アクションリストの先頭にアクションを追加
hello.doFirst { println 'Added to first 1' }
hello.doFirst { println 'Added to first 2' }
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ gradle -q hello
Added to first 2
Added to first 1
Hello!
{{< /code >}}

`doFirst` メソッドを呼び出すたびに、その時点でのアクションリストの先頭にアクションが追加されていくので、最後に追加したアクションが最初に実行されていることに注意してください。

