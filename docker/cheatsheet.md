---
title: "Docker チートシート"
date: "2015-04-02"
---

Registry / Repository / Image / Tag の概念
----

![cheatsheet.png](cheatsheet.png)

* DockerHub などのレジストリ上では、複数のリポジトリが管理されている。
* Top-level リポジトリでは、ubuntu や devian といった有名どころなものが管理されている。
* Top-level リポジトリ以外に、ユーザリポジトリがあり、こちらは `<user>/` というプレフィックスが付く。
* 各リポジトリ内には複数のイメージがあり、イメージ ID やタグで指定できる。
* 1 つのイメージに対しては唯一の ID が付けられるが、タグは複数付けられていることがある。


docker image（イメージ関連のコマンド）
----

| コマンド | 旧コマンド | 説明 |
| ---- | ---- | ---- |
| `docker image build`   | `docker build`   | Dockerfile からイメージをビルドする<br>Build an image from a Dockerfile |
| `docker image history` | `docker history` | イメージのレイヤ構造を表示する<br>Show the history of an image |
| `docker image import`  | `docker import`  | tar ファイルからファイルシステムイメージを作成する<br>Import the contents from a tarball to create a filesystem image |
| `docker image inspect` | `docker inspect` | イメージの詳細を表示する<br>Display detailed information on one or more images |
| `docker image load`    | `docker load`    | tar ファイルや標準入力からイメージをロードする<br>Load an image from a tar archive or STDIN |
| `docker image ls`      | `docker images`  | イメージの一覧を表示する<br>List images |
| `docker image prune`   | なし             | 使用していないイメージを削除する<br>Remove unused images |
| `docker image pull`    | `docker pull`    | レジストリからイメージを取得する<br>Pull an image or a repository from a registry |
| `docker image push`    | `docker push`    | イメージをリポジトリにプッシュする<br>Push an image or a repository to a registry |
| `docker image rm`      | `docker rmi`     | イメージを削除する<br>Remove one or more images |
| `docker image save`    | `docker save`    | tar ファイルにイメージを保存する<br>Save one or more images to a tar archive (streamed to STDOUT by default) |
| `docker image tag`     | `docker tag`     | イメージにタグを付ける<br>Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE |


docker container（コンテナ関連のコマンド）
----

| コマンド | 旧コマンド | 説明 |
| ---- | ---- | --- |
| `docker container attach`  | `docker attach` | 動作中のコンテナに標準入力や標準出力を接続する<br>Attach local standard input, output, and error streams to a running container |
| `docker container commit`  | `docker commit` | Create a new image from a container's changes |
| `docker container cp`      | `docker cp` | Copy files/folders between a container and the local filesystem |
| `docker container create`  | `docker create` | Create a new container |
| `docker container diff`    | `docker diff` | Inspect changes to files or directories on a container's filesystem |
| `docker container exec`    | `docker exec` | 動作中のコンテナで新しいプロセスを起動する<br>Run a command in a running container |
| `docker container export`  | `docker export` | Export a container's filesystem as a tar archive |
| `docker container inspect` | `docker inspect` | Display detailed information on one or more containers |
| `docker container kill`    | `docker kill` | Kill one or more running containers |
| `docker container logs`    | `docker logs` | Fetch the logs of a container |
| `docker container ls`<br>`docker container list`<br>`docker container pm` | `docker ps` | コンテナの一覧を表示する<br>List containers |
| `docker container pause`   | `docker pause` | Pause all processes within one or more containers |
| `docker container port`    | `docker port` | List port mappings or a specific mapping for the container |
| `docker container prune`   | なし | Remove all stopped containers |
| `docker container rename`  | `docker rename` | Rename a container |
| `docker container restart` | `docker restart` | Restart one or more containers |
| `docker container rm`      | `docker rm` | Remove one or more containers |
| `docker container run`     | `docker run` | 「イメージの取得」「コンテナの作成」「コンテナの起動」を連続して行う<br>Run a command in a new container |
| `docker container start`   | `docker start` | 作成済みのコンテナを起動する<br>Start one or more stopped containers |
| `docker container stats`   | `docker stats` | Display a live stream of container(s) resource usage statistics |
| `docker container stop`    | `docker stop` | Stop one or more running containers |
| `docker container top`     | `docker top` | Display the running processes of a container |
| `docker container unpause` | `docker unpause` | Unpause all processes within one or more containers |
| `docker container update`  | `docker update` | Update configuration of one or more containers |
| `docker container wait`    | `docker wait` | Block until one or more containers stop, then print their exit codes |


docker volume（ボリューム関連のコマンド）
----

| コマンド | 説明 |
| ---- | --- |
| `docker volume create`  | ボリュームを作成する<br>Create a volume |
| `docker volume inspect` | ボリュームの詳細情報を表示する<br>Display detailed information on one or more volumes |
| `docker volume ls`      | ボリュームの一覧を表示する<br>List volumes |
| `docker volume prune`   | 使用していないボリュームを削除する<br>Remove all unused local volumes |
| `docker volume rm`      | ボリュームを削除する<br>Remove one or more volumes |

その他のコマンド
----

| コマンド | 説明 |
| ---- | --- |
| `docker builder prune` | ビルドキャッシュを削除する<br>Remove build cache |
| `docker search` | Docker Hub 上のイメージを検索する<br>Search the Docker Hub for images |


Docker ファイルの命令
----

| 命令 | 内容 |
| ---- | ---- |
| `# <comment>` | `#` で始まる行はコメント |
| `ADD` | ファイルをイメージ上にコピーする（URL 指定でのダウンロードや、tar ファイルの展開を行う） |
| `CMD` | `ENTRYPOINT` が未指定、かつ `docker container run` で何も指定されなかったときに実行するコマンド |
| `COPY` | ファイルをイメージ上にコピーする |
| `ENTRYPOINT` | `docker container run` 時に実行するコマンド |
| `ENV <name> <value>` | 環境変数を設定する<br>例: `ENV http_proxy http://proxy.example.com:8888/`<br>例: `ENV PATH $PATH:/foo/bar` |
| `EXPOSE <port>` | ポートを公開する |
| `FROM <repo>:<tag>` | ベースイメージを指定する |
| `LABEL` | イメージのメタデータとしてラベルを追加する |
| `MAINTAINER <name> "<email>"` | イメージのメタデータとしてメンテナ名を追加する（非推奨） |
| `RUN <command>` | コマンドを実行する（`/bin/sh -c` による実行） |
| `RUN ["<arg1>", "<arg2>", "<arg3>"]` | コマンドを実行する（シェルを使わない） |
| `USER` | `RUN`、`CMD`、`ENTRYPOINT` のコマンドを実行するユーザー |
| `VOLUME` | 共有可能ボリュームをマウントする |
| `WORKDIR <path>` | 作業ディレクトリを設定する。`RUN`、`CMD`、`ENTRYPOINT`、`ADD`、`COPY` 実行時のベースディレクトリとなる。`RUN cd` では次の命令に引き継がれないので注意 |

