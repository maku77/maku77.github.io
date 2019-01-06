---
title: "Lint エラーが発生した場合もビルドを継続する"
date: "2016-09-07"
---

Android Lint による静的解析がエラーになった場合でも、Gradle によるビルド処理を継続するには、対象プロジェクトの `build.gradle` で下記のように設定します。

#### build.gradle

```groovy
android {
    lintOptions {
        abortOnError false
    }
}
```

マルチプロジェクト構成になっている場合は、トップレベルの `build.gradle` ファイルの中で、下記のように記述しておけば、サブプロジェクトすべてに対して設定を行うことができます。

#### build.gradle（トップレベル）

```groovy
subprojects {
    afterEvaluate { project ->
        if (project.hasProperty('android')) {
            project.android.lintOptions.abortOnError false
        }
    }
}
```

