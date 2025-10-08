---
title: "Kotlinメモ: マップからキーのリスト、値のリストを作成する (keys, values)"
url: "/p/6r9c6vu/"
date: "2019-06-03"
tags: ["kotlin"]
aliases: ["/kotlin/collection/keys-values.html"]
---

マップはキーと値のペアを保持するコレクションですが、**`keys`** プロパティでキーだけのリスト、**`values`** プロパティで値だけのリストを取得することができます。

```kotlin
val map = mapOf("AAA" to 1, "BBB" to 2, "CCC" to 3)
println(map.keys)    //=> ["AAA", "BBB", "CCC"]
println(map.values)  //=> [1, 2, 3]
```

