---
title: "GitHub の REST API で Issue 情報を取得する方法いろいろ"
url: "p/uvoibwi/"
date: "2020-05-25"
tags: ["GitHub", "REST"]
aliases: /git/github/github-rest-api-issues.html
---

あるリポジトリの Issues の一覧を取得する
----

GitHub の __`/repos/<ユーザー名>/<リポジトリ名>/issues`__ という REST API エンドポイントにアクセスすると、指定したリポジトリ内の Issue の一覧を取得することができます。
特定の Organization が提供しているリポジトリを指定するときは、`ユーザー名` の部分を `Organization名` に置き換えてください。

### Public リポジトリの場合

Public リポジトリの情報は、誰でも簡単に取得することができます。
下記は、`curl` コマンドを使って REST API を呼び出すサンプルです。
ユーザー名とリポジトリ名の部分は適切な値に変更してください。

{{< code lang="console" title="Public なリポジトリの場合" >}}
$ USER_NAME=myname
$ REPO_NAME=myrepo
$ curl https://api.github.com/repos/$USER_NAME/$REPO_NAME/issues
{{< /code >}}

HTTP レスポンスのヘッダ情報も確認したい場合は、 __`-i`__ オプションを追加してください。
上記のような GET リクエストにより、Issue のリスト（PullRequest も含む）が JSON 形式で返されます。
単純な GET リクエストなので、ブラウザで直接 URL を入力することでも情報を取得することができます。

下記は、1 つの Issue だけ含むリポジトリから情報を取得したときのサンプルです。
この Issue には、`ラベル1` と `ラベル2` という 2 つのラベルが設定されています。

{{< accordion title="リポジトリの Issue リストの例 (JSON)" >}}
{{< code lang="json" >}}
[
  {
    "url": "https://api.github.com/repos/maku77/sample/issues/1",
    "repository_url": "https://api.github.com/repos/maku77/sample",
    "labels_url": "https://api.github.com/repos/maku77/sample/issues/1/labels{/name}",
    "comments_url": "https://api.github.com/repos/maku77/sample/issues/1/comments",
    "events_url": "https://api.github.com/repos/maku77/sample/issues/1/events",
    "html_url": "https://github.com/maku77/sample/issues/1",
    "id": 624149172,
    "node_id": "MDU6SXNzdWU2MjQxNDkxNzI=",
    "number": 1,
    "title": "Issueのタイトル1",
    "user": {
      "login": "maku77",
      "id": 5519503,
      "node_id": "MDQ6VXNlcjU1MTk1MDM=",
      "avatar_url": "https://avatars2.githubusercontent.com/u/5519503?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/maku77",
      "html_url": "https://github.com/maku77",
      "followers_url": "https://api.github.com/users/maku77/followers",
      "following_url": "https://api.github.com/users/maku77/following{/other_user}",
      "gists_url": "https://api.github.com/users/maku77/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/maku77/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/maku77/subscriptions",
      "organizations_url": "https://api.github.com/users/maku77/orgs",
      "repos_url": "https://api.github.com/users/maku77/repos",
      "events_url": "https://api.github.com/users/maku77/events{/privacy}",
      "received_events_url": "https://api.github.com/users/maku77/received_events",
      "type": "User",
      "site_admin": false
    },
    "labels": [
      {
        "id": 2086106907,
        "node_id": "MDU6TGFiZWwyMDg2MTA2OTA3",
        "url": "https://api.github.com/repos/maku77/sample/labels/%E3%83%A9%E3%83%99%E3%83%AB1",
        "name": "ラベル1",
        "color": "23c6bc",
        "default": false,
        "description": ""
      },
      {
        "id": 2086107046,
        "node_id": "MDU6TGFiZWwyMDg2MTA3MDQ2",
        "url": "https://api.github.com/repos/maku77/sample/labels/%E3%83%A9%E3%83%99%E3%83%AB2",
        "name": "ラベル2",
        "color": "f9bbdb",
        "default": false,
        "description": ""
      }
    ],
    "state": "open",
    "locked": false,
    "assignee": null,
    "assignees": [

    ],
    "milestone": null,
    "comments": 0,
    "created_at": "2020-05-25T08:51:56Z",
    "updated_at": "2020-05-25T08:51:56Z",
    "closed_at": null,
    "author_association": "OWNER",
    "body": "Issueのコメント1"
  }
]
{{< /code >}}
{{< /accordion >}}

Issue が 1 つも存在しない場合は、空の配列を示す JSON 文字列 (`[]`) が返されます。
リポジトリ名などの指定が間違っている場合は、次のような JSON 文字列が返されます（レスポンスのステータスコードは `HTTP/1.1 404 Not Found` です）。

