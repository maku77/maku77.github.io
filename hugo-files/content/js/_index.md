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

- [`new String` は避ける](/p/is2iir3/)
- [テンプレート文字列の機能で文字列リテラル中の変数を展開する (template literal) (ECMAScript 2015)](/p/p3fzkd7/)
- [`==` と `===` による文字列比較の違い](/p/squitgq/)
- [文字列を大小比較すると何が起こるか](/p/iakd4aw/)
- [文字列と文字列、数値を結合する](/p/egfww25/)
- [文字列と数値を変換する](/p/upvd655/)
- [文字列の小文字と大文字を変換する (`toLowerCase`, `toUpperCase`)](/p/xbyztsb/)
- 検索
  - [文字列の中から文字列を検索する (`String#search`, `RegExp#test`)](/p/p5nx3n9/)
  - [文字列内に NG ワード（禁止語句）が含まれていないか調べる (`RegExp#test`)](/p/fg35hk8/)
- 分割／抽出
  - [文字列をデリミタで分割する／1文字ずつに分割する (`String#split`)](/p/dpp4v8n/)
  - [文字列から正規表現パターンに一致する部分を取り出す (`String#match`, `RegExp#exec`)](/p/tvuztbm/)
- 置換
  - [文字列を置換する (`String#replace`, `String#replaceAll`)](/p/8pnuzk4/)
- 編集
  - [文字列の先頭と末尾の空白を削除する (`String#trim`) (ECMAScript 2015)](/p/hpvmzdu/)
  - [文字列を指定の長さになるまでパディング（埋め合わせ）する (`String#padStart`, `#String#padEnd`)](/p/buatano/)
- [文字列の長さを取得する (`length`)](/p/f9anam8/)
- [部分文字列を取得する (`substring`, `slice`)](/p/tkd8fi2/)
- [文字列内の1文字を取得する／1文字ずつループ処理する (`charAt`)](/p/sbc3jzk/)
- [全角文字と半角文字を含んだ文字列を正規化して表記ゆれを吸収する (normalize) (ECMAScript2015)](/p/7b6o87n/)
- [文字列内のひらがなとカタカナを変換する](/p/ci73hgm/)
- 旧記事
  - [テンプレートを使ってテキストを生成する (Underscore.js)](/p/gamk5vt/)


配列／連想配列 (Array)
----
- [配列の基本](/p/o2o6p9w/)
- [配列に要素を追加・挿入する (`push`, `unshift`, `splice`)](/p/75v64mq/)
- [配列と配列を結合する (`...`スプレッド演算子, `concat`)](/p/r8isrjc/)
- [配列から部分配列を取得する (`slice`, `splice`)](/p/p53phnd/)
- [配列をコピーする](/p/d9kmxu3/)
- [配列をループで処理する (`for`, `forEach`, `for-of`)](/p/o75rrx9/)
- [配列をソートする (`sort`, `reverse`)](/p/gb6xnyz/)
- [配列にある要素が存在するか調べる (`includes`, `indexOf`)](/p/qk75km2/)
- [配列の長さを切り詰める (`length`, `splice`)](/p/tofj963/)
- [連想配列にキーが存在するか調べる (`in`)](/p/532kzwa/)
- [連想配列の要素（キー）を削除する (`delete`)](/p/txjpw82/)
- [連想配列の要素数を調べる](/p/eqssz44/)
- [配列要素をランダムで取り出す](/p/47oqrf9/)
- [配列の各要素に対して同じ処理を行う (`map`)](/p/cv582g2/)
- [配列の分割代入で複数の値を同時に代入する (Array destructuring)](/p/fp9iunf/)


日時／タイマー (Date/Timer)
----

### 日時
- [Date オブジェクト（日時情報）の作成方法いろいろ](/p/328s2ax/)
- [Date オブジェクトをいろいろな文字列表現に変換する](/p/xp2zujc/)
- [他の国のローカルタイム（現地時刻）を文字列形式で取得する](/p/q6c5kzi/)
- [Date オブジェクトの日付を加算／減算する](/p/9gvukm7/)
- [Date オブジェクト同士の差分を取る](/p/7qr46g6/)

