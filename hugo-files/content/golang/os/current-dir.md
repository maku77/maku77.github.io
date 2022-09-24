---
title: "Golang でカレントディレクトリ、実行ファイルのパスを取得する (os.Getwd, os.Executable)"
linkTitle: "カレントディレクトリ、実行ファイルのパスを取得する (os.Getwd, os.Executable)"
url: "p/egs2bjt/"
date: "2022-09-23"
tags: ["Go"]
---

カレントディレクトリのパス (os.Getwd)
----

Golang でカレントディレクトリのパスを取得するには、[os.Getwd 関数](https://pkg.go.dev/os#Getwd) を使用します。
戻り値は、カレントディレクトリを示す __絶対パス（フルパス）__ の文字列になります。
カレントディレクトリは、`go run` コマンドなどを実行したときのディレクトリのことであり、`.go` ファイルのあるパスではないことに注意してください。

{{< code lang="go" title="main.go" hl_lines="9" >}}
package main

import (
	"fmt"
	"os"
)

func main() {
	dir, err := os.Getwd()
	if err != nil {
		panic(err)
	}
	fmt.Println(dir)
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cd /Users/maku/hello
$ go run main.go
/Users/maku/hello

$ cd /Users/maku
$ go run hello/main.go
/Users/maku
{{< /code >}}


実行中のファイルのパス (os.Executable)
----

実行中のファイル (executable) のフルパスを取得するには、[os.Executable 関数](https://pkg.go.dev/os#Executable) を使用します。

{{< code lang="go" title="main.go" hl_lines="9" >}}
package main

import (
	"fmt"
	"os"
)

func main() {
	path, err := os.Executable()
	if err != nil {
		panic(err)
	}
	fmt.Println(path)
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cd ~/hello
$ go build -o myexe main.go
$ ./myexe
/Users/maku/hello/myexe
{{< /code >}}

`go run` コマンドで `.go` ファイルを指定して実行した場合は、内部的にビルドされて生成された実行ファイルのパスが表示されます。

```console
$ go run main.go
/var/folders/g7/08g8xg4x7_lb3k_tgpwwdfmr0000gn/T/go-build9032354/b001/exe/main
```

応用例として、実行ファイルのあるディレクトリのパスを取得したい場合は、__`filepath.Dir`__ 関数を組み合わせて使用します。

{{< code lang="go" hl_lines="14" >}}
package main

import (
	"fmt"
	"os"
	"path/filepath"
)

func main() {
	exePath, err := os.Executable()
	if err != nil {
		panic(err)
	}
	exeDir := filepath.Dir(exePath)

	fmt.Println(exePath) //=> /Users/maku/hello/myexe
	fmt.Println(exeDir)  //=> /Users/maku/hello
}
{{< /code >}}

実行ファイルと同じディレクトリにあるリソースファイルのパスを構築したければ、以下のように __`filepath.Join`__ でパスを繋ぎます。

```go
dataPath := filepath.Join(exeDir, "data.txt") //=> /Users/maku/hello/data.txt
```

とはいえ、前述の通り、`os.Executable` が返すパスは、`go run main.go` のように `.go` ファイルを指定して実行した場合はおそらく想定外のものになるので、リソースファイルのパスはコマンドライン引数や環境変数で指定するのが無難です。

- 参考: [Golang でコマンドライン引数を扱う (os.Args, flags)](/p/o8vbo2f/)
- 参考: [Golang で環境変数を扱う (os.Getenv, os.LookupEnv)](/p/4ox6dmu/)

