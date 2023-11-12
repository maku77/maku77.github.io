---
title: "Python"
url: "/python/"

categoryName: "まくまく Python ノート"
categoryUrl: "/python/"
categoryIcon: _index.svg
---

はじめに／基本
----

### コーディングスタイル／ドキュメンテーション

- コーディングスタイル
  - [Python のコーディングスタイル](/p/pyk3j2h/)
  - [Visual Studio Code で Python 用のフォーマッター (Black) を使う](https://maku.blog/p/4oybku6/)
- ドキュメンテーション
  - [ドキュメンテーションコメント (docstring) を記述する](/p/y2biqz7/)
  - [Windows で pydoc コマンドを使用できるようにする](/p/6h2izfh/)
  - [関数やクラスのソースコードを確認する (`insepct`)](/p/xbucsaq/)

### 開発ツール／デバッグ／ユニットテスト

- 仮想環境
  - [Python の実行環境を切り替えて使用する (venv) Python3.3以降](/p/wozpogm/)
  - [Python の実行環境を切り替えて使用する (virtualenv) Python3.2以前](/p/yqjs3aw/)
- パッケージ
  - [Python のパッケージ管理ツール (pip) の使い方](/p/7o9q8p6/)
  - [Python スクリプトを Windows の実行ファイル (.exe) に変換する (`py2exe`)](/p/kyj2izf/)
- 対話型シェル
  - [Python の対話型シェルのスタートアップ処理を設定する (`PYTHONSTARTUP`)](/p/6k5m4jy/)
  - [Python の対話型シェルで使える小技](/p/dsbsaq7/)
- テスト
  - [Python でユニットテストを記述する (`unittest`)](/p/fueucsa/)
- パフォーマンス
  - [Python コードの実行時間を計測する (`datetime.now`)](/p/ubtbs9p/)

### python コマンド／実行環境

- [Python でワンライナーを実行する](one-liner.html)
- [Python スクリプトの中で Python のバージョンを確認する (`sys.version`)](env/version.html)
- [拡張子に関連付けられたアプリケーションでファイルを開く (`os.system`)](env/system-open.html)
- [Python の最大再帰数を調べる／変更する](env/recursion-limit.html)
- [Python から外部プログラムを起動する (`subprocess.run`)](env/call-external-program.html)
- 環境変数
  - [環境変数を参照する (`os.getenv`, `os.environ`)](/p/y8hs5w6/)
  - [`.env` ファイルで環境変数を設定する (`python-dotenv`)](/p/gzo8d7y/)


構文 <!-- syntax -->
----

- モジュール / パッケージ
  - [Python でモジュールやパッケージを作成する](/p/n4n5m3i/)
  - [Python でモジュールを import する方法のまとめ](/p/zdxfvct/)
  - [import 時のモジュールの検索パスを調べる (`sys.path`)](/p/o4m4jyg/)
- 型 / 変数
  - [Python の型の一覧、ある値の型を調べる (type)](syntax/types.html)
  - [Python で定数を定義する](syntax/const.html)
- 制御構文
  - [Python の if 構文](syntax/if.html)
  - [Python の switch 構文](syntax/switch.html)
  - [range による数値のループ処理いろいろ](loop-with-range.html)
  - [三項演算子（条件演算子）を扱う](syntax/conditional-expressions.html)
  - [全てが真 (all)、少なくとも一つが真 (any) かどうか調べる](syntax/all-any.html)
- メソッド / 関数
  - [Python の関数をキーワード引数を使って呼び出す](/p/jf6kyao/)
  - [Python の関数で可変長引数を扱う (`*args, **kwargs`)](/p/rcaip63/)
- クラス
  - [クラス定義の基本 (`class`)](/p/ru9mxam/)
  - [オブジェクトを `print` 関数で出力できるようにする (`__str__`)](/p/uatcsaq/)
  - [オブジェクトの属性に名前でアクセスする (`getattr`, `setattr`)](/p/r3m4k2h/)
  - [`dataclass` デコレーターで簡単にデータクラスを定義する](/p/xqkgvb6/)


型
----

### 数値 <!-- numstr -->

* [2進数、8進数、16進数の数値リテラル](numstr/num-literal.html)
* [数値と文字（文字列）を変換する (chr, ord, int, hex, oct, bin)](numstr/convert-number-and-string.html)
* [整数の割り算の結果を整数 or 少数点数で取得する](numstr/num-division.html)
* [小数点以下を四捨五入する／切り捨てる／切り上げる](numstr/round-number.html)
* [独自オブジェクトの配列から最小値、最大値を求める (min_by/max_by)](numstr/object-min-max.html)
* [リスト要素の合計値を求める (sum)](numstr/sum.html)
* [ランダムな数値（乱数）を生成する (randrange, randint, random, uniform)](numstr/random.html)
* [階乗を求める (math.factorial)](numstr/factorial.html)
* [順列の数 (nPr) を求める](numstr/number-of-permutation.html)
* [組み合わせの数 (nCr) を求める](numstr/number-of-combination.html)

### 文字列 <!-- numstr -->

- [Python の文字列リテラルいろいろ](numstr/string-literal.html)
- [文字列の中で変数を展開する（書式文字列によるフォーマット） (`str#format`, `%` 演算子)](numstr/str-format.html)
- [同じ文字列を繰り返した文字列を作成する](numstr/repetitive-string.html)
- [文字列の長さを調べる (`len`)](numstr/string-length.html)
- [文字列がある文字列で始まっている／終わっているかを調べる](startswith-endswith.html)
- [文字列が正規表現に完全一致するか調べる (`re.fullmatch`)](numstr/re-fullmatch.html)
- [文字列内の部分文字列を検索する／抽出する（正規表現）](numstr/search-string.html)
- [文字列を置換する (`str.replace`, `re.sub`, `re.subn`)](/p/wnpquuy/)
- 分割
  - [文字列をデリミタで分割する (`split`)](numstr/split-string.html)
  - [文字列を改行で分割して 1 行ずつのリストにする (`splitlines`)](numstr/split-lines.html)
  - [文字列を 1 文字ずつに分割してリストにする](numstr/split-into-char.html)
- [リスト内の要素を結合して文字列にする (`join`)](numstr/concat-into-string.html)
- [文字列を逆順にする（反転させる）](numstr/reverse-string.html)
- [ランダムな文字列を生成する (`random.choice`)](numstr/random-string.html)
- [Python で UUID を生成する (`uuid.uuid4`)](numstr/uuid.html)
- JSON
  - [JSON テキスト ⇔ Python オブジェクトの相互変換 (`json.loads`, `json.dumps`)](/p/gwfweub/)

### シーケンス (Lists and Tuples) <!-- list -->

* [リストとタプルの違い](list/list-and-tuple.html)
* リストとタプルの生成
  * [リストやタプルをサイズを指定して作成する (［］＊ N)](list/create-with-size.html)
  * [連番からなるリストを生成する (range)](list/create-by-range.html)
* [リストに要素を追加する／リストを結合する (append, extend, +)](list/append.html)
* [リスト／タプルをループで処理する](list/loop-list.html)
* [リスト／タプルのサイズを取得する (len)](list/len.html)
* [リスト／タプル内の要素をランダムで取得する (random.choice, random.choices, random.sample)](list/random-choice.html)
* [リストを昇順ソート／降順ソートする (`list.sort`/`sorted`)](/p/cqtwqgx/)
* [リストをシャッフルする (random.shuffle)](list/random-shuffle.html)
* [リストから特定の値を持つ要素を削除する (list.remove)](list/remove.html)
* [リストをフィルタして条件に一致する要素を抽出する (filter)](list/filter.html)
* [リスト内の特定の値を持つ要素の数を数える (list.count)](list/count.html)
* [リスト内の条件に一致する要素の数を数える](list/count-if.html)
* [リスト内の要素をまとめて変更する (map)](list/map.html)
* [二次元配列を作成する](list/multidimensional-array.html)
* [二次元配列をソートする](list/sort-multidimensional-array.html)
* [2 つのリストに重複する要素を抽出する（`set` と `&`）](/p/58ctg7k/)

### ディクショナリ (Dictionary) <!-- dictionary -->

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
* [dictionary の内部的な要素順序を変更する (`dict`, `OrderedDictionary`)](/p/vexfweu/)

### セット (Set) <!-- set -->

- [set オブジェクトの基本](set/basic.html)
- [set オブジェクトで集合演算を行う](/p/h3jqpp9/)


入出力 (I/O) <!-- io -->
----

### コマンドライン引数／標準入力／標準エラー出力

- コマンドライン引数
  - [コマンドライン引数を扱う (`sys.argv`)](/p/ybxfwev/)
  - [`argparse` モジュールでコマンライン引数を扱う](/p/o6q8p6m/)
- [キーボードからのユーザ入力を取得する (`input`, `getpass`)](/p/bfev6xf/)
- print
  - [print 関数の改行を抑制する](print-without-line-break.html)
  - [print 関数に複数のパラメータを渡した時の接続文字を変更する](io/print-sep.html)
  - [標準入力から読み込む (`sys.stdin`, `fileinput`)](io/stdin.html)
  - [標準エラー出力へ出力する (`sys.stderr`)](/p/m3si4w6/)
  - [任意のオブジェクトを見やすく整形して出力する (`pprint`)](io/pprint.html)

### パス

- [実行中のスクリプトのファイル名やパスを取得する](io/script-path.html)
- [パスからファイル名だけを取り出す (basename)](io/basename.html)
- [パスを結合する (join)](io/join-path.html)
- [パスを指定したディレクトリからの相対パスに変換する (`os.path.relpath`)](/p/o4a37zc/)

### ファイル／ディレクトリ

- 列挙
  - [ディレクトリ内のファイルを列挙する (`os.listdir`, `os.walk`, `glob`)](/p/thhwr4b/)
  - [指定した拡張子や名前のファイルを列挙する (`glob.iglob`, `glob.glob`)](/p/6vpyp4z/)
- コピー／移動／リネーム
  - [ファイル／ディレクトリの名前を変更する (`os.rename`, `os.renames`)](/p/9aqzppe/)
  - [ファイル／ディレクトリをコピー、移動する (`shutil.copyfile`, `shutil.copy`, `shutil.copytree`, `shutil.move`)](/p/wyxzbdw/)
- 作成／削除
  - [ディレクトリを作成する (`os.mkdir`, `os.makedirs`)](create-directory.html)
  - [ディレクトリを削除する (os.rmdir)](remove-directory.html)
- [ファイル／ディレクトリの存在を確認する (`os.path.exists`, `os.path.isfile`, `os.path.isdir`)](/p/tr3cmu5/)
- [Python スクリプトが格納されているディレクトリのパスを取得する](script-dir.html)
- [テンポラリファイル／ディレクトリを作成する (`tempfile`)](/p/co7o5k2/)

### ファイルの読み書き

- 基本的なファイル読み書き
  - [テキストファイルを読み込む (`open`, `read`, `readline`, `readlines`)](/p/f4ckt29/)
  - [テキストファイルを書き込む (`open`, `write`, `writelines`)](/p/nmv4cjr/)
- CSV/TSV
  - [CSV ファイルや TSV ファイルを読み込む (`csv.reader`)](io/csv.html)
- JSON ファイル
  - [JSON 形式のテキストファイルを読み書きする (`json.load`, `json.dump`)](/p/xhyhzfv/)
  - [Python のワンライナーコマンドで JSON ファイルを整形する (`json.tool`)](/p/an8o6m4/)
- YAML ファイル
  - [YAML ファイルを扱う](io/yaml.html)
- HTML ファイル
  - [HTML をパースする (`HTMLParser`)](/p/nqz8fnu/)
  - [HTML をパースする (`BeautifulSoup`)](/p/r4m4k2i/)
- XML ファイル
  - [XML を扱う方法いろいろ](/p/pt6fpx8/)
  - [XML をパースする (`ElementTree`)](/p/cp9q7n5/)
  - [XML をパースする (`minidom`)](/p/guducs9/)
  - [XPath を使って XML 要素を参照する (`ElementTree`)](/p/fufwevc/)
  - [XML を構築する (`minidom`)](/p/m2k3jyg/)
- Excel ファイル
  - [Excel ファイルを読み込む (`xlrd`)](io/read-excel-file.html)
- ZIP/JAR ファイル
  - [ZIP/JAR ファイルの内容を読み込む (`zipfile`)](/p/gxajt4d/)
  - [ZIP ファイルを作成・展開する (`shutil.make_archive`, `shutil.unpack_archive`)](/p/euevcsa/)
- その他のファイル、応用サンプル
  - [HTML ファイルや Markdown ファイルやプレーンテキストに変換する (`unstructured`)](/p/uivwecs/)
  - [Java や C/C++ のコード中のコメントを削除する](io/remove-java-comments.html)
  - [複数ファイルの文字列をまとめて置換する (`glob`, `re`)](/p/xwog7ip/)
  - [複数ファイルをまとめて grep する](io/grep-files.html)


Web / HTTP <!-- web -->
----

### URL

- [URL 文字列を各パートに分割する (`urllib.parse.urlparse`)](/p/rr2ahpx/)
- [文字列を URL エンコード／デコードする (`urllib.parse.quote`, `unquote`, `urlencode`)](/p/w7p7n4j/)

### HTTP リクエスト

- [Python で HTTP を扱う方法いろいろ](/p/k5p4axo/)
- urllib.request
  - [urllib による HTTP 通信 (1) GET/POST リクエスト (`urllib.request.urlopen`)](/p/o2e43ct/)
  - [urllib による HTTP 通信 (2) プロキシ経由でアクセスする](/p/ogq5hdy/)
  - [ulrlib による HTTP 通信 (3) ファイルをダウンロードする (`urllib.request.urlretrieve`)](/p/5zj72ei/)
  - [urllib による HTTP 通信 (4) Basic 認証 (`urllib.request.HTTPBasicAuthHandler`)](/p/4yiygwe/)
  - [urllib による HTTP 通信 (5) ヘッダを付けてリクエストする](/p/i4zkqye/)
  - [urllib による HTTP 通信 (6) レスポンスヘッダを取得する](/p/wewevcs/)
- [requests パッケージによる HTTP 通信](/p/r7q8q7o/)
- サンプルコード
  - [Web サイトのサイトマップ (sitemap.xml) からすべての URL を抽出する](/p/bp9q8p6/)

### HTTP サーバー

- [Python で簡易的な HTTP サーバを立てる (`http.server`, `SimpleHTTPServer`)](/p/rr3cmu5/)


未分類 <!-- misc -->
----

- [ハッシュ値 (MD5/SHA-1/SHA-256/SHA-512) を求める (`hashlib`)](/p/gn4cn3s/)
- データベース / SQL
  - [Python で SQLite データベースを使用する](/p/g5xg2nc/)


日時（日付／時刻）
----

* [Python で日時（日付／時刻）の情報を扱う (datetime, date, time, timedelta)](datetime/basic.html)
* [現在時刻から YYYY-MM-DD のような日付文字列を生成する (strftime)](datetime/date-string.html)


画像処理
----

### scikit-image

- [scikit-image で画像処理](/p/apkiihk/)
- [scikit-image の data パッケージに含まれるテスト画像を使用する](/p/nz8zoxu/)

### OpenCV

- [OpenCV: 画像を閾値で二値化（白黒画像化）する (`cv2.threshold`)](/p/i26mc77/)

{{% private %}}
- [(DRAFT) OpenCV チートシート](/p/dgkrqjo/)
- [(DRAFT) OpenCV: 画像内の物体の輪郭を検出する (`cv2.findContours`)](/p/ocef2kr/)
{{% /private %}}

### keras

- [MNIST の手書き数字データをダウンロードして表示する (`keras.datasets.mnists`)](/p/849syej/)

NumPy / pandas / Matplotlib / scikit-learn
----

### 共通

- [NumPy / pandas / Matplotlib の概要とインストール](/p/qs6iv8j/)

### NumPy

- [NumPy 配列 (`ndarray`) の基本](/p/jwfxfvd/)
- [NumPy 配列 (`ndarray`) の作成方法まとめ](/p/htducs9/)

### pandas

- [pandas でラベル付き 1 次元データを扱う (`Series`)](/p/wbudtbr/)
- [pandas でラベル付き 2 次元データを扱う (`DataFrame`)](/p/watbs9p/)
- [pandas で CSV/TSV ファイルを読み込む (`pd.read_csv`, `pd.read_table`)](/p/78ns8r5/)
- [pandas で CSV/TSV ファイルを出力する (`pd.to_csv`)](/p/ioj6bqf/)
- pandas チートシート
  - [`DataFrame` のデータ抽出方法まとめ](/p/rnai4ko/) [具体例](/p/8j4k3iy/)
  - [`DataFrame` のデータ加工方法まとめ](/p/m4ggdkx/)
  - [`DataFrame` の欠損値 (NaN) の扱い方まとめ](/p/rtc38u8/)
- [pandas の `DataFrame` のカラム（列）名やインデックス（行）名を変更する (`df.rename`, `df.add_prefix`, `df.add_suffix`)](/p/3g687f5/)
- [pandas の `DataFrame` から数値カラムだけ抽出する (`df.select_dtypes`)](/p/pa2us3b/)
- [pandas の `DataFrame` のカラムのデータ型を変更する (`df.astype`)](/p/fk2e74z/)

{{% private %}}
- [(DRAFT) pandas の `DataFrame` の統計値を求める方法のまとめ](/p/q2pm7r3/)
{{% /private %}}

### Matplotlib

- [Matplotlib の `Axes`、`Figure`、`pyplot` の関係を理解する](/p/59ruo5m/)

{{% private %}}
- [(DRAFT) Matplotlib チートシート](/p/ghch694/)
{{% /private %}}

### scikit-learn

- [scikit-learn 組み込みのデータセットを扱う (`sklearn.dataset`)](/p/o7qnbeg/)

{{% private %}}
- 前処理 (sklearn.preprocessing)
  - [scikit-learn で特徴量の標準化を行う](/p/btov27k/)
{{% /private %}}



wxPython
----

### 基本ウィンドウとレイアウト

* [wxPython によるウィンドウ表示の基本](wxpython/basic.html)
* [wxPython - Dialog（ダイアログ）](wxpython/dialog.html)
* [wxPython - Frame（フレーム）](wxpython/frame.html)
* [wxPython - Layout（レイアウト）](wxpython/layout.html)
* [wxPython - SplitterWindow（スプリッターウィンドウ）](wxpython/splitterwindow.html)

### 各種コンポーネント

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

### その他の wxPython メモ

* [wxPython のイベントハンドラを理解する](wxpython/event-handler.html)
* [wxPython アプリケーションのエラー出力方法を理解する](wxpython/error-output.html)
* [wxPython によるグラフィックス処理を理解する](wxpython/graphics.html)
* [wxPython でマウスカーソル関連のイベントを処理する](wxpython/mouse-event.html)
* [wxPython のコンポーネントをドラッグで動かせるようにする](wxpython/drag-component.html)
* [wxPython 関連コラム](wxpython/column.html)


コラム
----

* [Python 開発の歴史](column/history.html)
* [Python のインデントによる構造化に慣れる](column/indent.html)
* [Python のリスト内包表記に慣れる](column/list-comprehension.html)

