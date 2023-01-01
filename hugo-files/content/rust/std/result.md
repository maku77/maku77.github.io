---
title: "Rust の Result 型で成功 (Ok) と失敗 (Err) を表現する"
url: "p/us2ahpw/"
date: "2022-12-29"
tags: ["Rust"]
draft: true
---

{{% private %}}
- https://doc.rust-lang.org/stable/std/result/
{{% /private %}}

Result 型とは？
----

Rust の標準ライブラリには、[Result](https://doc.rust-lang.org/std/result/enum.Result.html) という enum 型が用意されています。
`std::io` サブモジュールが提供する [`std::io::Result`](https://doc.rust-lang.org/std/io/type.Result.html) とは別なので注意してください（でも本質的に表現したいことは同じです）。
`Result` の列挙子 (variants) としては、次のように __`Ok`__ と __`Err`__ だけが定義されており、それぞれ何らかの処理の「成功」と「失敗」を表現するために使用できます。

```rust
enum Result<T, E> {
   Ok(T),
   Err(E),
}
```

成功と失敗を表すだけであれば、`bool` 型だけで表現できそうですが、`Result` 型は `Ok` と `Err` という列挙子が任意の値を持つことができるようになっているので、__成功した場合の結果や、失敗した場合の理由を表現することができます__。
また、何らかのメソッドが `Result` 型を返すとき、その値を呼び出し側で何も処理していないと、コンパイラが警告を出してくれるため、__エラーのハンドル忘れを防ぐ効果__ もあります。

`Result` 型の概念は、他のモダンな言語でも採用されています（参考: [Kotlin の Result 型](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/)）。


match による分岐処理
----


Result のメソッド
----

### expect（成功することを想定する）

[`Result#expect`](https://doc.rust-lang.org/std/result/enum.Result.html#method.expect) は、`Result` が `Err` variant だったときに、指定されたメッセージを表示してプログラムを強制終了 (panic) します。
つまり、処理が成功することを想定するコードになります。

{{< code lang="rust" hl_lines="4" >}}
let mut line = String::new();
io::stdin()
    .read_line(&mut line)
    .expect("Failed to read line");
{{< /code >}}

この例では、`read_line` の実行に失敗したときに、プログラムを終了させています。


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

何らかのメソッドが返した `Result` を、`match` や `expect` で「消費」することで、この警告は表示されなくなります。

- 参考: [The must_use attribute - The Rust Reference](https://doc.rust-lang.org/reference/attributes/diagnostics.html#the-must_use-attribute)

