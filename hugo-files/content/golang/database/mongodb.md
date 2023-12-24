---
title: "Golang で MongoDB を扱う (go.mongodb.org/mongo-driver)"
linkTitle: "MongoDB を扱う (go.mongodb.org/mongo-driver)"
url: "p/uft7jv9/"
date: "2022-10-16"
tags: ["Golang", "MongoDB"]
---

Golang を使って MongoDB サーバーに接続する方法の説明です。

Golang 用の MongoDB ドライバー
----

__`go.mongodb.org/mongo-driver/mongo`__ モジュールは、Golang 用の MongoDB 公式ドライバーです。
Golang のプロジェクトをモジュールモードで初期化して、`go get` で依存関係を追加すれば MongoDB にアクセスする準備は完了です。

```console
$ mkdir example-mongo
$ cd example-mongo
$ go mod init example-mongo
$ go get go.mongodb.org/mongo-driver/mongo
```


MongoDB サーバーの準備
----

接続先の MongoDB サーバーはローカルで起動しておくか、[MongoDB Atlas](https://www.mongodb.com/atlas) などのクラウドサービスで用意しておいてください。

- 参考: [MongoDB 関連記事｜まくろぐ](https://maku.blog/p/qikq9o8/)

以下の説明では、`localhost:27017` で MongoDB サーバーが稼働していることを想定しています。


MongoDB に接続する
----

MongoDB へ接続するためのクライアント設定は、[options.ClientOptions](https://pkg.go.dev/go.mongodb.org/mongo-driver/mongo/options#ClientOptions) インスタンスで表現します。
このインスタンスは __`options.Client()`__ 関数で生成できるので、あとは各種セッターメソッドでオプション指定していきます。
MongoDB Atlas などのサービスを使っているのであれば、接続文字列が提供されているはずなので、それをそのまま __`ApplyURI`__ メソッドに渡してやるだけで最低限の接続設定は完了します（参考: [接続文字列の形式](https://www.mongodb.com/docs/manual/reference/connection-string/)）。

{{< code lang="go" title="main.go（localhost:27017 への接続例）" >}}
package main

import (
	"context"
	"fmt"
	"log"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

// MongoDB サーバーの接続文字列
const uri = "mongodb://localhost:27017"

func main() {
	ctx := context.Background()

	// 接続文字列の設定
	opt := options.Client().ApplyURI(uri)
	if err := opt.Validate(); err != nil {
		log.Fatal(err)
	}

	// MongoDB サーバーへの接続
	client, err := mongo.Connect(ctx, opt)
	if err != nil {
		log.Fatal(err)
	}

	// 関数を抜けるときに自動クローズ
	defer func() {
		if err := client.Disconnect(ctx); err != nil {
			log.Fatal(err)
		}
	}()

	// Ping してみる
	if err := client.Ping(ctx, nil); err != nil {
		log.Fatal(err)
	}
	fmt.Println("Ping to MongoDB server succeeded")
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ go run .
Connected to MongoDB
{{< /code >}}


クラス化で見通しをよくする
----

次のように構造体で `mongo.Client` インスタンスを保持するようにすれば、コレクション操作などをメソッドの形にまとめることができます。
接続先のアドレスを __`MONGODB_URI`__ のような環境変数で設定できるようにしておくと、様々な環境で実行できるプログラムになります。

{{< code lang="go" title="db/mydb.go" >}}
package db

import (
	"context"
	"log"
	"os"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type MyDb struct {
	client *mongo.Client
}

func (db *MyDb) getUri() (uri string) {
	uri = os.Getenv("MONGODB_URI")
	if uri == "" {
		log.Println("Env variable `MONGODB_URI` is not set, use `mongodb://localhost:27017` instead.")
		uri = "mongodb://localhost:27017"
	}
	return uri
}

func (db *MyDb) Connect(ctx context.Context) (err error) {
	opt := options.Client().ApplyURI(db.getUri())
	if err = opt.Validate(); err != nil {
		return err
	}
	db.client, err = mongo.Connect(ctx, opt)
	return err
}

func (db *MyDb) Disconnect(ctx context.Context) error {
	return db.client.Disconnect(ctx)
}

// （テスト用）接続確認
func (db *MyDb) Ping(ctx context.Context) (err error) {
	if err = db.client.Ping(ctx, nil); err != nil {
		return err
	}
	fmt.Println("Ping to MongoDB server succeeded")
	return nil
}
{{< /code >}}

{{< code lang="go" title="main.go" >}}
package main

import (
	"context"
	"log"
	"time"

	"example-mongo/db"
)

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer cancel()

	// MongoDB サーバーへの接続
	mydb := &db.MyDb{}
	if err := mydb.Connect(ctx); err != nil {
		log.Fatal(err)
	}
	defer mydb.Disconnect(ctx)

	// Ping してみる
	if err := mydb.Ping(ctx); err != nil {
		log.Fatal(err)
	}
}
{{< /code >}}


BSON 形式について
----

{{% private %}}
- [Work with BSON — Go](https://www.mongodb.com/docs/drivers/go/current/fundamentals/bson/)
- [bson package - go.mongodb.org/mongo-driver/bson - Go Packages](https://pkg.go.dev/go.mongodb.org/mongo-driver/bson)
{{% /private %}}

MongoDB はコレクション内のドキュメントを、[BSON 形式](https://bsonspec.org/) で保存しています。
BSON は JSON をバイナリ形式にして効率的なやりとりを行えるようにしたフォーマットです。
Golang で MongoDB のデータ操作を行うには、次のような変換処理 (marshalling/unmarshalling) が必要です。

- Marshalling ... 「Golang オブジェクト → BSON」の変換
- Unmarshalling ... 「BSON → Golang オブジェクト」の変換

この変換処理には、MongoDB 公式の `mongo-driver` パッケージに含まれている __`bson`__ モジュールが使用されます。

```go
import "go.mongodb.org/mongo-driver/bson"
```

Golang の struct（構造体）を marshal/unmarshal しようとすると、__公開された（大文字で始まる）フィールドのみ__ が変換対象になります。
また、BSON キー名は、struct のフィールド名を __すべて小文字に変換したもの__ になります。

```go
type Book struct {
	Title   string   // BSON キー名は "title" になる
	Authors []string // BSON キー名は "authors" になる
}
```

デフォルトの変換ルールでは不十分なときは、Golang の [struct tags の仕組み](/p/hxhzfbs/) を使うことで、ある程度カスタマイズできます。

```go
type Student struct {
	FirstName string  `bson:"first_name,omitempty"`
	LastName  string  `bson:"last_name,omitempty"`
	Address   Address `bson:"inline"`
	Age       int
}
```

例えば上記のようにタグ指定しておけば、`FirstName` フィールドの BSON キー名は `firstname` ではなく `first_name` になり、ゼロ値のときは空文字列 (`""`) が保存されるのではなく BSON キー自体が省略されます。
`Address` 構造体の各フィールドはフラット化されて、`FirstName` や `LastName` と同じ階層に保存されます。


コレクションを操作する
----

MongoDB サーバーへの接続が済んだら、あとは [mongo.Collection](https://pkg.go.dev/go.mongodb.org/mongo-driver/mongo#Collection) の各種メソッドを使って、コレクション内のドキュメントを操作できます。

### コレクションにドキュメントを追加する (InsertOne)

次の例では、`testdb` データベースの `books` コレクションに、`Book` 構造体のデータを保存しています。

```go
// MongoDB のコレクションに格納するドキュメントの型
type Book struct {
	Title   string
	Authors []string `bson:"omitempty"`
}

// 追加するドキュメントを生成
book := &Book{Title: "Title-1"}

// 追加を実行 (InsertOne)
result, err := client.Database("testdb").Collection("books").InsertOne(ctx, book)
if err != nil {
	log.Fatal(err)
}
log.Printf("Book inserted %v\n", result)
```

### コレクションから 1 つのドキュメントを取得する (FindOne)

次の例では、`books` コレクションから、`title` キーが `Title-1` に一致するドキュメントを検索しています。
`FindOne` メソッドは、指定した条件（フィルター）に一致するドキュメントが複数あっても、1 つのドキュメントのみを返します。
検索フィルターとして使っている `bson.M` 構造体は、マップ形式の BSON データを表現するためのものです。

```go
// 検索フィルター
filter := &bson.M{"title": "Title-1"}

// 検索を実行 (FindOne)
result := client.Database("testdb").Collection("books").FindOne(filter)

// 結果をデコードして Golang オブジェクトとして参照する
var book Book
if err := result.Decode(&book); err != nil {
	log.Fatal(err)
}
log.Printf("%#v\n", book)
```

### コレクションから複数のドキュメントを取得する (Find)

フィルターで指定した条件に一致するすべてのドキュメントを取得したいときは、`FindOne` の代わりに `Find` メソッドを使用します。
`Find` メソッドは、`*mongo.Cursor` オブジェクトを返すので、これを使って取得した複数のドキュメントを参照できます。
ドキュメント内のすべてのドキュメントを取得したいときは、`Find` メソッドに空っぽのフィルター (__`bson.D{}`__) を指定します。

```go
// 検索フィルター（すべてのドキュメントを取得する場合）
filter := &bson.D{}

// 検索を実行 (Find)
coll := client.Database("testdb").Collection("books")
cursor, err := coll.Find(ctx, filter)

// 結果をデコードして Golang オブジェクトとして参照する
var books []Book
if err := cursor.All(ctx, &books); err != nil {
	log.Fatal(err)
}
log.Printf("%#v\n", books)
```


関連リンク
----

- [Deno で MongoDB を扱う](https://maku.blog/p/3pwojuj/)
- [Rust で MongoDB を扱う (mongodb)](/p/nzi6xbm/)

