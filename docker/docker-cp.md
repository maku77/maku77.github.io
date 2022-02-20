---
title: "Docker コンテナとホスト PC の間でファイルをコピーする (docker cp)"
date: "2022-02-21"
---

[docker cp](https://docs.docker.com/engine/reference/commandline/cp/) コマンドを使用すると、Docker コンテナ内の任意のファイルやディレクトリを、ホスト PC にコピーすることができます。

例えば、以下のようにすると、`mycon` コンテナ内の `/work/hello` というファイルを、ホスト側のカレントディレクトリにコピーすることができます。

```console
$ docker cp mycon:/work/hello ./
```

ディレクトリごと丸ごとコピーすることもできます。

```console
$ docker cp mycon:/work/imgs ./imgs
```

逆に、ホスト側のファイルを Docker コンテナにコピーすることもできます。

```console
$ docker cp hoge.txt mycon:/work/tempdir
```

