---
title: "Kotlin で読みやすいコードを書く方法（可読性の高い Kotlin コードとは）"
date: "2019-10-31"
---

Effective Kotlin という電子書籍に、Kotlin コードの可読性に関していろいろためになることが書いてあるのでメモメモ。


読みやすさのためのデザイン (Design for readability)
----

### 昔ながらの実装 vs Kotlin 的な実装

下記の実装 A と B はどちらが読みやすいでしょうか？

```kotlin
// 実装 A
if (person != null && person.isAdult) {
    view.showPerson(person)
} else {
    view.showError()
}

// 実装 B
person?.takeIf { it.isAdult }
    ?.let(view::showPerson)
    ?: view.showError()
```

どちらが読みやすいかは読む人のスキルによりますが、A の方が変更しやすく、デバッグしやすいです。
条件分岐を増やそうとすると、B の方は全体を考え直さないといけないので、頭が疲れます。
A と B は一見同じ振る舞いをするように見えますが、実際には異なる動作をします。
`let` はラムダ式の評価結果を返すので、`view::showPerson` が `null` を返すと、`showError()` が実行されてしまいます！
`let` などはしっかり仕様を理解して使わないと危険だということです。

一般ルールとしては、**認知負荷を下げる** ようにコードを記述することを考えるとよいです。
つまり、**どう動作するかがそのままコードに表現されている** ように書くということです。

「短いコード＝読みやすいコード」ではないということですね。
一般的に、コンパクトなコードを書ける人は、技術的にはどんなコードでも書ける人だと思います。
あとはその人がどれだけ思いやりを持って書けるかにかかっています。


### やりすぎはダメよ (Do not get extreme)

これはよくある safe call let イディオムなんですが、、、

```kotlin
person?.let {
    print(it.name)
}
```

これはどうですか？

```kotlin
students
    .filter { it.pointsInSemester > 15 && it.result >= 50 }
    .sortedWith(compareBy({ it.surname }, { it.name }))
    .joinToString(separator = "\n") {
        "${it.name} ${it.surname}, ${it.result}"
    }
    .let(::print)

var obj = FileInputStream("/file.gz")
    .let(::BufferedInputStream)
    .let(::ZipInputStream)
    .let(::ObjectInputStream)
    .readObject() as SomeObject
```

後者のようなコードの問題点は、デバッグが難しくなることと、Kotlin に慣れていない人にとって理解しにくいことです。
こういった Kotlin 的な**イディオムの組み合わせは、単独で順番に呼び出した時よりも何倍も複雑になってしまう**ということを意識しておかなければいけません。


演算子の振る舞いと関数名との一貫性 (Operator meaning should be consistent with its function name)
----

演算子のオーバーロードはとってもパワフル！でもとってもデンジャラス！

下記はある数の階乗を求めるプログラムです。

```kotlin
fun Iterable<Int>.product(): Int = fold(1) { acc, i -> acc * i }
fun Int.factorial(): Int = (1..this).product()

fun main() {
    print(6.factorial())  // 720
}
```

階乗は数学の世界では `10・6!` と書けるので、こんな風に定義したくなるかもしれません。

```kotlin
operator fun Int.not() = factorial()

print(!6)  // 720
```

`!6` という表現が、`6!` に似ててエレガント！と思うかもしれません。
でもこれは `not()` という関数を定義しているのであり、下記のシンタックスシュガーでしかありません。

```kotlin
print(6.not())  // 720
```

これは意味的におかしいですよね？
だから、演算子をオーバーロードするときは、その**演算子の本質的な意味を変えるような実装をしてはいけません**。


### 関数を整数倍 (times) するとは？

下記はどんな処理を表現しているのでしょうか？

```kotlin
3 * { print("Hello") }
```

ある人は、関数をその場で 3 回呼び出すことを意味していると思うかもしれません。

```kotlin
operator fun Int.times(operation: ()->Unit) {
    repeat(this) { operation() }
}

3 * { print("Hello") }  // HelloHelloHello
```

またある人は、関数を 3 回呼び出す関数を定義していると思うかもしれません。

```kotlin
operator fun Int.times(operation: () -> Unit): ()->Unit = {
    repeat(this) { operation() }
}

val tripledHello = 3 * { print("Hello") }
tripledHello()  // HelloHelloHello
```

このように、演算子のオーバーロードによって曖昧さを発生させる恐れがある場合は、わかりやすい名前の **拡張関数** を定義すべきです。
どうしても演算子のように呼び出せるようにしたいのであれば、`infix` を使いましょう。

```kotlin
infix fun Int.timesRepeated(operation: ()->Unit) = {
    repeat(this) { operation() }
}

val tripledHello = 3 timesRepeated { print("Hello") }
tripledHello()  // HelloHelloHello
```

でも、このように処理を繰り返したいときは、素直にトップレベル関数をそのまま使っちゃえば OK です。

