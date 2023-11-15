---
title: "GitHub の新しい Organization パーミッションモデル（2015年10月リリース）について理解する"
url: "p/494ernf/"
date: "2015-10-01"
lastmod: "2023-11-15"
tags: ["GitHub"]
changes:
  - 2023-11-15: GitHub の UI 変更に対応
aliases: /git/github/new-organization-permissions.html
---

GitHub の Organization アカウントの新しいパーミッションモデルが公開されました。

- 参考: [New organization permissions now available - The GitHub Blog](https://github.blog/2015-09-30-new-organization-permissions-now-available/)


チームメンテナの設定が可能
----

チーム内のメンバーに対して、__team maintainer__ という役割を設定できるようになりました（念願！）。
これまでは、各チームに対するメンバー追加を行うときは、Organization の Owners メンバーが追加作業を行う必要がありましたが、この機能によって、__チーム内のメンバー管理は、そのチームの代表者__ に任せることができるようになります。

チーム内のメンバーを team maintainer に割り当てるには次のようにします。

1. Organization のトップページから Teams を選択
2. 対象のチームを選択
3. チームメンテナに設定したいメンバーを選択し、"Change role..." → "Maintainer" を選択

チームメンテナとなったメンバーは、自分の管理するチームに対して、自由にメンバーを参加させたり外したりできます。
チーム名の変更や、チーム内メンバーのチームメンテナへの昇格も自由に行えるようになるため、ある程度責任のあるリーダクラスの人をチームメンテナとして設定すべきです。

- 参考: [Giving "team maintainer" permissions to an organization member](https://help.github.com/articles/giving-team-maintainer-permissions-to-an-organization-member-early-access-program/)


Organization 内のリポジトリに対するデフォルトパーミッションを設定可能
----

デフォルトパーミッションとして Read/Write 権限を付加しておくことで、Organization 内のメンバー全員に対して、すべてのプロジェクト（リポジトリ）の参照権限、コミット権限を付けることができます。

例えば、直接コミットできるメンバーはプロジェクトごとに限定したいけれど、コードの参照や PullRequest くらいは全メンバーに許可したい、ということが簡単に実現できます（これまでは、プロジェクトに対する参照権限を与えるために、対象のユーザを Read 権限のあるチームに追加させ、そのチームをプロジェクトに登録する、という分かりにくい作業が必要でした）。

メンバー全員に全リポジトリの参照権限を付けるには以下のようにします。

1. Organization のトップページから Settings を選択
2. Member privileges を選択
3. Base permissions の項目を No permission から Read に変更

全メンバーが全プロジェクトの開発に関わっているような Organization では、デフォルトのパーミッションとして Write を設定しておけば、アクセス権限管理のためにチームを作成する必要がなくなります。


チームをまたいだコラボレーションが可能
----

Organization 内の別のチームのメンバーに対しても自由にコメントできるようになりました（推奨設定: ON）。
PullRequest や Issue などで議論する際に、別のチームのメンバーに対しても [`@name` の形式で通知](https://help.github.com/articles/writing-on-github/#name-and-team-mentions-autocomplete) できます。

従来のチームの Visible 設定は Secret になっているので、チーム間のコラボレーションを可能にするために、Visible 設定を Visible に変更する必要があります。

1. Organization のトップページから Teams を選択
2. 対象のチームを選択
3. Settings ボタンをクリック
4. Team visibility 設定を Visible に変更


メンバーごとのアクセス権を確認可能
----

各メンバーが、それぞれのリポジトリに対してどのようなアクセス権限（Admin/Write/Read) を持っているかを、一覧で確認することができるようになりました。

1. Organization のトップページから People を選択
2. 対象のユーザの設定ボタン → Manage を選択

- 参考: [Managing an individual's access to an organization repository](https://help.github.com/articles/managing-an-individual-s-access-to-an-organization-repository-early-access-program/)


Organization 内のリポジトリの作成権限を追加
----

Owners メンバーでなくても、Organization 内にプロジェクトを自由に作成することができるようになりました。

1. Organization のトップページから Settings を選択
2. Member privileges を選択
3. Repository creation で必要に応じて下記にチェックを入れる
   - `Public` ... パブリックなリポジトリの作成を許可する
   - `Private` ... プライベートなリポジトリの作成を許可する

Billing information などの管理はさせたくないけれど、プロジェクト作成くらいは各メンバーに自由にさせたいような場合に有効です。


Organization 外のメンバーをプロジェクトメンバーに追加することが可能
----

今までは、Organization 内のリポジトリ（プロジェクト）に対するアクセス権限は、Organization 内のチーム単位で与える仕組みでしたが、個人単位、かつ、Organization 外のユーザに対しても権限を与えることができるようになりました。

1. Organization のトップページから Repositories を選択し、対象のリポジトリをクリック
2. プロジェクトの Settings を選択
3. Collaborators and teams を選択
4. Collaborators に GitHub のアカウント名やメールアドレスを指定して追加

この機能を使用すると、Private なリポジトリに対しても外部のユーザからのアクセスを許可できてしまうので、Confidential な情報を扱っている場合は注意して設定する必要があります。
ちなみに、Organization 外のメンバーとして誰が登録されているかは、People 一覧のページの __Outside collaborators__ というところから確認できます。

