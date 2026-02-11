---
title: "JavaScriptメモ: 精度の高いタイムスタンプを取得する (performance.now())"
url: "p/7fopb3d/"
date: "2018-03-07"
description: "パフォーマンス計測などの用途で経過時間を計りたいときは、Date.now() ではなく、performance.now() を使用すると、より精度の高い計測を行うことができます。"
tags: ["javascript"]
aliases: [/js/time/performance-now.html]
---

旧来の `Date.now()` を使用して得られるタイムスタンプは、ミリ秒単位の精度しかないため、より精度の高い計測を行いたい場合には使用できません。
W3C の [High Resolution Time](https://w3c.github.io/hr-time/) で定義されている [Performance インタフェース](https://w3c.github.io/hr-time/#sec-performance) を使用すると、**マイクロ秒**単位の経過時間を取得することができます。

```
[Exposed=(Window,Worker)]
interface Performance : EventTarget {
    DOMHighResTimeStamp now();
    readonly attribute DOMHighResTimeStamp timeOrigin;
    [Default] object toJSON();
};
```

[performance.now()](https://developer.mozilla.org/en-US/docs/Web/API/Performance/now) 関数は、そのコンテキストが生成された時点からの経過時間を返します（典型的には、ブラウザがあるページを表示した時点からの経過時間です）。

```javascript
console.log(performance.now())  //=> 12.499999997089617
```

**整数部分がミリ秒、小数点以下がマイクロ秒**を表しています。
上記の例では、ブラウザでページ表示を行った時点から、約 12.4999 ミリ秒経過していることを示しています。
ユーザエージェント（ブラウザ環境など）が対応していれば、最大 5 マイクロ秒の精度で情報を得られるとされています。
戻り値の型は、正確には `DOMHighResTimeStamp` 型として定義されていますが、`double` の `typedef` であり、一般的な浮動小数点数型として使用すれば問題ありません。

典型的な使用例としては、下記のようなパフォーマンス計測があります。

```javascript
const t0 = performance.now();
doSomething();
const t1 = performance.now();
console.log('doSomething took %f ms', (t1 - t0));
```

逆に、このような用途で `Date.now()` を使用すると、精度が悪いだけでなく、思わぬ結果を招くことがあります。
`Date.now()` は OS のシステムクロックに基づいたタイムスタンプを返すため、数値が遡る（あとから呼び出した方の値が小さくなる）ことがあるためです。
`performance.now()` を使用しておけば、同一コンテキスト内においては**単調増加 (monotonic increase) な値を返すことが保証されている**ため、安心してパフォーマンス計測などの用途に使用することができます。

<div class="note">
単調増加と言っても、同一コンテキスト内の処理であればという条件はあります。
例えば、Worker の中から <code>performance.now()</code> を呼び出して得られる値は、Worker が作成された時間が基準になるため、メインコンテキストで <code>performance.now()</code> を呼び出して得られる値より小さくなります。
</div>
