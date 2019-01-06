---
title: "Gradle で Java プロジェクトをビルドする"
date: "2014-06-08"
---

Java プラグインを適用すると、Java のプロジェクトをビルドするためのタスクが定義されます。

* [Java Plugin の説明（英語）](https://docs.gradle.org/current/userguide/java_plugin.html)
* [Java Plugin の説明（日本語）](http://gradle.monochromeroad.com/docs/userguide/java_plugin.html)

#### build.gradle
```groovy
apply plugin: 'java'
```

Java プロジェクトのディレクトリ構成
===
Java プラグインはデフォルトで、以下のようなディレクトリ構造でソースコードが格納されていることを期待して動作します。

```
src/main/java              製品用の Java ソースコード
src/main/resources         製品用のリソースファイル
src/test/java              ユニットテスト用の Java ソースコード
src/test/resources         ユニットテスト用のリソースファイル
```

例えば、`com.example.Main` クラスのソースコードは下記のようなパスに格納します（build.gradle ファイルがあるディレクトリからの相対パスです）。

### src/main/java/com/example/Main.java
```java
package com.example;

public class Main {
    public static void main(String... args) {
        System.out.println("Hello");
    }
}
```

java プラグインを使ったビルドとプログラムの実行
===
java プラグインが提供する **build** タスクを実行することにより、Java プロジェクトのビルドを行うことができます。

```sh
$ gradle build
...
BUILD SUCCESSFUL
```

ビルドが成功すると、`build` ディレクトリに生成物が出力されます。
例えば、クラスファイルは **build/classes/main** や **build/classes/test** ディレクトリ以下に出力されます。このディレクトリにクラスパスを通せば、Java プログラムを実行することができます。

```sh
$ java -cp build/classes/main com.example.Main
Hello World
```

また、`build/libs` 以下には、クラスファイルをアーカイブした JAR ファイルが格納されます。
JAR ファイルの名前は、デフォルトで **<プロジェクトのディレクトリ名>.jar** となります。
例えば、ディレクトリ名が `sample` の場合は、下記のように実行できることになります。

```sh
$ java -cp build/libs/sample.jar com.example.Main
Hello World
```

ちなみに、出力先された `build` ディレクトリは、java プラグインが提供する **clean** タスクで削除することができます。

```sh
$ gradle clean
```

