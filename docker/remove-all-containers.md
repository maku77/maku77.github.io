---
title: "すべての Docker コンテナを停止／削除する (docker container stop/rm)"
url: "p/6ehmpsv/"
permalink: "p/6ehmpsv/"
date: "2015-03-12"
lastmod: "2022-06-12"
tags: ["Docker"]
redirect_from:
  - /docker/remove-all-containers
---

Docker コンテナ上のシェルで何度も作業を行っていると、`docker container ps -a` コマンドで表示される Docker コンテナの履歴がどんどん溜まってきてしまいます。
すべてのコンテナを停止 or 削除するには下記のようにします（`docker container ps -a -q` ですべてのコンテナ ID を取得できることを利用しています）。

#### すべての Docker コンテナを停止する

```console
$ docker container stop $(docker container ps -a -q)
```

#### すべての Docker コンテナを削除する

```console
$ docker container rm $(docker container ps -a -q)
```

動作中のコンテナも含めてすべて削除したいときは、`docker contaier rm` に __`-f`__ オプションを付けてください。
ただし、これはとても危険なので注意して実行してください。

