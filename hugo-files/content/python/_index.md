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
  - [Visual Studio Code で Python 用の Linter ＆フォーマッターの Ruff を使う](https://maku.blog/p/6hnm4hy/)
  - [Visual Studio Code で Python 用のフォーマッター (Black) を使う](https://maku.blog/p/4oybku6/)
- ドキュメンテーション
  - [ドキュメンテーションコメント (docstring) を記述する](/p/y2biqz7/)
  - [Windows で pydoc コマンドを使用できるようにする](/p/6h2izfh/)
  - [関数やクラスのソースコードを確認する (`insepct`)](/p/xbucsaq/)

### 開発ツール／デバッグ／ユニットテスト <!-- env -->

- [Python プロジェクト用の爆速パッケージマネージャ uv を導入する](/p/fjsfjpw/)
- 実行環境／仮想環境
  - [python コマンドのバージョンを切り替える (`pyenv`, `py`)](/p/x4z298a/)
  - [Python の実行環境を `venv` で切り替える（仮想環境）](/p/wozpogm/)
  - [Python の実行環境を `virtualenv` で切り替える（仮想環境）（Python 3.2 以前）](/p/yqjs3aw/)
- パッケージ
  - [Python のパッケージ管理ツール (`pip`) の使い方](/p/7o9q8p6/)
  - [Python スクリプトを Windows の実行ファイル (.exe) に変換する (`py2exe`)](/p/kyj2izf/)
- 対話型シェル
  - [Python の対話型シェルのスタートアップ処理を設定する (`PYTHONSTARTUP`)](/p/6k5m4jy/)
  - [Python の対話型シェルで使える小技](/p/dsbsaq7/)
- テスト
  - [Python でユニットテストを記述する (`unittest`)](/p/fueucsa/)
- パフォーマンス
  - [Python コードの実行時間を計測する (`datetime.now`)](/p/ubtbs9p/)

### python コマンド／実行環境 <!-- env -->

- [python コマンドでワンライナーを実行する](/p/oi67p5c/)
- [Python スクリプトの中で Python のバージョンを確認する (`sys.version`)](/p/6b2qqny/)
- [拡張子に関連付けられたアプリケーションでファイルを開く (`os.system`)](/p/okhfyfx/)
- [Python の最大再帰数を調べる／変更する](/p/swuiipr/)
- [Python から外部プログラムを起動する (`subprocess.run`)](/p/gfpprmw/)
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
  - [Python の型アノテーションのまとめ](/p/rx77njz/)
  - [Python の型一覧／変数の型を調べる (`type`)](/p/a8cta8q/)
  - [Python で定数を定義する (`typing.Final`)](/p/37hwzxi/)
- 制御構文
  - [Python の条件分岐いろいろ（if, switch, 三項演算子）](/p/se5cwgm/)
  - [全てが真 (`all`)、少なくとも一つが真 (`any`) かどうか調べる](/p/z6xo3xs/)
  - [`for ~ in range` による数値のループ処理いろいろ](/p/eg6sr6t/)
- メソッド / 関数
  - [Python の関数をキーワード引数を使って呼び出す](/p/jf6kyao/)
  - [Python の関数で可変長引数を扱う (`*args`, `**kwargs`)](/p/rcaip63/)
- クラス
  - [クラス定義の基本 (`class`)](/p/ru9mxam/)
  - [オブジェクトを `print` 関数で出力できるようにする (`__str__`)](/p/uatcsaq/)
  - [オブジェクトの属性に名前でアクセスする (`getattr`, `setattr`)](/p/r3m4k2h/)
  - [`dataclass` デコレーターで簡単にデータクラスを定義する](/p/xqkgvb6/)


型
----

### 数値 <!-- numstr -->

* [2進数、8進数、16進数の数値リテラル](/p/3bkr3ka/)
* [数値と文字（文字列）を変換する (`chr`, `ord`, `int`, `hex`, `oct`, `bin`)](/p/35h86i9/)
* [整数の割り算の結果を整数 or 小数点数で取得する](/p/ac5cz6u/)
* [小数点以下を四捨五入する/切り捨てる/切り上げる (`round`, `math.floor`, `math.ceil`)](/p/73g4beh/)
* [独自オブジェクトの配列から最小値、最大値を求める (`min_by`/`max_by`)](/p/b22gfoi/)
* [リスト要素の合計値を求める (`sum`)](/p/6ta64b8/)
* [ランダムな数値（乱数）を生成する (`randrange`, `randint`, `random`, `uniform`)](/p/nx5xama/)
* [階乗を求める (`math.factorial`)](/p/jebrm6n/)
* [順列の数 (nPr) を求める／組み合わせの数 (nCr) を求める](/p/vbrsmed/)

### 文字列 <!-- numstr -->

- [Python の文字列リテラルいろいろ](/p/syyiqcm/)
- [文字列の中で変数を展開する（書式文字列によるフォーマット） (`str#format`, `%` 演算子)](/p/nmz89p9/)
- [同じ文字列を繰り返した文字列を作成する (`str * N`)](/p/6zogi6n/)
- [文字列の長さを調べる (`len`)](/p/f4fafpt/)
- [文字列がある文字列で始まっている/終わっているかを調べる (`startswith`, `endswith`)](/p/bu3u94c/)
- [文字列が正規表現に完全一致するか調べる (`re.fullmatch`)](/p/dbzfge7/)
- [文字列内の部分文字列を検索する/抽出する（正規表現）](/p/k4b46pw/)
- [文字列を置換する (`str.replace`, `re.sub`, `re.subn`)](/p/wnpquuy/)
- 分割
  - [文字列をデリミタで分割する (`split`)](/p/hhyq5vh/)
  - [文字列を改行で分割して 1 行ずつのリストにする (`splitlines`)](/p/q76q3wm/)
  - [文字列を 1 文字ずつに分割してリストにする](/p/pjv9mw5/)
- [リスト内の要素を結合して文字列にする (`join`)](/p/wqtsp93/)
- [文字列を逆順にする（反転させる）](/p/x4zi6b4/)
- [ランダムな文字列を生成する (`random.choice`)](/p/vfp5zx9/)
- [Python で UUID を生成する (`uuid.uuid4`)](/p/fje63fe/)
- JSON
  - [JSON テキスト ⇔ Python オブジェクトの相互変換 (`json.loads`, `json.dumps`)](/p/gwfweub/)

### シーケンス (Lists and Tuples) <!-- list -->

* [リストとタプルの違い](/p/wc2h2a7/)
* リストとタプルの生成
  * [リストやタプルをサイズを指定して作成する (`[] * N`)](/p/5vv2dfx/)
  * [連番からなるリストを生成する (`range`)](/p/pf6tfks/)
* [リストに要素を追加する／リストを結合する (`append`, `extend`, `+`)](/p/hz9uhyh/)
* [リスト／タプルをループで処理する](/p/76d29hw/)
* [リスト／タプルのサイズを取得する (`len`)](/p/7o7z4kg/)
* [リスト／タプル内の要素をランダムで取得する (`random.choice`, `random.choices`, `random.sample`)](/p/nwo79zc/)
* [リストを昇順ソート／降順ソートする (`list.sort`/`sorted`)](/p/cqtwqgx/)
* [リストをシャッフルする (`random.shuffle`)](/p/svic2nf/)
* [リストから特定の値を持つ要素を削除する (`list.remove`)](/p/m69amk4/)
* [リストをフィルタして条件に一致する要素を抽出する (`filter`)](/p/3ytbivi/)
* [リスト内の特定の値を持つ要素の数を数える (`list.count`)](/p/rx847s5/)
* [リスト内の条件に一致する要素の数を数える](/p/znmhxsw/)
* [リスト内の要素をまとめて変更する (`map`)](/p/qnkb4re/)
* [二次元配列を作成する](/p/tb3g9jx/)
* [二次元配列をソートする](/p/uoyq69m/)
* [2 つのリストに重複する要素を抽出する（`set` と `&`）](/p/58ctg7k/)

### 辞書・ディクショナリ (Dictionary) <!-- dictionary -->

- [dictionary（辞書）チートシート](/p/2tyvjmh/)
- [dictionary（辞書）の基本](/p/zpdyxso/)
  - [dictionary オブジェクトを作成する (`{}`, `dict`)](/p/zpdyxso/#create)
  - [dictionary の要素を参照・変更する (`[]`, `get`)](/p/zpdyxso/#get)
  - [dictionary の要素数を取得する (`len`)](/p/zpdyxso/#len)
  - [dictionary に指定したキーが存在するか調べる (`in`)](/p/zpdyxso/#in)
  - [dictionary の要素を削除する (`del`)](/p/zpdyxso/#del)
  - [dictionary をループ処理する (`items`, `enumerate`)](/p/zpdyxso/#loop)
- [dictionary の要素をソートして出力する (`sorted`)](/p/qqkggoz/)
  - [キーでソートしてループ（昇順/降順）](/p/qqkggoz/#sort-by-key)
  - [値でソートしてループ（昇順/降順）](/p/qqkggoz/#sort-by-value)
- [キーのリストと値のリストを組み合わせて dictionary を生成する (`zip`)](/p/cmy6ar3/)
- [dictionary からキーや値のリストを作成する (`keys`, `values`, `items`)](/p/83e3wrw/)
- [2 つの dictionary をマージする (`update`)](/p/ds9wgfz/)
- [dictionary にキーが存在しない場合のみ新しい値を格納する (`setdefault`)](/p/bq6yzpr/)
- [dictionary の内部的な要素順序を変更する (`dict`, `OrderedDictionary`)](/p/vexfweu/)

### セット (Set) <!-- set -->

- [set オブジェクトの基本](/p/m8k282q/)
- [set オブジェクトで集合演算を行う](/p/h3jqpp9/)


入出力 (I/O) <!-- io -->
----

### コマンドライン引数／標準入力／標準エラー出力

- コマンドライン引数
  - [コマンドライン引数を扱う (`sys.argv`)](/p/ybxfwev/)
  - [`argparse` モジュールでコマンライン引数を扱う](/p/o6q8p6m/)
- [キーボードからのユーザ入力を取得する (`input`, `getpass`)](/p/bfev6xf/)
- print
  - [`print` 関数の改行を抑制する](/p/ergteiz/)
  - [`print` 関数に複数のパラメータを渡した時の接続文字列を変更する (`sep`)](/p/29vbxrz/)
  - [標準入力から読み込む、標準入力とファイル入力に両対応する (`sys.stdin`, `fileinput`)](/p/3966z2v/)
  - [標準エラー出力へ出力する (`sys.stderr`)](/p/m3si4w6/)
  - [任意のオブジェクトを見やすく整形して出力する (`pprint`, `pformat`, `PrettyPrinter`)](/p/ctm63oi/)

### パス

- [実行中のスクリプトのファイル名やパスを取得する (`__file__`)](/p/28q9cob/)
- [パスからファイル名だけを取り出す (`basename`)](/p/9ysxj57/)
- [パスを結合する (`join`)](/p/qogtc4k/)
- [パスを指定したディレクトリからの相対パスに変換する (`os.path.relpath`)](/p/o4a37zc/)

### ファイル／ディレクトリ

- 列挙
  - [ディレクトリ内のファイルを列挙する (`os.listdir`, `os.walk`, `glob`)](/p/thhwr4b/)
  - [指定した拡張子や名前のファイルを列挙する (`glob.iglob`, `glob.glob`)](/p/6vpyp4z/)
- コピー／移動／リネーム
  - [ファイル／ディレクトリの名前を変更する (`os.rename`, `os.renames`)](/p/9aqzppe/)
  - [ファイル／ディレクトリをコピー、移動する (`shutil.copyfile`, `shutil.copy`, `shutil.copytree`, `shutil.move`)](/p/wyxzbdw/)
- 作成／削除
  - [ディレクトリを作成する (`os.mkdir`, `os.makedirs`)](/p/3bp92ih/)
  - [ディレクトリを削除する (`os.rmdir`)](/p/tn9x45h/)
- [ファイル／ディレクトリの存在を確認する (`os.path.exists`, `os.path.isfile`, `os.path.isdir`)](/p/tr3cmu5/)
- [Python スクリプトが格納されているディレクトリのパスを取得する](/p/aveia2i/)
- [テンポラリファイル／ディレクトリを作成する (`tempfile`)](/p/co7o5k2/)

### ファイルの読み書き

- 基本的なファイル読み書き
  - [テキストファイルを読み込む (`open`, `read`, `readline`, `readlines`)](/p/f4ckt29/)
  - [テキストファイルを書き込む (`open`, `write`, `writelines`)](/p/nmv4cjr/)
- CSV/TSV
  - [CSV ファイルや TSV ファイルを読み込む (`csv.reader`)](/p/cfqer8d/)
- JSON ファイル
  - [JSON 形式のテキストファイルを読み書きする (`json.load`, `json.dump`)](/p/xhyhzfv/)
  - [Python のワンライナーコマンドで JSON ファイルを整形する (`json.tool`)](/p/an8o6m4/)
- YAML ファイル
  - [YAML ファイルを扱う](/p/fy8j9h2/)
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
  - [Excel ファイルを読み込む (`xlrd`)](/p/enq2iz4/)
- ZIP/JAR ファイル
  - [ZIP/JAR ファイルの内容を読み込む (`zipfile`)](/p/gxajt4d/)
  - [ZIP ファイルを作成・展開する (`shutil.make_archive`, `shutil.unpack_archive`)](/p/euevcsa/)
- その他のファイル、応用サンプル
  - [HTML ファイルや Markdown ファイルやプレーンテキストに変換する (`unstructured`)](/p/uivwecs/)
  - [Java や C/C++ のコード中のコメントを削除する](/p/cwgffj8/)
  - [複数ファイルの文字列をまとめて置換する (`glob`, `re`)](/p/xwog7ip/)
  - [複数ファイルをまとめて grep する (`re.search`, `glob.iglob`)](/p/vcozyix/)


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
- [requests パッケージのチートシート](/p/succ4mb/)
  - [requests パッケージによる HTTP 通信の例](/p/r7q8q7o/)
- サンプルコード
  - [Web サイトのサイトマップ (sitemap.xml) からすべての URL を抽出する](/p/bp9q8p6/)

### HTTP サーバー / Web API サーバー

- [Python で簡易的な HTTP サーバを立てる (`http.server`, `SimpleHTTPServer`)](/p/rr3cmu5/)
- [Python の FastAPI フレームワークで Web API を実装する](/p/mq4c72g/)


未分類 <!-- misc -->
----

- [ハッシュ値 (MD5/SHA-1/SHA-256/SHA-512) を求める (`hashlib`)](/p/gn4cn3s/)
- データベース / SQL
  - [Python で SQLite データベースを使用する](/p/g5xg2nc/)


日時（日付／時刻）
----

* [Python で日時（日付／時刻）の情報を扱う (`datetime`, `date`, `time`, `timedelta`)](/p/spfor2m/)
* [現在時刻から YYYY-MM-DD のような日付文字列を生成する (`strftime`)](/p/sh36h2z/)


画像処理
----

### scikit-image

- [scikit-image で画像処理](/p/apkiihk/)
- [scikit-image の data パッケージに含まれるテスト画像を使用する](/p/nz8zoxu/)

{{% private %}}
- [(DRAFT) scikit-image チートシート](/p/bwemnew/)
{{% /private %}}

### OpenCV

- [OpenCV: 画像を閾値で二値化（白黒画像化）する (`cv2.threshold`)](/p/i26mc77/)

{{% private %}}
- [(DRAFT) OpenCV チートシート](/p/dgkrqjo/)
- [(DRAFT) OpenCV: 画像内の物体の輪郭を検出する (`cv2.findContours`)](/p/ocef2kr/)
{{% /private %}}

### keras

- [MNIST の手書き数字データをダウンロードして表示する (`keras.datasets.mnists`)](/p/849syej/)

{{% private %}}
- [(DRAFT) Keras チートシート](/p/niwn3x5/)
{{% /private %}}


データ分析 (NumPy / pandas / Matplotlib / scikit-learn)
----

### 共通

- [NumPy / pandas / Matplotlib の概要とインストール](/p/qs6iv8j/)

### NumPy

- [NumPy 配列 (`ndarray`) の基本](/p/jwfxfvd/)
- [NumPy 配列 (`ndarray`) の作成方法まとめ](/p/htducs9/)

{{% private %}}
- [NumPy チートシート](/p/vy94u2h/)
{{% /private %}}

### pandas

- [pandas でラベル付き 1 次元データを扱う (`Series`)](/p/wbudtbr/)
- [pandas でラベル付き 2 次元データを扱う (`DataFrame`)](/p/watbs9p/)
- [pandas で CSV/TSV ファイルを読み込む (`pd.read_csv`, `pd.read_table`)](/p/78ns8r5/)
- [pandas で CSV/TSV ファイルを出力する (`pd.to_csv`)](/p/ioj6bqf/)
- [pandas で DataFrame をループ処理する (`iterrows`, `items`)](/p/rfduqhx/)
- pandas チートシート
  - [`DataFrame` のデータ抽出方法まとめ](/p/rnai4ko/) [具体例](/p/8j4k3iy/)
  - [`DataFrame` のデータ加工方法まとめ](/p/m4ggdkx/)
  - [`DataFrame` の欠損値 (NaN) の扱い方まとめ](/p/rtc38u8/)
- [pandas の `DataFrame` のカラム（列）名やインデックス（行）名を変更する (`df.rename`, `df.add_prefix`, `df.add_suffix`)](/p/3g687f5/)
- [pandas の `DataFrame` から数値カラムだけ抽出する (`df.select_dtypes`)](/p/pa2us3b/)
- [pandas の `DataFrame` のカラムのデータ型を変更する (`df.astype`)](/p/fk2e74z/)

{{% private %}}
- [(DRAFT) `DataFrame` のデータ集計方法まとめ](/p/q2pm7r3/)
{{% /private %}}

- [Python データ分析: カテゴリデータを扱う](/p/vopvucs/)

### Matplotlib

- [Matplotlib の `Axes`、`Figure`、`pyplot` の関係を理解する](/p/59ruo5m/)
- [Matplotlib によるプロットの例（散布図）](/p/4xyn2kv/)

{{% private %}}
- [(DRAFT) Matplotlib チートシート](/p/ghch694/)
{{% /private %}}

### scikit-learn

- [scikit-learn 組み込みのデータセットを扱う (`sklearn.dataset`)](/p/o7qnbeg/)

{{% private %}}
- 前処理 (sklearn.preprocessing)
  - [scikit-learn で特徴量の標準化を行う](/p/btov27k/)
{{% /private %}}

{{% private %}}
### PyTorch
- [PyTorch チートシート](/p/c8rd5tg/)
{{% /private %}}


wxPython
----

### 基本ウィンドウとレイアウト

* [wxPython によるウィンドウ表示の基本](/p/tvwz23r/)
* [wxPython - Dialog（ダイアログ）](/p/mop9i6p/)
* [wxPython - Frame（フレーム）](/p/4zpyvdy/)
* [wxPython - Layout（レイアウト）](/p/e4c6ef2/)
* [wxPython - SplitterWindow（スプリッターウィンドウ）](/p/vorgsyf/)

### 各種コンポーネント

* [wxPython - Button（ボタン）](/p/h48tfez/)
* [wxPython - CheckBox（チェックボックス）](/p/nr3yh3z/)
* [wxPython - CheckListBox（チェック付きリストボックス）](/p/h7yciir/)
* [wxPython - Choice（プルダウンボックス）](/p/n7ri6ni/)
* [wxPython - ComboBox（コンボボックス）](/p/kcgbgcy/)
* [wxPython - Gauge（プログレスバー）](/p/6pt9isa/)
* [wxPython - Grid（グリッド）](/p/68p4zmq/)
* [wxPython - HtmlWindow（HTML ウィンドウ）](/p/rktuz7x/)
* [wxPython - ListBox（リストボックス）](/p/pc4be59/)
* [wxPython - Menu（メニュー）](/p/vn9evh6/)
* [wxPython - RadioButton（ラジオボタン）](/p/rknynxq/)
* [wxPython - Slider（スライダー）](/p/powg2k5/)
* [wxPython - SpinCtrl（スピンコントロール）](/p/nfe35fm/)
* [wxPython - StaticText（変更不可のラベル）](/p/m5kkuhu/)
* [wxPython - StatusBar（ステータスバー）](/p/3ox8ah2/)
* [wxPython - TextCtrl（テキスト入力用）](/p/3z7rz6i/)

### その他の wxPython メモ

* [wxPython のイベントハンドラを理解する](/p/jdcp2xr/)
* [wxPython アプリケーションのエラー出力方法を理解する](/p/tv6y4pv/)
* [wxPython によるグラフィックス処理を理解する](/p/bbta2ho/)
* [wxPython でマウスカーソル関連のイベントを処理する](/p/igqzo97/)
* [wxPython のコンポーネントをドラッグで動かせるようにする](/p/tkxvf77/)
* [wxPython 関連コラム](/p/y3bknpi/)


コラム
----

* [Python 開発の歴史](/p/i6k5xtf/)
* [Python のインデントによる構造化に慣れる](/p/cmeia2b/)
* [Python のリスト内包表記に慣れる](/p/6ppwdhi/)

