---
title: Docker コンテナの詳細情報を表示する
created: 2015-03-11
---

`docker inspect` コマンドを使用すると、Docker コンテナの詳細情報を表示することができます。
その Docker コンテナがどのイメージをもとに作成されているのか、環境変数の設定はどうなっているのかなどを JSON 形式で出力してくれます。

```
$ sudo docker inspect container_name
[{
    "Args": [
        "-c",
        "while true; do echo Hello; sleep 1; done"
    ],
    "Config": {
        "AttachStderr": false,
        "AttachStdin": false,
        "AttachStdout": false,
...
```

