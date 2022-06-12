---
title: "Docker イメージを削除する (docker image rm/prune)"
url: "p/8fjnqtw/"
permalink: "p/8fjnqtw/"
date: "2022-06-12"
tags: ["Docker"]
---

docker image rm
----

### 使い方

`docker image pull` や `docker container run` 経由でダウンロードした Docker イメージを削除するには、__`docker image rm`__ コマンド（旧: `docker rmi`）を使用します。

```console
$ docker image rm ＜イメージ＞
```

削除するイメージは、`イメージ名:タグ名` や `イメージID` の形で指定します。

```console
$ docker image rm ubuntu:20.04
$ docker image rm 54c9d81cbb44
```

ダウンロード済みのイメージの一覧は __`docker image ls`__ コマンド（旧: `docker images`）で確認できます。

```console
$ docker image ls
REPOSITORY    TAG       IMAGE ID       CREATED       SIZE
ubuntu        20.04     54c9d81cbb44   2 weeks ago   72.8MB
```

### ヘルプ

```console
$ docker help image rm

Usage:  docker image rm [OPTIONS] IMAGE [IMAGE...]

Remove one or more images

Aliases:
  rm, rmi, remove

Options:
  -f, --force      Force removal of the image
      --no-prune   Do not delete untagged parents
```


docker image prune
----

### 使い方

__`docker image prune`__ コマンドを使用すると、使用していない Docker イメージをまとめて削除することができます。

```console
$ docker image prune
```

デフォルトでは、dangling images（エラーなどで予期せず残ってしまったイメージ）のみを削除します。
コンテナと関連づけられていないイメージをすべて削除するには、__`-a (--all)`__ オプションを指定して実行します。

```console
$ docker image prune -a
```

### ヘルプ

```console
$ docker help image prune

Usage:  docker image prune [OPTIONS]

Remove unused images

Options:
  -a, --all             Remove all unused images, not just dangling ones
      --filter filter   Provide filter values (e.g. 'until=<timestamp>')
  -f, --force           Do not prompt for confirmation
```

