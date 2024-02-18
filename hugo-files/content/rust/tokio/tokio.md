---
title: "Rust の非同期ランタイム tokio の使い方 (async, await, Future)"
url: "p/z6e9or8/"
date: "2024-02-17"
tags: ["Rust"]
---

tokio とは
----

[tokio](https://tokio.rs/) は、Rust 用の非同期処理ランタイムです。
さらに、ネットワークアプリケーションの構築に必要な TCP ソケットなどのライブラリも含まれています。
Rust 言語には、コード上で非同期処理を表現するための __`async/await`__ 構文が用意されていますが、実際に非同期処理を動かすためのランタイムは標準搭載されていません。
非同期処理用の代表的なランタイムが `tokio` クレートです。

Rust の `async` は、ソフトウェアにより非同期処理を実現する仕組みであり、スレッド (`std::thread`) と比べてコンテキストスイッチのコストがかからないという利点があります。
非同期ランタイムの `tokio` はサードパーティライブラリとして提供されていますが、非同期処理で使われる `Future` トレイトなどは Rust に標準搭載されています。


tokio の依存関係の追加
----

tokio を使用するには、`cargo add` コマンドで Rust プロジェクトに依存関係を追加します。
ここでは、tokio のすべての機能を有効化するために `--features full` オプションを指定しています。

```console
$ cargo new myapp  # （必要があれば）新規プロジェクトを作成
$ cd myapp
$ cargo add tokio --features full
```

`cargo add` コマンドを使用する代わりに、次のように `Cargo.toml` ファイルに依存関係を記述しても OK です。

{{< code lang="toml" title="Cargo.toml" >}}
[dependencies]
tokio = { version = "1.36.0", features = ["full"] }
{{< /code >}}


async/await による非同期処理
----

下記は Rust の __`async/await`__ 構文の基本的な使い方です。

{{< code lang="rust" title="src/main.rs" >}}
// 非同期関数は async fn で定義する
async fn say_world() {
    println!("World");
}

#[tokio::main]
async fn main() {
    let future = say_world();  // Future インスタンスが返される
    println!("Hello");         // say_world() より先に実行される
    future.await;              // ここで say_world() が実行される
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cargo run -q
Hello
World
{{< /code >}}

非同期実行する関数を定義するには、__`async fn`__ という構文を使用します。
上記の `say_world` 関数と `main` 関数は両方とも非同期関数として定義されています。
非同期関数は `say_world()` のように通常の関数と同じ形で呼び出せますが、このタイミングでは実行されず、代わりに `Future` インスタンス（`Future` トレイトを実装した型）を返します。
`Future` インスタンスが指す非同期関数が実際に実行されるのは、__`.await`__ を呼び出したときです。
結果として、上記の `main` 関数を実行すると、`Hello`、`World` の順番で出力されます。

`.await` の呼び出しは、非同期関数 (`async fn`) の中でしか許可されていません。
上記の `main` 関数は非同期関数として定義されているので、`.await` の呼び出しが可能です。
`main` 関数は非同期処理が可能なコンテキスト（＝ランタイム）で実行されなければいけないので、__`#[tokio::main]`__ というアノテーションをつけて、非同期処理ランタイムの tokio で実行するよう指示しています。


tokio::spawn で非同期処理をすぐに開始する
----

前述の例からもわかるように、`Future` インスタンスの `.await` を呼び出すまでは、非同期関数の実行は開始されません（これは他の言語とは異なる部分かもしれません）。
でも、これだと、その非同期処理が終わるまでそこで待機してしまうので、他の非同期関数を並行して動かすことができません。

`Future` が指し示す非同期関数を直ちに実行開始するには、__`tokio::spawn()`__ に `Future` インスタンスを渡します。
次の例では、実行完了までに 3 秒かかる関数 (`process`) を、2 回連続して呼び出しています。

{{< code lang="rust" title="src/main.rc" >}}
use tokio::time::{sleep, Duration};

// 実行に 3 秒かかる非同期処理
async fn process(name: &str) -> String {
    println!("START: {}", name);
    sleep(Duration::from_secs(3)).await;
    println!("END: {}", name);
    format!("result from {}", name)
}

#[tokio::main]
async fn main() {
    // 2 つの Future を取得
    let future1 = process("process-1");
    let future2 = process("process-2");

    // Future が示す非同期処理を開始する
    let task1 = tokio::spawn(future1);
    let task2 = tokio::spawn(future2);

    // 非同期処理が完了するのを待つ
    let result1: String = task1.await.unwrap();
    println!("{}", result1);
    let result2: String = task2.await.unwrap();
    println!("{}", result2);
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cargo run -q
START: process-1
START: process-2
  （3 秒待つ）
END: process-2
END: process-1
result from process-1
result from process-2
{{< /code >}}

3 秒かかる 2 つの処理を同期実行したら 6 秒かかるところですが、ここでは `spawn()` で 2 つの非同期処理をほぼ同時に実行開始しているので、合計で 3 秒しかかかりません。
`spawn()` で開始した非同期処理が完了するのを待機するには、`spawn()` が返す __`JoinHandle`__ の `.await` を呼び出します。

2 つ以上の非同期処理がすべて完了するまで待機したいときは、`JoinHandle` の `.await` を個別に呼び出すのではなく、__`tokio::try_join!()`__ にすべての `JoinHandle` を渡します。
それぞれの非同期処理の結果はタプルとしてまとめて返されます。

```rust
// すべての非同期処理が完了するのを待つ
let results = tokio::try_join!(task1, task2).unwrap();
println!("{}, {}", results.0, results.1);
```

