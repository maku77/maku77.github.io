---
title: "GitHub の REST API を Python から使用する"
url: "p/8axzppz/"
date: "2020-05-30"
tags: ["GitHub", "REST"]
aliases: /git/github/github-rest-with-python.html
---

何を作るか？
----

Python で GitHub API を利用するアプリケーションを作るときは、GitHub API を実行して情報取得する部分を、1 つのモジュールとして切り出しておくと全体のコードがすっきりします。
ここでは、実装例として `GitHubApi` というクラスを作り、次のようなことを行えるようにします。

- `get_members()` や `get_issues()` といった直感的な API で GitHub の情報を取得できるようにする
- オプションでプロキシや Personal Access Token を指定できるようにする
- ページネーションによる連続アクセスで、多くの情報を一度に取得できるようにする

GitHub の REST API に関しては、下記の記事を参照してください。

- [GitHub の REST API を使用する](/p/mprs3v6/)
- [GitHub の REST API で Issue 情報を取得する方法いろいろ](/p/uvoibwi/)


GitHubApi クラスを実装する
----

ここで紹介する `GitHubApi` クラスを使うと、次のようなシンプルなコードで GitHub API を呼び出して、その結果を Python のオブジェクトとして取得することができます。

```python
# ユーザー情報を取得する
user = api.get_user('maku77')
print(user['login'])
```

下記は、`GitHubApi` クラスの実装例です。

{{< code lang="python" title="github.py" >}}
import json
import re
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

    def __get_json(self, url, paginate=False):
        req = self.__create_request(url)
        try:
            with urllib.request.urlopen(req) as res:
                json_text = res.read().decode('utf-8')
                json_obj = json.loads(json_text)
                # ページネーションによる繰り返し取得
                if paginate:
                    next_link = self.__get_next_link(res.info())
                    if next_link:
                        json_obj.extend(self.__get_json(next_link))
                return json_obj
        except urllib.error.URLError as err:
            print('Could not access: %s' % req.full_url, file=sys.stderr)
            print(err, file=sys.stderr)
            sys.exit(1)

    # ページネーションによる連続取得が必要な場合は、
    # 次のアドレスを返す。必要ない場合は None を返す。
    def __get_next_link(self, response_headers):
        link = response_headers['Link']
        if not link:
            return None
        match = re.search(r'<(\S+)>; rel="next"', link)
        if match:
            return match.group(1)
        return None

    def __create_request(self, url):
        req = urllib.request.Request(url)
        if 'token' in self.option:
            req.add_header('Authorization', 'token %s' % self.option['token'])
        if 'proxy' in self.option:
            req.set_proxy(self.option['proxy'], 'http')
            req.set_proxy(self.option['proxy'], 'https')
        return req

    # 指定したユーザーの情報を取得します
    def get_user(self, username):
        url = self.BASE_URL + '/users/%s' % quote(username)
        return self.__get_json(url)

    # 指定した組織のメンバーリストを取得します
    def get_members(self, org):
        url = self.BASE_URL + '/orgs/%s/members?per_page=100' % quote(org)
        return self.__get_json(url, paginate=True)

    # 指定した組織のリポジトリ一覧を取得します
    def get_org_repos(self, org):
        url = self.BASE_URL + '/orgs/%s/repos?per_page=100' % quote(org)
        return self.__get_json(url, paginate=True)

    # 指定したユーザーのリポジトリ一覧を取得します
    def get_user_repos(self, user):
        url = self.BASE_URL + '/users/%s/repos?per_page=100' % quote(user)
        return self.__get_json(url, paginate=True)

    # 指定したリポジトリの Issue 一覧を取得します
    def get_issues(self, owner, repo, params={}):
        url = self.BASE_URL + '/repos/%s/%s/issues?per_page=100' % (quote(owner), quote(repo))
        if params:
            url = '%s&%s' % (url, urlencode(params))
        return self.__get_json(url, paginate=True)
{{< /code >}}

最後の方にある `get_xxx` 系のメソッドが、GitHub API を呼び出すための public メソッドです。
同じようにメソッドを追加していけば、いろいろな GitHub API に対応することができます。
どのような API があるかは、[GitHub REST API v3 のサイト](https://developer.github.com/v3/) を参照してください。


ページネーション処理
----

GitHub API v3 でリポジトリの一覧などを取得すると、デフォルトでは 30 件ずつしか結果を返してくれません。
URL 末尾のクエリパラメーターで `per_page=100` と指定すれば、100 件までは一度に取得できるのですが、100 件を超えるすべての情報を取得したいときは、[ページネーションの仕組み](https://developer.github.com/v3/guides/traversing-with-pagination/) を使って何度かに分けて取得する必要があります。

次ページの情報を取得するときは、`page=2` のようにクエリ指定する必要があるのですが、次ページが存在する場合は HTTP レスポンスの `Link` ヘッダーに次ページのアドレスが含まれているので、このアドレスを使って繰り返しアクセスするのがよいでしょう。
上記のサンプルコードでは、`__get_json` メソッドの中で次のようにページネーション処理しています。
次のページがある場合は、再帰的に `__get_json` を呼び出し、その結果を `json_obj` 配列に結合しています。

```python
# ページネーションによる繰り返し取得
if paginate:
    next_link = self.__get_next_link(res.info())
    if next_link:
        json_obj.extend(self.__get_json(next_link))
```


GitHubApi クラスの使用例いろいろ
----

下記は、`GitHubApi` クラスを使って、いろいろな情報を取得するサンプルコードです。

```python
from github import GitHubApi

api = GitHubApi()

# ユーザー情報を取得
user = api.get_user('maku77')
print(user['login'])
print(user['blog'])
print(user['company'])

# 指定した組織のリポジトリ一覧を取得します
repos = api.get_org_repos('sony')
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

プロキシや Personal Access Token を指定してアクセスする必要がある場合は、`GitHubApi` コンストラクタのオプションで次のように指定します。

```python
options = {
    'proxy': 'proxy.example.com:80',
    'token': 'a0709c8d0ac21812d9c4b8511298b33ec0fd2813'
}
api = GitHubApi(options)
```

ここでは、取得した情報のうち、ごく一部のプロパティだけを参照していますが、GitHub API が返す値にはもっといろいろな情報が含まれています。
これらの情報をうまく使えば、日々の業務を自動化できるかもしれません。

