---
title: "Rust で列挙型 (enum) を定義して match、if let で照合する"
url: "p/ffqyajs/"
date: "2023-01-06"
tags: ["Rust"]
---

列挙型は、あらかじ定義された値（列挙子やバリアントと呼びます）の内、いずれかの値をとることができる型です。
Rust の列挙型は、各バリアントに任意の値を保持することができるので非常に強力です。
列挙型は構造体 (struct) と同様に、`impl` ブロックを使ったメソッド定義を行えます。


列挙型の基本
----

列挙型は __`enum`__ キーワードを使って定義します。
列挙型がとりうる値のことを __バリアント (variant)__ と呼びます。
次の `Fruit` 列挙型は、バリアントとして `Apple`、`Banana`、`Orange` を持ちます。

{{< code lang="rust" title="enum 型を定義する" >}}
// Fruit 列挙型を定義する
#[derive(Debug)]
enum Fruit {
    Apple,
    Banana,
    Orange,
}

// Fruit インスタンスを生成する
let f = Fruit::Apple;
println!("{:?}", f);       //=> Apple
println!("{}", f as i32);  //=> 0
{{< /code >}}

上記のように列挙型の定義時に `#[derive(Debug)]` 属性を付けておくと、`println!` マクロで列挙型変数の値を `{:?}` で参照できるようになります。
あるいは、`as i32` を使って、バリアントが持つ [内部的な整数値を取り出す](#extract_value) こともできます。


match による分岐
----

__`match`__ 構文によりパターンマッチにより、列挙型変数の値に基づいて分岐処理を行うことができます。
次の例では 3 つのパターンで分岐していますが、これらのパターンごとの記述を Rust では __アーム (arm)__ と呼びます。
各アームはカンマ (__`,`__) で区切って記述します。

{{< code lang="rust" title="match による分岐" >}}
let f = Fruit::Banana;

match f {
    Fruit::Apple => println!("I like apples"),
    Fruit::Banana => println!("I like bananas"),
    Fruit::Orange => println!("I like oranges"),
}
{{< /code >}}

Rust の __`match` は式として扱われる__ ので、評価結果を変数などで受け取ることができます。

```rust
let message = match f {
    Fruit::Apple => "I like apples",
    Fruit::Banana => "I like bananas",
    Fruit::Orange => "I like oranges",
};
println!("{}", message);
```

ファットアロー (`=>`) の右側に複数の文を並べたいときは、全体を `{}` で囲んでブロック化します。
閉じ括弧の後ろにカンマを付ける必要はありません。

{{< code lang="rust" hl_lines="2-5" >}}
match f {
    Fruit::Apple => {
        let msg = "I like apples";
        println!("{}", msg);
    }
    Fruit::Banana => println!("I like bananas"),
    Fruit::Orange => println!("I like oranges"),
}
{{< /code >}}


match で複数のバリアントや残り全てにマッチさせる
----

`match` ブロックの中で、複数のバリアントで共通の処理を行いたい場合は、バリアントをパイプ記号 (__`|`__) で列挙します。
また、「その他のバリアントすべて」にマッチさせたいときは、アンダースコア (__`_`__) 記号を使います（これは、C/C++ や Java の `default` ケースに相当するものです）。
アンダースコアはすべての値に一致してしまうため、最後のアームとして配置する必要があります。

{{< code lang="rust" hl_lines="6 7" >}}
// トランプのスート（マーク）を表現する列挙型
enum Suit { Clubs, Spades, Diamonds, Hearts }

let s = Suit::Clubs;
match s {
    Suit::Clubs | Suit::Spades => println!("Its color is black"),
    _ => println!("Its color is red"),
}
{{< /code >}}


未使用バリアントの警告を抑制する
----

アプリケーションの中で、使用していないバリアントがあると、Rust コンパイラーは次のような警告を出します。

```
warning: variants `Apple` and `Orange` are never constructed
```

これは、デフォルトで `#[warn(dead_code)]` 属性が設定されており、未使用コードが警告されるようになっているからです。
未使用コードの警告を抑制するには、列挙型の定義時に、__`#[allow(dead_code)]`__ 属性を追加します。

{{< code lang="rust" hl_lines="1" >}}
#[allow(dead_code)]
enum Fruit { Apple, Banana, Orange }

// Banana しか使っていないけど警告は出なくなる
let f = Fruit::Banana;
{{< /code >}}


バリアントに値を持たせる
----

enum 型の各バリアントは、任意の型のデータを保持することができます。
しかも __バリアントごとに異なる型のデータを持つことができる__ ので、C/C++ の union（共用体）のような感覚で使用することができます。

```rust
enum Message {
    Quit,                       // データを持たない
    Move { x: i32, y: i32 },    // 無名の構造体データを持つ
    Write(String),              // 1 つの String を持つ（タプル構造体）
    ChangeColor(i32, i32, i32), // 3 つの整数値を持つ（タプル構造体）
}

let m = Message::Quit;
let m = Message::Move { x: 5, y: 2 };
let m = Message::Write(String::from("Hello"));
let m = Message::ChangeColor(255, 255, 0);
```

`match` による分岐時に、各バリアントに格納されているデータを取り出すことができます。

```rust
match m {
    Message::Quit => println!("Quit"),
    Message::Move { x, y } => println!("Move to: {}, {}", x, y),
    Message::Write(s) => println!("Write: {}", s),
    Message::ChangeColor(r, g, b) => println!("Change color to: {}, {}, {}", r, g, b),
}
```

上記の例では、各バリアントが持つデータを変数で取り出していますが、変数の代わりにリテラル値を指定すると、そのリテラル値に一致するデータが格納されている場合の処理を記述することができます。
また、格納されているデータを使用しない場合は、変数名の代わりにアンダースコア (`_`) を指定します。

```rust
match m {
    Message::Move { x: 0, y: 0 } => println!("Move to the origin"),
    Message::Move { x, y } => println!("Move to {}, {}", x, y),
    Message::Write(_) => println!("Something written"),
    Message::ChangeColor(r, _, _) => println!("Red value is: {}", r),
    _ => (), // Nothing to do
}
```


列挙値を if let で処理する
----

列挙型のすべてのバリアントを包括的に処理する必要がなく、特定のバリアントのみ興味がある場合は、`match` の代わりに __`if let`__ 構文でパターンマッチを行うことができます。
マッチングのルールは `match` と同様です。
次の例では、`Color` 列挙型の変数の値が赤色を示す値かどうかを調べています。

```rust
#[allow(dead_code)]
enum Color {
    Red,
    Blue,
    Rgb(u8, u8, u8),             // タプルデータを持つ
    Hsv { h: u8, s: u8, v: u8 }, // 構造体データを持つ
}

let c = Color::Red;
if let Color::Red = c {
    println!("赤色です");
}

let c = Color::Rgb(255, 0, 0);
if let Color::Rgb(255, 0, 0) = c {
    println!("これも赤色です");
}

let c = Color::Hsv { h: 0, s: 100, v: 100 };
if let Color::Hsv { h: 0, s: 100, v: 100 } = c {
    println!("またまたこれも赤色です");
}
```

`Color::Rgb` や `Color::Hsv` のように、データを持つバリアントの場合は、`if let` で分岐処理するときに同時にそのデータを取り出すことができます。
このあたりの振る舞いも、`match` によるパターンマッチと同様です。

```rust
let c = Color::Rgb(255, 0, 0);
if let Color::Rgb(r, g, b) = c {
    println!("r={}, g={}, b={}", r, g, b);
}

let c = Color::Hsv { h: 0, s: 100, v: 100 };
if let Color::Hsv { h, s, v } = c {
    println!("h={}, s={}, v={}", h, s, v);
}
```

バリアントが持つデータのうち、参照する必要がないフィールドがある場合は、変数名の代わりにアンダースコア (`_`) を配置しておきます。

```rust
let c = Color::Rgb(255, 255, 0);
if let Color::Rgb(255, g, _) = c {
    // タプルの最初の値が 255 の場合のみ、2 番目の値を変数 g で取り出す
    println!("g={}", g);
}

let c = Color::Hsv { h: 0, s: 100, v: 100 };
if let Color::Hsv { h: 0, s, v: _ } = c {
    // フィールド h の値が 0 の場合のみ、フィールド s の値を取り出す
    println!("s={}", s);
}
```


列挙型にメソッドを定義する
----

列挙型にも [構造体 (struct)](/p/h8kw8ju/) と同様に、メソッドを定義することができます。
次の例では、`WebEvent` 列挙型に `dump` メソッドを定義しています。
メソッドの中では、自身の列挙型名 (`WebEvent`) の代わりに __`Self`__ というエイリアスを使用できます。

{{< code lang="rust" title="enum 型にメソッドを追加する" >}}
enum WebEvent {
    PageLoad,
    KeyPress(char),
    Click { x: u32, y: u32 },
}

impl WebEvent {
    fn dump(&self) {
        match self {
            Self::PageLoad => println!("page loaded"),
            Self::KeyPress(c) => println!("pressed '{}'", c),
            Self::Click { x, y } => println!("clicked at x={}, y={}", x, y),
        }
    }
}

let e = WebEvent::Click { x: 5, y: 7 };
e.dump();  //=> "clicked at x=5, y=7"
{{< /code >}}

パラメーターのルールも構造体と同様で、第 1 引数に `&self` があれば、呼び出し時にインスタンスが必要な「メソッド」となり、`&self` がなければ、呼び出し時にインスタンスが必要ない「関連関数」となります。
それ以降のパラメーターは、メソッド（または関連関数）の呼び出し時に渡した引数を受け取るために使用します。

{{< code lang="rust" title="パラメーター有りのメソッドを追加する" >}}
enum Operation {
    Add,
    Subtract,
}

impl Operation {
    /// バリアントに応じた演算を行う
    fn run(&self, a: i32, b: i32) -> i32 {
        return match self {
            Self::Add => a + b,
            Self::Subtract => a - b,
        };
    }
}

let op = Operation::Add;
println!("{}", op.run(1, 2)); //=> 3
{{< /code >}}


バリアントが内部的に持つ整数値を取り出す {#extract_value}
----

C/C++ と同様に、Rust の列挙型のバリアントも、内部的には 0 始まりの整数値で管理されています。
この値は、__`as i32`__ で取り出すことができます。

```rust
enum Color {
    Red,
    Green,
    Blue,
}

println!("{}", Color::Red as i32);   //=> 0
println!("{}", Color::Green as i32); //=> 1
println!("{}", Color::Blue as i32);  //=> 2

let red = Color::Red;
let green = Color::Green;
let blue = Color::Blue;

println!("{}", red as i32);   //=> 0
println!("{}", green as i32); //=> 1
println!("{}", blue as i32);  //=> 2
```

C/C++ と同様、この整数値は任意の値にオーバーライドすることができます。

```rust
enum Color {
    Red = 0xff0000,
    Green = 0x00ff00,
    Blue = 0x0000ff,
}

println!("{:08x}", Color::Red as i32);   //=> 00ff0000
println!("{:08x}", Color::Green as i32); //=> 0000ff00
println!("{:08x}", Color::Blue as i32);  //=> 000000ff
```

