---
title: Docker イメージを Docker Hub に登録する
date: "2015-03-12"
---

作成した Docker イメージを Docker Hub リポジトリに登録すると、世界中のユーザがそのイメージを使えるようになります（プライベートにすることもできます）。
まずは、下記のサイトで、Docker Hub リポジトリのアカウントを作成しておく必要があります。

- https://hub.docker.com/

アップロード予定の Docker イメージは、下記のように作成済みであるとします。

```
$ sudo docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
maku77/sample       v1                  8a6608d7d353        7 minutes ago       188.3 MB
```

Docker イメージのアップロードには、`docker push` コマンドを使用します。

```
$ sudo docker push maku77/sample:v1
...
（Docker Hub のログインパスワードなどを入力）
...
Pushing tag for rev [8a6608d7d353] on {https://cdn-registry-1.docker.io/v1/repositories/maku77/sample/tags/v1}
```

Docker Hub で公開されているイメージは、`docker search` コマンドで検索することができるので、たった今登録したイメージを検索してみます。

```
$ sudo docker search maku77
NAME            DESCRIPTION   STARS     OFFICIAL   AUTOMATED
maku77/sample                 0
```

これで、任意の Docker ホストからこのイメージをダウンロードして使えるようになりました。

```
$ sudo docker pull maku77/sample:v1
```

