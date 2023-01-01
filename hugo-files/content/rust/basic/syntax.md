---
title: "10 分で学ぶ Rust 文法"
url: "p/63m4k3i/"
date: "2022-12-13"
tags: ["Rust"]
---

Rust 言語の文法をざっと理解したり復習したりするときに参照するページです。

変数
----

- デフォルトで変更不可の変数 (immutable variable) になる
  ```rust
  let n = 100;
  n = 200;  // Error!
  ```
- 可変の変数にしたいときは __`let mut`__ を使う
  ```rust
  let mut n = 100;
  n = 200;  // OK
  ```
- 変数の再宣言 (redeclaration) により変数名を使いまわせる
  ```rust
  let s = "  ABC  ";
  let s = s.trim();
  ```

関数
----

関数はどこかで定義されていればよく、呼び出し前に定義しておく必要はありません。
次の `plus_one` 関数は、`i32` 型の整数値を 1 つ受け取り、戻り値として `i32` 型の整数値を返します。

```rust
fn main() {
    println!("{}", plus_one(100)); //=> 101
}

fn plus_one(x: i32) -> i32 {
    x + 1
}
```

Rust では、`{}` で囲まれたブロックの最後をセミコロンで終了しないと、その計算結果がブロック全体の値として評価されます（式として扱われます）。
上記の例では、関数ブロックの末尾の `x + 1` にセミコロンが付いていないので、その値が関数の戻り値として扱われます。
明示的に `return` キーワードを使用する場合は、セミコロンを付けることができます。

```rust
fn plus_one(x: i32) -> i32 {
    return x + 1;
}
```

制御構文
----

### 条件文 (if statement)

```rust
if n > 0 {
    println!("{} is positive", n);
} else if n < 0 {
    println!("{} is negative", n);
} else {
    println!("{} is zero", n);
}
```

C/C++ と異なり、条件部分は `()` で囲む必要はありません。
ただし、ブロック部分は必ず `{}` で囲む必要があります。

### 条件式 (conditional expression)

Rust の `if` は式として扱うことができるので、分岐後に評価した値を参照することができます（Kotlin などのモダンな言語と同様です）。
下記の変数 `s` には、`n` の値に応じて `Good` あるいは `Bad` が格納されます。

```rust
let s = if n > 0 { "Good" } else { "Bad" };
```

`if` を式として使う場合は、必ず `else` 句が必要なことに注意してください。
また、評価後の値（上記の例では `"Good"` や `"Bad"`）の後ろには、セミコロン (`;`) を付けてはいけません。

### while ループ

```rust
let mut n = 1;
while n <= 3 {
    println!("n = {n}");
    n += 1;
}
```

`if` と同様、条件部分は `()` で囲まず、ブロック部分は `{}` で囲みます。
C/C++ とは異なり、Rust には `do-while` 構文はありませんが、`break` や `continue` は同様に使用できます。
無限ループにしたいときは、`while true` とする代わりに、下記の `loop` を使用します。

### loop ループ

```rust
let mut n = 1;
loop {
    println!("n = {n}");
    n += 1;
    if n > 3 {
        break;
    }
}
```

Rust には、無限ループ用の __`loop`__ が用意されています。

### for-in ループ

{{< code lang="rust" title="1〜4 のループ (right-exclusive range operator)" >}}
for i in 1..5 {
    print!("{i} ")
}
{{< /code >}}

{{< code lang="rust" title="1〜5 のループ (right-inclusive range operator)" >}}
for i in 1..=5 {
    print!("{i} ")
}
{{< /code >}}

`in` の後ろの部分に式を記述した場合、ループの実行前に一度だけ評価されます。
例えば、下記の `s.len()` 部分は一度だけ実行されます。

```rust
for i in 0..s.len() {
    // ...
}
```

`for` の iteration variable（イテレーション変数）のスコープは `for` ステートメント内に限定されるので、外側で定義されている変数と同じ名前を使うことができます（推奨はされませんが）。


配列 (array)
----

### 配列の基本

