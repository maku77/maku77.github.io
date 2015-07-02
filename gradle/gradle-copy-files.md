---
title: Gradle でファイルをコピーするためのタスクを作成する
created: 2015-06-30
---

ファイルコピーの基本
----

次のタスクは、ディレクトリ `from/dir` 以下のファイルを再帰的にコピーします。
`from/dir` 以下のディレクトリ階層は保たれます。
コピー先のディレクトリ `to/dir` が存在しない場合は、自動的に作成されます。

```java
task hello(type: Copy) {
    from 'from/dir'
    into 'to/dir'
}
```

コピーする対象を絞り込む
----
Copy タスクにおいて、include や exclude でファイル名のパターンを指定することで、コピーするファイルを絞り込むことができます。

#### 拡張子が .txt のファイルをすべてコピー

```java
task hello(type: Copy) {
    from 'from/dir'
    into 'to/dir'
    include '**/*.txt'
}
```

#### 拡張子が .bk ではないファイルをすべてコピー

```java
task hello(type: Copy) {
    from 'from/dir'
    into 'to/dir'
    exclude '**/*.bk'
}
```

複数のディレクトリからファイルをコピーする
----

コピー元のディレクトリを複数指定して、一つのディレクトリにコピーすることもできます。
下記の例では、`from1` ディレクトリと `from2` ディレクトリ内のファイルを `to` ディレクトリにコピーしています。

```java
task hello(type: Copy) {
    from 'from1'
    from 'from2'
    into 'to'
}
```

ただし、この方法だと、複数のコピー元に同じファイル名のファイルがあると、ファイルが上書きコピーされてしまいます（ツリー構造がまったく異なればセーフですが）。
下記のようにすると、コピー先にディレクトリを作って、その中にファイルをコピーすることができます。
`from` のパラメータを括弧で囲むことに注意してください。

```java
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

