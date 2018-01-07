---
title: Docker のインストール
date: "2015-03-09"
---

Docker をインストールするときは、基本的には、下記のマニュアルに従ってインストールすれば OK です。

- https://docs.docker.com/installation/

ここでは Ubuntu 14.04 でのインストール方法をシンプルにまとめておきます。

方法 (1) Ubuntu 14.04 に APT で手軽にインストール（多少バージョンが古いことがあるけど超簡単）
====

1. Docker のインストール
---

```
$ sudo apt-get update
$ sudo apt-get install docker.io
```

2. TAB キーによる補完機能を有効にする
---

```
（bash を再起動するか、下記を実行）
$ source /etc/bash_completion.d/docker.io
```

3. 動作確認
---

```
$ docker.io -v
Docker version 1.0.1, build 990021a
```

方法 (2) Ubuntu 14.04 に最新の Docker をインストール
====

1. apt が https に対応しているかを確認
---

```
$ ls /usr/lib/apt/methods
bzip2  cdrom  copy  file  ftp  gpgv  gzip  http  https  lzma  mirror  rred  rsh  ssh  xz
```

2. 上記に https が表示されなかったら apt-transport-https をインストール
---

```
$ sudo apt-get update
$ sudo apt-get install apt-transport-https
```

3. Docker リポジトリのキーを登録
---

```
$ sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 36A1D7869245C8950F966E92D8576A8BA88D21E9
```

4. Docker のインストール
---

```
$ curl -sSL https://get.docker.com/ubuntu/ | sudo sh
```

