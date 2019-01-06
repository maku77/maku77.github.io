---
title: "Docker イメージのダウンロードと Hello World"
date: "2015-03-15"
---

Docker イメージのダウンロード
====

Docker でコンテナを作成するためには、ベースとなるイメージが必要です。
多くのイメージが **DockerHub** に用意されているので、通常はここからベースとなるイメージを取得します。
ここでは、**Ubuntu 14.04** のイメージをダウンロードします。

`docker pull` コマンドに、ダウンロードしたいイメージ名と、バージョンを表すタグ（最新なら `latest`）を指定してダウンロードします。
タグを指定しないと、全タグのイメージを取得してしまうので注意してください。

```
$ sudo docker pull ubunutu:14.04
Pulling repository ubuntu
2d24f826cb16: Download complete
511136ea3c5a: Download complete
fa4fd76b09ce: Download complete
1c8294cc4160: Download complete
117ee323aaa9: Download complete
```

イメージがダウンロードできているかを確認します。

```
$ sudo docker images
REPOSITORY   TAG       IMAGE ID       CREATED      VIRTUAL SIZE
ubuntu       14.04     2d24f826cb16   2weeks ago   188.3 MB
```


Docker イメージの削除
====

Docker イメージを削除したくなったときは、下記のように `docker rmi` コマンドで削除できます。
**「イメージ名:タグ名」**で指定する代わりに、`docker images` で表示される**「イメージの ID」**を指定することもできます。

```
$ sudo docker rmi ubuntu:14.04
```


Docker イメージの実行
====

`docker run` コマンドを使うと、指定した Docker イメージ上で任意のコマンドを実行することができます。
例えば、Ubuntu 14.04 の Docker イメージ上で、`echo` コマンドを実行するには、以下のようにします。

```
$ sudo docker run ubuntu:14.04 echo Hello World!
Hello World!
```

`docker run` コマンドにより、**「Docker コンテナ」**が起動され、その中で `echo` が実行されます。
この例の場合、`echo` コマンドの実行が終わり次第、すぐに「Docker コンテナ」は停止します。
`docker run` コマンドで指定したイメージがまだローカルに存在しない場合は、そのイメージを公開レジストリである Docker Hub からダウンロードしてくれます。
なので、実は `docker pull` であらかじめイメージを取得しておかなくても、上記の `docker run` を直接実行することができます。

