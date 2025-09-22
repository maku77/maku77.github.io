---
title: "Kotlinメモ: 変数を定義する (val, var)"
url: "p/qzkfnsq/"
date: "2019-01-22"
tags: ["kotlin"]
aliases: [/kotlin/basic/var.html]
---

変数の定義
---

Kotlin で変数を定義するには、**`val`** キーワード（あるいは **`var`** キーワード）を使用します。
変数定義時に値を初期化する場合は、コンパイラが型を判断してくれるので、多くの場合は型の記述を省略することができます（この機能を型推論［type inference］と言います）。

```kotlin
val s = "Hello"  //=> val s: String = "Hello"
val n = 100      //=> val n: Int = 100
val f = 7.5e6    //=> val f: Double = 7.5e6 (= 7.5x10^6)
```

ただし、変数の定義と同時に初期値を指定しない場合は、必ず型の指定が必要です。
型は、変数名の後ろに **`: 型名`** という形で指定します（Pascal や Scala、Swift などと同じ）。

```kotlin
val name: String
name = "Maku"
println("Hello, $name!")
```


val と var の違い
---

**`val`** と **`var`** の違いは、その変数に再代入が可能かどうかです。

* **`val`**: 再代入できない参照を保持するための変数 (immutable reference)。Java の `final` 変数に相当。value の略。
* **`var`**: 再代入可能な変数 (mutable reference)。variable の略。

```kotlin
val a = 1
var b = 1
a = 2  // NG (Val cannot be reassigned)
b = 2  // OK
```

使い分けの方針としては、変数を定義するときはまずは再代入できない `val` を使用することを考え、必要なケースでのみ `var` を使用するのがよいでしょう。
こうすることで、コードレビュー時などに変数の変化を追う必要性が下がるため、保守性が高くなります。

Java を使って同じようなことを実現しようとすると、コードの中に `final` があふれてしまって逆に読みにくくなってしまうのですが、Kotlin の場合は `val` も `var` もタイプ量は一緒なので、積極的に `final` 相当の `val` を使用してコーディングすることができます。


null 許容型の変数
---

Kotlin の変数はオブジェクトの参照を格納するものですが、Java と異なりデフォルトでは `null` を代入することが禁止されています。
`null` を代入することが可能な変数を定義するには、型名の後ろに `?` を付けて定義します。

```kotlin
var s: String? = null
s = "Hello"
```

ちなみに、このルールは関数のパラメータでも同様です。

```kotlin
fun greet(name: String?) {  // null も渡せる
    println("Hello, $name")
}
```


代入は文 (statement) である
----

Kotlin の代入 (`=`) は、式 (expression) ではなく文 (statement) として扱われます。
文 (statement) は評価後の値を持ちません。
つまり、次のような、ある変数への代入結果を、そのまま別の変数へ代入することはできないということです（コンパイル時に構文エラーになります）。

```kotlin
var a = b = 100  // ERROR
```

一方、Java では代入は式 (expression) として扱われるので、このような代入の連鎖が許されています。
Kotlin ではこのような書き方を意図的に禁止することで、代入 (`=`) と比較 (`==`) の記述ミスを避けることを狙っています。

