---
title: JUnit の代わりに TestNG を使ってユニットテストを実行する
date: "2015-09-24"
---

JUnit の代わりに TestNG を使ってユニットテストを記述したい場合は、build.gradle スクリプトを下記のように書き換えます。

#### build.gradle

```groovy
apply plugin: 'java'

repositories {
    mavenCentral()
}

dependencies {
    testCompile 'org.testng:testng:6.+'
}

test.useTestNG()
```

ディレクトリ構成や、test タスクで実行できるところは JUnit を使う場合と変わりありません。
最後に test.useTestNG() の呼び出しが必要なことに注意してください。

