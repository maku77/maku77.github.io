---
title: "Kotlinメモ: マップのキー／値をまとめて変更する (mapKeys, mapValues)"
url: "/p/633znjc/"
date: "2019-06-03"
tags: ["kotlin"]
aliases: ["/kotlin/collection/map-keys.html"]
---

マップ要素のキーをまとめて変更する (mapKeys)
----

既存のマップのキーとして使われている値をまとめて変更するには、**`mapKeys`** を使用します。
`mapKeys` にラムダ式を渡すと、各要素のキー＆バリューを保持した **`Map.Entry`** オブジェクトがそのラムダ式に順番に渡されます。
各ループ処理でラムダ式が返した戻り値が新しいキーとして扱われます。
`Map.Entry` オブジェクトからは、**`key`** で要素のキー、**`value`** で要素の値を参照することができます。

{{< code lang="kotlin" title="例: マップ内のキーをすべて大文字に変換する" >}}
val map = mapOf("aaa" to 1, "bbb" to 2, "ccc" to 3)
val map2 = map.mapKeys { it.key.toUpperCase() }
println(map2)  //=> {AAA=1, BBB=2, CCC=3}
{{< /code >}}

`mapKeys` の戻り値はマップ型です。


マップ要素の値をまとめて変更する (mapValues)
----

**`mapValues`** も `mapKeys` に似ていますが、こちらはマップ要素の値をまとめて変更します。
ラムダ式は、変更後の要素の値が戻り値になるように実装します。

{{< code lang="kotlin" title="例: マップ内の値をすべて 2 倍にする" >}}
val map = mapOf("aaa" to 1, "bbb" to 2, "ccc" to 3)
val map2 = map.mapValues { it.value * 2 }
println(map2)  //=> {aaa=2, bbb=4, ccc=6}
{{< /code >}}

`mapValues` の戻り値はマップ型です。

