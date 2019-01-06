---
title: "PHP"
layout: category-index
---

はじめに
----
- [PHP のコーディングスタイル](misc/coding-style.html)

構文
----
- [PHP の if-else 構文](syntax/if.html)
- [PHP のループ構文](syntax/loop.html)
- [クラスを定義する](syntax/class.html)
- [抽象クラス、抽象メソッドを定義する](syntax/abstract.html)
- [グローバル定数、クラス定数を定義する](syntax/constant.html)
- [定義されている定数をすべて表示する](syntax/list-constants.html)
- [名前を使って変数にアクセスする（可変変数）](syntax/variable-variables.html)
- [変数がセットされているか調べる (isset)](syntax/isset.html)
- [PHP でヒアドキュメント](syntax/here-document.html)
- [メソッドのパラメータにはタイプヒントを指定しよう](syntax/type-hint.html)
- [PHP ファイルからの相対パスで require_once する](syntax/relative-require.html)
- [関数内でグローバル変数を参照する](syntax/refer-to-global.html)
- [関数のデフォルト引数を設定する](syntax/default-parameter.html)
- [複数の値を返す関数を定義する（多値関数）](syntax/multivalued-function.html)
- [変数のスコープについて](syntax/scope.html)

設定／環境
----
- [PHP のエラー出力レベルを設定する](settings/error-level.html)
- [PHP で UTF-8 を使用するための設定](settings/utf8.html)
- [現在の include_path 設定を調べる](settings/include-path.html)
- [パッケージ管理環境 PEAR をインストールする](env/pear.html)
- [XAMPP で PHP のテスト環境を作成する](env/xampp.html)

配列／連想配列 (array)
----
- [配列を生成する](array/create.html)
- [多次元配列を生成する](array/multidimensional-array.html)
- [配列のサイズを調べる (count)](array/count.html)
- [配列が空かどうか調べる (empty)](array/empty.html)
- [配列をループ処理する (foreach)](array/loop.html)
- [配列をソートする (sort, ksort, asort)](array/sort.html)
- [配列を逆順にする (array_reverse)](array/array-reverse.html)
- [配列にある値が含まれているか調べる (in_array)](array/in-array.html)
- [配列要素を検索する (array_search)](array/search.html)
- [配列の先頭要素を取り出す (array_shift)](array/array-shift.html)
- [配列をスタックとして扱う (array_push, array_pop)](array/stack.html)
- [配列の各要素に対して同じ処理を行う (array_map)](array/array-map.html)
- [配列要素を結合して１つの文字列にする (implode)](array/implode.html)
- [連想配列にキーが存在するか調べる (array_key_exists)](array/key-exists.html)
- [連想配列の要素を削除する (unset)](array/unset.html)
- [配列の内容を再帰的にダンプする (print_r, var_dump)](array/dump-array.html)
- [PHP の配列要素は初期化された順番に保持される](array/internal-order.html)

文字列
----
- [文字列を比較する (strcmp, strcasecmp)](string/strcmp.html)
- [文字列を大文字、小文字に変換する (strtolower, strtoupper)](string/strtolower.html)
- [各単語の先頭１文字を大文字に変換する](string/camelcase.html)
- [文字列の先頭と末尾のスペースを削除する (trim)](string/trim.html)
- [文字列を分割する (explode)](string/explode.html)
- [文字列を結合する (implode)](string/implode.html)
- [文字列がある文字列で始まっているか調べる](string/starts-with.html)
- [文字列を URL エンコードする (urlencode)](string/urlencode.html)
- [HTML の特殊文字をエスケープする (htmlspecialchars)](string/htmlspecialchars.html)

入出力 (I/O)、ファイル
----
- [標準出力への出力を行う](io/stdio.html)
- [echo と print で迷ったら echo を使おう](io/echo-and-print.html)

日付／時刻
----
- [DateTime クラスで時刻を扱う](time/datetime.html)

データベース (PDO)
----
- [PDO - PDO を使ってデータベースにアクセスする](pdo/basic.html)
- [PDO - PHP ファイルからの相対パスで SQLite のデータベースファイルを開く](pdo/relative-open.html)
- [PDO - SELECT で取得したレコードの数を調べる](pdo/count-records.html)

Web アプリ
----
- [Web サーバ上のコンテンツを HTTP で取得する (file_get_contents)](web/file-get-contents.html)
- [cURL 関数を使って HTTP リクエストを送る](web/curl.html)
- [Web サイトへのアクセス時にリダイレクト要求を返す](web/redirect.html)

