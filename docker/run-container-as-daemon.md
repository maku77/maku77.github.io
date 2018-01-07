---
title: Docker コンテナをデーモンとして動作させる
date: "2015-03-11"
---

Docker コンテナ内でプログラムが動作している状態をキープするには、何らかの終了しないプログラムを動かす必要があります。
ここでは、1 秒おきに `Hello` と表示するプログラムを動かしてみます。
次のような簡単なシェルスクリプトです。

```
/bin/bash -c 'while true; do echo Hello; sleep 1; done'
```

上記のようなプログラムを、`docker run` コマンドで単純に Docker コンテナ内で動かすと、起動元の標準出力に `Hello` と表示され続けてしまいます。
そうではなくて、Docker コンテナ内の標準出力へ出力するようにするには、`docker run` コマンドに `-d` オプションを付けて実行します。

```
$ sudo docker run -d ubuntu:14.04 /bin/bash -c 'while true; do echo Hello; sleep 1; done'
e42ba7706dbe72295501c43e94f44b2d956e158fd8e0c585cda3ea21e6fff4b3
```

すると、`docker run` コマンドを実行した側には `Hello` と表示されず、プログラムを実行中の**「コンテナ ID」**が表示されます。
実行中の Docker コンテナの一覧は、以下のように `docker ps` コマンドで確認できます。

```
$ sudo docker ps
CONTAINER ID  IMAGE         COMMAND              CREATED              STATUS             PORTS    NAMES
e42ba7706dbe  ubuntu:14.04  /bin/bash -c 'while  About a minute ago   Up About a minute           prickly_fermat
```

このリストでは、動作中のコンテナのコンテナ ID や、その ID の代わりに使用できる名前（上記では `prickly_fermat`) などを確認できます。
Docker コンテナ内の標準出力に対して出力されている内容を調べるには、`docker logs` コマンドを使用します。

```
$ sudo docker logs e42b
Hello
Hello
Hello
```

デフォルトでは、その時点で出力済みの標準出力の内容をすべて出力して `docker logs` コマンドは終了します。
Linux の `tail -f` コマンドのように、標準出力への出力を監視して、出力を続けたい場合は `-f` オプションを付けます。

```
$ sudo docker logs -f e42b
Hello
Hello
Hello
（出力を待機）
```

動作中の Docker コンテナを外から停止するには、`docker stop` コマンドを使用します。

```
$ sudo docker stop prickly_fermat
prickly_fermat

$ sudo docker ps
CONTAINER ID       IMAGE       COMMAND       CREATED        STATUS        PORTS      NAMES
（空になっている）
```

