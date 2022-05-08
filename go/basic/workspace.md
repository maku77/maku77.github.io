---
title: "Go のワークスペースの考え方 (GOPATH)"
url: "p/u48bfim"
permalink: "p/u48bfim"
date: "2017-08-31"
tags: ["Go"]
description: "Go 言語を用いた開発では、他のプログラミング言語とは異なり、１つのワークスペース内ですべてのコードを管理するという慣習があります。"
redirect_from:
  - /hugo/go/workspace
---

ワークスペースの構成
----

Go のワークスペースは下記のように、`bin`、`pkg`、`src` という３つのディレクトリで構成することが決められています。
このルールに従ってファイルを配置することで、`Makefile` のようなビルド設定ファイルを用意しなくても、ビルドコマンド (`go build`) は構造化されたディレクトリ構成のプロジェクトを認識できるようになっています。

~~~
~/gowork/
  +-- bin/  # 作成したバイナリファイル（go install したときの保存先）
  +-- pkg/  # 依存パッケージのオブジェクトファイル（.a ファイルなど）
  +-- src/  # ソースコード（実行対象のファイルやインポートするファイルを配置）
~~~

Go による開発では、ひとつのシステム内（PC 内）に、ひとつのワークスペースだけを用意して、その中ですべての作業を完結させる方法が主流となっています。
自分で作成しているパッケージも、`go get` コマンドでインターネット上から取得したパッケージも、PC で唯一のワークスペース内の `src/` ディレクトリの中に格納します。
Go のプログラム内から外部パッケージをインポートするときは、まず `pkg/` ディレクトリ内のバイナリ版が検索され、次に `src/` ディレクトリが参照されるようになっています。

他の言語と比べると、特殊な管理方法（ほとんどルールベースで動作する）なので最初は戸惑うかもしれませんが、一度分かってしまえばそれほど複雑な仕組みではありません（こういった１ワークスペースでの開発が、Google 社内の開発ルールに適していたのかもしれませんね）。


GOPATH によるワークスペースの指定
----

Go 言語用のワークスペースとして扱うディレクトリ（上記の例では `gowork` ディレクトリ）は、__`GOPATH`__ 環境変数であらかじめ指定しておく必要があります。
Go 1.8 以上では、`GOPATH` を明示的に指定しなかった場合のデフォルトパスは __`$HOME/go`__ となります（Windows の場合は __`%USERPROFILE%\go`__ です）。

```console
$ export GOPATH=$HOME/gowork
```

Go が認識している `GOPATH` の値は、`go env GOPATH` コマンドで確認することができます。

```console
$ go env GOPATH
/Users/maku/gowork
```

`go build` や `go install` などのコマンドは、`$GOPATH/src` 以下のコードに対して実行されるため、どのディレクトリからでも実行することができます。
例えば、任意のディレクトリから、

```console
$ go install foo/bar/hello
```

と実行すると、`$GOPATH/src/foo/bar/hello` ディレクトリ以下の .go ファイル群がビルドされて、その中で `package main` と記述された .go ファイルが実行ファイルの形になって `$GOPATH/bin` にインストールされます。

#### $GOPATH/src/foo/bar/hello/hello.go

```go
package main

import "fmt"

func main() {
	fmt.Println("Hello")
}
```

ビルドしてできた実行ファイルは `$GOPATH/bin` にインストールされるので、そこに `PATH` を通しておくと便利かもしれません。

```console
$ export PATH=$PATH:$GOPATH/bin
$ hello
Hello
```

ちなみに、`go build foo/bar/hello` や `go install foo/bar/hello` のようにパッケージ名を指定してビルドした場合、パッケージ名をもとに実行ファイルが生成される（この場合は `hello` というディレクトリ名）ので、ソースコードのファイル名は何でも構いません（ここでは `hello.go` としたけど、`main.go` というファイル名にしてもよい）。
ただし、下記のように .go ファイル名を直接指定してビルドする場合は、ファイル名に基いてカレントディレクトリに実行ファイルが生成されます。

```console
$ go build hoge/moge/sample.go
$ ls
hoge/   sample*
```

