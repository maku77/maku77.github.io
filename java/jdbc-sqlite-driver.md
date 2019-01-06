---
title: "Java から SQLite を扱えるようにする"
date: "2010-08-08"
---

SQLite 用の JDBC ドライバをインストールすれば、JDBC インタフェースを使って SQlite データベースにアクセスできるようになります。

SQLite JDBC Driver をダウンロード
====

SQLite 用の JDBC ドライバは下記で提供されています (2010-08-08 時点で最新は sqlite-jdbc-3.6.20.jar)。

* http://www.xerial.org/trac/Xerial/wiki/SQLiteJDBC

上記の JAR ファイルには各プラットフォーム用の、ネイティブな SQLite 実行ライブラリが含まれています。
この JAR ファイルさえあれば、sqlite コマンドを提供する実行ファイルなどは必要ありません。

Eclipse から SQLite JDBC Driver を使用する
====

Eclipse で SQLite JDBC Driver を使ったプログラムを行うには、Java プロジェクトに sqlite-jdbc-x.x.x.jar へのクラスパスを追加します。
ライブラリの JAR ファイルを、プロジェクトの構成ディレクトリにコピーして使うようにすれば、別の PC で開発の続きをするときに、JAR ファイルを再度ダウンロードしなくて済みます。

1. Package Explorer のプロジェクトに、sqlite-jdbc-xxx.jar をドラッグ＆ドロップ。
これで、プロジェクトのディレクトリに sqlite-jdbc-xxx.jar がコピーされます。
2. Package Explorer でプロジェクトを右クリック => Property
3. Java Build Path の Libraries タブで Add JARs
4. プロジェクト内の sqlite-jdbc-xxx.jar を選択

