---
title: "Android Gradleメモ: Lint エラーが発生した場合もビルドを継続する (abortOnError)"
url: "p/o46hp5y/"
date: "2016-09-07"
tags: ["android"]
aliases: ["/android/gradle/ignore-lint-errors.html"]
---

Android Lint による静的解析がエラーになった場合でも、Gradle によるビルド処理を継続するには、対象プロジェクトの `build.gradle` で下記のように設定します。

{{< code lang="groovy" title="build.gradle" >}}
android {
    lintOptions {
        abortOnError false
    }
}
{{< /code >}}

マルチプロジェクト構成になっている場合は、トップレベルの `build.gradle` ファイルの中で、下記のように記述しておけば、サブプロジェクトすべてに対して設定を行うことができます。

{{< code lang="groovy" title="build.gradle（トップレベル）" >}}
subprojects {
    afterEvaluate { project ->
        if (project.hasProperty('android')) {
            project.android.lintOptions.abortOnError false
        }
    }
}
{{< /code >}}

