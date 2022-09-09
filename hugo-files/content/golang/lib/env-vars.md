---
title: "Golang で環境変数を扱う (os.Getenv, os.LookupEnv)"
linkTitle: "環境変数を扱う (os.Getenv, os.LookupEnv)"
url: "p/4ox6dmu/"
date: "2022-08-13"
tags: ["Golang"]
---

Go 言語で OS の環境変数を扱うには、標準ライブラリの [os パッケージ](https://pkg.go.dev/os) を使用します。


環境変数を参照する (os.Getenv, os.LookupEnv)
----

[os.Getenv 関数](https://pkg.go.dev/os#Getenv) を使うと、特定の環境変数を参照することができます。
指定した名前の環境変数が設定されていない場合は、空文字列 (`""`) を返します。

{{< code lang="go" title="main.go" >}}
package main

import (
	"fmt"
	"os"
)

func main() {
	shell := os.Getenv("SHELL")
	if shell == "" {
		fmt.Println("環境変数 SHELL が設定されていません")
		return
	}
	fmt.Printf("SHELL = %s\n", shell)
}
{{< /code >}}

環境変数の値として、明示的に空文字列が設定されていることを判別したい場合は、`os.Getenv` の代わりに [os.LookupEnv 関数](https://pkg.go.dev/os#LookupEnv) 関数を使用します。
`os.LookupEnv` は、2 番目の `bool` 型戻り値で、環境変数が設定されているかどうかを返します。

```go
val, ok := os.LookupEnv(key)
if !ok {
	fmt.Printf("%s not set\n", key)
} else {
	fmt.Printf("%s=%s\n", key, val)
}
```


文字列リテラルで環境変数を参照する (s.ExpandEnv)
----

環境変数の値を文字列に展開したいことはよくあります。
そのような場合は、[os.ExpandEnv 関数](https://pkg.go.dev/os#ExpandEnv) を使用すると、文字列リテラル内で __`$key`__ や __`${key}`__ という形で環境変数を展開できます。

```go
s := os.ExpandEnv("環境変数 HOME の値は ${HOME} です")
fmt.Println(s)
```


すべての環境変数を参照する (os.Environ)
----

[os.Environ 関数](https://pkg.go.dev/os#Environ) は、すべての環境変数を文字列配列で返します。
ただし、それぞれの要素は `key=value` という文字列になっているので、キーと値を別々に取り出したい場合は、`=` で文字列分割する必要があります。

```go
package main

import (
	"fmt"
	"os"
	"strings"
)

func main() {
	for _, env := range os.Environ() {
		arr := strings.SplitN(env, "=", 2)
		key, val := arr[0], arr[1]
		fmt.Printf("%s => %s\n", key, val)
	}
}
```


（おまけ）具体的な使用例
----

### 例: 環境変数でサーバーのポート番号を指定する

次の例では、何らかのサーバーで使用するポート番号を、環境変数 `PORT` で指定できるようにしています。
環境変版を指定しなかった場合は、デフォルトで 3000 番ポートを使用します。

```go
package main

import (
	"fmt"
	"os"
	"strconv"
)

const defaultPort = 3000

// 使用するポート番号を取得します
func getPort() (int) {
	// 環境変数 PORT の値を参照
	port := os.Getenv("PORT")
	if port == "" {
		return defaultPort
	}

	// 環境変数の値は文字列型なので数値に変換して返す
	portNum, err := strconv.Atoi(port)
	if err != nil {
		fmt.Fprintf(os.Stderr, "PORT '%s' is not valid\n", port)
		return defaultPort
	}
	return portNum
}

func main() {
	port := getPort()
	fmt.Printf("Server is running at port %d\n", port)
}
```

