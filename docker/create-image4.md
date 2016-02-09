---
title: Docker イメージを作成する - GitHub 上の Dockerfile からイメージを作成する
created: 2015-04-02
---

`docker build` コマンドでは、`Dockerfile` のあるディレクトリを指定するときに、GitHub のリポジトリを指定することができます。

```
$ sudo docker build -t maku77/sample:v1 git@github.com:maku77/sample
```

