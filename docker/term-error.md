---
title: Docker で apt-get install するときに TERM 系のエラーが出る
date: "2015-04-02"
---

`docker build` などで `apt-get install` を実行中に、下記のようなエラーが出ることがあります。

```
debconf: unable to initialize frontend: Dialog
debconf: (TERM is not set, so the dialog frontend is not usable.)
debconf: falling back to frontend: Readline
debconf: unable to initialize frontend: Readline
```

このような場合、`Dockerfile` で下記のように環境変数を設定してから `apt-get` するとエラーが出なくなります。

```
ENV DEBIAN_FRONTEND noninteractive
```

あるいは、`apt-get` 実行時に下記のようにプレフィックスで設定することもできます。

```
DEBIAN_FRONTEND=noninteractive apt-get install -y ...
```

`DEBIAN_FRONTEND` というのは、Debian Installer が使うフロントエンド (UI) を指定するもので、`noninteractive` にすることで、ユーザの入力を受け付けないインストールが可能になります。

- 参考: http://www.debian.org/releases/sarge/s390/ch05s02.html.ja

