---
title: "Docker イメージを削除する (docker image rm)"
url: "p/8fjnqtw/"
permalink: "p/8fjnqtw/"
date: "2022-06-12"
tags: ["Docker"]
---

docker image rm の使い方
----

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


（おまけ）docker image rm のヘルプ
----

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

