---
title: Docker のプロキシ設定
date: "2015-03-10"
---

(1) Docker ホスト側のプロキシ設定
====

`docker pull` などで、Docker Hub リポジトリからイメージを取得するときにプロキシ接続が必要な場合は、Docker ホスト用のプロキシ設定を行う必要があります。
実際には、`docker pull` コマンドは、docker デーモンに対して命令を送っているだけなので、docker デーモンの方がプロキシ設定を認識する必要があります。
docker デーモンのプロキシ設定は、下記のファイルで行います。

#### /etc/default/docker（あるいは docker.io）
```bash
export http_proxy="http://proxy.example.com:3128/"
```

設定変更後は、docker デーモンを再起動すれば OK です。

```
$ sudo service docker.io restart
```

これで、無事にプロキシ経由で `docker pull` できるようになります。


(2) Docker コンテナ内で使用するプロキシ設定
====

Docker コンテナの中で、`apt-get` や `curl` などを使ったインターネットアクセスを行う場合は、Docker コンテナ側でプロキシの設定を行う必要があります。
`docker run` で Docker コンテナを起動するときに、`-e` オプションで `http_proxy` 環境変数を渡して起動することができます。

```
$ sudo docker run -e http_proxy=http://proxy.example.com:8888/ -it debian:wheezy
```

もちろん、Docker コンテナを起動したあとで、コンテナ上のシェルから環境変数を設定することもできます。

```
$ sudo docker run -it debian:wheezy
root@7cb147891556:/# export http_proxy=http://proxy.example.com:8888/
root@7cb147891556:/#
```

Docker イメージとして、必ず特定のプロキシを使うことが決まっているのであれば、Docker イメージを作成する際の `Dockerfile` にプロキシ設定を埋め込んでしまうこともできます。
このプロキシ設定は、Docker イメージを構築する際の、`RUN` コマンド (`apt-get`) にも効いてきます。

#### Dockerfile
```
FROM debian:wheezy
ENV http_proxy http://proxy.example.com:8888/
ENV https_proxy http://proxy.example.com:8888/
RUN apt-get update && apt-get install python3
```