### タイマー／パフォーマンス
- [タイマーで任意の関数を呼び出す (`setTimeout`, `setInterval`)](/p/3gcvmoi/)
- [精度の高いタイムスタンプを取得する (`performance.now()`)](/p/7fopb3d/)
- [処理速度を計測する (performance)](/p/onwrqji/)


構文
----

### 関数
- [関数を定義する](/p/weqxn3c/)
- [デフォルト引数を扱う関数を定義する (Default parameters) (ECMAScript 2015)](/p/34hdoeh/)
- [可変長引数を扱う関数を定義する (arguments)](/p/dzn6jyi/)
- [関数の仮引数の数を取得する](/p/vh735vb/)
- [渡されたパラメータが関数かどうか調べる](/p/nnygkfo/)
- [関数オブジェクトから関数名を取得する](/p/wxogbq4/)
- [アロー関数で関数を定義する (ECMAScript 2015)](/p/x2v23iq/)
- [`yield` を使ってイテレート可能な関数（ジェネレーター関数）を定義する](/p/q3a7jg4/)

### 変数／定数／名前空間
- [変数定義には `var` ではなく `let` や `const` を使用する (ECMAScript 2015)](/p/3qq8jdn/)
- [JavaScript で定数を定義する (`const`)](/p/7wrpahv/)
- [JavaScript ではグローバル変数も関数も全てプロパティ](/p/abfcfbh/)
- [関数内からしか参照できないプライベート関数を定義する](/p/3pk43ed/)
- 分割代入 (Destructuring assignment)
    - [分割代入によりオブジェクトの特定のプロパティだけを単独変数に取得する (Object destructuring) (ECMAScript 2015)](/p/wodo6y4/)

### オブジェクト
- [オブジェクトの基本](/p/eh3hf9a/)
- [オブジェクトのプロパティを列挙する (`for-in`)](/p/zxqg4xp/)
- [2つのオブジェクトのプロパティをマージする（`...`スプレッド演算子、`Object.assign`）](/p/3rofw5q/)
- [Property Shorthand の記法で複数の変数をひとつのオブジェクトにまとめる (ECMAScript 2015)](/p/ynbyx8w/)

### クラス
- [クラスを定義する (`class`) (ECMAScript 2015)](/p/ikpcxk2/)
- [クラス定数／クラス変数／クラスメソッドを定義する](/p/7s4ynkh/)
- [あるオブジェクトが特定のクラスのインスタンスであるかを調べる (`instanceof`, `constructor`)](/p/5wfhp95/)
- [クラスに `toString()` メソッドを実装する](/p/fu9f374/)

### JavaScript 独自拡張
- [式クロージャ (Expression Closure)](/p/59vet2q/)

### その他
- [JavaScript において偽 (false) と評価される値](/p/x7qigy6/)
- [入れ子になったループを一度に抜ける（多重ループからの break）](/p/7eocmin/)
- [ある変数が初期化済みかどうか確認する](/p/8ukueso/)
- [外部スクリプトはなぜ `(function(){})();` のような書き方をするのか？](/p/t3yxzuh/)


非同期処理
----
- [Promise オブジェクトで連続するコールバック処理を簡潔に記述する (ECMAScript 2015)](/p/j9mgehj/)
- [Promise な非同期処理を `async`/`await` でさらに読みやすくする (ECMAScript 2017)](/p/sdq298e/)


HTML/DOM 操作
----

### 要素

