---
title: "Gradle のタスクに説明を付け、グルーピングする"
date: "2015-07-13"
---

プロジェクト内で使用できるタスクの一覧は `gradle tasks` で確認できますが、このときにタスクが適切にグルーピングされていて、タスクごとに簡単な説明が記述されているとわかりやすくなります。

タスクを定義するときに、`group` パラメータ、および `description` パラメータを指定することでこれらの設定を行うことができます。
下記の例では、`hello1` タスクと `hello2` タスクを `MyGroup` にグルーピングし、それぞれのタスクに説明文を設定しています。

#### build.gradle

```groovy
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
```

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

```sh
$ gradle -q tasks

...
MyGroup tasks
-------------
hello1 - Description for hello1
hello2 - Description for hello2
...
```

