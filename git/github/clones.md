---
title: "GitHub のプランと GitHub ライクなツールの比較など（GitHub と GitLab の比較）"
date: "2014-05-14"
---

GitHub (https://github.com/) デファクトスタンダード
----

### Public repository と Private repository プラン

- GitHub は公開リポジトリのみであれば無料で使用できるが、非公開にするには有料。
- 10 repos で $25/month
- 20 repos で $50/month

### GitHub Enterprise（社内サーバ用）

- GitHub 社が提供している、GitHub の機能をまるごと含めた VM。
- サーバは別途用意する必要あり。
- 社内で GitHub の機能を使ってコード管理したい場合などに有用。
- 若干高い。20 person で $5,000/year


GitLab (https://www.gitlab.com/)
----

### 特徴

- 一番メジャーなオープンソースの GitHub クローン
- 実装は Ruby on Rails. (+ PostgreSQL, Redis, Nginx, Unicorn, etc.)
- インストールは比較的容易。
- Pull Request もどき有り (Merge Request)。ブランチ間のマージも可能。
- LDAP 連携。ActiveDirectory 連携。

### GitHub より便利なところ

* ユーザに対して Master 権限と Developer 権限を分けて設定することで、master ブランチへマージできる人を制限できる。これにより、チーム内開発において、トピックブランチを使用した開発を強制できるようになる（GitHub フローなど）。
* MergeRequest のコメントで各ユーザが `+1` と記述しておくと、MergeRequest のページにその合計数が表示される。これを利用して、「`+3` 溜まったらトピックブランチをマージしてよい」といった運用を行うことができる。


Gitorious (http://gitorious.org/)
----

### 特徴

- 若干独自路線気味の GitHub クローン
- 実装は Ruby on Rails.
- インストール、サーバ管理に難あり（モジュール依存が多い）。
- Pull Request もどき有り (Merge Request)。ブランチ間のマージ機能がないので、GitHub Flow のような開発方法は行えない（2014-05-14 現在）。
- 権限＆Roll制御。ユーザごとに push 可能なリポジトリを制御可能。

