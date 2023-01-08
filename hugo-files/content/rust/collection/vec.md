---
title: "Rust でベクター型 (Vec) を扱う"
url: "p/jku3biq/"
date: "2023-01-07"
tags: ["Rust"]
draft: true
---

{{% private %}}
- https://doc.rust-lang.org/stable/std/vec/index.html
- https://doc.rust-lang.org/stable/std/vec/struct.Vec.html
- https://doc.rust-jp.rs/book-ja/ch08-01-vectors.html
- https://doc.rust-lang.org/beta/rust-by-example/std/vec.html
{{% /private %}}


Vec 型とは？
----

Rust のベクター型 (__`Vec<T>`__) は、特定の型 (`T`) の要素を保持する可変長配列です。
次の例では、__`Vec::new`__ 関数で空の `Vec<i32>` インスタンスを作成し、__`push`__ メソッドにより動的に要素を追加しています。

```rust
let mut v: Vec<i32> = Vec::new(); // 型は推論されるので省略可能
v.push(10);
v.push(20);
v.push(30);
println!("{:?}", v); //=> [10, 20, 30]
```

動的に要素を追加／削除するためには、`Vec` 変数は __`mut`__ を付けて定義しておく必要があります。
この例の場合、`push` メソッドの使い方から、要素の型は `i32` であることが推測されるので、`Vec` インスタンスの作成時に型注釈を省略することができます（ほとんどのケースでは省略できます）。

```rust
let mut v = Vec::new();
```


Vec インスタンスの作成方法
----

```rust
// 空の Vec を作成する
let mut v: Vec<i32> = Vec::new();

// vec! マクロで初期値を指定して作成する
let mut v: Vec<i32> = vec![];
let mut v = vec![10, 20, 30];
let mut v = vec![0; 3];  //=> [0, 0, 0]

// Range を collect して連番の Vec を作成する
let mut v: Vec<i32> = (0..3).collect();        //=> [0, 1, 2]
let mut v: Vec<i32> = (0..=3).collect();       //=> [0, 1, 2, 3]
let mut v: Vec<i32> = (0..=3).rev().collect(); //=> [3, 2, 1, 0]
```


Vec の要素を参照する
----

# [] で参照する

__`[]`__ 演算子により、指定したインデックスの要素を取得することができます。
不正なインデックスを指定すると、panic が発生してプログラムが強制終了します。


```rust
let v = vec!['A', 'B', 'C'];
let ch1 = v[0]; // 'A'
let ch2 = v[1]; // 'B'
let ch3 = v[2]; // 'C'
let ch4 = v[3]; // panic が発生！
```

# get で参照する

__`get`__ メソッドを使うと、[Option 型](/p/9m6m5m3/) で要素を取得することができます。
引数で正しいインデックスを指定すると、要素をデータとして持つ `Some` バリアントが返され、不正なインデックスを指定すると、`None` バリアントが返されます。
戻り値を `match` や `if let` でハンドルすることで、安全に要素を取得することができます。

```rust
let v = vec!['A', 'B', 'C'];

// if let を使う方法
if let Some(elem) = v.get(0) {
    println!("最初の要素は {} です", elem);
}

// match を使う方法
match v.get(10) {
    Some(elem) => println!("要素の値は {} です", elem),
    None => println!("不正なインデックスです"),
}
```

### Vec 要素をループ処理する

`for in` ループで `Vec` に格納された要素を 1 つずつ取り出すことができます。
ループ時に所有権の移動を防ぐため、`&` を付けて不変参照（スライス）を取得します。

```rust
let v = vec!['A', 'B', 'C'];
for elem in &v { // v.iter() でも可
    // elem は &char 型の不変参照
    println!("{}", elem);
}
```

ループで各要素の可変参照を取得すると、要素の値を変更することができます。

```rust
let mut v = vec![100, 200, 300];
for elem in &mut v { // v.iter_mut() でも可
    // elem は &mut i32 型の可変参照
    *elem *= 2;
}
println!("{:?}", v); //=> [200, 400, 600]
```

`enumerate` と組み合わせると、インデックス番号を取得しながらループ処理できます。

```rust
let v = vec!['A', 'B', 'C'];
for (index, elem) in v.iter().enumerate() {
    println!("{}:{}", index, elem);
}
```


Vec 要素を追加／削除する
----

### 末尾に要素を追加する (push)

```rust
let mut v = vec![];
v.push(10);
```

### 末尾の要素を取り出して削除する (pop)

```rust
let mut v = vec!['A', 'B', 'C'];

// if let で 1 つ取り出す
if let Some(elem) = v.pop() {
    println!("{elem}");
}

// while let で全部取り出す
while let Some(elem) = v.pop() {
    println!("{elem}");
}
```

### 指定したインデックスの要素を削除する (remove)

```rust
let mut v = vec!['A', 'B', 'C'];
v.remove(1);
println!("{:?}", v); //=> ['A', 'C']
```

### 指定したインデックスの前に挿入する (insert)

```rust
let mut v = vec!['A', 'B', 'C'];
v.insert(1, 'X');
println!("{:?}", v); //=> ['A', 'X', 'B', 'C']
```


enum で複数の型の要素を保持する
----

`Vec<T>` 型には、1 種類の型 `T` の要素しか格納できませんが、列挙型 (`enum`) の要素を保持するようにすれば、実質的に複数の型の要素を保持することができます。
Rust の列挙型は、各バリアントが異なる型のデータを持つことができるからです。

```rust
// 列挙型のバリアントで異なる型のデータを保持するようにする
enum Cell {
    /// 整数値を保持するセル
    Int(i32),
    /// 浮動小数点数を保持するセル
    Float(f64),
    /// テキストを保持するセル
    Text(String),
}

// 各バリアントで必要なデータを保持して Vec 要素として格納する
let cells = vec![
    Cell::Int(7),
    Cell::Text(String::from("AAA")),
    Cell::Float(12.34),
];

// Vec の各要素（列挙型）をループ処理
for elem in &cells {
    match elem {
        Cell::Int(x) => println!("Int: {}", x),
        Cell::Float(x) => println!("Float: {}", x),
        Cell::Text(x) => println!("Text: {}", x),
    }
}
```


その他の Vec 操作
----

### ソートする (sort)

```rust
let mut v = vec![3, 9, 1, 7, 5];
v.sort();
println!("{:?}", v); //=> [1, 2, 3, 4, 5]
```

### 逆順にする (reverse)

```rust
let mut v = vec!['A', 'B', 'C'];
v.reverse();
println!("{:?}", v); //=> ['C', 'B', 'A']
```

### 指定したインデックスの要素を入れ替える (swap)

```rust
let mut v = vec!['A', 'B', 'C'];
v.swap(1, 2);
println!("{:?}", v); //=> ['A', 'C', 'B']
```

