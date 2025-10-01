---
title: "Kotlinメモ: Kotlin のパッケージの扱い方を理解する"
url: "p/u8mz82t/"
date: "2019-04-26"
tags: ["kotlin"]
aliases: ["/kotlin/package/basic.html"]
---

Kotlin のパッケージの仕組みは、Java とは下記のような点で異なっています。

* クラスが所属するパッケージはファイル先頭の `package` 宣言によってのみ決まる
* `.kt` ファイル内に複数の public クラスを定義できる
* `.kt` ファイルのファイル名はクラス名に合わせる必要はない
* パッケージ階層とディレクトリ階層を合わせて `.kt` ファイルを配置する必要はない
* パッケージのトップレベルに関数やプロパティを定義することができる

つまり、**`package` 宣言でパッケージ定義すること以外はほとんど自由**です。

下記のように実装した `main.kt` ファイルは、

```kotlin
package com.example

fun main() {
    println("Hello")
}
```

Java の慣例に従って、`com/example/main.kt` というディレクトリ階層に配置しても、プロジェクトのルートに `main.kt` として配置しても構いません。
Kotlin のコンパイラが意識するのは、コード内に記述した `package` 宣言だけです。
とはいえ、できるだけパッケージ階層とディレクトリ階層は合わせておいた方が管理しやすいと思います。

下記のように、同じ `.kt` ファイル内のトップレベルに、**複数の public なクラスを定義**することもできます。

{{< code lang="kotlin" title="hogegege.kt" >}}
package com.example

class Foo
class Bar
{{< /code >}}

これらのクラスを参照するときは、Java と同様、次のようにインポートして使用します。

{{< code lang="kotlin" title="main.kt" >}}
import com.example.Foo
import com.example.Bar

fun main() {
    val foo = Foo()
    val bar = Bar()
}
{{< /code >}}

このようにインポートできる `Foo` クラス、`Bar` クラスが存在している場合、Java では必ず `Foo.java` ファイルと `Bar.java` ファイルが存在しますが、Kotlin の場合は必ずしも `Foo.kt`、`Bar.kt` という名前のファイルが存在しているとは限りません。

上記の例では、`hogegege.kt` というふざけた名前のファイルで `Foo` クラスと `Bar` クラスを定義していますが、利用する側にとってはこのファイル名を意識する必要はないので、**つまり `.kt` のファイル名は何でもよい**のです（正確には、Java コードから参照する場合はファイル名を意識する必要があります）。

また、逆に、**別々の `.kt` ファイルで定義したクラスや関数を、同一のパッケージに所属させる**こともできます。

{{< code lang="kotlin" title="foo.kt" >}}
package com.example
// ...
{{< /code >}}

{{< code lang="kotlin" title="bar.kt" >}}
package com.example
// ...
{{< /code >}}

**パッケージのトップレベルに関数やプロパティを定義することができる**のも Kotlin の大きな特徴です。
Java で関数を定義したいときは必ず何らかのクラスのメソッドとして定義しなければいけませんが、Kotlin ではクラスの外側に関数やプロパティを定義することができます。
インポートも関数単位で行うことができます（例: `import com.example.doSomething`）。

トップレベルでの関数定義の詳細は、下記の記事を参照してください。

* [パッケージのトップレベルに関数、プロパティ、定数を定義する](/p/xgn46vo/)

