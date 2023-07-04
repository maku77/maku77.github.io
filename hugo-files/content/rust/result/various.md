---
title: "Rust の各種ライブラリのエラー型と Error トレイト"
url: "p/8amv5eo/"
date: "2023-01-08"
tags: ["Rust"]
---

いろんな Error 実装がある
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

同じエラー型を使っているつもりでも、上記のように実体は異なる定義だったりするので注意してください。


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


{{% note title="独自のエラー型を定義する場合の推奨方法" %}}
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

