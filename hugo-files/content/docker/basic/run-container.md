---
title: "Docker コンテナで起動したシェルに接続する (docker container run/start/exec/attach)"
date: "2015-03-12"
lastmod: "2022-06-12"
url: "p/y8cfimp/"
tags: ["Docker"]
aliases: /docker/run-container
---

何をするか？
----

ここでは、Ubuntu の Docker イメージを使ってコンテナを起動し、その上で実行した bash シェルに接続して自由にコマンドを実行できるようにします。
__`docker container run`__、__`start`__、__`attach`__、__`exec`__ など似たようなコマンドがたくさんありますが、用途はそれぞれ違うのでここでひととおり理解しておきましょう。

| コマンド | 意味 |
| ---- | ---- |
| `docker container run` | 「イメージの取得」「コンテナの作成」「コンテナの起動」を連続して行う |
| `docker container start` | 作成済みのコンテナを「起動」する |
| `docker container attach` | 動作しているコンテナに「接続」する |
| `docker container exec` | 動作しているコンテナで「プロセスを起動」する |


docker container run はコンテナの「作成」と「起動」
----

__`docker container run`__（旧: `docker run`）は、もっとも頻繁に紹介されているコマンドですが、内部で複数のことを行うので __一番分かりにくいコマンド__ かもしれません。
`docker container run` は次のようなことを一度に実行します。

- 指定したイメージがなければダウンロード (`docker image pull`)
- Docker コンテナを作成する (`docker container create`)
- Docker コンテナを起動する (`docker container start`)

例えば、次のコマンドを実行すると、Docker イメージ (`ubuntu:20.04`) のダウンロード、Docker コンテナ (`mycon`) の作成、そのコンテナの起動までを一気に実行します。

```console
$ docker container run --name mycon -it ubuntu:20.04 /bin/bash
```

各オプションは次のような意味を持っています。

- {{< label-code "--name mycon" >}} 作成するコンテナに `mycon` という名前を付ける（省略するとランダムに単語を組み合わせた名前が付けられます。例: `cool_blackwell`）
- {{< label-code "-i" >}} 標準入力を有効にしたままにする（ようするに現在の端末上からキーボード入力できるようにする）
- {{< label-code "-t" >}} 起動するコマンドに対して TTY 端末を割り当てる（ようするに現在の端末上に bash のプロンプトを表示する）

オプションで `-i` と `-t` を指定しないと、一瞬で Docker コンテナが終了してしまうので、シェル接続する場合はこれらのオプションを指定する必要があります。
`-it` というオプション指定は、コンテナ起動してシェル接続する場合のおまじないと考えておけばよいです。

さて、ここで Docker を始めたばかりの人がハマるのが、次のようにコンテナ停止後にふたたび `docker container run` した場合です。

```
# 次のようにシェルを終了すると、Docker コンテナが停止する
root@65da3272d493:/# exit

# 再び run しようとするとエラー！ どうして！？
$ docker container run --name mycon -it ubuntu:20.04 /bin/bash
docker: Error response from daemon: Conflict. The container name "/mycon" is already
in use by container "65da3272d493c77c2034f58d9a6e0c80f302db3c058e8345e170dc72d67811f4".
You have to remove (or rename) that container to be able to reuse that name.
See 'docker run --help'.
```

これは、すでに存在する `mycon` という名前のコンテナをもう一度作成しようとしているからです。
`docker container run` コマンドが、`docker container create` を兼ねていることを思い出してください。
コンテナで動作している bash シェルを終了すると Docker コンテナも「停止」するのですが、停止状態のコンテナは残ったままです。
停止状態のコンテナ一覧は、次のようにして確認できます（`-a` オプションを付けないと、動作中のコンテナしか表示されません）。

```console
$ docker container ls -a
CONTAINER ID   IMAGE          COMMAND       CREATED          STATUS                        PORTS     NAMES
65da3272d493   ubuntu:20.04   "/bin/bash"   12 minutes ago   Exited (0) 4 minutes ago                mycon
```

停止状態のコンテナがすでに存在するときに、コンテナの起動＆シェルへの接続だけを行うには、次のように __`docker container start`__（後述）を使用します。

```console
$ docker container start -ai mycon
root@65da3272d493:/#
```

別の方法として、`docker container run` の __`--rm`__ オプションを指定するという方法もあります。
このオプションを指定すると、コンテナが停止したときに、自動的にコンテナが削除されます。

```console
$ docker container run --rm --name mycon -it ubuntu:20.04 /bin/bash
```

