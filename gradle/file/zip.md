---
title: Gradle でディレクトリ内のファイルを ZIP 圧縮する
date: "2016-09-09"
---

Gradle のタスクとして、ZIP アーカイブを作成するサンプルです。

```groovy
task createArchive(type: Zip) {
    from 'docs/'
    archiveName 'docs.zip'
}
```

上記で定義した `createArchive` タスクを実行すると、`docs` ディレクトリ内のファイルをまとめた `docs.zip` アーカイブファイルを作成します。

```
$ gradlew createArchive
```

