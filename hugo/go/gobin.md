---
title: "Go のコマンドインストール先にパスを通す (GOBIN, GOPATH/bin)"
url: "/p/empsvz3"
date: "2022-04-10"
tags: ["Go"]
---

`go install` コマンドで Go の実行可能コマンドをインストールすると、次のようなディレクトリにコマンドがインストールされます。

1. __`GOBIN`__ 環境変数が設定されていれば、__`$GOBIN`__ が指すディレクトリ
2. __`GOPATH`__ 環境変数が設定されていれば、__`$GOPATH/bin`__ が指すディレクトリ
3. __`$HOME/go/bin`__ ディレクトリ

これらのディレクトリに OS のパスを通しておくと、どのディレクトリからでもインストールしたコマンドを実行できるようになります。
Linux や macOS であれば、`~/.zlogin` や `~/.bash_profile` で次のような感じで設定できます。
ここでは、`GOBIN` 環境変数を設定して、コマンドのインストール先を `~/bin` に設定しています。

#### ~/.zlogin

```sh
export GOBIN=~/bin
export PATH=$PATH:$GOBIN
```

例えば、次のような感じでコマンドを GitHub からインストールして、どこからでも実行できるようになります。

```console
$ go install github.com/maku77/go-hello
$ go-hello
Hello, world!
```

