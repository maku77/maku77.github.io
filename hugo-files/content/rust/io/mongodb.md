---
title: "Rust で MongoDB を扱う (mongodb)"
url: "p/nzi6xbm/"
date: "2023-12-23"
tags: ["mongodb", "rust"]
---

Rust プログラムから MongoDB にアクセスするには、[`mongodb` クレート](https://docs.rs/mongodb/latest/mongodb/) を使用します。

各種クレートのインストール
----

`cargo add` コマンドを使って、次のようなクレートをインストールします。

- __`mongodb`__ ... MongoDB を扱うためのクレート
- __`serde`__ ... MongoDB 内の BSON データと Rust の struct を相互変換（シリアライズ／デシリアライズ）するためのクレート
- __`tokio`__ ... Rust の async 処理用のランタイム実装を提供するクレート
- __`futures`__ ... TryStream と呼ばれる非同期ストリームを操作するための拡張メソッドを使うため（サンプルコード内の `cursor.try_next()` の部分に必要）

```console
# （必要に応じて）プロジェクトの作成
$ cargo new myapp
$ cd myapp

# クレートのインストール
$ cargo add mongodb serde tokio futures
```

`Cargo.toml` に次のような感じで依存関係が追加されていれば準備 OK です。

{{< code lang="toml" title="Cargo.toml" >}}
[dependencies]
futures = "0.3.29"
mongodb = "2.8.0"
serde = "1.0.193"
tokio = "1.35.1"
{{< /code >}}


MongoDB サーバーの準備
----

接続先の MongoDB サーバーはローカルで起動しておくか、[MongoDB Atlas](https://www.mongodb.com/atlas) などのクラウドサービスで用意しておいてください。

- 参考: [MongoDB 関連記事｜まくろぐ](https://maku.blog/p/qikq9o8/)

以下の説明では、`localhost:27017` で MongoDB サーバーが稼働していることを想定しています。


サンプルコード
----

{{% private %}}
- https://www.mongodb.com/docs/drivers/rust/current/quick-reference/
{{% /private %}}

### データベースの一覧を取得する

下記のコードは、MongoDB サーバーに接続して、データベースの一覧を列挙する例です。

{{< code lang="rust" title="databases.rs" >}}
use mongodb::Client;

const URI: &str = "mongodb://127.0.0.1:27017";

#[tokio::main]
async fn main() -> mongodb::error::Result<()> {
    // MongoDB サーバーに接続して Client インスタンスを取得
    let client = Client::with_uri_str(URI).await?;

    // データベースを列挙
    for db_name in client.list_database_names(None, None).await? {
        println!("{}", db_name);
    }
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cargo run -q
admin
config
local
{{< /code >}}

### コレクション内のドキュメント操作

次の例では、`mydb` データベース内の `books` コレクションにドキュメントをいくつか追加し、それらを取り出しています。
ここでは、各ドキュメントを表現する `struct` として `Book` 型を定義しています。

{{< code lang="rust" title="books.rs" >}}
use futures::TryStreamExt;
use mongodb::{bson::oid::ObjectId, Client};
use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize)]
struct Book {
    #[serde(rename = "_id", skip_serializing)]
    id: Option<ObjectId>,
    title: String,
    price: i32,
}

const URI: &str = "mongodb://127.0.0.1:27017";

#[tokio::main]
async fn main() -> mongodb::error::Result<()> {
    // MongoDB サーバーに接続して Client インスタンスを取得
    let client = Client::with_uri_str(URI).await?;

    // books コレクションのハンドルを取得する
    let books_coll = client.database("mydb").collection::<Book>("books");

    // コレクション内のドキュメントをすべて削除する
    books_coll.drop(None).await?;

    // コレクションにドキュメントを追加する
    let books = vec![
        Book {
            id: None,
            title: "Title-1".to_string(),
            price: 1000,
        },
        Book {
            id: None,
            title: "Title-2".to_string(),
            price: 2000,
        },
        Book {
            id: None,
            title: "Title-3".to_string(),
            price: 3000,
        },
    ];
    books_coll.insert_many(books, None).await?;

    // コレクション内のすべてのドキュメントを取得する
    let mut cursor = books_coll.find(None, None).await?;
    while let Some(book) = cursor.try_next().await? {
        println!("{:?}, {}, {}", book.id, book.title, book.price);
    }

    Ok(())
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cargo run -q
Some(ObjectId("658721862f9a5832444f7833")), Title-1, 1000
Some(ObjectId("658721862f9a5832444f7834")), Title-2, 2000
Some(ObjectId("658721862f9a5832444f7835")), Title-3, 3000
{{< /code >}}


関連リンク
----

- [Deno で MongoDB を扱う](https://maku.blog/p/3pwojuj/)
- [Golang で MongoDB を扱う (go.mongodb.org/mongo-driver)](/p/uft7jv9/)

