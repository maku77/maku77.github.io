---
title: Gradle デーモンを使って gradle コマンドを高速化する
created: 2015-07-10
---

Gradle デーモンの立ち上げ
----

`gradle` コマンドを実行すると、デフォルトでは毎回 Java のバーチャルマシンを起動するため、下記のような簡単なタスクを実行するだけでも **2、3 秒**の実行時間がかかってしまいます。

#### build.gradle
```groovy
task hello << {
    println 'Hello'
}
```

#### 実行結果
```sh
$ gradle hello
:hello
Hello

BUILD SUCCESSFUL

Total time: 2.353 secs
```

`gradle` コマンドを実行するときに、**--daemon** オプションを付けるようにすると、Gradle のプロセスを常駐させ（デーモン化）、そのプロセスを毎回のビルドで使いまわすようになります。
初回はデーモンの立ち上げのために時間がかかりますが、2 度目からのビルドは高速に実行することができます。
下記の実行例を見ると、**1 秒以内**に処理できていることがわかります。

#### 実行結果（--daemon オプションあり）
```sh
$ gradle --daemon hello
:hello
Hello

BUILD SUCCESSFUL

Total time: 0.905 secs
```

ちなみに、常駐している Gradle のプロセスは、**3 時間**使用されないと自動的に終了します。
下記のように明示的に停止することもできます。

```sh
$ gradle --stop
Stopping daemon(s).
Gradle daemon stopped.
```

Gradle デーモンをデフォルトで有効にする
----

`gradle` コマンドに毎回 `--daemon` オプションを付けるのが面倒な場合は、Gradle の設定ファイル (gradle.properties) でデーモンを常に有効にすることができます。

設定は下記のいずれかのファイルで行います。

* ＜Project＞/gradle.properties （プロジェクトごとの設定）
* $HOME/.gradle/gradle.properties （システム全体の設定 - Linux の場合）
* %USERPROFILE%/.gradle/gradle.properties （システム全体の設定 - Windows の場合）

#### gradle.properties
```groovy
org.gradle.daemon=true
```

