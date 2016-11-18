---
title: Shell メモ
layout: category-index
---

Linux のシェルや、Bash プログラミングに関するノートです。

シェルスクリプト / Bash プログラミング
----

### 変数
* [変数の基本](variables.html)
* [シェル変数/環境変数がセットされているか調べる](check-if-var-is-set.html)

### 制御構文
* [if-else による分岐処理](syntax/if.html)

### パス
* [絶対パスと相対パスの変換](absolute-path-to-relative-path.html)
* [カレントディレクトリの絶対パスを取得する](absolute-path-of-current-dir.html)
* [シェルスクリプトのあるディレクトリの絶対パスを取得する](absolute-path-of-script-dir.html)
* [シェルスクリプトのあるディレクトリに移動する](move-to-script-dir.html)
* [指定したファイルの絶対パスを取得する](absolute-path-of-file.html)

### 関数
* [関数へパラメータを渡す](pass-params-to-function.html)
* [関数内でローカル変数を扱う](local-var-in-function.html)
* [関数から戻り値を返す](return-value-from-function.html)
* [関数のデフォルト引数を定義する](default-params-in-function.html)

### 入出力 (I/O)
* [ユーザ入力を取得する](obtain-user-input.html)
* [ユーザ入力を取得して "y" が入力された場合だけ処理を継続する](obtain-user-input2.html)
* [ディレクトリ内のファイルを順に処理する](process-all-files.html)

### 日時
* [今日の日付から YYYYMMDD のような文字列を作成する (date)](time/date-string.html)

### 数値
* [ランダムな数値を取得する ($RANDOM)](number/random.html)

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

Linux 全般　
----
* [Windows で Linux 系のコマンドを使用できるようにする (Gow)](basic/gow.html)
* [find で見つけたファイルを grep 検索する](basic/find-and-grep.html)
* [rsync コマンドでディレクトリを同期する](basic/rsync.html)
* [行番号付きでテキストファイルの内容を出力する](basic/cat-with-line-number.html)
* [あるファイルがテキストファイルかどうか調べる (file)](basic/check-if-text-file.html)
* [リダイレクトとパイプの役割](basic/redirect-and-pipe.html)
* [Linux でリダイレクトによってファイルが上書きされてしまうのを防ぐ](basic/avoid-overridden-by-redirect.html)
* [Linux のメモリ情報を調べるコマンド](memory.html)
* [ls コマンドが使えないときに echo コマンドで代用する](basic/echo-instead-of-ls.html)
* [いずれかの文字列を含むファイルだけを列挙する](basic/ls-with-pattern-matching.html)
* [bash のバージョンを調べる](bash-version.html)
* [Linux の各種 ID（プロセス ID やユーザ ID など）についてのメモ](linux-ids.html)
* [カーネルに渡されたパラメータを調べる (/proc/cmdline)](kernel-params.html)

### Linux のシステム設定
* [NTP でシステム時刻を設定する](settings/ntp.html)

### curl コマンド
* [プロキシ経由で curl コマンドを実行する](tool/curl-via-proxy.html)
* [curl で HTTP のレスポンスヘッダのみを確認する](tool/curl-response-header.html)