```kotlin
repeat(3) { print("Hello") }
```

例外として、DSL (Domain Specific Language) を定義する場合は、意図的に特殊なオーバーライドをすることがあります。

```kotlin
body {
    div {
        +"Some text"  // String.unaryPlus の呼び出し
    }
}
```

Unit? 型の戻り値は便利？ (Avoid returning or operating on Unit?)
----

```kotlin
fun keyIsCorrect(key: String): Boolean = /* ... */
if (!keyIsCorrect(key)) return
```

こーゆーのは、戻り値を `Unit?` にしておけば、Elvis operator で簡潔に記述できるのでは？という主張があります。

こんな感じに `if` を省略できる。。。

```kotlin
fun verifyKey(key: String): Unit? = /* ... */
verifyKey(key) ?: return
```

確かにこれは書いている人にとっては快適かもしれないけど、読む人にとっては混乱のもとです。
素直に Boolean を使いましょう！

特に問題になるのは、ちょっと前にも出てきた次のような呼び出し方をした場合です。

```kotlin
getData()?.let { view.showData(it) } ?: view.showError()
```

万が一、ラムダ式で呼び出している `showData()` が `null` を返すような実装をしていたら、`showError()` まで呼ばれてしまいます。これは恐ろしい。。。
`Unit?` の方が有用なケースってあまりないはずなので、結論としては、`Unit?` なんて使うなということです。


型が明確でないときは省略しない (Specify the variable type when it is not clear)
----

Kotlin の型推論機能 (type inference) はよいよねー。

```kotlin
val num = 10
val name = "Marcin"
val ids = listOf(12, 112, 554, 997)
```

こーゆーのはシンプルでとてもよい。

でも下記のように、関数の戻り値を受ける場合はどうでしょう？

```kotlin
val data = getSomeData()
```

確かに Kotlin の型推論によって、変数 `data` の型指定は省略できます。
でも、このコードを見ただけでは、`data` はどんな型なのかがわかりません。
読み手はいちいち関数の定義を見なければ理解することができません。

IDE の機能を使ったり、関数を辿っていけば分かるじゃん、とは言いますが、GitHub 上でレビューすることはよくあるでしょう？
言語的に型を省略できる場合でも、この例のような場合はちゃんと型を明示した方が読みやすくなります（最近の Android Studio は、このようなケースで型名を省略すると suggestion を出してくれるようになりました）。

```kotlin
val data: UserData = getSomeData()
```

特にプラットフォーム型（言語的には `xxx!` と示される内部表現） を返すメソッドを呼び出す場合は、必ずその場で受け取る型を宣言しましょう。
NotNull な値として扱うのか、Nullable な値として扱うのかを早めに指定することで、`null` まわりの不具合を見つけやすくなります。

```kotlin
val text = javaObj.getUserName()  // これはダメ！深いところでヌルポになるバグのもと
val text: String = javaObj.getUserName()   // OK: NotNull な値として扱う
val text: String? = javaObj.getUserName()  // OK: Nullable な値として扱う
```

仮に本当は Nullable にすべき戻り値を NotNull な型で受けるように実装してしまっても、早い段階で NullPointerException が発生するので、潜在的なバグの混入は防ぐことができます。


レシーバーオブジェクトの明示 (Consider referencing receivers explicitly)
----

### 拡張関数における this の明示

下記はクイックソートを拡張関数として実装する例です。

```kotlin
fun <T : Comparable<T>> List<T>.quickSort(): List<T> {
    if (size < 2) return this
    val pivot = first()
    val (smaller, bigger) = drop(1).partition { it < pivot }
    return smaller.quickSort() + pivot + bigger.quickSort()
}
```

どれがレシーバーのプロパティかわかりにくいかもしれません。
このような場合は、レシーバー (`this`) を明示的に記述した方が理解しやすくなることがあります。

```kotlin
fun <T : Comparable<T>> List<T>.quickSort(): List<T> {
    if (this.size < 2) return this
    val pivot = this.first()
    val (smaller, bigger) = this.drop(1).partition { it < pivot }
    return smaller.quickSort() + pivot + bigger.quickSort()
}
```

### たくさんレシーバーがいるとき

`apply` や `with` や `run` などを使うとき、レシーバーが複数混在することがあります。
そのようなケースでは、ちゃんとレシーバーを明示しないと危険です。

```kotlin
class Node(val name: String) {
    fun makeChild(childName: String) =
        create("$name.$childName")
            .apply { print("Created ${name}") }

    fun create(name: String): Node? = Node(name)
}

fun main() {
    val node = Node("parent")
    node.makeChild("child")
}
```

上記を実行すると何が表示されるでしょう？
（答え → <span style="background: white; color: white">Created parent</span>）

次に、レシーバーとして `this` を明示してみるとどうなるでしょう？

