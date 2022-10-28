---
title: "Go ツアーを起動して Go 言語の基本を勉強する"
url: "p/dkpsvz3/"
date: "2017-08-31"
lastmod: "2022-04-10"
tags: ["Go"]
description: "Go には、Go ツアーという Go 言語勉強用のツールが用意されています。まずはこれで Go の基本を一通り学んでみるのがよいです。"
aliases: /hugo/go/go-tour.html
---

Go ツアーの Web サイトで学ぶ
----

Go には、Go ツアーという Go 言語勉強用のツールが用意されています。まずはこれで Go の基本を一通り学んでみるのがよいです。

{{< image src="img-001.png" >}}

下記の Web サイトでは、ブラウザ上で Go 言語のコードを記述、実行しながら Go 言語の基本を学んでいくことができます。

- [A Tour of Go (https://go.dev/tour/)](https://go.dev/tour/)


ローカルで Go ツアーを起動する方法
----

A Tour of Go は、次のようにローカルの Web サーバーとして立ち上げることができます。
サーバーが立ち上がると、自動的に Web ブラウザが開きます。

```console
$ go run golang.org/x/website/tour@latest
```

次のようにして、__`tour`__ コマンドとしてインストールすることもできます。

```console
$ go install golang.org/x/website/tour@latest
```

デフォルトでは `$HOME/go/bin` 以下に `tour` コマンドがインストールされるので、次のように起動できます。

```
$ ~/go/bin/tour
```

`GOBIN` 環境変数や `GOPATH` 環境変数を設定している場合は、`go install` によるコマンドのインストール先が変わるので注意してください。

- 参考: [go install のコマンドインストール先にパスを通す (GOBIN, GOPATH/bin)](/p/s258beh)

