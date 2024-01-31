---
title: "Docker"
url: "/docker/"

categoryName: "まくまく Docker ノート"
categoryUrl: "/docker/"
categoryIcon: _index.svg
---

Docker をはじめる（インストールと設定） <!-- start -->
----
* [Docker をインストールする (Docker Desktop / Docker Engine)](/p/96o6n4j/)
* [Docker のプロキシ設定](/p/w69cfim/)
* [一般ユーザから docker コマンドを実行できるようにする（docker グループへの登録）](/p/an7o5m3/)


Docker の基本的な使い方 <!-- basic -->
----

* [Docker でイメージを取得してコンテナを起動する (`docker image pull`, `docker container run`)](/p/y2biqx6/)
* [Docker コンテナで起動したシェルに接続する (`docker container run/start/exec/attach`)](/p/y8cfimp/)
* [Docker コンテナをデーモンとして動作させる (`docker container run -d`, `docker container logs`)](/p/dmpsvz3/)
* [Docker Compose の基本（docker コマンドを使いやすくする）](/p/qm5k2hx/)

Docker コマンドによる操作
----

* [Docker チートシート／Docker コマンドの一覧](/p/p4o6m3i/)

### イメージ (docker image)
* [Docker のコンテナイメージを作成する (`docker image build`, `docker container commit`)](/p/5j4k3iy/)
* [Docker のコンテナイメージを削除する (`docker image rm/prune`)](/p/8fjnqtw/)
  * [（応用）リポジトリ名のついていない Docker イメージをすべて削除する (`docker image rm`)](/p/oziyhxf/)
* [Docker のコンテナイメージを Docker Hub に登録する (`docker image push`)](/p/rwco2dp/)
* [Docker のマルチステージビルドで軽量のアプリ実行用イメージを作成する](/p/z3n4hye/)
* [Docker Hub のイメージを検索する (`docker search`)](/p/4ohyhxe/)

### コンテナ (docker container)
* [Docker コンテナ内で動作しているプロセスの一覧を表示する (`docker container top`)](/p/s3m4jyg/)
* [Docker コンテナの詳細情報を表示する (`docker container inspect`)](/p/47hs3ck/)
* [Docker コンテナ側の公開ポートがホスト側のどのポートにマッピングされているか調べる (`docker container port`)](/p/ow258be/)
* [すべての Docker コンテナを停止／削除する (`docker container stop/rm`)](/p/6ehmpsv/)
* [Docker コンテナとホスト PC の間でファイルをコピーする (`docker container cp`)](/p/cqar8o5/)

### ネットワーク (docker network)
* [Docker のネットワークについて理解する (none, host, bridge)](/p/7fjnqtw/)
* [Docker コンテナからホスト側のサーバーにアクセスする (`host.docker.internal`)](/p/najs2ah/)
* [Docker Compose でリバースプロキシを立てて別の Docker Compose 内のコンテナに接続する](/p/5f2j2hz/)
* [WSL2 内の Docker サーバーに LAN 内の別 PC からアクセスする (netsh)](/p/w6cjckc/)

### マウント（ボリューム、バインドマウント、tmpfs マウント）
* [Docker のマウント機能でファイルを永続化する（ボリュームマウント、バインドマウント、tmpfsマウント）](/p/hxhzgxf/)

### Docker Compose
* [Docker Compose の基本 (`docker compose up`)](/p/qm5k2hx/)
* [Docker Compose をデタッチモードで起動する (`docker compose up -d`)](/p/94m3izf/)
* [Docker Compose で環境変数を使用する (`env_file`, `environment`)](/p/8r3cmu5/)

### Docker コンテキスト (docker context)
* [Docker コンテキストを切り替えてリモートホスト上で Docker コマンドを実行する](/p/qatbs9p/)


その他 <!-- misc -->
----

* [サーバー再起動時に Docker コンテナを自動起動する](/p/rh8qm2n/)
* トラブルシューティング
  * [Docker で apt-get install するときに TERM 系のエラーが出る](/p/3i2iygw/)
  * [Dockerfile からの apt-get install で zlib1g-dev:i386 がインストールできないとき](/p/6g3j2iz/)
* [（旧）Android ビルド環境を構築するための Dockerfile](/p/ao8p7n4/)

