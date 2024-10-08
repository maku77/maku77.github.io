---
title: "Perl"
layout: category-index
---

Perl ドキュメント
----
- [Perl のマニュアルページを表示する (perldoc)](misc/perldoc.html)
- `perldoc perl` **ドキュメントのリストを表示**
- `perldoc XYZ`  **XYZ のドキュメント**
- `perldoc -f Func`  **組み込み関数のドキュメント**


構文／言語仕様
----
- [論理演算子（短絡演算子）を利用したデフォルト値のイディオム](syntax/logical-operator.html)
- [整数リテラル](syntax/integer-literal.html)
- [文字列リテラル](syntax/string-literal.html)
- [ブール値（真偽値）](syntax/bool.html)
- [not と ! の結合度の違い](syntax/not-and-exclamation.html)
- [数値比較と文字列比較](syntax/comparison.html)
- 制御構造
    - [if/unless による分岐処理](syntax/if.html)
    - [while/until によるループ処理](syntax/while.html)
    - [for/foreach によるループ処理](syntax/for.html)
- [エラー変数 $! について](syntax/error.html)
- [undef と defined 演算子](syntax/undef-defined.html)
- [require で別ファイル（ライブラリ）をインクルードする](syntax/require.html)
- [パッケージによる名前空間の設定 (package, our)](syntax/namespace.html)
- [一時的にカレントパッケージを変更する](syntax/change-current-package.html)
- [定数を定義する（constant プラグマ）](syntax/constant.html)
- [リファレンス先のデータの生存期間](syntax/reference-lifetime.html)


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
- [配列の添え字に小数を指定するとどうなるか？](list/misc.html)
- [無名配列コンストラクタ／無名ハッシュコンストラクタ](list/anonymous-array.html)


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
- [モジュールの検索パスを追加する（use lib プラグマ）](module/use-lib.html)
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

### 基本的な文字列処理
- [文字列を結合する (.)](string/concat.html)
- [ある文字列を繰り返した文字列を作成する (x)](string/repeat.html)
- [文字列リテラルの中で配列を展開する](string/expand-array.html)
- [文字列をデリミタで分割する (split)](string/split.html)
- [文字列を１文字ずつに分割する (split)](string/split-into-chars.html)
- [リスト要素を結合して１つの文字列にする (join)](string/join.html)
- [文字列の末尾の改行を取り除く (chomp)](string/chomp.html)
- [部分文字列の位置を検索する (index, rindex)](string/index-rindex.html)
- [文字列リテラルを囲むクォート文字に任意の記号を使用する](string/quote-char.html)

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

### 応用
- [テキストの折り返し（改行）処理を自動で行う (Text::Wrap)](string/text-wrap.html)


入出力 (I/O)
----
- [printf のフォーマット出力を使いこなす](io/printf.html)
- [標準入力を読み込む (STDIN)](io/read-from-stdio.html)
- [ダイヤモンド演算子を使って標準入力とファイルからの入力に対応する](io/diamond-operator.html)
- [変数の内容をファイルに保存する／復元する](io/store.html)


ファイル・ディレクトリ
----
- [ファイルのオープン・クローズと読み書きの基本](file/basic.html)
- [ファイルがオープンできないとき自動で終了する（autodie プラグマ）](file/autodie.html)
- [グロブよってファイル名のリストを取得する (glob)](file/glob.html)
- [ディレクトリハンドルによってファイル名のリストを取得する (readdir)](file/readdir.html)
- [指定したディレクトリ以下のファイルを列挙する (find)](file/find.html)
- [ディレクトリを作成する](file/mkdir.html)
- [ファイル・ディレクトリを削除する (unlink, rmdir)](file/unlink-rmdir.html)
- [ファイル名を変更する (rename)](file/rename.html)
- [ファイルを移動する (rename)](file/move.html)
- [ファイルをコピーする (copy)](file/copy.html)
- [ディレクトリ内のファイルの合計サイズを調べる](file/dir-size.html)
- [読み込んでいるファイルの内容を直接変更する（書き戻し編集: in-place editing）](file/in-place-editing.html)
- [スクリプトの末尾にテキストデータを埋め込む（埋め込みドキュメント）](file/embed-data.html)
- [標準出力 (STDOUT) の出力先をファイルに切り替える](file/redirect-stdout.html)
- [Config ファイル（key=value という行形式のファイル）を読み込むサンプル](file/config.html)
- ファイルテスト
    - [ファイルが存在するかどうか調べる (if -e)](file/exist.html)
    - [ファイルがしばらく更新されていないかどうか調べる (if -M)](file/last-mod.html)
    - [ファイルサイズを調べる (-s)](file/size.html)
    - [ファイルがテキストファイルかどうか調べる (-T)](file/find-text-files.html)
- パス
    - [カレントディレクトリのパスを取得する (cwd)](file/cwd.html)
    - [カレントディレクトリを変更する (chdir)](file/chdir.html)
    - [フルパス（絶対パス）からファイル名やディレクトリ名を抽出する (basename, dirname)](file/basename.html)


プロセス
----
- [fork で子プロセスを作成する](process/fork.html)
- [あるプロセス ID のプロセスがまだ生きているかを確認する (kill)](process/kill.html)
- [SIGINT シグナル (Ctrl-C) をハンドリングする](process/sigint.html)
- [子プロセス（外部プログラム）への入出力を取り込む（パイプ）](process/pipe.html)
- [子プロセス（外部プログラム）の出力を取り込む（バッククォート文字列）](process/backquote.html)


その他
----
- [プログラムの起動パラメータ (-t filename) などを処理する](misc/command-line-params.html)
- [Perl スクリプトのエンコーディングを指定する](misc/script-encoding.html)
- [Perl スクリプトが Windows で実行されているか調べる](misc/run-on-windows.html)
- [Perl のスカラ変数と配列に $, @ というプレフィックスを付ける理由](misc/var-prefix.html)
- [環境変数を参照する (%ENV)](misc/env.html)
- [現在の日時を文字列で取得する (localtime, gmtime)](misc/localtime.html)
- [画像ファイルの幅、高さを調べる (Image::Size)](misc/imgsize.html)
- [ユーザー名、グループ名からユーザ ID、グループ ID を取得する (getpwnam, getgrnam)](misc/getpwnam.html)
- [現在の環境で使用可能な make ユーティリティの名前を確認する](misc/make.html)
- デバッグ
    - [Data::Dumper で複雑なデータをダンプする](misc/dumper.html)
    - [Perl のデバッガを使用する](misc/debugger.html)
    - [Perl で使用可能なテストモジュール](misc/test-module.html)
    - [一時的にエラーメッセージを詳しくする（diagnostics プラグマ）](misc/diagnostics.html)

