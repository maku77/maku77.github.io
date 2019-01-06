---
title: "Shell メモ"
layout: category-index
---

Linux のシェルや、Bash プログラミングに関するノートです。

シェルスクリプト / Bash プログラミング
----

* [シェルスクリプトのコーディングスタイル](basic/coding-style.html)

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
* [if-else による分岐処理](syntax/if.html)
* [case による分岐処理](syntax/case.html)
* [for/while によるループ処理](syntax/for.html)
    * [ループ内での複数の出力をまとめてリダイレクト、パイプ処理する](syntax/loop-output.html)

### 起動時の処理
* [コマンドライン引数を取得する](startup/command-line-params.html)
* [コマンドライン引数の数が正しいかチェックする](startup/check-param-count.html)
* [ある環境変数が定義されているかチェックする](startup/check-env.html)
* [ある外部コマンドが使用できるかチェックする](startup/check-external-command.html)

### 関数
* [関数を定義する (function)](syntax/function.html)
* [関数へパラメータを渡す](syntax/pass-params-to-function.html)
* [関数内でローカル変数を扱う](syntax/local-var-in-function.html)
* [関数から戻り値を返す](syntax/return-value-from-function.html)
* [関数のデフォルト引数を定義する](syntax/default-params.html)

### パス
* [絶対パスと相対パスの変換 (basename, dirname)](path/absolute-path-to-relative-path.html)
* [指定したファイルの絶対パスを取得する](path/absolute-path-of-file.html)
* [カレントディレクトリの絶対パスを取得する](path/absolute-path-of-current-dir.html)
* [実行中のシェルスクリプトのファイル名を取得する](path/path-of-script.html)
* [実行中のシェルスクリプトがあるディレクトリの絶対パスを取得する](path/absolute-path-of-script-dir.html)
* [実行中のシェルスクリプトがあるディレクトリに移動する](path/move-to-script-dir.html)

### 入出力 (I/O)
* [echo で出力した文字列の中の連続するスペースが1つのスペースになってしまうのを防ぐ](io/echo-spaces.html)
* [echo で出力する文字の色を変える](io/echo-color.html)
* [ユーザ入力を取得する (read)](io/user-input.html)
* [ディレクトリ内のファイルを順に処理する](io/loop-files.html)
* [外部コマンドの実行結果を一行ずつ処理する](io/external-command.html)
* [テキストファイルを読み込む](io/read-file.html)
* [expect で ssh や rsync のパスワード入力を自動化する](io/expect.html)

### 日時
* [今日の日付から YYYYMMDD のような文字列を作成する (date)](time/date-string.html)

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
* [bash のバージョンを調べる ($BASH_VERSION)](bash-version.html)
* [Linux の各種 ID（プロセス ID やユーザ ID など）についてのメモ](linux-ids.html)
* [カーネルに渡されたパラメータを調べる (/proc/cmdline)](kernel-params.html)
* [Linux のメモリ情報を調べるコマンド](memory.html)
* [NTP でシステム時刻を設定する](settings/ntp.html)
* [ディレクトリ内のファイルを zip ファイルにバックアップする](manage/backup.html)
* [Deep Learning や仮想通貨のマイニング時に CPU 使用率が 100% になってしまうのを防ぐ (cpulimit)](manage/cpulimit.html)

### プログラミング
* [Linux システムコールを使用して core dump を吐かないようにする (setrlimit)](manage/setrlimit.html)


Linux コマンド全般　
----
* [Windows で Linux 系のコマンドを使用できるようにする (Gow)](basic/gow.html)
* find
    * [find で見つけたファイルを grep 検索する](basic/find-and-grep.html)
    * [ファイル名の大文字と小文字を区別せずに find 検索する](basic/find-iname.html)
* [rsync コマンドでディレクトリを同期する](basic/rsync.html)
* [行番号付きでテキストファイルの内容を出力する (cat -b)](basic/cat-with-line-number.html)
* [あるファイルがテキストファイルかどうか調べる (file)](basic/check-if-text-file.html)
* [リダイレクトとパイプ処理のまとめ](basic/redirect-and-pipe.html)
* [Linux でリダイレクトによってファイルが上書きされてしまうのを防ぐ](basic/avoid-overridden-by-redirect.html)
* [ls コマンドが使えないときに echo コマンドで代用する](basic/echo-instead-of-ls.html)
* [いずれかの文字列を含むファイルだけを列挙する](basic/ls-with-pattern-matching.html)

### curl コマンド
* [プロキシ経由で curl コマンドを実行する](tool/curl-via-proxy.html)
* [curl で HTTP のレスポンスヘッダのみを確認する](tool/curl-response-header.html)

