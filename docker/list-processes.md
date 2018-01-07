---
title: Docker コンテナ内で動作しているプロセスの一覧を表示する
date: "2015-03-11"
---

`docker top` コマンドで、指定した Docker コンテナ内で現在動作しているプロセスを確認することができます。

```
$ sudo docker top container_name
PID                 USER                COMMAND
854                 root                python app.py
855                 root                sleep 1
```

