---
title: "Ruby"
url: "/ruby/"

categoryName: "まくまく Ruby ノート"
categoryUrl: "/ruby/"
categoryIcon: "_index.svg"
---

はじめに
----

* [Ruby のコーディングスタイル](/p/7wv9hrf/)
* [知っておくべき Ruby 関連ツール (bundler, rake, rvm, rdoc, yard, rspec, test-unit)](/p/wj4bj3p/)
* [Ruby の対話型プログラムを起動する (irb, pry)](/p/scxfak3/)
* [require で他のライブラリを読み込む](/p/f49nx8n/)
* [グローバルスコープな実行コードを記述しない](/p/yum82jz/)
* [Ruby の HelloWorld テンプレート](/p/ruqnqe4/)


Ruby の構文
----

* 制御構文
  * [Ruby の真偽値](/p/fe3ufno/)
  * [if と unless による分岐](/p/39asp6i/)
  * [case による分岐](/p/2qpgt7a/)
  * [繰り返し処理（ループ） (while, until, times, for, each, step)](/p/b4ra4pj/)
  * [複数の値を同時に代入する（多重代入）](/p/ggekbmf/)
  * [2 つの変数の値をスワップする](/p/55yq5h2/)
* クラス
  * [クラスとメソッドを定義する](/p/twb57kz/)
  * [コンストラクタを定義する](/p/wiegfcm/)
  * [擬似コンストラクタを定義する](/p/2kz4x74/)
  * [属性を定義する（アクセサメソッド）](/p/trm7osx/)
  * [クラスメソッドを定義する](/p/p2acsia/)
  * [クラス定数を定義する](/p/cbiwibv/)
  * [メソッドの可視性を設定する（メソッドのアクセス制御）](/p/h4ugjqd/)
  * [クラスを継承する](/p/9pjsrsu/)
* 関数／メソッド
  * [関数定義の基本](/p/3sgsrtu/)
  * [配列を展開して関数に渡す](/p/eqa98oa/)
  * [可変長引数を扱う関数を定義する](/p/ykhgcoz/)
  * [複数の戻り値を返す関数を定義する](/p/dx4jebd/)
  * [関数にコードブロックを渡す（yield による呼び出し）](/p/qae23vv/)
  * [関数を一行で定義する](/p/r7nk7ap/)


コレクション
----

### 配列

* [配列を作成する](/p/wwn5oef/)
* [配列要素の追加/配列に要素を追加する](/p/76ga4od/)
* [配列要素の追加/配列を結合する](/p/satzyck/)
* [配列をコピーする](/p/exp3jnw/)
* [配列をループで処理する](/p/4uubbev/)
* [配列をソートする（昇順ソート／降順ソート）(sort)](/p/xt2xzxi/)
* [配列をランダムに並び替える (sort_by)](/p/rdck2o4/)
* [配列の各要素の値を変更する (collect)](/p/eoudxxo/)
* 要素の参照
  * [部分配列を取得する（配列スライシング）](/p/jrf5596/)
  * [配列の要素をランダムに取得する](/p/ggqktqi/)
* 要素の削除
  * [指定した位置の要素を削除する](/p/ie33iti/)
  * [指定した値と同じ値を持つ要素を削除する](/p/kjxrxs6/)
  * [条件を満たす要素を削除する](/p/4k4e4nv/)
  * [先頭から n 個の要素を削除する](/p/fofgyfx/)
  * [先頭から条件を満たす間だけ要素を削除する](/p/jqimc9v/)
  * [重複する要素を削除する (uniq)](/p/r894npf/)
* 要素の検索
  * [条件に一致する要素を検索する (find/find_all)](/p/zph2i79/)
  * [条件を満たす要素と満たさない要素の配列に分割する (partition)](/p/inqqbua/)
  * [文字列配列から正規表現に一致する要素を検索する (grep)](/p/ynufrhd/)
  * [先頭から条件を満たす範囲の配列を取得する (take_while)](/p/kmuzhww/)

### ハッシュ (Hash)

* [ハッシュの基本](/p/k65kjdt/)
* [ハッシュのデフォルト値を設定する](/p/n3na2tg/)
* [ハッシュをループで処理する](/p/bfkqdnw/)
* [ハッシュのキー、値のリストを取得する (keys, values)](/p/899kqkj/)

### セット

* [セットを使用する（Set クラス）](/p/3muxcie/)


文字列／数値
----

### 文字列

* 生成
  * [文字列をコピーする (dup)](/p/wkqctke/)
  * [インデント用の文字列を作成する](/p/s84ydu4/)
  * [n 文字のランダム文字列を生成する](/p/vt7ws7t/)
* 分割／結合
  * [文字列を結合する](/p/y2qwaqw/)
  * [文字列をデリミタで分割する](/p/hd66e3i/)
  * [文字列を１文字ずつに分割する](/p/iz4nr7v/)
