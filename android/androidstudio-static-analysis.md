---
title: Android Studio で静的解析プラグインを使用する
created: 2015-12-09
---

Gradle で Checkstyle や FindBugs のタスクを実行するのもよいですが、Android Studio に Plugin を入れておくと、警告箇所を簡単に調べられるようになります。

CheckStyle 用のプラグイン (CheckStyle-IDEA)
====

### インストール

1. `Ctrl + Alt + S` で Settings を開く
2. `Plugins` の `Browse repositories...` ボタンを押す
3. **CheckStyle-IDEA** をインストール
4. Android Studio を再起動

### 設定（プロジェクト用の設定が必要な場合）

1. `Ctrl + Alt + S` で Settings を開く
2. `Other tools` の `CheckStyle` を開く
3. プロジェクト用の設定ファイルを追加し、チェックボックスにチェックを入れる

### 実行

1. `Ctrl + Shift + A` で `check` と入力
  * カレントファイルをチェックする場合: `Check Current File`
  * モジュール全体をチェックする場合: `Check Module`
  * プロジェクト全体をチェックする場合: `Check Project`


FindBugs 用のプラグイン (FindBugs-IDEA)
====

### インストール

1. `Ctrl + Alt + S` で Settings を開く
2. `Plugins` の `Browse repositories...` ボタンを押す
3. **FindBugs-IDEA** をインストール
4. Android Studio を再起動

### 設定（プロジェクト用の設定が必要な場合）

1. `Ctrl + Alt + S` で Settings を開く
2. `Other tools` の `FindBugs-IDEA` を開く
3. `Filters` タブの `Exclude filter files` の `Add` ボタンを押し、排他設定ファイルを読み込み。などなど。

### 実行

1. `Ctrl + Shift + A` で `findbugs` と入力
  * カレントファイルをチェックする場合: `Analyze Current File`
  * モジュール全体をチェックする場合: `Analyze Module Files`
  * プロジェクト全体をチェックする場合: `Analyze Project Files`


