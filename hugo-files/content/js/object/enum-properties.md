---
title: "JavaScriptメモ: オブジェクトのプロパティを列挙する (for-in)"
url: "p/zxqg4xp/"
date: "2012-12-02"
tags: ["javascript"]
aliases: [/js/object/enum-properties.html]
---

オブジェクトのプロパティの列挙には、以下の for in 構文を使用します。

* `for (var k in obj)` でプロパティ名の列挙
* `for each (var v in obj)` でプロパティ値の列挙

ただし、`for each` の方は、ECMAScript では定義されておらず、JavaScript の独自拡張です。
前者の構文を使用するのがよいでしょう。

{{< code lang="javascript" title="サンプルコード" >}}
const obj = {x:1, y:2, z:3};
for (let k in obj) { console.log(k); }
{{< /code >}}

{{< code title="実行結果" >}}
x
y
z
{{< /code >}}

上記のようにプロパティを列挙すると、プロトタイプ継承したプロパティも含めて列挙されます。
