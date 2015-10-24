---
title: Gradle のテンプレート機能によりファイル内容を書き換える
created: 2015-06-30
layout: gradle
---

Groovy の SimpleTemplateEngine クラスの機能を使用することで、ファイルコピー時にファイル内の文字列を置換することができます。

例えば、入力ファイルとして、下記のようなテンプレートファイルを用意しておきます。

#### input/build.properties
```
version=${version}
buildDate=${buildDate.format("yyyyMMdd'T'HHmmss")}
```

`${string}` という部分が動的に置換される部分です。
そこにどんな値を格納するかは、コピータスクの `expand()` メソッドで指定します。

#### build.gradle
```groovy
task hello(type: Copy) {
    from 'input'
    include 'build.properties'
    into 'output'

    expand([
        version: '1.0.0',
        buildDate: new Date()
    ])
}
```

上記のタスクを実行すると、下記のようなファイルが出力されます。

#### output/build.properties
```
version=1.0.0
buildDate=20150630T224515
```
