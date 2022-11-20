---
title: "Docker コンテナとホスト PC の間でファイルをコピーする (docker container cp)"
url: "p/cqar8o5/"
date: "2022-02-21"
tags: ["Docker"]
aliases: /docker/docker-cp.html
---

[docker container cp](https://docs.docker.com/engine/reference/commandline/container_cp/) コマンドを使用すると、Docker コンテナ内の任意のファイルやディレクトリを、ホスト PC にコピーすることができます。

例えば、以下のようにすると、`mycon` コンテナ内の `/work/hello` というファイルを、ホスト側のカレントディレクトリにコピーすることができます。

```console
$ docker container cp mycon:/work/hello ./
```

ディレクトリごと丸ごとコピーすることもできます。

```console
$ docker container cp mycon:/work/imgs ./imgs
```

逆に、ホスト側のファイルを Docker コンテナにコピーすることもできます。

```console
$ docker container cp hoge.txt mycon:/work/tempdir
```

