---
title: "Gradle"
date: "2025-08-26"
url: "/gradle/"

categoryName: "まくまく Gradle ノート"
categoryUrl: "/gradle/"
categoryIcon: _logo-gradle.svg
---

インストールと設定／管理
----
* [Gradle をインストールする (macOS/Windows)](/p/6qdfg4d/)
* [Gradle のプロキシを設定する (`gradle.properties`)](/p/5x6qgkx/)
* [Gradle デーモンを使って `gradle` コマンドを高速化する (`org.gradle.daemon`)](/p/szhzejv/)
* [Gradle によるビルドのボトルネックを探す（プロファイリング） (`--profile`)](/p/ofoencn/)

Gradle の基本
----
* [プロジェクトとタスクとアクション](basics/project-and-task.html)
* [Gradle 実行時の 3 つのフェーズ](basics/three-phases.html)
* [Gradle のタスクを定義する (task)](define-gradle-tasks.html)
* [Gradle でデフォルトタスクを指定する (defaultTasks)](default-task.html)
* [Gradle でタスクの依存関係を表現する (dependsOn)](task-dependency.html)
* [Gradle のタスクに説明を付け、グルーピングする (description, group)](group-tasks.html)
* [既存の Gradle タスクにアクションを追加する (doLast, doFirst)](add-action-to-task.html)
* [Gradle Wrapper スクリプト (gradlew) を作成する](gradle-wrapper.html)
* [Gradle Wrapper スクリプト (gradlew) をサブプロジェクトから簡単に呼び出せるようにする](gradle-wrapper-wrapper.html)

### Maven リポジトリ
* [独自の Maven リポジトリを使用する](repository/specify-maven-url.html)

Gradle で Java プロジェクトを扱う (java プラグイン)
----
* [Gradle で Java プロジェクトをビルドする](build-java-project.html)
* [Gradle で Javadoc API Document を出力する](javadoc.html)
* [Gradle で実行可能な JAR ファイルを作成する](executable-jar.html)
* [Gradle で Java プロジェクトをビルドするときのディレクトリ構成を変更する (sourceSets, buildDir)](java-project-structure.html)

Gradle でユニットテスト
----
* [Gradle で JUnit によるユニットテストを実行する](test-junit.html)
* [Gradle で JUnit の代わりに TestNG でユニットテストを実行する](test-testng.html)
* [Gradle で JUnit のカバレッジレポートを生成する (JaCoCo)](test-coverage.html)
* [Gradle でユニットテストの結果をコンソールに詳しく表示する](test-detail-log.html)
* [Gradle でユニットテストを並列実行する](test-parallel.html)


Gradle でマルチプロジェクトを扱う
----
* [Gradle によるマルチプロジェクトの基本](multi-project.html)
* [Gradle のマルチプロジェクトで依存関係を扱う](multi-project-dependency.html)

Gradle で静的解析 (static analysis) を行う
----
* [Gradle で Checkstyle による静的解析を実行する](checkstyle.html)
* [Gradle で PMD による静的解析を実行する](pmd/pmd.html)


Gradle での I/O（ファイル入出力、環境変数、ユーザ入力）
----

### ファイル
* [Gradle でファイルをコピーするためのタスクを作成する](gradle-copy-files.html)
* [Gradle でファイルをリネームする](gradle-rename-files.html)
* [Gradle のテンプレート機能によりファイル内容を書き換える](gradle-template-engine.html)
* [Gradle でディレクトリ内のファイルを ZIP 圧縮する](file/zip.html)

### プロパティ
* [Gradle でプロパティファイル (.properties) を読み込む](property-file.html)
* [Gradle のプロパティを環境変数 (ORG_GRADLE_PROJECT) で定義する](envvar.html)


Gradle のコマンド
----
* [Gradle タスクの一覧を表示する (tasks)](gradle-tasks.html)
* [Gradle のプロジェクトで使用可能なプロパティの一覧を表示する (properties)](gradle-properties.html)


Groovy スクリプト
----
* [Groovy と Java の違い](/p/som2e4k/)
* [Groovy で assert を使用する](/p/m9veity/)
* [Groovy でメソッドを定義する](/p/6qzzzry/)
* [Groovy でクラスを定義する](/p/j5bvcq9/)
* [Groovy で文字列リテラルを扱う](/p/v8m6rme/)
* [Groovy でリスト (List) を扱う](/p/z9qmfd4/)
* [Groovy でマップ (Map) を扱う](/p/ohhdpvf/)
* [Groovy のクロージャ](/p/b962btg/)
* [Groovy でパスワードなどをユーザに入力させる (`readPassword`)](/p/yxha7mn/)

独自の Gradle プラグインを作成する
----

* [Gradle プラグインを作成する (1) Plugin クラスの基本](plugin/plugin-class.html)
* [Gradle プラグインを作成する (2) JAR へのパッケージング](plugin/packaging.html)

Android 開発における Gradle
----

Android に関しては [こちらを参照](../android/)

