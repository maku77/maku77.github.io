---
title: "JUnit の代わりに TestNG を使ってユニットテストを実行する"
url: "p/84xtqff/"
date: "2015-09-24"
tags: ["gradle"]
aliases: ["/gradle/test-testng.html"]
---

JUnit の代わりに **TestNG（テスティング）** を使ってユニットテストを記述したい場合は、`build.gradle` スクリプトを下記のように書き換えます。

{{< code lang="groovy" title="build.gradle" >}}
apply plugin: 'java'

repositories {
    mavenCentral()
}

dependencies {
    testCompile 'org.testng:testng:6.+'
}

test.useTestNG()
{{< /code >}}

ディレクトリ構成や、`test` タスクで実行できるところは JUnit を使う場合と変わりありません。
最後に **`test.useTestNG()`** の呼び出しが必要なことに注意してください。

