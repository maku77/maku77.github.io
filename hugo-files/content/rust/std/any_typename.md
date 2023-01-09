---
title: "Rust で型の名前を取得する (std::any::type_name)"
url: "p/m9vdtaq/"
date: "2022-12-29"
tags: ["Rust"]
---

[std::any::type_name](https://doc.rust-lang.org/std/any/fn.type_name.html) 関数を使うと、型パラメーターで指定した型の名前を、文字列表現で取得することができます。
次の例では、`String` 型の型名を取得しています。

```rust
let name = std::any::type_name::<String>();
println!("{}", name) //=> alloc::string::String
```

返される文字列の形式は明確には仕様化されておらず、Rust のバージョンごとに変わったりするので、__この文字列に依存したビジネスロジックは記述すべきではない__ とされています（診断用にのみ使用できます）。

次のようなユーティリティ関数を作成すれば、任意のリテラル値や変数の型を簡単に調べることができます。

```rust
fn print_type_of<T>(_: T) {
    println!("{}", std::any::type_name::<T>())
}

fn main() {
    print_type_of(0);        //=> i32
    print_type_of(0b1111);   //=> i32
    print_type_of(0i8);      //=> i8
    print_type_of(0u64);     //=> u64
    print_type_of(0.1);      //=> f64
    print_type_of(0.1e5);    //=> f64
    print_type_of(0.1f32);   //=> f32
    print_type_of(0.1e5f32); //=> f32
    print_type_of('a');      //=> char
    print_type_of("Hello");  //=> &str
}
```

