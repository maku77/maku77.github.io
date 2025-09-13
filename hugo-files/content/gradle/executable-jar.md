---
title: "Gradle で実行可能な JAR ファイルを作成する"
url: "p/5iacmi7/"
date: "2015-07-10"
tags: ["gradle"]
aliases: ["/gradle/executable-jar.html"]
---

Gradle の `java` プラグインを使用して、実行可能な JAR ファイルを作成してみます。
ここでは、下記のような簡単な Hello World アプリケーションをビルドします。

{{< code lang="java" title="src/main/java/com/example/Main.java" >}}
package com.example;

public class Main {
    public static void main(String... args) {
        System.out.println("Hello");
    }
}
{{< /code >}}

JAR ファイルを特別なパラメータなしで実行できるようにするには、JAR ファイルにアーカイブする `Manifest` ファイルでエントリポイントとなる `Main` クラスを指定しておく必要があります。
ビルドスクリプト内で下記のように記述しておくと、`com.example.Main` をエントリポイントとするような `Manifest` ファイルを生成してくれるようになります。

{{< code lang="groovy" title="build.gradle" >}}
apply plugin: 'java'

version = 1.0

jar {
    manifest {
        attributes 'Main-Class': 'com.example.Main'
    }
}
{{< /code >}}

ビルドします。

```console
$ gradle build
...
BUILD SUCCESSFUL

Total time: 1.063 secs
```

作成された JAR ファイルは、下記のように実行できるようになっているはずです。

```console
$ java -jar build/libs/sample-1.0.jar
Hello
```

上記のファイル名からもわかるように、プロジェクトのプロパティ (`version`) に設定したバージョン情報が、自動的に JAR ファイル名のプレフィックスとして設定されます。

