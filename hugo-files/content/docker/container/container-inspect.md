---
title: "Docker コンテナの詳細情報を表示する (docker container inspect)"
url: "p/47hs3ck/"
date: "2015-03-11"
lastmod: "2022-02-28"
aliases: /docker/inspect-container.html
tags: ["Docker"]
---

__`docker container inspect`__（あるいは `docker inspect`）コマンドを使用すると、Docker コンテナの詳細情報を表示することができます。
その Docker コンテナがどのイメージをもとに作成されているのか、環境変数の設定はどうなっているのかなどを JSON 形式で出力してくれます。

```console
$ docker container inspect my-container
[
    {
        "Id": "ac2dc4e539f1bc7c273beb71d03536e83393d7f673d7ba67994569c75173b2a2",
        "Created": "2022-02-25T05:28:16.4553051Z",
        "Path": "/bin/bash",
        "Args": [],
        "State": {
            "Status": "exited",
            "Running": false,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 0,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2022-02-25T05:29:17.5120196Z",
            "FinishedAt": "2022-02-25T05:29:22.1845301Z"
        },
        "Image": "sha256:54c9d81cbb440897908abdcaa98674db83444636c300170cfd211e40a66f704f",
        ...
    }
]
```

