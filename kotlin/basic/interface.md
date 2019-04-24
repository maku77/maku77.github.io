---
title: "インタフェースを定義する (interface)"
date: "2019-04-24"
---

Kotlin でのインタフェース定義は、Java と同様に **`interface`** キーワードを使用します。
下記の例では、`Command` インタフェースを定義し、それを `GreetCommand` クラスと `ExplodeCommand` クラスが実装しています。

```kotlin
interface Command {
    fun execute()
}

class GreetCommand(val target: String) : Command {
    override fun execute() {
        println("Hello, $target")
    }
}

class ExplodeCommand : Command {
    override fun execute() {
        println("Boom!")
    }
}

fun executeAllCommands(commands: List<Command>) {
    for (c in commands) {
        c.execute()
    }
}

fun main() {
    val commands = listOf(GreetCommand("Maku"), ExplodeCommand())
    executeAllCommands(commands)
}
```

Java の `Serializable` インタフェースなどは、メソッドを何も持っていないマーカーインタフェースです。
Kotlin では、マーカーインタフェースは下記のようにシンプルに定義できます。

```kotlin
interface MyMarkerInterface
```

インタフェースは他のインタフェースを継承することができます。

```kotlin
interface Command {
    fun execute()
}

interface UndoableCommand : Command {
    fun undo()
}
```

インタフェースおよびクラスは、複数のインタフェースを継承することができます。
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

あるオブジェクトが特定のインタフェースを継承しているかを調べるには、**`is`** を使って、`if (obj is InterfaceName)` のように記述します。
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

