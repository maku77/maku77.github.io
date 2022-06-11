---
title: "Docker で Hello World 〜 イメージの取得からコンテナの起動まで (docker image pull, docker container run)"
url: "p/z8behko/"
permalink: "p/z8behko/"
date: "2015-03-15"
lastmod: "2022-02-18"
tags: ["Docker"]
redirect_from:
  - /docker/download-image
---

Docker イメージのダウンロード (docker image pull)
----

Docker でコンテナを作成するためには、ベースとなるイメージが必要です。
多くのイメージが [Docker Hub](https://hub.docker.com/) に用意されているので、通常はここからベースとなるイメージを取得します。
ここでは、Ubuntu 20.04 のイメージをダウンロードしてみます。
__`docker image pull`__（旧: `docker pull`）コマンドに、ダウンロードしたいイメージ名 `ubuntu` と、バージョンを表すタグ `20.04` を指定してダウンロードします。
省略するとデフォルトタグとして、最新バージョンを表す `lastest` が使用されます。

```console
$ docker image pull ubuntu:20.04
20.04: Pulling from library/ubuntu
Digest: sha256:669e010b58baf5beb2836b253c1fd5768333f0d1dbcb834f7c07a4dc93f474be
Status: Downloaded newer image for ubuntu:20.04
docker.io/library/ubuntu:20.04
```

ダウンロード済みのイメージの一覧は __`docker image ls`__ コマンド（旧: `docker images`）で確認できます。

```console
$ docker image ls
REPOSITORY    TAG       IMAGE ID       CREATED       SIZE
ubuntu        20.04     54c9d81cbb44   2 weeks ago   72.8MB
```

Docker イメージを削除したくなったときは、次のように __`docker image rm`__ コマンド（旧: `docker rmi` ）で削除できます。

```console
$ docker image rm ubuntu:20.04
```

- 参考: [Docker イメージを削除する (docker image rm)](/p/8fjnqtw/)


Docker イメージの実行 (docker container run)
----

__`docker container run`__ (`docker run`) コマンドを使うと、指定した Docker イメージからコンテナを起動し、任意のコマンドを実行することができます。
例えば、Ubuntu 20.04 の Docker イメージ上で、`echo` コマンドを実行するには、以下のようにします。

```console
$ docker container run ubuntu:20.04 echo Hello World!
Hello World!
```

`docker container run` コマンドにより、__Docker コンテナ__ が起動され、その中で `echo` が実行されます。
`echo` コマンドの実行が終わり次第、Docker コンテナはすぐに停止します。
`docker container run` コマンドで指定したイメージがまだローカルに存在しない場合は、そのイメージを公開レジストリである Docker Hub からダウンロードしてくれます。
なので、実は `docker image pull` であらかじめイメージを取得しておかなくても、上記の `docker container run` を直接実行することができます。

