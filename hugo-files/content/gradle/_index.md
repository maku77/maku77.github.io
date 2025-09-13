---
title: "Gradle"
url: "/gradle/"
date: "2025-08-26"
tags: ["gradle"]

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
* [Gradle のプロジェクトとタスクとアクションを理解する](/p/yqpxbpa/)
* [Gradle 実行時の 3 つのフェーズ (initialization, configuration, execution)](/p/o7yqmcx/)
* [Gradle のタスクを定義する (`task`)](/p/a88r2ua/)
* [Gradle でデフォルトタスクを指定する (`defaultTasks`)](/p/zfc37zt/)
* [Gradle でタスクの依存関係を表現する (`dependsOn`)](/p/sifxuig/)
* [Gradle のタスクに説明を付け、グルーピングする (`description`, `group`)](/p/kgwpdtz/)
* [既存の Gradle タスクにアクションを追加する (`doLast`, `doFirst`)](/p/6q9wkjm/)
* [Gradle Wrapper スクリプト (`gradlew`) を作成する](/p/m7u5dgp/)
* [Gradle Wrapper スクリプト (`gradlew`) をサブプロジェクトから簡単に呼び出せるようにする](/p/je3xamc/)
* [Gradle で定義されているタスクの一覧を表示する (`gradle tasks`)](/p/qoaky8w/)

Gradle で Java プロジェクトを扱う (java プラグイン)
----
* [Gradle で Java プロジェクトをビルドする](/p/bvinwfk/)
* [Gradle で Javadoc API ドキュメントを出力する](/p/sj6sc4d/)
* [Gradle で実行可能な JAR ファイルを作成する](/p/5iacmi7/)
* [Gradle で Java プロジェクトをビルドするときのディレクトリ構成を変更する (`sourceSets`, `buildDir`)](/p/odjj8e7/)
* [Gradle で独自の Maven リポジトリを使用する](/p/tmkh8p6/)

Gradle でマルチプロジェクトを扱う
----
* [Gradle によるマルチプロジェクトの基本 (`settings.gradle`)](/p/3g9gimf/)
* [Gradle のマルチプロジェクトで依存関係を扱う (`dependencies`, `compile`)](/p/hg9n9wd/)

Gradle でユニットテスト
----
* [Gradle で JUnit によるユニットテストを実行する](/p/x6f9r2o/)
* [Gradle で TestNG によるユニットテストを実行する](/p/84xtqff/)
* [Gradle で JUnit のカバレッジレポートを生成する (JaCoCo)](/p/tesecus/)
* [Gradle でユニットテストの結果をコンソールに詳しく表示する](/p/8ot95rq/)
* [Gradle でユニットテストを並列実行する (`test.forkEvery`, `test.maxParallelForks`)](/p/pxkr6wv/)

Gradle で静的解析 (static analysis)
----
* [Gradle で Checkstyle による静的解析を実行する](/p/em3rtg6/)
* [Gradle で PMD による静的解析を実行する](/p/pz9gz3h/)

Gradle で I/O（ファイル入出力、環境変数、ユーザ入力）
----
* [Gradle でファイルをコピー、リネームするためのタスクを作成する (`type: Copy`)](/p/q6ducqz/)
* [Gradle のテンプレート機能を使ってファイルを生成する (`type: Copy`)](/p/ijynhet/)
* [Gradle でディレクトリ内のファイルを ZIP 圧縮する (`type: Zip`)](/p/p6bu2pa/)
* [Gradle でプロパティファイル (`.properties`) を読み込む](/p/quhymp7/)
* [Gradle のプロパティを環境変数 (`ORG_GRADLE_PROJECT`) で定義する](/p/2uxzo7h/)

Groovy スクリプト
----
* [Groovy と Java の違い](/p/som2e4k/)
* [Groovy で `assert` を使用する](/p/m9veity/)
* [Groovy でメソッドを定義する](/p/6qzzzry/)
* [Groovy でクラスを定義する](/p/j5bvcq9/)
* [Groovy で文字列リテラルを扱う](/p/v8m6rme/)
* [Groovy でリスト (List) を扱う](/p/z9qmfd4/)
* [Groovy でマップ (Map) を扱う](/p/ohhdpvf/)
* [Groovy のクロージャ](/p/b962btg/)
* [Groovy でパスワードなどをユーザに入力させる (`readPassword`)](/p/yxha7mn/)

独自の Gradle プラグインを作成する
----
* [Gradle プラグインを作成する (1) Plugin クラスの基本](/p/negnqwf/)
* [Gradle プラグインを作成する (2) JAR へのパッケージング](/p/dyas9zg/)

Android 開発での Gradle ビルド
----
Android に関しては [こちらを参照](/android/)

