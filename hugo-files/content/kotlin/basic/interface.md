---
title: "Kotlinメモ: インタフェースを定義する (interface)"
url: "p/ep23xid/"
date: "2019-04-24"
tags: ["kotlin"]
aliases: [/kotlin/basic/interface.html]
---

インタフェースの基本
----

Kotlin でのインタフェース定義は、Java と同様に **`interface`** キーワードを使用します。
下記の例では、`Command` インタフェースを定義し、それを `GreetCommand` クラスと `ExplodeCommand` クラスが実装しています。

```kotlin
interface Command {
    fun execute()
}

class GreetCommand(val name: String) : Command {
    override fun execute() = println("Hi, $name")
}

class ExplodeCommand : Command {
    override fun execute() = println("Boom!")
}

fun executeAllCommands(commands: List<Command>) {
    commands.forEach { it.execute() }
}

fun main() {
    val commands = listOf(
        GreetCommand("Maku"),
        GreetCommand("Hemu"),
        ExplodeCommand())
    executeAllCommands(commands)
}
```

#### 実行結果

```
Hi, Maku
Hi, Hemu
Boom!
```

インタフェースのメソッドはオーバーライドされることを前提としているため、`fun` の前に `open` を付ける必要はないことに注意してください。
**オーバーライドする側のメソッドには `override` を付ける**必要があります（Java のような `@Override` アノテーションは使いません）。


インタフェースの継承
----

インタフェースは他のインタフェースを継承することができます。

```kotlin
interface Command {
    fun execute()
}

interface UndoableCommand : Command {
    fun undo()
}
```

インタフェース（およびクラス）は、複数のインタフェースを継承（および実装）することができます。
下記の `DrawCommand` クラスは、`Command` インタフェースと `Undoable` インタフェースを実装しています。

```kotlin
interface Command {
    fun execute()
}

interface Undoable {
    fun undo()
}

class DrawCommand: Command, Undoable {
    override fun undo() {
        // ...
    }
    override fun execute() {
        // ...
    }
}
```

あるオブジェクトが特定のインタフェースを継承しているかどうかを調べるには、**`is`** を使って、`if (obj is InterfaceName)` のように記述します。
下記の例では、リストに含まれている要素が `Number` であるかをひとつずつチェックしています。

```kotlin
fun printAllNums(maybeNumbers: List<Any>) {
    for (e in maybeNumbers) {
        if (e is Number) {
            println(e)
        }
    }
}

fun main() {
    val list = listOf(100, "AAA", 200, "BBB")
    printAllNums(list)  //=> 100 200
}
```


マーカーインタフェース
----

Java の `Serializable` インタフェースなどは、メソッドを何も持っていないマーカーインタフェースです。
Kotlin では、マーカーインタフェースは下記のようにシンプルに定義できます。

```kotlin
interface MyMarker
```

あるオブジェクトがマーカーインタフェースを持っているかを調べるには、`is` キーワードを使用します。

```kotlin
fun checkMarker(obj: Any) {
    if (obj is MyMarker) {
        println("MyMarker を実装しています")
    }
}

fun main() {
    class MyClass : MyMarker
    checkMarker(MyClass())
}
```


インタフェースのデフォルト実装
----

Kotlin のインタフェースは、Java8 のインタフェースのデフォルトメソッドと同様に、デフォルトの実装を持つことができます。
下記の `Command` インタフェースの `help` メソッドはデフォルト実装を持っています。

```kotlin
interface Command {
    fun execute()
    fun help() = println("No help exists")
}

class MyCommand : Command {
    override fun execute() = println("I am MyCommand")
}

fun main() {
    val a = MyCommand()
    a.execute()  //=> I am MyCommand
    a.help()  //=> No help exists
}
```

Java でインタフェースにデフォルト実装を持たせるときは `default` キーワードが必要でしたが、Kotlin では通常のメソッドと同様に定義するだけで OK です。


### デフォルト実装の衝突の回避

インタフェースのデフォルト実装が可能になるということは、実装の多重継承ができることと同等の意味を持ちます。
これは、実装の衝突という問題を生みます。

下記は、2 つのインタフェースが、同じ `help` という名前のデフォルト実装を持つ例です。

```kotlin
interface Menu {
    fun help() = println("Menu.help")
}

interface Command {
    fun help() = println("Command.help")
}

class FileOpenMenu : Menu, Command {
    // コンパイルエラー！
}
```

`FileOpenMenu` クラスは `help()` のデフォルト実装を使おうとしますが、`Menu.help()` を呼び出せばいいのか、`Command.help()` を呼び出せばいいのか判断できないため、上記のコードはコンパイルエラーになります。

次のように、自分自身でオーバーライドしてしまえば問題ありません。

```kotlin
class FileOpenMenu : Menu, Command {
    override fun help() = println("FileOpenMenu.help")
}
```

どちらかのデフォルト実装をそのまま呼び出したい場合は、**`super`** キーワードを使って次のように記述します。

```kotlin
class FileOpenMenu : Menu, Command {
    override fun help() = super<Menu>.help()
}
```

デフォルト実装を呼び出す位置に制約はないので、次のようにして両方のデフォルト実装を呼び出してしまうこともできます。

```kotlin
class FileOpenMenu : Menu, Command {
    override fun help() {
        super<Menu>.help()
        super<Command>.help()
    }
}
```


インタフェースで抽象プロパティを定義する
----

Kotlin のインタフェースには**抽象プロパティ (abstract property)** を持たせることができます（Kotlin のプロパティは実質 getter と setter メソッドの省略記法なので、当然といえば当然ですが）。
下記の `Book` クラスは、`description` という抽象プロパティを定義しています。

```kotlin
interface Book {
    val description: String
}
```

このインタフェースには、`description` プロパティが示す実体は存在しないことに注意してください。
このインタフェースを実装するクラスで、`description` プロパティを実装 (`override`) する必要があります。
下記のようにパラメータでプロパティの実体を定義してしまってもよいし、

```kotlin
class RealBook(override val description: String) : Book
```

下記のように getter メソッドを用意しても OK です。

```kotlin
class RealBook(bookId: Int) : Book {
    override val description: String
        get() = getBookDescription(bookId)
}
```


コラム: インタフェースの実装とクラスの継承の見分け方
----

Java では、インタフェースの実装には `implements`、クラスの継承には `extends` というように、キーワードを使い分けていました。
一方、Kotlin では、どちらも非常に似たような文法を使用します。

- `class Foo : MyInterface` ... インタフェースの実装
- `class Bar : Parent()` ... クラスの継承

**見分けるポイントは、後ろに括弧 `()` があるかないか**です。
クラスを継承する場合は、親クラスのコンストラクタを指定するための括弧が必要になります。
パラメータを取らないプライマリ・コンストラクタを呼び出す場合は、上記のように空の括弧 `()` を指定することになります。

下記は、インタフェースの実装と、クラスの継承を同時に行うクラスの実装例です。

```kotlin
interface Command {
    fun execute()
}

open class Parent {
    open fun hello() = println("Parent")
}

class Child : Command, Parent() {
    override fun execute() = println("Child.execute")
    override fun hello() = println("Child.hello")
}
```

`Child` クラスの定義に着目してください。
インタフェースの実装を示す部分 (`Command`) には括弧がついておらず、クラスの継承を示す部分 (`Parent()`) には括弧がついています。

