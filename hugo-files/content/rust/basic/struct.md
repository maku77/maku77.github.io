---
title: "Rust の文法: 構造体 (struct) を定義する"
url: "p/h8kw8ju/"
date: "2023-02-13"
tags: ["Rust"]
---

構造体の基本
----

Rust の構造体の定義とインスタンス化は直感的です。
文字列フィールドの型としては、`&str`（文字列スライス）ではなく `String` を使うことで、構造体インスタンス自身が文字列の所有者になることができます。

```rust
// 構造体の定義
struct User {
    name: String,
    email: String,
    sign_in_count: u64,
    active: bool,
}

// インスタンス化
let user = User {
    email: String::from("maku@example.com"),
    name: String::from("maku"),
    active: true,
    sign_in_count: 1,
};

// フィールドの参照にはドットを使う
println!("{}", user.name);
```

構造体インスタンスの参照（借用）経由でフィールドにアクセスする場合もドットが使えます。

```rust
let user_ref = &user;  // 借用 (borrow) して参照を作成
println!("{}", user_ref.name);  // 参照のフィールドもドットでアクセスする
```


可変な構造体
----

インスタンス生成時に `let` の代わりに `let mut` を使うと、可変 (mutable) なインスタンスとなり、すべてのフィールドに再代入可能になります（一部のフィールドだけを再代入可能にすることはできません）。

```rust
let mut user = User {
    // ...
};

// 各フィールドに別の値を代入可能
user.name = String::from("puni");
user.active = false;
```


フィールド初期化の省略記法
----

フィールド初期化時に、フィールド名と初期値とする変数名が同じ場合、フィールド名だけを記述する省略記法を使えます。

```rust
fn build_user(email: String, name: String) -> User {
    User {
        email,  // email: email の省略記法
        name,   // name: name の省略記法
        active: true,
        sign_in_count: 1,
    }
}
```

このような省略記法は TypeScript (JavaScript) でも採用されています。


部分的に異なるインスタンスを生成する
----

既存の構造体インスタンスがあるときに、一部のフィールドの値だけが異なる別のインスタンスを生成したいときは、次のように __`..`__ を使って残りのフィールドをコピーします。

{{< code lang="rust" hl_lines="11" >}}
let user1 = User {
    email: String::from("maku@example.com"),
    name: String::from("maku"),
    active: true,
    sign_in_count: 1,
};

let user2 = User {
    email: String::from("puni@example.com"),
    name: String::from("puni"),
    ..user1
};
{{< /code >}}

`..user1` の後ろにはカンマ (`,`) を付けないことに注意してください。
この構文もスプレッド演算子として TypeScript (JavaScript) に採用されています。


タプル構造体 (tuple struct)
----

構造体のフィールド名を省略した、__タプル構造体 (tuple struct)__ を定義することができます。
タプルに名前を付けたようなものと考えればよいです。
通常のタプルと同様、各フィールドの値は、`.0`、`.1` のようなインデックスで参照します。

```rust
// タプル構造体を定義する
struct Point(i32, i32);

// 基本的な使い方
let p = Point(100, 200);
println!("{}, {}", p.0, p.1);  //=> 100, 200

// 各フィールドの値を別々の変数に展開
let Point(x, y) = p;
println!("{x}, {y}");  //=> 100, 200
```


デバッグ出力
----

構造体を定義するときに __`#[derive(Debug)]`__ という属性を付けておくと（あるいは [Debug トレイト](https://doc.rust-lang.org/std/fmt/trait.Debug.html) を自力で実装しておくと）、`println!` マクロで、__`{:?}`__ により構造体の内容をデバッグ出力できるようになります（複数行で整形して出力したいときは `{:?}` の代わりに __`{:#?}`__ を使用します）。

```rust
#[derive(Debug)]
struct Point {
    x: i32,
    y: i32,
}

let p = Point { x: 100, y: 200 };
println!("{:?}", p);  //=> "Point { x: 100, y: 200 }"
```


メソッドの定義
----

構造体のメソッドは、__`impl`__ ブロック内で定義します。
メソッドの第 1 引数は、構造体インスタンスの参照を示す `&self` になります（Python の文法と似ています）。
構造体の型は明らかなので、`&self` に型を指定する必要はありません。

```rust
struct Rect {
    width: u32,
    height: u32,
}

// impl ブロック内で Rect のメソッドを定義する
impl Rect {
    fn area(&self) -> u32 {
        self.width * self.height
    }
}

let r = Rect { width: 5, height: 2 };
println!("{}", r.area()); //=> 10
```

構造体のフィールドを変更したい場合は、メソッドの第 1 引数を __`&mut self`__ にします。


関連関数 (associated function)
----

`impl` ブロック内で `&self`（や `&mut self`）を第 1 引数に取らない関数を定義すると、その構造体の __関連関数 (associated function)__ として扱われます。
他の言語では static メソッドと呼ばれているもので、呼び出し時にインスタンスを必要としません。
関連関数を呼び出すには __`構造体名::関数名`__ という構文を使用します（C++ の static メソッドと同様です）。

```rust
struct Point {
    x: f64,
    y: f64,
}

impl Point {
    // 関連関数 (静的メソッド）の定義
    fn origin() -> Point {
        Point { x: 0.0, y: 0.0 }
    }
}

// 関連関数を呼び出す
let p = Point::origin();
```

