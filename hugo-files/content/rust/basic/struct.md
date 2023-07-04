---
title: "Rust の文法: 構造体 (struct) とタプル構造体 (tuple struct)"
url: "p/h8kw8ju/"
date: "2023-02-13"
tags: ["Rust"]
---

構造体の基本
----

Rust の構造体の定義とインスタンス化は直感的です。
構造体を定義するときは __`struct 構造体名 { ... }`__、定義済みの構造体をインスタンス化するときは __`構造体名 { ... }`__ という構文を使用します。
先頭に `struct` というキーワードが付くかどうかの違いしかないので、どちらの処理を行っているのか気を付けて読み取ってください。

```rust
// 構造体の定義（フィールド名と型を指定していく）
struct User {
    name: String,
    email: String,
    active: bool,
    sign_in_count: u64,
}

// インスタンス化（フィ―ルド名と初期値を指定していく）
let user = User {
    email: String::from("maku@example.com"),
    name: String::from("maku"),
    active: true,
    sign_in_count: 1,
};

// フィールドの参照にはドットを使う
println!("{}", user.name);
```

構造体のフィールドとして文字列を持ちたいときは、`&str`（文字列スライス）ではなく `String` 型を使うことで、構造体インスタンス自身が文字列の所有者になることができます（参考: [所有権と借用](/p/4nx8hqy/)）。

構造体インスタンスの参照（借用）経由でフィールドにアクセスする場合もドットが使えます。

```rust
let user_ref = &user;  // 借用 (borrow) して参照 (&User) を作成
println!("{}", user_ref.name);  // 参照のフィールドもドットでアクセスする
```


可変な構造体 (mutable struct)
----

インスタンス生成時に `let` の代わりに `let mut` を使うと、可変 (mutable) なインスタンスとなり、すべてのフィールドが再代入可能になります（一部のフィールドだけを再代入可能にすることはできません）。

```rust
let mut user = User {
    // ...
};

// 各フィールドに別の値を代入可能
user.name = String::from("puni");
user.active = false;
```

次のように、構造体インスタンスを丸ごと入れ替えることもできます。

```rust
user = User {
    email: String::from("puni@example.com"),
    name: String::from("puni"),
    active: false,
    sign_in_count: 777,
};
```


フィールド初期化の省略記法
----

フィールド初期化時に、フィールド名と初期値とする変数名が同じ場合、フィールド名だけを記述する省略記法を使えます。

```rust
fn build_user(email: String, name: String) -> User {
    User {
        email,  // email: email と書くのと同じ
        name,   // name: name と書くのと同じ
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
[通常のタプル](/p/7r3cmv6/#tuple) に名前を付けたようなものと考えればよいです。
通常のタプルと同様に、各フィールドの値は、__`.0`__、__`.1`__ のようなインデックスで参照します。

```rust
// Point という名前のタプル構造体を定義する
struct Point(i32, i32);

// 基本的な使い方
let p = Point(100, 200);
println!("{}, {}", p.0, p.1);  //=> 100, 200
```

通常のタプルと同様に、各フィールドの値を別々の変数に展開することができます。

```rust
// 各フィールドの値を別々の変数（x と y）に展開
let Point(x, y) = p;
println!("{x}, {y}");  //=> 100, 200
```


構造体のデバッグ出力
----

構造体を定義するときに __`#[derive(Debug)]`__ という属性を付けておくと（あるいは [Debug トレイト](https://doc.rust-lang.org/std/fmt/trait.Debug.html) を自力で実装しておくと）、`println!` マクロで、__`{:?}`__ により構造体の内容をデバッグ出力できるようになります（複数行で整形して出力したいときは `{:?}` の代わりに __`{:#?}`__ を使用します）。

```rust
#[derive(Debug)]
struct Point {
    x: i32,
    y: i32,
}

let p = Point { x: 100, y: 200 };
println!("{:?}", p);
println!("{:#?}", p);
```

{{< code lang="rust" title="出力結果" >}}
Point { x: 100, y: 200 }
Point {
    x: 100,
    y: 200,
}
{{< /code >}}


構造体のメソッドを実装する
----

構造体のメソッドは、__`impl`__ ブロック内で定義します。
メソッドの第 1 引数は、構造体インスタンスの参照を示す `&self` になります（Python の文法と似ています）。
構造体の型は明らかなので、`&self` に型を指定する必要はありません。

```rust
struct Rect {
    width: u32,
    height: u32,
}

// Rect のメソッドを実装する
impl Rect {
    fn area(&self) -> u32 {
        self.width * self.height
    }
}

let r = Rect { width: 5, height: 2 };
println!("{}", r.area()); //=> 10
```

構造体のフィールドを変更したい場合は、メソッドの第 1 引数を __`&mut self`__ にします。
このメソッドを呼び出す場合は、構造体のインスタンスを生成するときに `let mut` を使わなければいけないことに注意してください。

```rust
impl Rect {
    // 自身のフィールドの内容を変更するメソッド
    fn double(&mut self) {
        self.width *= 2;
        self.height *= 2;
    }
}

let mut r = Rect { width: 5, height: 2 };
r.double();
```


関連関数 (associated function)
----

`impl` ブロック内で `&self`（や `&mut self`）を第 1 引数に取らない関数を定義すると、その構造体の __関連関数 (associated function)__ として扱われます。
他の言語では static メソッドと呼ばれているもので、呼び出し時にインスタンスを必要としません。
関連関数を呼び出すには __`構造体名::関数名`__ という構文を使用します（C++ の static メソッドと同様です）。

{{< code lang="rust" title="関連関数としてファクトリ関数を定義する" >}}
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
{{< /code >}}

上の例では `origin` 関数の戻り値の型を `Point` としていますが、次のように __`Self`__ というキーワードを使うことで、`impl` 対象の構造体の型が指定されたものとみなされます。

```rust
impl Point {
    fn origin() -> Self {
        Point { x: 0.0, y: 0.0 }
    }
}
```

