---
title: "Golang で GraphQL サーバーを作成する (gqlgen)"
url: "p/v48adgi/"
date: "2022-08-20"
lastmod: "2022-09-15"
tags: ["Golang", "GraphQL"]
---

Go 言語用の GraphQL ライブラリ [gqlgen](https://gqlgen.com) を使って、GraphQL サーバーを作ってみます。
gqlgen は、スキーマファーストの設計を採用しており、最初に GraphQL スキーマを定義し、それに合わせて各クエリ用のリゾルバーを実装していきます。
リゾルバーの雛形は、`gqlgen generate` というコマンドで生成できます。


Go プロジェクトを作成して gqlgen コマンドをインストールする
----

### Go プロジェクトの作成

まずは、Go のプロジェクトを Go Modules として作成します。
モジュール名は適当に `example.com/myapp` としておきます。

```console
$ mkdir myapp && cd myapp
$ go mod init example.com/myapp
```

これで、`myapp` ディレクトリ内に `go.mod` ファイルが生成されます。

### gqlgen コマンドのインストール

`gqlgen` コマンドは `github.com/99designs/gqlgen` という Go モジュールとして提供されています。
Go 言語の慣例として、プロジェクトのビルドに必要なツールのモジュール依存情報は、__`tools.go`__ というファイルに記述すべしとされているので、次のような内容で作成しておきます（参考: [How can I track tool dependencies for a module?](https://github.com/golang/go/wiki/Modules#how-can-i-track-tool-dependencies-for-a-module)）。

{{< code lang="go" title="tools.go" >}}
//go:build tools

package tools

import (
	_ "github.com/99designs/gqlgen"
)
{{< /code >}}

`go get` コマンドを実行して、プロジェクトの `go.mod` に `gqlgen` コマンド用のモジュール依存情報を追加します。
指定可能なバージョンは [gqlgen の Release ページ](https://github.com/99designs/gqlgen/releases) で確認してください。

```console
$ go get github.com/99designs/gqlgen@latest  # 最新バージョンを使う場合（go get . でも OK）
$ go get github.com/99designs/gqlgen@v0.17.14  # バージョンを指定する場合
```

次のように `gqlgen` コマンドを実行できるようになれば OK です。

```console
$ go run github.com/99designs/gqlgen version
v0.17.14
```

{{% note title="なぜ tools.go が必要？" %}}
`tools.go` ファイルがなくても、`go get` コマンドで `gqlgen` 関連の依存情報を追加することはできます。
ただ、`go mod tidy` コマンドで依存情報を整理すると、Go コードから参照されていないモジュールの依存情報は `go.mod` ファイルから削除されてしまうので、何らかの Go コードで `gqlgen` コマンド用のモジュールをインポートしておかなければいけません。
そのために使われるファイルが `tools.go` です。
さらに、このファイルの先頭に、`//go:build tools` という特殊なタグ ([Build Constraints](https://pkg.go.dev/go/build#hdr-Build_Constraints)) を記述しておくことで、アプリ本体のビルド時には `tools.go` は無視してくれるようになります。
{{% /note %}}


gqlgen プロジェクトのスケルトンを生成する (gqlgen init)
----

__`gqlgen init`__ コマンドを使用すると、gqlgen プロジェクトのスケルトンコードを生成することができます。
ここから先は、[本家のチュートリアル](https://gqlgen.com/getting-started/) 通りにコードを修正していきます。

```console
$ go run github.com/99designs/gqlgen init
Creating gqlgen.yml
Creating graph/schema.graphqls
Creating server.go
Generating...

Exec "go run ./server.go" to start GraphQL server
```

この時点で、次のようなディレクトリ構成になっているはずです。
__★__ が付いているのが `gqlgen init` で生成されたファイルです。

- `go.mod`
- `go.sum`
- `gqlgen.yml`  __★__ gqlgen の設定ファイル
- `graph/`  __★__ GraphQL スキーマやそのリゾルバー実装は基本的にここに入れる
  - `generated/`  __★__ gqlgen が自動生成するファイル群
    - `generated.go` __★__ 触らない
  - `model/`  __★__ GraphQL クエリで取得するデータの型情報
    - `models_gen.go`  __★__ `schema.graphqls` に基づいて自動生成される（触らない）
  - `resolver.go`  __★__ GraphQL サーバーが読み込むリゾルバー。自力で実装する
  - `schema.graphqls`  __★__ GraphQL のスキーマファイル。ここから定義していく
  - `schema.resolvers.go`  __★__ 上記スキーマ用のリゾルバー実装。resolver.go から参照する
- `server.go`  __★__ GraphQL サーバー本体（main パッケージの main 関数）
- `tools.go`

生成された GraphQL スキーマファイルを覗いてみると、どうやら TODO を管理する API のサンプルになっているようです。
ルートクエリとして `todos`、mutation 用に `createTodo` が定義されています。

{{< code lang="graphql" title="graph/schema.graphqls" >}}
type Todo {
  id: ID!
  text: String!
  done: Boolean!
  user: User!
}

type User {
  id: ID!
  name: String!
}

type Query {
  todos: [Todo!]!
}

input NewTodo {
  text: String!
  userId: String!
}

type Mutation {
  createTodo(input: NewTodo!): Todo!
}
{{< /code >}}

ちなみに、GraphQL スキーマファイルは、拡張子が __`.graphqls`__ であれば、複数のファイルに分割されていても大丈夫です（`gqlgen.yml` 設定ファイルで、`schema: [graph/*.graphqls]` のように指定されているからです）。

ここで GraphQL サーバーを起動してみたいところですが、悲しいことに、スケルトンとして生成されたファイルはチュートリアル用で不完全なので、以下のようにリゾルバーの実装を少し修正する必要があります（サーバーの起動自体はできますが、クエリ時に panic が発生します）。


GraphQL リゾルバーの実装
----

### graph/resolver.go

`resolver.go` は、GraphQL リゾルバーのルート定義的なファイルです。
GraphQL サーバーが利用する `Resolver` 構造体の型を定義しておきます。

```go
package graph

import "example.com/myapp/graph/model"

type Resolver struct {
	todos []*model.Todo
}
```

`Resolver` は、`model.Todo` のスライス (`todos`) を保持しています。
この値は GraphQL サーバーが動作している間だけメモリ上に保持されます（いわゆるステートです）。
サーバー本体 (`server.go`) の実装を見てみると、サーバー生成時に、`&graph.Resolver{}` といった感じで上記の struct 値が生成されていることがわかります。

肝心のリゾルバー関数の定義が見当たりませんが、それらは `graph/schema.resolvers.go` という別ファイルで定義するようになっています。
Go 言語の仕様上、同じパッケージ内であればどのファイルで定義してもよいのですが、`graph/schema.graphqls` というスキーマファイル名に対応するリゾルバーファイル名になっているようです（このあたりのファイル構成は gqlgen のバージョンによって変わるかもしれません）。

### graph/schema.resolvers.go

mutation 操作用の `CreateTodo` 関数と、query 操作用の `Todos` 関数の実装が空っぽになっているので、次のような感じで実装します。

```go
// createTodo mutation 用のリゾルバー
func (r *mutationResolver) CreateTodo(ctx context.Context, input model.NewTodo) (*model.Todo, error) {
	todo := &model.Todo{
		Text: input.Text,
		ID:   fmt.Sprintf("T%d", rand.Int()),
		User: &model.User{ID: input.UserID, Name: "user " + input.UserID},
	}
	r.todos = append(r.todos, todo)
	return todo, nil
}

// todos query 用のリゾルバー
func (r *queryResolver) Todos(ctx context.Context) ([]*model.Todo, error) {
	return r.todos, nil
}
```

やっていることは単純で、`CreateTodo` 関数で `model.Todo` オブジェクトを生成して `Resolver` の `todos` スライスに追加し、`Todos` 関数でそのスライスの内容を返すように実装しています。
`todos` スライスの値はメモリ上に保持されるので、GraphQL サーバーを起動してから `createTodo` mutation を呼び出し、続けて `todos` query を呼び出せば、その値を取得できるはずです。

ここまで実装できたら、次のように GraphQL サーバーを起動します。

```console
$ go run server.go
2022/08/20 22:59:26 connect to http://localhost:8080/ for GraphQL playground
```

Web ブラウザで `http://localhost:8080/` を開くと、次のように GraphiQL が起動して、任意のクエリをテストできます。

{{< image w="600" border="true" src="img-001.png" title="GraphiQL の画面" >}}

初期状態では `Resolver` の `todos` スライスが空っぽなので、`todos` クエリをかけても何も返って来ません。
次のように `createTodo` mutation で TODO を追加してから、

```graphql
mutation createTodo {
  createTodo(input: { text: "ゴミ捨て", userId: "1" }) {
    id
    text
    done
  }
}
```

続けて次のように `todos` クエリを実行すると、

```graphql
query findTodos {
  todos {
    id
    text
    done
    user {
      name
    }
  }
}
```

次のような結果 (JSON) が返って来ます。

```json
{
  "data": {
    "todos": [
      {
        "id": "T5577006791947779410",
        "text": "ゴミ捨て",
        "done": false,
        "user": {
          "name": "user 1"
        }
      }
    ]
  }
}
```

これでチュートリアル的な GraphQL サーバーの実装は完成です。
上記の TODO 情報は、サーバーを起動している間のみ有効です。


GraphQL サーバーを CORS 対応する
----

Web ブラウザ上で動作させる JavaScript（クライアントサイド JS）から、GraphQL サーバーにアクセスする場合、おそらく GraphQL サーバー側で CORS（クロスドメインアクセス）用の対応が必要になります。
__`rs/cors`__ パッケージを使うと簡単に CORS 対応のための HTTP レスポンスを返すことができます。

{{< code lang="console" title="rs/cors パッケージの依存を追加" >}}
$ go get github.com/rs/cors
{{< /code >}}

具体的には、HTTP サーバーのミドルウェアとして、`cors.Cors` オブジェクトが提供するハンドラー実装を挟むようにします。

{{< code lang="go" title="server.go（抜粋）" hl_lines="9-10" >}}
// import "github.com/rs/cors"

func main() {
	// ...

	srv := handler.NewDefaultServer(generated.NewExecutableSchema(
		generated.Config{Resolvers: &graph.Resolver{}}
	))
	handler := cors.Default().Handler(srv) // ★CORS レスポンス対応
	http.Handle("/query", handler)

	log.Fatal(http.ListenAndServe(":"+port, nil))
}
{{< /code >}}

CORS 対策はあくまで HTTP サーバーに必要なものであって、GraphQL サーバーを実装しているかどうかは本質的には関係ないことに注意してください。

- 参考: [Golang で HTTP サーバーを作成する (net/http, rs/cors)](/p/goruwy4/)

