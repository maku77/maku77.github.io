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
- [GitHub で複数アカウントの SSH キーを使い分ける (`~/.ssh/config`)](/p/vn9aqfb/)


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

* [Git やり直し: 直前のコミットのコメントを修正する (`git commit --amend`)](/p/wyq4tea/)
* [Git やり直し: 直前のコミットを取り消す (`git reset --soft HEAD~`)](/p/kth4c6j/)
* [Git やり直し: main ブランチに入れてしまったコミットを別のブランチに移す](/p/8uatxxj/)
* [Git やり直し: 最近のコミットを歴史から抹消する (`git rebase -i`)](/p/hmcakev/)
* [Git やり直し: コミット履歴を書き換えてディレクトリ階層を変更する (`git filter-branch`)](/p/iut5h68/)
* [変更の一部だけをコミットする (`git add -p`)](/p/a6776js/)


ログ (git log)
----

* [コミットログ表示の基本](/p/hsavjib/)
* [コミットログの書き方](/p/svg6x8f/)
* [コミットログを絞り込んで表示する](/p/xsbjsnr/)
* [コミットログにファイル名や変更行数を表示する (`git log --name-only, --stat`)](/p/zgr593x/)
* [コミットログの出力形式をカスタマイズする (`git log --pretty, --date`)](/p/7ehnuk6/)
* [コミットログにある文字列が含まれているコミットを検索する (`git log --grep`)](/p/hi5iir5/)
* [変更内容にある文字列が含まれているコミットを検索する (`git log -G/-S`)](/p/hf7bnyi/)
* [指定したディレクトリ以下の変更に関連するコミットログのみ表示する](/p/kftbcyc/)
* [ブランチ間の差分を調べる](/p/fmarnds/)
* [リモートブランチとローカルブランチの差分を調べる](/p/zyitasn/)
* [コミットログを標準出力へ出力する (`git log --no-pager`)](/p/ntcpmjj/)
* 統計情報
  * [ユーザーごとのコミット統計を出力する (`git shortlog`)](/p/ocva5fh/)
  * [あるバージョン間の変更行数（追加＆削除）を集計する (`git diff --stat`, `git log --numstat`)](/p/3cjkt3v/)


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
](/p/n3vaeh5/)
* [マージ時のコンフリクトを解決する](/p/ymfxj2s/)
* [別ブランチへの複数のコミットを1つのコミットとしてスカッシュマージする (`git merge --squash`)](/p/6naegzu/)
* [マージのためのコミットを revert する（取り消す） (`git revert -m`)](/p/iz8y69c/)
* [マージ作業に失敗したときにやり直す (`git reset`)](/p/49yjwoe/)
* [マージで競合解決が必要なファイルを調べる (`git ls-files -u`)](/p/jok7ggh/)
* [マージベースとなったコミットを見つける（fast-forward の関係になっているか調べる） (`git merge-base`)](/p/2u3igu6/)
* [別の Git リポジトリの内容を強引にマージする](/p/odgmdp2/)


差分・パッチファイル (git diff / patch)
----

* [git diff でファイルの差分を調べる](/p/y9v4isr/)
* [git diff で改行コードの違いを無視する (`git diff -w`)](/p/zm7ezhx/)
* [git diff で単語単位で差分を表示する (`git diff --word-diff`)](/p/xav46kn/)
* [git diff の出力で相対パスを使うようにする (`git diff --relative`)](/p/w266n3s/)
* [git diff で `package-lock.json` の差分が表示されるのを防ぐ (`:(exclude)`)](/p/4zoiaou/)
* [Git でパッチファイルを作成する (`git diff/apply/format-patch/am`)](/p/3x2viow/)


ファイル操作
----

* [git clean で追跡されていないファイルを削除する (`git clean -df`)](/p/scqf3sw/)
* [ファイルを削除せずに Git によるバージョン管理の対象から外す (`git rm --cached`)](/p/qu3d2f9/)
* [ファイルの変更を取り消してコミットやインデックスの内容に戻す (`git checkout`, `git checkout HEAD`)](/p/xjixs9v/)
* [特定バージョンのファイルの内容を確認する (`git show`, `git cat-file -p`)](/p/32b2ttc/)


タグ (git tag)
----

* [コミットに対してタグをつけて中央リポジトリにプッシュする (`git tag`)](/p/y2cmv5d/)
* [タグの名前を変更する (`git tag new old`)](/p/h4dnw6e/)


リポジトリ／サーバー／バックアップ
----

* [共有リポジトリを作成する (`git init --bare`, `git clone --bare`)](/p/yaocjjs/)
* [共有リポジトリとやりとりする (`git fetch`, `git push`)](/p/72jawne/)
* [git-daemon でリポジトリを読み取り専用で公開する](/p/kax48rz/)
* [Git リポジトリを移行する (`git clone --mirror`, `git push --mirror`)](/p/bfxgbf4/)
* [リポジトリ内のディレクトリを別リポジトリに切り出す](/p/vaqzwih/)
* [ローカルディレクトリをリモートリポジトリとして使用する](/p/jkbezsr/)
* [リモートリポジトリに略称を付ける (`git remote add`)](/p/mhqsei2/)
* [ソースコードのアーカイブファイル (ZIP/TAR) を作成する (`git archive`)](/p/dmpa5vw/)


hook スクリプト
----

* [Git の update フックの基本 (`.git/hooks/update`)](/p/gyotfx7/)
* [特定のユーザのみ git push できるように制限する](/p/f84ekdq/)


その他
----

* [Git 用語集](/p/f844tkg/)
* [Git で N 個前のコミットを表現する](/p/npo47yj/)
* [Git で管理するシェルスクリプトに実行権限（パーミッション）を付ける（`chmod +x` 相当） (`git update-index`)](/p/xwxdv6y/)
* [`git pull` の引数省略の仕組み](/p/xonap3b/)
* [GitHub で git コマンドが Authentication failed になる場合（HTTPS 認証とアクセストークン）](/p/c7ddt8i/)
* [Git サブモジュールで別リポジトリの内容を組み込む (`git submodule`)](/p/dsctaq7/)
* GitLab（GitHub クローン）
  * [GitLab をインストールする](/p/4mjwu34/)
  * [GitLab 関連サービスを起動／停止する](/p/9hybaqx/)
  * [GitLab のデータをバックアップ／リストアする](/p/df778jg/)
  * [起動中の GitLab 関連サービスを調べる](/p/5672eis/)
  * [GitLab からの通知メールを設定する](/p/ycphr93/)
  * [GitLab リポジトリの Clone URL として表示されるホスト名を変更する](/p/b9fmgxq/)
  * [GitLab 用 nginx サーバの設定ファイルの場所](/p/bqqhyr2/)
  * [GitLab が使用する Unicorn 用のポート番号を変更する](/p/fhyfx7p/)
  * [GitLab サーバのアクセスログを調べる](/p/nzhf2bv/)
* オワコン情報 (Subversion とか CVS とか）
  * [git-svn を使って Git と Subversion を連携する](/p/mujnu3f/)
  * [CVS のファイルのパーミッションの扱いについて](/p/mt89s5z/)
  * [`.svn` ディレクトリをまとめて削除する](/p/3thorrh/)

