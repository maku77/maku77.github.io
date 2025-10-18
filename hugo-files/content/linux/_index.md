---
title: "Linux/Shell"
url: "/linux/"

categoryName: "まくまく Linux/ShellScript ノート"
categoryUrl: "/linux/"
categoryIcon: _index.svg
---

シェルスクリプト / Bash プログラミング
----

* [シェルスクリプトのコーディングスタイル](/p/oukq4dh/)

### 変数
* [変数の基本](var/basic.html)
* [定数を定義する (readonly)](var/read-only.html)
* [シェル変数/環境変数がセットされているか調べる](var/check-if-var-is-set.html)
* [Bash の変数展開機能を活用する（文字列の置換、デフォルト値など）](var/expansion.html)
* 配列
  * [配列を作成する](var/create-array.html)
  * [配列をループ処理する](var/loop-array.html)
  * [配列のサイズ（要素数）を調べる](var/array-size.html)
  * [配列に要素を追加する](var/add-elem-to-array.html)
  * [配列と配列を結合する](var/concat-arrays.html)
* 数値
  * [数値変数を加算／減算する](var/add-and-sub.html)
  * [ランダムな数値を取得する ($RANDOM)](number/random.html)

### 制御構文
* [`if-else` による分岐処理](/p/seogpah/)
* [`case` による分岐処理](/p/hro2nd4/)
* [`for/while` によるループ処理](/p/eimpfje/)
  * [ループ内での複数の出力をまとめてリダイレクト、パイプ処理する](/p/tmm4zvz/)

### 起動時の処理 <!-- startup -->
* コマンドライン引数
  1. [コマンドライン引数の基本 (`$1`, `$@`, `$*`)](/p/c2kx7er/)
  1. [コマンドライン引数の数が正しいかチェックする (`$#`)](/p/4tbzpyf/)
  1. [名前付きのコマンドラインオプションを扱う (`getopts`)](/p/2fyizgw/)
* [ある環境変数が定義されているかチェックする (`test -z`)](/p/r3myewb/)
* [ある外部コマンドが使用できるかチェックする (`type -P`)](/p/xi5sjju/)

### 関数
* [関数を定義する (`function`)](/p/em67j5z/)
* [関数へパラメータを渡す (`$1`, `$2`, `$3`)](/p/ecj6wbo/)
* [関数内でローカル変数を扱う (`local`)](/p/52i36fy/)
* [関数から戻り値を返す (`$?`)](/p/8uionzb/)
* [関数のデフォルト引数を定義する](/p/7ovg5nr/)

### パス
* [絶対パスと相対パスの変換 (`basename`, `dirname`)](path/absolute-path-to-relative-path.html)
* [指定したファイルの絶対パスを取得する](path/absolute-path-of-file.html)
* [カレントディレクトリの絶対パスを取得する](path/absolute-path-of-current-dir.html)
* [実行中のシェルスクリプトのファイル名を取得する](path/path-of-script.html)
* [実行中のシェルスクリプトがあるディレクトリの絶対パスを取得する](path/absolute-path-of-script-dir.html)
* [実行中のシェルスクリプトがあるディレクトリに移動する](path/move-to-script-dir.html)

### 入出力 (I/O) <!-- io -->
* [echo の結果を標準エラー出力 (stderr) に出力する (`1>&2`)](/p/q2k3j2h/)
* [echo で出力した文字列の中の連続するスペースが 1 つのスペースになってしまうのを防ぐ](/p/25gqyai/)
* [echo で出力する文字の色を変える](/p/fufwdub/)
* [ユーザ入力を取得する (`read`)](/p/6m6n5k3/)
* [ディレクトリ内のファイルを順に処理する (`for`, `while`)](/p/or3cmv6/)
* [外部コマンドの実行結果を 1 行ずつ処理する (`for`, `while`, `read`)](/p/s9r9q7n/)
* [テキストファイルを 1 行ずつ読み込む (`read`)](/p/co9p7nj/)
* [`expect` で外部コマンドの出力を待機する](/p/3i3j2hx/)

### 日時
* [今日の日付から YYYYMMDD のような文字列を作成する (`date`)](time/date-string.html)