```rust
let arr = [0, 1, 2];
let arr = ["AAA", "BBB", "CCC"];
let arr = [true, false, true];
```

配列要素の型は同一でなければいけません。
配列のサイズ（要素数）は __`arr.len()`__ で取得できます。
要素の参照は、C/C++ と同様に 0 始まりのインデックス指定で行えます (`arr[0]`)。

### 可変配列 (mutable array)

要素を変更可能にしたいときは、次のように __`let mut`__ で配列を定義します（配列のサイズや型を変更することはできません）。

```rust
let mut arr = ["AAA", "BBB", "CCC"];
arr[1] = "XXX";
println!("{:?}", arr); //=> ["AAA", "XXX", "CCC"]
```

次のように、すべての要素をまとめて入れ替えることもできます。

```rust
let mut arr = [1, 2, 3];
println!("{:?}", arr); //=> [1, 2, 3]
arr = [4, 5, 6];
println!("{:?}", arr); //=> [4, 5, 6]
```

いずれにしても、配列のサイズは不変でなければいけません。

### 配列のサイズを指定して初期化

```rust
let arr = ["AAA"; 5];
println!("{:?}", arr); //=> ["AAA", "AAA", "AAA", "AAA", "AAA"]
```


Vec 型（可変サイズのベクター型）
----

Rust のベクター型 (`Vec`) は、C++ の `std::vector` 型に相当するもので、可変サイズの配列として使用できます。
ベクターを生成するには、__`vec!`__ マクロを使用します。

```rust
let mut v = vec!["AAA", "BBB"]; // Vec<&str> 型
v.push("CCC");
v.push("DDD");
v[0] = "XXX";
println!("{:?}", v); //=> ["XXX", "BBB", "CCC", "DDD"]
```

ベクターは配列と同様に、初期化時にサイズを指定できますが、このサイズ指定に変数を使用できます（配列では許されていません）。

```rust
let length = 3;
let v = vec!["AAA"; length];
println!("{:?}", v); //=> ["AAA", "AAA", "AAA"]
```

下記は、よく使うベクター操作です。

```rust
v.push("AAA");       // 末尾に要素を追加
a = v.pop();         // 末尾の要素を取り出して削除
v.insert(0, "XXX");  // 指定したインデックスの前に要素を追加
v.remove(0);         // 指定したインデックスの要素を削除
```


数値リテラル
----

{{< code lang="rust" title="プレフィックス" >}}
0x10  // 16進数 (= 16)
0o10  // 8進数 (= 8)
0b10  // 2進数 (= 2)
{{< /code >}}

{{< code lang="rust" title="可読性のためのアンダースコア" >}}
1_234_567    // アンダースコアを自由に入れて OK
0b1100_0101  // 2進数リテラルでもアンダースコアを使える
{{< /code >}}

{{< code lang="rust" title="浮動小数点数の指数表記" >}}
12.3e6   // 12.3 * 10^6 (= 12300000)
12.3e-6  // 12.3 * 10^-6 (= 0.0000123)
{{< /code >}}


型注釈 (type annotation)
----

```rust
let a: u8 = 200;
```

数値リテラルから変数を生成するときに、型注釈をつけることで具体的なサイズや符号の有無を制御できます。
型を省略した場合（かつコンパイラが型推論できない場合）は、デフォルトで `i32` 型が使用されます（浮動小数点数の場合は `f64`）。

- 整数の型（符号あり）: `i8`, `i16`, `i32`, `i64`, `i128`（デフォルトは `i32`）
- 整数の型（符号なし）: `u8`, `u16`, `u32`, `u64`, `u128`
- 浮動小数点数の型: `f32`, `f64`（デフォルトは `f64`）
- 特殊な型: `usize`（配列のインデックスやサイズ用で、実質 unsigned int）

{{< code lang="rust" title="usize の使用例" >}}
const N: usize = 5;
let arr = [0; N];  //=> [0, 0, 0, 0, 0]（型は [i32; 5]）
{{< /code >}}


列挙型 (enum) と match
----



その他
----

- 標準ライブラリ（`str` など）はインクルードなしでデフォルトで使える
