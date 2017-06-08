---
title: Perforce メモ
layout: category-index
---

p4 コマンド
----
* [p4 コマンドの引数で接続先や使用するクライアント名を指定する](port-by-parameter.html)
* [特定のサーバに接続するための p4v のショートカットを作成する](shortcut-icon.html)

ファイル操作
----
* [オープンされているファイルを一覧表示する (p4 opened)](list-opened-files.html)
* [オープンされているファイルを編集前の状態に戻す (p4 revert)](revert.html)
* [ディポ上のファイルを標準出力に出力する／ローカルファイルに保存する](print.html)
* [ディレクトリ内のすべてのファイルを別のディレクトリへ移動する (p4 move)](move-all-files.html)
* [ディポ上にディレクトリを追加する](create-dir.html)
* [p4 update と p4 integrate の違いを理解する](update-and-integrate.html)
* [バイナリファイルとしてファイルを追加する (p4 add)](add-as-binary.html)
* [オープンされていて、かつ変更されているファイルの一覧をローカルパスで表示する](opened-file-local-path.html)
* [ディレクトリ内のファイルを再帰的に p4 add する](recursive-add.html)
* [サーバ上のディポ (depot) の一覧を表示する (p4 depots)](depots.html)
* [ディポ上のファイルを検索する (p4 files)](search-files-on-depot.html)
* [チェンジリスト番号を指定して p4 sync する](sync-with-changelist.html)
* [日時指定で p4 sync する](sync-with-date.html)

Have List
----
- [クライアントの have list を表示する (p4 have)](show-have-list.html)
- [クライアントの have list をサーバから削除する](delete-have-list.html)
- [ローカルのワークスペース上でのファイル編集情報をサーバへ通知する（have list の更新）](update-have-list.html)

チェンジリスト
----
* [新しいチェンジリストを作成する（デフォルトのチェンジリストにチェンジリスト番号を付ける）](create-new-changelist.html)
* [チェンジリストの内容を確認する (p4 describe)](describe.html)
* [指定したチェンジリストを削除する (p4 change)](delete-changelist.html)
* [編集中のファイルがどのチェンジリストで管理されているか調べる (p4 opened)](which-changelist.html)
* [指定したチェンジリストでファイルをオープンする (p4 edit)](edit-in-specified-changelist.html)
* [編集中のファイルを別のチェンジリストに移動する (p4 reopen)](move-to-different-changelist.html)
* [あるユーザがどのファイルを submit せずに pending しているかを調べる (p4 changes)](pending-list.html)
* [あるユーザが行った submit のログを調べる](search-log-by-username.html)

ブランチ
----
- [ブランチを作成する](create-branch.html)
- [ブランチ仕様でブランチビューを定義して integrate 作業を簡単にする](branch-view.html)

管理／設定
----
* [Perforce クライアントのインストールと初期設定](install-client.html)
* [ユーザの各 depot へのアクセス権限を確認する](check-permission.html)
* [Perforce ユーザ ID から本名やメールアドレスを調べる (p4 users)](user-info.html)
* [p4 sync 時に空のディレクトリを削除する ([no]rmdir)](rmdir-after-sync.html)
* [p4 sync 時のファイル上書き設定 ([no]clobber)](clobber-settings.html)
* [p4admin で Perforce サーバを設定する](p4admin.html)
* [オーバーレイ・マッピングによる差分開発を行う](overlay-mapping.html)
* [カレントディレクトリの位置によって P4 設定を切り替える (P4CONFIG)](p4config.html)
* [ファイルを p4 add したときにデフォルトで設定される filetype を調べる (p4 typemap)](typemap.html)

トラブルシューティング
----
* [p4 client -d でクライアントを削除できない](cannot-delete-client.html)
* [p4 sync しようとすると opend for edit and can't be deleted というエラーになる](cannot-sync.html)

便利スクリプト
----
* [p4-backup：オープンされているファイルをバックアップする](p4-backup.html)
* [p4-patch：変更されたファイルの diff からパッチファイルを作る／パッチファイルを適用する](p4-patch.html)

