---
title: "あるインスタンスがどのクラスのオブジェクトなのか調べる (javaClass)"
date: "2019-04-26"
---

Kotlin のあるインスタンスが、どのクラスのインスタンスかを調べるには、**`javaClass`** プロパティを使用します（Java の `Object#getClass()` に相当します）。

下記の例では、コレクション系のオブジェクトが、どのクラスのインスタンスなのかを調べています。

```kotlin
val array = arrayOf(1, 2, 3)
val list = listOf(1, 2, 3)
val arrayList = arrayListOf(1, 2, 3)
val set = setOf("A", "B", "C")
val hashSet = hashSetOf("A", "B", "C")
val map = mapOf("A" to 1, "B" to 2)
val hashMap = hashMapOf("A" to 1, "B" to 2)

println(array.javaClass)      //=> class [Ljava.lang.Integer;
println(list.javaClass)       //=> class java.util.Arrays$ArrayList
println(arrayList.javaClass)  //=> class java.util.ArrayList
println(set.javaClass)        //=> class java.util.LinkedHashSet
println(hashSet.javaClass)    //=> class java.util.HashSet
println(map.javaClass)        //=> class java.util.LinkedHasMap
println(hashMap.javaClass)    //=> class java.util.HashMap
```

この結果から、Kotlin のコレクション系クラスには Java の実装が用いられていることがわかります。

ちなみに、`javaClass` によって得られるインスタンスは、すべて `java.lang.Class` のインスタンスです。

```kotlin
println(list.javaClass.javaClass)  //=> class java.lang.Class
```

インスタンスではなく、クラスの型から `java.lang.Class` オブジェクトを取得するには、**`Class名::class.java`** のようにします。

```kotlin
println(ArrayList::class.java)  //=> class java.lang.ArrayList
```

