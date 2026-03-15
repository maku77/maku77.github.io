---
title: "Javaメモ: jar コマンドで実行可能な JAR ファイルを作成する"
url: "p/24t3vaq/"
date: "2016-11-02"
tags: ["java"]
aliases: ["/java/jar/executable-jar.html"]
---

実行可能 JAR ファイルをビルドする
----

ここでは、下記のような `com.example.myapp.Main` クラスの `main` メソッドを実行できるような JAR ファイルを作成することを考えます。

{{< code lang="java" title="src/com/example/myapp/Main.java" >}}
package com.example.myapp;

public class Main {
    public static void main(String args...) {
        System.out.println("Hello");
    }
}
{{< /code >}}

コンパイルします。ここでは、`build` ディレクトリ以下にクラスファイルが出力されるように指定しています。

```console
$ mkdir build
$ javac -d build src/com/example/myapp/*.java
```

次に、エントリポイントとなるクラスを指定するために、マニフェストファイルを作成します。
ファイル名は何でも構いません。

{{< code title="manifest.txt" >}}
Manifest-Version: 1.0
Main-Class: com.example.myapp.Main
{{< /code >}}

あとは、クラスファイルとマニフェストファイルを JAR アーカイブにまとめれば完成です。

```console
$ jar cvfm myapp.jar manifest.txt -C build com
マニフェストが追加されました
com/を追加中です(入=0)(出=0)(0%格納されました)
com/example/を追加中です(入=0)(出=0)(0%格納されました)
com/example/myapp/を追加中です(入=0)(出=0)(0%格納されました)
com/example/myapp/Main.classを追加中です(入=425)(出=295)(30%収縮されました)
```

- 注意点
  - JAR ファイル名と、マニフェストファイル名は、`jar` コマンドオプションの `f` と `m` の指定順序通りに並べて指定する必要があります。
  - クラスファイルのルートディレクトリを `-C build` というオプションで指定します。これを指定しておかないと、JAR ファイルに `build` ディレクトリごと取り込まれてしまい、実行時に `Main` クラスが見つからなくなります。

念のためアーカイブの内容を確認しておきます。
下記のような構成になっていればバッチリです。

```console
$ jar tf myapp.jar
META-INF/
META-INF/MANIFEST.MF
com/
com/example/
com/example/myapp/
com/example/myapp/Main.class
```


実行可能 JAR ファイルを実行する
----

作成した JAR ファイルは、下記のように `java` コマンドに **`-jar`** オプションを指定することで実行することができます。

```console
$ java -jar myapp.jar
Hello
```

Windows などでは、JAR ファイルをダブルクリックすることでも実行することができます。

