---
title: Java
layout: category-index
---

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
* [Boolean 型で synchronized するのは NG](thread/boolean-sync.html)

JAR/ZIP ファイル
----
* [jar コマンドで JAR ライブラリを作成する](jar/create-jar.html)
* [jar コマンドで実行可能な JAR ファイルを作成する](jar/executable-jar.html)
* [JAR ファイルの署名について](jar/jarsigner.html)
* [Zip ファイル内の要素（ファイルとディレクトリ）を列挙する](zip/enum-entries.html)

数値／文字列／ユニコード
----
* [数値と文字列の変換方法いろいろ](numstr/convert.html)
* [バイト配列から 16 進数文字列を作成する](numstr/byte-to-hex.html)
* [文字列をデリミタで分割する (String.split)](numstr/split-string.html)
* [文字列を単語単位で分割する (BreakIterator)](numstr/break-iterator.html)
* [文字列の結合には StringBuffer ではなく StringBuilder を使用する](numstr/string-builder.html)
* [小文字と大文字の変換を行う](numstr/case-conversion.html)
* [小文字と大文字の判別を行う](numstr/is-lower-case.html)
* [全角文字と半角文字を含んだ文字列を正規化する](numstr/normalize-string.html)
* [ある文字が空白文字（スペース）かどうかを判別する](numstr/is-space.html)
* [コードポイントに対応するユニコード文字 (char) を取得する](numstr/code-point-to-char.html)
* [ある文字のコードポイントに対応するユニコード名を調べる](numstr/unicode-name.html)
* [対応している文字セット (Charset) の一覧を取得する](numstr/available-charsets.html)

データベース/JDBC
----
* [JDBC ドライバの一覧を取得する](jdbc-list-drivers.html)
* [Java から SQLite を扱えるようにする](jdbc-sqlite-driver.html)
* [JDBC による DB 操作の流れ](jdbc-basic-flow.html)
* [JDBC でプレースホルダを使用して SQL クエリを作成する](jdbc-placeholder.html)
* [JDBC で最初のレコードだけを取得する](jdbc-get-first-record.html)


解析／デバッグ
----
* [コールスタックを表示する](call-stack.html)
* [Java で Linux の CPU 使用率を調べる](parse-proc-stat.html)
* [SuprressWarnings アノテーションで Checkstyle の警告を抑制する](suppress-checkstyle.html)
* [Java ビルドツールのまとめ (Ant, Maven, Gradle)](tools/build-tools.html)
* [クラスやメソッドの依存関係を調べる](tools/jdeps.html)
* [DEBUG フラグでログ出力を ON/OFF するときは、呼び出し側で if 分岐すること](debug/debug-flag.html)

その他
----
* [Javadoc コメントの書き方](../memo/how-to-write-comment.html)
* [例外のテストを記述する](test-exception.html)
* [Java7 の try with resources でストリームの close を自動的に行う](try-with-resources.html)
* [コレクションクラスのまとめ](collection/summary.html)
* [static ブロックが実行されるタイミング](static-block.html)
* [オブジェクトプールを実現するためのクラスを実装する](misc/object-pool.html)

コラム
----
* [J2SE から Java SE へ](column/j2se-to-javase.html)

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
* [Swing - コンポーネントにツールティップを表示する](swing/basic/tooltip.html)
* [Swing - ショートカットキーでフォーカスを移動する](swing/basic/move-focus-by-shortcut.html)
* [Swing - 右クリックでポップアップメニューを表示する](swing/basic/popup-menu.html)
* [Swing - ルック＆フィールを切り替える](swing/basic/change-look-and-feel.html)

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

