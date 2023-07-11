---
title: "Rust でジェネリクス構造体・関数を定義する（任意の型を扱えるようにする）"
url: "p/be8u7sg/"
date: "2023-07-09"
tags: ["Rust"]
---

ジェネリクスの基本
----

Rust の __ジェネリクス (generics)__ の仕組みを使うと、任意の型を扱う処理を 1 つのコードで記述することができます。

まずは、ジェネリクスを使っていないコードから見てみます。
次のサンプルコードでは、整数型 (`i32`) の座標値を表現する構造体 `Point` と、その浮動小数点数型 (`f64`) バージョンである `PointF` を定義しています。

{{< code lang="rust" title="ジェネリクスを使わない Point 構造体" >}}
/// 整数型の座表値
struct Point {
    x: i32,
    y: i32,
}

/// 浮動小数点数型の座標値
struct PointF {
    x: f64,
    y: f64,
}

// 使用例
fn main() {
    let p1 = Point { x: 0, y: 5 };
    println!("{}, {}", p1.x, p1.y);

    let p2 = PointF { x: -1.2, y: 7.4 };
    println!("{}, {}", p2.x, p2.y);
}
{{< /code >}}

Rust では、数値だけでも様々なプリミティブ型 (`u8`, `i128`, `f64`, `usize`, ...) で区別して扱うので、このように扱う数値の種類によってコードを書き分けなければいけません。
一方の型（上記の例では `PointF` 構造体）だけで実装しようとすると、__`as`__ を使ったキャストがいたるところに出てくることになります。

そこでジェネリクスの出番です。
ジェネリクスの仕組みでは、構造体や関数の定義時に __型パラメーター__ を宣言することで、（コンパイル時に決定される）任意の型を扱えるようになります。
次のコードは、ジェネリクス版の `Point` 構造体です。
汎用的な型パラメーターの名前としては、__`T`__ を使うのが慣例となっています。

{{< code lang="rust" title="ジェネリクスを使う場合" >}}
struct Point<T> {
    x: T,
    y: T,
}

fn main() {
    let p1 = Point { x: 0, y: 5 }; // Point<i32> とみなされる
    println!("{}, {}", p1.x, p1.y);

    let p2 = Point { x: -1.2, y: 7.4 }; // Point<f64> とみなされる
    println!("{}, {}", p2.x, p2.y);
}
{{< /code >}}

これで、様々な数値型の座標値を、`Point` というジェネリック構造体 1 つで表現できるようになりました！


複数の型パラメーターを扱う
----

型パラメーターは任意の数だけカンマで並べて宣言できます。
次の `Pair` 構造体は、任意の型のフィールド `first` と `second` を持つ構造体です。
`first` と `second` はそれぞれ別の型の値を保持することができます。

{{< code lang="rust" title="ジェネリックな Pair 構造体" >}}
struct Pair<TFirst, TSecond> {
    first: TFirst,
    second: TSecond,
}

fn main() {
    let pair1 = Pair { first: "key1", second: "val1" }; // Pair<&str, &str> とみなされる
    println!("{:?}, {:?}", pair1.first, pair1.second); //=> "key1", "val1"

    let pair2 = Pair { first: [1, 2], second: 100 }; // Pair<[i32; 2], i32> とみなされる
    println!("{:?}, {:?}", pair2.first, pair2.second); //=> [1, 2], 100
}
{{< /code >}}


ジェネリクス関数
----

関数でも型パラメーターを使うことができます。
次の `swap` 関数は、2 つの値を受け取り、その順番を入れ替えたタプルを返します。

{{< code lang="rust" title="簡単なジェネリクス関数の例" >}}
/// 2 つの要素の順番を入れ替えたタプルを返す
fn swap<T1, T2>(a: T1, b: T2) -> (T2, T1) {
    (b, a)
}

fn main() {
    let tuple = swap(100, "hello");
    println!("{:?}, {:?}", tuple.0, tuple.1); //=> "hello", 100

    // Destructuring の構文でタプル要素を分解して受け取ることもできる
    let (x, y) = swap(100, "hello");
    println!("{:?}, {:?}", x, y); //=> "hello", 100
}
{{< /code >}}

ちなみに、Rust でプリミティブ型の値をスワップしたいときは、次のようにタプルの destructuring 構文を使うのが簡単です。

```rust
let mut a = 1;
let mut b = 2;
(b, a) = (a, b);  // 値のスワップ
```

