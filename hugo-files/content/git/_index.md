---
title: "Git"
url: "/git/"

categoryName: "まくまく Git ノート"
categoryUrl: "/git/"
categoryIcon: _index.svg
---

GitHub
----

- [Pull Request のコードレビューワーを自動でアサインする](/p/oifj5jn/)
- [GitHub でソフトウェア配布用にプロジェクトの Web サイトを作成する (GitHub Pages)](/p/yujway4/)
- [GitHub Pages で独自の 404 ページを用意する](/p/bf2nban/)
- [GitHub の Organization を構成したときのチームの権限について理解する](/p/x7hr9z7/)
- [GitHub の新しい Organization パーミッションモデル（2015年10月リリース）について理解する](/p/494ernf/)
- [GitHub の Markdown でメールアドレスへのリンクを記述する](/p/xikzhtr/)
- [（旧）GitHub のプランと GitHub ライクなツールの比較など](/p/x63is5h/)
- [GitHub の REST API を使用する](/p/mprs3v6/)
  - [GitHub の REST API で Issue 情報を取得する方法いろいろ](/p/uvoibwi/)
  - [GitHub の REST API を Python から使用する](/p/8axzppz/)
- [GitHub で管理している社内リポジトリをトピックで分類する (topics)](/p/3j6qatd/)
- [GitHub のオートリンク機能で外部サイトへ ID ベースでリンクする (autolink)](/p/ias8kt3/)
- GitHub Actions
  - [GitHub Actions で GitHub wiki ページを自動更新する](/p/f2eggno/)
  - [GitHub Actions のワークフローをローカルで実行する (`act`)](/p/iudtbr8/)


Git の基本
---

- [Git の作業ツリーを作成する (`git init`, `git clone`)](/p/muhmimi/)
- [Git でファイルの変更をステージする（コミットの印をつける）(`git add`, `git rm`, `git mv`)](/p/6qp26ez/)
- [Git で変更をローカルリポジトリにコミットする](/p/ez3knei/)
- [コミット対象になっているファイルを確認する (`git status`)](/p/z26xvc9/)
- [Git コマンドの長いオプション名を省略して入力する小技](/p/7cf83cy/)


Git の設定
---

* 設定全般
  * [`git config` で設定すべき一般的な設定項目のまとめ](/p/rcwaz54/)
  * [Git 設定のスコープ (`local`/`global`/`system`) を理解する](/p/af7q7n3/)
  * [Git の設定値がどのファイルで設定されているか調べる (`config --show-origin`)](/p/msds6iv/)
  * [`git config` による設定を削除する (`config --unset`)](/p/4pad93a/)
* [コミット時に使用するユーザ名とメールアドレスを設定する (`user.name`, `user.email`)](/p/gr3v7r8/)
* [コミット、チェックアウト時に改行コードを自動変換する (`core.autocrlf`, `core.safecrlf`)](/p/efmfnuy/)
* [コミット時にコメント記述に使用するエディタを設定する (`core.editor`)](/p/cqjv7wv/)
* [Git コマンドの出力をカラフルにする (`color.ui`)](/p/odshtcs/)
* [Git コマンドのエイリアスを作成する (`alias.xxx`)](/p/a59xkpd/)
* [Git コマンドで使用するプロキシを設定する (`http.proxy`)](/p/uyzcgd8/)
* [git プロトコルではなく強制的に https プロトコルで `git clone` するようにする (`url.xxx`)](/p/k84zg8x/)
* [Git ユーザーをディレクトリごとに自動で切り替える (`.gitconfig`, `includeIf`)](/p/hxyiu7g/)
* [`git diff` や `git status` での日本語の文字化けを防ぐ (`core.page`, `core.quotepath`)](/p/cj2uie9/)
* Windows 用の設定
  * [Windows の `git diff` で改行コードが `^M` で表示される問題を解決する (`core.whitespace`)](/p/uek4bb2/)
  * [Windows でパーミッションの違いで `diff` が表示されてしまうのを防ぐ (`core.filemode`)](/p/ijepy8e/)
  * [msysGit で "terminal is not fully functional" と怒られる場合の対策](/p/coekmmp/)
