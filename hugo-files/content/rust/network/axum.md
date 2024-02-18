---
title: "Rust で Web サーバーを作る (axum)"
url: "p/q49pmjt/"
date: "2024-02-16"
tags: ["Rust"]
---

axum とは
----

__axum__ は Rust 用の Web フレームワークです。

- Repo: https://github.com/tokio-rs/axum/
- Docs: https://docs.rs/axum/latest/axum/

axum は Rust の非同期処理ランタイムの代表格である tokio のサブプロジェクトとして公開されました。
そのため、axum を使ったアプリケーション実装では、tokio が提供するフレームワークを組み合わせて使用します。

- 参考: [Rust の非同期ランタイム tokio の使い方 (async/await)](/p/z6e9or8/)

Rust 用の Web フレームワークには、他にも Actix Web (`actix-web`) や Rocket (`rocket`) などがありますが、axum は後発の Web フレームワークで、公開直後の 2022 年頃から利用者が急増しています。
この人気っぷりは、やはり tokio ファミリーのプロジェクトであることが大きいのでしょう。

axum は、他のフレームワークと違って、`get` や `post` マクロなどを使わないのが特徴的で、マクロ疲れしている人にはぴったりです。
ここでは、axum を使ってシンプルな Web サーバーを実装してみます。


プロジェクトの作成
----

まず、Rust のプロジェクトを作成します。
ここでは、`webserver` という名前にします。

{{< code lang="console" title="プロジェクトの作成" >}}
$ cargo new webserver
$ cd webserver
{{< /code >}}

必要なライブラリの依存関係を追加します。
通常は、非同期ランタイムの `tokio` や JSON を扱うための `serde` も必要になります。

{{< code lang="console" title="依存情報の追加" >}}
$ cargo add axum
$ cargo add tokio --features full    # 非同期ランタイム
$ cargo add serde --features derive  # シリアライズ＆デシリアライズ
$ cargo add serde_json
{{< /code >}}


まずは Hello World サーバーを作ってみる
----

下記は、`Hello, World!` というテキストを返すだけの、シンプルな Web サーバーの実装例です。

{{< code lang="rust" title="src/main.rs" >}}
use axum::{routing::get, Router};
use tokio::net::TcpListener;

#[tokio::main(flavor = "multi_thread")]
async fn main() {
    let app = Router::new().route("/", get(root));
    let listener = TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

// 文字列だけを返すハンドラーは、ステータスコード `200 OK` の
// `content-type: text/plain; charset=utf-8` のレスポンスになる。
async fn root() -> &'static str {
    "Hello, World!"
}
{{< /code >}}

処理の流れはほとんど明らかだと思いますが、URL のルートパス `/` に GET メソッドでアクセスしたら `root` という名前のハンドラー関数が呼ばれる、という実装ですね。
次のようにして Web サーバーを起動できます。

```console
$ cargo run
```

Web サーバーを起動した状態で、別のターミナルや Web ブラウザから `http://localhost:8080` にアクセスして、メッセージが返ってきたら成功です。

```console
$ curl localhost:8080
Hello, World!
```

シンプル！


リクエスト時のパスを取得する (axum::extract::Path)
----

