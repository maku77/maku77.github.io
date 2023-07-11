---
title: "Rust でハッシュマップ型 (HashMap) を扱う"
url: "p/eefwaa3/"
date: "2023-01-25"
tags: ["Rust"]
---

ハッシュマップの基本
----

Rust の標準ライブラリとして、キー＆バリューのコレクションを扱うハッシュマップ型 (__`HashMap<K, V>`__) が用意されています。

下記は、空のハッシュマップを作成し、キー＆バリューを追加／取得する例です。

```rust
use std::collections::HashMap;

let mut map = HashMap::new();
map.insert(String::from("AAA"), 100);
map.insert(String::from("BBB"), 200);
map.insert(String::from("CCC"), 300);
println!("{:?}", map.get("AAA"));  //=> Some(100)
println!("{:?}", map.get("ZZZ"));  //=> None
```

上記の例では、`HashMap` の型パラメーターを省略していますが、`insert` しているデータから、キーの型は `String`、値の型は `i32` と推測されます。
`HashMap` のキーの型が `String` であっても、`get` メソッドの引数には `&str` を渡せるようになっています。


ハッシュマップの生成方法
----

### 空のハッシュマップを生成する (new, with_capacity)

```rust
let mut map: HashMap<String, i32> = HashMap::new();
let mut map: HashMap<String, i32> = HashMap::with_capacity(100)
```

空のマップは __`new`__ で生成できますが、あらかじめ格納する要素数を想定できるときは __`with_capacity`__ を使うと効率的です。
`HashMap` 型には、`Vec` 型の `vec!` のようなインスタンス生成用のマクロは用意されていないので、これらの関数を使ってインスタンスを生成する必要があります。
空のハッシュマップには何らかのデータを詰める必要があるので、通常は mutable な変数として定義します。

コンパイラーが、格納するデータから型を推測してくれるので、ほとんどのケースで型情報 (`: HashMap<String, i32>`) を省略できます。

```rust
let mut map = HashMap::new();
let mut map = HashMap::with_capacity(100)
```

### タプル（キーと値）のベクターから生成する

```rust
use std::collections::HashMap;

let kv_pairs = vec![
    (String::from("AAA"), 100),
    (String::from("BBB"), 200),
    (String::from("CCC"), 300),
];
let map: HashMap<_, _> = kv_pairs.into_iter().collect();
```

このケースでは、__`collect`__ メソッドに、`HashMap` 型としてまとめ上げることを知らせるために、__`HashMap<_, _>`__ という型情報の指定が必要になります。
キーの型と値の型はコンパイラに推測してもらうので、`_` とだけ記述しておけば OK です。

`HashMap` の __`extend`__ メソッドを使う方法もあります。

```rust
use std::collections::HashMap;

let vec = vec![("AAA", 100), ("BBB", 200), ("CCC", 300)];
let mut map = HashMap::new();
map.extend(vec);
```

### キーのベクターと値のベクターから生成する

```rust
use std::collections::HashMap;

let keys = vec![String::from("AAA"), String::from("BBB"), String::from("CCC")];
let values = vec![100, 200, 300];
let map: HashMap<_, _> = keys.iter().zip(values.iter()).collect();
```


要素の追加と削除 (insert, remove)
----

`HashMap` への要素の追加は __`insert`__、削除は __`remove`__ メソッドで行います。
すでに存在するキーに対して `insert` を実行すると、古い値が上書きされます。
存在しないキーに対して `remove` を実行した場合は無視されます。

```rust
use std::collections::HashMap;

let mut map = HashMap::new();
map.insert(String::from("AAA"), 100);
map.insert(String::from("AAA"), 0); // 上書きする
map.insert(String::from("BBB"), 200);
map.remove("BBB"); // 削除する（BBB が削除される）
map.remove("CCC"); // 削除する（何も起こらない）

println!("{:?}", map); //=> {"AAA": 0}
```

