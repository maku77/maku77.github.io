---
title: "Node.jsメモ: プログラムの実行時間を計測する (console.time, console.timeEnd)"
url: "p/226tint/"
date: "2018-11-22"
tags: ["nodejs"]
aliases: /nodejs/console-time.html
---

Node.js でグローバルに定義されている `console` オブジェクトには、プログラムの経過時間を計るための下記のようなメソッドが用意されています。

- [console.time 系のメソッド](https://nodejs.org/api/console.html#console_console_time_label)
    - `console.time([label])`: 計測開始
    - `console.timeEnd([label])`: 計測終了して経過時間を stdio へ出力
    - `console.timeLog([label][, ...data])`: `console.time` と `console.timeEnd` の間で使用し、途中経過を stdio へ出力する


console.time の基本的な使い方
----

下記は、単純なループ処理にかかる経過時間を計測するサンプルです。

{{< code lang="js" title="sample.js" >}}
console.time('inc-100-times');

let a = 1;
for (let i = 0; i < 100; ++i) {
    a += 1;
}

console.timeEnd('inc-100-times');
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample.js
inc-100-times: 0.150ms
{{< /code >}}

`console.timeEnd` メソッドは結果の出力まで実行してくれるため、`console.log` などを呼び出して出力する必要がないことに注目してください。
そのため、自力で `Date` オブジェクトなどを生成して経過時間を計測する方法よりも、手軽にパフォーマンス計測を行うことができます。


console.timeLog で途中経過を出力する
----

次の例では、さらに `console.timeLog` を使用することで、`console.time` ～ `console.timeEnd` の間の途中経過を出力しています。

{{< code lang="js" title="sample2.js" >}}
// 計測したい任意の処理
function doHoge() {
    let a = 1.2345;
    for (let i = 0; i < 100; ++i) { a /= 2; }
}

// 5回計測
console.time('doHoge');
for (let i = 0; i < 5; ++i) {
    doHoge();
    console.timeLog('doHoge', `step=${i+1}`);
}
console.timeEnd('doHoge');
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample2.js
doHoge: 0.191ms step=1
doHoge: 3.376ms step=2
doHoge: 3.542ms step=3
doHoge: 3.608ms step=4
doHoge: 3.667ms step=5
doHoge: 3.751ms
{{< /code >}}

ちなみに、`console.timeEnd` を実行した後に `console.timeLog` を呼び出すと次のようなエラーになるので注意してください。

```
(node:18196) Warning: No such label 'doHoge' for console.timeLog()
```

`console.timeLog` は、あくまで `console.time` ～ `console.timeEnd` 間の途中経過を出力するために使用します。

