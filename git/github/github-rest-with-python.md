---
title: "GitHub REST API を Python から使用する"
date: "2020-05-30"
---

何を作るか？
----

Python で GitHub API を利用するアプリケーションを作るときは、GitHub API を実行して情報取得する部分を、1 つのモジュールとして切り出しておくと全体のコードがすっきりします。
ここでは、サンプルとして `GitHubApi` というクラスを作ってみます。

GitHub の REST API 自体の説明は、下記の記事を参照してください。

- [GitHub REST API を使用する](./github-rest-api)
- [GitHub REST API で Issue 情報を取得する方法いろいろ](./github-rest-api-issues.html)


GitHubApi クラスの実装
----

ここで紹介する `GitHubApi` クラスを使うと、GitHub API を簡単に呼び出して、その結果を Python のオブジェクトとして取得することができます。
使用イメージは次のような感じです。

```python
# ユーザー情報を取得する
user = api.get_user('maku77')
print(user['login'])
```

下記は `GitHubApi` クラスの実装です。

### github.py

```python
import json
import sys
from urllib.parse import quote, urlencode
import urllib.request

class GitHubApi:
    BASE_URL = 'https://api.github.com'
    UNVERIFY_SSL = True

    def __init__(self, option={}):
        self.option = option
        if self.UNVERIFY_SSL:
            self.__unverify_ssl()

    def __unverify_ssl(self):
        import ssl
        ssl._create_default_https_context = ssl._create_unverified_context

    def __get_json(self, url):
        req = self.__create_request(url)
        try:
            with urllib.request.urlopen(req) as res:
                json_text = res.read().decode('utf-8')
                return json.loads(json_text)
        except urllib.error.URLError as err:
            print('Could not access: %s' % req.full_url, file=sys.stderr)
            print(err, file=sys.stderr)
            sys.exit(1)

    def __create_request(self, url):
        req = urllib.request.Request(self.BASE_URL + url)
        if 'token' in self.option:
            req.add_header('Authorization', 'token %s' % self.option['token'])
        if 'proxy' in self.option:
            req.set_proxy(self.option['proxy'], 'http')
        return req

    # 指定したユーザーの情報を取得します
    def get_user(self, username):
        url = '/users/%s' % quote(username)
        return self.__get_json(url)

    # 指定した組織のメンバーリストを取得します
    def get_members(self, org):
        url = '/orgs/%s/members' % quote(org)
        return self.__get_json(url)

    # 指定したオーナー（ユーザー／組織）のリポジトリ一覧を取得します
    def get_repos(self, owner):
        url = '/users/%s/repos' % quote(owner)
        return self.__get_json(url)

    # 指定したリポジトリの Issue 一覧を取得します
    def get_issues(self, owner, repo, params={}):
        url = '/repos/%s/%s/issues' % (quote(owner), quote(repo))
        if params:
            url = '%s?%s' % (url, urlencode(params))
        return self.__get_json(url)
```

最後の方にある `get_xxx` 系のメソッドが、GitHub API を呼び出すための public メソッドです。
同じようにメソッドを追加していけば、いろいろな GitHub API に対応することができます。
どのような API があるかは、[GitHub REST API v3 のサイト](https://developer.github.com/v3/) を参照してください。

下記は、上記の `GitHubApi` クラスを使って、いろいろな情報を取得するサンプルコードです。

### 使用例いろいろ

```python
from github import GitHubApi

api = GitHubApi()

# ユーザー情報を取得
user = api.get_user('maku77')
print(user['login'])
print(user['blog'])
print(user['company'])

# 指定したオーナー（ユーザー／組織）のリポジトリ一覧を取得します
repos = api.get_repos('sony')
for repo in repos:
    print(repo['name'])
    print(repo['full_name'])
    print(repo['description'])
    print(repo['html_url'])
    print()

# 指定した組織のメンバーリストを取得
members = api.get_members('sony')
for member in members:
    print(member['login'])

# 指定したリポジトリの Issue 一覧を取得します（open 状態のもの）
issues = api.get_issues('sony', 'nnabla')
for issue in issues:
    print(issue['title'])

# 指定したリポジトリの Issue 一覧を取得します（close 状態のものも取得）
issues = api.get_issues('sony', 'nnabla', params={'state': 'all'})
for issue in issues:
    print(issue['title'])
```

ここでは、取得した情報のうち、ごく一部のプロパティだけを参照していますが、GitHub API が返す値にはもっといろいろな情報が含まれています。
これらの情報をうまく使えば、日々の業務を自動化できるかもしれません。

