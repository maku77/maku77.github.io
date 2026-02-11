---
title: "JavaScriptメモ: あるオブジェクトが特定のクラスのインスタンスであるかを調べる (instanceof, constructor)"
url: "p/5wfhp95/"
date: "2018-02-19"
description: "クラスのインスタンスに対して typeof 演算子を適用すると、一律で 'object' という文字列を返すため、どのクラスのインスタンスであるかを調べるには別の方法が必要です。"
tags: ["javascript"]
aliases: [/js/object/instanceof.html]
---

JavaScript の `typeof` 演算子は、オブジェクト、配列、null に対して適用すると、すべて "object" という文字列を返します。
あるインスタンスが、特定のクラスのインスタンスであることを調べるには、`instanceof` 演算子を使用して判別する方法があります。

```js
const d = new Date();
if (d instanceof Date) {
    // d は Date インスタンスである
}
if (d instanceof Object) {
    // d は Object インスタンスでもある
}
```

他には、`constructor` プロパティを使用する方法があります。
`constructor` プロパティは、そのオブジェクトの生成に使われたコンストラクタ関数を返します。

```js
const d = new Date();
if ((d != null) && (d.constructor === Date)) {
    // d は Date インスタンスである
}
```

`constructor` プロパティを参照する方法では、親クラスのインスタンスであるということは判別できないので注意してください（プロトタイプチェーンを使用した継承構造を意識せず、単純にインスタンス生成時に使用されたコンストラクタ関数が参照されます）。

<div class="note">
上記の例では、<code>d != null</code> というチェックを先に行うことで、万が一 <code>d</code> が null だったり undefined だったりした場合にもエラーにならないようにしています。<code>instanceof</code> を使用する方法では、このような null チェックは必要ありません。
</div>
