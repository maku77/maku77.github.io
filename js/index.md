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


文字列／数値
----
- [new String は避ける](string/dont-use-new-string.html)
- [== と === による文字列比較の違い](string/compare.html)
- [文字列を大小比較すると何が起こるか](string/compare-large-and-small.html)
- [文字列と文字列、数値を結合する](string/concat.html)
- [文字列と数値を変換する](string/convert.html)
- [文字列を置換する (replace)](string/replace.html)
- [文字列をデリミタで分割する／１文字ずつに分割する (split)](string/split.html)
- [文字列の先頭と末尾の空白を削除する ($.trim)](string/trim.html)
- [文字列の長さを取得する (length)](string/length.html)
- [部分文字列を取得する (substring, slice)](string/substring.html)
- [文字列内の１文字を取得する／１文字ずつループ処理する (charAt)](string/char-at.html)
- [テンプレートを使ってテキストを生成する (Underscore.js)](string/template.html)


配列
----
- [配列の基本](array/basic.html)
- [配列をループで処理する](array/loop.html)
- [配列をソートする](array/sort.html)
- [配列をコピーする](array/copy.html)
- [配列内にある要素が存在するか調べる](array/has.html)
- [配列要素をランダムで取り出す](array/random.html)


時刻情報／タイマー
----
- [タイマーで任意の関数を呼び出す](time/timer.html)


構文
----

### 関数
- [関数を定義する](syntax/define-function.html)
- [デフォルト引数を扱う関数を定義する](syntax/default-param.html)
- [可変長引数を扱う関数を定義する (arguments)](syntax/variable-length-argument.html)
- [渡されたパラメータが関数かどうか調べる](syntax/check-if-parameter-is-function.html)
- [関数オブジェクトから関数名を取得する](syntax/get-func-name.html)

### 名前空間
- [JavaScript ではグローバル変数も関数も全てプロパティ](syntax/global-object.html)
- [関数内からしか参照できないプライベート関数を定義する](syntax/private-scope-function.html)

### オブジェクト／クラス
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
- [HTML の DOM 要素を取得する (1) タグ名、クラス名、ID を検索](dom/get-element1.html)
- [HTML の DOM 要素を取得する (2) 親要素、子要素、兄弟要素を検索](dom/get-element2.html)
- [HTML 要素に独自の data 属性（カスタム属性）を設定する](dom/data-attribute.html)
- [HTML 要素の class 属性の値を追加・削除・トグルする](dom/control-class-attribute.html)
- [ページ内の h2 要素から自動的にメニュー (TOC) を生成する](dom/table-of-contents.html)

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
- [HTML5 Canvas によるテキストの描画サイズを調べる](canvas/measure-text.html)
- [HTML5 Canvas に矩形や円を描画する](canvas/draw-rect-circle.html)
- [HTML5 Canvas に画像ファイルを描画する](canvas/draw-image-file.html)
- [HTML5 Canvas 内のマウスドラッグで、描画オブジェクト全体を移動させる](canvas/drag-object.html)
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

