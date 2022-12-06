---
title: "Golang で HTTP サーバーを作成する (net/http, rs/cors)"
linkTitle: "HTTP サーバーを作成する (net/http, rs/cors)"
url: "p/goruwy4/"
date: "2022-08-20"
lastmod: "2022-09-12"
tags: ["Go"]
---

Hugo 標準ライブラリの [net/http パッケージ](https://pkg.go.dev/net/http) ([src](https://cs.opensource.google/go/go/+/master:src/net/http/)) は、HTTP クライアント／サーバーを作成するためのパッケージです。
ここでは、この `net/http` パッケージを使って、簡単な Web サーバーを実装してみます。


最小限の HTTP サーバーを作る
----

{{< code lang="go" title="main.go" >}}
package main

import (
	"log"
	"net/http"
)

func main() {
	// ポート番号 8080 で待ち受けを開始
	log.Fatal(http.ListenAndServe(":8080", nil))
}
{{< /code >}}

これがおそらく Golang における最小の HTTP サーバー実装です。
[http.ListenAndServe 関数](https://pkg.go.dev/net/http#ListenAndServe) で、指定したアドレスとポート番号で待ち受けを開始しています。
ここではポート番号 `8080` だけを指定しているため、__`localhost:8080`__ で待ち受けることになります。

{{% note title="log.Fatal で囲んでいるのはなぜ？" %}}
`http.ListenAndServe` 関数はサーバーの起動に失敗すると `error` オブジェクトを返します。
`log.Fatal` 関数で囲んでいるのは、エラーが発生した場合にその内容を出力してから終了するためです。
例えば、サーバーを 2 回続けて起動しようとしたときに、ポート番号が使用中であることを表示してくれます。
逆にサーバーの起動に成功した場合は、`http.ListenAndServe` 関数は戻ってこないので、`log.Fatal` 関数が実行されることはありません。
サーバーを <kbd>Ctrl + C</kbd> で終了したときも、`log.Fatal` 関数は実行されません。
このイディオムは、`net/http` パッケージのドキュメントでも使われています。
{{% /note %}}

`go run main.go` でプログラムを起動してから、Web ブラウザで `http://localhost:8080/` にアクセスするとレスポンスを確認できます。
ただし、まだ何もコンテンツを返していないので、`404 page not found` エラーが返ってきます。
サーバーを停止するときは、<kbd>Ctrl + C</kbd> と入力します。


ハンドラー関数を登録する (http.HandleFunc)
----

Web サーバーがコンテンツを返すようにするには、リクエスト時の URL とレスポンスの内容を対応づける必要があります。
ここでは、次のようにコンテンツを返すようにしてみます。

- `http://localhost:8080/foo/` へのアクセス → `Hello-1` というレスポンスを返す
- `http://localhost:8080/bar/` へのアクセス → `Hello-2` というレスポンスを返す

次のサンプルコードでは、特定のパスにアクセスされたときに呼び出されるハンドラー関数を設定しています。

{{< code lang="go" title="main.go（全体のコード）" >}}
package main

import (
	"io"
	"log"
	"net/http"
)

func main() {
	// ハンドラー関数を定義する
	handler1 := func(w http.ResponseWriter, _ *http.Request) {
		io.WriteString(w, "Hello-1\n")
	}
	handler2 := func(w http.ResponseWriter, _ *http.Request) {
		io.WriteString(w, "Hello-2\n")
	}

	// パスとハンドラー関数を結びつける
	http.HandleFunc("/foo/", handler1)
	http.HandleFunc("/bar/", handler2)

	// Web サーバーを起動する
	log.Fatal(http.ListenAndServe(":8080", nil))
}
{{< /code >}}

HTTP リクエストを処理するハンドラー関数は、次のようなシグネチャの関数として実装します。
__`http.ResponseWriter`__ と __`*http.Request`__ を受け取る関数です。

```go
type HandlerFunc func(ResponseWriter, *Request)
```

今回は次のようなハンドラー関数を定義しています。
レスポンスの内容（テキスト）を `http.ResponseWriter` に書き込むときには、__`io.WriteString`__ や __`fmt.Fprint`__、__`fmt.Fprintf`__ などを使用できます（これは、`http.ResponseWriter` が `io.Writer` インタフェースを実装しており、それを受け取る関数に渡せるようになっているからです）。
2 番目のパラメーター (`*http.Request`) は参照していないので、変数名を `_` にしています。

```go
handler1 := func(w http.ResponseWriter, _ *http.Request) {
	io.WriteString(w, "Hello-1\n")
	// fmt.Fprint(w, "Hello-1\n")
}

handler2 := func(w http.ResponseWriter, _ *http.Request) {
	io.WriteString(w, "Hello-2\n")
	// fmt.Fprint(w, "Hello-2\n")
}
```

HTTP リクエストを受信したときにこれらのハンドラー関数が呼び出されるようにするには、[http.HandleFunc 関数](https://pkg.go.dev/net/http#HandleFunc) を使用します。

```go
http.HandleFunc("/foo/", handler1)
http.HandleFunc("/bar/", handler2)
```


任意のオブジェクトをハンドラーとして登録する (http.Handle)
----

前述の例では、`http.HandleFunc` 関数でハンドラー関数を登録しましたが、代わりに [http.Handle 関数](https://pkg.go.dev/net/http#Handle) を使うと、任意のオブジェクトをハンドラーとして登録することができます。
このオブジェクトは Web サーバーの起動中は破棄されないので、ステート（状態）を持ったハンドラーとして使用できます。
ハンドラーとなる型は、次のようなシグネチャのメソッドを持っている必要があります（`http.Handler` インタフェースとして定義されています）。
実装方法は、前述のハンドラー関数と同様です。

```go
ServeHTTP(w http.ResponseWriter, r *http.Request)
```

次の `countHandler` は、呼び出されるたびに自身の `count` フィールドをインクリメントし、その値をレスポンスとして返します。

{{< code lang="go" title="handler.go" >}}
package main

import (
	"fmt"
	"net/http"
	"sync"
)

// カウンターを持つ HTTP リクエストハンドラー
type countHandler struct {
	mutex sync.Mutex // guards count
	count int
}

func (h *countHandler) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	h.mutex.Lock()
	defer h.mutex.Unlock()
	h.count++
	fmt.Fprintf(w, "Count: %d\n", h.count)
}
{{< /code >}}

このハンドラーを登録するには、次のようにします。

{{< code lang="go" title="main.go" >}}
package main

import (
	"log"
	"net/http"
)

func main() {
	http.Handle("/count", new(countHandler))
	log.Fatal(http.ListenAndServe(":8080", nil))
}
{{< /code >}}

`http://localhost:8080/count/` にアクセスして、次のようなレスポンスが返って来れば成功です。
Count の値はブラウザをリロードするたびに 1 つずつ増えていきます。

```
Count: 1
```


ハンドラーはどこに登録されているのか？
----

これまでのサンプルコードでは、ハンドラーを登録するときに、`http.HandleFunc` 関数や `http.Handle` 関数を使ってきましたが、これらは単なる関数です。
これらに渡したハンドラー関数やハンドラーオブジェクトが、どのように Web サーバーと結び付けられているのか疑問に感じたかもしれません。
その謎は、`net/http` パッケージのコードを覗いてみると分かります。

{{< code lang="go" title="net/http/server.go（抜粋）" >}}
func Handle(pattern string, handler Handler) {
	DefaultServeMux.Handle(pattern, handler)
}

func HandleFunc(pattern string, handler func(ResponseWriter, *Request)) {
	DefaultServeMux.HandleFunc(pattern, handler)
}
{{< /code >}}

どちらの関数も、内部的には __`DefaultServeMux`__ という [http.ServeMux](https://pkg.go.dev/net/http#ServeMux) オブジェクトにハンドラーを登録しています。
`ServeMux` は、ハンドラーを束ねるハンドラーです（Composite パターン）。
そして、次のように第 2 引数を `nil` にして Web サーバーを起動すると、この `DefaultServeMux` がハンドラーとして使われるようになっています。

```go
http.ListenAndServe(":8080", nil)
```

第 2 引数で別のハンドラーオブジェクトを指定してしまうと、`http.HandleFunc` 関数や `http.Handle` 関数で登録したハンドラーは呼び出されなくなってしまうので注意してください。

次のようにすれば、独自の `ServeMux` オブジェクトを作成して、Web サーバーの待ち受けを開始することができます。

```go
func main() {
	// 独自の ServeMux を作成してハンドラーを登録していく
	mux := http.NewServeMux()
	mux.HandleFunc("/foo/", func(w http.ResponseWriter, _ *http.Request) {
		io.WriteString(w, "I am foo")
	})
	mux.HandleFunc("/bar/", func(w http.ResponseWriter, _ *http.Request) {
		io.WriteString(w, "I am bar")
	})

	// Web サーバーの待ち受けを開始
	log.Fatal(http.ListenAndServe(":8080", mux))
}
```


静的ファイルをホスティングする (http.FileServer)
----

ハンドラー実装として、[http.FileServer](https://pkg.go.dev/net/http#FileServer) を使うと、ローカルディレクトリに配置した静的ファイル（HTML ファイルなど）を簡単にホスティングできます。

{{< code lang="go" title="public ディレクトリ内のファイルを公開する" >}}
package main

import (
	"fmt"
	"log"
	"net/http"
)

const port = 8080
const directory = "public"

func main() {
	// 指定したディレクトリをホスティングする
	handler := http.FileServer(http.Dir(directory))
	http.Handle("/", handler)

	// サーバーの待ち受けを開始
	log.Printf("Serving %s on HTTP port: %d\n", directory, port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", port), nil))
}
{{< /code >}}

シンプルに書きたいのであれば、次のように 1 行で書くこともできます。

```go
func main() {
	log.Fatal(http.ListenAndServe(":8080", http.FileServer(http.Dir("."))))
}
```


CORS アクセス対応する (rs.cors)
----

Web ブラウザ上で動作するクライアントサイド JavaScript から、別ドメインの Web サーバーにアクセスしてデータを取得するには、[CORS (Cross-Origin Resource Sharing)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS) 用のレスポンスヘッダーを返す必要があります。
下記は、JSON データを返す簡単な Web サーバー実装です。

{{< code lang="go" title="main.go（CORS 未対応）" >}}
package main

import (
	"log"
	"net/http"
)

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		w.Write([]byte("{\"hello\": \"world\"}"))
	})

	log.Fatal(http.ListenAndServe(":8080", handler))
}
{{< /code >}}

`http://localhost:8080/` にアクセスすると、`{"hello": "world"}` という JSON データが返ってくるはずなのですが、別ドメインの Web サーバーにより配信された JavaScript から次のようにアクセスすると、CORS ポリシーによりブラウザがアクセスをブロックしてしまいます。

{{< code lang="js" title="クライアントサイド JS" >}}
fetch('http://localhost:8080/')
  .then((response) => response.json())
  .then((data) => console.log(data))
{{< /code >}}

アクセスがブロックされたことは、Web ブラウザのコンソールログを見るとわかります。

> Access to fetch at 'http://localhost:8080/' from origin 'http://localhost:3000' has been __blocked by CORS policy__: No 'Access-Control-Allow-Origin' header is present on the requested resource. If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled.

Golang の [rs/cors パッケージ](https://pkg.go.dev/github.com/rs/cors) を使用すると、簡単に CORS 対応用の HTTP レスポンスヘッダーを返すことができます。

{{< code lang="console" title="rs/cors の依存情報を追加" >}}
$ go get github.com/rs/cors
{{< /code >}}

{{< code lang="go" title="main.go（CORS 対応版）" >}}
package main

import (
	"log"
	"net/http"

	"github.com/rs/cors"
)

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/", func(w http.ResponseWriter, _ *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		w.Write([]byte("{\"hello\": \"world\"}"))
	})

	// CORS レスポンスヘッダーの追加
	c := cors.Default()
	handler := c.Handler(mux)

	log.Fatal(http.ListenAndServe(":8080", handler))
}
{{< /code >}}

追加したコードは、下記の部分です。

```go
c := cors.Default()
handler := c.Handler(mux)
```

既存のハンドラー (`mux`) を、デフォルトの `cors.Cors` インスタンスが持つハンドラーでラップしています。
これにより、次のようなクロスドメインアクセスを許可するレスポンスヘッダーが付加されるようになります。

```yaml
Access-Control-Allow-Origin: *
```

上記のように `cors.Default()` が返す `cors.Cors` インスタンスを使うと、すべてのドメインからの GET/POST アクセスを許可しますが、次のように独自の `cors.Cors` オブジェクトを作成して受け入れるドメインや HTTP メソッドを指定することができます。

```go
c := cors.New(cors.Options{
	AllowedOrigins:   []string{"http://localhost:3000", "http://foo.com"},
	AllowedMethods:   []string{http.MethodGet, http.MethodPost, http.MethodDelete},
	AllowCredentials: true,
})
```

CORS アクセスの問題がどうしても解決できないときは、次のように、すべての接続を許可する CORS 設定 (`cors.AllowAll()`) を試してみるとよいです。

```go
c := cors.AllowAll()
handler := c.Handler(mux)
```