`remove` メソッドは、[Option 型](/p/9m6m5m3/) を返すようになっており、削除されたキーに対応する値を取得することができます。
存在しないキーを指定した場合（何も削除されなかった場合）は、`None` を返します。

```rust
if let Some(val) = map.remove("AAA") {
    println!("削除された値は {} です", val);
}
```


キーに対応する値を取得する (get)
----

__`HashMap#get`__ メソッドで、指定したキーに対応する値を取得することができます。
戻り値は [Option 型](/p/9m6m5m3/) で返されるため、指定されたキーが存在するかを調べつつ値を取り出すことができます。

```rust
// HashMap を作成する
let mut map = HashMap::new();
map.insert(String::from("AAA"), 100);

// キーに対応する値を取得する
let key = String::from("AAA");
if let Some(val) = map.get(&key) {
    println!("{} の値は {} です", &key, val);
} else {
    println!("{} が見つかりません", &key);
}
```


全ての要素をループ処理する
----

`HashMap` の要素（キー＆バリュー）を 1 つずつ取り出して処理したいときは、__`iter`__ メソッドを使用します。

```rust
let mut map = HashMap::new();
map.insert(String::from("AAA"), 100);
map.insert(String::from("BBB"), 200);
map.insert(String::from("CCC"), 300);

// HashMap::iter() は (&'a key, &'a value) を順不同で返す
for (key, value) in map.iter() {
    println!("{} => {}", key, value);
}
```

{{< code title="実行結果" >}}
CCC => 300
AAA => 100
BBB => 200
{{< /code >}}

ループしながら値を書き換えたいときは、`iter` の代わりに __`iter_mut`__ を使用して、値への可変参照を取得します。

```rust
// HashMap::iter_mut() は (&'a key, &'a mut value) を順不同で返す
for (key, value) in map.iter_mut() {
    *value += 1;
    println!("{} の値に 1 を足して {} にしました", key, value);
}
```

{{< code title="実行結果" >}}
BBB の値に 1 を足して 201 にしました
CCC の値に 1 を足して 301 にしました
AAA の値に 1 を足して 101 にしました
{{< /code >}}


キーが見つからない場合に値をセットする (or_insert)
----

```rust
// HashMap の作成
let mut map = HashMap::new();
map.insert(String::from("AAA"), 100);

// 指定したキーが存在しなければ新しい値をセットする
map.entry(String::from("AAA")).or_insert(0);
map.entry(String::from("BBB")).or_insert(0);

println!("{}", map.get("AAA").unwrap()); //=> 100
println!("{}", map.get("BBB").unwrap()); //=> 0
```

`or_insert` は可変参照を返すので、取り出した値を利用して自分自身の値を書き換えることができます。
次の例では、単語の出現数をカウントしています。

```rust
let text = "AAA BBB CCC BBB AAA";
let mut word_count = HashMap::new();
for word in text.split_whitespace() {
    let count = word_count.entry(word).or_insert(0);
    *count += 1;
}
println!("{:?}", word_count);  //=> {"AAA": 2, "BBB": 2, "CCC": 1}
```

整数型 (`i32`) のデフォルト値は 0 なので、`or_insert(0)` の代わりに `or_default()` を使うこともできます。


{{% private %}}
- 独自型のインスタンスをキーにする
  - https://doc.rust-jp.rs/rust-by-example-ja/std/hash/alt_key_types.html
  - `Eq` と `Hash` トレイトを実装している型であれば、`HashMap` のキーとして使えます。
    - 整数型（`i32` や `u32`）
    - 文字列型（`String` や `&str`）
    - bool 型（2 つのキーしか使えないのであまり用途はありません）
    - `Eq` と `Hash` トレイトを実装した独自型
  - 独自の struct 型を `HashMap` のキーとして使いたい場合は、通常は `#[derive(PartialEq, Eq, Hash)]` を付けるだけで OK です。
{{% /private %}}

