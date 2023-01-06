---
title: "10 分で理解する Rust 文法"
url: "p/63m4k3i/"
date: "2022-12-13"
tags: ["Rust"]
---

Rust 言語の文法をざっと理解するためのページです。
以下をざっと読めば、8 割くらいの Rust コードは読めるようになります。

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

### if 文 (if statement)

```rust
if n > 0 {
    println!("{} is positive", n);
} else if n < 0 {
    println!("{} is negative", n);
} else {
    println!("{} is zero", n);
}
```

C/C++ と異なり、条件式は `()` で囲む必要はありません。
ただし、ブロック部分は必ず `{}` で囲む必要があります。

### if 式 (if expression)

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

Rust には、無限ループ用の __`loop`__ が用意されています。

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

### for-in ループ

`for-in` ループは、イテレート可能なコレクション要素を 1 つずつ取り出すときに使用します。

{{< code lang="rust" title="配列要素を 1 つずつ取り出す" >}}
let arr = [100, 200, 300];
for x in arr {
    println!("The value is: {}", x);
}
{{< /code >}}

範囲演算子（__`..`__ や __`..=`__）で `Range` オブジェクトを作れば、数値範囲のループ処理を行えます。

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

{{< code lang="rust" title="逆順にループ（5 → 1）" >}}
for i in (1..=5).rev() {
    println!("{i}");
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

### 外側のループを抜ける

```rust
'outer: loop {
    for i in 0..5 {
        println!("{}", i);
        if i == 2 {
            break 'outer;
        }
    }
}
```

`while`、`loop`、`for-in` ループに __`'ラベル名: `__ という形でラベルを付けておくと、ネストされたループを `break` で抜けることができます。


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


タプル (tuple)
----


構造体 (struct)
----

### 構造体の基本

構造体の定義とインスタンス化は直感的です。
文字列フィールドの型としては、`&str`（文字列スライス）ではなく `String` を使うことに注意してください。
文字列の所有者は、構造体インスタンス自身だからです。

```rust
// 構造体の定義
struct User {
    name: String,
    email: String,
    sign_in_count: u64,
    active: bool,
}

// インスタンス化
let user = User {
    email: String::from("maku@example.com"),
    name: String::from("maku"),
    active: true,
    sign_in_count: 1,
};

// フィールドの参照にはドットを使う
println!("{}", user.name);
```

構造体インスタンスの参照（借用）経由でフィールドにアクセスする場合もドットが使えます。

```rust
let user_ref = &user;  // 借用 (borrow) して参照を作成
println!("{}", user_ref.name);  // 参照のフィールドもドットでアクセスする
```

### 可変な構造体

インスタンス生成時に `let` の代わりに `let mut` を使うと、可変 (mutable) なインスタンスとなり、すべてのフィールドに再代入可能になります（一部のフィールドだけを再代入可能にすることはできません）。

```rust
let mut user = User {
    // ...
};

// 各フィールドに別の値を代入可能
user.name = String::from("puni");
user.active = false;
```

### フィールド初期化の省略記法

フィールド初期化時に、フィールド名と初期値とする変数名が同じ場合、フィールド名だけを記述する省略記法を使えます。

```rust
fn build_user(email: String, name: String) -> User {
    User {
        email,  // email: email の省略記法
        name,   // name: name の省略記法
        active: true,
        sign_in_count: 1,
    }
}
```

このような省略記法は TypeScript (JavaScript) でも採用されています。

### 部分的に異なるインスタンスを生成する

既存の構造体インスタンスがあるときに、一部のフィールドの値だけが異なる別のインスタンスを生成したいときは、次のように __`.. を使って残りのフィールドをコピーします。

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

`..user1` の後ろにカンマ (`,`) を付けてはいけないことに注意してください。
この構文もスプレッド演算子として TypeScript (JavaScript) に採用されています。

### タプル構造体 (tuple struct)

構造体のフィールド名を省略した、__タプル構造体 (tuple struct)__ を定義することができます。
タプルに名前を付けたようなものと考えればよいです。
通常のタプルと同様、各フィールドの値は、`.0`、`.1` のようなインデックスで参照します。

```rust
// タプル構造体を定義する
struct Point(i32, i32);

// 基本的な使い方
let p = Point(100, 200);
println!("{}, {}", p.0, p.1);  //=> 100, 200

// 各フィールドの値を別々の変数に展開
let Point(x, y) = p;
println!("{x}, {y}");  //=> 100, 200
```

### デバッグ出力

構造体を定義するときに __`#[derive(Debug)]`__ という属性を付けておくと（あるいは [Debug トレイト](https://doc.rust-lang.org/std/fmt/trait.Debug.html) を自力で実装しておくと）、`println!` マクロで、__`{:?}`__ により構造体の内容をデバッグ出力できるようになります（複数行で整形して出力したいときは `{:?}` の代わりに __`{:#?}`__ を使用します）。

```rust
#[derive(Debug)]
struct Point {
    x: i32,
    y: i32,
}

let p = Point { x: 100, y: 200 };
println!("{:?}", p);  //=> "Point { x: 100, y: 200 }"
```

### メソッドの定義

構造体のメソッドは、__`impl`__ ブロック内で定義します。
メソッドの第 1 引数は、構造体インスタンスの参照を示す `&self` になります（Python の文法と似ています）。
構造体の型は明らかなので、`&self` に型を指定する必要はありません。

```rust
struct Rect {
    width: u32,
    height: u32,
}

// impl ブロック内で Rect のメソッドを定義する
impl Rect {
    fn area(&self) -> u32 {
        self.width * self.height
    }
}

let r = Rect { width: 5, height: 2 };
println!("{}", r.area()); //=> 10
```

構造体のフィールドを変更したい場合は、メソッドの第 1 引数を __`&mut self`__ にします。

### 関連関数 (associated function)

`impl` ブロック内で `&self`（や `&mut self`）を第 1 引数に取らない関数を定義すると、その構造体の __関連関数 (associated function)__ として扱われます。
他の言語では static メソッドと呼ばれているもので、呼び出し時にインスタンスを必要としません。
関連関数を呼び出すには __`構造体名::関数名`__ という構文を使用します（C++ の static メソッドと同様です）。

```rust
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
```


所有権 (ownership) と参照と借用
----

Rust においてヒープ上で管理される値は必ず 1 つの所有者となる変数と結びついており、その変数がスコープから外れるときに値が破棄されます。
代入演算子 (`=`) を使うと、所有権が別の変数に移動します。

```rust
// ヒープ上に String インスタンスが確保され、変数 s1 が所有者 (owner) となる
let s1 = String::from("Hello");

// s1 の所有権が s2 に移動 (move) し、s1 は無効化される
let s2 = s1;

// コンパイルエラー（ここでは s1 はもう使えない）
println!("{}, {}", s1, s2);
```

次のように `clone` メソッドで明示的なディープコピーを行えば、所有権は移動しません。
ヒープ上の新しい領域に値がコピーされ、それぞれの変数が別の値の所有者となるからです。

```rust
let s1 = String::from("Hello");
let s2 = s1.clone();  // ディープコピー

// s1 も s2 も独立した値の所有者なので両方アクセスできる
println!("{}, {}", s1, s2);
```

`i32` 型などのプリミティブ型に関しては、常にスタック上に値が配置されるので、どの変数も唯一の所有者となることはありません。

```rust
let a = 100;
let b = a;
// ここで a も b も使える
```

プリミティブ型であっても、次のように `Box` 型でラップすることでヒープ上で管理することができます。

```rust
let x = Box::new(7);  // x が Box<i32> を所有する
let y = x;  // 所有権が y に移動し、x は無効化される
```

所有権の移動 (move) は、関数の引数として変数値を渡す場合や、関数の戻り値で変数値を返す場合にも発生します。
この振る舞いは煩わしいことが多いので、型や変数に __`&`__ を付けて __参照渡し__ にすることで、所有権の移動を抑制できるようになっています。
Rust では、ある変数の参照を作ることを __借用 (borrow)__ と呼びます。

```rust
// パラメーターを参照にすることで、所有権の移動を防ぐ
// TODO: 実際には文字列は &str 型で受け取った方がよい（後述）
fn calc_length(s: &String) -> usize {
    s.len()
}

let s = String::from("Hello");
let len = calc_length(&s); // 値を借用しているので s の所有権は奪われない（参照渡し）
// ここでも s は使用可能
```

参照経由で値を変更するには、__`&mut`__ を付けて可変の参照を作成する必要があります（この操作を __可変借用 (mutable borrow)__ と呼びます）。

```rust
// 可変参照で String を受け取って内容を変更する
fn add_suffix(s: &mut String) {
    s.push_str("BBB")
}

let mut s = String::from("AAA");
add_suffix(&mut s); // 可変借用 (mutable borrow) して作った可変参照を渡す
println!("{}", s);  //=> "AAABBB"
```

ちなみに、ある変数の可変参照は 1 度に 1 つまでしか作れないという制約があります。
これは、複数箇所からの同時変更による競合を防ぐための Rust の仕様です。
また、通常の借用 (`borrow`) で不変な参照を保持しているときも、可変参照を作ることはできません。
不変だと思って参照している値が、別の場所から変更されては困るからです。
不変な参照は同時にいくつでも作成できます。
このあたり振る舞いは、一般的に Read-Write Lock と呼ばれているデザインパターンそのものです。

関数内で作成したインスタンスを戻り値として返す場合は、参照ではなく通常の型で返すのが一般的です。
関数の終わりで所有権が移動するのは正しい振る舞いだからです。

```rust
fn create_message() -> String {
    let s = String::from("Hello");
    s  // 呼び出し側に所有権が移動する (OK)
}
```


スライス
----

スライスは、配列などのコレクション要素を部分的に参照（借用）するための構文です。
取得した参照は、不変参照になります。
例えば、次の例では `i32` 配列のスライスを取得しており、その型は __`&[i32]`__ です。

```rust
let arr = [0, 1, 2, 3, 4];

let a1 = &arr[1..4];  //=> [1, 2, 3] （&[i32] 型）
let a2 = &arr[..3];   //=> [0, 1, 2] （&[i32] 型）
let a3 = &arr[2..];   //=> [2, 3, 4] （&[i32] 型）
```

文字列のスライスを作成することもできます。
文字列スライスも不変参照であり、__`&str`__ 型として扱われます。
`&str` は文字列の一部を参照するための不変 (immutable) な型として用意されており、その可変バージョン (`&mut str`) は存在しません。

```rust
let s = "ABCDE";  // 実はこれも `&str` 型

let s1 = &s[1..4];  //=> "BCD" （&str 型）
let s2 = &s[..3];   //=> "ABC" （&str 型）
let s3 = &s[2..];   //=> "CDE" （&str 型）
```

文字列スライス（文字列の不変参照）の型 `&str` は、関数のパラメーターとして文字列を受け取るときによく使われます。
`&str` 型で文字列を受け取るようにしておけば、文字列リテラル (`&str`) と `String` オブジェクト（から生成した参照）のどちらでも受け取れるようになります。

```rust
// 文字列は &str 型で受け取るとよい
fn first_two_chars(s: &str) -> &str {
    &s[..2]
}

fn main() {
    println!("{}", first_two_chars("ABC"));  // 文字列リテラルはそのまま渡せる

    let s1 = "ABC";  // &str 型
    println!("{}", first_two_chars(s1));  // &str なのでそのまま渡せる

    let s2 = String::from("ABCDE");  // String 型
    println!("{}", first_two_chars(&s2[..]));  // 文字列スライス (&str) にして渡す
    println!("{}", first_two_chars(&s2));  // &String も &str として解釈してくれる
}
```


列挙型 (enum) と match
----

Rust の列挙型 (enum) は、各列挙子 (variant) に任意の値を保持することができるので非常に強力です。
列挙型にはメソッドを追加することさえできます。

### 構造体の基本

```rust
```



その他
----

- 標準ライブラリ（`str` など）はインクルードなしでデフォルトで使える