* チェック
  * [文字列内にある文字列が含まれているか調べる (String#include, =~)](/p/6835vdj/)
* 抽出
  * [文字列から正規表現にマッチする部分を取り出す (String#slice, Regexp#match, String#scan)](/p/has6rjr/)
* 置換／削除／変更
  * [文字列を置換する (sub/gsub)](/p/upbbkhw/)
  * [テンプレート機能（プレースホルダ）で文字列の一部を置換する (%)](/p/3yvtnt6/)
  * [文字列内のある文字を指定した文字に置換する (tr)](/p/uuyzqx7/)
  * [文字列からある文字を取り除く (delete/gsub)](/p/6fpfhwj/)
  * [文字列を逆順にする (reverse)](/p/hwuci5s/)
  * [文字列をインクリメントする (succ/next)](/p/z8te97w/)
* 変換（文字列⇔数値）
  * [数値と文字列を変換する (to_i, to_f, to_s)](/p/m9pkr73/)
  * [ASCII コードと文字を変換する (ord, chr, bytes, unpack, codepoints)](/p/cbif895/)

### 数値

* [最大値／最小値を求める (min/max/minmax)](/p/dgjggob/)
* [独自オブジェクトの配列から最小値、最大値を求める (`min_by`/`max_by`)](/p/b22gfoi/)
* [配列内で最大値を持つ要素のインデックスを取得する](/p/9xi4svg/)
* [乱数（ランダムな数値）を生成する](/p/rbcp4ou/)
* [数値のインクリメントとデクリメント](/p/fdx622r/)

### 組み合わせ

* [順列 (permutation) を作成する](/p/wk75ra7/)

### 日時（Time オブジェクト）

* [文字列から時刻オブジェクト (Time) に変換する](/p/rtt88eb/)


入出力 (I/O)
----

* [コマンドライン引数を扱う](/p/btwa5fv/)
* [コマンドライン引数によるオプションに対応する (optparse)](/p/r2ryc4t/)
* [標準出力へ出力する (puts/print/printf)](/p/a2zcbyr/)
* [標準エラー出力へ出力する (STDERR)](/p/odauy26/)
* [キーボードからの入力を取得する (STDIN.gets/readline)](/p/yf9e2j2/)
* [標準入力から読み込む (gets)](/p/h3uzf9m/)
* [キーボードからのパスワードの入力を取得する](/p/xmkp7u4/)
* [外部プログラムを呼び出してその出力を取得する](/p/85axqoj/)
* [外部プログラムを実行可能か調べる／外部プログラムの絶対パスを取得する](/p/stvk9gx/)
* [ソースコード内にテキストデータを埋め込む](/p/m6zz7z2/)
* [Ruby でワンライナープログラミング](/p/f6fj4c7/)
* ファイル／ディレクトリ
  * [テキストファイルを読み込む](/p/wzbqu3e/)
  * [テキストファイルを grep する](/p/ynufrhd/)
  * [様々なディレクトリのパスを取得する／パスを加工する](/p/vynqmh5/)
  * [親ディレクトリのパスを取得する](/p/xqwzqn4/)
  * [ディレクトリ内のファイルを列挙する](/p/kzuq3cd/)
  * [ディレクトリ内のファイルを検索する](/p/zpoj5yq/)
  * [ディレクトリを作成／削除する](/p/rsiaoio/)
  * [一時ディレクトリ／一時ファイルを使用する](/p/vrcmada/)
  * [ファイルやディレクトリが存在するか調べる (File.exist?, Dir.exist?)](/p/dnav4hc/)
* XML
  * [REXML とは](/p/wmrawp3/)
  * [REXML で XML ファイルを作成する](/p/sapxuyk/)
  * [REXML で XML ファイルを読み込む](/p/a3z2tp7/)
* YAML
  * [Ruby で YAML ファイルを扱う](/p/ai66hxc/)
* mp3
  * [ruby-mp3info で MP3 ファイルのタグ情報を取得／修正する](/p/25f969i/)


その他
----

* 環境
  * [複数バージョンの Ruby をインストールする (RVM)](/p/q75ofiy/)
  * [ユーザライブラリの検索パスを調べる](/p/pgsu6vb/)
  * [環境変数を扱う](/p/jijhyxm/)
* RubyGems
  * [RubyGems とは](/p/ega8g26/)
  * [プロキシ経由で gem コマンドを使用する](/p/gsmh9o2/)
  * [Gem パッケージのインストール先を調べる](/p/p2ib8fk/)
  * [Gem パッケージを作成する (1) 基本](/p/k6gcrgj/)
  * [Gem パッケージを作成する (2) 実行可能コマンドを追加する](/p/sf4zv8i/)
  * [Gem パッケージを作成する (3) パッケージを公開する](/p/sjzsisu/)
  * [勉強用に Gem パッケージをダウンロードする](/p/ovi2oa5/)
  * [Gem パッケージの gemspec へのファイルの追加し忘れを防ぐ](/p/sa4jekt/)
* デバッグ／テスト
  * [test-unit によるユニットテスト](/p/wshqogt/)
  * [プロファイラを使用してボトルネックを探る](/p/fydda5r/)
* トラブルシューティング
  * [invalid byte sequence in Windows-31J というエラーが出る場合](/p/2pn937h/)

