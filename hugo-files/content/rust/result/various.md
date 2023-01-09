---
title: "Rust の各種ライブラリのエラー型と Error トレイト"
url: "p/8amv5eo/"
date: "2023-01-08"
tags: ["Rust"]
---

いろんな Error 型
----

Rust には、成功と失敗を表現するための標準的な型である [std::result::Result](https://doc.rust-lang.org/std/result/enum.Result.html) 型が用意されています。

{{< code lang="rust" title="std::result::Result" >}}
enum Result<T, E> {
   Ok(T),
   Err(E),
}
{{< /code >}}

`Result` を返す関数内でエラーが発生した場合は、__`Err`__ バリアントのフィールドに具体的なエラーオブジェクトを詰めて返すことになるのですが、このエラーオブジェクトの型として、各ライブラリが独自のエラー型を定義しています。
下記はその一例です。

- [std::io::Error](https://doc.rust-lang.org/std/io/struct.Error.html)
- [std::fmt::Error](https://doc.rust-lang.org/std/fmt/struct.Error.html)
- [std::str::Utf8Error](https://doc.rust-lang.org/std/str/struct.Utf8Error.html)
- [std::num::ParseIntError](https://doc.rust-lang.org/std/num/struct.ParseIntError.html)


Error トレイト
----

Rust は共通のエラーインタフェースとして、次のような [std::error::Error トレイト](https://doc.rust-lang.org/std/error/trait.Error.html) を定義しています。
前述の各種エラー型は、この `Error` トレイトを実装しています。

```rust
pub trait Error: Debug + Display {
    fn source(&self) -> Option<&(dyn Error + 'static)> { ... }
    fn description(&self) -> &str { ... }
    fn cause(&self) -> Option<&dyn Error> { ... }
    fn provide(&'a self, demand: &mut Demand<'a>) { ... }
}
```

`Error` は、`Debug` と `Display` も備えていることがわかります。
そのため、ほとんどのエラー型は、次のような共通のコードで解析することができます。

```rust
println!("ERROR: {}", err);  // Display による簡潔なエラー情報
println!("ERROR: {:?}", err);  // Debug によるデバッグ情報（技術的な情報）
```

__`err.source()`__ メソッドを使えば、そのエラー型の発生原因となったエラー型を取得できます。
戻り値は `Option` 型になっており、自分自身のエラー型がルートの発生源であれば、`err.source()` は `None` を返します。

