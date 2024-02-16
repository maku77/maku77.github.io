---
title: "Rust で REST API サーバーを作る (axum)"
url: "p/q49pmjt/"
date: "2024-02-16"
tags: ["Rust"]
draft: true
---

axum とは
----

__axum__ は Rust 用の Web フレームワークです。

- Repo: https://github.com/tokio-rs/axum/
- Docs: https://docs.rs/axum/latest/axum/

Rust 用の Web フレームワークには、他にも Actix Web (`actix-web`) や Rocket (`rocket`) などがありますが、axum は後発の Web フレームワークで、ビルド後のファイルサイズが比較的小さくなります。
axum は、他のフレームワークと違って、`get` や `post` マクロなどを使わないのが特徴的で、マクロ疲れしている人にはぴったりです。
ここでは、axum を使ってシンプルな REST API サーバーを実装してみます。


プロジェクトの作成
----

まず、Rust のプロジェクトを作成します。
ここでは、`rest-server` という名前にします。

```console
$ cargo new rest-server
$ cd rest-server
```

必要なライブラリの依存関係を追加します。
`axum` に加え、非同期ランタイムの `tokio` も使用します。

```console
$ cargo add axum
$ cargo add tokio --features rt-multi-thread  # Rust の非同期ランタイム
```


まずは Hello World サーバーを作ってみる
----

下記は、`Hello, World!` というテキストを返すだけの、シンプルな Web サーバーの実装例です。

{{< code lang="rust" title="src/main.rs" >}}
use axum::{routing::get, Router};
use tokio::net::TcpListener;

#[tokio::main(flavor = "multi_thread")]
async fn main() {
    let router = Router::new().route("/", get(root));
    let listener = TcpListener::bind("0.0.0.0:8000").await.unwrap();
    axum::serve(listener, router).await.unwrap();
}

async fn root() -> &'static str {
    "Hello, World!"
}
{{< /code >}}

処理の流れはほとんど明らかだと思いますが、URL のルートパス `/` に GET メソッドでアクセスしたら `root` 関数が呼ばれる、という実装ですね。
次のようにして Web サーバーを起動できます。

```console
$ cargo run
```

Web サーバーを起動した状態で、別のターミナルや Web ブラウザから `http://localhost:8000` にアクセスして、メッセージが返ってきたら成功です。

```console
$ curl localhost:8000
Hello, World!
```

シンプル！


