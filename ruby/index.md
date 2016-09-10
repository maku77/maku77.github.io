---
title: Ruby
layout: category-index
---

はじめに
====
* [Ruby のコーディングスタイル](coding-style.html)
* [知っておくべき Ruby 関連ツール](ruby-tools.html)
* [Ruby の対話型プログラムを起動する](interactive-ruby.html)
* [require で他のライブラリを読み込む](require.html)
* [グローバルスコープな実行コードを記述しない](global-main.html)

Ruby の制御構文
====
* [Ruby の真偽値](syntax/true-and-false.html)
* [if と unless による分岐](syntax/if.html)
* [case による分岐](syntax/case.html)
* [条件指定によるループ (while と until)](syntax/while.html)
* [範囲指定によるループ (for, each, step)](syntax/for.html)
* [複数の値を同時に代入する（多重代入）](syntax/multiple-assignment.html)

クラス
====
* [クラスとメソッドを定義する](define-class.html)
* [コンストラクタを定義する](constructor.html)
* [擬似コンストラクタを定義する](pseudo-constructor.html)
* [属性を定義する（アクセサメソッド）](attribute.html)
* [クラスメソッドを定義する](class-method.html)
* [クラス定数を定義する](class-const.html)
* [メソッドの可視性を設定する（メソッドのアクセス制御）](method-visibility.html)
* [クラスを継承する](inherit-class.html)

関数／メソッド
====
* [関数定義の基本](method.html)
* [配列を展開して関数に渡す](expand-array-parameter.html)
* [可変長引数を扱う関数を定義する](variable-parameters.html)
* [複数の戻り値を返す関数を定義する](multiple-value-function.html)
* [関数にコードブロックを渡す（yield による呼び出し）](yield.html)
* [関数を一行で定義する](oneline-method.html)

配列
====
* [配列を作成する](array/create-array.html)
* [配列要素の追加/配列に要素を追加する](array/add-element.html)
* [配列要素の追加/配列を結合する](array/combine-arrays.html)
* [配列をコピーする](array/copy-array.html)
* [配列をループで処理する](array/loop-array.html)
* [配列をソートする（昇順ソート／降順ソート）(sort)](array/sort-array.html)
* [配列をランダムに並び替える (sort_by)](array/sort-randomly.html)
* [配列の各要素の値を変更する (collect)](array/collect-array.html)

### 要素の参照
* [部分配列を取得する（配列スライシング）](array/slice.html)
* [配列の要素をランダムに取得する](array/random.html)

### 要素の削除
* [指定した位置の要素を削除する](array/delete-at.html)
* [指定した値と同じ値を持つ要素を削除する](array/delete-same-elements.html)
* [条件を満たす要素を削除する](array/delete-if.html)
* [先頭から n 個の要素を削除する](array/drop.html)
* [先頭から条件を満たす間だけ要素を削除する](array/drop-while.html)
* [重複する要素を削除する (uniq)](array/uniq.html)

### 要素の検索
* [条件に一致する要素を検索する (find/find_all)](array/find.html)
* [条件を満たす要素と満たさない要素の配列に分割する (partition)](array/partition.html)
* [文字列配列から正規表現に一致する要素を検索する (grep)](array/grep.html)
* [先頭から条件を満たす範囲の配列を取得する (take_while)](array/take-while.html)


ハッシュ
====
* [ハッシュの基本](hash/basic.html)
* [ハッシュのデフォルト値を設定する](hash/default-hash-value.html)

文字列／数値
====

文字列
----
* [文字列を結合する](string/concat.html)
* [文字列をデリミタで分割する](string/split.html)
* [文字列を１文字ずつに分割する](string/each-char.html)
* [文字列をコピーする (dup)](string/dup.html)
* [文字列を置換する (sub/gsub)](string/replace.html)
* [文字列内のある文字を指定した文字に置換する (tr)](string/tr.html)
* [文字列を逆順にする (reverse)](string/reverse.html)
* [文字列からある文字を取り除く (delete/gsub)](string/delete.html)
* [インデント用の文字列を作成する](string/create-indent.html)
* [文字列をインクリメントする (succ/next)](string/succ.html)

正規表現
----
* [文字列から正規表現にマッチする部分を取り出す](string/extract.html)

数値
----
* [最大値／最小値を求める (min/max/minmax)](number/max-and-min.html)
* [配列内で最大値を持つ要素のインデックスを取得する](number/max-value-index.html)
* [乱数を生成する](number/random.html)
* [数値のインクリメントとデクリメント](number/increment.html)

日時（Time オブジェクト）
----
* [文字列から時刻オブジェクト (Time) に変換する](time/parse.html)

入出力 (I/O)
====
* [コマンドライン引数を扱う](command-line-params.html)
* [コマンドライン引数によるオプションに対応する (optparse)](io/optparse.html)
* [標準出力へ出力する (puts/print/printf)](io/print-to-stdout.html)
* [キーボードからの入力を取得する (STDIN.gets/readline)](io/input-from-keyboard.html)
* [標準入力から読み込む (gets)](io/input-from-stdin.html)
* [キーボードからのパスワードの入力を取得する](input-password-from-keyboard.html)
* [外部プログラムを呼び出してその出力を取得する](io/execute-external-command.html)
* [外部プログラムを実行可能か調べる／外部プログラムの絶対パスを取得する](io/is_executable.html)
* [ソースコード内にテキストデータを埋め込む](embed-data.html)

ファイル／ディレクトリ
----
* [テキストファイルを読み込む](read-text-file.html)
* [様々なディレクトリのパスを取得する／パスを加工する](get-dir-path.html)
* [ディレクトリ内のファイルを列挙する](enum-files.html)
* [ディレクトリ内のファイルを検索する](find-files.html)
* [ディレクトリを作成／削除する](create-dir.html)
* [一時ディレクトリ／一時ファイルを使用する](temporary-file.html)

XML
----
* [REXML とは](rexml.html)
* [REXML で XML ファイルを作成する](rexml-create-xml.html)
* [REXML で XML ファイルを読み込む](rexml-read-xml.html)

mp3
----
* [ruby-mp3info で MP3 ファイルのタグ情報を取得／修正する](ruby-mp3info.html)

制御構文
====
* [2 つの変数の値をスワップする](swap-values.html)

ユニットテスト
====
* [test-unit によるユニットテスト](test-unit.html)

その他
====
* [複数バージョンの Ruby をインストールする (RVM)](rvm.html)
* [ユーザライブラリの検索パスを調べる](load-path.html)
* [環境変数を扱う](environment-variable.html)
* [プロファイラを使用してボトルネックを探る](other/profiler.html)

RubyGems
----
* [RubyGems とは](what-is-rubygems.html)
* [プロキシ経由で gem コマンドを使用する](gem-with-proxy.html)
* [Gem パッケージのインストール先を調べる](gem-environment-gemdir.html)
* [Gem パッケージを作成する (1) 基本](create-gem.html)
* [Gem パッケージを作成する (2) 実行可能コマンドを追加する](create-gem2.html)
* [勉強用に Gem パッケージをダウンロードする](fetch-gem.html)

トラブルシューティング
----
* [invalid byte sequence in Windows-31J というエラーが出る場合](trouble/invalid-byte-sequence.html)

