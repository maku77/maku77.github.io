---
title: "Rust で JSON フォーマットを扱う (serde)"
url: "p/xdyk5o8/"
date: "2023-01-09"
lastmod: "2024-02-18"
tags: ["Rust"]
---

Serde とは？
----

Rust の __serde クレート__ は、Rust プログラム内で定義したユーザー型（struct や enum）を、JSON や YAML、BSON といった様々なデータ形式にシリアライズ／デシリアライズするためのライブラリです。
Serde という名前は、<strong>Ser</strong>ialize ＋ <strong>De</strong>serialize から来ています。
発音は、__すぁーでぃ__ です。

- [Serde website](https://serde.rs/)
- [serde API documentation](https://docs.rs/serde/latest/serde/)


Serde を使う準備
----

Serde を使うためには、ベースとなる __`serde`__ クレートに加えて、扱いたいデータフォーマット用のクレート（`Serializer` / `Deserialize` 実装）を依存関係に追加しておく必要があります。　
例えば、JSON であれば [__`serde_json`__](https://docs.rs/serde_json/latest/serde_json/)、YAML であれば [__`serde_yaml`__](https://docs.rs/serde_yaml/latest/serde_yaml/) です。

{{< code lang="console" title="serde 本体と各データフォーマット用の依存を追加" >}}
$ cargo add serde --features=derive
$ cargo add serde_json  # データフォーマットとして JSON を使う場合
{{< /code >}}

Serde の `derive` マクロを有効にするために、__`--features=derive`__ オプションを指定する必要があることに注意してください。
`Cargo.toml` に次のような依存関係が追加されていれば準備 OK です。

{{< code lang="toml" title="Cargo.toml" >}}
[dependencies]
serde = { version = "1.0.152", features = ["derive"] }
serde_json = "1.0.91"
{{< /code >}}


基本的な使い方 (to_string, from_str)
----

次のサンプルコードでは、ユーザー定義の構造体 (`Book`) のインスタンスから JSON 文字列への変換（シリアライズ）と、その逆の、JSON 文字列から構造体インスタンスへの変換（デシリアライズ）を行っています。

注: コードのシンプル化ため、ここでは `Result#unwrap` メソッドを使っていますが、プロダクトコードでは正しく `Result` を処理してください（参考: [Result の基本](/p/us2ahpw/)）。

{{< code lang="rust" title="src/main.rs" >}}
use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize)]
struct Book {
    id: i32,
    title: String,
}

fn main() {
    // シリアライズ (構造体 → JSON 文字列)
    // 整形したいときは to_string の代わりに to_string_pretty を使えば OK
    let book = Book { id: 1, title: String::from("Title-1") };
    let json = serde_json::to_string(&book).unwrap();
    println!("{}", json); //=> {"id":1,"title":"Title-1"}

    // デシリアライズ (JSON 文字列 → 構造体)
    let json = r#"{"id":2, "title":"Title-2"}"#;
    let book: Book = serde_json::from_str(json).unwrap();
    println!("{}, {}", book.id, book.title); //=> 2, Title-2
}
{{< /code >}}

ユーザー定義型（`struct` や `enum`）を、任意の `Serializer` / `Deserializer` 実装（`serde_json` など）で変換するには、その型に [Serialize トレイト](https://docs.rs/serde/latest/serde/trait.Serialize.html) および [Desrialize トレイト](https://docs.rs/serde/latest/serde/trait.Deserialize.html) を実装しておく必要があります。
これは、ユーザー定義型を、[Serde が処理できる汎用的なデータモデル](https://serde.rs/data-model.html#types) に変換するための実装ですが、シンプルな構成の型であれば、上記のように __`#[derive(Serialize, Deserialize)]`__ 属性を付加するだけで、デフォルト実装を提供してくれます。

{{% note title="コンパイル時のコード生成" %}}
Serde が提供する derive マクロにより、`Serialize` と `Deserialize` の実装がコンパイル時に自動生成されます。
JSON ライブラリの設計によっては、アプリケーションの実行時にデータ型をリフレクションで処理するという方法も考えられますが、Serde は変換用の実装コードをコンパイル時に生成するという設計を採用しています。
これにより、実行時に高速かつ安全に動作することを保証しています。
{{% /note %}}

必要に応じて、`serde::ser::Serialize` トレイトを実装することで、[独自のシリアライズ処理](https://serde.rs/impl-serialize.html) を提供することができます。


JSON ファイルへの保存と読み込み (to_writer, from_reader)
----

`serde_json` クレートは、`io::Write` への書き込みを行う [to_writer](https://docs.rs/serde_json/latest/serde_json/fn.to_writer.html) / [to_writer_pretty](https://docs.rs/serde_json/latest/serde_json/fn.to_writer_pretty.html) 関数や、`io::Read` からの読み込みを行う [from_reader](https://docs.rs/serde_json/latest/serde_json/fn.from_reader.html) 関数を提供しています。
これらの関数を利用して、ファイルやネットワークストリームに対して読み書きを行えます。

### JSON ファイルへの保存

{{< code lang="rust" title="src/main.rs" hl_lines="12" >}}
use std::{error::Error, fs::File, path::Path};
use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize)]
struct Book {
    id: i32,
    title: String,
}

fn save_book_to_json_file<P: AsRef<Path>>(path: P, book: &Book) -> Result<(), Box<dyn Error>> {
    let file = File::create(path)?; // std::io::Error の可能性
    serde_json::to_writer_pretty(file, book)?; // serde_json::Error の可能性
    Ok(())
}

fn main() {
    let book = Book { id: 1, title: String::from("Title-1") };
    match save_book_to_json_file("book.json", &book) {
        Ok(_) => println!("JSON ファイルへの保存に成功しました"),
        Err(err) => eprintln!("JSON ファイルへの保存に失敗しました: {}", err),
    }
}
{{< /code >}}

### JSON ファイルの読み込み

{{< code lang="rust" title="src/main.ts" hl_lines="13" >}}
use std::{error::Error, fs::File, io::BufReader, path::Path};
use serde::{Deserialize, Serialize};

#[derive(Serialize, Deserialize)]
struct Book {
    id: i32,
    title: String,
}

fn load_book_from_json_file<P: AsRef<Path>>(path: P) -> Result<Book, Box<dyn Error>> {
    let file = File::open(path)?; // std::io::Error の可能性
    let reader = BufReader::new(file); // 読み込み時は明示的にバッファリング
    let book = serde_json::from_reader(reader)?; // serde_json::Error の可能性
    Ok(book)
}

fn main() {
    match load_book_from_json_file("book.json") {
        Ok(book) => {
            println!("JSON ファイルの読み込みに成功しました");
            println!("内容: {}, {}", book.id, book.title);
        }
        Err(err) => eprintln!("JSON ファイルの読み込みに失敗しました: {}", err),
    }
}
{{< /code >}}


フィールド名を変更する
----

デフォルトでは JSON フィールド名は、Rust の構造体のフィールド名がそのまま使われますが、構造体の定義に __`#[serde(rename_all)]`__ 属性を付けると、対応付ける JSON フィールド名のルールをまとめて変更できます。

{{< code lang="rust" title="JSON フィールド名を camelCase 形式にする" hl_lines="2" >}}
#[derive(Serialize, Deserialize)]
#[serde(rename_all = "camelCase")]
struct User {
    avatar_url: String,  // JSON フィールド名は "avatarUrl" になる
    website_url: String, // JSON フィールド名は "websiteUrl" になる
}
{{< /code >}}

`rename_all` の値として、他にも次のようなルールを指定できます。

- `"lowercase"`
- `"UPPERCASE"`
- `"PascalCase"`
- `"camelCase"`
- `"snake_case"`
- `"SCREAMING_SNAKE_CASE"`
- `"kebab-case"`
- `"SCREAMING-KEBAB-CASE"`

構造体のフィールドに __`#[serde(rename = "別名")]`__ 属性を付けると、各フィールドを指定した名前でリネームすることができます。

{{< code lang="rust" title="JSON フィールド名を具体的に指定する" hl_lines="3" >}}
#[derive(Serialize, Deserialize)]
struct User {
    #[serde(rename = "id")]
    user_id: String,
    // ...
}
{{< /code >}}

デシリアライズ時に、別の JSON フィールド名でも読み込めるようにするには、__`#[serde(alias = "name")]`__ で別名を指定します。
これは、JSON へのシリアライズには影響しないことに注意してください。
別名は複数指定することができます。
この機能は恒久的には使うべきではないかもしれませんが、JSON ファイルのフォーマットを段階的に移行したいときに便利です。

{{< code lang="rust" title="JSON フィールド名が異なっていても読み込む" hl_lines="3" >}}
#[derive(Serialize, Deserialize)]
struct User {
    #[serde(alias = "id", alias = "ID", alias = "userId")]
    user_id: String,
    // ...
}
{{< /code >}}

参考: [Attributes · Serde](https://serde.rs/attributes.html)


未知の JSON フィールドが見つかったらエラーにする
----

デシリアライズしようとしている JSON データに、未知のフィールドが含まれているとき（Rust のユーザー定義型に対応するフィールドがないとき）、デフォルトではそのフィールドの値は無視されます。
つまり、構造体のインスタンスの生成は問題なく実行されます。

JSON データに未知のフィールドが含まれているときにエラーにしたい場合は、ユーザー定義型に __`deny_unknown_fields`__ 属性 (container attribute) を付加します。

{{< code lang="rust" hl_lines="2" >}}
#[derive(Serialize, Deserialize)]
#[serde(deny_unknown_fields)]
struct Book {
    // ...
}
{{< /code >}}


Nullable や存在しない JSON フィールドを扱う
----

### JSON の null 値を扱う

Rust には `null` という概念は存在しませんが、値が存在しないかもしれないフィールドは [Option 列挙型で表現できます](/p/9m6m5m3/)。
例えば、JSON データの `title` フィールドの値として `null` が含まれている可能性がある場合は、対応する構造体の `title` フィールドを次のように `Option` 型にします。

{{< code lang="rust" hl_lines="4" >}}
#[derive(Serialize, Deserialize)]
struct Book {
    id: i32,
    title: Option<String>,
}
{{< /code >}}

これで、次のような `null` 値を含む JSON ファイルを読み込めます。
Rust 側で値を参照すると `Option::None` という値として参照できます。

{{< code lang="json" title="book.json（title フィールドの値が null）" >}}
{
  "id": 1,
  "title": null
}
{{< /code >}}

### JSON にフィールドが存在しないとき

次のように、JSON データに対象のフィールド自体が存在しない場合も、`Option` 型でハンドルできます。
この場合も、`Option::None` という値が格納されます。

{{< code lang="json" title="book.json（title フィールドが存在しない）" >}}
{
  "id": 1
}
{{< /code >}}

構造体のフィールドを `Option` 型にする代わりに、__`#[serde(default)]`__ 属性を付けて、その型のデフォルト値を入れることもできます。
次のように定義すると、JSON データに対応するフィールドが存在しないときに、`String` 型のデフォルト値である空文字列 (`""`) が格納されます（`#[serde(default = "func_name")]` として、任意のデフォルト値生成関数を呼び出すこともできます）。

{{< code lang="rust" hl_lines="4" >}}
#[derive(Serialize, Deserialize)]
struct Book {
    id: i32,
    #[serde(default)]
    title: String,
}
{{< /code >}}

ただし、これは、JSON にフィールドが存在しない場合のみ機能するもので、値として `null` が含まれている場合はエラーになることに注意してください（その場合は `Option` 型を使う必要があります）。

参考: [Default value for a field · Serde](https://serde.rs/attr-default.html)


シリアライズ／デシリアライズの対象外にする
----

特定の構造体フィールドを Serde のシリアライズ／デシリアライズの対象外にするには、次のような属性をフィールドに付加します。

- __`#[serde(skip)]`__ ... シリアライズとデシリアライズの対象外にする。
- __`#[serde(skip_serializing)]`__ シリアライズの対象外にする。
- __`#[serde(skip_deserializing)]`__ デシリアライズの対象外にする。

デシリアライズの対象外になっている構造体フィールドは、`Default::default()` が返すデフォルト値で初期化されます。
もし、__`#[serde(default = "func_name")]`__ という属性値がセットされている場合は、指定した関数がデフォルト値の生成のために呼び出されます。

他にも、属性を使って次のように細かな制御を行うことができます。

```rust
// Option 値が None のときは JSON フィールドを出力しない
#[serde(skip_serializing_if = "Option::is_none")]
comment: Option<String>,

// 文字列が空の場合は JSON のフィールドを出力しない、ただし、読み込み時は空文字列として初期化する
#[serde(default, skip_serializing_if = "String::is_empty")]
serial: String,

// ベクターが空の場合は JSON のフィールドを出力しない、ただし、読み込み時は空のベクターとして初期化する
#[serde(default, skip_serializing_if = "Vec::is_empty")]
authors: Vec<String>,

// マップが空の場合は JSON のフィールドを出力しない、ただし、読み込み時は空のマップとして初期化する
#[serde(default, skip_serializing_if = "HashMap::is_empty")]
attributes: HashMap<String, String>,
```


{{% private %}}
- シリアライズ時にフィールド名でソートする
{{% /private %}}


形式の不明な JSON ファイルを読み込む (serde_json::Value)
----

どのようなフィールドが含まれているかわからない JSON ファイルを読み込む場合は、任意の JSON データ型を示す [`serde_json::Value`](https://docs.rs/serde_json/latest/serde_json/value/enum.Value.html) として読み込みます。
`serde_json::Value` は次のような定義の列挙型 (`enum`) で、JSON で表現できるデータ型がバリアントとして定義されています。

```rust
pub enum Value {
    Null,                        // null
    Bool(bool),                  // bool
    Number(Number),              // 数値
    String(String),              // 文字列
    Array(Vec<Value>),           // 配列
    Object(Map<String, Value>),  // オブジェクト
}
```

ここでは、次のような JSON ファイルを読み込んでみます。
「オブジェクトの配列」の形になっているということまでは分かっているものとします。

{{< code lang="json" title="games.json" >}}
[
  { "title": "Final Fantasy", "genre": "RPG" },
  { "title": "Sqoon", "genre": "STG", "price": 4900 },
  { "title": "Gimmick", "note": "プレミア価格" }
]
{{< /code >}}

次の例では、`games.json` を `Value` 型として読み込み、その内容を表示しています。

{{< code lang="rust" title="src/main.rs" >}}
use serde_json::Value;
use std::fs::File;
use std::io::BufReader;

// games.json を読み込んで Value 型で返す
fn load_games() -> Value {
    let file = File::open("games.json").expect("ファイルが開けませんでした。");
    let reader = BufReader::new(file);
    serde_json::from_reader(reader).expect("JSON の解析に失敗しました。")
}

fn main() {
    let games_json: Value = load_games();

    // JSON 全体は配列なので Value::Array として参照する
    if let Value::Array(games) = &games_json {
        for game in games {
            // 個々の要素は Value::Object として取り出す
            for (key, value) in game.as_object().unwrap() {
                println!("{}: {}", key, value);
            }
            println!("--------------------");
        }
    } else {
        println!("JSON のルート要素は配列でなければいけません。");
    }
}
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ cargo run -q
genre: "RPG"
title: "Final Fantasy"
--------------------
genre: "STG"
price: 4900
title: "Sqoon"
--------------------
note: "プレミア価格"
title: "Gimmick"
--------------------
{{< /code >}}

まず、下記の行で `games.json` ファイル全体を汎用的な `Value` 型として読み出しています。

```rust
let games_json: Value = load_games();
```

`Value` は列挙型なので、その内容を参照するには、`if let` 構文でどのバリアントなのかを判別してから参照する必要があります（参考: [enum 型の使い方](/p/ffqyajs/)）。
今回の `games.json` は配列形式で記述されていると想定し、次のようにして `Value::Array` バリアント（内容は `Vec<Value>` 型）として参照しています。

```rust
if let Value::Array(games) = &games_json {
    // ... games を Vec<Value> 型として参照できる ...
}
```

`Value::Array` バリアントとして取り出した `games` は `Vec<Value>` 型なので、`for-in` ループで列挙することができます。
そして、`games` の中の個々の要素 `game` はオブジェクト形式なので、`Value::Object` バリアントとして参照することができます。
下記のコードでは、__`as_object()`__ を使って `Value::Object` バリアントとして取り出していますが、ここでも `if let` を使って `Value::Object` バリアントかどうかを判別するのでも OK です。

```rust
for game in games {
    for (key, value) in game.as_object().unwrap() {
        println!("{}: {}", key, value);
    }
}
```

このようにすれば、どんなフィールドを持っているか不明な JSON ファイルを処理できますが、できればデータ型をちゃんと定義して扱いたいですね。

