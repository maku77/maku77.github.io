---
title: "Python の urllib による HTTP 通信 (4) Basic 認証 (urllib.request.HTTPBasicAuthHandler)"
url: "p/4yiygwe/"
date: "2015-11-19"
tags: ["Python", "HTTP"]
aliases: /python/web/http-request-with-basic-auth.html
---

`urllib.request` モジュールを使って Basic 認証の設定された Web サイトにアクセスすると、デフォルトでは下記のようなエラーが発生します。

{{< code title="Basic 認証エラー" >}}
urllib.error.HTTPError: HTTP Error 401: Authorization Required
{{< /code >}}

__`HTTPBasicAuthHandler`__ を使用すると、各 Web サイトにアクセスするときのユーザ名と、パスワード情報を設定することができます。
具体的には、Basic 認証のかかった Web サイトにアクセスした場合に Web サーバから返される __realm 情報__ と、__ベースとなる URI 情報__ に対して、使用するユーザ名とパスワードを関連付けます。

下記のサンプルでは、`http://example.com/secret/` 以下にアクセスする際に、Basic 認証のユーザ名、パスワードとして、`user1`、`pass1` を利用するように設定しています。
ここでは、realm 情報は無視し、URI 情報だけに関連付けるようにするため、__`HTTPPassswordMgrWithDefaultRealm`__ を使用しています。

{{< code lang="python" title="Basic 認証の付いたサイトにアクセスする" hl_lines="4-13" >}}
import urllib.request
import os.path

def setup_basic_auth(base_uri, user, password):
    password_mgr = urllib.request.HTTPPasswordMgrWithDefaultRealm()
    password_mgr.add_password(
            realm=None,
            uri=base_uri,
            user=user,
            passwd=password)
    auth_handler = urllib.request.HTTPBasicAuthHandler(password_mgr)
    opener = urllib.request.build_opener(auth_handler)
    urllib.request.install_opener(opener)

def download_file(url):
    filename = os.path.basename(url) or 'index.html'
    print('Downloading ... {0} as {1}'.format(url, filename))
    urllib.request.urlretrieve(url, filename)

if __name__ == '__main__':
    setup_basic_auth('http://example.com/secret/', 'user1', 'pass1')
    download_file('http://example.com/secret/sample.zip')
{{< /code >}}

