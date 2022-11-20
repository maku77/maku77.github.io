---
title: "Docker イメージを Docker Hub に登録する (docker image push)"
url: "p/rwco2dp/"
date: "2015-03-12"
lastmod: "2022-01-21"
tags: ["Docker"]
aliases: /docker/register-image-to-dockerhub.html
---

作成した Docker イメージを [Docker Hub](https://hub.docker.com) リポジトリに登録すると、世界中のユーザがそのイメージを使えるようになります（プライベートにすることもできます）。
まずは、下記のサイトで、Docker Hub リポジトリのアカウントを作成しておく必要があります。

- [https://hub.docker.com](https://hub.docker.com)

アップロード予定の Docker イメージは、下記のように作成済みであるとします。

{{< code lang="console" title="ローカルのイメージの一覧を確認" >}}
$ docker image ls
REPOSITORY      TAG   IMAGE ID       CREATED         VIRTUAL SIZE
maku77/sample   v1    8a6608d7d353   7 minutes ago   188.3 MB
{{< /code >}}

Docker イメージのアップロードには、__`docker image push`__ コマンドを使用します。

{{< code lang="console" title="レジストリにイメージをデプロイ" >}}
$ docker image push maku77/sample:v1
...
（Docker Hub のログインパスワードなどを入力）
...
Pushing tag for rev [8a6608d7d353] on {https://cdn-registry-1.docker.io/v1/repositories/maku77/sample/tags/v1}
{{< /code >}}

Docker Hub で公開されているイメージは、`docker search` コマンドで検索することができるので、たった今登録したイメージを検索してみます。

{{< code lang="console" title="レジストリ上のイメージを検索" >}}
$ docker search maku77
NAME            DESCRIPTION   STARS   OFFICIAL   AUTOMATED
maku77/sample                 0
{{< /code >}}

これで、任意の Docker ホストからこのイメージをダウンロードして使えるようになりました。

{{< code lang="console" title="レジストリからイメージを取得" >}}
$ docker pull maku77/sample:v1
{{< /code >}}

