---
title: Gradle のプロパティを環境変数 (ORG_GRADLE_PROJECT) で定義する
created: 2016-10-13
---

現在使用している PC の環境変数として、`ORG_GRADLE_PROJECT_` というプレフィックスで始まる変数を定義しておくと、Gradle のビルドスクリプト内からその値を参照できるようになります。
ユーザごとに異なる設定（ユーザ ID やパスワード）などの設定を行いたい場合に使用すると便利です。
下記の例では、Maven サーバ用のアクセス情報を示すプロパティ（`MAVEN_USERNAME` など）を設定しています。

#### ~/.bash_profile

```sh
export ORG_GRADLE_PROJECT_MAVEN_URL=http://repo.mycompany.com/maven2
export ORG_GRADLE_PROJECT_MAVEN_USERNAME=xxx
export ORG_GRADLE_PROJECT_MAVEN_PASSWORD=yyy
```

あとは、下記のようにビルドスクリプトの中から簡単に参照することができます。

#### build.gradle

```groovy
repositories {
    maven {
        credentials {
            username MAVEN_USERNAME
            password MAVEN_PASSWORD
        }
        url MAVEN_URL
    }
}
```


参考
----
* [ORG_GRADLE_PROJECT プレフィックスの環境変数について (The Build Environment - Gradle User Guide)](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_properties_and_system_properties)
* [Maven リポジトリの Credential 設定 (Dependency Management - Gradle User Guide)](https://docs.gradle.org/current/userguide/dependency_management.html#sec:accessing_password_protected_maven_repositories)

