---
title: "Rust の文法: 制御構文 (if, while, loop, for)"
url: "p/22cnw7f/"
date: "2023-02-13"
tags: ["Rust"]
---

条件分岐
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


ループ
----

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

