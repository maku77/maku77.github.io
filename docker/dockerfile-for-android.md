---
title: "Android ビルド環境を構築するための Dockerfile"
date: "2015-04-02"
---

下記の Dockerfile を使うと、Ubuntu 14.04 あるいは、Ubuntu 12.04 をベースにした Android ビルド用の Docker イメージを作成することができます。

#### Dockerfile (Ubuntu 14.04)

```shell
FROM ubuntu:14.04

# Set the proxies if needed.
# ENV http_proxy http://proxy.example.com:10080/
# ENV https_proxy http://proxy.example.com:10080/

# Suppress errors on interactive installer
ENV DEBIAN_FRONTEND noninteractive

# To install i386 packages such as zlib1g-dev:i386
RUN dpkg --add-architecture i386

# The following is based on http://source.android.com/source/initializing.html
# For installing openjdk-7-jdk, --no-install-recommends option has to be added
# not to struggle with 'colord' and 'sgml-base' errors.
RUN apt-get -qq update
RUN apt-get install -y openjdk-7-jdk --no-install-recommends
RUN apt-get install -y bison g++-multilib git gperf libxml2-utils make zlib1g-dev:i386 zip --no-install-recommends

# For the repo command
RUN apt-get install -y curl python --no-install-recommends

=== Dockerfile (Ubuntu 12.04) ===
FROM ubuntu:12.04

# Set the proxies if needed.
# ENV http_proxy http://proxy.examle.com:10080/
# ENV https_proxy http://proxy.example.com:10080/

# Suppress errors on interactive installer
ENV DEBIAN_FRONTEND noninteractive

# The following is based on http://source.android.com/source/initializing.html
RUN apt-get -qq update
RUN apt-get install -y openjdk-7-jdk --no-install-recommends
RUN apt-get install -y git gnupg flex bison gperf build-essential zip curl libc6-dev libncurses5-dev:i386 x11proto-core-dev libx11-dev:i386 libreadline6-dev:i386 libgl1-mesa-glx:i386 libgl1-mesa-dev g++-multilib mingw32 tofrodos python-markdown libxml2-utils xsltproc zlib1g-dev:i386 --no-install-recommends
```

やっていることは、ほぼ下記に記述されている通りです。

- [http://source.android.com/source/initializing.html](http://source.android.com/source/initializing.html)

ただ、いくつかポイントがあり、下記のような設定を行っています。

* インストール時の readline 系エラーの抑制 (`ENV DEBIAN_FRONTEND noninteractive`)
* 無駄なパッケージのインストールによるエラー抑制 (`apt-get install -y openjdk-7-jdk --no-install-recommends`)
* Ubuntu 14.04 の場合は、i386 系パッケージのインストール設定 (`RUN dpkg --add-architecture i386`)
* Ubuntu 14.04 の場合は、repo コマンドのダウンロード＆実行のために curl、python (2.7) を追加インストール

実際に Docker イメージを作成するには、この `Dockerfile` がカレントディレクトリにある状態で、下記のように実行します。

```
$ sudo docker build android_ubuntu14 ./
```

これで、android_ubuntu14 というイメージが作成されるので、あとは、Docker コンテナとして起動できます。

```
$ sudo docker run -it android_ubuntu14 /bin/bash
```

この Docker イメージは、あくまでビルド環境の構築までしか行っていないため、実際にビルドをする前には、`repo` を使ったソースコードのダウンロードなどを行う必要があります。

- [http://source.android.com/source/downloading.html](http://source.android.com/source/downloading.html)

上記の Android 公式サイトでは、ホームディレクトリの `~/bin` に `repo` コマンドをインストールしていますが、Docker コンテナ上での実行時には、適宜 `/opt/bin` などに読み替えるとよいでしょう。

