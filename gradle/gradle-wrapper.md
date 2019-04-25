---
title: "Gradle Wrapper スクリプト (gradlew) を作成する"
update: "2019-04-25"
date: "2015-07-10"
---

Gradle Wrapper とは
----

Gradle Wrapper スクリプト (**gradlew**) を作成しておくと、Gradle の実行環境をインストールしていない環境でも `gradle` コマンドを実行するのと同様のビルドを行えるようになります。
Gradle Wrapper は、具体的には下記のようなスクリプトファイルです。

* gradlew（Linux 用のシェルスクリプト）
* gradlew.bat（Windows 用のバッチファイル）

プロジェクトに `gradlew` が用意されている場合は、`gradle` コマンドの代わりにそちらを使えば OK です。例えば、`build` タスクを実行したい場合は、下記のように実行します。

#### gradle コマンドの代わりに gradlew コマンドを使用

```
$ gradlew build
```

仕組みは単純で、Gradle がインストールされていない環境で `gradlew` コマンドを実行すると、最初に Gradle の実行環境がインストールされてビルドが実行されるようになっています。
Gradle の実行環境の実体は `~/.gradle/wrapper/dists/gradle-1.12-bin` といったディレクトリにインストールされ、次回の `gradlew` コマンド実行時には、そこにあるファイルが使用されるようになります（この動きを特に意識する必要はありません）。


Gradle Wrapper の作成
----

Gradle Wrapper のスクリプトファイル、およびその実行に必要なライブラリ群は、下記のコマンドで生成することができます。

```
$ gradle wrapper
```

これだけでも十分なのですが、下記のように `wrapper` タスクのコンフィギュレーションを定義しておくことで、どのバージョンの Gradle 相当の Gradle Wrapper を作成するかを指定することができます（他にもいろいろな設定を行うことができます。詳しくは [Wrapper の DSL ドキュメント](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.wrapper.Wrapper.html) を参照してください）。

#### build.gradle

```groovy
wrapper {
    gradleVersion = '5.0'
}
```

上記のようなバージョン指定を行わない場合は、Gradle Wrapper 作成時に使用した `gradle` コマンドのバージョンに相当する Gradle Wrapper が作成されます。
ビルド環境をより明確にするためにも、上記のタスクは定義しておいた方がよいでしょう。

<div class="note">
（2019-04-25 追記）昔は下記のように <code>wrapper</code> タスクを定義する方法が用いられていました。

<pre>
task wrapper(type: Wrapper) {
    gradleVersion = '1.12'
}
</pre>

ただし、Gradle 4.4 以降で上記のようなタスク定義を行うと、<code>Cannot add task 'wrapper' as a task with that name already exists.</code> というタスクの重複定義エラーが発生するので、コンフィギュレーションブロックの定義の形に修正する必要があります。
</div>

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


Gradle Wrapper のバージョンアップ
----

Gradle の最新バージョンは [こちら](https://services.gradle.org/distributions/) で確認できます。
プロジェクトで使用している Gradle Wrapper のバージョンを変更したい場合は、下記のように `wrapper` タスクのバージョン情報を更新し、

#### build.gradle

```groovy
wrapper {
    gradleVersion = '5.4'
}
```

この `wrapper` タスクを実行します。

```
$ gradlew wrapper
```

一度目の実行は、現在の Gradle Wrapper のバージョンで実行され、下記のような設定ファイルが更新されます。

- `gradle/wrapper/gradle-wrapper.properties`

上記には、Gradlew Wrapper 実行時に取得する Gradle のバージョンが記述されており、次回からの `gradlew` コマンドは新しい Gradle バージョンで実行されるようになります。
**Gradle の実体である jar ファイルが実際に取得されるのは、もう一度 `gradlew` コマンドを実行したとき**のようなので、もう一回同じコマンドを実行しておきます。

```
$ gradlew wrapper
```

これで、一連の Gradlew Wrapper 関連ファイルが更新されるので、Git でコミットすれば Gradle Wrapper のバージョン更新作業は完了です。

```
$ git add build.gradle
$ git add gradle/wrapper
$ git add gradlew
$ git add gradlew.bat
$ git commit
```


トラブルシューティング (IllegalCharsetNameException)
----

Windows で Gradle 5.4 の `gradlew.bat` を実行すると、下記のようにコケる現象が発生しました（Ver 5.0 でも同様）。

```
D:\myproj\> gradlew --version
Error occurred during initialization of VM
java.lang.ExceptionInInitializerError
        at java.lang.ClassLoader.initSystemClassLoader(ClassLoader.java:1451)
        at java.lang.ClassLoader.getSystemClassLoader(ClassLoader.java:1436)
Caused by: java.nio.charset.IllegalCharsetNameException: UTF-8"-Xmx64m
...
```

どうも、バッチファイルの記述がおかしくなっているようです。

#### gradlew.bat （L.33 あたり）

```bat
set GRADLE_OPTS="-Dfile.encoding=UTF-8""-Xmx64m" "-Xms64m"
```

を下記のように修正します（パラメータ間のスペースが足りないので追加）。

```bat
set GRADLE_OPTS="-Dfile.encoding=UTF-8" "-Xmx64m" "-Xms64m"
```

これでエラーなしで実行できるようになります。

