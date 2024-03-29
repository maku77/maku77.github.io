---
title: "パッケージのトップレベルに関数、プロパティ、定数を定義する"
date: "2019-04-26"
---

トップレベル関数を定義する
----

Java ではパッケージのトップレベルに関数を定義することはできませんでした（あるクラスの static メソッドとして定義する必要がありました）が、Kotlin ではクラスの外に関数（プロパティ、定数）を定義することができます。

下記の例では、`util.kt` のトップレベルで `printError` 関数を定義し、それを `main.kt` の中から利用しています。

#### util.kt

```kotlin
package com.example

fun printError(message: String) {
    System.err.println("Error: $message")
}
```

#### main.kt

```kotlin
import com.example.printError

fun main() {
    printError("ファイルが見つかりません")
}
```

`main.kt` からの参照方法を見ると分かるように、Kotlin では `.kt` ファイルの名前は参照時に意識する必要はなく、あくまで**package により宣言されたパッケージ名だけが参照情報として必要**になります。

トップレベルに定義された関数をまとめてインポートしたいときは、下記のようなワイルドカードを使用できます。

```kotlin
import com.example.*
```


トップレベル関数を private にする
----

パッケージのトップレベルに定義した関数（やプロパティ）は、デフォルトでは public になり、他の `.kt` ファイルから参照できるようになっています。
可視性を自分のファイル内に限定したい場合は、**`private`** を付けて関数を定義します。

```kotlin
package com.example.util

// これは他のファイルからも参照できる
fun greet(name: String) {
    println(getMessage(name))
}

// これはこのファイルからしか参照できない
private fun getMessage(name: String) : String {
    return "Hello, $name!"
}
```


Java からどう見えるか？
----

### kt ファイル名から Java のクラス名が生成される

#### util.kt

```kotlin
package com.example

fun printError(message: String) {
    System.err.println("Error: $message")
}
```

上記のように `util.kt` ファイルの中で定義した `com.example` パッケージの `printError` 関数は、Java (JVM) の世界からは `com.example.UtilKt.printError` のように見えます。
Java の世界ではすべてのメソッドは何らかのクラスに所属していなければいけないので、**`util.kt` というファイル名から、自動的に `UtilKt` という名前のクラスが生成される**ようになっています。

#### Main.java

```java
import com.example.UtilKt;

public class Main {
    public static void main(String... args) {
        UtilKt.printError("こんにちは");
    }
}
```

### 生成される Java のクラス名を変更する

デフォルトでは `.kt` ファイルの名前をベースに Java クラス名が生成されますが、**`@JvmName`** アノテーションを使用すると、生成される Java クラス名を変更することができます。

#### util.kt

```kotlin
@file:JvmName("LogUtil")
package com.example

fun printError(message: String) {
    System.err.println("Error: $message")
}
```

上記の `util.kt` ファイルのトップレベルに定義した関数は、`UtilKt` ではなく、`LogUtil` という名前の Java クラス内に定義されます。
よって、Java のコードからは下記のように参照することになります。

#### Main.java

```java
import com.example.LogUtil;

public class JavaMain {
    public static void main(String... args) {
        LogUtil.printError("こんにちは");
    }
}
```


トップレベルプロパティを定義する
----

関数と同様に、プロパティもパッケージのトップレベルに定義することができます。

下記の例では、`util.kt` ファイルのトップレベルに `logPrefix` というプロパティを定義しています。

#### util.kt

```kotlin
package com.example

var logPrefix = "Error: "

fun printError(message: String) {
    System.err.println("${logPrefix}${message}")
}
```

このプロパティは、デフォルトで public なので、別の `.kt` ファイルから参照することができます。
下記の例では、`main.kt` ファイルの中から、`util.kt` のトップレベルプロパティの値を変更しています（`var` による変数定義なので値を変更できます）。

#### main.kt

```kotlin
import com.example.logPrefix
import com.example.printError

fun main() {
    logPrefix = "Hoge: "
    printError("ファイルが見つかりません")
}
```

#### 実行結果

```
Hoge: ファイルが見つかりません
```


トップレベル定数を定義する
----

パッケージのトップレベルにプロパティを定義する際に、`var` ではなく **`const val`** を使用することで、トップレベルの**定数**として扱うことができます。

#### math.kt

```kotlin
package com.example.math

const val PI = 3.14
```

#### main.kt

```kotlin
import com.example.math.PI

fun main() {
    println("円周率は $PI だよ")
}
```

### （コラム） val でなく const val で定数定義する理由

定数を定義するときに、`val` でなく、`const val` と定義することで、Java の `public static final` 相当のバイトコードが生成されます。
`const` を省略して `val` と記述するだけでも同様に定数として扱うことはできるのですが、その場合、Kotlin は内部で getter メソッドを生成することになります。

Kotlin のコードからアクセスするときはどちらの場合も違いはないのですが、Java のコードからアクセスするときに明らかな違いが発生します。

```kotlin
const val PI = 3.14  // Java から見ると PI という定数
val PI = 3.14        // Java から見ると getPI() というメソッド
```

