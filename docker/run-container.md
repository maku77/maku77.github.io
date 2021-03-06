---
title: "Docker コンテナを起動してシェル (bash) を扱う"
date: "2015-03-12"
---

Ubuntu 14.04 の Docker イメージ上で bash を動作させ、そのコマンドを実行した端末上でその bash を扱うには、下記のように `docker run` コマンドを実行します。

```
$ sudo docker run -it ubuntu:14.04 /bin/bash
```

オプションで `-i` と `-t` を指定しないと、一瞬で Docker コンテナが終了してしまうので、必ず指定する必要があります。
オプションの `-i` と `-t` は、具体的には下記のような意味を持っていますが、bash などのシェルを使う時のおまじないと考えておけばよいでしょう。

* `-i`: 標準入力を有効にしたままにする（ようするに現在の端末上からキーボード入力できるようにする）
* `-t`: 起動するコマンドに対して TTY 端末を割り当てる（ようするに現在の端末上に bash のプロンプトを表示する）