```kotlin
lass Node(val name: String) {
    fun makeChild(childName: String) =
        create("$name.$childName")
            .apply { print("Created ${this?.name}") }

    fun create(name: String): Node? = Node(name)
}

fun main() {
    val node = Node("parent")
    node.makeChild("child")
}
```

（答え → <span style="background: white; color: white">Created parent.child</span>）

これは `apply` の間違った使い方の一つです。
こーゆー場合は、レシーバーをパラメータとして受け取る `also` や `let` を使えば、パラメータに名前を付けることができるし、`this` の意味がラムダ式 `{}` の内側と外側で変わってしまうのを防ぐことができます。

```kotlin
class Node(val name: String) {
    fun makeChild(childName: String) =
        create("$name.$childName")
            .also { print("Created ${it?.name} in $name") }

    fun create(name: String): Node? = Node(name)
}

fun main() {
    val node = Node("parent")
    node.makeChild("child")  // Created parent.child in parent
}
```

ちなみに、`also` と `let` の違いは、`also` が自分自身を戻り値とするのに対し、`let` は戻り値が任意であるという点です。

よい例ではないですが、仮に `apply` を使ったときに外側の `name` パラメータを参照したい場合は、次のように `this` をラベリングして参照する必要があります。

```kotlin
fun makeChild(childName: String) =
    create("$name.$childName")
        .apply { print("Created ${this?.name} in ${this@Node.name}") }
```


プロパティは振る舞いではなく状態を表す (Properties should represent state, not behavior)
----

Kotlin のプロパティと Java のフィールドが大きく異なるのは、Kotlin ではカスタムゲッターとしてプロパティの実装ができるところです（Kotlin のプロパティはフィールドではなくアクセッサーです）。
プロパティは実際のところ関数なので、どんな実装でもできてしまいますが、アルゴリズム的な振る舞いを実装すべきではありません。

```kotlin
// DON’T DO THIS!
val Tree<Int>.sum: Int
    get() = when (this) {
        is Leaf -> value
        is Node -> left.sum + right.sum
    }
```

上記のようなプロパティ実装は、要素を再帰的に走査するため、コレクションのサイズによって計算量が莫大になり得ます。
getter メソッドは、暗黙的に高速に値を返すと想定されるので、このような大きなアルゴリズムはプロパティとして実装してはいけません。
代わりに、次のように関数として実装すべきです。

```kotlin
fun Tree<Int>.sum(): Int = when (this) {
    is Leaf -> value
    is Node -> left.sum() + right.sum()
}
```

標準ライブラリも `sum` は関数として提供しますね。

```kotlin
val s = (1..100).sum()
```


### プロパティと関数のどちらで定義すべきか？

* メソッドにしたときに、`setXxx()/getXxx()` と命名できるもののみプロパティとして定義する資格がある
* プロパティは O(1) のオーダーで完了するべき
* ビジネスロジックを含むものはプロパティにしてはいけない（例外: ロギング、リスナへの通知、結合された要素値の変更）
* 呼び出しの順序によって振る舞いが変わるもの、複数回呼び出すと振る舞いが変わるものはプロパティにしてはいけない
* 自身の値を全体的に変換するものは、慣例としてメソッドとして提供する（例: `Int.toDouble()`）

一言でいうなら、プロパティは「状態」であり、関数は「振舞い」であるということ。


名前付き引数を活用する (Consider naming arguments)
----

名前付き引数を使って、パラメータの意味を分かりやすくしましょう。
下記は名前付き引数 (named arguments) のよい例です。

```kotlin
val text = (1..10).joinToString(separator = "|")
```

下記は似ているけど、変数名で意味を示そうとしています（named values というらしい）。

```kotlin
val separator = "|"
val text = (1..10).joinToString(separator)
```

後者は、開発者のミスが入りうるので前者の名前付き引数を使った方がベターです。
後者の実装では、関数の利用者が変数名を付け間違えることがあるし、関数側のパラメータの順序が変更された場合に気付くことができません。

引数名と同じような名前の変数を下記のように渡すのは、一見冗長に見えますが、上記のような理由から有用です。

```kotlin
val separator = "|"
val text = (1..10).joinToString(separator = separator)
```

### 時間の単位（ミリ秒 or 秒）を明確にする

時間の単位があいまいな値を記述するのは避けましょう。

```kotlin
sleep(100)  // 100秒？
```

これでは、100 という値が 100 秒なのか 100 ミリ秒なのかが伝わりません。
次のような書き方をすれば、「ミリ秒」単位の指定であることが一目瞭然になります。

```kotlin
sleep(timeMillis = 100)  // 名前付き引数で示す
sleep(Millis(100))       // 関数名で示す
sleep(100.ms)            // 拡張関数で示す
```

### 同じ型の変数が複数ある場合は名前付き引数で指定する

