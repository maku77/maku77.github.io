---
title: "Docker コンテナをデーモンとして動作させる (docker container run -d, docker container logs)"
url: "p/dmpsvz3/"
date: "2015-03-11"
lastmod: "2022-07-18"
tags: ["Docker"]
aliases: /docker/run-container-as-daemon
---

コンテナのバックグラウンド起動 (docker container run -d)
----

Docker コンテナ内でプログラムが動作している状態をキープするには、何らかの終了しないプログラムを動かす必要があります（典型的には何らかのサーバープログラムです）。
ここでは、5 秒おきに `HELLO` と表示するプログラムを動かしてみます。
次のような簡単な bash プログラムです。

```bash
/bin/bash -c 'while true; do echo HELLO; sleep 5; done'
```

上記のようなプログラムを、`docker run` コマンドで単純に Docker コンテナ内で動かすと、起動元の端末（の標準出力）に `HELLO` と表示され続けてしまいます。
そうではなくて、Docker コンテナ内に閉じて出力するには、`docker run` コマンドに __`-d (--detach)`__ オプションを付けて実行します。

```console
$ docker container run --rm -d --name mycon ubuntu:22.04 /bin/bash -c 'while true; do echo HELLO; sleep 5; done'
57bf2e3edddf8dcc786ee42e9b2b5a1f50786d80bd45525afa21debcf108613b
```

引数の意味:

- {{< label-code "--rm" >}} コンテナ停止時にコンテナを自動で削除します。
- {{< label-code "-d (--detach)" >}} コンテナをバックグラウンドで動作させます。
- {{< label-code "--name mycon" >}} コンテナに `mycon` という名前を付けます。
- {{< label-code "ubuntu:22.04" >}} イメージとして Ubuntu 22.04 を使用します。

すると、`docker container run` コマンドを実行した側には `HELLO` と表示されず、プログラムを実行中の __コンテナ ID__ のみが表示されます。
実行中の Docker コンテナの一覧は、以下のように `docker container ps` コマンドで確認できます。

```console
$ docker container ls
CONTAINER ID  IMAGE         COMMAND                 CREATED        STATUS        PORTS  NAMES
57bf2e3edddf  ubuntu:22.04  "/bin/bash -c 'while…"  5 seconds ago  Up 5 seconds         mycon
```

このリストでは、動作中のコンテナのコンテナ ID や、その ID の代わりに使用できる名前（上記では `mycon`) などを確認できます。


コンテナ内のデーモンの出力内容を確認する (docker container logs)
----

Docker コンテナ内の標準出力に対して出力されている内容を調べるには、__`docker container logs`__ コマンドを使用します。

```console
$ docker container logs mycon
HELLO
HELLO
HELLO
...（省略）...
```

デフォルトでは、その時点で出力済みの標準出力の内容をすべて出力して `docker container logs` コマンドは終了します。
Linux の `tail -f` コマンドのように、標準出力への出力を監視して継続的に出力したい場合は __`-f`__ オプションを付けます。

```console
$ docker logs -f mycon
HELLO
HELLO
HELLO
...（出力を待機）...
```

動作中の Docker コンテナを外から停止するには、__`docker container stop`__ コマンドを使用します。

```console
$ docker container stop mycon
mycon

$ docker container ps
CONTAINER ID    IMAGE    COMMAND    CREATED    STATUS    PORTS    NAMES
...（空になっている）...
```


bash に仮想端末を割り当てて終了しないようにする方法
----

`docker container run` でコンテナを起動するときに、__`-t (--tty)`__ オプションで仮想端末 (pseudo-TTY) を割り当てて `bash` を起動することでも、コンテナを起動したままにできます（Ubuntu イメージはデフォルトで `bash` を起動するので、末尾の `bash` は省略できます）。

```console
$ docker container run --rm -dt --name mycon ubuntu:22.04 bash
52ac8c76e47ed495e2698676530c662531782f500504b03daa87da6c634c9fba
```

この状態はちょっと分かりにくいですが、内部的に仮想端末が割り当てられ、そこに繋がった `bash` がバックグラウンドで動き続けているというイメージです。
`-d (--detach)` オプションを付けて実行しているので、`bash` のプロンプトは見えなくなっています。

次のようにすると、確かにコンテナ上で `bash` が動作し続けていることを確認できます。

```console
$ docker container ls
CONTAINER ID  IMAGE         COMMAND  CREATED        STATUS        PORTS  NAMES
52ac8c76e47e  ubuntu:22.04  "bash"   3 minutes ago  Up 3 minutes         mycon
```

このコンテナ上で新しく `bash` を起動して何か操作したいときは次のようにします。

```console
$ docker container exec -it mycon bash
root@52ac8c76e47e:/#
```

`docker container exec` コマンドの詳細は下記記事を参照してください。

- [Docker コンテナで起動したシェルに接続する (docker container run/start/exec/attach)](/p/y8cfimp/)

