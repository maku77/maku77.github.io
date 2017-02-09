---
title: Java
layout: category-index
---

ユニットテスト
----
- [例外のテストを記述する](test-exception.html)

入出力 (I/O)
----
* [Java7 の try with resources でストリームの close を自動的に行う](try-with-resources.html)

アノテーション
----
* [アノテーションとは](what-is-annotation.html)
* [Java でカスタムアノテーションを作成する](custom-annotation.html)
* [Javadoc でアノテーションのサンプルコードを記述するときの &#064; の扱い](javadoc-for-annotation-example.html)

マルチスレッド
----
* [Executor によるタスク処理 (1) Executor インタフェースを使用する](thread/executor1.html)
* [Executor によるタスク処理 (2) ExecutorService インタフェースで Executor を停止する](thread/executor2.html)
* [Executor によるタスク処理 (3) Future オブジェクトによりタスクの実行結果を取得する](thread/executor3.html)
* [Executor によるタスク処理 (4) 既存の Thread から Future オブジェクトを取得する](thread/executor4.html)
* [Executor によるタスク処理 (5) ScheduledExecutorService によるタスクのスケジュール](thread/executor5.html)
* [BlockingQueue を使ってスレッド間の通信を行う](thread/blocking-queue.html)
* [Lock と Condition による条件ごとのスレッド待機](thread/lock-and-condition.html)
* [TimeUnit によって単位時間を明確にする](thread/time-unit.html)
* [スレッドで発生した Uncaught Exception（未捕捉例外）をハンドルする](thread/uncaught-exception.html)
* [Java のオブジェクトロックは再入可能であることを理解する](thread/reentrant.html)

JAR
----
* [jar コマンドで JAR ライブラリを作成する](jar/create-jar.html)
* [jar コマンドで実行可能な JAR ファイルを作成する](jar/executable-jar.html)
* [JAR ファイルの署名について](jar/jarsigner.html)

Zip ファイル
----
* [Zip ファイル内の要素（ファイルとディレクトリ）を列挙する](zip/enum-entries.html)

数値／文字列
----
* [数値と文字列の変換方法いろいろ](numstr/convert.html)

コレクション (Collection)
----
* [コレクションクラスのまとめ](collection/summary.html)

データベース/JDBC
----
* [JDBC ドライバの一覧を取得する](jdbc-list-drivers.html)
* [Java から SQLite を扱えるようにする](jdbc-sqlite-driver.html)
* [JDBC による DB 操作の流れ](jdbc-basic-flow.html)
* [プレースホルダを使用して SQL クエリを作成する](jdbc-placeholder.html)
* [最初のレコードだけを取得する](jdbc-get-first-record.html)

Java の仕様
----
* [static ブロックが実行されるタイミング](static-block.html)

Effective Java や Java の鉄則など
----
1. [コンストラクタの代わりに static ファクトリメソッドを検討する](effective/01.html)
1. [数多くのコンストラクタパラメータに直面したときにはビルダーを検討する](effective/02.html)
1. [private のコンストラクタか enum 型でシングルトン特性を強制する](effective/03.html)
1. [private のコンストラクタでインスタンス化不可能を強制する](effective/04.html)
1. [不必要なオブジェクトの生成を避ける](effective/05.html)
1. [廃れたオブジェクト参照を取り除く](effective/06.html)
1. [ファイナライザを避ける](effective/07.html)
1. [equals をオーバライドする時は一般契約に従う](effective/08.html)
1. [equals をオーバライドする時は常に hashCode をオーバライドする](effective/09.html)
1. [toString を常にオーバライドする](effective/10.html)
1. [clone を注意してオーバライドする](effective/11.html)
1. [Comparable の実装を検討する](effective/12.html)

* [コンストラクタからオーバライド可能なメソッドを呼び出さない](practice/dont-call-overridable-method-from-constructor.html)

国際化
----
* [Java アプリケーションの国際化 (i18n) と Locale クラス](i18n/locale.html)
* [Locale が変わったときに変化すべき表示項目](i18n/locale-sensitive-data.html)

Swing
----

### 基本
* [Swing で Hello World](swing/basic/helloworld.html)
* [Swing - Container と JComponent と JFrame の関係](swing/basic/container.html)
* [Swing - JPanel に GUI コンポーネントを配置する](swing/basic/jpanel.html)
* [Swing - 標準的なダイアログいろいろ](swing/basic/dialogs.html)
* [Swing - OK ボタンと Cancel ボタンのあるダイアログを作る](swing/basic/ok-cancel-dialog.html)
* [Swing - JDialog のウィンドウサイズを変更できるようにする](swing/basic/change-dialog-size.html)
* [Swing - スプリッターを配置してウィンドウを分割する](swing/basic/splitpane1.html)
* [Swing - スプリッターを折り畳み可能にする](swing/basic/splitpane2.html)
* [Swing - スプリッターの分割方向を動的に変更する](swing/basic/splitpane3.html)

### レイアウト
* [Swing - レイアウトマネージャの種類](swing/layout/layout-manager.html)
* [Swing - デフォルトのレイアウトマネージャについて](swing/layout/default-layout.html)
* [Swing - BorderLayout でコンポーネントを上下左右中央に並べる](swing/layout/border-layout1.html)
* [Swing - BorderLayout で中央に配置するコンポーネントのデフォルトサイズを設定する](swing/layout/border-layout2.html)
* [Swing - BorderLayout でコンポーネント間のスペースを設定する](swing/layout/border-layout3.html)
* [Swing - BoxLayout でコンポーネントを縦方向、横方向に並べる](swing/layout/box-layout1.html)
* [Swing - BoxLayout で左寄せ／中央寄せ／右寄せ表示する](swing/layout/box-layout2.html)
* [Swing - FlowLayout でコンポーネントを左上から並べる](swing/layout/flow-layout1.html)
* [Swing - FlowLayout でコンポーネントが複数行表示されるときに左寄せ／右寄せする](swing/layout/flow-layout2.html)

### ウィジェット
* [Swing - JButton（ボタン）にショートカットキーを割り当てる](swing/widget/jbutton-shortcut.html)

コラム
----
* [J2SE から Java SE へ](column/j2se-to-javase.html)

その他（解析）
----
* [コールスタックを表示する](call-stack.html)
* [Java で Linux の CPU 使用率を調べる](parse-proc-stat.html)
* [SuprressWarnings アノテーションで Checkstyle の警告を抑制する](suppress-checkstyle.html)
* [Java ビルドツールのまとめ (Ant, Maven, Gradle)](tools/build-tools.html)
* [クラスやメソッドの依存関係を調べる](tools/jdeps.html)

