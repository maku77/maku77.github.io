---
title: "Android Gradleメモ: Android の Gradle 関連ファイルまとめ"
url: "p/8f3pcke/"
date: "2015-11-12"
tags: ["android", "gradle"]
aliases: [/android/gradle-related-files.html]
---

Android Studio を使ったり、Gradle でマルチプロジェクトを扱っていたりすると、Gradle 関連のプロパティファイル (`.properties`) やビルドスクリプト (`.gradle`) が増えてきます。
混乱しやすいのでまとめておきます。

| ファイル名 | 自動生成される？ | Gitにコミットする？ | 説明 |
| ---- | ---- | ---- | ---- |
| build.gradle | 手動で編集 | する | プロジェクトごとのビルドスクリプト |
| settings.gradle | 手動で編集 | する | マルチプロジェクト構成のプロジェクトにおいて、サブプロジェクトなどのリストを管理 |
| gradle.properties | Android Studio が自動生成 | しない | Gradle 実行のプロキシ設定やメモリ容量設定 |
| local.properties | Android Studio が自動生成 | しない | Android SDK のパスなど、個人環境に依存する設定が格納される |
| その他.properties | 手動で編集 | する | 独自に作成したプロパティファイル（ビルドスクリプト内で読み込んだりしているはず） |

