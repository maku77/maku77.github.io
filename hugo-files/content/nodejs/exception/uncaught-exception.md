---
title: "Node.js での例外処理: Uncaught Exception（未捕捉例外）をハンドルする"
url: "p/4zwjrzz/"
date: "2014-01-25"
tags: ["nodejs"]
aliases:
  - /nodejs/exception/uncaught-exception.html
  - /nodejs/exception/domain-for-exception.html
---

Uncaught Exception を処理する
----

ハンドルされていない例外を最上位でまとめてハンドルするには、以下のように **`process` グローバルオブジェクトで `uncaughtException` イベントをハンドル** するよう設定します。

- [参考: Node.js - Process Event: 'uncaughtException'](https://nodejs.org/api/process.html#process_event_uncaughtexception)

{{< code lang="javascript" title="sample.js" >}}
process.on('uncaughtException', function (err) {
    console.log(err.name);
    console.log(err.message);
    console.log(err.toString());
    console.log('----------------------------------');
    console.log(err.stack);
    process.exit(-1);
});

foo();  // 未定義の関数を呼び出してみる
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
ReferenceError
foo is not defined
ReferenceError: foo is not defined
----------------------------------
ReferenceError: foo is not defined
    at Object.<anonymous> (/Users/maku/sandbox/test.js:10:1)
    at Module._compile (module.js:456:26)
    at Object.Module._extensions..js (module.js:474:10)
    at Module.load (module.js:356:32)
    at Function.Module._load (module.js:312:12)
    at Function.Module.runMain (module.js:497:10)
    at startup (node.js:119:16)
    at node.js:902:3
{{< /code >}}

上記の例では、`uncaughtException` のハンドラ内で `process.exit()` を呼んでいるので、例外が発生した場合にプロセスが終了します (デフォルト動作と同じ)。
例外を適切に処理し、`process.exit()` を呼ばないようにすれば、処理を継続させることができます。


ドメインごとに Uncaught Exception をハンドルする
----

**`Domain`** オブジェクトを使用すると、あるコンテキスト内で発生したエラーだけをハンドルすることができます。
`Domain` オブジェクトの `run` メソッドを使って任意の処理を実行すると、その中 (ドメイン) で発生したエラーが `Domain` オブジェクトによってハンドルされるようになります。
エラー発生時に実行したいエラーハンドラは、`Domain` オブジェクトの `error` イベントハンドラとしてあらかじめ設定しておく必要があります。

{{< code lang="javascript" title="sample.js" >}}
var domain = require('domain');

// ドメインの作成
var d = domain.create();

// ドメイン内で発生したエラーをハンドル
d.on('error', function(err) {
    console.log(err.name + ': ' + err.message);
});

// ドメイン内でエラーを発生させる
d.run(function() {
    throw new Error('my error');
});
{{< /code >}}

{{< code lang="console" title="実行結果" >}}
$ node sample
Error: my error
{{< /code >}}

