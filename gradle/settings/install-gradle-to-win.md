---
title: Gradle を Windows にインストールする
created: 2014-06-16
---

[http://www.gradle.org/downloads](http://www.gradle.org/downloads)

から `gradle-1.12-bin.zip` などをダウンロードして、適当なディレクトリに配置します。
`gradle` コマンドを実行するには、`java` コマンドを実行できるようになっている必要があります。
任意のバージョンの `java` コマンドを使用するには、`JAVA_HOME` 環境変数を設定しておきます。

```
C:\> set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_05
    （binまでは含めないことに注意）
```

`gradle` コマンドを任意のディレクトリから実行できるように、PATH を通しておきます。

```
C:\> set PATH=%PATH%;C:\app\gradle-1.12\bin
```

#### 実行テスト:

```
C:\> gradle -v

------------------------------------------------------------
Gradle 1.12
------------------------------------------------------------

Build time:   2014-04-29 09:24:31 UTC
Build number: none
Revision:     a831fa866d46cbee94e61a09af15f9dd95987421

Groovy:       1.8.6
Ant:          Apache Ant(TM) version 1.9.3 compiled on December 23 2013
Ivy:          2.2.0
JVM:          1.8.0_05 (Oracle Corporation 25.5-b02)
OS:           Windows 7 6.1 amd64
```
