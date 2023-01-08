---
title: "Rust の Option 型の基本 ─ 値の有無を表現する型"
url: "p/9m6m5m3/"
date: "2023-01-06"
tags: ["Rust"]
---

Option 型とは？
----

多くのオブジェクト指向言語には、オブジェクトが存在しないことを示す `null` という値が用意されていますが、Rust には `null` は存在しません。
Rust の設計者は、`null` という概念が不具合の温床となっていると判断しました。
その代わりに、Rust には [__`Option`__](https://doc.rust-lang.org/stable/std/option/enum.Option.html) という組み込みの列挙型 (enum) が用意されており、__ある値が存在しているか__ を表現できるようになっています。
そして、この設計は `null` を使った表現よりも柔軟で、かつ安全です。

`Option` 型の定義はとてもシンプルで、次のような感じになっています。

{{< code lang="rust" title="Option 型の定義" >}}
pub enum Option<T> {
    Some(T),
    None,
}
{{< /code >}}

__`Some`__ バリアントが「（任意の型 `T` の）値が存在する」ことを示し、__`None`__ バリアントが「値が存在しない」ことを示します。
つまり、`Some` と `None` で値の有無を表現しつつ、値が存在する場合はその値を `Some` バリアントから取り出せるようになっています。

例えば、値が存在しないかもしれない `String` 型（他の言語では Nullable な `String` 型）は、__`Option<String>`__ 型として表現することができ、その `Some` 値と `None` 値を次のように生成できます。

```rust
let some_val: Option<String> = Some(String::from("Hello"));
let none_val: Option<String> = None;
```

こんな感じで別名を付けると理解しやすいでしょうか。

```rust
type NullableString = Option<String>;
let some_val: NullableString = Some(String::from("maku"));
let none_val: NullableString = None;
```

`Option` 型のバリアントは頻繁に使用するため、`Option::Some` や `Option::None` ではなく、`Some` や `None` と記述できるようになっています。
これらのシンボルは、Rust の初期化処理 (prelude) でロードされるからです。


Option 型の値を match で処理する
----

次の関数は、渡された文字列に応じて `Some<i32>` あるいは `None` を返します。
うまくパースできない（想定外の）の文字列が渡されたときは、`None` を返すようにしています（Rust 以外の言語であれば、`null` を返したり、例外を発生させたりするところです）。

```rust
fn parse_num_str(s: &str) -> Option<i32> {
    match s {
        "one" | "一" => Some(1),
        "two" | "二" => Some(2),
        "three" | "三" => Some(3),
        _ => None,
    }
}
```

戻り値の `Option<i32>` は enum 型の値なので、次のように `match` で分岐処理しつつ、`Some` バリアントに含まれている `i32` 値を取り出すことができます。

```rust
let num = parse_num_str("三");

match num {
    Some(x) => println!("The number is {}", x),
    None => println!("Could not parse"),
}
```


Option 型の値を if let で処理する
----

`Option` 型の値として `Some` バリアントが返された場合のみ何らかの処理をしたいときは、`match` の代わりに __`if let`__ 構文を使うとシンプルに記述できます。

{{< code lang="rust" title="Some が返された場合のみ処理する" >}}
let num = parse_num_str("三");
if let Some(num) = num {
    // ここで num は i32 型の値になっている
    println!("The number is {}", num);
}
{{< /code >}}

`match` 構文と同様に、特定のリテラル値に一致するかどうかを調べることもできます。

```rust
let num = parse_num_str("三");
if let Some(3) = num {
    println!("Found: three");
}
```


None かどうかをチェックする (is_none)
----

`Option` 型の値が `None` バリアントかどうかを確認したいときは、__`is_none()`__ メソッドを使います。
値が存在しないときに早期リターンしたいケースで使えるかもしれません。
逆に `Some` バリアントかどうかを調べる `is_some()` も用意されていますが、あまり使うことはないでしょう。

```rust
let num = parse_num_str("三");
if num.is_none() {
    eprintln!("Parse error");
    return;
}
```

`Option` 列挙型には、`Some` バリアントが保持するデータをダイレクトに取り出すための __`unwrap`__ というメソッドが用意されていますが、このメソッドは `None` バリアントに対して呼び出すと panic が発生するで危険です。
ただ、上記のように `None` のケースを排除できていれば、安全に `unwrap` することができます。

```rust
// Some バリアントであることがわかっていれば unwrap で安全に値を取り出せる
let num = num.unwrap();
println!("The number is {}", num);
```


None だった場合に代替値を使う (unwrap_or, unwrap_or_else)
----

`Some` バリアントが保持するデータを取り出す `unwrap` メソッドは、`None` バリアント対して呼び出すと panic が発生してしまいますが、代わりに __`unwrap_or`__ メソッドを使うと、`None` だった場合に代替値を返すことができます。
次の例では、`get_user_id` 関数が返す `Option` 値が `None` だった場合に、代替値として `-1` を使うようにしています。

{{< code lang="rust" hl_lines="10" >}}
fn get_user_id(name: &str) -> Option<i32> {
    match name {
        "root" => Some(0),
        "maku" => Some(1),
        _ => None,
    }
}

let opt_id = get_user_id("unknown");
let id = opt_id.unwrap_or(-1); // opt_id が None のとき -1 になる
println!("{}", id); //=> -1
{{< /code >}}

`unwrap_or` メソッドで指定する代替値は、メソッドの引数として渡すことになるので、そこに何らかの式を指定すると必ず評価されてしまうことに注意してください。

```rust
// get_default_id 関数は必ず呼び出されてしまう
let id = opt_id.unwrap_or(get_default_id());
```

この振る舞いを防ぐには、`unwrap_or` の代わりに __`unwrap_or_else`__ メソッドを使用します。

```rust
// opt_id が None のときのみ get_default_id 関数が呼び出される
let id = opt_id.unwrap_or_else(|| get_default_id());
```

似たようなメソッドに、__`unwrap_or_default`__ がありますが、これは代替値としてその型のデフォルト値（`i32` なら `0`、`String` なら `""`）を返します。

```rust
let opt_id = get_user_id("unknown");
let id = opt_id.unwrap_or_default(); //=> 0
```

よく考えると、`unwrap_or` 系のメソッドは、次のような `if let` のショートカットだったりします。

```rust
let id = if let Some(x) = opt_id { x } else { -1 };
```


結局 Option 型の値はどうやってハンドルすればよいの？
----

以上のように、`Option` 型の値はいろいろなハンドル方法がありますが、どのようにハンドルするかは、次のような優先度で考慮すればよいと思います。

1. `match` でハンドルする
   - `match` は `Option` のバリアントが包括的に処理されているかをコンパイル時に確認してくれるので安全です。
2. `if let` でハンドルする
   - 特定の `Some` 値にしか興味がない場合は、`if let` でその値を取り出すことを考えます。ただし、想定外のバリアント値を見過ごすことがないように、`else` ブロックを配置しておくと安全です。
3. その他の `Option` メソッドでハンドルする
   - 十分に注意して `Option` 型のその他のメソッドを使用します。特に、panic を発生させる `unwrap()` メソッドなどは、プロダクトコードでは使わないようにするのが無難です。

