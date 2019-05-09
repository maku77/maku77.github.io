---
title: "クラスを定義する (class)"
date: "2019-04-24"
---

クラス定義の基本
----

Kotlin のクラス定義は Java と同様に **`class`** キーワードを使用しますが、**デフォルトで public final 扱い**という違いがあります。

* デフォルトでは全クラスからアクセス可能 (public)
* デフォルトでは継承できない (final)

次の `Book` クラスは、リードオンリーな `title` プロパティ持つ、シンプルなクラスの実装例と使用例です。

#### リードオンリーなプロパティを持つクラス

```kotlin
class Book(val title: String)

val b = Book("Title1")
println(b.title)  //=> Title1
```

詳しくは後述しますが、Kotlin にはメソッドを簡潔に記述するための仕組みがたくさん用意されています。
メソッド実装などの記述が必要ない場合は、上記のようにクラス本体部分を示す `{ }` ブロックすら省略して記述することができます。
Java とは異なり、コンストラクタを呼び出すときの `new` キーワードも省略できます（というより `new` は存在しません）。

上記は `title` プロパティをリードオンリーとして定義していますが、プロパティの値を書き換え可能にするには、`val` キーワードを `var` キーワードに置き換えるだけで済みます。

#### 書き換え可能なプロパティを持つクラス

```kotlin
class Book(var title: String)

val b = Book("Title1")
b.title = "Title2"
println(b.title)  //=> Title2
```

上記のように `b.title` という形で `title` フィールドにアクセスできるのは、内部で getter/setter メソッドが定義されて呼び出されているからです。
`title` フィールドそのものが `public` になっているわけではなく、あくまで `public` な getter/setter メソッドが暗黙的に呼び出されています。
この `Book` クラスを Java のコードから使用する場合、`title` フィールドへのアクセスは、`b.getTitle()`、`b.setTitle("...")` のように記述することになります。


コンストラクタを定義する
----

### コンストラクタは 2 種類ある

Kotlin のクラスのコンストラクタには、**プライマリ・コンストラクタ (primary constructor) とセカンダリ・コンストラクタ (secondary constructor)** の 2 種類があります。

プライマリ・コンストラクタ
: インスタンス生成時に必ず呼び出されるコンストラクタ。class 本文の外で定義する。

セカンダリ・コンストラクタ
: コンストラクタのバリエーション。プライマリ・コンストラクタが定義されている場合、セカンダリ・コンストラクタから直接的、あるいは間接的にプライマリ・コンストラクタを呼び出す必要がある（プライマリ・コンストラクタはいかなる場合にも呼び出される）。class 本文で定義する。

例えば、プライマリ・コンストラクタと、セカンダリ・コンストラクタを 2 つ持つクラスがあった場合、コンストラクタの呼び出しは下記のような感じで、**プライマリ・コンストラクタによる初期化が起点となってオブジェクトの構築が行われます**。
コード上はセカンダリ・コンストラクタからプライマリ・コンストラクタを呼び出しているかのように見えるかもしれませんが、あくまで実行順序はプライマリ・コンストラクタが先です。

* <b>プライマリ（＋初期化ブロック）</b>
* <b>プライマリ（＋初期化ブロック）</b> → セカンダリA
* <b>プライマリ（＋初期化ブロック）</b> → セカンダリA → セカンダリB

上記のセカンダリ・コンストラクタの説明では、「バリエーション」という言葉を使いましたが、コンストラクタのパラメータとしてデフォルト引数の仕組みが使えるので、プライマリ・コンストラクタだけでもある程度の生成の「バリエーション」を持たせることは可能です。

### プライマリ・コンストラクタと初期化ブロック

プライマリ・コンストラクタは、インスタンスの生成時に必ず呼び出されます。
省略記法がいろいろありますが、一番冗長な書き方から順番に見ていきます。

プライマリ・コンストラクタが受け取るパラメータは、クラス名の後ろに続けて `constructor(...)` という形で宣言します。
プライマリ・コンストラクタが呼び出されると、(1) **プロパティの定義部分 (property initializer)**、(2) **`init` で囲まれた初期化ブロック (initializer block)** が順番に実行されます。
(1) と (2) の中では、プライマリ・コンストラクタに渡されたパラメータを直接参照することができます。
下記の `Book` クラスは、プライマリ・コンストラクタで 1 つの値を受け取り、それをリードオンリーなプロパティとして保持しています。

```kotlin
class Book constructor(title: String) {
    // (1) プロパティ定義 (property initializer)
    val title: String

    // (2) 初期化ブロック (initializer block)
    init {
        this.title = title
    }
}

// 使用例
val b = Book("タイトル")
println(b.title)
```

