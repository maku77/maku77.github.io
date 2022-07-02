---
title: "Docker コンテナからホスト側のサービスにアクセスする (host.docker.internal)"
url: "p/najs2ah/"
permalink: "p/najs2ah/"
date: "2022-07-02"
tags: ["Docker"]
---

特殊なホスト名 host.docker.internal を使う
----

Docker コンテナの中から、ホスト側で動作しているサービス（Web サーバーなど）にアクセスするには、IP アドレスの代わりに特殊な DNS 名 __`host.docker.internal`__ を使用します（`localhost` だとコンテナ自身を参照してしまうのでうまくいきません）。

#### コンテナからホスト上のサービスにアクセスする

```console
$ curl http://host.docker.internal:8000/
```


接続テスト
----

テストとして、ホスト上で Web サーバーを起動してコンテナからアクセスしてみます。
まず、何でもいいのでホスト側で Web サーバーを起動します。

#### Python で Web サーバーを起動する方法

```console
$ python -m http.server 8000
Serving HTTP on :: port 8000 (http://[::]:8000/) ...
```

- 参考: [Python で簡易的な HTTP サーバを立てる (http.server, SimpleHTTPServer)](/p/rr3cmu5/)

#### Docker で Web サーバーを起動する方法

```console
$ docker container run --rm -d -p 8000:80 --name webserver nginx:alpine

# 使い終わったら次のように停止してコンテナを削除
$ docker container stop webserver
```

次に、コンテナ側からこの Web サーバーに `curl` でアクセスできるか確認します。
ここでは、軽量のコンテナとして Alpine Linux を使っています。

```console
$ docker container run --rm -it alpine ash

/ # apk add curl
/ # curl http://host.docker.internal:8000/
<!DOCTYPE html>
<html>
...（省略）...
```

