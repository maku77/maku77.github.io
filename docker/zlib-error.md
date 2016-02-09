---
title: Dockerfile からの apt-get install で zlib1g-dev:i386 がインストールできないとき
created: 2015-04-02
---

ubuntu:14.04 などのベースイメージに対して、`zlib1g-dev:i386` などの i386 系のパッケージをインストールしようとしたときに下記のようなエラーがでることがあります。

```
E: Unable to locate package zlib1g-dev
```

これを防ぐには、`Dockerfile` で下記のように実行するようにしておきます。

```
RUN dpkg --add-architecture i386
```

