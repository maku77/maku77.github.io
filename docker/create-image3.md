---
title: "Docker イメージを作成する - docker build アプローチ"
date: "2015-03-12"
---

**Dockerfile** という、Docker イメージ作成のための手順書を作成しておくと、`docker build` コマンドを使って自動的にイメージを作成することができます。
下記は、Debian のイメージをベースにして、Python 3 をインストールしたイメージを作成する場合の `Dockerfile` の例です。

#### Dockerfile
```shell
# Debian (Wheezy) のイメージをベースに作成する（Debian は小さいのでオススメ）
FROM debian:wheezy

# 作者情報
MAINTAINER Maku Maku <makumaku@example.com>

# apt-get などのコマンドでプロキシ設定が必要な場合
ENV http_proxy http://proxy.example.com:8888/
ENV https_proxy http://proxy.example.com:8888/

# コンテナ構築のためのコマンド実行
RUN apt-get -qq update && apt-get -y install python3
```

この `Dockerfile` を元に Docker イメージを作成するには、下記のように `docker build` を実行します。

```
$ sudo docker build -t <イメージ名> <Dockerfileのあるディレクトリ>
```

イメージ名は `<user>/<repo>:<tag>` という構成で指定します（`:<tag>` を省略すると、自動的に `latest` というタグが付けられます）。
カレントディレクトリに `Dockerfile` ファイルがある場合、例えば以下のように実行します。

```
$ sudo docker build -t maku77/python3:v1 ./
```

`debian:wheezy` のイメージファイルが既に `docker pull` コマンドなどでローカルにキャッシュされている場合は、そのイメージが利用されるため、イメージ構築はより早く終わります。
`docker images` コマンドで、作成された Docker イメージを確認することができます。

```
$ sudo docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
maku77/python3      v1                  c3d5556730a9        4 minutes ago       93.88 MB
debian              wheezy              d5570ef1464a        4 days ago          84.98 MB
...
```

作成された Docker イメージ (`maku77/python3:v1`) 上で、`python3` コマンドを実行してみます。

```
$ sudo docker run -it maku77/python3:v1 python3
Python 3.2.3 (default, Feb 20 2013, 14:44:27)
[GCC 4.7.2] on linux2
Type "help", "copyright", "credits" or "license" for more information.
>>>
```

