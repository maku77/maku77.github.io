---
title: "Gradle でデフォルトタスクを指定する (defaultTasks)"
url: "p/zfc37zt/"
date: "2014-06-08"
lastmod: "2021-02-03"
tags: ["gradle"]
aliases: ["/gradle/default-task.html"]
---

`gradle` コマンドではパラメータとしてタスク名を指定するのですが、パラメータを省略した場合に実行する __デフォルトタスク__ を指定しておくこともできます。
下記のサンプルでは、`hello` タスクをデフォルトタスクとして指定しています。

{{< code lang="groovy" title="build.gradle" >}}
defaultTasks 'hello'

task hello {
    doLast {
        println 'Hello World'
    }
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ gradle -q  # gradle -q hello と同じ結果になる
Hello World
{{< /code >}}

デフォルトタスクはカンマで区切って、複数指定することができます。
デフォルトタスクを複数指定した場合、指定した順番に実行されます。

{{< code lang="groovy" title="build.gradle" >}}
defaultTasks 'hello1', 'hello2'

task hello1 {
    doLast { println 'Hello1' }
}

task hello2 {
    doLast { println 'Hello2' }
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ gradle -q  # gradle -q hello1 hello2 と同じ結果になる
Hello1
Hello2
{{< /code >}}

