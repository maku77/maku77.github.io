---
title: Perl
layout: category-index
---

Perl ドキュメントの参照方法
----
- `perldoc perl` **ドキュメントの一覧**
- `perldoc XYZ`  **XYZ のドキュメント**

配列とリスト
----
- [配列とリストを定義する](list/define-array-list.html)
- [配列のサイズを取得する](list/get-array-size.html)
- [配列のサイズを切り詰める](list/shorten-array.html)
- [配列の範囲外アクセス](list/undef.html)
- [配列の末尾の要素を取得する](list/get-last-element.html)
- [配列に要素を追加する／取り出す](list/push-unshift.html)
- [配列の要素を print で出力する](list/print-array.html)
- [配列の要素をループで処理する](list/loop-array.html)
- [配列をコピーする](list/copy-array.html)
- [配列を連結する](list/concat-array.html)
- [リスト代入](list/list-substitution.html)
- [grep 演算子によるリスト要素のフィルタリング](list/grep-list.html)
- [条件に一致するリスト要素の数を調べる](list/grep-count.html)
- [map 演算子を使ってリストの各要素を加工したリストを取得する](list/map-list.html)
- [リストスライス、配列スライス、ハッシュスライス](list/list-slice.html)
- [配列へのリファレンスを取得する](list/reference.html)
- [配列の配列（二次元配列）を作成する](list/two-dimensional-array.html)
- [配列やリストをソートする](list/sort-array.html)
- [配列あれこれ](list/misc.html)

ハッシュ
----
- [ハッシュを定義する](hash/define-hash.html)
- [キーのリストからハッシュを作成する](hash/create-hash-by-map.html)
- [ハッシュのキーや値のリストを取得する](hash/hash-key-value-list.html)
- [ハッシュが空かどうか調べる](hash/check-if-hash-is-empty.html)
- [ハッシュのサイズを取得する](hash/get-hash-size.html)
- [ハッシュをコピーする](hash/copy-hash.html)
- [ハッシュの要素をループで処理する](hash/loop-hash.html)
- [ハッシュをソートする](hash/sort-hash.html)
- [ハッシュのキーが存在するか調べる](hash/check-key.html)
- [ハッシュからキーを削除する](hash/remove-key.html)
- [ハッシュを空にする（ハッシュの要素をすべて削除する）](hash/clear-hash.html)
- [ハッシュをリストに展開する](hash/expand-hash-to-list.html)
- [ハッシュをファイルに保存する（DBMハッシュ）](hash/save-hash-into-file.html)
- [ハッシュへのリファレンスを取得する](hash/reference-to-hash.html)
- [2 つのハッシュを合成する](hash/merge-hash.html)
- [（コラム）ハッシュと連想配列](hash/history-of-name.html)

サブルーチン
----
- [サブルーチンの基本](subroutine/basic.html)
- [サブルーチンを可変長引数に対応させる](subroutine/variable-length-arguments.html)
- [サブルーチンがリストコンテキストで呼び出されたか調べる](subroutine/want-array.html)
- [サブルーチン内では exit を使わずに die を使う](subroutine/use-die-instead-of-exit.html)
- [サブルーチンの引数をリファレンスで渡す（参照渡し）](subroutine/call-by-reference.html)
- [サブルーチンへのリファレンスを取得する](subroutine/reference-to-subroutine.html)
- [無名サブルーチンを定義する](subroutine/anonymous-subroutine.html)

モジュール
----
- [use によるモジュールのインポート](module/import-module.html)
- [インポート可能なモジュールを作成する](module/create-module.html)
- [ディストリビューション用のテンプレートファイルを作成する (h2xs)](module/module-template.html)
- [CPAN に登録されたディストリビューションの README ファイルを表示する](module/cpan-readme.html)
- [指定したディレクトリにモジュールをインストールする](module/install-directory.html)
- [POD フォーマットについて](module/pod.html)

