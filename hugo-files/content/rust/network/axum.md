---
title: "Rust で HTTP サーバーを作る (axum)"
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
その代わりに、リクエストをハンドルする関数に、extractor と呼ばれる引数を配置することで、リクエストの情報を抽出します。
例えば、次のような extractor を、ハンドラー関数の引数として任意の数だけ配置できます。

- `axum::extract::Path` ... パスパラメーター（URL 内のパスの部分的なセグメント）を抽出する
- `axum::extract::Query` ... URL の末尾のクエリパラメーターを抽出する
- `axum::extract::Json` ... POST メソッドのペイロードとして送られてきた JSON データを抽出する
- `axum::extract::Request` ... リクエスト全体を抽出する
- `http::header::Method` ... リクエストメソッドを抽出する
- `http::header::HeaderMap` ... リクエストヘッダーを抽出する
- `String` ... リクエスト本文をそのまま utf-8 文字列で取得する

下記はリクエストに使われた HTTP メソッドとヘッダー、本文（ペイロード）を参照するハンドラー関数の引数の例です。

```rust
use axum::http::{Method, HeaderMap};

async fn handler(
    method: Method,      // メソッドを取得したいときはこの引数を配置
    headers: HeaderMap,  // ヘッダーを取得したいときはこの引数を配置
    body: String,        // 本文を取得したいときはこの引数を配置
) {
    // ...
}
```


ここでは、axum を使ってシンプルな HTTP サーバーを実装してみます。


プロジェクトの作成
----

まず、Rust のプロジェクトを作成します。
ここでは、`http-server` という名前にします。

{{< code lang="console" title="プロジェクトの作成" >}}
$ cargo new http-server
$ cd http-server
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

#[tokio::main]
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

#[tokio::main]
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

ちなみに、`/users/:user_id/team/:team_id` のように 2 つ以上のパスパラメーターを抽出するときは、次のようにタプルを使います。

```rust
async fn users_teams_create(
    Path((user_id, team_id)): Path<(String, String)>,
) {
    // ...
}
```


リクエスト時のクエリ文字列を取得する (axum::extract::Query)
----

同様に、URL の末尾に指定されたクエリ文字列（例: `example.com/search?genre=ACT&year=2000` の `genre=ACT&year=2000` の部分）を取得したいときは、__`axum::extract::Query`__ を使用します。
`serde` のデシリアライズ機能を使うことで、クエリパラメーターを構造体の形で参照できます。

{{< code lang="rust" title="src/main.rs" >}}
use axum::{extract::Query, routing::get, Router};
use serde::Deserialize;
use tokio::net::TcpListener;

#[tokio::main]
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


リクエストのメソッドやヘッダー情報を取得する
----

HTTP リクエストに使われたメソッド（GET や POST）を取得したいときは、ハンドラー関数の引数として __`Method`__ を追加します。
同様に、リクエストヘッダー情報を取得したいときは、ハンドラー関数の引数として __`HeaderMap`__ を追加します。

{{< code lang="rust" title="src/main.rs" hl_lines="18-19">}}
use axum::{
    http::{HeaderMap, Method},
    routing::get,
    Router,
};
use tokio::net::TcpListener;

#[tokio::main]
async fn main() {
    // パス `/` で GET と POST の両方をハンドルする
    let app = Router::new().route("/", get(handler).post(handler));

    let listener = TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

async fn handler(
    method: Method,     // メソッドの extractor
    headers: HeaderMap, // ヘッダーの extractor
) {
    println!("{:?}", method);
    println!("{:?}", headers);
}
{{< /code >}}

下記は、`curl` コマンドで HTTP リクエストを送ったときのサーバー側の出力を示しています。

{{< code lang="console" title="実行結果" >}}
$ curl "127.0.0.1:8080"
GET
{"host": "127.0.0.1:8080", "user-agent": "curl/7.79.1", "accept": "*/*"}

$ curl -X "127.0.0.1:8080"
POST
{"host": "127.0.0.1:8080", "user-agent": "curl/7.79.1", "accept": "*/*"}

$ curl "localhost:8080" -H "key:value"
GET
{"host": "localhost:8080", "user-agent": "curl/7.79.1", "accept": "*/*", "key": "value"}
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
`Router.with_state()` でセットしたステート情報は、各ハンドラーの引数として __`State`__ extractor を配置することで受け取ることができます。

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


POST リクエストで送られた JSON データを取得する
----

POST メソッドで送られてきた本文を単純な utf-8 文字列として取得するだけであれば、次のようにハンドラー関数に `String` 型の引数を追加するだけですみます。

```rust
async fn handler(body: String) {
    println!("{}", body);
}
```

ただ、REST API などを実装しているときは、本文として送られてくるデータは JSON 形式になっていることがほとんどなので、このデータを構造体インスタンスとして参照できると便利です。
axum の __`JSON`__ extractor と、serde の __`Deserialize`__ 属性を使うことでこれを実現できます。

次の例では、本文として送られてきた JSON データを `CreateTodoPayload` 構造体として参照しています。

{{< code lang="rust" title="src/main.rs" >}}
use axum::{extract::Json, routing::post, Router};
use serde::Deserialize;
use tokio::net::TcpListener;

#[derive(Debug, Deserialize)]
struct CreateTodoPayload {
    title: String,         // 必須のフィールド
    note: Option<String>,  // 省略可能なフィールド
}

#[tokio::main]
async fn main() {
    let app = Router::new().route("/todos", post(create_todo));
    let listener = TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

async fn create_todo(Json(payload): Json<CreateTodoPayload>) -> String {
    let title = payload.title;
    let note = payload.note.unwrap_or_default();  // 省略時は空文字列とする
    format!("Created: title={}, note={}", title, note)
}
{{< /code >}}

{{< code title="実行結果" >}}
$ curl -X POST 'localhost:8080/todos' \
       -H 'Content-Type: application/json' \
       --data '{"title": "Title-1", "note": "Note-1"}'

Created: title=Title-1, note=Note-1
{{< /code >}}


レスポンスとして JSON データを返す
----

構造体データを JSON 形式のレスポンスとして返したいときは、ハンドラー関数の戻り値で __`axum::Json`__ を返します（`axum::reponse::Json` でも OK）。
`Json` コンストラクターには、serde の __`Serialize`__ 属性をつけた構造体インスタンスを渡します。

{{< code lang="rust" title="src/main.rs" >}}
use axum::{extract::Path, routing::get, Json, Router};
use serde::Serialize;
use tokio::net::TcpListener;

#[derive(Debug, Serialize)]
struct UserInfo {
    id: String,
    name: String,
}

#[tokio::main]
async fn main() {
    let app = Router::new().route("/users/:id", get(get_user));
    let listener = TcpListener::bind("0.0.0.0:8080").await.unwrap();
    axum::serve(listener, app).await.unwrap();
}

async fn get_user(Path(user_id): Path<String>) -> Json<UserInfo> {
    // 本当はデータベースなどからユーザー情報を取得する
    let user_info = UserInfo {
        id: user_id.clone(),
        name: format!("User-{}", user_id),
    };

    // JSON 形式のレスポンスとして返す
    Json(user_info)
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ curl '127.0.0.1:8080/users/123'
{"id":"123","name":"User-123"}
{{< /code >}}

