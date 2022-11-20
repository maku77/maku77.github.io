---
title: "Docker をインストールする (Docker Desktop / Docker Engine)"
url: "p/96o6n4j/"
date: "2015-03-09"
lastmod: "2022-01-25"
tags: ["Docker"]
aliases: /docker/install/
---

Docker 実行環境のインストール
----

Docker の実行環境（`docker` コマンド）をインストールするには、下記の公式マニュアルの手順に従ってください。
基本的に、Windows や macOS では __Docker Desktop__、Linux では __Docker Engine__ をインストールすることになります。

- [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/)
  - 例: [Windows の場合 (Docker Desktop)](https://docs.docker.com/desktop/windows/install/)
  - 例: [macOS の場合 (Docker Desktop)](https://docs.docker.com/desktop/mac/install/)
  - 例: [Ubuntu の場合 (Docker Engine)](https://docs.docker.com/engine/install/ubuntu/)
  - 例: [Debian の場合 (Docker Engine)](https://docs.docker.com/engine/install/debian/)

例えば、Linux 環境では次のような感じで Docker Engine をインストールできます（詳細は上記の公式ページを参照してください）。

```console
# インストール
$ curl -sSL get.docker.com -o install.sh
$ sh install.sh

# Docker サービスを systemd で自動起動する設定
$ systemctl enable docker

# Docker サービスを直ちに起動
$ systemctl start docker
```

インストール手順を実行後、`docker` コマンドが使えるようになっていれば準備 OK です。

```console
$ docker system info
```


Docker Desktop について
----

Docker コンテナを動作させるためのコアになる Docker Engine は、Linux OS 上で動作させることを前提としているため、そのままでは Windows や macOS 上で動かすことができません。
Windows や macOS で Docker を動かすには、まず、Linux VM（仮想環境）を立ち上げ、その上で Docker Engine を動作させる、といった複雑な手順が必要です。

とはいえ、それでは手間がかかるので、Windows や macOS には [Docker Desktop](https://docs.docker.com/desktop/) というソフトウェアが提供されており、これをインストールすると、__Linux VM + Docker の実行環境を一度に整えられます__（Windows では内部的に WSL2（Windows 標準の仮想環境）が利用されます）。
さらに、Docker Desktop は次のような GUI フロントエンドを備えており、ここから Docker イメージの管理、コンテナの管理、ボリュームの管理などを行えるため非常に便利です。

{{< image w="800" src="img-001.png" title="Docker Desktop の画面" >}}

Docker Desktop をインストールすると、Docker Engine だけでなく、下記がまとめて使えるようになります。

- Docker Engine
- Docker CLI client
- Docker Build/BuildKit
- Docker Compose
- Docker Content Trust
- Kubernetes
- Docker Scan
- Credential Helper

Docker Desktop は Linux VM として、BusyBox ベースの軽量な Alpine Linux を使用しているため、高速に起動することができます。
下手に自分で Linux VM 環境を用意するより快適に使用できます。

2022年2月からは、__Docker Desktop を巨大企業（250人以上 or 1,000万ドル以上の収益）で使用する場合は有償__ になるため注意してください。
個人利用では引き続き無料（Personal プラン）で使用できます。
