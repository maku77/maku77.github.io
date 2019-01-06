---
title: "GitHub の新しい Organization パーミッションモデル（2015年10月リリース）について理解する"
date: "2015-10-01"
---

GitHub の Organization アカウントの新しいパーミッションモデルが公開されました。

* https://github.com/blog/2064-new-organization-permissions-now-available


チームメンテナの設定が可能
----

チーム内のメンバに対して、**team maintainer** という役割を設定できるようになりました（念願！）。
これまでは、各チームに対するメンバ追加を行うときは、Organization の Owners メンバが追加作業を行う必要がありましたが、この機能によって、**チーム内のメンバ管理は、そのチームの代表者**に任せることができるようになります。

### 設定方法
1. Organization のトップページから Teams を選択
2. 対象のチームを選択
3. チームメンテナに設定したいメンバの設定アイコンをクリックし、Promote to team member を選択

チームメンテナとなったメンバは、自分の管理するチームに対して、自由にメンバを参加させたり外したりできます。
チーム名の変更や、チーム内メンバのチームメンテナへの昇格も自由に行えるようになるため、ある程度責任のあるリーダクラスの人をチームメンテナとして設定すべきです。

* 参考: [Giving "team maintainer" permissions to an organization member](https://help.github.com/articles/giving-team-maintainer-permissions-to-an-organization-member-early-access-program/)


Organization 内のリポジトリに対するデフォルトパーミッションを設定可能
----

デフォルトパーミッションとして Read/Write 権限を付加しておくことで、Organization 内のメンバ全員に対して、すべてのプロジェクト（リポジトリ）の参照権限、コミット権限を付けることができます。

直接コミットできるメンバはプロジェクトごとに限定したいけれど、コードの参照や、PullRequest くらいは全メンバに許可したい、ということが簡単にできます（これまでは、プロジェクトに対する参照権限を与えるために、対象のユーザを Read 権限のあるチームに追加させ、そのチームをプロジェクトに登録する、という分かりにくい作業が必要でした）。

### 設定方法
1. Organization のトップページから Settings を選択
2. Member privileges を選択
3. Default repository permission の項目を None から Read に変更

全メンバが全プロジェクトの開発に関わっているような Organization では、デフォルトのパーミッションとして Write を設定しておけば、アクセス権限管理のためにチームを作成する必要はなくなります。


チームをまたいだコラボレーションが可能
----

Organization 内の別のチームのメンバに対しても自由にコメントできるようになりました（推奨設定: ON）。
PullRequest や Issue などで議論する際に、別のチームのメンバに対しても [@name の形式で通知](https://help.github.com/articles/writing-on-github/#name-and-team-mentions-autocomplete) できます。

### 設定方法
既存のチームの Visible 設定は Secret になっているので、チーム間のコラボレーションを可能にするために、Visible 設定を Visible に変更する必要があります。

1. Organization のトップページから Teams を選択
2. 対象のチームを選択
3. Settings ボタンをクリック
4. Team visibility 設定を Visible に変更

既存のすべてのチームの Visible 設定をまとめて Visible にするには、Organization トップページの、**Settings => Team privacy** から変更できます。


メンバ一人ひとりのアクセス権限を確認可能
----

各メンバが、それぞれのリポジトリに対してどのようなアクセス権限（Admin/Write/Read) を持っているかを、一覧で確認することができるようになりました。

### 設定方法

1. Organization のトップページから People を選択
2. 対象のユーザの Manage Access ボタンをクリック

* 参考: [Managing an individual's access to an organization repository](https://help.github.com/articles/managing-an-individual-s-access-to-an-organization-repository-early-access-program/)


Organization 内のリポジトリの作成権限を追加
----

Owners メンバでなくても、Organization 内にプロジェクトを自由に作成することができるようになりました。

### 設定方法
1. Organization のトップページから Settings を選択
2. Member privileges を選択
3. Allow members to create repositories for this organization にチェック

Billing information などの管理はさせたくないけれど、プロジェクト作成くらいは各メンバに自由にさせたいような場合に有効です。
ただし、Private リポジトリも自由に作成できるようになってしまうので、Private リポジトリ数に制限があるような組織ではこの機能は有効にしない方がよいかもしれません。


Organization 外のメンバをプロジェクトメンバに追加することが可能
----

今までは、Organization 内のリポジトリ（プロジェクト）に対するアクセス権限は、Organization 内のチーム単位で与える仕組みでしたが、個人単位、かつ、Organization 外のユーザに対しても権限を与えることができるようになりました。

### 設定方法
1. Organization のトップページから対象のプロジェクトを選択
2. プロジェクトの Settings を選択
3. Collaborators & teams を選択
4. Collaborators に GitHub のアカウント名やメールアドレスを指定して追加

この機能を使用すると、Private なリポジトリに対しても外部のユーザからのアクセスを許すことができます。
Confidential な情報を扱っている場合は、気を付けて設定する必要があります。
ちなみに、Organization 外のメンバとして誰が登録されているかは、People 一覧のページの **Outside collaborators** というところから確認することができます。

