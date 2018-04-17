---
title: Windows メモ
layout: category-index
---

Windows バッチファイル
----

### 環境
* [Windows で Ruby や Python のスクリプトを組み込みコマンドのように使用する](run-script-as-command.html)
* [環境変数が設定されているかどうか調べる](batch/check-env-var.html)

### 構文
* [バッチファイルの FOR ループ (1) 数値、ファイル集合、ディレクトリ集合のループ処理](for-loop.html)
* [バッチファイルの FOR ループ (2) テキストファイル、コマンド出力を 1 行ずつループ処理](for-loop2.html)
* [バッチファイルを途中で終了する](exit-batch.html)
* [バッチファイルで関数もどき（サブルーチン）を実現する](subroutine.html)
* [長いコマンドを複数行に分けて記述する](syntax/separate-long-line.html)

### 入出力 (I/O)
* [echo の出力を標準エラー (stderr) に出力する](echo-to-stderr.html)
* [echo で改行だけ出力する](echo-newline.html)
* [複数のコマンドの出力をまとめてリダイレクトする](io/collect-output.html)

### ファイル／ディレクトリ／パス
* [DIR コマンドでファイルやディレクトリを再帰的に検索する](find-files.html)
* [DIR コマンドでファイルやディレクトリを OR 検索する](or-dir.html)
* [カレントディレクトリ以下のファイルやディレクトリを順に処理する](for-each-file.html)
* [findstr でファイル内の文字列を検索する](file/findstr.html)
* [既にディレクトリが存在している場合の mkdir のエラーを抑制する](file/avoid-mkdir-error.html)
* [バッチファイルが格納されているディレクトリのパスを取得する](file/batch-dir.html)
* [複数のファイルを連結する](file/concat-file.html)

### 日時
* [バッチファイル内で日時を出力する](batch/display-time.html)


コマンドプロンプト
----
* [カレントディレクトリのパスを取得する](cmd/get-current-dir.html)
* [コマンドプロンプトの文字色と背景色を変更する](settings/change-color-of-cmd.html)
* [コマンドプロンプトのプロンプト表示を変更する](settings/change-prompt.html)
* [doskey を使ってコマンドプロンプト用のマクロを登録する](settings/doskey.html)
* [コマンドプロンプトから複数のコマンドを並列実行する (start)](cmd/parallel.html)
* [コマンドプロンプトからインターネット上のファイルをダウンロードする (bitsadmin)](cmd/download.html)


Windows の便利操作／ショートカット
----
* [Windows でキーボード操作だけで様々なフォルダを素早く開く](open-dir-by-keyboard.html)


Windows の設定／管理／セキュリティ
----

* タスク管理
    * [Windows で実行中のプロセス（タスク）の一覧を表示する (tasklist)](admin/tasklist.html)
    * [Windows で実行中のプロセス（タスク）を終了 (kill) する (taskkill)](admin/taskkill.html)
* [Windows OS のバージョン情報をコマンドラインから調べる](admin/os-version.html)
* [NIC に割り当てられた MAC アドレスを調べる (getmac)](admin/getmac.html)
* [コマンドラインから Windows サービスを管理する](manage-services-from-command-line.html)
* [Administrator で Windows にログオンできるようにする](logon-as-admin.html)
* [Windows のアカウント名を変更する](change-account-name.html)
* [ドメインユーザーにローカル PC の Administrator 権限を与える](add-admin-to-domain-user.html)
* [日本語キーボードのノート PC で英字配列の USB キーボードを使用する](usb-us-keyboard.html)
* [Windows のユーザのパスワードの有効期限を無期限に設定する](settings/unlimited-password.html)
* [xcopy でディレクトリ内のファイルをバックアップする](xcopy.html)

### ネットワーク
* [Windows ファイアウォールで特定のポートを開放する](open-firewall-port.html)
* [ネットワーク切り替え時に PC 名ですぐに検索できるようにする（NetBios 名の再登録）](network/register-netbios.html)
* [Windows のログオフ時にネットワーク接続を維持する](network/keep-connection-after-logging-off.html)
* [Windows の DNS キャッシュをクリアする](network/clear-dns-cache.html)


MS Office
----

### Excel
- [Excel のブック、ワークシート、セルの関係を理解する](excel/structure.html)
- [VBA マクロに関してはこちらを参照](../vba/)

### PowerPoint
- [PowerPoint で使いこなすとかっこいいショートカット](powerpoint/shortcut.html)
- [SVG ファイルを EMF ファイルに変換して PowerPoint に貼り付ける](powerpoint/svg2emf.html)

### Word
- [Word で章・節・項の見出しを作成する](word/create-chapter.html)
- [Word で章・節・項の「見出しマップ」を表示する](word/chapter-map.html)
- [Word で章・節・項の見出しの表示スタイルを変更する](word/chapter-style.html)
- [Word で章・節・項の見出しに連番（段落番号）を自動で振る](word/number-chapters.html)
- [Word で章・節・項の見出しから目次を作成する](word/create-toc.html)

### Outlook
- [Outlook で返信メールのメッセージの行頭に引用記号 (>) を付ける](outlook/quote-mark.html)
- [Outlook の予定表を他のユーザから見えるようにする](outlook/share-schedule.html)


Windows API
----

### Legacy API
* [プロセス・ハンドルについて](winapi/process-handle.html)
* [Windows の静的ライブラリと動的ライブラリ](misc/windows-library.html)
* [指定したウィンドウ (HWND) を確実にアクティブにする](winapi/activate-window-forcedly.html)
- [現在使用中の Windows OS のバージョンを調べる](winapi/os-version.html)
- [Windows API ですべてのウィンドウを列挙し、特定の条件にマッチするウィンドウを取得する](winapi/search-window.html)

### .NET

#### 日付／時刻
* [.NET - 時刻部分をすべて 0 にした DateTime インスタンスを作成する](dotnet/zero-time-date.html)
* [.NET - DateTime インスタンスと文字列を相互に変換する](dotnet/datetime-to-string.html)
* [.NET - 現地時刻（ローカルタイム）と世界協定時刻 (UTC) の扱いを理解する](dotnet/localtime-and-utc.html)

#### その他
* [.NET - System.Data.SQLite で SQLite データベースを使用する](dotnet/system-data-sqlite.html)
* [.NET - XML 形式の設定ファイルを XPath で操作するサンプル](dotnet/xpath.html)

