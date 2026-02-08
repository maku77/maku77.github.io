---
title: "JavaScriptメモ: JavaScript ではグローバル変数も関数も全てプロパティ"
url: "p/abfcfbh/"
date: "2012-11-09"
tags: ["javascript"]
aliases: [/js/syntax/global-object.html]
---

JavaScript では、定義した変数や関数は何らかのオブジェクトのプロパティとして保持されます。

```javascript
function add(a, b) {
  return a + b;
}
print(add(100, 200));
```

上記の `add` 関数はグローバルなスコープで定義しており、どのオブジェクトにも属していないように見えますが、実はグローバルオブジェクトのプロパティとして保持されています。

グローバルオブジェクトにアクセスするには、グローバルなスコープで `this` を参照します。
ただし、グローバルオブジェクトの `this` は省略して記述できるので、通常は、上記の `print` 関数、`add` 関数の例のように、あたかもグローバル空間に定義された関数のようにアクセスできます。
冗長に書くのであれば、下記のようにも書けるということです。

```javascript
this.print(this.add(100, 200));
```

これは、関数じゃなくて変数の場合も同様です。

```javascript
var obj = {x:100, y:200};
print(obj.x);
print(this.obj.x);  // 同上
```
