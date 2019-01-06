---
title: "すべての Docker コンテナを削除する"
date: "2015-03-12"
---

Docker コンテナ上のシェルで何度も作業を行っていると、`docker ps -a` コマンド表示される Docker コンテナの履歴がどんどん溜まってきてしまいます。
すべてのコンテナを削除するには下記のようにします（`docker ps -a -q` ですべてのコンテナ ID を取得できることを利用しています）。


すべての Docker コンテナを停止する
----
```bash
$ sudo docker stop $(sudo docker ps -a -q)
```

すべての Docker コンテナを削除する
----
```bash
$ sudo docker rm $(sudo docker ps -a -q)
```

