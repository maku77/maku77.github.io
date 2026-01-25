---
title: "JavaScript"
url: "/js/"
date: "2023-12-01"

categoryName: "まくまく JavaScript ノート"
categoryUrl: "/js/"
categoryIcon: logo-javascript.svg
---

はじめに
----
- [JavaScript のコーディングスタイル](/p/pgw5j96/)
- [最初から Strict モードを有効にすべし (ECMAScript 5)](/p/bxrtpbp/)
- [JavaScript の 6 つの型](/p/wur8gmp/)


静的解析ツール
----
- [（旧）JavaScript の静的解析ツールの比較 (JSLint, JSHint, ESLint)](/p/ufcmoxr/)
- [（旧）JSLint で JavaScript コードの静的解析を行う](/p/os3jvi6/)
- [（旧）JSHint で JavaScript コードの静的解析を行う](/p/y7o9g7a/)
- [ESLint で JavaScript コードの静的解析を行う（オススメ）](/p/s8i5cr9/)


デバッグ
----
- [デバッグ出力用の print 関数の統一について](/p/r5vogb3/)
- [ある処理を 1 秒間に何回実行できるかプロファイリングする](/p/zy772in/)


数値／文字列 (Number/String) <!-- numstr -->
----

### 数値

- [実数の小数点以下の切り上げ、切り捨て、整数への変換 (`floor`, `ceil`, `round`, `toFixed`)](/p/92v7zx8/)

### 文字列

{{% private %}}
- [JavaScript の文字列処理チートシート](/p/y4ccxc8/)
{{% /private %}}

- [`new String` は避ける](string/dont-use-new-string.html)
- [テンプレート文字列の機能で文字列リテラル中の変数を展開する (template literal) (ECMAScript 2015)](string/template-literal.html)
- [`==` と `===` による文字列比較の違い](string/compare.html)
- [文字列を大小比較すると何が起こるか](string/compare-large-and-small.html)
- [文字列と文字列、数値を結合する](string/concat.html)
- [文字列と数値を変換する](string/convert.html)
- [文字列の小文字と大文字を変換する (`toLowerCase`, `toUpperCase`)](string/uppercase.html)
- 検索
  - [文字列の中から文字列を検索する (`String#search`, `RegExp#test`)](/p/p5nx3n9/)
  - [文字列内に NG ワード（禁止語句）が含まれていないか調べる (`RegExp#test`)](string/ng-word.html)
- 分割／抽出
  - [文字列をデリミタで分割する／1文字ずつに分割する (`String#split`)](/p/dpp4v8n/)
  - [文字列から正規表現パターンに一致する部分を取り出す (`String#match`, `RegExp#exec`)](/p/tvuztbm/)
- 置換
  - [文字列を置換する (`String#replace`, `String#replaceAll`)](/p/8pnuzk4/)
- 編集
  - [文字列の先頭と末尾の空白を削除する (`String#trim`) (ECMAScript 2015)](string/trim.html)
  - [文字列を指定の長さになるまでパディング（埋め合わせ）する (`String#padStart`, `#String#padEnd`)](/p/buatano/)
- [文字列の長さを取得する (`length`)](string/length.html)
- [部分文字列を取得する (`substring`, `slice`)](string/substring.html)
- [文字列内の1文字を取得する／1文字ずつループ処理する (`charAt`)](string/char-at.html)
- [全角文字と半角文字を含んだ文字列を正規化して表記ゆれを吸収する (normalize) (ECMAScript2015)](string/normalize.html)
- [文字列内のひらがなとカタカナを変換する](string/convert-kana.html)
- 旧記事
  - [テンプレートを使ってテキストを生成する (Underscore.js)](string/template.html)


配列／連想配列 (Array)
----
- [配列の基本](array/basic.html)
- [配列に要素を追加・挿入する (push, unshift, splice)](array/push.html)
- [配列と配列を結合する (...スプレッド演算子, concat)](array/concat.html)
- [配列から部分配列を取得する (slice, splice)](array/slice.html)
- [配列をコピーする](array/copy.html)
- [配列をループで処理する (for, forEach, for-of)](array/loop.html)
- [配列をソートする (sort, reverse)](array/sort.html)
- [配列にある要素が存在するか調べる (includes, indexOf)](array/has.html)
- [配列の長さを切り詰める (length, splice)](array/cut.html)
- [連想配列にキーが存在するか調べる (in)](array/in.html)
- [連想配列の要素（キー）を削除する (delete)](array/delete.html)
- [連想配列の要素数を調べる](array/size-of-assoc-array.html)
- [配列要素をランダムで取り出す](array/random.html)
- [配列の各要素に対して同じ処理を行う (map)](array/map.html)
- [配列の分割代入で複数の値を同時に代入する (Array destructuring)](array/destructuring.html)


日時／タイマー (Date/Timer)
----

### 日時
- [Date オブジェクト（日時情報）の作成方法いろいろ](time/create-date.html)
- [Date オブジェクトをいろいろな文字列表現に変換する](time/date-to-string.html)
- [他の国のローカルタイム（現地時刻）を文字列形式で取得する](time/other-country-time.html)
- [Date オブジェクトの日付を加算／減算する](time/date-calculation.html)
- [Date オブジェクト同士の差分を取る](time/date-diff.html)

### タイマー／パフォーマンス
- [タイマーで任意の関数を呼び出す (setTimeout, setInterval)](time/timer.html)
- [精度の高いタイムスタンプを取得する (performance.now())](time/performance-now.html)
- [処理速度を計測する (performance)](time/measure-performance.html)


