---
title: "GitHub REST API を使用する"
date: "2019-01-30"
lastmod: "2019-12-04"
---

GitHub の REST API でできること
----

GitHub は Web API (REST API) を提供しており、コマンドラインや任意のアプリケーションから利用することで、GitHub の操作を自動化することができます。
Linux の `curl` コマンドや、スクリプト（Python や Ruby など）から簡単に利用できるので、Organization やリポジトリを管理する立場にある人はぜひ使ってみてください。

- [GitHub - REST API v3](https://developer.github.com/v3/)

例えば、下記のようなことを REST API によって自動化することができます。

* [Users](https://developer.github.com/v3/users/)
    * 指定したユーザの詳細情報を取得する
    * 二要素認証が設定できていないユーザのリストを取得する
* [Repositories](https://developer.github.com/v3/repos/)
    * 指定したオーナー（ユーザー or 組織）のリポジトリのリストを取得する
* [Organizations/Members](https://developer.github.com/v3/orgs/members/)
    * 指定した組織のユーザーリストを取得する
* [Pull Requests](https://developer.github.com/v3/pulls/)
    * Pull Request のリストを取得する
* [Issues](https://developer.github.com/v3/issues/)
    * 指定したリポジトリの Issue のリストを取得する
* [Projects](https://developer.github.com/v3/projects/)
    * 指定したリポジトリのプロジェクト（かんばん）のリストを取得する


API トークンを発行する
----

REST API を呼び出すには、**API トークン (HTTPS Access Token)** が必要です。
プライベートリポジトリに関する情報を取得するには、そのリポジトリへのアクセス権限を持つユーザが作成した API トークンを使用して REST API を呼び出す必要があります。

GitHub の API トークンは下記のページで生成することができます。

- [GitHub - Personal access token](https://github.com/settings/tokens)

<samp>Generate new token</samp> のボタンを押せば、新しいトークンを発行できます。
Select scope の項目で、そのトークンを使って使用できる機能 (API) の範囲を制御できるようになっているので、必要な機能のみにチェックを入れるようにしてください。

API トークンは次のようなハッシュ値です。
REST API を呼び出す際に必要になるので保存しておいてください。

```
c647c084ed5f60ee7fd53a22537fd6602ace4ced
```


REST API を呼び出す
----

REST API は単純な HTTP リクエストをベースに動作するため、Linux の `curl` コマンドなどを使って簡単に実行できます。

#### 例: オープンな Pull Request の一覧を取得する (github-api.sh)

```bash
#!/bin/bash
GITHUB_TOKEN=9b671cc01a1d966f3a3d1dc3366867836aa19c5d
GITHUB_BASEURL=https://api.github.com
GITHUB_API=/repos/YourOrganization/YourProject/pulls?state=open

curl -k -s -u :$GITHUB_TOKEN $GITHUB_BASEURL$GITHUB_API
```

`GITHUB_TOKEN` 変数は自分で作成した API トークンに、`GITHUB_API` 変数は実行した API にそれぞれ置き換えて実行してください。

- **`GITHUB_TOKEN`**: API トークン
- **`GITHUB_API`**: 実行したい REST API

上記のサンプルコードでは、指定した Organization あるいはユーザ（ここでは `YourOrganization`) の、指定したリポジトリ (ここでは `YourProject`) のうち、オープン状態 (`state=open`) になっている Pull リクエストの一覧を取得しています。
結果は JSON 形式で出力されますが、長いのでここでは省略します。

`GITHUB_API` 変数は、他にも下記のような感じで置き換えて実行できます。
詳細は [GitHub REST API](https://developer.github.com/v3/) のサイトを参照してください。

- **`/users/MyName`**: 指定したユーザ (`MyName`) の詳細情報
- **`/orgs/MyOrg/members`**: 指定した Organization (`MyOrg`) 内のメンバーリスト

ちなみに、上記の `curl` コマンド実行時に指定しているオプションの意味は下記の通りです。

- **`-u USER:PASSWORD`**: Server user, password and login options
- **`-k`**: Allow connections to SSL sites without certs
- **`-s`**: Silent mode. Don't output anything


### プロキシ環境から curl 実行する場合

プロキシ環境内から実行する場合は、`https_proxy` 環境変数でプロキシサーバのアドレスとポート番号を設定してから実行してください。

```
$ export https_proxy=https://proxy.example.com:10080
$ ./github-api.sh
```

プロキシは、`curl` コマンドの `--proxy (-x)` オプションで指定する方法もあります。

```
$ curl --proxy http://proxy.example.com:10080 https://www.google.com/
```


### すべての結果が得られない場合

REST API の結果は、デフォルトでは[ページネーション](https://developer.github.com/v3/#pagination)によって **30件ずつ** しか返されません。
一度にたくさんの結果を取得するには、URL の末尾に `per_page` オプションを指定することで**最大 100 件ずつ**取得することができます。
それ以上の件数を取得するときは、`page` オプションを使用して取得位置を切り替える必要があります。

```bash
GITHUB_API=/user/repos?per_page=100&page=1  # 1～100件目のリポジトリ情報
GITHUB_API=/user/repos?per_page=100&page=2  # 101～200件目のリポジトリ情報
GITHUB_API=/user/repos?per_page=100&page=3  # 201～300件目のリポジトリ情報
```

[ページの切り替え処理](https://developer.github.com/v3/guides/traversing-with-pagination/)を自動化するのは若干面倒ですが、件数が多くても 500 件以下であることが分かっているのであれば、下記のように単純にループで取得してしまえば事足りるかもしれません。

```bash
GITHUB_API=/user/repos?per_page=100

# 100件の取得を5ページ分繰り返す
for i in `seq 1 5`; do
  curl -k -s -u :$GITHUB_TOKEN $GITHUB_BASEURL$GITHUB_API\&page=$i
done
```


（おまけ）Windows のバッチファイルで GitHub REST API
----

Window 10 version 1803 (2018年4月) 以降には、デフォルトで `curl` コマンドがインストールされるようになったみたいです。
これで、Windows のバッチファイルからも気兼ねなく `curl` コマンドを使えます。
次の例では、バッチファイルから `curl` を使って、GitHub の REST API を呼び出すサンプルコードです。

#### 例: members.bat（YourOrganization のメンバー一覧を取得する）

```bat
@echo off
setlocal

REM ---------------
REM  User settings
REM ---------------
set GITHUB_TOKEN=b72209e0b0ed0ad32d7b8cc38b36cf71b416d0a0
set GITHUB_API=/orgs/YourOrganization/members
REM set GITHUB_API=/orgs/YourOrganization/repos
REM set GITHUB_API=/user/repos
REM set PROXY=-x http://proxy.example.com:10080

REM -----------------
REM  Call GitHub API
REM -----------------
set GITHUB_BASEURL=https://api.github.com

FOR /L %%A IN (1, 1, 5) DO (
    curl -k -s %PROXY% -u :%GITHUB_TOKEN% "%GITHUB_BASEURL%%GITHUB_API%?per_page=100&page=%%A"
)
```

FOR ループでページネーションを切り替えながら 100 件ずつ取得することで、1～500 件目までの情報を表示しています。
プロキシ環境下で実行したい場合は、`PROXY` 変数の `REM` コメントアウトを外してください。

`GITHUB_API` 変数の部分を置き換えることで、取得する情報を切り替えることができます。

- `set GITHUB_API=/orgs/YourOrganization/members` ... 組織のメンバーリスト
- `set GITHUB_API=/orgs/YourOrganization/repos` ... 組織のリポジトリリスト
- `set GITHUB_API=/user/repos` ... 自分のリポジトリリスト

