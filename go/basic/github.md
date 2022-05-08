---
title: "（旧）GitHub 上のパッケージを参照する／GitHub にパッケージを公開する"
url: "p/xs3ahpw"
permalink: "p/xs3ahpw"
date: "2017-08-31"
tags: ["Go"]
description: "Go 言語には GitHub 上に公開されたパッケージを簡単にインポートして使用する仕組みが組み込まれています。"
redirect_from:
  - /hugo/go/github
---

主要なプログラミング言語には、インターネット上のセントラルリポジトリでパッケージを配布する仕組みが提供されていることが多いのですが（Ruby の [RubyGems](https://rubygems.org/) など）、Go 言語では、Git リポジトリ上（主に GitHub）に公開されたソースコードを直接取得 (`go get`) してインポートする方法を採用しています。

GitHub 上のパッケージをインポートする
----

`go get` コマンドで、GitHub リポジトリのパスを下記のように指定すると、GitHub 上で公開されている Go パッケージ（ここでは `github.com/maku77/gosample`）のコードを、`$GOPATH/src` ディレクトリに取得することができます（`$GOPATH` については[こちらを参照](./workspace.html)）。

#### github.com/maku77/gosample パッケージを取得する

~~~
$ go get github.com/maku77/gosample
~~~

取得したパッケージは、標準パッケージと同じようにインポートして使用することができます。

#### sample.go

~~~ go
package main

import "github.com/maku77/gosample"

func main() {
	gosample.Hello("Maku")
}
~~~

#### ビルド＆実行

~~~
$ go run sample.run
Hello, Maku!
~~~

`go get` コマンドの面白いところは、GitHub 上で公開されているリポジトリ名そのものではなく、そのサブディレクトリで公開されている Go パッケージ名を指定してコードを取得できるところです（`git clone` コマンドではこのようなことはできませんね）。
例えば、代表的な Pretty Print 系のパッケージである `github.com/davecgh/go-spew/spew` パッケージは、GitHub 上でのリポジトリ名は `https://github.com/davecgh/go-spew.git` ですが、そのサブディレクトリとして公開されているパッケージ名を指定して取得することができます。

~~~
$ go get github.com/davecgh/go-spew/spew
~~~

実際には、`go-spew.git` リポジトリ以下のファイルがすべて取得されますが、その配下のパッケージを使用するユーザが特に意識する必要はありません。

下記は `spew` パッケージの使用例です。

#### sample.go

~~~ go
package main

import (
	"github.com/davecgh/go-spew/spew"
)

type Book struct {
	Title  string
	Author string
}

func main() {
	a := Book{
		Title:  "Golang ABC",
		Author: "Maku",
	}
	spew.Dump(a)
}
~~~

#### ビルド＆実行

~~~
$ go run sample.go
(main.Book) {
 Title: (string) (len=10) "Golang ABC",
 Author: (string) (len=4) "Maku"
}
~~~


GitHub にパッケージを公開する
----

自作のパッケージを GitHub に公開したいときは、単純に、Go で作成したソースコードを自分のアカウントで作成した GitHub リポジトリにコミットするだけで OK です。

まずは、リポジトリ名を決めて GitHub 上で新規リポジトリを作成しましょう。
ここでは、`https://github.com/maku77/gosample` というリポジトリを作成し、その中のコードを作成していくことにします。

GitHub 上で新規リポジトリを作成したら、ローカルでの作業用に `git clone` で取得します。
取得先は、`$GOPATH/src/github.com/＜ユーザ名＞/＜リポジトリ名＞/` というディレクトリにします（`go get` コマンドの取得先に合わせる）。

~~~
$ git clone https://github.com/maku77/gosample.git $GOPATH/src/github.com/maku77/gosample
~~~

次に、簡単な `Hello` 関数を実装してみます。

#### $GOPATH/src/github.com/maku77/gosample/gosample.go

~~~ go
package gosample

import "fmt"

func Hello(name string) {
	fmt.Printf("Hello, %s!\n", name)
}
~~~

ビルドできるか確認します。

~~~
$ go build github.com/maku77/gosample
~~~

何もエラーがでなければ成功です。

GitHub にコミットする前に、ちゃんと動作するかテストプログラムを実行して確かめておきましょう。

#### sample.go

~~~ go
package main

import "github.com/maku77/gosample"

func main() {
	gosample.Hello("Maku")
}
~~~

#### ビルド＆実行

~~~
$ go run sample.go
Hello, Maku!
~~~

正しく動作していることが確認できたら、コミットして GitHub へ公開します。

~~~
$ git add gosample.go
$ git commit -m "Add Hello function"
$ git push
~~~

これで、他のユーザは `go get github.com/maku77/gosample` と実行するだけで、GitHub からパッケージ取得できるようになります。