### トラブルシューティング
* [改行コードが原因の関数定義の syntax error](trouble/function-syntax-error.html)


D-Bus
----

### D-Bus 全般
* [D-Bus 関連用語](dbus/words.html)
* [D-Bus 関連リンクと関連ツール](dbus/links.html)
* [GDBus と dbus-glib](dbus/gdbus-and-dbus-glib.html)

### dbus-glib
* [dbus-glib 開発用ライブラリをインストールする](dbus/dbus-glib-1.html)
* [dbus-glib で method call を実装する（ヘッダファイルの生成）](dbus/dbus-glib-2.html)
* [dbus-glib で method call を実装する（サーバ側の実装）](dbus/dbus-glib-3.html)
* [dbus-glib で method call を実装する（クライアント側の実装）](dbus/dbus-glib-4.html)

### GDBubs
* [GDBus で簡単な P2P（クライアント＆サーバ）アプリを実装する](dbus/gdbus.html)
* [GDBus サーバから signal を発行する](dbus/gdbus-emit-signal.html)
* [GDBus でバイナリデータ（バイト配列）を受け取る方法](dbus/gdbus-receive-binary.html)
* [glib の GVariant を使ってみる](dbus/gvariant.html)

### D-Bus Java
* [D-Bus Java をインストールする](dbus/dbus-java-install.html)
* [D-Bus Java で Session bus に接続してメソッドを呼び出す](dbus/dbus-java-session-bus.html)
* [D-Bus Java で P2P D-Bus サーバに接続してメソッドを呼び出すサンプル](dbus/dbus-java-p2p-client.html)

### dbus-python
* [dbus-python の公式サンプルコード](dbus/dbus-python/sample.html)
* [dbus-python で Session bus を使用するサーバ＆クライアントを実装する（単純なメソッドの実装）](dbus/dbus-python/server-and-client-1.html)
* [dbus-python で Session bus を使用するサーバ＆クライアントを実装する（シグナルの実装）](dbus/dbus-python/server-and-client-2.html)
* [dbus-python で D-Bus の P2P クライアントを実装する](dbus/dbus-python/p2p-client.html)


Linux システム／管理
----
### コマンド
* [Linux カーネル／OS のバージョンを調べる](linux-version.html)
* [bash のバージョンを調べる (`$BASH_VERSION`)](bash-version.html)
* [Linux の各種 ID（プロセス ID やユーザ ID など）についてのメモ](linux-ids.html)
* [カーネルに渡されたパラメータを調べる (/proc/cmdline)](kernel-params.html)
* [Linux のメモリ情報を調べるコマンド](memory.html)
* [NTP でシステム時刻を設定する](settings/ntp.html)
* [ディレクトリ内のファイルを zip ファイルにバックアップする](/p/3qnenzf/)
* [Deep Learning や仮想通貨のマイニング時に CPU 使用率が 100% になってしまうのを防ぐ (cpulimit)](/p/t8yiqm8/)

### プログラミング
* [Linux システムコールを使用して core dump を吐かないようにする (setrlimit)](/p/afze7gn/)


Linux コマンド全般
----
* [Linuxコマンド: ファイルやディレクトリを検索する (`find`, `grep`)](/p/hudubr8/)
* [Windows で Linux 系のコマンドを使用できるようにする (Gow)](/p/qijsiy5/)
* [`rsync` コマンドでディレクトリを同期する](/p/dvd889d/)
* [行番号付きでテキストファイルの内容を出力する (`cat -n`, `cat -b`)](/p/sybn8yf/)
* [あるファイルがテキストファイルかどうか調べる (`file`)](/p/ams75pd/)
* [リダイレクトとパイプ処理のまとめ](/p/knkamyp/)
* [Linux でリダイレクトによってファイルが上書きされてしまうのを防ぐ (`set -o noclobber`)](/p/jw5xt77/)
* [`ls` コマンドが使えないときに `echo` コマンドで代用する](/p/z3dxcch/)
* [`ls` コマンドでパターンに一致するファイルだけを列挙する](/p/vtkjxha/)

### curl コマンド
* [プロキシ経由で curl コマンドを実行する](tool/curl-via-proxy.html)
* [curl で HTTP のレスポンスヘッダのみを確認する](tool/curl-response-header.html)