* プロンプト設定
  * [bash のプロンプトに Git のカレントブランチ名を表示する (`PS1`)](/p/2mb4a94/)
* .gitignore
  * [`.gitignore` ファイルで Git にバージョン管理させないファイルを指定する](/p/ucp6uab/)


コミット／履歴の書き換え
---

* [Git やり直し: 直前のコミットのコメントを修正する](git-modify-comment.html)
* [Git やり直し: 直前のコミットを取り消す](commit/reset-prev-commit.html)
* [Git やり直し: main ブランチに入れてしまったコミットを別のブランチに移す](commit/move-commit.html)
* [Git やり直し: 最近のコミットを歴史から抹消する](commit/delete-recent-commits.html)
* [Git やり直し: コミット履歴を書き換えてディレクトリ階層を変更する](git-change-dir-hierarchy.html)
* [変更の一部だけをコミットする](commit/commit-by-hunk.html)


ログ (git log)
----

* [コミットログ表示の基本](log/basic.html)
* [コミットログの書き方](log/commit-log-rules.html)
* [コミットログを絞り込んで表示する](log/filter-logs.html)
* [コミットログにファイル名や変更行数を表示する](log/modified-lines.html)
* [コミットログの出力形式をカスタマイズする (git log --pretty, --date)](log/log-format.html)
* [コミットログにある文字列が含まれているコミットを検索する (git log --grep)](log/grep-log.html)
* [変更内容にある文字列が含まれているコミットを検索する (git log -G/-S)](log/pickaxe.html)
* [指定したディレクトリ以下の変更に関連するコミットログのみ表示する](log/dir-log.html)
* [ブランチ間の差分を調べる](log/diff-between-branches.html)
* [リモートブランチとローカルブランチの差分を調べる](log/diff-remote-and-local.html)
* [コミットログを標準出力へ出力する](log/output-to-stdout.html)
* 統計情報
  * [ユーザーごとのコミット統計を出力する (git shortlog)](stats/shortlog.html)
  * [あるバージョン間の変更行数（追加＆削除）を集計する (git diff --stat, git log --numstat)](stats/count-changes.html)


grep 検索 (git grep)
----

- [Git リポジトリ内のコードを grep 検索する (`git grep`)](/p/2c29gvn/)


ブランチ (git branch)
----

* [ブランチを作成する (`git branch`)](/p/x9s8k2e/)
* [ブランチをチェックアウトする（作業対象のブランチを切り替える）(`git switch/checkout`)](/p/8cwbp3e/)
* [ブランチの作成とチェックアウトを同時に行う (`git switch -c`, `git checkout -b`)](/p/ivbss76/)
* [ブランチを削除する (`git branch -d/D`)](/p/stpfje9/)
* [ブランチ名を変更する (`git branch -m`)](/p/9bh2ody/)
* [ブランチやタグの作者を調べる (`git for-each-ref`)](/p/o2m2ft7/)
* [他の人が作成したブランチ上で作業する](/p/ewvaoe3/)
* [リモートブランチを削除する (`git push origin :xxx`, `git fetch --prune`)](/p/cv5pi7a/)
* [他のブランチの最新ファイルを現在のブランチにコピーする (`git checkout`)](/p/6g8n3jw/)
* [detached HEAD 状態へのチェックアウトとは](/p/whv8ues/)


マージ (git merge, git cherry-pick)
----

* [ブランチでの変更をマージする（直接マージ、スカッシュマージ、チェリーピック）
](merge/merge-branch.html)
* [マージ時のコンフリクトを解決する](merge/resolve-conflict.html)
* [別ブランチへの複数のコミットを１つのコミットとしてマージする (squash merge)](merge/squash-merge.html)
* [マージのためのコミットを revert する（取り消す）](merge/revert-merge.html)
* [マージ作業に失敗したときにやり直す](merge/cancel-merge.html)
* [マージで競合解決が必要なファイルを調べる](merge/list-conflicted-files.html)
* [マージベースとなったコミットを見つける（fast-forward の関係になっているか調べる）](merge/find-fast-forward.html)
* [別の Git リポジトリの内容を強引にマージする](merge/merge-repo.html)