未分類
---
- [PHPUnit を使用してユニットテストを行う](misc/phpunit.html)
- [foreach でループ処理できるクラスを作成する（Iterator の実装）](misc/iterable-class.html)

CakePHP
----
- [CakePHP 入門 (1) セットアップ](cakephp/abc-1.html)
- [CakePHP 入門 (2) データベースの設定](cakephp/abc-2.html)
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](cakephp/abc-3.html)
- [CakePHP 入門 (4) Controller、View、Model を作成する](cakephp/abc-4.html)
- [CakePHP 入門 (5) 個別のレコードを表示する](cakephp/abc-5.html)
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](cakephp/abc-6.html)
- [CakePHP 入門 (7) レコードを追加できるようにする](cakephp/abc-7.html)
- [CakePHP 入門 (8) レコードを編集できるようにする](cakephp/abc-8.html)
- [CakePHP 入門 (9) レコードを削除できるようにする](cakephp/abc-9.html)
- [CakePHP - ページのタイトルを設定する](cakephp/set-page-title.html)
- [CakePHP - クライアントからのリクエストで送られたデータを取得する](cakephp/get-request-data.html)
- [CakePHP - 使用するテンプレートファイルを指定する](cakephp/specify-template.html)
- [CakePHP - 他のページにリダイレクトする](cakephp/redirect.html)

CodeIgniter
----

### コントローラー
- [CodeIgniter - コントローラーを作成する](codeigniter/create-controller.html)
- [CodeIgniter - フォームに入力した値をコントローラーで受け取る](codeigniter/get-form-input.html)
- [CodeIgniter - 別のコントローラー、アクションへリダイレクトする](codeigniter/redirect.html)
- [CodeIgniter - コントローラやライブラリの中から通常のクラスを require_once する](codeigniter/require-once.html)

### ビュー
- [CodeIgniter - ビューに動的引数（テンプレート引数）を渡す](codeigniter/template-param.html)
- [CodeIgniter - Form タグの出力には Form ヘルパーの form_open を使う](codeigniter/form-open.html)
- [CodeIgniter - URL ヘルパーでコントローラへのリンクを挿入する](codeigniter/anchor.html)
- [CodeIgniter- HTML ヘルパーでスタイルシート用の link タグを挿入する](codeigniter/link-tag.html)

### データベース
- [CodeIgniter - MySQL データベースの接続設定を行う](codeigniter/setup-mysql.html)
- [CodeIgniter- SQLite3 データベースの接続設定を行う](codeigniter/setup-sqlite.html)
- [CodeIgniter - モデルクラスを使用してデータベースから値を取得する](codeigniter/model.html)
- [CodeIgniter - Active Record でデータベースの CRUD 操作を行う](codeigniter/crud.html)
- [CodeIgniter - ActiveRecord でカラムが NULL のもの、NULL でないものを検索する](codeigniter/search-null-column.html)
- [CodeIgniter - データベースのテーブルを作成する](codeigniter/create-db-table.html)
- [CodeIgniter - データベースのテーブルを列挙する](codeigniter/list-tables.html)
- [CodeIgniter - 現在どんなデータベースを使用しているか調べる](codeigniter/db-platform.html)

### ライブラリ／ヘルパー
- [CodeIgniter - ライブラリ、ヘルパーの基礎](codeigniter/library-and-helper.html)
- [CodeIgniter - 独自のユーザライブラリから別のライブラリをロードする](codeigniter/load-lib-from-user-lib.html)
- [CodeIgniter - Form Validation クラスでフォームに入力された値を検査する](codeigniter/form-validation.html)
- [CodeIgniter - Session クラスでセッションデータを保持する](codeigniter/session.html)

### 応用
- [CodeIgniter - 相対パスの扱い方のまとめ](codeigniter/path.html)
- [CodeIgniter - ユーザのログイン状態を管理する](codeigniter/manage-login.html)
- [CodeIgniter - JSON テキストを返す Web API をサクッと作成する](codeigniter/json-webapi.html)
- [CodeIgniter - index.php を省略した URL でアクセスできるようにする](codeigniter/pretty-url.html)
- [CodeIgniter - 使用中の CodeIgniter のバージョンを調べる](codeigniter/version.html)
- [CodeIgniter - 参考になるチュートリアル動画集](codeigniter/tutorials.html)

