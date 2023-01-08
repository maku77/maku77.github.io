---
title: "Rust の Result 型の基本 ─ 成功と失敗を表現する型"
url: "p/us2ahpw/"
date: "2022-12-29"
tags: ["Rust"]
---

Result 型とは？
----

Rust の標準ライブラリには、[std::result::Result](https://doc.rust-lang.org/std/result/enum.Result.html) という列挙型 (enum) が用意されており、何らかの処理が「成功」したこと、あるいは「失敗」したことを表現するために使われます（仕組み的には、値の「有無」を表現する [Option 型](/p/9m6m5m3/) と同様です）。
他の言語では、例外 (exception) の仕組みでエラーの発生を表現したりしますが、Rust では `Result` 型を使ってエラーを表現します（Rust には例外の仕組みが存在しません）。
`Result` 列挙型のバリアントとしては、次のように __`Ok`__ と __`Err`__ だけが定義されており、それぞれが何らかの処理の「成功」と「失敗」を表現します。

{{< code lang="rust" title="Result 型の定義" >}}
enum Result<T, E> {
   Ok(T),
   Err(E),
}
{{< /code >}}

成功と失敗を表すだけであれば、`bool` 型だけで表現できそうですが、`Result` 型は `Ok` と `Err` というバリアントが任意の値を持つことができるようになっているので、__成功した場合の結果や、失敗した場合の理由を表現することができます__。
また、何らかのメソッドが `Result` 型を返すとき、その値を呼び出し側で利用していないと、コンパイラが警告を出してくれるため、__エラーのハンドル忘れを防ぐ効果__ があります。
`Result` 型の概念は、他のモダンな言語でも採用されています（例: [Kotlin の Result 型](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/)）。

これらのシンボルはデフォルトでインポートされるようになっており、`Result`、`Ok`、`Err` と記述するだけで使用することができます（`Result::Ok` や `Result::Err` のように記述する必要はありません）。


Result を処理する
----

`Result` インスタンスから `Ok` バリアントや `Err` バリアントの情報を取り出すには、__`match`__ 式や __`if let`__ 式を使います。
以下のサンプルコードでは、`str::parse` メソッドで、数値を含む文字列をパースして `i32` 値に変換しています。
パースに成功すると `Ok` バリアントが返されるので、そこからパース結果を取り出すことができます。
パースに失敗すると `Err` バリアントが返されるので、そこからエラー情報（`ParseIntError` など）を取り出すことができます。

### match 式

`Result` 列挙型のバリアント（`Ok` と `Err`）を漏れなくハンドルしたいときは、`match` 式を使用します。

```rust
let result = "123".parse::<i32>();
match result {
    Ok(num) => println!("数値 {} としてパースできました", num),
    Err(err) => println!("パースできませんでした: {}", err),
}
```

### if let 式

処理の成功時に `Ok` バリアントが持つ値を取り出すだけでよければ、`if let` 構文を使うと簡潔に記述できます。

```rust
let result = "123".parse::<i32>();
if let Ok(num) = result {
    println!("数値 {} としてパースできました", num);
}
```

### is_ok / is_err メソッド

処理に成功したか、失敗したかだけを確認するには、__`is_ok`__ や __`is_err`__ メソッドを使用します。

```rust
if result.is_ok() {
    println!("成功しました");
}

if result.is_err() {
    println!("失敗しました");
}
```

### unwrap 系メソッド

`Ok` バリアントが含むデータをダイレクトに取り出したいときは、__`unwrap`__ 系のメソッドを使用します。
ただし、`unwrap` メソッドは `Result` の値が `Err` のときに呼び出すとパニックが発生するので、ほとんどのケースでは使用を避けるべきです。

```rust
let result = "123".parse::<i32>();

// 成功時は 123 を返し、失敗時はパニックを発生させる
let num = result.unwrap();

// unwrap の代わりに expect を使うと、パニック時のメッセージを指定できる
let num = result.expect("Failed to parse");
```

`unwrap` の代わりに __`unwrap_or`__ メソッドを使用すると、`Err` のときに使用する代替値を指定できます。
こちらはパニックが発生しないので安全です。

```rust
let num = result.unwrap_or(0); // 成功時は 123、失敗時は 0 が返される
let num = result.unwrap_or_default(); // これでも可（整数型のデフォルト値 0 が使われる）
```

代替値の生成にコストがかかる場合は、__`unwrap_or_else`__ メソッドを使って代替値を生成する関数を渡すようにします。
この関数は `Err` 時にしか呼び出されません。
次の例では、クロージャの形で代替値の生成処理を指定しています。

```rust
// 成功時は 123 を返し、失敗時は代替値生成用の関数を呼び出す
let num = result.unwrap_or_else(|_| create_default_value());
```


独自の Result 型を返すサンプル
----

下記の `divide` 関数は、整数値の割り算を行う関数です。
計算に成功すると、その結果 (`i32`) を含む `Result::Ok` を返し、計算に失敗すると、エラーメッセージ (`&str`) を含む `Result::Err` を返します。

```rust
fn divide(numerator: i32, denominator: i32) -> Result<i32, &'static str> {
    if denominator == 0 {
        return Err("0 で割ることはできません");
    }
    Ok(numerator / denominator)
}

match divide(5, 0) {
    Ok(num) => println!("割り算の結果: {}", num),
    Err(err) => println!("エラー発生: {}", err),
}
```

{{% note title="エラー型を定義するときの推奨方法" %}}
- Error トレイトを実装する
  - __`std::error::Error`__ トレイトを実装することで、共通のメソッドを使ってエラーを解析できるようになります。例えば、`Error#source` メソッドでエラーの発生源を調べることができます。
  - `source` の実装は、内部で保持している `Error` オブジェクトを返すだけで済みます。
- Display と Debug を実装する
  - これらを実装することで、クライアントがエラーの内容を出力できるようにしておきます（`Error` トレイトを実装するとき、自動的にこれらの実装も必要になります）。
  - `Display` の内容は、1 行で簡潔に「何が悪かったのか」分かるような表現にします。
他のレポートにネストされる形で表示されたりするので、基本的には __すべて小文字__ で、__末尾のピリオドは付けない__ ようにします。
  - `Debug` が提供する情報は、`Display` よりも具体的になるようにします。例えば、オープンに失敗したファイルの名前や、ポート番号などの情報を含めます。多くのケースでは、__`#[derive(Debug)]`__ を採用できます。
- Send と Sync を実装する
  - 可能であれば、スレッド境界を越えられるように `Send` と `Sync` を実装しておきます。エラー型がスレッドセーフになっていなければ、きっとそのクレートはマルチスレッドなコンテキストでは利用できません。
- static なライフタイムを持たせる
  - __`'static`__ にすることで、エラー情報を用意にコールスタックに乗せることができるようになります。
{{% /note %}}


Result 型でエラーのハンドル忘れを防ぐことができるのはなぜか？
----

`Result` 型の定義には、次のように __`#[must_use]`__ アノテーションが付いているため、何らかのメソッドが戻り値として `Result` を返したとき、その値を無視できないようになっています（参考: [Result 型のコード](https://doc.rust-lang.org/src/core/result.rs.html#501)）。

{{< code lang="rust" title="Result の実装（抜粋）" >}}
// ...
#[must_use = "this `Result` may be an `Err` variant, which should be handled"]
pub enum Result<T, E> {
    // ...
}
{{< /code >}}

何らかのメソッドが返した `Result` を、`match` や `if let` で「消費」することで、この警告は表示されなくなります。

- 参考: [The must_use attribute - The Rust Reference](https://doc.rust-lang.org/reference/attributes/diagnostics.html#the-must_use-attribute)

