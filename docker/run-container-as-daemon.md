---
title: "Docker コンテナをデーモンとして動作させる (docker container run -d, docker container logs)"
url: "p/dmpsvz3/"
permalink: "p/dmpsvz3/"
date: "2015-03-11"
lastmod: "2022-06-12"
tags: ["Docker"]
redirect_from:
  - /docker/run-container-as-daemon
---

コンテナのデーモン起動
----

Docker コンテナ内でプログラムが動作している状態をキープするには、何らかの終了しないプログラムを動かす必要があります（典型的には何らかのサーバープログラムです）。
ここでは、1 秒おきに `HELLO` と表示するプログラムを動かしてみます。
次のような簡単な bash プログラムです。

```bash
/bin/bash -c 'while true; do echo HELLO; sleep 1; done'
```

上記のようなプログラムを、`docker run` コマンドで単純に Docker コンテナ内で動かすと、起動元の標準出力に `HELLO` と表示され続けてしまいます。
そうではなくて、Docker コンテナ内の標準出力へ出力するようにするには、`docker run` コマンドに __`-d (--detach)`__ オプションを付けて実行します。

```console
$ docker container run -d ubuntu:20.04 /bin/bash -c 'while true; do echo HELLO; sleep 1; done'
e42ba7706dbe72295501c43e94f44b2d956e158fd8e0c585cda3ea21e6fff4b3
```

すると、`docker container run` コマンドを実行した側には `HELLO` と表示されず、プログラムを実行中の __コンテナ ID__ のみが表示されます。
実行中の Docker コンテナの一覧は、以下のように `docker container ps` コマンドで確認できます。

```console
$ docker container ps
CONTAINER ID  IMAGE         COMMAND              CREATED              STATUS             PORTS    NAMES
e42ba7706dbe  ubuntu:20.04  /bin/bash -c 'while  About a minute ago   Up About a minute           prickly_fermat
```

このリストでは、動作中のコンテナのコンテナ ID や、その ID の代わりに使用できる名前（上記では `prickly_fermat`) などを確認できます。


コンテナ内のデーモンの出力内容を確認する
----

Docker コンテナ内の標準出力に対して出力されている内容を調べるには、`docker container logs` コマンドを使用します。

```console
$ docker container logs e42ba77
HELLO
HELLO
HELLO
```

デフォルトでは、その時点で出力済みの標準出力の内容をすべて出力して `docker container logs` コマンドは終了します。
Linux の `tail -f` コマンドのように、標準出力への出力を監視して継続的に出力したい場合は `-f` オプションを付けます。

```console
$ docker logs -f e42ba77
HELLO
HELLO
HELLO
（出力を待機）
```

動作中の Docker コンテナを外から停止するには、__`docker container stop`__ コマンドを使用します。

```console
$ docker container stop prickly_fermat
prickly_fermat

$ docker container ps
CONTAINER ID       IMAGE       COMMAND       CREATED        STATUS        PORTS      NAMES
（空になっている）
```

