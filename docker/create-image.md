---
title: "Docker でコンテナイメージを作成する (docker image build, docker container commit)"
date: "2015-03-12"
lastmod: "2022-02-28"
---

2 つのイメージ作成方法
----

ここでは、Ubuntu の Docker イメージをカスタマイズして、独自の Docker イメージを作成してみます。
Docker イメージの作成方法には、大きく下記の 2 つの方法があります。

1. `Dockerfile` にイメージの作成手順を記載しておき、__`docker image build`__ で作成
2. OS イメージをインタラクティブモードで起動し、各種設定を行った後に __`docker container commit`__ で作成

再現性、ポータビリティといった観点から、`Dockerfile` を扱うアプローチが推奨されています。


docker image build アプローチ
----

__Dockerfile__ という、Docker イメージ作成のための手順書を作成しておくと、__`docker image build`__（あるいは `docker build`）コマンドを使って自動的にイメージを作成することができます。
下記は、Debian のイメージをベースにして、Python 3 をインストールしたイメージを作成する場合の `Dockerfile` の例です。

#### Dockerfile

```shell
# Debian (Wheezy) のイメージをベースにする
FROM debian:wheezy

# コンテナ構築のためのコマンド実行
RUN apt-get -qq update && apt-get -y install python3
```

この `Dockerfile` を元に Docker イメージを作成するには、下記のように `docker image build` を実行します。

```console
$ docker image build -t <イメージ名> <Dockerfileのあるディレクトリ>
```

`-t` の後ろに指定するイメージ名は __`<user>/<repo>:<tag>`__ という構成で指定します。
末尾のタグ名 (`:<tag>`) を省略すると、自動的に `latest` というタグが付けられます。
カレントディレクトリに `Dockerfile` ファイルがある場合は次のような感じでイメージをビルドします。

```console
$ docker build -t maku77/python3:v1 .
```

`debian:wheezy` のイメージファイルが既に `docker pull` コマンドなどでローカルにキャッシュされている場合は、そのイメージが利用されるため、イメージ構築はより早く終わります。
`docker image ls`（あるいは `docker images`）コマンドで、作成された Docker イメージを確認することができます。

```
$ docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
maku77/python3      v1                  c3d5556730a9        4 minutes ago       93.88 MB
debian              wheezy              d5570ef1464a        4 days ago          84.98 MB
...
```

作成された Docker イメージ (`maku77/python3:v1`) からコンテナを起動し、`python3` コマンドを実行してみます。

```
$ docker container run -it maku77/python3:v1 python3
Python 3.2.3 (default, Feb 20 2013, 14:44:27)
[GCC 4.7.2] on linux2
Type "help", "copyright", "credits" or "license" for more information.
>>>
```

### （おまけ）GitHub 上の Dockerfile からイメージを作成する

`docker image build` コマンドで `Dockerfile` のあるディレクトリを指定するときに、GitHub のリポジトリを指定することができます。

```console
$ docker mage build -t maku77/sample:v1 git@github.com:maku77/sample
```


docker container commit アプローチ
----

__`docker container commit`__（あるいは `docker commit`）コマンドによるアプローチでは、Docker イメージをインタラクティブに作成していくことができます。
Docker コンテナ上でソフトウェアのインストールなどを行い、最後に `docker container commit` コマンドを実行することにより、Docker イメージを作成します。
まずは、ベースイメージを指定して Docker コンテナを起動します。

```console
$ docker container run -it ubuntu:14.04 /bin/bash
```

Docker コンテナ上の bash プロンプトが表示されたら、その中で `apt-get` やファイルの作成などを行い、Docker イメージの構築作業を進めていきます。
下記の例では、Hello と表示するだけのシェルスクリプト (`/greet`) を作成しています。

```
root@c338a2f4c60e:/# cat > greet
#!/bin/bash
echo Hello
（ここで Ctrl-D で終了）

root@c338a2f4c60e:/# chmod +x greet
root@c338a2f4c60e:/# exit
```

起動元のシェルに戻ってきたら、`docker ps` コマンドで、最新のコンテナ ID（あるいは名前）を確認します。

```console
$ docker ps -a
CONTAINER ID    IMAGE           COMMAND      CREATED          STATUS                           PORTS    NAMES
c338a2f4c60e    ubuntu:14.04    /bin/bash    3 minutes ago    Exited (0) About a minute ago             loving_torvalds
```

これで、先ほどインタラクティブに構成した Docker コンテナの ID が `c338a2`、名前が `loving_torvalds` だということが分かるので、`docker container commit` コマンドを実行して、コンテナからイメージを生成します。
`docker container commit` コマンドのフォーマットは下記のようになっています。

```
docker container commit -a <作者> -c <コメント> <コンテナ> <リポジトリ名>:<タグ名>
```

`<コンテナ>` には、`docker ps` コマンドで確認したコンテナ ID か名前を指定します。
リポジトリ名は、Docker Hub の流儀に合わせて、__`<アカウント名>/<イメージ名>`__ という形で指定します。
例えば、Docker Hub アカウントが `maku77` で、`sample` という名前のイメージを作成する場合は次のようにします。

```console
$ docker commit -a 'Maku <maku77@example.com>' -m 'First commit' c338a2 maku77/sample:v1
8a6608d7d353d966f5cdc044b48b89158943c2dc9fd08b7a4832b43a21b5df41
```

Docker イメージの作成に成功すると、上記のように作成されたイメージの ID が表示されます。
`docker image ls` コマンドで、実際に新しくイメージが作成されたことを確認できます。

```
$ docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
maku77/sample       v1                  8a6608d7d353        7 minutes ago       188.3 MB
ubuntu              14.04               2103b00b3fdf        43 hours ago        188.3 MB
...
```

この Docker イメージを使って、先ほど作成した `/greet` コマンドを実行してみます。

```
$ docker container run maku77/sample:v1 /greet
Hello
```