今回の例の場合、シェルから `exit` すると、`mycon` コンテナが停止してさらに削除まで行われます。
よって、その後もう一度 `docker container run` を実行しようとすると、`mycon` コンテナの作成からやり直しになるので結果的にうまく動作します。
つまり、1 コマンドだけ実行するための使い捨てのコンテナということですね。
すぐに破棄されるので、`--name mycon` オプションでコンテナに名前を付ける必要はあまりないかもしれません。


docker container start は存在するコンテナを起動する
----

すでに上記で使いましたが、__`docker container start`__ コマンド（旧: `docker start`）は「停止状態のコンテナ」を起動するためのコマンドです。
`docker container run` とは異なり、コンテナの作成は行わないので、存在しないコンテナを起動しようとするとエラーになります。

```console
$ docker container start mycon
Error response from daemon: No such container: mycon
```

コンテナを起動するには、あらかじめ __`docker container create`__ でコンテナを作成しておく必要があります。
過去に `docker container run` で作成したコンテナでも大丈夫です。

```console
$ docker container create -it --name mycon ubuntu:20.04 /bin/bash
327ad0cfd0047eb90a1ae4ec63769d0680697c48b074d640ed83b793c5d19beb
```

`docker container create` で作成したコンテナは、次のように停止状態のコンテナの一覧に表示されます。

```console
$ docker container ls -a
CONTAINER ID   IMAGE          COMMAND       CREATED          STATUS                        PORTS     NAMES
327ad0cfd004   ubuntu:20.04   "/bin/bash"   23 seconds ago   Created                                 mycon
```

あとは、`docker container start` でこのコンテナを起動できます。
起動と同時にシェルプロセスに接続するには、__`-ai`__ オプションを付けます。

```console
$ docker container start -ai mycon
root@327ad0cfd004:/#
```


docker container attach は動作しているコンテナに接続する
----

__`docker container attach`__ コマンド（例: `docker attach`）は、ローカルの標準入出力をコンテナのプロセス (PID=1) にアタッチします。

この振る舞いを調べるために、まずコンテナを起動してシェルに接続しておきます。

```console
$ docker container create -it --name mycon ubuntu:20.04 /bin/bash
$ docker container start -ai mycon
root@9bf22d6b0a5c:/#
```

ここで起動した bash シェルのプロセス ID (PID) は 1 になっています。

```console
root@9bf22d6b0a5c:/# ps
  PID TTY          TIME CMD
    1 pts/0    00:00:00 bash
   10 pts/0    00:00:00 ps
```

別のローカル端末を起動して、次のように `docker container attach` すると、上記の PID=1 の bash プロセスの標準入出力に接続されます。

```console
$ docker container attach mycon
root@9bf22d6b0a5c:/#
```

2 つの端末で bash シェルを表示している状態になりますが、どちらも PID=1 の同じプロセスに接続しているため、一方で入力したコマンドの結果がもう一方にも表示されます。
どちらかで `exit` すると、もう一方のシェルも閉じて Docker コンテナが終了します。

一方の接続だけを切りたい場合は、<kbd>Ctrl + P</kbd> <kbd>Ctrl + Q</kbd> と入力します。
こうすれば、もう一方のシェルプロセスは残ったままになり、コンテナも終了しません。


docker container exec は動作しているコンテナでプロセスを起動する
----

__`docker container exec`__ コマンド（旧: `docker exec`）を使うと、動作しているコンテナ上で新しいプロセスを起動することができます。
これを使うと、1 つのコンテナ上で __2 つのシェルを起動して接続する__ ということができます。

停止状態のコンテナを指定するとエラーになるので、まずはコンテナを起動してシェル接続しておきます。
この bash シェルの PID は前述のように 1 です。

```console
$ docker container create -it --name mycon ubuntu:20.04 /bin/bash
$ docker container start -ai mycon
root@e3d9f66349d4:/# ps
  PID TTY          TIME CMD
    1 pts/0    00:00:00 bash
   10 pts/0    00:00:00 ps
```

この状態で別の端末を起動して、`docker container exec` で新しい bash シェルを起動して接続します。
この bash シェルは新しいプロセスとして起動するので、別の PID が割り当てられます。

```console
$ docker container exec -it mycon /bin/bash
root@e3d9f66349d4:/# ps
  PID TTY          TIME CMD
   11 pts/1    00:00:00 bash
   20 pts/1    00:00:00 ps
```

これで、1 つのコンテナに 2 つのシェルを起動して接続できました。
一方のシェルを `exit` で終了しても、もう一方のシェル（およびコンテナ）は実行されたままです。
ここで `docker container attach` を使うと、同じプロセス (PID=1) に接続されてしまうので、`docker container exec` の方を使って新しいシェルのプロセスを作るのがポイントです。

