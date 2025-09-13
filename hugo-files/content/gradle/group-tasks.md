---
title: "Gradle のタスクに説明を付け、グルーピングする"
url: "p/kgwpdtz/"
date: "2015-07-13"
tags: ["gradle"]
aliases: ["/gradle/group-tasks.html"]
---

Gradle のタスクを定義するときに **`group`** パラメータを指定すると、複数のタスクをグルーピングすることができます。
また、**`description`** パラメータを指定すると、タスクに説明文を設定することができます。
これらの設定を行うことで、**`gradle tasks`** でタスクを一覧表示したときに、グルーピングされたタスクと説明が表示されるようになります。

下記の例では、`hello1` タスクと `hello2` タスクを `MyGroup` にグルーピングし、それぞれのタスクに説明文を設定しています。

{{< code lang="groovy" title="build.gradle" >}}
task hello1(group: 'MyGroup', description: 'Description for hello1.') {
    doLast {
        println 'Hello 1'
    }
}

task hello2(group: 'MyGroup', description: 'Description for hello2.') {
    doLast {
        println 'Hello 2'
    }
}
{{< /code >}}

タスクの Configuration クロージャ内で `Task` オブジェクトのメソッド (`group`, `description`) を呼び出すことでも設定できます。

```groovy
task hello1 {
    group 'MyGroup'
    description 'Description for hello1.'
    doLast {
        println 'Hello 1'
    }
}

task hello2 {
    group 'MyGroup'
    description 'Description for hello2.'
    doLast {
        println 'Hello 2'
    }
}
```

Gradle の `tasks` タスクを実行することで、それぞれのタスクがグルーピングされて表示されることが分かります。

```console
$ gradle -q tasks

...
MyGroup tasks
-------------
hello1 - Description for hello1
hello2 - Description for hello2
...
```

