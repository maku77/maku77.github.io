---
title: "シールクラスで継承可能なクラスを制限する (sealed class)"
date: "2019-05-13"
lastmod: "2021-06-01"
---

シールクラスとは
----

クラスを定義するときに、**`sealed`** キーワードを使ってマークするとそのクラスは**シールクラス (sealed class)** となり、そのクラスのサブクラスとなれるクラスを制限することができます。
例えば、`Screen` クラスのサブクラスが、`Screen.Main`、`Screen.ProductList` しか存在しないことを明示することができます。

Kotlin はシールクラスを列挙型クラス (enum class) の強化版のようなものだと位置付けています。
enum 型は各インスタンスがシングルトンとしてのみ存在可能ですが、シールクラスのサブクラスは、シングルトンにすることもできますし、個別のインスタンスとして生成することもできます。

シールクラスは抽象クラスと同様の性質を持ちます。
デフォルトで継承可能 (`open`) なクラスとして定義され、`abstract` なメソッド、プロパティを含むことができます。
インスタンスを生成するときは、必ずサブクラスのインスタンスとして生成しなければいけません。


シールクラスの利用例
----

シールクラスは利用例を見たほうが理解しやすいと思います。
例えば、列挙型クラスのようなものを作りたいのだけれど、場合によってはパラメータを渡して亜種となるオブジェクトを作らなければいけない、といったケースで利用できます。

ここでは、アプリケーションの「画面状態」を示す `Screen` クラスを考えてみます。
画面状態には下記の種類があります。

1. `Screen.Main` -- メイン画面
2. `Screen.ProductList` -- 全商品のリスト画面
3. `Screen.ProductDetail` -- ある商品の詳細画面（**商品 ID プロパティを含む**）

これらが 3 つの画面状態だけを示せればよいのであれば、従来の列挙クラスで 3 つの要素を定義してやれば解決です。
問題は、3 つ目の `Screen.ProductDetail` が、表示対象となる商品の ID をプロパティとして保持するという点です。
つまり、`Screen.ProductDetail` という 1 つの画面状態だけを示せばよいのではなく、`Screen.ProductDetail(1)` とか、`Screen.ProductDetail(2)` といった異なる画面状態を表現したいということです。

このように、列挙型の個々のオブジェクトに異なるプロパティを持たせたいという場面に出くわしたら、シールクラスの出番です。

```kotlin
sealed class Screen {
    object Main : Screen()
    object ProductList: Screen()
    data class ProductDetail(val productId: Int) : Screen()
}
```

<div class="note">
ここでは、シールクラスのサブクラスをネストして定義していますが、ネストさせる必要は必ずしもありません。
ただ、enum クラスの拡張としてシールクラスを利用するのであれば、このようにネストして定義しておいた方がわかりやすいでしょう。
</div>

上記のようにシールクラスを定義すると、`Screen.Main` や `Screen.ProductList` などのシングルトンは従来の列挙型要素のように扱うことができ、さらに、データクラスとして定義した `Screen.ProductDetail` には自身のプロパティとして `productId` を持たせることができます。

下記は、この `Screen` シールクラスの使用例です。
`transition` 関数に `Screen` オブジェクトを渡すと、その画面へ遷移するというユースケースを想定しています。

```kotlin
// 画面遷移の実装（だと過程）
fun gotoMain() = println("gotoMain")
fun gotoProductList() = println("gotoProductList")
fun gotoProductDetail(id: Int) = println("gotoProductDetail($id)")

// 渡されたスクリーンタイプによって画面遷移する
fun transition(screen: Screen) {
    when (screen) {
        is Screen.Main -> gotoMain()
        is Screen.ProductList -> gotoProductList()
        is Screen.ProductDetail -> gotoProductDetail(screen.productId)
    }
}

fun main() {
    val list = listOf(
        Screen.Main,
        Screen.ProductList,
        Screen.ProductDetail(1),
        Screen.ProductDetail(2)
    )
    list.forEach { transition(it) }
}
```

#### 実行結果

```
gotoMain
gotoProductList
gotoProductDetail(1)
gotoProductDetail(2)
```

上記の `when` 式の記述を見ると分かるように、`else` 節の記述が省略されています。
シールクラスを使用すると、コンパイラがすべてのサブクラス分岐をカバーできていることを把握できるようになるので、`else` 節によるフォールバック記述が必要なくなります。
この仕組みにより、enum 型クラスと同様、`when` 式での記述漏れを防ぐことができます。


（コラム）シールクラスオブジェクトのリストの先頭が null になる問題
----

シールクラスを使い始めた人が時々ハマる問題です。
下記のように、シールクラスで定義したオブジェクトを `companion object` でリスト化しようとすると、先頭の要素がなぜか `null` になります。

```kotlin
sealed class Color(rgb: String) {
    object R: Color("ff0000")
    object G: Color("00ff00")
    object B: Color("0000ff")

    companion object {
        val AvailableColors = listOf(R, G, B)
    }
}

fun main() {
    println(Color.R)
    println(Color.AvailableColors)
}
```

```
Color$R@2a84aee7
[null, Color$G@452b3a41, Color$B@4a574795]
```

これは、オブジェクト初期化時の循環参照が原因になっており、現状は次のように遅延初期化することで解決できます。

```kotlin
companion object {
    val AvailableColors by lazy {
        listOf(R, G, B)
    }
}
```

```
Color$R@2a84aee7
[Color$R@2a84aee7, Color$G@452b3a41, Color$B@4a574795]
```

この話は、[stack overflow でもたまに取り上げられます](https://stackoverflow.com/questions/47648689/) 。


参考
----

* [継承可能なクラスを作成する](./extend.html)

