---
title: "リポジトリ名のついていない Docker イメージをすべて削除する"
url: "p/oziyhxf/"
date: "2015-04-02"
lastmod: "2022-06-12"
tags: ["Docker"]
aliases: /docker/remove-unnamed-images.html
---

普通に Docker コンテナ上で編集作業などを行っていると、Docker イメージのキャッシュがどんどん増えていってしまいます。

```console
$ docker image ls
REPOSITORY              TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
<none>                  <none>              8a15692b92ea        About an hour ago   1.077 GB
<none>                  <none>              0bcfd9025fab        About an hour ago   1.077 GB
<none>                  <none>              4a0a98efbd7e        About an hour ago   802.8 MB
...
```

`docker image ls` コマンドの出力で、REPOSITORY 名が __`<none>`__ になっているイメージをすべて削除するには、例えば以下のようにします。

```console
$ sudo docker rmi $(docker images | awk '/^<none>/ {print $3}')
```

- 参考: [Docker のコンテナイメージを削除する (`docker image rm/prune`)](/p/8fjnqtw/)