### コンポーネント（ウィジェット）
* [Swing - JButton（ボタン）にショートカットキーを割り当てる](swing/widget/jbutton-shortcut.html)
* [Swing - JComboBox でルック＆フィールの選択肢を表示する](swing/widget/jcombobox.html)
* [Swing - JEditorPane で HTML コンテンツを表示する](swing/widget/jeditorpane1.html)
* [Swing - JEditorPane でハイパーリンク関連のイベントをハンドルする](swing/widget/jeditorpane2.html)
* [Swing - JList で項目のリストを表示する](swing/widget/jlist1.html)
* [Swing - JList で扱えるモデルクラスを作成する](swing/widget/jlist-model.html)
* [Swing - JList に動的に要素を追加／削除する](swing/widget/jlist-add-element.html)
* [Swing - JList で項目を選択したときのイベントをハンドルする](swing/widget/jlist-event.html)
* [Swing - JList で最初に表示される行数を指定する](swing/widget/jlist-init-lines.html)
* [Swing - JList で選択されている項目を取得する](swing/widget/jlist-get-selection.html)
* [Swing - JList で単一の項目のみ選択できるようにする](swing/widget/jlist-single-selection.html)
* [Swing - JSpinner を使って上下矢印で値を入力できるようにする](swing/widget/jspinner.html)
* [Swing - JTextArea で複数行のテキストを表示する](swing/widget/jtextarea-multiline.html)
* [Swing - JTextArea のテキストを画面端で折り返す](swing/widget/jtextarea-wrap.html)
* [Swing - JTextArea のテキストを編集不可にする](swing/widget/jtextarea-prohibit-edit.html)
* [Swing - JTextField のテキストを右寄せで表示する](swing/widget/jtextfield-align.html)
* [Swing - JTextField でテキストの先頭／末尾部分を強制的に表示する](swing/widget/jtextfield-scroll.html)
* [Swing - JTree でツリービューを作成する](swing/widget/jtree.html)
* [Swing - JTree に動的にノードを追加する](swing/widget/jtree-add-node.html)
* [Swing - JTree で選択されているノードを取得する](swing/widget/jtree-get-selection.html)
* [Swing - JTree でノードを選択したときのイベントをハンドルする](swing/widget/jtree-event.html)
* [Swing - JTree でルートノードを表示する](swing/widget/jtree-show-root.html)
* [Swing - JTree で一階層目のノード間に水平線を表示する](swing/widget/jtree-line.html)
* [Swing - JTree で同時に1つのノードしか選択できないようにする](swing/widget/jtree-single-selection.html)
* [Swing - JTree で指定したノードを選択する](swing/widget/jtree-select-node.html)

SWT (Standard Widget Toolkit)
----
* [SWT - AWT と Swing と SWT と JFace の違い](swt/difference.html)
* [SWT - SWT を使用するための設定](swt/settings-for-swt.html)
* [SWT - SWT アプリケーションの雛形コード](swt/skeleton-for-swt.html)
* [SWT - SWT の Layout は Composite の入れ子で構成する](swt/layout.html)
* [SWT - RowLayout でウィジェットを縦／横方向に並べる](swt/row-layout.html)
* [SWT - FillLayout でウィジェットを縦／横に等間隔に並べる](swt/fill-layout.html)
* [SWT - SashForm でウィンドウを分割して配置する](swt/sashform.html)
* [SWT - リストボックス (List) を表示する](swt/list.html)
* [SWT - ツリービュー (Tree) を表示する](swt/tree.html)
* [SWT - いろいろなダイアログ (MessageBox) を表示する](swt/dialog.html)
* [SWT - ブラウザウィジェット (Browser) で HTML を表示する](swt/browser.html)
* [SWT - SWT/JFace における色 (Color) 情報の扱い方](swt/color.html)
* [SWT - 矢印ボタンを作る](swt/arrow-button.html)
* [SWT - Canvas を使って自由に描画する](swt/canvas.html)
* [JFace を使用するための設定](swt/settings-for-jface.html)
* [JFace アプリケーションの雛形コード](swt/skeleton-for-jface.html)
* [JFace のウィンドウにウィジェットを配置する](swt/jface-create-contents.html)
* [JFace のウィンドウのタイトルを設定する](swt/jface-window-title.html)
* [JFace の ListViewer を使用する](swt/jface-list-viewer.html)
* [JFace の TreeViewer を使用する](swt/jface-tree-viewer.html)
* [JFace の TreeViewer を使ってディレクトリツリーを表示する](swt/jface-tree-viewer-dir.html)
* [階層構想を表現した RDB のデータを JFace の TreeViewer でツリー表示する](swt/jface-tree-viewer-rdb.html)
* [JFace の CheckboxTreeViewer を使用する](swt/jface-checkbox-tree-viewer.html)
* [JFace の TableViewer を使用してテーブルにデータを表示する](swt/jface-table-viewer.html)

