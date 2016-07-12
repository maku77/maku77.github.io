---
title: "Node.js での例外処理: (2) Uncaught Exception（未捕捉例外）をハンドルする"
created: 2014-01-25
---

ハンドルされていない例外を、最上位でまとめてハンドルするには、以下のようにして `process` グローバルオブジェクトに、`uncaughtException` イベントのハンドラを設定します。

- [参考: Node.js - Process Event: 'uncaughtException'](https://nodejs.org/api/process.html#process_event_uncaughtexception)

#### sample.js

```javascript
process.on('uncaughtException', function (err) {
    console.log(err.name);
    console.log(err.message);
    console.log(err.toString());
    console.log('----------------------------------');
    console.log(err.stack);
    process.exit(-1);
});

foo();  // 未定義の関数を呼び出してみる
```

#### 実行結果

```
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
```

上記の例では、`uncaughtException` のハンドラ内で `process.exit()` を読んでいるので、例外が発生した場合にプロセスが終了します（デフォルト動作と同じ）。
例外を適切に処理し、`process.exit()` を呼ばないようにすれば、処理を継続させることができます。

