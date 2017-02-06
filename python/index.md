---
title: Python メモ
layout: category-index
---


はじめに／基本
====
* [Python のコーディングスタイル](coding-style.html)
* [Python でモジュールを import する方法いろいろ](import-modules.html)
* [Python のモジュール／パッケージを作成する](env/create-module.html)


開発環境／デバッグ／ユニットテスト
----
* [Windows で pydoc コマンドを使用できるようにする](dev/pydoc-on-windows.html)
* [PYTHONSTARTUP で Python のインタラクティブシェルを便利にする](dev/python-startup.html)
* [Python のパッケージ管理ツール (pip)](pip.html)
* [Python でユニットテストを記述する](unittest.html)
* [Python コードの実行時間を計測する](misc/measure-time.html)
* [関数／メソッドのソースコードを確認する (insepct)](dev/get-source.html)
* [Python スクリプトを Windows の実行ファイル (.exe) に変換する (py2exe)](dev/py2exe.html)


構文
----

### 制御構文
* [Python の if 構文](syntax/if.html)
* [Python の switch 構文](syntax/switch.html)
* [range による数値のループ処理いろいろ](loop-with-range.html)
* [三項演算子（条件演算子）を扱う](syntax/conditional-expressions.html)
* [全てが真 (all)、少なくとも一つが真 (any) かどうか調べる](syntax/all-any.html)

### メソッド / 関数
* [可変長引数を扱う](syntax/variable-length-args.html)

### クラス
* [Python のクラス構文](syntax/class.html)
* [オブジェクトを print で出力できるようにする](print-object.html)
* [クラスの属性に名前でアクセスする](get-attribute-by-name.html)

python コマンド / 実行環境
====
* [Python でワンライナーを実行する](one-liner.html)
* [Python スクリプトを Windows で動く実行ファイル形式に変換する](py2exe.html)
* [Python スクリプトの中で Python のバージョンを確認する (sys.version)](env/version.html)
* [拡張子に関連付けられたアプリケーションでファイルを開く (os.system)](env/system-open.html)
* [Python の最大再帰数を調べる／変更する](env/recursion-limit.html)
* [Python から外部プログラムを起動する (subprocess.run)](env/call-external-program.html)


文字列
====
* [Python の文字列リテラルいろいろ](numstr/string-literal.html)
* [文字列がある文字列で始まっている／終わっているかを調べる](startswith-endswith.html)
* [文字列内の部分文字列を検索する／抽出する](numstr/search-string.html)
* [文字列を置換する](numstr/replace-string.html)
* [文字列を１文字ずつに分割してリストにする](numstr/split-into-char.html)
* [リスト内の要素を結合して文字列にする](numstr/concat-into-string.html)
* [Python で UUID を生成する (uuid.uuid4)](numstr/uuid.html)

### JSON
* [Python のオブジェクトを JSON 形式のテキストに変換する](numstr/python-to-json.html)
* [JSON 形式のテキストを Python のオブジェクトに変換する](numstr/json-to-python.html)

数値
====
* [2進数、8進数、16進数の数値リテラル](numstr/num-literal.html)
* [数値と文字列を変換する](numstr/convert-number-and-string.html)
* [整数の割り算の結果を整数 or 少数点数で取得する](numstr/num-division.html)
* [小数点以下を四捨五入する／切り捨てる／切り上げる](numstr/round-number.html)
* [独自オブジェクトの配列から最小値、最大値を求める (min_by/max_by)](numstr/object-min-max.html)
* [リスト要素の合計値を求める (sum)](numstr/sum.html)
* [階乗を求める (math.factorial)](numstr/factorial.html)
* [順列の数 (nPr) を求める](numstr/number-of-permutation.html)
* [組み合わせの数 (nCr) を求める](numstr/number-of-combination.html)

シーケンス (Lists and Tuples)
====

* [リストとタプルの違い](list/list-and-tuple.html)
* [リストに要素を追加する／リストを結合する (append, extend, +)](list/append.html)
* [リスト／タプルをループで処理する](list/loop-list.html)
* [リスト／タプルのサイズを取得する (len)](list/len.html)
* [リスト／タプル内の要素をランダムで取得する (random.choice)](list/random-choice.html)
* [リストを昇順ソート／降順ソートする (list.sort/sorted)](list/sort.html)
* [リストをシャッフルする (random.shuffle)](list/random-shuffle.html)
* [リストから特定の値を持つ要素を削除する (list.remove)](list/remove.html)
* [リスト内の特定の値を持つ要素の数を数える (list.count)](list/count.html)
* [リスト内の条件に一致する要素の数を数える](list/count-if.html)
* [リスト内の要素をまとめて変更する (map)](list/map.html)
* [二次元配列を作成する](list/multidimensional-array.html)
* [二次元配列をソートする](list/sort-multidimensional-array.html)

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

