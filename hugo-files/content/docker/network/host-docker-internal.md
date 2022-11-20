---
title: "Docker コンテナからホスト側のサーバーにアクセスする (host.docker.internal)"
url: "p/najs2ah/"
date: "2022-07-02"
tags: ["Docker"]
---

コンテナからホスト側のサーバーにアクセスする
----

Docker コンテナの中から、ホスト側で動作しているサービス（Web サーバーなど）にアクセスするには、IP アドレスの代わりに特殊な DNS 名 __`host.docker.internal`__ を使用します（`localhost` だとコンテナ自身を参照してしまうのでうまくいきません）。

{{< code lang="console" title="コンテナからホスト上のサービスにアクセスする" >}}
$ curl http://host.docker.internal:8000/
{{< /code >}}


接続テスト
----

テストとして、ホスト上で Web サーバーを起動してコンテナからアクセスしてみます。
まず、何でもいいのでホスト側で Web サーバーを起動します。

{{< code lang="console" title="Docker で nginx サーバーを起動する方法" >}}
$ docker container run --rm -p 8000:80 --name webserver nginx:alpine
{{< /code >}}

{{< code lang="console" title="Python のワンライナーで Web サーバーを起動する方法" >}}
$ python -m http.server 8000
Serving HTTP on :: port 8000 (http://[::]:8000/) ...
{{< /code >}}

- 参考: [Python で簡易的な HTTP サーバを立てる (http.server, SimpleHTTPServer)](/p/rr3cmu5/)

次に、コンテナ側からこの Web サーバーに `curl` でアクセスできるか確認します。
ここでは、軽量のコンテナとして Alpine Linux を使っています。

{{< code title="コンテナからホスト側の Web サーバーにアクセス" >}}
$ docker container run --rm -it alpine ash

/ # apk add curl
/ # curl http://host.docker.internal:8000/
<!DOCTYPE html>
<html>
...（省略）...
{{< /code >}}

