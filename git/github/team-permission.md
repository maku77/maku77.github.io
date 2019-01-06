---
title: "GitHub の Organization を構成したときのチームの権限について理解する"
date: "2015-05-07"
---

GitHub の Organization の 4 種類のチーム
====

GitHub で Organization を作成すると、その下に作成したリポジトリは、Organization 内に作成したチーム単位でアクセス権限を設定することになります。
[GitHub のサイト](https://help.github.com/articles/permission-levels-for-an-organization-repository/)にざっと説明がありますが、分かりにくいのでここで補足します。

チーム区分としては、

  * Owners
  * Admin
  * Write
  * Read

に分かれていて、それぞれ異なる権限を持ちます。

Owners チームとその権限
====

Owners はその名の通り organization 自体の管理者チームであり、デフォルトで存在しているものです。
残りの 3 つ（Admin、Write、Read）とは性質が異なり、削除したり、Owners という名前を変更することはできません。
organization を作成した人が、Owners の最初のメンバになります。

* Owners 権限
  * デフォルトで存在するチームで、その organization に関してすべての権限を持つ。
  * Owners に属するメンバは、支払い情報を管理し、クレジットカードなどの登録を行う。
  * Owners には必ず 1 人以上が所属している必要がある。
  * organization へのメンバ追加が可能。
  * リポジトリを作成可能。
  * チームを作成可能。

残りの Admin、Write、Read というのは、後付で作成したチームに対して割り当てる権限を意味しています。
Owners チームは organization 内でひとつしか存在しませんが、Admin、Write、Read 権限を持つチームは複数作ることが可能です。
チームを作成できるのは Owners のメンバだけです。

各リポジトリへのアクセス権限は、このチーム単位で参加者 (contributors) を割り当てることで行います。
例えば、my-project というリポジトリの contributors として、team1 (Write 権限)、team2 (Read 権限) を参加させた場合、team1 に参加しているメンバだけが、my-project へのコミット権限を持つことになります。
リポジトリに対してチームを割り当てることができるのは Owners のメンバだけです。

Admin チームとその権限
====

チームの作成やリポジトリへの登録は Owners のメンバしか行えませんが、Admin チームに属していれば、リポジトリの作成を行うことができます。
さらに、Admin チームに属するメンバは、その Admin チーム自体へのメンバ追加が可能です。

* Admin 権限
  * リポジトリを作成可能。
  * organization へのメンバ追加が可能。
  * Admin チームへのメンバ追加が可能（Admin 以外のチームへのメンバ追加は行えない）
  * リポジトリに Admin チームを登録すると、Admin チームメンバはそのリポジトリの設定変更可能。

Admin チームに属しているけれど、Owner チームには属していないメンバがリポジトリを作成すると、そのリポジトリの contributor に自動的に Admin チームが追加されてしまうようです。
このとき、複数の Admin チームに属していた場合は、その中のいずれかのチームが contributor として登録されます。
Admin チームは扱いにくいですね・・・。

Write / Read チームとその権限
====
Write 権限、Read 権限については分かりやすく、リポジトリへの push が可能かどうかを示しています。
コードを Read only で見るだけでよいのであれば、そのユーザを Read 権限だけ付いたチームに登録し、そのチームを対象のリポジトリの contributor として登録します。


運用例
====

例えば、下記のようなルールで運用すると、各リポジトリごとに細かくメンバのアクセス権限を制御することができます。

  * Owners メンバがリポジトリ（例: rep1）の作成を行う。
  * Owners メンバがそのリポジトリ用の Write 権限のチーム（例: team-rep1）、Read 権限のチーム（例: team-rep1-ro）を作成し、リポジトリに登録する。
  * リポジトリ rep1 の開発メンバは team-rep1 に所属させる。
  * リポジトリ rep1 をリードオンリーで参照させたいメンバは team-rep1-ro に所属させる。
  * そのリポジトリの設定を Owners メンバ以外に任せたくなったら、そのリポジトリ用の Admin 権限のチーム（例 team-rep1-admin）を作成し、リポジトリに登録する。
