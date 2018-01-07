---
title: Java のビルドツールのまとめ (Ant, Maven, Gradle)
date: "2014-06-02"
---

Ant
----

[http://ant.apache.org/](http://ant.apache.org/)

* build.xml で管理
* Make のプラットフォーム依存を解決したが、記述が冗長
* ちょっと凝ったことをしようとすると build.xml が巨大になってしまう
* ライブラリの依存関係が解決できない

Maven
----

[https://maven.apache.org/](https://maven.apache.org/)

* 動作が重い
* 決まったことをする場合は楽に記述できる
* 少し複雑なことをしようとすると Ant よりも複雑になる（ビルド職人になってしまう）
* そもそも XML は human-readable じゃないと皆気づいた。XML はコンピュータのためのもの。

Gradle
----

[https://gradle.org/](https://gradle.org/)

* build.gradle で管理
* Groovy の DSL ベースで記述するため柔軟性が非常に高く、読みやすい
* Ant や Maven よりも記述が楽
* プロジェクト間の依存関係を解決できる
* Android Studio と、そのもとの IntelliJ IDEA はデフォルトは Gradle になっている
* Make, Ant, Ivy, Maven, Rake, Gant, Scons, SBT, Leinengen, Buildr などでやっていたことを統一的に扱える


