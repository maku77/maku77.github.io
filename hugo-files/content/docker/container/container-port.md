---
title: "Docker コンテナ側の公開ポートがホスト側のどのポートにマッピングされているか調べる (docker container port)"
url: "p/ow258be/"
date: "2015-03-12"
lastmod: "2022-02-28"
tags: ["Docker"]
aliases: /docker/port-mappings
---

docker container port コマンドの使い方
----

__`docker container port`__（あるいは `docker port`）コマンドを使用すると、Docker コンテナの中で公開されているポート番号 (private port) が、ホスト側のどのポート番号にマッピングされているかを調べることができます。

```console
$ docker container port <コンテナ名> [コンテナ側ポート番号]
```

例えば、`my-nginx` コンテナの中で Web サーバーが動作しているとして、それがホスト側から見てどのポート番号にマッピングされているかを調べるには以下のようにします。

```console
$ docker container port my-nginx
80/tcp -> 0.0.0.0:8000
80/tcp -> :::8000
```

この例では、コンテナ側の 80 番ポートが、ホスト側の 8000 番ポートにマッピングされていることがわかります。
つまり、コンテナの Web サーバーには、`http://localhost:8000` といったアドレスでアクセスできます。

特定の（コンテナ側の）ポート番号に関してだけ調べることもできます。
次の例では、コンテナ側の 80 番ポートに対して、ホスト側のどのポートがマッピングされているかを調べています。

```console
$ docker container port my-nginx 80
0.0.0.0:8000
:::8000
```


（おまけ）nginx サーバーを立ち上げて実際に試してみる
----

Alpine Linux をベースにした nginx サーバーのイメージ (`nginx:alpine`) はとても軽量（20MB くらい）なので、これを利用して、上記の `docker container port` コマンドを試してみます。

コンテナで nginx サーバーを起動するには次のように実行します。
ここでは、コンテナ名を `my-nginx` とし、ホスト側の 8000 番ポートとコンテナ側の 80 番ポートを結びつけています。

```console
$ docker container run -d -p 8000:80 --name my-nginx nginx:alpine
```

コンテナが起動したら、ホスト PC 側で `http://localhost:8000` にアクセスすると Web ページを表示できます。
この状態で、次のようにポート番号のマッピング情報を確認できます。

```console
$ docker container port my-nginx
80/tcp -> 0.0.0.0:8000
80/tcp -> :::8000
```

試し終わったら、`my-nginx` コンテナを削除しておきましょう。

```console
$ docker container rm -f my-nginx
```

