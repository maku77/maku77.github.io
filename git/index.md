---
title: Git と GitHub の記事
created: 2015-05-17
layout: category-index
---

GitHub
====
* [GitHub でソフトウェア配布用にプロジェクトの Web サイトを作成する](github-project-portal.html)
* [GitHub の Organization を構成したときのチームの権限について理解する](github-team-permission.html)
* [GitHub の新しい Organization パーミッションモデル（2015年10月リリース）について理解する](github-new-organization-permissions.html)
* [GitHub のプランと GitHub ライクなツールの比較など](github-clones.html)
* [GitHub の Markdown でメールアドレスへのリンクを記述する](email-address-in-markdown.html)

GitLab（GitHub クローン）
====
* [GitLab をインストールする](gitlab/install.html)
* [GitLab のデータをバックアップ／リストアする](gitlab/backup-and-restore.html)

Git
====

設定
---
* [コミット、チェックアウト時に改行コードを自動変換する](settings/autocrlf.html)

コミット／履歴の書き換え
---
* [直前のコミットのコメントを修正する](git-modify-comment.html)
* [変更の一部だけをコミットする](commit/commit-by-hunk.html)
* [Git のコミット履歴を書き換えてディレクトリ階層を変更する](git-change-dir-hierarchy.html)

ログ (git log)
----
* [コミットログ表示の基本](log/basic.html)
* [コミットログを絞り込んで表示する](log/filter-logs.html)
* [コミットログにファイル名や変更行数を表示する](log/modified-lines.html)
* [コミットログの出力形式をカスタマイズする](log/log-format.html)
* [コミットログを grep する](log/grep-log.html)
* [指定したディレクトリ以下の変更に関連するコミットログのみ表示する](log/dir-log.html)
* [ブランチ間の差分を調べる](log/diff-between-branches.html)
* [リモートブランチとローカルブランチの差分を調べる](log/diff-remote-and-local.html)
* [コミットログを標準出力へ出力する](log/output-to-stdout.html)

ブランチ (git branch)
----
* [ブランチを作成する](branch/create-branch.html)
* [ブランチをチェックアウトする（作業対象のブランチを切り替える）](branch/checkout-branch.html)
* [ブランチの作成とチェックアウトを同時に行う](branch/create-and-checkout-branch.html)
* [ブランチを削除する](branch/delete-branch.html)
* [リモートブランチを削除する](branch/delete-remote-branch.html)
* [ブランチ名を変更する](branch/rename-branch.html)
* [ブランチやタグの作者を調べる](branch/who-created-branch.html)
* [detached HEAD 状態へのチェックアウトとは](branch/detached-head.html)
* [他の人が作成したブランチ上で作業する](branch/checkout-pushed-branch.html)

差分・パッチファイル (git diff / patch)
----
* [git diff でファイルの差分を調べる](git-diff.html)
* [git diff で改行コードの違いを無視する](diff-ignore-linefeed.html)
* [git diff で単語単位で差分を表示する](diff-words.html)
* [git diff の出力で相対パスを使うようにする](git-diff-relative-path.html)
* [Git でパッチファイルを作成する](git-patch.html)

タグ (git tag)
----
* [git tag でコミットに対してタグをつける](add-tag.html)
* [タグを中央リポジトリに push する](push-tag.html)
* [タグの名前を変更する](rename-tag.html)

リポジトリ／サーバー／バックアップ
----
* [共有リポジトリを作成する](server/create-bare-repository.html)
* [git-daemon による読み取り専用リポジトリの公開](server/git-daemon.html)
* [Git リポジトリを移行する](server/transfer-repository.html)
* [リポジトリ内のディレクトリを別リポジトリに切り出す](move-dir-to-another-repo.html)
* [ローカルディレクトリをリモートリポジトリとして使用する](server/dir-as-repository.html)
* [ソースコードのアーカイブを作成する](create-src-archive.html)

hook スクリプト
----
* [Git の update フックの基本](update-hook.html)
* [特定のユーザのみ git push できるように制限する](limit-push-users.html)

その他
----
* [Git 関連用語](git-words.html)
* [Git で N 個前のコミットを表現する](other/represent-commit.html)
* [Git で管理しているファイルの実行権限（パーミッション）を変更する](file-permission.html)

* [二要素認証を設定した後に git コマンドが Authentication failed になる場合](git-two-factor-auth-error.html)