クラス／オブジェクト
----
- マニュアルは `perldoc perlobj` で
- [コンストラクタを定義する](class/constructor.html)
- [メンバ変数とメンバメソッドを定義する](class/member.html)
- [クラスを継承する](class/inherit.html)
- [デストラクタを定義する](class/destructor.html)
- [オブジェクトを明示的に破棄する](class/undef.html)
- [クラスメソッドとしてもインスタンスメソッドとしても使えるメソッドを定義する](class/hybrid-method.html)
- [複数のオブジェクトを一度に作成する](class/bulk-instantiation.html)
- [すべてのクラスの親クラスとして振舞う UNIVERSAL パッケージを拡張する](class/universal.html)
- [あるクラスを継承しているかどうか調べる](class/check-inheritance.html)
- [あるメソッドが存在するか調べる](class/check-method-existence.html)

文字列／正規表現
----

### マッチングと抽出
- [正規表現による文字列マッチングの基本](string/basics-of-regexp.html)
- [欲張りな量指定子と欲張りでない量指定子による文字列マッチング](string/greedy-match.html)
- [大文字と小文字を区別しない文字列マッチング](string/ignore-case.html)
- [行をまたいだ文字列マッチング](string/multi-line-match.html)
- [正規表現パターンにマッチした部分文字列を抜き出す](string/extract.html)
- [インデックス指定で文字列を抜き出す (substr)](string/substr.html)
- [サンプル: インデントされた行、インデントされていない行を抜き出す](string/indented-lines.html)

### 置換
- [文字列を置換する](string/replace-string.html)
- [文字単位で置換する](string/replace-char.html)
- [大文字と小文字を置換する](string/replace-case.html)
- [インデックス指定で文字列を置換する (substr)](string/replace-by-index.html)
- [サンプル: 数値を３桁ごとにカンマを入れて表示する](string/number-with-comma.html)
- [サンプル: スクリプトや設定ファイルのコメント（#以降）を削除する](string/remove-comments.html)

### その他の文字列処理
- [文字列リテラルの中で配列を展開する](string/expand-array.html)
- [文字列をデリミタで分割する (split)](string/split.html)
- [文字列を１文字ずつに分割する (split)](string/split-into-chars.html)
- [リスト要素を結合して１つの文字列にする (join)](string/join.html)
- [部分文字列の位置を検索する (index, rindex)](string/index-rindex.html)
- [文字列リテラルを囲むクォート文字に任意の記号を使用する](string/quote-char.html)

入出力
----
- [標準入力を読み込む](io/read-from-stdio.html)
- [ダイヤモンド演算子を使って標準入力とファイルからの入力に対応する](io/diamond-operator.html)

ファイル・ディレクトリ
----
- [ファイルのオープン・クローズと読み書きの基本](file/basic.html)
- [グロブよってファイル名のリストを取得する](file/glob.html)
- [ディレクトリハンドルによってファイル名のリストを取得する (readdir)](file/readdir.html)
- [指定したディレクトリ以下のファイルを列挙する (find)](file/find.html)
- [ディレクトリを作成する](file/mkdir.html)
- [ファイル・ディレクトリを削除する (unlink, rmdir)](file/unlink-rmdir.html)
- [ファイル名を変更する (rename)](file/rename.html)
- [ファイルを移動する (rename)](file/move.html)
- [ファイルをコピーする (copy)](file/copy.html)
- [ディレクトリ内のファイルの合計サイズを調べる](file/dir-size.html)
- [書き戻し編集の仕組みで読み込んでいるファイルの内容を直接変更する](file/in-place-editing.html)
- [スクリプトの末尾にテキストデータを埋め込む（埋め込みドキュメント）](file/embed-data.html)
- [標準出力 (STDOUT) の出力先をファイルに切り替える](file/redirect-stdout.html)
- ファイルテスト
    - [ファイルが存在するかどうか調べる (if -e)](file/exist.html)
    - [ファイルがしばらく更新されていないかどうか調べる (if -M)](file/last-mod.html)
    - [ファイルサイズを調べる (-s)](file/size.html)
- パス
    - [カレントディレクトリのパスを取得する (cwd)](file/cwd.html)
    - [カレントディレクトリを変更する (chdir)](file/chdir.html)
    - [フルパス（絶対パス）からファイル名やディレクトリ名を抽出する (basename, dirname)](file/basename.html)

その他
----
- [Perl スクリプトのエンコーディングを指定する](misc/script-encoding.html)
- [Perl スクリプトが Windows で実行されているか調べる](misc/run-on-windows.html)


