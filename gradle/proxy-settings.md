---
title: Gradle のプロキシを設定する
created: 2014-06-01
---

Gradle のプロキシ設定は、下記のいずれかのファイルで行います。

* <プロジェクトルート>/gradle.properties
* ~/.gradle/gradle.properties

#### gradle.properties
```groovy
systemProp.http.proxyHost=<ホスト>
systemProp.http.proxyPort=<ポート番号>
systemProp.http.proxyUser=<ユーザ>
systemProp.http.proxyPassword=<パスワード>
systemProp.https.proxyHost=<ホスト>
systemProp.https.proxyPort=<ポート番号>
systemProp.https.proxyUser=<ユーザ>
systemProp.https.proxyPassword=<パスワード>
```

#### 設定例
```groovy
systemProp.http.proxyHost=proxy.example.com
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=proxy.example.com
systemProp.https.proxyPort=8080
```
