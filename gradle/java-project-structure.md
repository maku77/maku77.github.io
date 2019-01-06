---
title: "Gradle で Java プロジェクトをビルドするときのディレクトリ構成を変更する"
date: "2015-07-08"
---

java プラグインは、Java ソースコードが **src/main/java** や **src/test/java** に置かれていることを前提としています。
また、ビルド後の出力先のディレクトリは **build** になっています。
これは、Gradle のソースセットの設定（インプットファイルの置き場所の設定）がデフォルトでそのようになっているからです。

このソースセットの設定を変更するとこで、プロジェクトの都合に合わせたディレクトリ構成に変更できます。
例えば、製品用の Java ソースコードを **src** ディレクトリ、テスト用の Java ソースコードを **test** ディレクトリ、出力先を **out** ディレクトリに変更するには以下のように設定します。

#### build.gradle
```groovy
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
```

こう書くこともできます。

```groovy
apply plugin: 'java'

sourceSets.main.java.srcDirs = ['src']
sourceSets.test.java.srcDirs = ['test']
buildDir = 'out'
```

この設定により、下記のようなパスに置かれた Java ソースコードがコンパイルされるようになります。

* src/com/example/Main.java

正確には、`srcDirs` メソッドは、ディレクトリを変更するのではなく、追加します。
なので、もともとの `src/main/java` ディレクトリ以下に置かれている Java ソースコードは、相変わらずコンパイルの対象になります。

ちなみに、出力先ディレクトリ (`buildDir`) を変更しておくと、`gradle clean` コマンドでクリーンナップされるディレクトリも変更されます。

