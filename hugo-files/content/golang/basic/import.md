---
title: "パッケージの作成とインポート (import)"
url: "p/t269cgj/"
date: "2017-08-31"
lastmod: "2022-04-10"
tags: ["Go"]
description: "Go 言語でパッケージを作る方法と、他のパッケージを参照する色々な方法を紹介します。"
aliases: /hugo/go/import.html
---

Go 言語でパッケージを作る方法と、他のパッケージを参照する色々な方法を紹介します。

パッケージとモジュール
----

### パッケージ

Go 言語のパッケージは、1 つのディレクトリ内にまとめられたソースコードの集まりです。
下記のディレクトリ構成では、`mymodule` というモジュール（後述）に、`pkg1` と `pkg2` という名前の 2 つのパッケージが含まれています。

```
- mymodule/
  - pkg1/
    - auth.go (package pkg1)
    - user.go (package pkg1)
  - pkg2/
    - hoge.go (package pkg2)
```

パッケージ内のファイルの分割粒度は自由で、あるコードがパッケージ内のどの Go ファイルに記述されているかで違いはありません。
パッケージのインポート時にはパッケージ名しか指定しないので、パッケージの利用者は、パッケージを構成する各々の Go ファイルの存在を意識する必要はありません。
パッケージの実装者は、パッケージ内のファイルを都合のいいように分割できます。

Go 言語のソースコードは、必ず 1 つのパッケージに所属している必要があり、ソースコードの先頭の __`package`__ ディレクティブでパッケージ名（＝親ディレクトリ名）を指定します。

```go
package pkg1
```

特に、プログラムのエントリポイントとなる `main` 関数は、`main` パッケージに存在している必要があり、典型的にはモジュールルートを `main` パッケージとして扱います。

```go
package main

function main() {
	// ...
}
```

### モジュール

Go 1.11 以降は __モジュール__ という考え方が導入され、Go 1.10 以前の [GOPATH によるコードの一元管理](/p/u48bfim/) からの移行が進みました。
簡単に言うと、プロジェクト単位でディレクトリを自由な場所に置けるようになりました（以前はすべてのプロジェクトを `$GOPATH` 以下に置かなければいけませんでした）。
モジュールは単一あるいは複数のパッケージを集めたもので、GitHub などで管理するときののリリース単位として用いられます。

```
モジュール ◇── パッケージ
```

モジュールのルートディレクトリには __`go.mod`__、__`go.sum`__ というファイルが置かれ、依存するパッケージのバージョンやハッシュ情報などが管理されます。
公開されるモジュール名は世界で一意になっている必要があり、一般的に GitHub のリポジトリで管理されるため、次のような名前が使われます。

```
github.com/<username>/<modulename>
```

例えば、`github.com/ojisancancode/golibs` として公開されているモジュールの、`pkg1` パッケージを利用したい場合、次のようにインポートできます。

```go
import "github.com/ojisancancode/golibs/pkg1"
```

上記のように、モジュール内の単一のパッケージをインポートする場合でも、モジュール全体がダウンロードされるため、初回のビルドには少し時間がかかります。


インポートの使い方
----

### 基本

Go 言語のプログラムの中から、他のパッケージをインポートするには __`import`__ を使用します。
GitHub で公開されているパッケージを直接インポートすることもできます。

```go
import "fmt"
import "os"
import "strings"
import "github.com/ユーザー名/リポジトリ名/パッケージ名"
```

括弧を使って次の様にまとめることもできます。

```go
import (
	"fmt"
	"os"
	"strings"
	"github.com/ユーザー名/リポジトリ名/パッケージ名"
)
```

パッケージで定義されている関数を参照するときは、__`パッケージ名.関数名`__ という形で呼び出します。
大文字で始まる関数のみ参照できます。
次の例では、`cmp` パッケージの `Diff` 関数を呼び出しています。

```go
package main

import "fmt"
import "github.com/google/go-cmp/cmp"

func main() {
	fmt.Println(cmp.Diff("Hello World", "Hello Go"))
}
```

### オプション

パッケージをインポートするときに、パッケージ名の前にオプションを指定することで、別名を付けたり、パッケージ名を省略してアクセスできるようになります。

```go
package main

import (
	. "fmt"                             // パッケージ名を省略して関数名だけで呼び出せるようにする
	sample "github.com/ojisancancode/gosample" // パッケージに別名を付けて参照
	_ "math/rand"                       // 参照していなくてもコンパイルエラーにしない
)

func main() {
	Println("Good morning")
	sample.Hello("Maku")
}
```

