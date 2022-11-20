---
title: "一般ユーザから docker コマンドを実行できるようにする（docker グループへの登録）"
url: "p/an7o5m3/"
date: "2015-03-10"
tags: ["Docker"]
aliases: /docker/run-docker-without-root.html
---

`docker` コマンドは、内部で socket を使って通信を行っており、一般ユーザからコマンドを実行すると、permission denied で怒られることがあります。

```console
$ docker version
Client version: 1.0.1
Client API version: 1.12
Go version (client): go1.2.1
Git commit (client): 990021a
2015/03/10 22:50:19 Get http:///var/run/docker.sock/v1.12/version: dial unix //var/run/docker.sock: permission denied
```

上記の出力を見ると、`var/run/docker.sock` ファイルへのアクセス権限がないことが分かります。
このような場合は、次のいずれかの方法で `docker` コマンドを実行できるようになります。

- `sudo` を付けて実行する
- ユーザーを `docker` グループに追加する


sudo を付けて実行する方法
----

`sudo` をつけてスーパーユーザー権限で `docker` コマンドを実行する方法です。

```console
$ sudo docker version
Client version: 1.0.1
Client API version: 1.12
Go version (client): go1.2.1
Git commit (client): 990021a
Server version: 1.0.1
Server API version: 1.12
Go version (server): go1.2.1
Git commit (server): 990021a
```


ユーザーを docker グループに追加する方法
----

Docker が使用している socket ファイル (`/var/run/docker.sock`) は、__docker グループ__ からのアクセスを許可しています。

```console
$ ls -l /var/run/docker.sock
srw-rw---- 1 root docker 0  3月 10 00:02 /var/run/docker.sock
```

必要なユーザを、`docker` グループに追加することで、自由に `docker` コマンドを実行できるようになります。

```console
$ sudo gpasswd -a maku docker    # ユーザ maku を docker グループに追加
$ sudo gpasswd -d maku docker    # ユーザ maku を docker グループから外す
```

上記の設定を反映させるには、一度 Ubuntu などからログアウトする必要があります。

