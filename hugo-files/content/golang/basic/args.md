---
title: "Golang でコマンドライン引数を扱う (os.Args, flags)"
linkTitle: "コマンドライン引数を扱う (os.Args, flags)"
url: "p/o8vbo2f/"
date: "2022-09-09"
tags: ["Golang"]
---

Golang でコマンドライン引数を扱うには、標準パッケージの `os` や `flags` を使用します。

- `os.Args` ... 単純に文字列配列で参照する場合
- [flags パッケージ](https://pkg.go.dev/flag) ... `-key value` スタイルのオプションを扱う場合


文字列配列でコマンドライン引数を参照する (os.Args)
----

__`os.Args`__ という文字列配列を参照すると、コマンド実行時に指定したファイル名と、コマンドライン引数を取得することができます。
次のサンプルコードでは、この配列のサイズと、各要素を出力しています。

{{< code lang="go" title="main.go" >}}
package main

import (
	"fmt"
	"os"
)

func main() {
	fmt.Printf("Length = %d\n", len(os.Args))
	for i, arg := range os.Args {
		fmt.Printf("[%d] %s\n", i, arg)
	}
}
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ go build -o hello.exe  # ビルド
$ hello AAA BBB "CCC DDD"
Length = 4
[0] hello
[1] AAA
[2] BBB
[3] CCC DDD
{{< /code >}}

上記の `"CCC DDD"` のように、コマンドライン引数をダブルクォート (`"`) で囲むことで、1 つの引数として扱われます。

ちなみに、`os.Args[0]` に入る実行ファイルの名前は、コマンドラインで指定した通りの文字列になることに注意してください。
次のように呼び出し方を変えると、`os.Args[0]` の内容も変わります。

```
$ hello              → hello
$ ./hello            → ./hello
$ hello.exe          → hello.exe
$ .\myapp\hello.exe  → .\myapp\hello.exe
```

実行ファイルの名前をフルパスで取得したいときは、[os.Executable 関数](https://pkg.go.dev/os#Executable) を使用できます。


オプション引数をパースする（flag パッケージ）
----

### flag パッケージの基本

Golang 標準の [flag パッケージ](https://pkg.go.dev/flag) を使用すると、次のような形式のオプション引数を扱うことができます。

```
-x
-x 100
-aaa
--aaa
-aaa 100
-aaa=100
--aaa 100
--aaa=100
```

`flag` パッケージでは、次のような関数でオプションの定義と、値の取得を同時に行います。
オプションの型 (`string`, `int`, `bool`) によって関数が分かれています。

```go
// 指定された値を戻り値で取得するバージョン
func String(name string, value string, usage string) *string
func Int(name string, value int, usage string) *int
func Float64(name string, value float64, usage string) *float64
func Bool(name string, value bool, usage string) *bool

// 第 1 引数に渡した変数に値を取得するバージョン
func StringVar(p *string, name string, value string, usage string)
func IntVar(p *int, name string, value int, usage string)
func Float64Var(p *float64, name string, value float64, usage string)
func BoolVar(p *bool, name string, value bool, usage string)
```

例えば、`user` という名前の文字列型のオプションを定義するには次のようにします。
2 番目のパラメーター (`value`) はデフォルト値で、3 番目のパラメーター (`usage`) はヘルプテキストです。

```go
func main() {
	user := flag.String("user", "UNKNOWN", "user name") // *string
	flag.Parse()
	fmt.Println(*user)
}
```

ユーザーが入力した値は、関数の戻り値として取得できるのですが、実際に入力値がパースされるのは __`flag.Parse`__ 関数を呼び出したときだということに注意してください。
`flag.String` 関数の戻り値がポインタ型になっているのは、このように後から値が設定されるためです。
`flag.Parse` 関数を呼び出す前に `flag.Bool` 関数の戻り値を参照すると、必ずデフォルト値（上記例では `UNKNOWN`）になってしまうので注意してください。

下記は、アプリケーションを `myapp` という名前でビルドして、いろいろな形でオプション指定した場合の結果です。

```console
$ myapp
UNKNOWN

$ myapp --user maku
maku

$ myapp --user=hoge
hoge
```

`flag` パッケージを使うと、ヘルプメッセージを表示するための __`-h`__ や __`--help`__ オプションも暗黙的に定義されます。

```console
$ myapp -h
Usage of myapp:
  -user string
        user name (default "UNKNOWN")
```

各オプションの使い方を間違えた場合は、そのオプションに関するヘルプメッセージを表示してくれます。

```console
$ myapp --user
flag needs an argument: -user
Usage of myapp:
  -user string
        user name (default "UNKNOWN")
```

`int` 型のオプション (`flag.Int`) と `bool` 型のオプション (`flag.Bool`) は、次のような入力値が正しい値だと判断されます。

- `flag.Int()` ... 1234, 0664, 0x1234, -1
- `flag.Bool()` ... 1, 0, t, f, T, F, true, false, TRUE, FALSE, True, False

### 変数にオプション値を取得するバージョン

末尾に `Var` の付く次のようなバージョンの関数を使うと、第 1 引数で指定した変数にオプション値を取得することができます。

```go
func StringVar(p *string, name string, value string, usage string)
func IntVar(p *int, name string, value int, usage string)
func Float64Var(p *float64, name string, value float64, usage string)
func BoolVar(p *bool, name string, value bool, usage string)
```

次の例では、`options` 構造体の各フィールドに様々な型のオプション値を格納しています。

{{< code lang="go" title="main.go" >}}
package main

import (
	"flag"
	"fmt"
)

var options struct {
	isDark bool
	targetEnv string
	retryCount int
}

func parseOptions() {
	flag.BoolVar(&options.isDark, "dark", false, "enables dark mode")
	flag.StringVar(&options.targetEnv, "env", "development", "target environment")
	flag.IntVar(&options.retryCount, "retry", 0, "how many times to retry")
}

func main() {
	parseOptions()
	fmt.Printf("%+v\n", options)
}
{{< /code >}}

{{< code lang="console" title="実行例" >}}
$ myapp
{isDark:false targetEnv:development retryCount:0}

$ myapp --dark --env production --retry 100
{isDark:true targetEnv:production retryCount:100}

$ myapp -h
Usage of myapp:
  -dark
        enables dark mode
  -env string
        target environment (default "development")
  -retry int
        how many times to retry
{{< /code >}}

### オプション名のエイリアス

オプション名としてロングバージョンとショートバージョンを設定したい場合（例: `--port` と `-p`）、`flag.IntVar` や `flag.StringVar` 関数を同じ変数に対して繰り返し適用します。

```go
var options struct {
	port int
}

func parseOptions() {
	const (
		defaultPort = 1234
		portUsage = "port number"
	)
	flag.IntVar(&options.port, "port", defaultPort, portUsage)
	flag.IntVar(&options.port, "p", defaultPort, portUsage+" (shorthand)")
	flag.Parse()
}
```

ヘルプ表示は次のように分かれて表示されてしまうみたいですが、そこは目をつぶります。

```console
$ myapp --help
Usage of myapp:
  -p int
        port number (shorthand) (default 1234)
  -port int
        port number (default 1234)
```

