---
title: "Node.jsメモ: 現在位置のスタックトレースを出力する (console.trace)"
url: "p/oeka5t6/"
date: "2018-11-22"
tags: ["nodejs"]
aliases: /nodejs/debug/console-trace.html
---

Node.js にグローバルで定義されている `console` オブジェクトの `trace` メソッドを呼び出すと、現在位置のスタックトレースを標準エラー出力に出力してくれます。

- 参考: [console.trace メソッドの定義](https://nodejs.org/api/console.html#console_console_trace_message_args)

下記のサンプルでは、`world()` 関数の中で `console.trace()` を呼び出しています。

{{< code lang="js" title="sample.js" >}}
function hello() {
    world();
}

function world() {
    console.trace();
}

hello();
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample.js
Trace
    at world (D:\sandbox\test-node\sample.js:8:13)
    at hello (D:\sandbox\test-node\sample.js:4:5)
    at Object.<anonymous> (D:\sandbox\test-node\sample.js:11:1)
    at Module._compile (internal/modules/cjs/loader.js:688:30)
    at Object.Module._extensions..js (internal/modules/cjs/loader.js:699:10)
    at Module.load (internal/modules/cjs/loader.js:598:32)
    at tryModuleLoad (internal/modules/cjs/loader.js:537:12)
    at Function.Module._load (internal/modules/cjs/loader.js:529:3)
    at Function.Module.runMain (internal/modules/cjs/loader.js:741:12)
    at startup (internal/bootstrap/node.js:285:19)
{{< /code >}}

出力されたトレースの上の方を見ると、`hello()` → `world()` という順に呼び出されていることが分かります。

