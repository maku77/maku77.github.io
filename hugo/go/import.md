---
title: "パッケージを参照する (import)"
date: "2017-08-31"
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

標準パッケージに加え、[GOPATH で設定したワークスペース](./workspace.html) の `src/` ディレクトリに配置したパッケージもインポートすることができます（デフォルトでは、`~/go/src/` です）。
例えば、下記のような `Add` 関数を公開する `mylib/mymath` パッケージを作成してみましょう。
Go 言語では、大文字で始まる関数が、別パッケージから参照できるように公開されます。

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


インポート時のオプション指定
----

パッケージをインポートするときに、パッケージ名の前にオプションを指定することで、別名を付けたり、パッケージ名を省略してアクセスできるようになります。

#### sample.go

~~~ go
package main

import (
	. "fmt"                             // パッケージ名を省略して呼び出せるようにする
	sample "github.com/maku77/gosample" // 別名を付けて参照
	_ "math/rand"                       // 参照していなくてもコンパイルエラーにしない
)

func main() {
	Println("Good morning")
	sample.Hello("Maku")
}
~~~

パッケージ名の前に `.` オプションを付けると、パッケージ名を省略してアクセスできるようになります。上記の例では、`fmt.Println` の呼び出しを、`Println` と記述できるようにしています。

２つ目の例では、`gosample` パッケージに `sample` という別名を付けてアクセスできるように指定しています。
Go 言語では、パッケージ名を階層構造で表現しない（潔い！）ので、パッケージ名のコンフリクトが発生しやすくなっています。
同じ名前のパッケージをインポートするときは、この別名の仕組みを利用して解決しましょう。

#### 実行結果

~~~
$ go run sample.go
Good morning
Hello, Maku!
~~~

