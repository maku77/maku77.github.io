---
title: "Gradle のプロパティを環境変数 (ORG_GRADLE_PROJECT) で定義する"
url: "p/2uxzo7h/"
date: "2016-10-13"
tags: ["gradle"]
aliases: ["/gradle/envvar.html"]
---

環境変数で Gradle プロジェクトのプロパティを設定する
----

OS の環境変数として **`ORG_GRADLE_PROJECT_`** というプレフィックスで始まる変数を定義しておくと、Gradle のビルドスクリプト内から、プロジェクトのプロパティとして参照できるようになります。
ユーザごとに異なる値（ユーザ ID やパスワード）を設定したいときに便利です。
下記の例では、Maven サーバ用のアクセス情報を示すプロパティ（`MAVEN_USERNAME` など）を設定しています。

{{< code lang="bash" title="~/.bash_profile" >}}
export ORG_GRADLE_PROJECT_MAVEN_URL=http://repo.mycompany.com/maven2
export ORG_GRADLE_PROJECT_MAVEN_USERNAME=xxx
export ORG_GRADLE_PROJECT_MAVEN_PASSWORD=yyy
{{< /code >}}

あとは、下記のようにビルドスクリプトの中から簡単に参照することができます。

{{< code lang="groovy" title="build.gradle" >}}
repositories {
    maven {
        credentials {
            username MAVEN_USERNAME
            password MAVEN_PASSWORD
        }
        url MAVEN_URL
    }
}
{{< /code >}}


プロパティが設定されているかどうか確認する
----

定義されていないプロパティを参照しようとするとエラーが発生します。
プロジェクトのプロパティが定義されているかどうかを調べるには、以下のように `project.has` メソッドを使用します（`project.` は省略可能です）。

```groovy
if (!project.has('MAVEN_USERNAME')) {
    println 'ORG_GRADLE_PROJECT_MAVEN_USERNAME is not set'
}
```

環境変数 `ORG_GRADLE_PROJECT_xxx` で定義された値も、プロジェクトのプロパティとして参照できるようになるため、上記のようにして定義されているかどうかを確認することができます。


参考
----
* [ORG_GRADLE_PROJECT プレフィックスの環境変数について (The Build Environment - Gradle User Guide)](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_properties_and_system_properties)
* [Maven リポジトリの Credential 設定 (Dependency Management - Gradle User Guide)](https://docs.gradle.org/current/userguide/dependency_management.html#sec:accessing_password_protected_maven_repositories)

