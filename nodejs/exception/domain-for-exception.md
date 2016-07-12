---
title: "Node.js での例外処理: (3) ドメインごとに Uncaught Exception（未捕捉例外）をハンドルする"
created: 2014-01-25
---

`Domain` オブジェクトを使用すると、あるコンテキスト内で発生したエラーだけをハンドルすることができます。

`Domain` オブジェクトの `run` メソッドを使って任意の処理を実行すると、その中（ドメイン）で発生したエラーが `Domain` オブジェクトによってハンドルされるようになります。
エラー発生時に実行したいエラーハンドラは、`Domain` オブジェクトの `error` イベントハンドラとしてあらかじめ設定しておく必要があります。

#### sample.js

```javascript
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
```

#### 実行結果

```
$ node sample
Error: my error
```

