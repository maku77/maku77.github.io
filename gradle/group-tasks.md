---
title: "Gradle のタスクに説明を付け、グルーピングする"
date: "2015-07-13"
---

プロジェクト内で使用できるタスクの一覧は `gradle tasks` で確認できますが、このときにタスクが適切にグルーピングされていて、タスクごとに簡単な説明が記述されているとわかりやすくなります。

タスクを定義するときに、`group` パラメータ、および `description` パラメータを指定することでこれらの設定を行うことができます。
下記の例では、task1 と task2 を Group 1 にグルーピングし、それぞれのタスクに説明文を設定しています。

#### build.gradle
```groovy
task task1(group: 'Group 1', description: 'This is a description 1') << {
    println 'Hello task1'
}

task task2(group: 'Group 1', description: 'This is a description 2') << {
    println 'Hello task2'
}
```

タスクの Configuration クロージャ内で各 setter メソッド (`group`, `description`) を呼び出すことでも設定できます。

```groovy
task task1 {
    group = 'Group 1'
    description = 'This is a description 1'
    doLast {
        println 'Hello task1'
    }
}

task task2 {
    group = 'Group 1'
    description = 'This is a description 2'
    doLast {
        println 'Hello task2'
    }
}
```

Gradle の `tasks` タスクを実行することで、それぞれのタスクがグルーピングされて表示されることが分かります。

```sh
$ gradle -q tasks

...
Group 1 tasks
-------------
task1 - This is a description 1
task2 - This is a description 2
...
```

