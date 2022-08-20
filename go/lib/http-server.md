---
title: "HTTP サーバーを作成する (net/http)"
url: "p/goruwy4/"
permalink: "p/goruwy4/"
date: "2022-08-20"
tags: ["Golang"]
---

Hugo 標準ライブラリの [net/http パッケージ](https://pkg.go.dev/net/http) ([src](https://cs.opensource.google/go/go/+/master:src/net/http/)) は、HTTP クライアント／サーバーを作成するためのパッケージです。
ここでは、この `net/http` パッケージを使って、簡単な Web サーバーを起動してみます。


最小限の HTTP サーバーを作る
----

### 全体のコード

#### main.go

```go
package main

import (
	"io"
	"log"
	"net/http"
)

func main() {
	// ハンドラー関数を定義する
	h1 := func(w http.ResponseWriter, _ *http.Request) {
		io.WriteString(w, "Hello-1\n")
	}
	h2 := func(w http.ResponseWriter, _ *http.Request) {
		io.WriteString(w, "Hello-2\n")
	}

	// パスとハンドラー関数を結びつける
	http.HandleFunc("/", h1)
	http.HandleFunc("/foo", h2)

	// Web サーバーを起動する
	if err := http.ListenAndServe(":8080", nil); err != nil {
		log.Fatal(err)
	}
}
```

このプログラムを起動すると、簡単な Web サーバーが起動します。
Web ブラウザなどで、`http://localhost:8080`にアクセスすると、`Hello-1` というレスポンスを返します。
`http://localhost:8080/foo/` にアクセスすると、`Hello-2` というレスポンスを返します。

サーバーを停止するときは、<kbd>Ctrl + C</kbd> を入力します。

### コードの説明

Web サーバーにアクセスしたときに呼び出されるハンドラー関数は、次のようなシグネチャの関数として実装します。
`http.ResponseWrite` と `http.Request` を受け取る関数です。

```
type HandlerFunc func(ResponseWriter, *Request)
```

今回は次のようなハンドラー関数を定義しています。
レスポンスの内容（テキスト）を `http.ResponseWriter` に書き込むときには、`io.WriteString` や `fmt.Printf` を使用できます。

```go
h1 := func(w http.ResponseWriter, _ *http.Request) {
	io.WriteString(w, "Hello-1\n")
}
h2 := func(w http.ResponseWriter, _ *http.Request) {
	io.WriteString(w, "Hello-2\n")
}
```

[http.HandleFunc 関数](https://pkg.go.dev/net/http#HandleFunc) を使って、ハンドラー関数を結びつけます。

```go
http.HandleFunc("/", h1)
http.HandleFunc("/foo", h2)
```

あとは、[http.ListenAndServe 関数](https://pkg.go.dev/net/http#ListenAndServe) で特定のアドレス（＋ポート）で待ち受けを開始します。
ここではポート番号 8080 だけを指定しているため、`localhost:8080` で待ち受けることになります。

```go
http.ListenAndServe(":8080", nil)
```

