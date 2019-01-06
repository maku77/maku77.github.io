---
title: "Gradle Wrapper スクリプト (gradlew) をサブプロジェクトから簡単に呼び出せるようにする"
date: "2017-08-07"
---

Gradle Wrapper Wrapper (gradleww) とは
----

[Gradle Wrapper Wrapper (gradleww)](https://github.com/maku77/gradleww) を使用すると、サブプロジェクト（サブディレクトリ）で作業しているときに、プロジェクトのルートにおいてある `gradlew` を簡単に呼び出すことができるようになります。

例えば、通常はサブプロジェクトで作業しているときは、`gradlew` コマンドを実行するときにこんな感じで実行することになります。

~~~
$ ../../gradlew assembleDebug
~~~

`gradlew` の代わりに `gradleww` コマンドを実行するようにすれば、次のように簡単に呼び出すことができます（内部で自動的に上位ディレクトリの `gradlew` を実行してくれます）。

~~~
$ gradleww assembleDebug
~~~

Gradle Wrapper (gradlew) をさらにラップしたコマンドのため、Gradle Wrapper Wrapper (gradleww) と呼んでいます。


Gradlew Wrapper Wrapper (gradleww) のインストール
----

Gradle Wrapper Wrapper（`gradleww` コマンド）は、RubyGems パッケージとして配布されているため、Ruby 環境がインストールされていれば、下記のように簡単にインストールすることができます。

~~~
$ gem install gradleww
~~~

