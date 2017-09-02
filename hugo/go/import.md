---
title: "パッケージを参照する (import)"
created: 2017-08-31
description: "Go 言語のプログラムの中から、他のパッケージを参照する色々な方法を紹介します。"
---


インポートの基本
----

Go 言語のプログラムの中から、他のパッケージをインポートするには `import` を使用します。

~~~ go
import "fmt"
import "os"
import "strings"
~~~

括弧を使って次の様にまとめることもできます。

~~~ go
import (
	"fmt"
	"os"
	"strings"
)
~~~

ワークスペースに配置されたパッケージのインポート
----

標準パッケージに加え、[GOPATH で設定したワークスペース](./workspace.html)の `src/` ディレクトリに配置したパッケージもインポートすることができます。
例えば、下記のような `Add` 関数を公開する `mylib/mymath` パッケージを作成してみましょう（Go 言語では、大文字で始まる関数が、別パッケージから参照できるように公開されます）。

#### $GOPATH/src/mylib/mymath/mymath.go

~~~ go
package mymath

func Add(a, b int) int {
	return a + b
}
~~~

このようにパッケージが正しく `$GOPATH/src` 配下に作成されていれば、どの Go プログラムからも、次のようにしてインポートして使用することができます。

#### sample.go

~~~ go
package main

import (
	"fmt"
	"mylib/mymath"
)

func main() {
	x := mymath.Add(100, 200)
	fmt.Printf("100+200=%d\n", x)
}
~~~

#### ビルド＆実行

~~~
$ go run sample.go
100+200=300
~~~

