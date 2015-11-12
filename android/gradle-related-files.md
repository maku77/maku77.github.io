---
title: Android Studio を使っているときの Gradle 関連ファイル
created: 2015-11-12
---

Android Studio を使ったり、Gradle でマルチプロジェクトを扱っていたりすると、Gradle 関連のプロパティファイル (.properties) やビルドスクリプト (.gradle) が増えてきます。
混乱しやすいのでまとめておきます。

| ファイル名 | 自動生成される？ | コードリポジトリ (Git) にコミットする？ | 説明 |
| ---- | ---- | ---- | ---- |
| build.gradle | 手動で編集 | コミットする | プロジェクトごとのビルドスクリプト |
| settings.gradle | 手動で編集 | コミットする | マルチプロジェクト構成のプロジェクトにおいて、サブプロジェクトなどのリストを管理 |
| project.properties | Android Studio が自動生成 | コミットする | プロジェクトで使用する Android のターゲットバージョンなどが設定される |
| local.properties | Android Studio が自動生成 | コミットしない | Android SDK のパスなど、個人環境に依存する設定が格納される |
| その他.properties | 手動で編集 | コミットする | 独自に作成したプロパティファイル（ビルドスクリプト内で読み込んだりしているはず） |