- [HTML の DOM 要素を取得する (1) タグ名、クラス名、ID を検索](/p/on7omgt/)
- [HTML の DOM 要素を取得する (2) 親要素、子要素、兄弟要素を検索](/p/vuc2i4u/)
- [HTML の DOM ツリーに要素を追加する](/p/jo9nv2m/)
- [HTML 要素に独自の data 属性（カスタム属性）を設定する](/p/8w6ps88/)
- [HTML 要素の class 属性の値を追加・削除・トグルする](/p/6wxqs3z/)
- [ページ内のヘッダ要素 (h2/h3/h4) から自動的にメニュー (TOC) を生成する](/p/pzkgih9/)
- [HTML 要素のサイズを取得する](/p/afa7mck/)
- [HTML 要素の位置を取得する](/p/zjomx8d/)

### ウィンドウ

- [Web ブラウザのページ表示位置を調べる（スクロール位置を取得する）](/p/sg34oyg/)


画像／イメージ
----
- [複数の画像ファイルが読み込まれるのを待機する](/p/4rni2hu/)


音声/オーディオ
----
- [オーディオファイル (mp3) を再生する](/p/vmwj6jf/)


I/O（入出力）
----
- [キーボードからの入力を取得する](/p/byc3c82/)
- [テキストボックスでキー入力したときのイベントをハンドルする (`onkeyup`)](/p/i6x3cm6/)
- [ボタンを押したときのイベントをハンドルする (`onclick`)](/p/i5hwe49/)
- [プルダウンリストから項目を選択したときのイベントをハンドルする (`onchange`)](/p/6uju8p3/)

レガシーライブラリ
----
- jQuery
  - [jQuery 本体のロード方法いろいろ](/p/juq4qnm/)
  - [jQuery 関連用語](/p/mxevfzw/)
  - [jQuery で Hello World](/p/b5shzha/)
  - [jQuery で要素を追加、移動、コピーする (`append`, `prepend`)](/p/29f3ahv/)
    - [jQuery で動的にリスト項目 (li) を追加する](/p/nqvvwdi/)
    - [jQuery でテーブルに動的にレコード (tr) を追加する](/p/fcsowho/)
  - [jQuery で要素を削除する (`remove`)](/p/3dwng6b/)
  - [jQuery の ajax/get/post メソッドが動作しない](/p/7r2q66y/)
  - [jQuery でフォームに入力した値を取得する](/p/t2ifk4j/)
- [Backbone.js の使い方メモ](/p/wobqnsc/)
- [RequireJS の使い方メモ](/p/nouqw33/)


HTML5 Canvas
----
- [HTML5 Canvas のサイズ指定について](/p/5py4d68/)
- [HTML5 Canvas でテキストを描画する](/p/ozui3sp/)
- [HTML5 Canvas に矩形や円を描画する](/p/pymfycy/)
- [HTML5 Canvas に画像ファイルを描画する](/p/y9da6h5/)
- [HTML5 Canvas 内のマウスドラッグで、描画オブジェクト全体を移動させる](/p/3df8z6p/)
- [HTML5 Canvas のまわりにテレビの枠画像を表示してそれっぽく見せる](/p/bjvoaut/)
- [ウィンドウサイズに応じて HTML5 Canvas のサイズを変更する](/p/iqkhk2x/)
- [HTML5 Canvas の描画領域全体をクリアする](/p/ybbvku5/)


Web サイト
----
- [URL エンコード／デコードを行う (`encodeURI`, `encodeURIComponent`)](/p/p5m3yb2/)
- [URL 内のハッシュフラグメントの値を扱う (`hashchange`, `location.hash`)](/p/geapn83/)
- [URL からクエリ文字列を取り出す (`window.location.search`, `URLSearchParams`)](/p/2qhash7/)


実行環境
----
- [CORS - Cross-Origin Resource Sharing とは？](/p/rv3nk4i/)
- [Chrome で Ajax (XMLHttpRequest) によるクロスドメイン通信の制約をなくす](/p/qbwu8id/)
- [使用している Web ブラウザが IE (Internet Explorer) かどうか判別する](/p/d52isdv/)


Node.js
----
- [⇒ まくまく Node.js ノート](/nodejs/)

