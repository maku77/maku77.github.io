---
title: "Docker イメージを作成する - docker commit アプローチ"
date: "2015-03-12"
---

`docker commit` コマンドによるアプローチでは、Docker イメージをインタラクティブに作成していくことができます。
Docker コンテナ上でソフトウェアのインストールなどを行い、最後に `docker commit` コマンドを実行することにより、Docker イメージを作成します。

まずは、ベースイメージを指定して Docker コンテナを起動します。

```
$ sudo docker run -it ubuntu:14.04 /bin/bash
```

Docker コンテナ上の bash プロンプトが表示されたら、その中で `apt-get` やファイルの作成などを行い、Docker イメージの構築作業を進めていきます。
下記では、Hello と表示するだけのシェルスクリプト (`/greet`) を作成しています。

```
root@c338a2f4c60e:/# cat > greet
#!/bin/bash
echo Hello
（ここで Ctrl-D で終了）

root@c338a2f4c60e:/# chmod +x greet
root@c338a2f4c60e:/# exit
```

起動元のシェルに戻ってきたら、`docker ps` コマンドで、最新のコンテナ ID（あるいは名前）を確認します。

```
$ sudo docker ps -l
CONTAINER ID    IMAGE           COMMAND      CREATED          STATUS                           PORTS    NAMES
c338a2f4c60e    ubuntu:14.04    /bin/bash    3 minutes ago    Exited (0) About a minute ago             loving_torvalds
```

これで、先ほどインタラクティブに構成した Docker コンテナの ID が `c338`、名前が `loving_torvalds` だということが分かるので、`docker commit` コマンドで、イメージ化を行います。`docker commit` コマンドのフォーマットは下記のようになっています。

```
docker commit -a <作者> -c <コメント> <コンテナ> <リポジトリ名>:<タグ名>
```

`<コンテナ>` には、`docker ps` コマンドで確認したコンテナ ID か、名前を指定します。
リポジトリ名は、Docker Hub の流儀に合わせて、`DockerHubのアカウント名/イメージ名` という形で指定します。
ここでは、ユーザ `maku77` の `sample` という名前のイメージを作成すると仮定します。

```
$ sudo docker commit -a 'Maku <maku77@example.com' -m 'First commit' c338 maku77/sample:v1
8a6608d7d353d966f5cdc044b48b89158943c2dc9fd08b7a4832b43a21b5df41
```

Docker イメージの作成に成功すると、上記のように作成されたイメージの ID が表示されます。
`docker images` コマンドで、実際に新しくイメージが作成されたことを確認できます。

```
$ sudo docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
maku77/sample       v1                  8a6608d7d353        7 minutes ago       188.3 MB
ubuntu              14.04               2103b00b3fdf        43 hours ago        188.3 MB
...
```

この Docker イメージを使って、先ほど作成した `/greet` コマンドを実行してみます。

```
$ sudo docker run maku77/sample:v1 /greet
Hello
```

