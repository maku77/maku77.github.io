---
title: "Go のコマンドインストール先にパスを通す (GOBIN, GOPATH/bin)"
url: "p/s258beh/"
permalink: "p/s258beh/"
date: "2022-04-10"
tags: ["Go"]
redirect_from:
  - /hugo/go/gobin
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

でも、なんだかんだ言って次のような標準的な構成にしておくとトラブルが少なくてよいかも。

```sh
export GOPATH=$HOME/go
export GOBIN=$GOPATH/bin
export PATH=$PATH:$GOBIN
```

Windows なら、コントロールパネルの「環境変数を編集」から次のような感じで設定します（`%USERPROFILE%` 以外の変数を参照すると、うまく展開できなくて問題が出たりするので注意）。

```
GOPATH → %USERPROFILE%\go
GOBIN  → %USERPROFILE%\go\bin
PATH   → %USERPROFILE%\go\bin （を PATH に追加）
```

上記のようにパスを通しておけば、`go install` でインストールしたコマンドを、どこからでも実行できるようになります。

```console
$ go install github.com/maku77/go-hello
$ go-hello
Hello, world!
```

