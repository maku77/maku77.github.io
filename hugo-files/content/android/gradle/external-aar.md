---
title: "Android Gradleメモ: 外部から提供された AAR ファイルを利用する"
url: "p/9giv5yz/"
date: "2016-08-04"
tags: ["android"]
aliases: ["/android/gradle/external-aar.html"]
---

Android のライブラリプロジェクトをビルドすると、`build/output/aar` ディレクトリに AAR ファイルが作成されます。
この AAR ファイルを配布すれば、複数のアプリケーションからライブラリとして再利用することができます。

配布された AAR ファイルを利用してプロジェクトをビルドするためには、`build.gradle` に下記のように記述しておきます。
ここでは、`aars` ディレクトリに配布された `mylib.aar` ファイルを格納していると仮定します。

{{< code lang="groovy" title="build.gradle" >}}
repositories {
    flatDir {
        dir 'aars'
    }
}

dependencies {
    compile(name: 'mylib', ext: 'aar')
}
{{< /code >}}

