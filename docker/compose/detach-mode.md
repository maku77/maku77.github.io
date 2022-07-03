---
title: "Docker Compose をデタッチモードで起動する (docker compose up -d)"
url: "p/94m3izf/"
permalink: "p/94m3izf/"
date: "2022-07-03"
tags: ["Docker"]
---

`docker compose up` コマンドを実行すると、現在のターミナル（端末）に入出力がアタッチされて Docker Compose のログが出力されるようになるため、その端末では別の入力作業ができなくなってしまいます（<kbd>Ctrl-D</kbd> で停止できます）。

```console
$ docker compose up
[+] Running 2/0
 ⠿ Network myproject_default  Created
 ⠿ Container myproject-web-1  Created
Attaching to myproject-web-1
...（省略）...
myproject-web-1  | 2022/07/01 06:50:46 [notice] 1#1: start worker processes
myproject-web-1  | 2022/07/01 06:50:46 [notice] 1#1: start worker process 31
myproject-web-1  | 2022/07/01 06:50:46 [notice] 1#1: start worker process 32
...（省略）...
```

`docker compose up` コマンドを実行するときに、__`-d (--detach)`__ オプションを指定すると、Docker Compose がデタッチモードで起動（バックグラウンド実行）されるため、その端末を引き続き使用できるようになります。

```console
$ docker compose up -d
[+] Running 1/1
 ⠿ Container myproject-web-1  Started

$ （次のコマンドを入力可能）
```

表示されなくなったログは、__`docker compose logs`__ コマンドで確認できます。

```console
$ docker compose logs
...（省略）...
myproject-web-1  | 2022/07/01 06:50:46 [notice] 1#1: start worker processes
myproject-web-1  | 2022/07/01 06:50:46 [notice] 1#1: start worker process 31
myproject-web-1  | 2022/07/01 06:50:46 [notice] 1#1: start worker process 32
...（省略）...
```

Docker Compose で動作しているコンテナの一覧は __`docker compose ps`__ で確認できます。

```console
$ docker compose ps
NAME             COMMAND                 SERVICE  STATUS   PORTS
myproject-web-1  "/docker-entrypoint.…"  web      running  0.0.0.0:80->80/tcp
```

デタッチモードで起動したコンテナをすべて停止／起動するには、__`docker compose stop`__ / __`start`__ コマンドを使います。

```console
$ docker compose stop   # コンテナを停止
$ docker compose start  # 停止中のコンテナを起動
```

コンテナやネットワークをすべて削除するには、__`docker compose down`__ コマンドを実行します。
こちらは停止ではなくて削除なので気をつけてください。

```console
$ docker compose down            # コンテナとネットワークの削除
$ docker compose down --volumes  # ボリュームまで削除する場合
```

なお、上記のようなコマンドは、対象の `docker-compose.yml` があるディレクトリ内（プロジェクト内）で実行する必要があります。
別のディレクトリから実行したいときは、`-p` オプションでプロジェクト名（デフォルトではディレクトリ名）を指定します。

```console
$ docker compose -p myproject ps
```

