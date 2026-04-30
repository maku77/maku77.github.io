---
title: "Windows"
url: "/windows/"

categoryName: "まくまく Windows ノート"
categoryUrl: "/windows/"
categoryIcon: _index.svg
---

Windows バッチファイル
----

### 環境
* [Windows で Ruby や Python のスクリプトを組み込みコマンドのように使用する](/p/256385g/)
* [環境変数が設定されているかどうか調べる (`if defined`)](/p/bdima57/)
* [バッチファイルが管理者として実行されているか調べる](/p/5xohfdp/)

### 構文
* [バッチファイルの `FOR` ループ (1) 数値、ファイル集合、ディレクトリ集合のループ処理](/p/xrjbcty/)
* [バッチファイルの `FOR` ループ (2) テキストファイル、コマンド出力を 1 行ずつループ処理 (`for /f`)](/p/tpd5xxt/)
* [バッチファイルを途中で終了する (`exit /b`)](/p/66q7m2j/)
* [バッチファイルでサブルーチンを定義する、別のバッチファイルを呼び出す (`call`, `setlocal`)](/p/jz8fwmk/)
* [バッチファイル内で長いコマンドを複数行に分けて記述する](/p/bat276x/)

### 入出力 (I/O)
* [バッチファイルでコマンドライン引数を扱う](/p/s7r9r9p/)
* [バッチファイルでプロンプトを表示してユーザー入力を促す (`set /p`)](/p/2dn6njw/)
* [標準出力 (stdout) と標準エラー (stderr) への出力をファイルに保存する](/p/knrzjz9/)
* [`echo` で改行だけ出力する／改行しないで出力する](/p/ukdcdw8/)
* [複数のコマンドの出力をまとめてリダイレクトする](/p/mwo8tt5/)
* [バッチファイルでコマンドの実行結果を変数に格納する](/p/srpdmrg/)

### ファイル／ディレクトリ／パス
* [バッチファイルのファイル名やディレクトリ名を取得する (`%0`)](/p/76ipisd/)
* [`DIR` コマンドでファイルやディレクトリを再帰的に検索する (`dir /b`)](/p/chexxgu/)
* [`DIR` コマンドでファイルやディレクトリを OR 検索する](/p/gpuitde/)
* [カレントディレクトリ以下のファイルやディレクトリを順に処理する (`for /r`)](/p/nmp2o4d/)
* [`findstr` コマンドでファイル内の文字列を検索する](/p/iuxsf5h/)
* [既にディレクトリが存在している場合の `mkdir` のエラーを抑制する](/p/2sarvou/)
* [`copy` コマンドで複数のファイルを連結する](/p/w7erbwg/)

### 日時
* [バッチファイル内で日時を出力する (`%DATE%`, `%TIME%`)](/p/thinhxh/)
* [バッチファイル内で日時を元にファイル名を作成する](/p/4t8f54c/)
* [バッチファイル内で1日後の日時を取得する（時刻の演算）](/p/iyjskqt/)


コマンドプロンプト
----
* [コマンドプロンプトを管理者権限 (`Administrator`) で起動する](/p/36au7y3/)
* [コマンドプロンプトの文字色と背景色を変更する (`COLOR`)](/p/s4m64bm/)
* [コマンドプロンプトのプロンプト表示を変更する (`PROMPT`)](/p/o7sfc8a/)
* [コマンドプロンプトからウィンドウサイズを変更する (`MODE`)](/p/fdsy5gc/)
* [`doskey` を使ってコマンドプロンプト用のマクロを登録する](/p/3u7yuu2/)
* [コマンドプロンプトから複数のコマンドを並列実行する (`start`)](/p/dgnh7w4/)

### ファイル／ディレクトリ／パス
* [カレントディレクトリのパスを取得する (`%CD%`)](/p/imkepzz/)
* [コマンドプロンプトでディレクトリ内のファイルをすべて削除する (`del`, `rmdir`)](/p/pv8cbr7/)
* [コマンドプロンプトからインターネット上のファイルをダウンロードする (`curl`, `Invoke-WebRequest`, `bitsadmin`)](/p/xd3b8uo/)


Windows の便利操作／ショートカット
----
* [Windows でキーボード操作だけで様々なフォルダを素早く開く](/p/acsfhxi/)


Windows の設定／管理／セキュリティ
----

* タスク管理
  * [Windows で実行中のプロセス（タスク）の一覧を表示する (`tasklist`)](/p/6c6eu8p/)
  * [Windows で実行中のプロセス（タスク）を終了 (kill) する (`taskkill`)](/p/agnokb9/)
  * [Windows で任意のコマンド（タスク）を自動実行する (`schtasks`)](/p/ntrrqvz/)
