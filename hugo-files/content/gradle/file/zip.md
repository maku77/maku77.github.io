---
title: "Gradle でディレクトリ内のファイルを ZIP 圧縮する (type: Zip)"
url: "p/p6bu2pa/"
date: "2016-09-09"
tags: ["gradle"]
aliases: ["/gradle/file/zip.html"]
---

Gradle でディレクトリ内のファイルを ZIP 圧縮するには、次のように **`Zip`** 型のタスクを定義します。

```groovy
task createArchive(type: Zip) {
    from 'docs/'
    archiveName 'docs.zip'
}
```

このように定義した `createArchive` タスクを実行すると、`docs` ディレクトリ内のファイルをまとめた `docs.zip` アーカイブファイルを作成できます。

```console
$ gradlew createArchive
```