この例のように、初期化ブロック内で単純なプロパティ代入しか行っていない場合は、初期化ブロック (`init`) の記述を省略して、プロパティの定義部分で値の設定まで済ませてしまうことができます。
プロパティ定義部分では型推論が働くので、型の指定を省略することができます。

```kotlin
class Book constructor(title: String) {
    val title = title
}
```

さらに、プライマリ・コンストラクタに、アノテーションや可視性の指定がない場合は、**`constructor` キーワードを省略**することができます。

```kotlin
class Book(title: String) {
    val title = title
}
```

さらに、パラメータで受け取った値を、プロパティの定義部分で単純に代入しているだけであれば、パラメータ名の前に `val`、あるいは `var` キーワードを付けることによって、**パラメータとプロパティの定義を同時に行ってしまうことができます**。
`val` を付けた場合はリードオンリーなプロパティとなり、コンストラクタで設定された値から変更することができなくなります。
最終的に `Book` クラスは下記のようにシンプルに記述できることになります。

```kotlin
class Book(val title: String)
```

コンストラクタの（パラメータの）定義を省略した場合は、パラメータを取らないプライマリ・コンストラクタが自動的に生成されます。

```kotlin
class Book {
    var title: String = "Unknown"
    init {
        println("初期化ブロックはいつでも書けるよ")
    }
}

val b = Book()
b.title = "ぴよぴよ"
println(b.title)  //=> ぴよぴよ
```

### デフォルト値と名前付き引数

コンストラクタのパラメータには、**デフォルト値**を持たせることができます（通常の関数と同様です）。
下記の例では、2 つのパラメータにデフォルト値を設定しています。

```kotlin
class Book(val title: String = "無題", val author: String = "著者不明") {
    override fun toString() = "$title, $author"
}
```

コンストラクタの呼び出し時に引数を省略すると、デフォルト値として指定した値が使用されます。

```kotlin
println(Book())  //=> 無題, 著者不明
println(Book("ああ"))  //=> ああ, 著者不明
println(Book("ああ", "まく"))  //=> ああ, まく
```

また、コンストラクタに引数を渡す時に `パラメータ名=値` という形で指定すると、任意の順序でパラメータを指定することができます （**名前付き引数**）。

```kotlin
println(Book(author = "まく", title="ああ"))  //=> ああ, まく
```

型が同じパラメータが複数ある場合、名前付き引数の仕組みを使うと、引数の順番を間違えるといったミスを防ぐことができます。

### セカンダリ・コンストラクタ

プライマリ・コンストラクタだけではカバーしきれないような、パラメータのバリエーションを持たせたい場合は、**セカンダリ・コンストラクタ (secondary constructor)** を定義します。
Kotlin にはデフォルト値や名前付き引数の仕組みがあるので、多くの場合はプライマリ・コンストラクタだけで十分ですが、フレームワークで定義されているクラスを継承するようなケースで必要になったりします（親クラスのコンストラクタに合わせてパラメータ定義する必要があったりするため）。

セカンダリ・コンストラクタは、**クラス本体部分で `constructor` キーワードを使って定義**します。
下記の `Indenter` クラスは、テキストの前にインデントを入れて出力するためのクラスです。
コンストラクタで渡した文字数分のスペース、あるいは、渡された文字列そのものをインデントとして出力します。
パラメータに応じて異なる初期化処理を行う必要があるため、2 つのセカンダリ・コンストラクタを作成して、それぞれの初期化処理を定義しています。

```kotlin
class Indenter {
    val text: String
    constructor(size: Int) {
        text = " ".repeat(size)
    }
    constructor(text: String) {
        this.text = text
    }
    fun puts(message: String) {
        println("$text$message")
    }
}

fun main() {
    Indenter(4).puts("Hello")  //=> "    Hello"
    Indenter("----").puts("Hello")  //=> "----Hello"
}
```

上記のように、パラメータ付きのセカンダリ・コンストラクタのみを定義した場合、パラメータなしのプライマリ・コンストラクタが自動生成されることはありません。

```kotlin
Indenter()  // NG（パラメータなしのコンストラクタはない）
```

プライマリ・コンストラクタとセカンダリ・コンストラクタの両方を定義する場合、**セカンダリ・コンストラクタから `this` を使って、間接的、あるいは、直接的にプライマリ・コンストラクタを呼び出しておく必要があります**。
プライマリ・コンストラクタはいかなる場合にも呼び出されるからです。

下記の例では、`Int` 値を受け取るセカンダリ・コンストラクタから、`String` 値を受け取るプライマリ・コンストラクタを呼び出しています（実装部分は空なので後ろの `{}` を省略しています）。