パッケージ名の前に __`.`__ オプションを付けると、パッケージ名を省略してアクセスできるようになります。
上記の例では、`fmt.Println` の呼び出しを、`Println` と記述できるようにしています。

2 つ目の例では、`gosample` パッケージに `sample` という別名を付けてアクセスできるように指定しています。
Go 言語では、パッケージ名を階層構造で表現しない（潔い！）ので、パッケージ名のコンフリクトが発生しやすくなっています。
同じ名前のパッケージをインポートするときは、この別名の仕組みを利用して解決しましょう。


同一パッケージ内の関数を参照する
----

同じパッケージ内（つまり、同じディレクトリ内）の Go ファイルで定義されている関数は、互いに参照することができます。
下記は、プログラムのエントリポイントとなる `main.go` ファイルです。

{{< code lang="go" title="main.go" >}}
package main

func main(){
	hello("Maku")
}
{{< /code >}}

`hello` 関数は、同じパッケージ（同じディレクトリ内）の別のファイルで定義しています。

{{< code lang="go" title="greet.go" >}}
package main

import "fmt"

func hello(name string) {
	fmt.Printf("Hello, %s\n", name)
}
{{< /code >}}

実行するときは、これらを一緒にビルドする必要があります。

```console
$ go run *.go
Hello, Maku
```

ビルドして、実行ファイル `myapp` を作るときも同様です。

```console
$ go build -o myapp *.go
$ ./myapp
Hello, Maku
```

`go.mod` ファイルを生成してモジュール対応モードにしておけば、`go build` は引数なしで実行できます。

```console
$ go mod init myapp
$ go build
$ ./myapp
Hello, Maku
```


別のパッケージの関数を参照する
----

ある関数を別のパッケージから参照できるようにするには、関数名を大文字で始める必要があります（例: `Hello`）。
ここでは、次のようなディレクトリ構成の `myapp` モジュールを作って、`main.go`（main パッケージ）から `mymath/mymath.go`（mymath パッケージ）で定義した関数を参照したいとします。

```
myapp/
  go.mod
  main.go （main パッケージ）
  mymath/ （mymath パッケージ）
    mymath.go
```

まず、モジュールのルートディレクトリ (`myapp`) で、__`go mod init`__ コマンドを実行して、`go.mod` ファイルを生成します。
GitHub で管理することを想定しているのであれば、モジュールパス名はリポジトリ名に合わせて __`github.com/<user>/<repo>`__ のようにします。
これにより、モジュールパス名が世界で一意になるとともに、別のモジュールから GitHub 経由でインポートできるようになります。
ローカルでテスト開発するだけのアプリであれば、とりあえずモジュールパス名は `myapp` のように適当に付けちゃって構いません。

```console
$ cd ~/gitwork/myapp
$ go mod init github.com/ojisancancode/myapp  # GitHub で管理するならリポジトリ名を指定
$ go mod init myapp                    # ローカルでのテスト用ならこれでも OK
```

`mymath` パッケージでは、簡単な足し算を行う `Add` 関数を提供することにします。
パッケージ外部から参照できるようにするには、関数名を大文字で始める必要があります。

{{< code lang="go" title="mymath/mymath.go" >}}
package mymath

func Add(a, b int) int {
	return a + b
}
{{< /code >}}

この `mymath.Add` 関数を、`main` パッケージの `main` 関数から参照するには次のようにします。

{{< code lang="go" title="main.go" >}}
package main

import "fmt"
import "github.com/ojisancancode/myapp/mymath"

func main() {
	fmt.Println(mymath.Add(100, 200))
}
{{< /code >}}

`main` パッケージ（の `main` 関数）は次のように実行できます。

```console
$ go run .
300
```

ビルドして実行ファイル `myapp` を生成することもできます。

```console
$ go build
$ ./myapp
300
```

Go 言語のパッケージは、`"./mymath"` のような __相対パスではインポートできない__ ことに注意してください。
必ず `"github.com/ojisancancode/myapp/mymath"` のようにモジュール名を含むパッケージパス全体（絶対パス）を指定する必要があります（`go mod init` でモジュールパス名を `myapp` のように簡略化した場合は、"myapp/mymath" のようにインポートします）。
昔は同一モジュール内のパッケージであれば相対パスでインポートできたのですが、現在は外部モジュールのパッケージと同様に絶対パスによる指定に統一されています。

