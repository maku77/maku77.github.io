---
title: "Gradle のプロキシを設定する (gradle.properties)"
url: "p/5x6qgkx/"
date: "2014-06-01"
tags: ["gradle"]
aliases: ["/gradle/settings/proxy-settings.html"]
---

`gradle` コマンド実行時に Maven リポジトリにアクセスしたり、Gradle Wrapper をインストールするためのアーカイブをダウンロードしたりする際にネットワークアクセスが発生します。
これらのアクセスをプロキシ経由で行う必要がある場合は、**`gradle.properties`** ファイルにプロキシの設定を記述します。

| 用途 | 設定ファイル |
| ---- | ---- |
| プロジェクトごとの設定 | `<project>/gradle.properties` |
| ユーザごとの設定 (macOS/Linux) | `$HOME/.gradle/gradle.properties` |
| ユーザごとの設定 (Windows) | `%USERPROFILE%/.gradle/gradle.properties` |

以下のように設定します。

{{< code lang="groovy" title="gradle.properties" >}}
systemProp.http.proxyHost=<ホスト>
systemProp.http.proxyPort=<ポート番号>
systemProp.http.proxyUser=<ユーザ>
systemProp.http.proxyPassword=<パスワード>
systemProp.https.proxyHost=<ホスト>
systemProp.https.proxyPort=<ポート番号>
systemProp.https.proxyUser=<ユーザ>
systemProp.https.proxyPassword=<パスワード>
{{< /code >}}

{{< code lang="groovy" title="設定例" >}}
systemProp.http.proxyHost=proxy.example.com
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=proxy.example.com
systemProp.https.proxyPort=8080
{{< /code >}}