```kotlin
class Indenter(val text: String) {
    // プライマリ・コンストラクタを呼び出す
    constructor(size: Int) : this(" ".repeat(size))
}
```

`this` キーワードは、プライマリ・コンストラクタの呼び出しだけでなく、別のセカンダリ・コンストラクタの呼び出しにも使用できます。
下記の例では、1 つ目のセカンダリ・コンストラクタから、2 つ目のセカンダリ・コンストラクタを呼び出しています。
結果的にプライマリ・コンストラクタの呼び出しにつながるため、このようなコンストラクタ定義も正しいものとなります（これを「間接的」なプライマリ・コンストラクタの呼び出しと呼んでいます）。

```kotlin
class Indenter(val text: String) {
    // 別のセカンダリ・コンストラクタを呼び出すセカンダリ・コンストラクタ
    constructor(size: Int) : this(size, " ")

    // プライマリ・コンストラクタを呼び出すセカンダリ・コンストラクタ
    constructor(size: Int, text: String) : this(text.repeat(size))
}
```

セカンダリ・コンストラクタを呼び出した場合でも、**先にプライマリ・コンストラクタの処理が実行される**ことに注意してください。
下記のような順番で実行されていきます。

1. プライマリ・コンストラクタによるフィールドの初期化
2. プライマリ・コンストラクタの初期化ブロック (`init`)
3. セカンダリ・コンストラクタの本文部分

下記のようなテストコードを実行してみれば、処理の流れを理解できると思います。

```kotlin
class Book(title: String) {
    // (1) プライマリ・コンストラクタによるフィールドの初期化
    val title = title
    var author = "作者不明"

    // (2) プライマリ・コンストラクタの初期化ブロック
    init {
        println("---- init ----")
        println(author)
    }

    // (3) セカンダリ・コンストラクタの中身は最後に実行される
    constructor(title: String, author: String) : this(title) {
        println("---- secondary ----")
        println(this.author)
        this.author = author
    }
}

fun main() {
    // セカンダリ・コンストラクタを呼び出し
    val b = Book("タイトル", "まく")
    println("---- main ----")
    println(b.author)  //=> 作者
}
```

#### 実行結果

```
---- init ----
作者不明
---- secondary ----
作者不明
---- main ----
まく
```

### コンストラクタの private 化

プライマリ・コンストラクタをクラスの外部から呼び出せないようにするには、`constructor` の前に **`private`** キーワードを付けます。
このように可視性を付加する場合、`constructor` の記述は省略できなくなります。

```kotlin
class Book private constructor() {}
```

`private` なコンストラクタしか存在しないクラスのインスタンスを生成するには、クラスのコンパニオン・オブジェクト (companion object) からコンストラクタを呼び出す必要があります。
コンパニオン・オブジェクトに定義した関数は、インスタンスがなくても呼び出すことができるため、外部から間接的に private なコンストラクタを呼び出すための入り口として使用できます。

コンストラクタのパラメータが複雑な場合、直観的な名前のついた**ファクトリ・メソッドをコンストラクタの代わりに提供する**と可読性を向上させることができます。
下記の `Book` クラスは、インスタンスの生成をファクトリ・メソッド経由で行うことを強制しています。

```kotlin
class Book private constructor(val title: String, val price: Int) {
    companion object {
        fun newFreeBook(title: String) = Book(title, 0)
        fun newDamnedBook(title: String) = Book(title, -1)
    }
}

fun main() {
    val b = Book.newFreeBook("はじめてのKotlin")
    println(b.title)
    println(b.price)
}
```

この例だとコンストラクタのパラメータがシンプルすぎて、ファクトリ・メソッドを導入するメリットは感じられないかもしれませんが、こういった設計パターンがあることを覚えておくといつか役に立つでしょう。
こういった抽象度の高いデザインパターンは、Kotlin に限らず、一般的なベストプラクティスとして受け入れられています。

プライマリ・コンストラクタを private にする目的が、**シングルトンを作成したいということであれば、代わりに Kotlin の `object` 宣言を使用する**と簡潔な記述が可能です（後述）。
単なる静的なユーティリティ関数を集めただけのユーティリティ・クラスを作りたいということであれば、パッケージのトップレベルに関数を定義してしまうのが手っ取り早いです。



カスタムアクセサを定義する (set() / get())
----

getter メソッドや setter メソッドの内容を明示的に実装したい場合は、下記のように **`get()`** や **`set()`** メソッドを定義します。
この例では読み出し専用の `isExpensive` プロパティを定義しています。

