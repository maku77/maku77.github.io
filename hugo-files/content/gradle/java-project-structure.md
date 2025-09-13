---
title: "Gradle で Java プロジェクトをビルドするときのディレクトリ構成を変更する (sourceSets, buildDir)"
url: "p/odjj8e7/"
date: "2015-07-08"
lastmod: "2021-02-03"
tags: ["gradle"]
aliases: ["/gradle/java-project-structure.html"]
---

`java` プラグインは、プロジェクトのディレクトリ構成が次のようになっていると想定しています。
これは、Gradle のソースセットの設定（インプットファイルの置き場所の設定）がデフォルトでこのようになっているからです。

- Java ソースコード: __`src/main/java`__ および __`src/test/java`__
- 出力先のディレクトリ: __`build`__

このソースセットの設定を変更するとこで、プロジェクトの都合に合わせたディレクトリ構成に変更できます。
例えば、製品用の Java ソースコードを __`src`__ ディレクトリ、テスト用の Java ソースコードを __`test`__ ディレクトリ、出力先を __`out`__ ディレクトリに変更するには以下のように設定します。

{{< code lang="groovy" title="build.gradle" >}}
apply plugin: 'java'

sourceSets {
    main {
        java {
            srcDirs = ['src']
        }
    }
    test {
        java {
            srcDirs = ['test']
        }
    }
}

buildDir = 'out'
{{< /code >}}

こう書くこともできます。

```groovy
apply plugin: 'java'

sourceSets.main.java.srcDirs = ['src']
sourceSets.test.java.srcDirs = ['test']
buildDir = 'out'
```

この設定により、下記のようなパスに置かれた Java ソースコードがコンパイルされるようになります。

- `src/com/example/Main.java`

正確には、`srcDirs` メソッドは、__ディレクトリを変更するのではなく追加__ します。
なので、もともとの `src/main/java` ディレクトリ以下に置かれている Java ソースコードは、相変わらずコンパイルの対象になります。

ちなみに、出力先ディレクトリ (`buildDir`) を変更した場合、`gradle clean` コマンドでクリーンナップされるディレクトリも変更されます。

