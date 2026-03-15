---
title: "Java"
url: "/java/"

categoryName: "まくまく Java ノート"
categoryUrl: "/java/"
categoryIcon: _index.svg
---

アノテーション
----
* [アノテーションとは](/p/su4opzc/)
* [Java でカスタムアノテーションを作成する](/p/nvwk62g/)
* [Javadoc でアノテーションのサンプルコードを記述するときの &#064; の扱い](/p/6p4qghv/)

マルチスレッド
----
* [Executor によるタスク処理 (1) `Executor` インタフェースを使用する](/p/6ru2qdt/)
* [Executor によるタスク処理 (2) `ExecutorService` インタフェースで `Executor` を停止する](/p/u89pswg/)
* [Executor によるタスク処理 (3) `Future` オブジェクトによりタスクの実行結果を取得する](/p/po4fvgr/)
* [Executor によるタスク処理 (4) 既存の `Thread` から `Future` オブジェクトを取得する](/p/k2hc592/)
* [Executor によるタスク処理 (5) `ScheduledExecutorService` によるタスクのスケジュール](/p/paxoatq/)
* [`BlockingQueue` を使ってスレッド間の通信を行う](/p/gz5c3cx/)
* [`Lock` と `Condition` による条件ごとのスレッド待機](/p/h5y2spb/)
* [`TimeUnit` によって単位時間を明確にする](/p/k35ktt4/)
* [スレッドで発生した Uncaught Exception（未捕捉例外）をハンドルする](/p/x5d4bpz/)
* [Java のオブジェクトロックは再入可能であることを理解する](/p/y6mtqir/)
* [`Boolean` 型で `synchronized` するのは NG](/p/f9tnr9m/)

ファイル
----

### JAR/ZIP
* [`jar` コマンドで JAR ライブラリを作成する](/p/gxtngpp/)
* [`jar` コマンドで実行可能な JAR ファイルを作成する](/p/24t3vaq/)
* [JAR ファイルの署名について](/p/fht5eox/)
* [ZIP ファイル内の要素（ファイルとディレクトリ）を列挙する](/p/46p2ir5/)

### XML
* [DOM プログラミング - XML のルート要素を取得する](/p/wxhedjx/)
* [DOM プログラミング - 子要素のテキストノードの値を取得する](/p/6n8pbn8/)
* [DOM プログラミング - 同じタグ名を持つ全ての子要素をループ処理する](/p/63zzwy6/)

### ファイル一般
* [Properties ファイルから設定値を読み込む](/p/yq4ro44/)
* [`MANIFEST.MF` ファイルの内容を取得する](/p/mbmxnx9/)

数値／文字列／ユニコード
----

* [数値と文字列の変換方法いろいろ](/p/z78jmj9/)
* [バイト配列から 16 進数文字列を作成する](/p/338cgk8/)
* [文字列をデリミタで分割する (`String.split`)](/p/jurhvh9/)
* [CSV 形式の文字列を配列に分割する (`String.split`, `Pattern.split`)](/p/jqdm99y/)
* [文字列を単語単位で分割する (`BreakIterator`)](/p/6qoqz6u/)
* [文字列の結合には `StringBuffer` ではなく `StringBuilder` を使用する](/p/f52ckrm/)
* [小文字と大文字の変換を行う (`toUpperCase`, `toLowerCase`)](/p/gxnjhq4/)
* [小文字と大文字の判別を行う (`isLowerCase`, `isUpperCase`)](/p/97r9p79/)
* [ある文字が空白文字（スペース）かどうかを判別する](/p/o24kwj6/)
* [全角文字と半角文字を含んだ文字列を正規化して表記ゆれを吸収する (`java.text.Normalizer`)](/p/j3jbsey/)
* [コードポイントに対応するユニコード文字 (`char`) を取得する](/p/9srjnk4/)
* [ある文字のコードポイントに対応するユニコード名を調べる](/p/zeyf67t/)
* [対応している文字セット (`Charset`) の一覧を取得する](/p/o2o3f5v/)
* [文字列が正規表現に完全に一致するか調べる (`String.matches`)](/p/zanxxvq/)
* [文字列の一部が正規表現に一致するか調べ、一致した部分をグループごとに抜き出す (`Pattern.matcher`)](/p/qaicq3j/)
* [文字列の正規表現に一致した部分を置き換える (`String.replaceAll`)](/p/cookya8/)
* [複数行のテキストを含んだ `String` を一行ずつ処理する](/p/ka564ww/)

### 組み合わせ

* [順列 (permutation) を作成する](/p/ou6ra65/)


データベース/JDBC
----
* [JDBC ドライバの一覧を取得する](/p/c96fmw7/)
* [Java から SQLite を扱えるようにする](/p/tb479oj/)
* [JDBC による DB 操作の流れ](/p/62fx45h/)
* [JDBC でプレースホルダを使用して SQL クエリを作成する](/p/cmjwu3p/)
* [JDBC で最初のレコードだけを取得する](/p/nj5v9bp/)


解析／デバッグ
----
* [コールスタックを表示する (`Throwable#getStackTrace`)](/p/uep5gr9/)
* [Java で Linux の CPU 使用率を調べる](/p/kgnf5i4/)
* [`SuppressWarnings` アノテーションで Checkstyle の警告を抑制する](/p/qg4y36v/)
* [Java ビルドツールのまとめ (Ant, Maven, Gradle)](/p/xxwvumf/)
* [クラスやメソッドの依存関係を調べる (jdeps, cfa)](/p/mpqhkpv/)
* [DEBUG フラグでログ出力を ON/OFF するときは、呼び出し側で `if` 分岐すること](/p/q42s87s/)

その他
----
* [Javadoc コメントの書き方](/p/fiw27s4/)
* [例外のテストを記述する](/p/oj2psba/)
* [Java 7 の try-with-resources でストリームの `close` を自動的に行う](/p/j3oguy4/)
* [コレクションクラスのまとめ](/p/djcjgem/)
* [`static` ブロックが実行されるタイミング](/p/eu3hut6/)
* [シングルトンクラスの Lazy Loading イディオム](/p/i3kp2o6/)
* [オブジェクトプールを実現するためのクラスを実装する](/p/ytcj4de/)
* [JNI (Java Native Interface) の基本](/p/bdtggsp/)
* [Java でランダムな UUID を生成する (`java.util.UUID`)](/p/uwatzo2/)
* [`enum` の各項目に任意のデータを関連付ける](/p/n8c5cyp/)
* [既定の Web ブラウザで URL を開く](/p/6fw8xon/)

コラム
----
* [J2SE から Java SE へ](/p/6peeowz/)
* [`System.out.println()` でオブジェクトを出力するときは `toString()` しない](/p/uarsa4x/)

Effective Java や Java の鉄則など
----
1. [コンストラクタの代わりに static ファクトリメソッドを検討する](/p/p9ehn47/)
1. [数多くのコンストラクタパラメータに直面したときにはビルダーを検討する](/p/zcjg874/)
1. [private のコンストラクタか enum 型でシングルトン特性を強制する](/p/yewv23z/)
1. [private のコンストラクタでインスタンス化不可能を強制する](/p/mwriemy/)
1. [不必要なオブジェクトの生成を避ける](/p/694hck7/)
1. [廃れたオブジェクト参照を取り除く](/p/frvttgs/)
1. [ファイナライザを避ける](/p/exd2yyx/)
1. [`equals` をオーバーライドする時は一般契約に従う](/p/exiwzrf/)
1. [`equals` をオーバーライドする時は常に `hashCode` をオーバーライドする](/p/ahoova6/)
1. [`toString` を常にオーバーライドする](/p/d98hgy9/)
1. [`clone` を注意してオーバーライドする](/p/rtsoitj/)
1. [`Comparable` の実装を検討する](/p/prkp5wi/)

* [コンストラクタからオーバーライド可能なメソッドを呼び出さない](/p/p2mvf2b/)

国際化
----
* [Java アプリケーションの国際化 (i18n) と `Locale` クラス](/p/3jj68m7/)
* [`Locale` が変わったときに変化すべき表示項目](/p/puy6ye8/)

Swing
----

* 基本
  * [Swing で Hello World](/p/8hyqz4q/)
  * [Swing - `Container` と `JComponent` と `JFrame` の関係](/p/wkaidfg/)
  * [Swing - `JPanel` に GUI コンポーネントを配置する](/p/j27xwvj/)
  * [Swing - 標準的なダイアログいろいろ](/p/698zhpz/)
  * [Swing - OK ボタンと Cancel ボタンのあるダイアログを作る](/p/64mwkpq/)
  * [Swing - `JDialog` のウィンドウサイズを変更できるようにする](/p/utkwe8j/)
  * [Swing - スプリッターを配置してウィンドウを分割する](/p/c4ecax2/)
  * [Swing - スプリッターを折り畳み可能にする](/p/5sd7u5a/)
  * [Swing - スプリッターの分割方向を動的に変更する](/p/hp76jeg/)
  * [Swing - コンポーネントにツールティップを表示する](/p/wfrdpzt/)
  * [Swing - ショートカットキーでフォーカスを移動する](/p/pxomauz/)
  * [Swing - 右クリックでポップアップメニューを表示する](/p/y9rx3hh/)
  * [Swing - ルック＆フィールを切り替える](/p/mbcbow4/)
* レイアウト
  * [Swing - レイアウトマネージャの種類](/p/rfr27x9/)
  * [Swing - デフォルトのレイアウトマネージャについて](/p/5rvhyzn/)
  * [Swing - `BorderLayout` でコンポーネントを上下左右中央に並べる](/p/fk4i3yy/)
  * [Swing - `BorderLayout` で中央に配置するコンポーネントのデフォルトサイズを設定する](/p/6oh7few/)
  * [Swing - `BorderLayout` でコンポーネント間のスペースを設定する](/p/qvxkpuj/)
  * [Swing - `BoxLayout` でコンポーネントを縦方向、横方向に並べる](/p/qepm239/)
  * [Swing - `BoxLayout` で左寄せ／中央寄せ／右寄せ表示する](/p/jjujyf9/)
  * [Swing - `FlowLayout` でコンポーネントを左上から並べる](/p/x9smwmt/)
  * [Swing - `FlowLayout` でコンポーネントが複数行表示されるときに左寄せ／右寄せする](/p/ckpnxmt/)
* コンポーネント（ウィジェット）
  * [Swing - `JButton`（ボタン）にショートカットキーを割り当てる](/p/7oqiiz4/)
  * [Swing - `JComboBox` でルック＆フィールの選択肢を表示する](/p/ugtz6rw/)
  * [Swing - `JEditorPane` で HTML コンテンツを表示する](/p/nvyjnkj/)
  * [Swing - `JEditorPane` で画像ファイルを表示する](/p/7gihjdi/)
  * [Swing - `JEditorPane` にスタイルシート (CSS) を設定する](/p/xdn79kz/)
  * [Swing - `JEditorPane` でハイパーリンク関連のイベントをハンドルする](/p/z95kz63/)
  * [Swing - `JEditorPane` で先頭部分を表示する](/p/pur7zs5/)
  * [Swing - `JList` で項目のリストを表示する](/p/gbyupmw/)
  * [Swing - `JList` で扱えるモデルクラスを作成する](/p/hcngbie/)
  * [Swing - `JList` に動的に要素を追加／削除する](/p/9czfb5x/)
  * [Swing - `JList` で項目を選択したときのイベントをハンドルする](/p/ccxiosj/)
  * [Swing - `JList` で最初に表示される行数を指定する](/p/mrwfos6/)
  * [Swing - `JList` で選択されている項目を取得する](/p/jodffpp/)
  * [Swing - `JList` で単一の項目のみ選択できるようにする](/p/ot34aii/)
  * [Swing - `JList` で最後の項目を選択する](/p/grodjd9/)
  * [Swing - `JList` でリスト項目としてチェックボックスを表示する](/p/4y6imuv/)
  * [Swing - `JSpinner` を使って上下矢印で値を入力できるようにする](/p/mf97fjz/)
  * [Swing - `JTextArea` で複数行のテキストを表示する](/p/vvtvhiy/)
  * [Swing - `JTextArea` のテキストを画面端で折り返す](/p/8rkka7w/)
  * [Swing - `JTextArea` のテキストを編集不可にする](/p/j9pq4vu/)
  * [Swing - `JTextArea` に表示するフォントを設定する](/p/95grovt/)
  * [Swing - `JTextArea` でテキストの先頭部分を表示する](/p/gy6njyy/)
  * [Swing - `JTextField` で Enter キーを押したときにテキストを取得する](/p/6tebgt3/)
  * [Swing - `JTextField` で Esc キーを押したときにテキストをクリアする](/p/rczcgu3/)
  * [Swing - `JTextField` のテキストを右寄せで表示する](/p/tdpgxvj/)
  * [Swing - `JTextField` でテキストの先頭／末尾部分を強制的に表示する](/p/wv5hb9i/)
  * [Swing - `JTree` でツリービューを作成する](/p/p8zwibx/)
  * [Swing - `JTree` に動的にノードを追加する](/p/q8ynipi/)
  * [Swing - `JTree` で選択されているノードを取得する](/p/2fou9pa/)
  * [Swing - `JTree` でノードを選択したときのイベントをハンドルする](/p/cozmf9p/)
  * [Swing - `JTree` でルートノードを表示する](/p/xdnjok3/)
  * [Swing - `JTree` で一階層目のノード間に水平線を表示する](/p/9pgpxzk/)
  * [Swing - `JTree` で同時に 1 つのノードしか選択できないようにする](/p/yqoe4b6/)
  * [Swing - `JTree` で指定したノードを選択する](/p/ody2qf8/)

SWT (Standard Widget Toolkit)
----
* [SWT - AWT と Swing と SWT と JFace の違い](/p/sbah5o3/)
* [SWT - SWT を使用するための設定](/p/oc4s6nu/)
* [SWT - Mac で SWT を使用する](/p/w6zf87p/)
* [SWT - SWT アプリケーションの雛形コード](/p/xv2cni2/)
* [SWT - SWT の `Layout` は `Composite` の入れ子で構成する](/p/cncbo9p/)
* [SWT - `RowLayout` でウィジェットを縦／横方向に並べる](/p/djpv6ye/)
* [SWT - `FillLayout` でウィジェットを縦／横に等間隔に並べる](/p/65pnvmd/)
* [SWT - `SashForm` でウィンドウを分割して配置する](/p/oas7dyd/)
* [SWT - リストボックス (`List`) を表示する](/p/3b54rxa/)
* [SWT - ツリービュー (`Tree`) を表示する](/p/aiyo24q/)
* [SWT - いろいろなダイアログ (`MessageBox`) を表示する](/p/8xtar7h/)
* [SWT - ブラウザウィジェット (`Browser`) で HTML を表示する](/p/7tfudc3/)
* [SWT - SWT/JFace における色 (`Color`) 情報の扱い方](/p/szozke9/)
* [SWT - 矢印ボタンを作る](/p/y9n56tn/)
* [SWT - `Canvas` を使って自由に描画する](/p/v6qfxxa/)
* [SWT - Cannot load 32-bit SWT libraries エラーが発生する場合](/p/syh2mkk/)
* [JFace を使用するための設定](/p/n6pq7am/)
* [JFace アプリケーションの雛形コード](/p/6g22sgg/)
* [JFace のウィンドウにウィジェットを配置する](/p/u54x752/)
* [JFace のウィンドウのタイトルを設定する](/p/4yp5qci/)
* [JFace の `ListViewer` を使用する](/p/fnyfiax/)
* [JFace の `TreeViewer` を使用する](/p/9m37xcn/)
* [JFace の `TreeViewer` を使ってディレクトリツリーを表示する](/p/abwakr5/)
* [階層構造を表現した RDB のデータを JFace の `TreeViewer` でツリー表示する](/p/5yk6o5v/)
* [JFace の `CheckboxTreeViewer` を使用する](/p/x36umj7/)
* [JFace の `TableViewer` を使用してテーブルにデータを表示する](/p/3gzw65v/)

トラブルシューティング
----
* [CLASSPATH 関連のエラーに対処する](/p/9aqyky2/)
* [Mac で `javac` の出力が文字化けする](/p/c5qe42u/)
