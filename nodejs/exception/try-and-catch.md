---
title: "Node.js での例外処理: (1) try ～ catch による例外処理"
created: 2014-01-25
---

実行時のエラーをハンドルする
----

Node.js での処理中に何かエラーが発生した場合、そのエラーをハンドルするコードがないと、デフォルトではスタックトレースが表示されて処理が終了します。
明示的にエラーをハンドルするには、`try` ～ `catch` の構文を使用します（Node.js 特有ではなく、通常の JavaScript の構文です）。
以下の例では、未定義の関数を呼び出したときに発生する `ReferenceError` をハンドルしています。

#### 例: ReferenceError のハンドル

```javascript
try {
    foo();
} catch (err) {
    console.log(err.name + ': ' + err.message);
    process.exit(-1);
}
```

#### 実行結果

```
ReferenceError: foo is not defined
```


Error オブジェクト
----

コールバック関数に渡される `Error` オブジェクトは、V8 (JavaScript Engine) でネイティブに定義されているオブジェクトで、以下のようなプロパティを参照できます。

* `err.name` → エラー名
* `err.message` → エラーメッセージ
* `err.toString()` → `err.name` + ": " + `err.message`
* `err.stack` → スタックトレース

#### err.name の例

```
ReferenceError
```

#### err.message の例

```
foo is not defined
```

#### err.toString() の例

```
ReferenceError: foo is not defined
```

#### err.stack の例

```
ReferenceError: foo is not defined
    at Object.<anonymous> (/Users/maku/sample.js:2:5)
    at Module._compile (module.js:456:26)
    at Object.Module._extensions..js (module.js:474:10)
    at Module.load (module.js:356:32)
    at Function.Module._load (module.js:312:12)
    at Function.Module.runMain (module.js:497:10)
    at startup (node.js:119:16)
    at node.js:902:3
```


エラーごとに処理を分ける
----

エラーの種類 (`err.name`) ごとに、ハンドル処理を切り替えるには、例外を `catch` したときに取得するオブジェクトの種類を `instanceof` で調べます。

```javascript
try {
    foo();
} catch (err) {
    if (err instanceof ReferenceError) {
        // ...
    } else {
        // ...
    }
}
```