同じ型のパラメータを複数取る関数は、呼び出し時にパラメータの順序を間違えるミスが発生します。
次の関数は、String 型のパラメータを 2 つ受け取ります。

```kotlin
fun sendEmail(to: String, message: String) { /* ... */ }
```

このような関数を呼び出す場合は、次のように **名前付き引数** の形で呼び出せば、パラメータの指定順序のミスはなくすことができます。

```kotlin
sendEmail(
    to = "contact@example.com",
    message = "Hello, ..."
)
```

### 絶対に名前付き引数で呼び出した方がいいケース

次のように、関数型パラメータを複数もつ関数を呼び出すケースでは、必ず名前付き引数で呼び出すようにした方がよいです。
同じような呼び出し方をしているのに、振る舞いが変わってしまうことがあるからです。

```kotlin
fun call(before: () -> Unit = {}, after: () -> Unit = {}) {
    before()
    print(" MIDDLE ")
    after()
}

call({ print("CALL") })  //=> CALL MIDDLE
call { print("CALL") }   //=> MIDDLE CALL
```

名前付き引数で、次のように呼び出せば間違えることがありません。

```kotlin
call(before = { print("CALL") })  //=> CALL MIDDLE
call(after = { print("CALL") })   //=> MIDDLE CALL
```

3rd パーティライブラリを使う場合も、このような名前付き引数は活用できるケースはよくあります。
例えば、Java 言語で RxJava の `Observable` を使う場合、次のように処理内容をチェーンさせて記述します。

```kotlin
observable.getUsers()
    .subscribe((List<User> users) -> {  // onNext
        // ...
    }, (Throwable throwable) -> {  // onError
        // ...
    }, () -> {  // onCompleted
        // ...
    });
```

いちいちコメントで何の処理かを記述しているところがかっこ悪いです。
Kotlin の名前付き引数で記述すれば、次のようにスッキリ読みやすく書けるし、パラメータの順序を間違えることもありません。

```kotlin
observable.getUsers()
    .subscribeBy(
        onNext = { users: List<User> ->
            // ...
        },
        onError = { throwable: Throwable ->
            // ...
        },
        onCompleted = {
            // ...
        })
```

ちなみに名前付き引数バージョンで呼び出せるようにするため、RxJava オリジナルの `subscribe()` ではなく、Kotlin ラッパー（拡張関数）として定義されている `subscribeBy()` を呼び出さなければいけないことに注意してください。


コーディング規約に従おう (Respect coding conventions)
----

### 公式のコーディング規約

Kotlin 標準のコーディング規約は公式サイトでちゃんと定義されているので、できるだけこれに従いましょう。
これは読んでおかなければいけないドキュメントです。

- [Coding Conventions - Kotlin Programming Language](https://kotlinlang.org/docs/reference/coding-conventions.html)

もちろん、社内プロジェクトのすべてがこのコーディング規約に従う必要はないけれど、オープンなコミュニティで使用するプロジェクトでは従うべきです。
誰にとっても読みやすくなるし、コードをプロジェクト間で移動させるのも容易になります。

今 Kotlin を使っている人は、Java から移行してきた人が多いので、間違って Java のコーディング規約で書いている人が多いです。
特に、クラス定義や関数定義のパラメータ部分に改行を入れる場合に、次のような間違った記述方法をよく見かけます。

#### 間違った例（Java の世界では OK だが、Kotlin の世界では NG）

```kotlin
class Person(val id: Int = 0,
             val name: String = "",
             val surname: String = "") : Human(id, name) {
    // body
}
```

Kotlin では次のように改行するのが標準のルールで、最初の開き括弧 `(` の後ろですぐに改行し、1 パラメータずつ改行していきます。

#### 関数 body の直前の行（開き中括弧 `{` の行）はインデントしない

```kotlin
class Person(  // 一行目にはパラメータを記述しない
    val id: Int = 0,
    val name: String = "",
    val surname: String = ""
) : Human(id, name) {  // ここで一度インデントをリセットする
    // body
}
```

とはいえ、Effective Kotlin の著者は、関連度の強いパラメータ（`x`、`y` など）は、同じ行に書いてしまいたいとも言っています。

### コーディング規約に従うためのツール

Kotlin 標準のコーディング規約に従う場合は、下記のようなツールを活用しましょう。

* IntelliJ/Android Studio の**フォーマット設定**
    1. `Setting` → `Editor` → `Code Style` → `Kotlin`
    2. `Set from...` のリンクをクリック
    3. `Predefined Style` → `Kotlin style guide` を選択
* **ktlint** - Kotlin 用の Lint ツール
    * [https://github.com/pinterest/ktlint](https://github.com/pinterest/ktlint)

コーディング規約はとても重要なものなのに、軽視している開発者もいます。
プロジェクトをよりよいものにするために、コーディング規約を守りましょう！

