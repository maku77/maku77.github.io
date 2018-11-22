---
title: JavaScript
layout: category-index
---

はじめに
----
- [JavaScript のコーディングスタイル](intro/coding-style.html)
- [最初から Strict モードを有効にすべし](intro/strict-mode.html)
- [JavaScript の型の基本](intro/types.html)


静的解析ツール
----
- [JavaScript の静的解析ツールの比較 (JSLint, JSHint, ESLint)](tool/static-analysis-tools.html)
- [JSLint で JavaScript コードの静的解析を行う](tool/jslint.html)
- [JSHint で JavaScript コードの静的解析を行う](tool/jshint.html)
- [ESLint で JavaScript コードの静的解析を行う](tool/eslint.html)（オススメ）


デバッグ
----
- [デバッグ出力用の print 関数の統一について](debug/debug-print.html)
- [ある処理を 1 秒間に何回実行できるかプロファイリングする](debug/profile-function.html)


数値／文字列 (Number/String)
----
- [new String は避ける](string/dont-use-new-string.html)
- [== と === による文字列比較の違い](string/compare.html)
- [文字列を大小比較すると何が起こるか](string/compare-large-and-small.html)
- [文字列と文字列、数値を結合する](string/concat.html)
- [文字列と数値を変換する](string/convert.html)
- [文字列の小文字と大文字を変換する (toLowerCase, toUpperCase)](string/uppercase.html)
- [文字列の中から文字列を検索する (String#search, RegExp#test)](string/search.html)
- [文字列を置換する (replace)](string/replace.html)
- [文字列をデリミタで分割する／１文字ずつに分割する (split)](string/split.html)
- [文字列から正規表現パターンに一致する部分を取り出す (String#match, RegExp#exec)](string/match.html)
- [文字列の先頭と末尾の空白を削除する ($.trim)](string/trim.html)
- [文字列の長さを取得する (length)](string/length.html)
- [部分文字列を取得する (substring, slice)](string/substring.html)
- [文字列内の１文字を取得する／１文字ずつループ処理する (charAt)](string/char-at.html)
- [テンプレートを使ってテキストを生成する (Underscore.js)](string/template.html)

### 数値
- [実数の小数点数以下を丸めて整数に変換する](numstr/real-to-int.html)


配列 (Array)
----
- [配列の基本](array/basic.html)
- [配列に要素を追加・挿入する (push, unshift, splice)](array/push.html)
- [配列と配列を結合する (concat)](array/concat.html)
- [配列から部分配列を取得する (slice, splice)](array/slice.html)
- [配列をコピーする](array/copy.html)
- [配列をループで処理する](array/loop.html)
- [配列をソートする (sort, reverse)](array/sort.html)
- [配列内にある要素が存在するか調べる](array/has.html)
- [配列の長さを切り詰める (length, splice)](array/cut.html)
- [連想配列の要素（キー）を削除する (delete)](array/delete.html)
- [連想配列の要素数を調べる](array/size-of-assoc-array.html)
- [配列要素をランダムで取り出す](array/random.html)
- [配列の各要素に対して同じ処理を行う (map)](array/map.html)


日時／タイマー (Date/Timer)
----

### 日時
- [Date オブジェクト（日時情報）の作成方法いろいろ](time/create-date.html)
- [Date オブジェクトをいろいろな文字列表現に変換する](time/date-to-string.html)
- [Date オブジェクトの日付を加算／減算する](time/date-calculation.html)
- [Date オブジェクト同士の差分を取る](time/date-diff.html)

### タイマー／パフォーマンス
- [タイマーで任意の関数を呼び出す](time/timer.html)
- [精度の高いタイムスタンプを取得する (performance.now())](time/performance-now.html)
- [処理速度を計測する (performance)](time/measure-performance.html)


構文
----

### 関数
- [関数を定義する](syntax/define-function.html)
- [デフォルト引数を扱う関数を定義する](syntax/default-param.html)
- [可変長引数を扱う関数を定義する (arguments)](syntax/variable-length-argument.html)
- [関数の仮引数の数を取得する](syntax/aug-length.html)
- [渡されたパラメータが関数かどうか調べる](syntax/check-if-parameter-is-function.html)
- [関数オブジェクトから関数名を取得する](syntax/get-func-name.html)
- [アロー関数で関数を定義する](syntax/arrow-function.html)

### 名前空間
- [JavaScript ではグローバル変数も関数も全てプロパティ](syntax/global-object.html)
- [関数内からしか参照できないプライベート関数を定義する](syntax/private-scope-function.html)

### オブジェクト／クラス
- [オブジェクトの基本](object/basic.html)
- [オブジェクトのプロパティを列挙する (for-in)](object/enum-properties.html)
- [２つのオブジェクトのプロパティをマージする](object/merge-properties.html)
- [クラス定数／クラス変数／クラスメソッドを定義する](object/class-const.html)
- [あるオブジェクトが特定のクラスのインスタンスであるかを調べる (instanceof, constructor)](object/instanceof.html)
- [クラスに toString() メソッドを実装する](object/to-string.html)

### JavaScript 独自拡張
- [JavaScript で定数を定義する (const)](syntax/const.html)
- [式クロージャ (Expression Closure)](syntax/expression-closure.html)

### その他
- [JavaScript において偽 (false) と評価される値](syntax/false-values.html)
- [入れ子になったループを一度に抜ける](syntax/break-nested-loop.html)
- [ある変数が初期化済みかどうか確認する](misc/check-if-undefined.html)
- [外部スクリプトはなぜ (function(){})(); のような書き方をするのか？](misc/scope-of-library.html)


HTML/DOM 操作
----

### 要素

- [HTML の DOM 要素を取得する (1) タグ名、クラス名、ID を検索](dom/get-element1.html)
- [HTML の DOM 要素を取得する (2) 親要素、子要素、兄弟要素を検索](dom/get-element2.html)
- [HTML の DOM ツリーに要素を追加する](dom/add-element.html)
- [HTML 要素に独自の data 属性（カスタム属性）を設定する](dom/data-attribute.html)
- [HTML 要素の class 属性の値を追加・削除・トグルする](dom/control-class-attribute.html)
- [ページ内の h2 要素から自動的にメニュー (TOC) を生成する](dom/table-of-contents.html)
- [HTML 要素のサイズを取得する](dom/elem-size.html)
- [HTML 要素の位置を取得する](dom/elem-pos.html)

### ウィンドウ

- [Web ブラウザのページ表示位置を調べる（スクロール位置を取得する）](window/scroll-pos.html)


画像／イメージ
----
- [複数の画像ファイルが読み込まれるのを待機する](image/onload.html)


音声/オーディオ
----
- [オーディオファイル (mp3) を再生する](audio/play.html)


I/O（入出力）
----
- [キーボードからの入力を取得する](io/keyboard.html)
- [テキストボックスでキー入力したときのイベントをハンドルする (onkeyup)](io/textbox-key-event.html)
- [ボタンを押したときのイベントをハンドルする (onclick)](io/button-onclick.html)
- [プルダウンリストから項目を選択したときのイベントをハンドルする (onchange)](io/pulldown-onchange.html)

jQuery
----
- [jQuery 本体のロード方法いろいろ](jquery/load-jquery.html)
- [jQuery 関連用語](jquery/terms.html)
- [jQuery で Hello World](jquery/hello-world.html)
- [jQuery で要素を追加、移動、コピーする (append, prepend)](jquery/add-element.html)
  - [jQuery で動的にリスト項目 (li) を追加する](jquery/add-list-item.html)
  - [jQuery でテーブルに動的にレコード (tr) を追加する](jquery/add-table-record.html)
- [jQuery で要素を削除する (remove)](jquery/remove-element.html)
- [jQuery の ajax/get/post メソッドが動作しない](jquery/trouble-ajax-get-post.html)
- [jQuery でフォームに入力した値を取得する](jquery/form.html)

HTML5 Canvas
----
- [HTML5 Canvas のサイズ指定について](canvas/size.html)
- [HTML5 Canvas でテキストを描画する](canvas/text.html)
- [HTML5 Canvas に矩形や円を描画する](canvas/draw-rect-circle.html)
- [HTML5 Canvas に画像ファイルを描画する](canvas/draw-image-file.html)
- [HTML5 Canvas 内のマウスドラッグで、描画オブジェクト全体を移動させる](canvas/drag-object.html)
- [HTML5 Canvas のまわりにテレビの枠画像を表示してそれっぽく見せる](canvas/tv-frame.html)
- [ウィンドウサイズに応じて HTML5 Canvas のサイズを変更する](canvas/auto-resize.html)
- [HTML5 Canvas の描画領域全体をクリアする](canvas/clear.html)
- [IE 8 以下でも canvas タグを使用できるようにする](canvas/ie8.html)

Node.js
----
- [Node.js 用モジュールの作り方（require でロード可能な Node モジュールを作成する）](node/create-node-module.html)

Web サイト
----
- [URL 内のハッシュフラグメントの変化を検出する (hashchange)](web/detect-fragment-change.html)


実行環境
----
- [CORS - Cross-Origin Resource Sharing とは？](env/cors.html)
- [Chrome で Ajax (XMLHttpRequest) によるクロスドメイン通信の制約をなくす](env/disable-web-security-of-chrome.html)