セット (Set)
====
* [set オブジェクトの基本](set/basic.html)
* [set オブジェクトで集合演算を行う](set/set-operation.html)

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

パス
----
* [実行中のスクリプトのファイル名やパスを取得する](io/script-path.html)
* [パスからファイル名だけを取り出す (basename)](io/basename.html)
* [パスを結合する (join)](io/join-path.html)


ファイル／ディレクトリ
----
* [ディレクトリ内のファイルを列挙する](enum-files.html)
* [指定した拡張子、名前のファイルを列挙する](find-files.html)
* [ファイル／ディレクトリの存在を確認する](check-file-existence.html)
* [ファイル／ディレクトリの名前を変更する](change-filename.html)
* [ディレクトリを作成する](create-directory.html)
* [ディレクトリを削除する](remove-directory.html)
* [Python スクリプトが格納されているディレクトリのパスを取得する](script-dir.html)

テキストファイルの読み書き
----
* [テキストファイルを読み込む](io/read-text-file.html)
* [JSON 形式のテキストファイルを整形して出力する](json-pretty-print.html)
* [JSON 形式のテキストを Python オブジェクトに変換する](json-to-object.html)
* [Java や C/C++ のコード中のコメントを削除する](io/remove-java-comments.html)

ZIP/JAR ファイルの読み書き
----
* [ZIP/JAR ファイル内のファイルリストを取得する](io/zip-file-list.html)


コマンドライン引数／標準入力／標準エラー出力
----
* [コマンドライン引数を取得する](command-line-params.html)
* [キーボードからのユーザ入力を取得する (input, getpass)](io/user-input.html)
* [標準入力から読み込む](read-stdin.html)
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

NumPy / Matplotlib
====
* [NumPy／pandas／Matplotlib の概要とインストール](numpy/install.html)
* [NumPy 配列 (ndarray) で行列を扱う](numpy/ndarray.html)
* [pandas.Series - Pandas でラベル付き一次元データを扱う](numpy/pd-series.html)
* [pandas.DataFrame - Pandas でラベル付き二次元データを扱う](numpy/pd-dataframe.html)
* [pandas.DataFrame - DataFrame から部分データを抽出する](numpy/dataframe-select.html)

wxPython
====

基本ウィンドウとレイアウト
----
* [wxPython - Dialog（ダイアログ）](wxpython/dialog.html)
* [wxPython - Frame（フレーム）](wxpython/frame.html)
* [wxPython - Layout（レイアウト）](wxpython/layout.html)
* [wxPython - SplitterWindow（スプリッターウィンドウ）](wxpython/splitterwindow.html)

各種コンポーネント
----
* [wxPython - Button（ボタン）](wxpython/button.html)
* [wxPython - CheckBox（チェックボックス）](wxpython/checkbox.html)
* [wxPython - CheckListBox（チェック付きリストボックス）](wxpython/checklistbox.html)
* [wxPython - Choice（プルダウンボックス）](wxpython/choice.html)
* [wxPython - ComboBox（コンボボックス）](wxpython/combobox.html)
* [wxPython - Gauge（プログレスバー）](wxpython/gauge.html)
* [wxPython - Grid（グリッド）](wxpython/grid.html)
* [wxPython - HtmlWindow（HTML ウィンドウ）](wxpython/htmlwindow.html)
* [wxPython - ListBox（リストボックス）](wxpython/listbox.html)
* [wxPython - Menu（メニュー）](wxpython/menu.html)
* [wxPython - RadioButton（ラジオボタン）](wxpython/radiobutton.html)
* [wxPython - Slider（スライダー）](wxpython/slider.html)
* [wxPython - SpinCtrl（スピンコントロール）](wxpython/spinctrl.html)
* [wxPython - StaticText（変更不可のラベル）](wxpython/statictext.html)
* [wxPytnon - StatusBar（ステータスバー）](wxpython/statusbar.html)
* [wxPython - TextCtrl（テキスト入力用）](wxpython/textctrl.html)

その他の wxPython メモ
----
* [wxPython のイベントハンドラを理解する](wxpython/event-handler.html)
* [wxPython アプリケーションのエラー出力方法を理解する](wxpython/error-output.html)
* [wxPython でマウスカーソル関連のイベントを処理する](wxpython/mouse-event.html)
* [wxPython のコンポーネントをドラッグで動かせるようにする](wxpython/drag-component.html)

コラム
====
* [Python 開発の歴史](column/history.html)
* [Python のインデントによる構造化に慣れる](column/indent.html)
* [Python のリスト内包表記に慣れる](column/list-comprehension.html)
* [Python のインタラクティブモードの小技](column/interactive.html)