リクエストされた URL に含まれるパス情報（例: `example.com/users/123` の `123` の部分）を取得したいときは、axum の [extractor](https://docs.rs/axum/latest/axum/#extractors) のひとつである __`axum::extract::Path`__ を使用します。

次の例では、`/users/:id` というパスに対するハンドラー関数 `get_user()` を定義し、`:id` の位置で指定されたパス文字列を取得しています。

{{< code lang="rust" title="src/main.rs" >}}
use axum::{extract::Path, routing::get, Router};
use tokio::net::TcpListener;

#[tokio::main(flavor = "multi_thread")]
async fn main() {
    let app = Router::new().route("/users/:id", get(get_user));
    let listener = TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

async fn get_user(Path(id): Path<String>) -> String {
    format!("User ID: {}", id)
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ curl 127.0.0.1:8080/users/123
User ID: 123
{{< /code >}}


リクエスト時のクエリ文字列を取得する (axum::extract::Query)
----

同様に、URL の末尾に指定されたクエリ文字列（例: `example.com/search?genre=ACT&year=2000` の `genre=ACT&year=2000` の部分）を取得したいときは、__`axum::extract::Query`__ を使用します。
`serde` のデシリアライズ機能を使うことで、クエリパラメーターを構造体の形で参照できます。

{{< code lang="rust" title="src/main.rs" >}}
use axum::{extract::Query, routing::get, Router};
use serde::Deserialize;
use tokio::net::TcpListener;

#[tokio::main(flavor = "multi_thread")]
async fn main() {
    let listener = TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app_router()).await.unwrap();
}

fn app_router() -> Router {
    // クエリパラメーターの型
    #[derive(Deserialize, Debug)]
    #[allow(dead_code)]
    struct SearchQuery {
        genre: Option<String>,
        year: Option<usize>,
    }

    // クエリパラメーターのデフォルト値
    impl Default for SearchQuery {
        fn default() -> Self {
            Self {
                genre: None,
                year: None,
            }
        }
    }

    async fn search(query: Option<Query<SearchQuery>>) -> String {
        // いずれかのクエリパラメーターが不正な型だった場合は、すべてデフォルト値になる
        let Query(search_query) = query.unwrap_or_default();
        format!("{:?}", search_query)
    }

    Router::new().route("/search", get(search))
}
{{< /code >}}

下記はいろいろなクエリパラメーターでリクエストしたときの、レスポンスの例です。
パラメーターの型が不正なとき（例: `year=ABC`）は、すべてのパラメーターをデフォルト値（今回はすべて `None`）として扱っています。

{{< code lang="console" title="実行結果" >}}
$ curl "127.0.0.1:8080/search"
SearchQuery { genre: None, year: None }

$ curl "127.0.0.1:8080/search?genre=RPG"
SearchQuery { genre: Some("RPG"), year: None }

$ curl "127.0.0.1:8080/search?genre=RPG&year=2000"
SearchQuery { genre: Some("RPG"), year: Some(2000) }

$ curl "127.0.0.1:8080/search?genre=ACT&year=ABC"
SearchQuery { genre: None, year: None }
{{< /code >}}


ステートを保持する (with_state)
----

HTTP はステートレスなプロトコルなので、基本的に各リクエストは独立したものになりますが、__`Router.with_state()`__ メソッドを使うと、リクエスト間で任意のステート情報を共有することができます。
例えば、次のような用途で使用できます。

- アプリケーションの共通設定
- データベース接続のプール
- セッション管理、認証情報の保持
- ロードに時間がかかるデータのキャッシュ

次の例では、ステートとして `AppState` 構造体の値を保持しています。
`Router.with_state()` でセットしたステート情報は、各ハンドラーの引数として受け取ることができます。

{{< code lang="rust" title="src/main.rs" hl_lines="23" >}}
use axum::extract::State;
use axum::{routing::get, Router};
use std::sync::Arc;
use tokio::net::TcpListener;
use tokio::sync::Mutex;

// リクエスト間で共有するステートの型
struct AppState {
    message: String,
    counter: Mutex<u64>,
}

#[tokio::main]
async fn main() {
    // ステートの初期化
    let app_state = Arc::new(AppState {
        message: String::from("Welcome!"),
        counter: Mutex::new(0),
    });

    let app = Router::new()
        .route("/", get(root))
        .with_state(app_state);  // ルーター内でステートを共有

    let listener = TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

// ハンドラーの引数としてステートを受け取る
async fn root(State(app_state): State<Arc<AppState>>) -> String {
    let mut counter = app_state.counter.lock().await;
    *counter += 1;
    format!("{} {}", app_state.message, *counter)
}
{{< /code >}}

ハンドラーの中でステートの `counter` 値をインクリメントしているので、`curl` や Web ブラウザーでアクセスするたびに次のように出力が変わります。

{{< code lang="console" title="実行結果" >}}
$ curl 127.0.0.1:8080
Welcome! 1
$ curl 127.0.0.1:8080
Welcome! 2
$ curl 127.0.0.1:8080
Welcome! 3
{{< /code >}}

