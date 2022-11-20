---
title: "Docker コンテナ内で動作しているプロセスの一覧を表示する (docker container top)"
url: "p/s3m4jyg/"
date: "2015-03-11"
tags: ["Docker"]
aliases: /docker/list-processes
---

__`docker container top <コンテナ名>`__ コマンドを実行すると、指定した Docker コンテナ内で現在動作しているプロセスを確認することができます（互換性維持のため、`docker top` コマンドも残されています）。

```console
$ docker container top my-container
PID                 USER                COMMAND
854                 root                python app.py
855                 root                sleep 1
```

末尾に `ps` コマンドのオプションを指定して、出力内容をカスタマイズできます。
次の例では、`-o pid,command` と指定することで、出力するフィールドを `PID` と `COMMAND` のみにしています。

```console
$ docker container top my-container -o pid,command
PID                 COMMAND
2007                /usr/bin/qemu-x86_64 /usr/sbin/mysqld mysqld
```

