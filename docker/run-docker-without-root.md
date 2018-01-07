---
title: 一般ユーザから docker コマンドを実行できるようにする
date: "2015-03-10"
---

`docker` コマンドは、内部で socket を使って通信を行っており、一般ユーザからコマンドを実行すると、permission denied で怒られることがあります。

```
$ docker version
Client version: 1.0.1
Client API version: 1.12
Go version (client): go1.2.1
Git commit (client): 990021a
2015/03/10 22:50:19 Get http:///var/run/docker.sock/v1.12/version: dial unix //var/run/docker.sock: permission denied
```

このようなケースでは、`sudo` をつけて実行すれば、問題なく実行できます。

```
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

あるいは、この socket ファイルは、**docker グループ**からのアクセスを許可しているので、

```
$ ls -l /var/run/docker.sock
srw-rw---- 1 root docker 0  3月 10 00:02 /var/run/docker.sock
```

必要なユーザを、`docker` グループにすることで、自由に `docker` コマンドを実行できるようにすることもできます。

```
$ sudo gpasswd -a maku docker    # ユーザ maku を docker グループに追加
$ sudo gpasswd -d maku docker    # ユーザ maku を docker グループから外す
```

上記の設定を反映させるには、一度 Ubuntu などからログアウトする必要があります。

