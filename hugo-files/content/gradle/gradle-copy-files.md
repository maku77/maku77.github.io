---
title: "Gradle でファイルをコピー、リネームするためのタスクを作成する (type: Copy)"
url: "p/q6ducqz/"
date: "2015-06-30"
tags: ["gradle"]
aliases:
  - "/gradle/gradle-copy-files.html"
  - "/gradle/gradle-rename-files.html"
---

Gradle のビルドスクリプトで **`Copy`** 型のタスクを定義すると、ファイルのコピーや移動を簡単に行うことができます。


ファイルコピーの基本
----

次の `hello` タスクは、ディレクトリ `from/dir` 以下のファイルを再帰的にコピーします。
`from/dir` 以下のディレクトリ階層は保たれます。
コピー先のディレクトリ `to/dir` が存在しない場合は、自動的に作成されます。

```groovy
task hello(type: Copy) {
    from 'from/dir'
    into 'to/dir'
}
```


コピーする対象を絞り込む
----

`Copy` タスクにおいて、**`include`** や **`exclude`** でファイル名のパターンを指定することで、コピーするファイルを絞り込むことができます。

{{< code lang="groovy" title="拡張子が .txt のファイルをすべてコピー" >}}
task hello(type: Copy) {
    from 'from/dir'
    into 'to/dir'
    include '**/*.txt'
}
{{< /code >}}

{{< code lang="groovy" title="拡張子が .bk ではないファイルをすべてコピー" >}}
task hello(type: Copy) {
    from 'from/dir'
    into 'to/dir'
    exclude '**/*.bk'
}
{{< /code >}}


複数のディレクトリからファイルをコピーする
----

コピー元のディレクトリを複数指定して、一つのディレクトリにコピーすることもできます。
下記の例では、`from1` ディレクトリと `from2` ディレクトリ内のファイルを `to` ディレクトリにコピーしています。

```groovy
task hello(type: Copy) {
    from 'from1'
    from 'from2'
    into 'to'
}
```

ただし、この方法だと、複数のコピー元に同じファイル名のファイルがあると、ファイルが上書きコピーされてしまいます（ツリー構造が異なれば大丈夫です）。
下記のようにすると、コピー先にディレクトリを作って、その中にファイルをコピーすることができます。
`from` のパラメータを括弧で囲むことに注意してください。

```groovy
task hello(type: Copy) {
    from('from1') {
        into 'f1'
    }
    from('from2') {
        into 'f2'
    }
    into 'to'
}
```

このようにすると、`from1` ディレクトリ内のファイルは `to/f1` に、`from2` ディレクトリ内のファイルは `to/f2` に、それぞれコピーされるようになります。


ファイルをリネームする
----

`Copy` 型のタスクでファイルをコピーする際に、**`rename`** メソッドを使用するとファイル名を変更することができます。

{{< code lang="groovy" title="例: 拡張子 .txt を .html にリネームしてコピー" >}}
task hello(type: Copy) {
    from 'from'
    into 'to'
    rename(/(.+)\.txt$/, '$1.html')
}
{{< /code >}}

拡張子として `.txt` を持たないファイルに関しては、元のファイル名のままコピーされます。
上記では `rename` メソッド呼び出しのパラメータとして置換パターンを指定していますが、クロージャの形で置換処理を記述することもできます。

```groovy
task hello(type: Copy) {
    from 'from'
    into 'to'
    rename { it - ~/\.txt$/ + '.html' }
}
```

上記の例では、それぞれのファイル名に対して、末尾から `.txt` を削除し、`.html` を付加するという処理を行っています。
末尾に `.txt` がないファイルに対しても、`.html` を付加してしまうことに注意してください。