* [Windows OS のバージョン情報をコマンドラインから調べる (`ver`, `wmic`)](/p/yrxkvac/)
* [NIC に割り当てられた MAC アドレスを調べる (`getmac`)](/p/p4hpf3r/)
* [コマンドラインから Windows サービスを管理する](/p/fsge3yg/)
* [Administrator で Windows にログオンできるようにする](/p/62ksygm/)
* [Windows のアカウント名を変更する](/p/fjnp64s/)
* [ドメインユーザーにローカル PC の Administrator 権限を与える](/p/p43yih6/)
* [日本語キーボードのノート PC で英字配列の USB キーボードを使用する](/p/ybmz5ak/)
* [Windows のユーザのパスワードの有効期限を無期限に設定する](/p/grjsduo/)
* [`xcopy` でディレクトリ内のファイルをバックアップする](/p/ewszcxd/)
* [NTP による時刻同期情報を取得する (`w32tm`)](/p/rjxzdgh/)

### ネットワーク
* [Windows ファイアウォールで特定のポートを開放する](/p/ktgsp4e/)
* [ネットワーク切り替え時に PC 名ですぐに検索できるようにする（NetBios 名の再登録）](/p/uxqyis7/)
* [Windows のログオフ時にネットワーク接続を維持する](/p/u7mm35a/)
* [Windows の DNS キャッシュをクリアする](/p/kwbibnj/)


MS Office
----

### Excel
- [Excel のブック、ワークシート、セルの関係を理解する](/p/wtdpxmr/)
- [Excel の行・列の表示／非表示をショートカットキーで素早く切り替える](/p/w6yequ6/)
- [Excel で点数などから順位を求める（`RANK.EQ` 関数）](/p/m9znv3s/)
- [Excel で順位セルの値通りに並び替えて表示する](/p/t2uzpn2/)

### PowerPoint
- [PowerPoint で使いこなすとかっこいいショートカット](/p/8t9df2e/)
- [SVG ファイルを EMF ファイルに変換して PowerPoint に貼り付ける](/p/vvmudxy/)
- [PowerPoint プレゼン資料でおすすめのフォント設定](/p/fxeyqgs/)

### Word
- [Word で章・節・項の見出しを作成する](/p/y4kvrrg/)
- [Word で章・節・項の「見出しマップ」を表示する](/p/9j2kkw4/)
- [Word で章・節・項の見出しの表示スタイルを変更する](/p/vdddvku/)
- [Word で章・節・項の見出しに連番（段落番号）を自動で振る](/p/nct57dw/)
- [Word で章・節・項の見出しから目次を作成する](/p/v7cyvgr/)

### Outlook
- [Outlook で返信メールのメッセージの行頭に引用記号 (>) を付ける](/p/orfioq3/)
- [Outlook の予定表を他のユーザから見えるようにする](/p/72sdgsa/)

（VBA マクロに関しては [こちら](/vba/) を参照）


Windows API
----

### Legacy API
- [プロセス ID とプロセスハンドル](/p/vt4pow4/)
- [Windows の静的ライブラリ (lib) と動的ライブラリ (dll)](/p/73ibsy6/)
- [指定したウィンドウ (HWND) を確実にアクティブにする](/p/vgpuvtq/)
- [現在使用中の Windows OS のバージョンを調べる (`GetVersionEx`)](/p/79zztwr/)
- [Windows API ですべてのウィンドウを列挙し、特定の条件にマッチするウィンドウを取得する (`EnumWindows`)](/p/8jm46o5/)
- [Windows API でスクリーンセーバーの起動時間を取得／設定する (`SystemParametersInfo`)](/p/v2af5j6/)

### .NET

- 日付／時刻
  - [.NET - 時刻部分をすべて 0 にした DateTime インスタンスを作成する](/p/m77q6nx/)
  - [.NET - DateTime インスタンスと文字列を相互に変換する](/p/d2khvev/)
  - [.NET - 現地時刻（ローカルタイム）と世界協定時刻 (UTC) の扱いを理解する](/p/pmua6r4/)
- その他
  - [.NET - System.Data.SQLite で SQLite データベースを使用する](/p/r3tew6w/)
  - [.NET - XML 形式の設定ファイルを XPath で操作するサンプル](/p/yq6kawu/)
  - [.NET - ツリーノードのドラッグ＆ドロップの流れ](/p/hepcaw7/)
  - [.NET - ツリーノードを右クリックしたときにそのノードを選択状態にする](/p/7njuocg/)

