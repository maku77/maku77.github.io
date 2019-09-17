---
title: "クラス内の MutableList を immutable な List にして公開する"
date: "2019-09-17"
---

- 参考: [immutable なコレクションを mutable に変換する (toMutableList, toMutableSet, toMutableMap)](../collection/to-mutable.html)

上記の記事では、immutable（不変）な `List` から `MutableList` を生成する例を示していますが、逆に、mutable（可変）なオブジェクトを immutable にして見せたいことがあります。
定型的なのは、クラス内のフィールドとして `MutableList` を持っているときに、不変なリストとして外部に公開したいケースです。
このような場合は、単純に戻り値の型を `List` にアップキャストしたメソッド（あるいはプロパティ）を用意するだけで対応できます。

```kotlin
class TitleList {
    private val mutableTitles = mutableListOf<String>()
    val titles: List<String> = mutableTitles

    // メンバーメソッドからは内部のリストを変更できる
    fun changeInternally1() {
        mutableTitles.add("Title1")
    }

    fun changeInternally2() {
        mutableTitles.add("Title2")
    }
}

fun main() {
    val list = TitleList()
    list.changeInternally1()
    list.changeInternally2()
    println(list.titles)  //=> [Title1, Title2]

    // 下記はエラー
    list.titles.add("Title3")  // Unresolved reference: add
}
```

ただし、このような実装はスレッドセーフではないことに注意してください。
`titles` プロパティが参照するリストオブジェクトの実体は、`mutableTitles` が参照するものと同じだからです。
下記のように実行すると、`titles` 変数の内容が、外部要因によってどんどん変化していくことが分かります。

```kotlin
fun main() {
    val list = TitleList()
    val titles = list.titles
    println(titles)  //=> []

    list.changeInternally1()
    println(titles)  //=> [Title1]

    list.changeInternally2()
    println(titles)  //=> [Title1, Title2]
}
```

`titles` プロパティによって取得したリストの内容が別スレッドから変更されないことを保証するには、下記のようにコピーして作成したリストを返すように実装します。

```kotlin
class TitleList {
    private val mutableTitles = mutableListOf<String>()
    val titles: List<String>
        get() = mutableTitles.toList()

    //...
}
```

このように、外部からの変更の恐れをなくすために丸ごとコピーしちゃう手法を **防御的コピー (defencive copying)** と呼びます。
Effective Java の「項目39 必要な場合には、防御的にコピーする」に詳しく記述されています。


おまけ Android の LiveData の例
----

Android アプリを作成していると、LiveData まわりで同様の処理が必要になったります。

```kotlin
class PlayerViewModel : ViewModel() {
    private val life_ = MutableLiveData<Int>
    val life: LiveData<Int> = life_
    //...
}
```

`MutableLiveData` フィールドを private に定義しておくことで、内部からは LiveData の値を変更できるようにしつつ、外部からはリードオンリーな `LiveData` 型で参照するようにしています。

