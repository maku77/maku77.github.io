---
title: "すべての Docker コンテナを停止／削除する (docker container stop/rm)"
url: "p/6ehmpsv/"
date: "2015-03-12"
lastmod: "2022-06-12"
tags: ["Docker"]
aliases: /docker/remove-all-containers
---

全コンテナを停止する
----

すべての Docker コンテナをまとめて停止するには次のようにします。
これは、`docker container ps -a -q` コマンドですべてのコンテナ ID を取得できることを利用しているため、Linux や macOS 上でしか実行できません（Windows のコマンドプロンプトでは実行できません）。

{{< code lang="console" title="すべての Docker コンテナを停止する" >}}
$ docker container stop $(docker container ps -a -q)
{{< /code >}}


全コンテナを削除する
----

同様に、次のようにすればすべての Docker コンテナを削除することができます。

{{< code lang="console" title="すべての Docker コンテナを削除する" >}}
$ docker container rm $(docker container ps -a -q)
{{< /code >}}

動作中のコンテナも含めてすべて削除したいときは、`docker contaier rm` に __`-f`__ オプションを付けて実行する必要があります。
ただし、これはとても危険なので注意して実行してください。

