---
title: "算術演算子を定義してオブジェクトに + や += を適用できるようにする"
date: "2019-07-16"
---

Kotlin では、`+` や `+=` などの演算子をオーバーロードして、独自の振る舞いを定義することができます（Java では演算子のオーバーロードはできません）。

ここでは、様々な演算子のオーバーロードの例を示すために、下記のような複素数を保持する簡単なデータクラスを使用することにします。

```kotlin
data class Complex(val re: Int, val im: Int)
```


二項演算子のオーバーロード (Binary arithmetic operators)
----

Kotlin で `a + b` のような演算を可能にする二項演算子を定義するには、`plus` や `minus` などの **演算子関数** を実装します。

| 使用する演算子 | 定義する演算子関数 |
| ---- | ---- |
| `+` | `plus` |
| `-` | `minus` |
| `*` | `times` |
| `/` | `div` |
| `%` | `mod` |

下記は、`+` 演算子をオーバーロードする例です。
演算子関数を定義するときは、モディファイアとして **`operator`** を付けることに注意してください（同名の通常関数の定義と区別するためです）。

```kotlin
data class Complex(val re: Int, val im: Int) {
    operator fun plus(other: Complex): Complex {
        return Complex(re + other.re, im + other.im)
    }
}
```

これにより、独自オブジェクト同士の `+` 演算子による演算が可能になります。

```kotlin
val c1 = Complex(1, 2)
val c2 = Complex(3, 4)
println(c1 + c2)  //=> Complex(re=4, im=6)
```

上記では、メンバ関数として演算子関数を定義しましたが、拡張関数として定義することもできます。
この方法であれば、既存の外部ライブラリが提供するクラスの演算子をオーバーロードすることが可能です。

```kotlin
operator fun Complex.plus(other: Complex): Complex {
    return Complex(re + other.re, im + other.im)
}
```

ここまでは、`Complex` インスタンス同士の演算を定義していましたが、別の型との演算を定義することもできます。
次の例では、各プロパティを、指定した `Int` 値倍に変化させる `*` 演算子 (`times`) を定義しています。

```kotlin
data class Complex(val re: Int, val im: Int) {
    operator fun times(scale: Int): Complex {
        return Complex(re * scale, im * scale)
    }
}

fun main() {
    val c1 = Complex(1, 2)
    val c2 = c1 * 10
    println(c2)  // => Complex(re=10, im=20)
}
```

このように違う型同士の演算を定義した場合、**演算子の左右の値は交換可能ではない** ことに注意してください。
例えば、`10 * Complex(1, 2)` という演算を行いたいのであれば、`Int.times(c: Complex)` という演算子関数を定義しておく必要があります。

二項演算子の戻り値を、元のオブジェクトの型とは別のものにすることもできます。

```kotlin
operator fun Char.times(n: Int) = toString().repeat(n)

fun main() {
    println('A' * 3)  //=> "AAA"
}
```


複合代入演算子のオーバーロード (Compound assignment operators)
----

Kotlin では、二項演算子の `+` (`plus`) を定義すると、自動的に複合代入演算子である `+=` も使用できるようになります。
ただし、この場合は変数の値を変更できるようにするために、`val` ではなく `var` で変数定義しておく必要があります。

```kotlin
var c = Complex(1, 2)
c += Complex(3, 4)
println(c)  // => Complex(re=4, im=6)
```

一方、コレクション系クラスやビルダ系クラスでは、要素の追加を行うための `+=` 演算子のみを定義したいことがあります。
このようなケースでは、**`plusAssign`** や **`minusAssign`** という名前の演算子関数を定義します。

| 使用する演算子 | 定義する演算子関数 |
| ---- | ---- |
| `+=` | `plusAssign` |
| `-=` | `minusAssign` |
| `*=` | `timesAssign` |
| `/=` | `divAssign` |
| `%=` | `remAssign` |


下記は、[MutableCollection](https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/collections/MutableCollections.kt) による拡張関数定義の抜粋です。

```kotlin
public inline operator fun <T> MutableCollection<in T>.plusAssign(element: T) {
    this.add(element)
}

public inline operator fun <T> MutableCollection<in T>.minusAssign(element: T) {
    this.remove(element)
}
```

つまり、`a += b` という記述は、`a = a.plus(b)` あるいは `a = a.plusAssign(b)` という呼び出しのどちらかにマッピングされることになります。
`plus` と `plusAssign` の両方が定義されていると、`a += b` という呼び出しはコンパイルエラーになります。
もちろん、`a = a.plus(b)` のように関数名を明示して呼び出せばエラーにはなりませんが、通常は `plus` と `plusAssign` を同時に定義しないようにすべきでしょう。


単項演算子のオーバーロード (Unary operators)
----

下記のような単項演算子をオーバーロードすることもできます。

| 使用する演算子 | 定義する演算子関数 |
| ---- | ---- |
| `+x` | `unaryPlus` |
| `-x` | `unaryMinus` |
| `!x` | `not` |
| `++x`, `x++` | `inc` |
| `--x`, `x--` | `dec` |

次の例では、各プロパティの符号を反転する `-` 演算子を定義しています。

```kotlin
data class Complex(val re: Int, val im: Int) {
    operator fun unaryMinus() = Complex(-re, -im)
}

fun main() {
    val c1 = Complex(1, 2)
    val c2 = -c1
    println(c2)  // => Complex(re=-1, im=-2)
}
```

前置インクリメント (`++x`)、後置インクリメント (`x++`) を使用するには、`inc` 演算子関数を 1 つ定義するだけで OK です。
デクリメントに関しても同様です。

```kotlin
data class Complex(val re: Int, val im: Int) {
    operator fun inc() = Complex(re + 1, im + 1)
}

fun main() {
    var c = Complex(1, 2)
    println(c++) // => Complex(re=1, im=2)
    println(++c) // => Complex(re=3, im=4)
}
```