差分・パッチファイル (git diff / patch)
----

* [git diff でファイルの差分を調べる](git-diff.html)
* [git diff で改行コードの違いを無視する](diff-ignore-linefeed.html)
* [git diff で単語単位で差分を表示する](diff-words.html)
* [git diff の出力で相対パスを使うようにする](git-diff-relative-path.html)
* [git diff で package-lock.json の差分が表示されるのを防ぐ](diff/node-diff.html)
* [Git でパッチファイルを作成する](git-patch.html)


ファイル操作
----

* [git clean で追跡されていないファイルを削除する](git-clean.html)
* [ファイルを削除せずに Git によるバージョン管理の対象から外す (git rm --cached)](file/preclude-file-from-git.html)
* [コミットからファイルをチェックアウトする（ファイルを元に戻す）](file/checkout-from-commit.html)
* [インデックスからファイルをチェックアウトする（ファイルを元に戻す）](file/checkout-from-index.html)
* [特定のバージョンのファイルの内容を確認する (cat-file)](file/cat-file.html)


タグ (git tag)
----

* [コミットに対してタグをつけて中央リポジトリにプッシュする (git tag)](/p/y2cmv5d/)
* [タグの名前を変更する](/p/h4dnw6e/)


リポジトリ／サーバー／バックアップ
----

* [共有リポジトリを作成する](server/create-repository.html)
* [git-daemon による読み取り専用リポジトリの公開](server/git-daemon.html)
* [Git リポジトリを移行する](server/transfer-repository.html)
* [リポジトリ内のディレクトリを別リポジトリに切り出す](move-dir-to-another-repo.html)
* [ローカルディレクトリをリモートリポジトリとして使用する](server/dir-as-repository.html)
* [リモートリポジトリに略称を付ける](server/remote-alias.html)
* [共有リポジトリからの fetch と push](server/fetch-and-push.html)
* [ソースコードのアーカイブを作成する](create-src-archive.html)


hook スクリプト
----

* [Git の update フックの基本](update-hook.html)
* [特定のユーザのみ git push できるように制限する](limit-push-users.html)


その他
----

* [Git 用語集](/p/f844tkg/)
* [Git で N 個前のコミットを表現する](other/represent-commit.html)
* [Git で管理するシェルスクリプトに実行権限（パーミッション）を付ける（chmod +x 相当）](/p/xwxdv6y/)
* [git pull の引数省略の仕組み](other/remote-complement.html)
* [二要素認証を設定した後に git コマンドが Authentication failed になる場合](git-two-factor-auth-error.html)
* [Git サブモジュールで別リポジトリの内容を組み込む (`git submodule`)](/p/dsctaq7/)

### GitLab（GitHub クローン）

* [GitLab をインストールする](gitlab/install.html)
* [GitLab 関連サービスを起動／停止する](gitlab/start-service.html)
* [GitLab のデータをバックアップ／リストアする](gitlab/backup-and-restore.html)
* [起動中の GitLab 関連サービスを調べる](gitlab/service-list.html)
* [GitLab からの通知メールを設定する](gitlab/email-settings.html)
* [GitLab リポジトリの Clone URL として表示されるホスト名を変更する](gitlab/change-hostname.html)
* [GitLab 用 nginx サーバの設定ファイルの場所](gitlab/nginx-conf-path.html)
* [GitLab が使用する Unicorn 用のポート番号を変更する](gitlab/unicorn-port.html)
* [GitLab サーバのアクセスログを調べる](gitlab/access-log.html)

### オワコン情報 (Subversion とか CVS とか）

* [git-svn を使って Git と Subversion を連携する](svn/git-svn.html)
* [CVS のファイルのパーミッションの扱いについて](svn/cvs-file-permission.html)
* [.svn ディレクトリをまとめて削除する](svn/delete-all-svn-dirs.html)

