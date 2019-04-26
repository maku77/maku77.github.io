---
title: "Kotlin のコードにドキュメンテーションコメントを記述する (KDoc)"
linkTitle: "ドキュメンテーションコメントを記述する (KDoc)"
date: "2019-03-05"
---

ドキュメンテーションコメントとは
----

Kotlin のソースコードにドキュメンテーションコメントを記述しておくと、関数のコメントなどから API ドキュメントを自動生成することができるようになります。
また、関数のドキュメンテーションコメントを記述しておけば、AndroidStudio などの開発環境上でコーディングしているときに、関数の説明をポップアップ表示してくれるようになります。

Java のドキュメンテーションコメントのフォーマットは Javadoc と呼ばれていますが、Kotlin のドキュメンテーションコメントは **KDoc** と呼びます。

- [KDoc - Documenting Kotlin Code](https://kotlinlang.org/docs/reference/kotlin-doc.html)

多人数で使用するライブラリを作成する場合は API ドキュメントを用意するのは必須です。
一人で開発する場合でも、API の仕様を明確にして実装する癖をつけることで、自然にきれいな設計を行う力が身に付きます。
ドキュメンテーションコメントに関する基本的な考え方は下記を参照してください。

* [ドキュメンテーションコメントの書き方](/memo/how-to-write-comment.html)


Javadoc コメントと KDoc コメントの違い
----

KDoc ドキュメンテーションコメントは、Javadoc と同様に **`/**`** で始め **`*/`** で終わります。

**`@タグ名`** の形で付加的な情報を記述できることも同様ですが、KDoc のみで使用できるタグや、逆に KDoc では使えないタグもあります（後述）。

Javadoc コメント内では HTML タグを使った修飾が可能でしたが、KDoc コメントでは代わりに [Markdown](https://daringfireball.net/projects/markdown/basics) を使用します。

下記は、Kotlin のドキュメントサイトで紹介されている KDoc コメントの記述例です。

```kotlin
/**
 * A group of *members*.
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @param T the type of a member in this group.
 * @property name the name of this group.
 * @constructor Creates an empty group.
 */
class Group<T>(val name: String) {
    /**
     * Adds a [member] to this group.
     * @return the new size of the group.
     */
    fun add(member: T): Int { ... }
}
```

KDoc ドキュメンテーションコメントの書き方
----

### パラメータに関する記述

```kotlin
/**
 * Sums two values, [a] and [b].
 */
fun myAdd(a: Int, b: Int) = a + b
```

KDoc のドキュメンテーションコメントから関数のパラメータを参照するときは、上記のようにパラメータ名をブラケット `[]` で囲みます。

Javadoc ではパラメータの説明は `@param` タグ、戻り値の説明は `@return` タグを使って記述することが推奨されていましたが、Kotlin の開発チームは、KDoc では説明文の形でシンプルに記述してしまうことを推奨しています。

```kotlin
/**
 * Sums two values, [a] and [b].
 *
 * @param[a] the first value to be added
 * @param[b] the second value to be added
 * @return the sum of two values
 */
```

上記のようなコメントは冗長なので、下記のようにシンプルに記述すればよいということですね。

```
/**
 * Sums two values, [a] and [b].
 */
```

もちろん、パラメータや戻り値の詳細な説明が必要なケースでは、`@param` や `@return` タグを使用してコメントを記述した方がよいでしょう。


### KDoc で使用できるブロックタグ

KDoc コメント内では、下記のようなブロックタグが使用できます。
Javadoc には存在しなかった KDoc 独自のタグはハイライトしています。


`@param name 説明`<br>`@param[name] 説明`
: 関数のパラメータ、あるいはクラス、プロパティ、関数のタイプパラメータの説明に使用します。

`@return 説明`
: 戻り値の説明に使用します。

`@throws class 説明`<br>`@exception class 説明`
: あるメソッドが投げる可能性のある例外に関しての説明を記述します。

**`@constructor 説明`**
: クラスコメントにプライマリコンストラクタの説明を記述するときに使用します。プライマリコンストラクタのパラメータの説明は、`@constructor` タグよりも下に `@param` タグを使って記述します。

**`@property name 説明`**
: クラスコメントで、各プロパティの説明を記述する際に使用します。プライマリコンストラクタで定義したプロパティの説明にも使用できます。各プロパティの説明は、このブロックタグを使用してクラスコメント内に記述できるので、各プロパティの定義位置に記述する必要はありません。

**`@receiver 説明`**
: 拡張関数（プロパティ）のレシーバーの説明に使用します。

**`@sample シンボル`**
: 指定した関数の実装をサンプルコードとして埋め込み表示します。完全修飾名 (fully qualified name) で関数を指定します。サンプルコードの置き場所は `build.gradle` の `dokka.samples` で指定します。

**`@suppress`**
: API ドキュメントとしては出力しないように指示します。一般的な API としては非公開にしたいのだけど、実装上どうしても呼び出さなければいけないメソッドなどで使用します。

`@see シンボル`
: 参考にすべきクラスやメソッドがある場合に、See also リンクを張るために使用します。

`@version バージョン`
: この API が導入されたバージョンを示します。


### KDoc で使用できないブロックタグ

逆に、Javadoc では使えたけど KDoc では使えなくなっているタグもあります。

`@deprecated` タグ
: Kotlin では非推奨の API は `@Deprecated` アノテーションだけで示すのでコメント用のタグは存在しません。ただし、ドキュメンテーションコメントとして代替メソッドの説明を記述することは忘れないようにしましょう。

`@inheritdoc` タグ
: Kotlin のドキュメンテーションコメントは、サブクラスに自動的に継承されるので、ドキュメントの継承を明示するタグは存在しません。

装飾用のインラインタグ (`@link`、`@code` など）
: Kotlin では文章の装飾は Markdown フォーマットで行います（下記参照）。


### 文章の中で使用できる Markdown フォーマット

Javadoc コメント内の文章に装飾を加える場合は、HTML タグや `@literal` などのインラインタグを使用していましたが、KDoc コメントでは代わりに [Markdown](https://daringfireball.net/projects/markdown/basics) フォーマットを使用します。

#### リンク

ドキュメンテーションコメント内でクラス名や関数名にリンクを張りたいときは、下記のようにシンボル名をブラケットで囲みます（関数のパラメータと同様）。

- `[MyClass]`
- `[myFunc]`
- `[com.example.myapp.MyClass]`
- `[com.example.myapp.MyClass.myFunc]`

表示に使用するテキストを変更したい場合は、下記のように、表示したいテキストをブラケットで囲んで前に追加します。

- `[test class][com.example.myapp.test.Bar]`

一般的な Web サイトへのリンクは、アドレスを囲む括弧を `()` にします。

- `[Google](https://google.com/)`


#### コードブロック

    ```kotlin
    fun hello() {
        Log.i(TAG, "hello")
    }
    ```

#### リスト

```
- apple
- banana
- orange

1. apple
2. banana
3. orange
```

#### その他

```
## 見出しを入れることができます

アスタリスクで囲むと *強調* されて表示されます。

バッククォートで囲んだ `インラインコード` は等幅フォントで表示されます。
```


Dokka による API ドキュメントの生成
----

Kotlin のソースコードから HTML 形式などの API ドキュメントを出力するには、**Dokka** というツールを使用します。

- [Dokka](https://github.com/Kotlin/dokka)

Dokka は Java コードに記述された Javadoc コメントを扱う機能を備えているため、Java コードと Kotlin コードの混在するプロジェクトでも、Dokka だけ使用すればよいようになっています。

Gradle の **`org.jetbrains.dokka`** プラグインが [Maven リポジトリ (jcenter)](https://bintray.com/kotlin/dokka/dokka)から配信されているので、下記のように `build.gradle` ファイルに依存関係を記述するだけで Gradle の **`dakka`** タスクを実行できるようになります。

### 通常の Java アプリの場合

#### build.gradle

```groovy
buildscript {
    ext.dokka_version = '0.9.+'
    repositories {
        jcenter()
    }
    dependencies {
        classpath "org.jetbrains.dokka:dokka-gradle-plugin:$dokka_version"
    }
}

apply plugin: 'org.jetbrains.dokka'
```

### Android アプリの場合

Android プロジェクトでは、`org.jetbrains.dokka` プラグインの代わりに **`org.jetbrains.dokka-android`** プラグインを使用します。
Android 用のプラグインを使用することで、自動生成される `R` クラスなどのドキュメントが無駄に出力されないようになります。
`dependencies` で指定する ID が微妙に異なるので注意してください。

#### ルートの build.gradle

```groovy
buildscript {
    ext.dokka_version = '0.9.+'
    repositories {
        jcenter()
    }
    dependencies {
        classpath "org.jetbrains.dokka:dokka-android-gradle-plugin:$dokka_version"
    }
}
```

Android のプロジェクトでは、`app` ディレクトリなどのサブプロジェクト構成になっていると思います。
各サブプロジェクトの `build.gradle` で `com.android.application` や `com.android.library` プラグインを適用した後で、`org.jetbrains.dokka-android` プラグインを適用します。

#### app/build.gradle

```groovy
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'org.jetbrains.dokka-android'
```

これで、Gradle の **`dokka`** タスクを実行して API ドキュメントを生成できるようになります。

#### 実行例

```
$ ./gradlew dokka
```

デフォルトでは、各プロジェクトの **`build/dokka`** ディレクトリ内に API ドキュメントが出力されます。


Dokka の出力設定
----

Gradle プラグインとして提供されている Dokka を使用する場合、`dokka` ブロックで `dokka` タスクの設定を行うことができます。

### 出力形式を変更する

```groovy
dokka {
    outputFormat = 'javadoc'
}
```

下記のような出力形式を指定できます。

- `html` - デフォルト形式
- `javadoc` - Javadoc 形式
- `markdown` - Markdown structured as html（一般的な Markdown 形式）
- `gfm` - GitHub flavored markdown（生成されたファイルを GitHub にそのまま push すれば Web 上で読めるようになる）
- `jekyll` - Jekyll compatible markdown（GitHub pages も Jekyll で動作しているのでこれが使える）

デフォルトの出力形式は `html` です。
`javadoc` を指定することで、見慣れた Javadoc 形式の API ドキュメントを生成することができます。
Javadoc 形式の方が、サイドバーがあって使いやすいかもしれません。

### 出力先ディレクトリを変更する

```groovy
dokka {
    outputDirectory = "$buildDir/javadoc"
}
```

デフォルトの出力先は `$buildDir/dokka` です。

### モジュール／パッケージのドキュメンテーションファイルを指定する

```groovy
dokka {
    includes = ['packages.md']
}
```

KDoc では、複数パッケージのドキュメントを、独立した Markdown ファイルにまとめて記述しておくことができます。
Javadoc で使われていた `package-info.java` のようなものを、1 ファイルにまとめて記述できるということですね。
Kotlin は Java と違って「ディレクトリ構造＝パッケージ構造」ではないので、パッケージに関するドキュメントファイルもディレクトリ構造とは独立した形で作成できるようになっています。

パッケージドキュメントの記述方法に関しては、下記のドキュメントを参照してください。

- [Documenting Kotlin Code - Module and Package Documentation](https://kotlinlang.org/docs/reference/kotlin-doc.html#module-and-package-documentation)

