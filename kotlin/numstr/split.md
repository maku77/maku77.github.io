---
title: "文字列をデリミタ文字で分割する (split)"
date: "2019-05-08"
---

カンマで文字列を分割する
----

Kotlin で文字列を分割するには、Java と同様に `String` クラスの **`split`** メソッドを使用します。
下記は、CSV 形式の文字列（カンマ区切り文字列）をカンマで分割する例です。

```kotlin
val s = "AAA, BBB, CCC"
val arr = s.split(",")
println(arr) //=> ["AAA", " BBB", " CCC"]
```

カンマの前後の空白文字を削除したい場合は、分割後に `trim()` を全要素に対してかけてあげればよいでしょう。

```kotlin
val s = "AAA, BBB, CCC"
val arr = s.split(",").map { it.trim() }
println(arr)  //=> ["AAA", "BBB", "CCC"]
```

頻繁に使用する場合は、次にように `String` クラスに拡張関数を作ってしまうのもよいかもしれません。

```kotlin
/**
 * CSV 形式の文字列をカンマで分割します。
 * 各要素の先頭・末尾の空白は削除されます。
 */
fun String.parseCsv() : List<String> {
    return split(",").map { it.trim() }
}

// 使用例
fun main() {
    val arr = "1, 2, 3".parseCsv()
    println(arr)  //=> ["1", "2", "3"]
}
```


複数のデリミタ文字を指定する
----

Kotlin は `String` クラスの拡張関数として、`split` の別バージョンを定義しています。
例えば、複数のデリミタ文字を受け取ることができるようになっているので、下記のようにしてドット (`.`)、あるいはカンマ (`,`) で分割するといった処理を簡単に記述できます。

```kotlin
val s = "123.456,789"
val arr = s.split(".", ",")
println(arr)  //=> ["123", "456", "789"]
```

ちなみに Java で同じようなことを実現しようとすると、次のように正規表現を使うことになります。

```java
String s = "123.456,789";
String[] arr = s.split("\\.|,");
for (String elem : arr) {
    System.out.println(elem);
}
```

バックスラッシュ使ってエスケープしたりとか面倒ですね。

