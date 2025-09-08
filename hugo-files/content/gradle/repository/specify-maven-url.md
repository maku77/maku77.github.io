---
title: "Gradle で独自の Maven リポジトリを使用する"
url: "p/tmkh8p6/"
date: "2016-08-03"
tags: ["gradle"]
aliases: ["/gradle/repository/specify-maven-url.html"]
---

Maven リポジトリの指定方法の基本
----

Gradle はライブラリの依存関係などを処理するために、インターネット上の Maven リポジトリである JCenter や Maven Central などを簡単に利用できるようになっています。
どのリポジトリを使用するかは **`repositories`** ブロックで下記のように指定します。

{{< code lang="groovy" title="build.gradle" >}}
repositories {
    jcenter()       // Maven リポジトリとして JCenter を使用する
    mavenCentral()  // Maven リポジトリとして Maven Central を使用する
}
{{< /code >}}


ローカルリポジトリを指定する
----

ローカルディレクトリに Maven リポジトリを作成して、ビルド時にそこからライブラリを引っ張ってくるようにすることもできます。
ローカルの Maven リポジトリを参照するためには、`repository` ブロックで **`mavenLocal()`** を実行します。

{{< code lang="groovy" title="build.gradle" >}}
repositories {
    jcenter()     // Maven リポジトリとして JCenter を使用する
    mavenLocal()  // Maven リポジトリとしてローカルディレクトリを使用する
}
{{< /code >}}

デフォルトでは、下記のディレクトリがローカルの Maven リポジトリとして参照されます。

* Linux/MacOSX の場合: **`$HOME/.m2`**
* Windows の場合: **`%UserProfile%\.m2`**

リポジトリとするディレクトリを変更したい場合は、下記のように設定します。

```groovy
repositories {
    maven {
        url '../maven_repo'
    }
}
```


リモートリポジトリを指定する
----

独自で立ち上げた Maven サーバを利用するように設定することもできます。

{{< code lang="groovy" title="build.gradle" >}}
repositories {
    maven {
        url 'http://example.com/repo'
        credentials {
            username 'yourname'
            password 'sd$49aYd'
        }
    }
}
{{< /code >}}

Maven サーバにアクセスするためのユーザ名やパスワードは、上記のように **`credentials`** で設定できますが、ビルドスクリプト内にパスワードを記述するのは避けたほうがよいでしょう。
例えば、別ファイル（`gradle.properties` など）に定義しておき、ビルドスクリプトから参照するようにします。

{{< code title="gradle.properties" >}}
mavenUsername = yourname
mavenPassword = sd$49aYd
{{< /code >}}

{{< code lang="groovy" title="build.gradle" >}}
repositories {
    maven {
        url 'http://example.com/repo'
        credentials {
            username mavenUsername
            password mavenPassword
        }
    }
}
{{< /code >}}

他にも、[環境変数にユーザ名やパスワードを設定しておく方法](/p/2uxzo7h/)もあります。