```kotlin
class Book(val title: String, var price: Int) {
    val isExpensive: Boolean
        get() {
            return price > 1000
        }
}

// 使用例
val b = Book("Kotlin ABC", 1500)
println(b.isExpensive)
```

上記の `isExpensive` の実装は、1 つの式の評価結果を `return` しているだけなので、expression-body の構文を使って、下記のように簡潔に記述することもできます。

```kotlin
val isExpensive: Boolean
    get() = price > 1000
```


データクラスを定義する (data class)
----

単純なデータを保持するだけのクラス (value object) は、Kotlin では下記のように **`data`** キーワードを使用して非常にシンプルに定義することができます。

```kotlin
data class Book(var title: String)
```

データクラスとして定義したクラスには、下記のようなメソッドが自動的に定義されます。

* `hashCode()` メソッド
* `equals()` メソッド
* `clone()` メソッド

このおかげで、データクラスのオブジェクトは、そのままハッシュ系のコンテナクラスに格納することができます。


継承可能なクラスを作成する
----

### open class

Kotlin のクラス定義を行うと、デフォルトでは継承できないようになっています。
**継承可能なクラスとして定義するには、`open` モディファイアを付ける**必要があります。
さらに、その中で**オーバーライド可能なメソッドにも `open` モディファイアを付ける**必要があります。

下記の `Animal` クラスは継承可能であり、`cry` メソッドがオーバーライドできるようになっています。

```kotlin
open class Animal(val name: String) {
    fun greet() {
        print("私は${name}です。")
        cry()
    }
    open fun cry() {
        println("ウー！")
    }
}
```

下記の `Cat`、`Dog` クラスは `Animal` クラスを継承し、`cry` メソッドをオーバーライドしています。
**オーバーライドしていることを示すために、メソッドの定義時に `override` キーワードを付ける**必要があります。

```kotlin
open class Cat : Animal("猫") {
    override fun cry() {
        println("ニャー！")
    }
}

open class Dog : Animal("犬") {
    override fun cry() {
        println("ワンワン！")
    }
}

fun main() {
    Animal("動物").greet()  // 私は動物です。ウー！
    Cat().greet()  // 私は猫です。ニャー！
    Dog().greet()  // 私は犬です。ワンワン！
}
```

`override` の付いたメソッドはデフォルトで `open` な状態になっており、さらにその子クラスでオーバーライドすることが可能です。
**さらにオーバーライドされることを防ぐには、明示的に `final` キーワードを付けて `override` する**必要があります。

```kotlin
open class Cat : Animal("猫") {
    final override fun cry() {
        println("ニャー！")
    }
}
```

サブクラスのコンストラクタ呼び出しからは、間接的、あるいは直接的に親クラスのコンストラクタを呼び出す必要があります。
親クラスがパラメータなしの（自動生成された）コンストラクタしか持たない場合でも、その**パラメータなしのコンストラクタを使用するということを示すために、後ろに `()` を付けて呼び出しておく必要があります**。

```kotlin
open class Parent

class Child : Parent() {
    // ...
}
```

サブクラスがセカンダリ・コンストラクタしか持たない場合は、**`super`** キーワードを使って、親クラスのいずれかのコンストラクタを呼び出すようにすれば OK です。

```kotlin
open class Parent(val name: String) {
    constructor(dummy: Int) : this("ほげ")
}
class Child : Parent {
    constructor(name: String) : super(100)
}
```

### 抽象クラスを作成する (abstract class)

Kotlin でも Java と同様に、**`abstract` キーワードを使って抽象クラスを定義することができます**。
抽象クラスは 1 つ以上の抽象メソッド（実装のないメソッド）を定義することができ、このメソッドにも `abstract` キーワードを付けます。
`abstract` キーワードの付いたメソッドはオーバーライドすることを前提としているため、`open` を付ける必要はありません。

```kotlin
abstract class Parent {
    // オーバーライドして実装しなければいけない抽象メソッド（実装がない）
    abstract fun abstractMethod()

    // オーバーライド可能なメソッド（実装がある）
    open fun openMethod() { println("openMethod of Parent") }

    // オーバーライドできない通常のメソッド
    fun normalMethod() { println("normalMethod of Parent") }
}

class Child : Parent() {
    override fun abstractMethod() { println("abstractMethod in Child") }
    override fun openMethod() { println("openMethod in Child") }
}

fun main() {
    val obj = Child()
    obj.abstractMethod()  //=> abstractMethod in Child
    obj.openMethod()  //=> openMethod in Child
}
```

上記のサンプルを見ると分かるように、`abstract` メソッドと `open` メソッドの使い方にほとんど差はありません。
違いは、`abstract` メソッドがデフォルトで実装を持っていないということです。