構文
----

### 関数
- [関数を定義する](syntax/define-function.html)
- [デフォルト引数を扱う関数を定義する (Default parameters) (ECMAScript 2015)](syntax/default-param.html)
- [可変長引数を扱う関数を定義する (arguments)](syntax/variable-length-argument.html)
- [関数の仮引数の数を取得する](syntax/aug-length.html)
- [渡されたパラメータが関数かどうか調べる](syntax/check-if-parameter-is-function.html)
- [関数オブジェクトから関数名を取得する](syntax/get-func-name.html)
- [アロー関数で関数を定義する (ECMAScript 2015)](syntax/arrow-function.html)
- [`yield` を使ってイテレート可能な関数（ジェネレーター関数）を定義する](/p/q3a7jg4/)

### 変数／定数／名前空間
- [変数定義には `var` ではなく `let` や `const` を使用する (ECMAScript 2015)](syntax/let-const.html)
- [JavaScript で定数を定義する (const)](syntax/const.html)
- [JavaScript ではグローバル変数も関数も全てプロパティ](syntax/global-object.html)
- [関数内からしか参照できないプライベート関数を定義する](syntax/private-scope-function.html)
- 分割代入 (Destructuring assignment)
    - [分割代入によりオブジェクトの特定のプロパティだけを単独変数に取得する (Object destructuring) (ECMAScript 2015)](syntax/object-destructuring.html)

### オブジェクト
- [オブジェクトの基本](object/basic.html)
- [オブジェクトのプロパティを列挙する (for-in)](object/enum-properties.html)
- [2つのオブジェクトのプロパティをマージする（...スプレッド演算子、Object.assign）](object/merge-properties.html)
- [Property Shorthand の記法で複数の変数をひとつのオブジェクトにまとめる (ECMAScript 2015)](object/property-shorthand.html)

### クラス
- [クラスを定義する (class) (ECMAScript 2015)](class/class.html)
- [クラス定数／クラス変数／クラスメソッドを定義する](object/class-const.html)
- [あるオブジェクトが特定のクラスのインスタンスであるかを調べる (instanceof, constructor)](object/instanceof.html)
- [クラスに toString() メソッドを実装する](object/to-string.html)

### JavaScript 独自拡張
- [式クロージャ (Expression Closure)](syntax/expression-closure.html)

### その他
- [JavaScript において偽 (false) と評価される値](syntax/false-values.html)
- [入れ子になったループを一度に抜ける（多重ループからの break）](syntax/break-nested-loop.html)
- [ある変数が初期化済みかどうか確認する](misc/check-if-undefined.html)
- [外部スクリプトはなぜ (function(){})(); のような書き方をするのか？](misc/scope-of-library.html)


非同期処理
----
- [Promise オブジェクトで連続するコールバック処理を簡潔に記述する (ECMAScript 2015)](async/promise.html)
- [Promise な非同期処理を async/await でさらに読みやすくする (ECMAScript 2017)](async/async-await.html)


HTML/DOM 操作
----

### 要素

- [HTML の DOM 要素を取得する (1) タグ名、クラス名、ID を検索](/p/on7omgt/)
- [HTML の DOM 要素を取得する (2) 親要素、子要素、兄弟要素を検索](dom/get-element2.html)
- [HTML の DOM ツリーに要素を追加する](dom/add-element.html)
- [HTML 要素に独自の data 属性（カスタム属性）を設定する](dom/data-attribute.html)
- [HTML 要素の class 属性の値を追加・削除・トグルする](dom/control-class-attribute.html)
- [ページ内のヘッダ要素 (h2/h3/h4) から自動的にメニュー (TOC) を生成する](dom/table-of-contents.html)
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

レガシーライブラリ
----
- jQuery
  - [jQuery 本体のロード方法いろいろ](jquery/load-jquery.html)
  - [jQuery 関連用語](jquery/terms.html)
  - [jQuery で Hello World](jquery/hello-world.html)
  - [jQuery で要素を追加、移動、コピーする (append, prepend)](jquery/add-element.html)
    - [jQuery で動的にリスト項目 (li) を追加する](jquery/add-list-item.html)
    - [jQuery でテーブルに動的にレコード (tr) を追加する](jquery/add-table-record.html)
  - [jQuery で要素を削除する (remove)](jquery/remove-element.html)
  - [jQuery の ajax/get/post メソッドが動作しない](jquery/trouble-ajax-get-post.html)
  - [jQuery でフォームに入力した値を取得する](jquery/form.html)
- [Backbone.js の使い方メモ](/p/wobqnsc/)
- [RequireJS の使い方メモ](/p/nouqw33/)


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


Web サイト
----
- [URL エンコード／デコードを行う (encodeURI, encodeURIComponent)](web/encode-uri.html)
- [URL 内のハッシュフラグメントの値を扱う (hashchange, location.hash)](web/detect-fragment-change.html)
- [URL からクエリ文字列を取り出す (window.location.search, URLSearchParams)](web/search-params.html)


実行環境
----
- [CORS - Cross-Origin Resource Sharing とは？](env/cors.html)
- [Chrome で Ajax (XMLHttpRequest) によるクロスドメイン通信の制約をなくす](env/disable-web-security-of-chrome.html)
- [使用している Web ブラウザが IE (Internet Explorer) かどうか判別する](env/is-ie.html)


Node.js
----
- [⇒ まくまく Node.js ノート](/nodejs/)