{{< code lang="json" title="存在しないリポジトリを指定した場合" >}}
{
  "message": "Not Found",
  "documentation_url": "https://developer.github.com/v3/issues/#list-issues-for-a-repository"
}
{{< /code >}}

### Private リポジトリの場合

Private なリポジトリの情報を単純に GET リクエストで取得しようとすると、間違ったリポジトリ名を指定した場合と同様の Not Found メッセージが返されます。
`403 Forbidden` ではなく、`404 Not Found` を返すのは、Private リポジトリの存在に気づかれないようにするためのセキュリティ上の対策とされています。

Private リポジトリから Issue の一覧を取得するには、GitHub の [Personal access token 設定](https://github.com/settings/tokens) で発行したトークンや、OAuth トークンを使ってアクセスする必要があります。
二要素認証を有効にしている場合でも、これらのトークンによるアクセスは有効です。
トークンは次のように HTTP リクエストの __`Authorization`__ ヘッダで指定します。

{{< code lang="console" title="アクセストークンを指定した API 呼び出し" >}}
$ TOKEN=dd7bbe7538fd705d5350bc152c5e44d828d32b22
$ USER_NAME=myname
$ REPO_NAME=myrepo
$ curl -H "Authorization: token $TOKEN" https://api.github.com/repos/$USER_NAME/$REPO_NAME/issues
{{< /code >}}

アクセストークンのスコープとしては、次のように `repo (Full control of private repositories)` を割り当てておく必要があるようです。

{{< image w="700" border="true" src="img-001.png" title="Issue のリストを取得するためのアクセストークン設定" >}}


あるユーザーにアサインされた Issue の一覧を取得する
----

__`/user/issues`__ という REST API エンドポイントにアクセスすると、認証済みユーザー（つまり自分）にアサインされた Issue の一覧を取得することができます。
例えば、次のように、Personal Access Token を使った情報取得が可能です。

{{< code lang="console" title="ユーザーにアサインされた Issue の一覧を取得する" >}}
$ TOKEN=dd7bbe7538fd705d5350bc152c5e44d828d32b22
$ curl -H "Authorization: token $TOKEN" https://api.github.com/user/issues
{{< /code >}}


取得する Issue の種類を指定する
----

URL 末尾にクエリパラメーターを追加することで、取得する Issue を絞り込むことができます。

### closed 状態の Issue を取得する (`state=closed`)

{{< code lang="console" title="例: Close 済みの Issue を取得する" >}}
$ USER_NAME=myname
$ REPO_NAME=myrepo
$ curl https://api.github.com/repos/$USER_NAME/$REPO_NAME/issues?state=closed
{{< /code >}}

__`state`__ パラメーターを指定すると、open/closed 状態を指定して Issue リストを取得することができます。

- `state=open` ... Open 状態の Issue のみ取得（デフォルト）
- `state=closed` ... Close 状態の Issue のみ取得
- `state=all` ... すべての Issue を取得

ちなみに `curl` コマンドでは、URL 末尾のクエリパラメーター (`?...`) の代わりに、次のように __`--data`__ オプションで指定することもできます。
スクリプトを作成するときに便利かもしれません。
`--data` オプションを指定すると、`curl` は POST メソッドでアクセスしようとするので、__`-G`__ も同時に指定して `GET` メソッドを使うように指示する必要があります。

{{< code lang="console" title="クエリパラメーターの代わりに --data オプションを使う方法" >}}
$ curl -G --data "state=closed" https://api.github.com/repos/$USER_NAME/$REPO_NAME/issues
{{< /code >}}

### あるラベルが付いている Issue を取得する (`labels`)

{{< code lang="console" title="例: 'ラベル1' というラベルを持つ Issue を取得する" >}}
$ curl -G --data-urlencode "labels=ラベル1" https://api.github.com/repos/$USER_NAME/$REPO_NAME/issues
{{< /code >}}

__`labels`__ パラメーターでは、複数のラベル名をカンマ区切りで指定することができます。
URL 末尾のクエリパラメーターとして指定することもできるのですが、日本語などを含むラベルは URL エンコードした形で指定しなければいけないので、ここでは curl の __`--data-urlencode`__ オプションを使って `labels` パラメーターを渡しています。
この場合も前述の例と同様に、`-G` オプションを指定して `GET` メソッドを使う必要があります。

### その他のパラメーター

Issue の一覧を取得するときには、他にもいろいろクエリパラメーターで絞り込みを行えます。
詳しくは下記の GitHub Developers Guide のサイトで確認してみてください。

- 参考: [Issues ｜ GitHub Developer Guide](https://docs.github.com/ja/rest/issues?apiVersion=2022-11-28#parameters-3)

