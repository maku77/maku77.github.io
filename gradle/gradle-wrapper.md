---
title: Gradle Wrapper スクリプト (gradlew) を作成する
created: 2015-07-10
layout: gradle
---

Gradle Wrapper とは
===

Gradle Wrapper スクリプト (**gradlew**) を作成しておくと、Gradle の実行環境をインストールしていない環境でも `gradle` コマンドを実行するのと同様のビルドを行えるようになります。
Gradle Wrapper は、具体的には下記のようなスクリプトファイルです。

* gradlew（Linux 用のシェルスクリプト）
* gradlew.bat（Windows 用のバッチファイル）

プロジェクトに `gradlew` が用意されている場合は、`gradle` コマンドの代わりにそちらを使えば OK です。例えば、`build` タスクを実行したい場合は、下記のように実行します。

#### gradle コマンドの代わりに gradlew コマンドを使用
```sh
$ gradlew build
```

仕組みは単純で、Gradle がインストールされていない環境で `gradlew` コマンドを実行すると、最初に Gradle の実行環境がインストールされてビルドが実行されるようになっています。
Gradle の実行環境の実体は `~/.gradle/wrapper/dists/gradle-1.12-bin` といったディレクトリにインストールされ、次回の `gradlew` コマンド実行時には、そこにあるファイルが使用されるようになります（この動きを特に意識する必要はありません）。


Gradle Wrapper の作成
===
Gradle Wrapper のスクリプトファイル、およびその実行に必要なライブラリ群は、下記のコマンドで生成することができます。

```sh
$ gradle wrapper
```

これだけでも十分なのですが、下記のように `wrapper` タスクを定義しておくことで、どのバージョンの Gradle 相当の Gradle Wrapper を作成するかを指定することができます（他にもいろいろな設定を行うことができます。詳しくは [Wrapper の DSL ドキュメント](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.wrapper.Wrapper.html) を参照してください）。

#### build.gradle
```
task wrapper(type: Wrapper) {
    gradleVersion = '1.12'
}
```

上記のようなバージョン指定を行わない場合は、Gradle Wrapper 作成時に使用した `gradle` コマンドのバージョンに相当する Gradle Wrapper が作成されます。
ビルド環境をより明確にするためにも、上記のタスクは定義しておいた方がよいでしょう。

Gradle Wrapper の生成に成功すると、次のようなファイル群が生成されます。

```
＜Project＞
  +-- gradle
  |    +-- wrapper
  |         +-- gradle-wrapper.jar
  |         +-- gradle-wrapper.properties
  +-- gradlew      （Linux 用のコマンド）
  +-- gradlew.bat  （Windows 用のコマンド）
```

Git などのコードリポジトリには、**上記のファイル群をすべてコミット**するようにします。
そうすれば、他の開発メンバは提供されている `gradlew` コマンドを実行するだけで、プロジェクトのビルドを行えるようになります。

