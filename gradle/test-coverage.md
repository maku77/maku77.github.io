---
title: Gradle で JUnit のカバレッジレポートを生成する (JaCoCo)
created: 2015-09-25
---

JaCoCo Plugin を使用すると、JUnit などによるユニットテスト結果のカバレッジレポートを生成することができます。
カバレッジレポートは、そのままブラウザで見ることのできる HTML 形式や、Jenkins などの CI サーバで扱う exec データなどの形式で出力できます。

* [Gradle JaCoCo Plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)

#### HTML 形式のカバレッジレポートの例

![test-coverage.png](test-coverage.png)

下記は jacoco というタスクを定義する例です。

#### build.gradle

```groovy
apply plugin: 'java'
apply plugin: 'jacoco'

repositories {
    mavenCentral()
}

dependencies {
    testCompile 'junit:junit:4.+'
}

jacoco {
    toolVersion = "0.7.+"
    // reportsDir = file("$buildDir/reports/jacoco")
}

task jacoco(type: JacocoReport, dependsOn: 'test') {
    sourceSets sourceSets.main
    executionData = files(tasks.jacocoTestReport.executionData)
    reports {
        html.enabled = true
        xml.enabled = true
        csv.enabled = true
    }
}
```

カバレッジレポートを生成するには、ユニットテストの結果が必要なので、jacoco タスクは test タスクに依存するように定義しています。
テストカバレッジを生成するには、下記のように実行します。

```
$ gradle jacoco
:compileJava
:processResources UP-TO-DATE
:classes
:compileTestJava
:processTestResources UP-TO-DATE
:testClasses
:test
:jacoco

BUILD SUCCESSFUL

Total time: 8.697 secs
```

タスクの実行に成功すると、下記のようなファイルが生成されます。

* build/jacoco/test.exec（Jenkins の JaCoCo プラグインなどで使用）
* build/reports/jacoco/jacoco/html/index.html（ブラウザ表示用のレポート）
* build/reports/jacoco/jacoco/jacoco.csv（CSV 形式のレポート）
* build/reports/jacoco/jacoco/jacoco.xml（XML 形式のレポート）


