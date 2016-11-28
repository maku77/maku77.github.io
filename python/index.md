---
title: Python メモ
layout: category-index
---


はじめに／基本
====
* [Python のコーディングスタイル](coding-style.html)
* [Python でモジュールを import する方法いろいろ](import-modules.html)
* [Python のパッケージ管理ツール (pip)](pip.html)

構文
----
* [Python の if 構文](syntax/if.html)
* [Python の switch 構文](syntax/switch.html)
* [Python のクラス構文](syntax/class.html)
* [range によるループ処理いろいろ](loop-with-range.html)

python コマンド / 実行環境
====
* [Python でワンライナーを実行する](one-liner.html)
* [Python スクリプトを Windows で動く実行ファイル形式に変換する](py2exe.html)
* [Python スクリプトの中で Python のバージョンを確認する (sys.version)](env/version.html)
* [拡張子に関連付けられたアプリケーションでファイルを開く (os.system)](env/system-open.html)
* [Python の最大再帰数を調べる／変更する](env/recursion-limit.html)

クラス
====
* [オブジェクトを print で出力できるようにする](print-object.html)
* [クラスの属性に名前でアクセスする](get-attribute-by-name.html)

文字列
====
* [Python の文字列リテラルいろいろ](numstr/string-literal.html)
* [文字列がある文字列で始まっている／終わっているかを調べる](startswith-endswith.html)
* [文字列内の部分文字列を検索する／抽出する](numstr/search-string.html)
* [文字列を置換する](numstr/replace-string.html)

数値
====
* [2進数、8進数、16進数の数値リテラル](numstr/num-literal.html)
* [数値と文字列を変換する](numstr/convert-number-and-string.html)
* [整数の割り算の結果を整数 or 少数点数で取得する](numstr/num-division.html)
* [小数点以下を四捨五入する／切り捨てる／切り上げる](numstr/round-number.html)
* [独自オブジェクトの配列から最小値、最大値を求める (min_by/max_by)](numstr/object-min-max.html)

シーケンス (Lists and Tuples)
====

* [リストとタプルの違い](list/list-and-tuple.html)
* [リスト/リスト内の要素をループで処理する](list/loop-list.html)
* [リスト/リスト内の要素をシャッフルする](list/shuffle-list.html)
* [リスト/リスト内の要素をランダムで取得する](list/choice-from-list.html)
* [二次元配列を作成する](list/multidimensional-array.html)

ディクショナリ (Dictionary)
====
* [dictionary オブジェクトを生成する ({})](dictionary/create.html)
* [dictionary の要素を参照する ([], get)](dictionary/get.html)
* [dictionary の要素を削除する (del)](dictionary/del.html)
* [dictionary の要素数を取得する (len)](dictionary/len.html)
* [dictionary に指定したキーが存在するか調べる (in)](dictionary/in.html)
* [キーのリストと値のリストから dictionary を生成する (zip)](dictionary/zip.html)
* [dictionary からキー、値のリストを作成する (keys, values, items)](dictionary/keys-values.html)
* [2 つの dictionary をマージする (update)](dictionary/update.html)
* [dictionary の要素をループで処理する](dictionary/loop.html)
* [dictionary の要素をソートして出力する](dictionary/sort.html)
* [dictionary にキーが存在しない場合のみ新しい値を格納する (setdefault)](dictionary/setdefault.html)


HTML/XML のパース
====
* [Python で XML を扱う方法いろいろ](xml-in-python.html)
* [Python で XML をパースする (ElementTree)](parse-xml-by-element-tree.html)
* [Python で XML をパースする (minidom)](parse-xml-by-minidom.html)
* [Python で XPath を使用する (ElementTree)](xpath.html)
* [Python で XML を構築する (minidom)](create-xml-by-minidom.html)
* [Python で HTML をパースする (HTMLParser)](parse-html-by-html-parser.html)
* [Python で HTML をパースする (Beautiful Soup)](parse-html-by-beautiful-soup.html)

入出力 (I/O)
====
* [ディレクトリ内のファイルを列挙する](enum-files.html)
* [指定したファイル名のファイルを検索する](find-files.html)
* [ファイル／ディレクトリの存在を確認する](check-file-existence.html)
* [ファイル／ディレクトリの名前を変更する](change-filename.html)
* [ディレクトリを作成する](create-directory.html)
* [ディレクトリを削除する](remove-directory.html)
* [コマンドライン引数を取得する](command-line-params.html)
* [Python スクリプトが格納されているディレクトリのパスを取得する](script-dir.html)

テキストファイル
----
* [テキストファイルを読み込む](read-text-file.html)
* [JSON 形式のテキストファイルを整形して出力する](json-pretty-print.html)
* [JSON 形式のテキストを Python オブジェクトに変換する](json-to-object.html)

標準入力
----
* [標準入力から読み込む](read-stdin.html)
* [キーボードからのユーザ入力を取得する](keyboard-input.html)

標準出力
----
* [print 関数の改行を抑制する](print-without-line-break.html)

SQL
----
* [Python で SQLite データベースを使用する](sqlite.html)

Web
====
* [Python で HTTP を扱う方法いろいろ](http-in-python.html)
* [urllib による HTTP リクエスト (1)](http-request.html)
* [urllib による HTTP リクエスト (2) プロキシ経由でアクセス](http-request-with-proxy.html)
* [urllib による HTTP リクエスト (3) Basic 認証](http-request-with-basic-auth.html)
* [requests パッケージによる HTTP リクエスト](http-request-with-requests-package.html)
* [HTTP でファイルをダウンロードする](download-file.html)


ユニットテスト
====
* [Python でユニットテストを記述する](unittest.html)


コラム
====
* [Python 開発の歴史](column/history.html)
* [Python のインデントによる構造化に慣れる](column/indent.html)
* [Python のリスト内包表記に慣れる](column/list-comprehension.html)
* [Python のインタラクティブモードの小技](column/interactive.html)
