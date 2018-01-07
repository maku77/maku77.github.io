---
title: 独自の Maven リポジトリを使用する
date: "2016-08-03"
---

Maven リポジトリの指定方法の基本
----

Gradle はライブラリの依存関係などを処理するために、インターネット上の Maven リポジトリである JCenter や Maven Central などを簡単に利用できるようになっています。
どのリポジトリを使用するかは `repositories` ブロックで下記のように指定します。

#### build.gradle

```groovy
repositories {
    jcenter()       // Maven リポジトリとして JCenter を使用する
    mavenCentral()  // Maven リポジトリとして Maven Central を使用する
}
```


ローカルリポジトリを指定する
----

ローカルディレクトリに Maven リポジトリを作成して、ビルド時にそこからライブラリを引っ張ってくるようにすることもできます。
ローカルの Maven リポジトリを参照するためには、`repository` ブロックで `mavenLocal()` を実行します。

#### build.gradle

```groovy
repositories {
    jcenter()     // Maven リポジトリとして JCenter を使用する
    mavenLocal()  // Maven リポジトリとしてローカルディレクトリを使用する
}
```

デフォルトでは、下記のディレクトリがローカルの Maven リポジトリとして参照されます。

* Linux/MacOSX の場合: `$HOME/.m2`
* Windows の場合: `%UserProfile%\.m2`

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

#### build.gradle

```groovy
repositories {
    maven {
        url 'http://example.com/repo'
        credentials {
            username 'yourname'
            password 'sd$49aYd'
        }
    }
}
```

Meven サーバにアクセスするためのユーザ名やパスワードを上記のように設定しておくこともできます。
ただし、ビルドスクリプト内にパスワードを記述するのは避けたほうがよいので、例えば別ファイル（`gradle.properties` など）に定義しておくのがよいでしょう。

#### gradle.properties

```
mavenUsername = yourname
mavenPassword = sd$49aYd
```

#### build.gradle

```groovy
repositories {
    maven {
        url 'http://example.com/repo'
        credentials {
            username mavenUsername
            password mavenPassword
        }
    }
}
```

他にも、[環境変数にユーザ名やパスワードを設定しておく方法](../envvar.html)もあります。

