---
title: mongoose で MongoDB (4-2) DB からドキュメントを検索する
created: 2014-02-10
---

DB のコレクションからドキュメントを検索するには、`Model` クラスの下記のようなクラスメソッドを使用します。

- `Model.find()` -- 指定した条件ですべてのドキュメントを取得
- `Model.findOne()` -- 指定した条件で 1 つだけドキュメントを取得
- `Model.findById()` -- 指定した ID のドキュメントを取得

コレクション (`memos`) に格納されている全てのドキュメントを取得するには以下のようにします（ここでは、既に `Memo` という名前のモデルクラスが定義されているとします）。

```javascript
Memo.find(function(err, memos) {
  if (err) {
    console.log('ERROR:', err.message);
    return;
  }
  memos.forEach(function (m) {
    console.log(m);
  });
});
```

モデルクラスの `find()` メソッドには、特定のフィールドを正規表現で検索する機能もあります。

```javascript
// title フィールドに Hoge を含むドキュメントを検索
Memo.find({ title: /Hoge/ }, callback);

// title フィールドが Hoge と完全一致するものを検索
Memo.find({ title: 'Hoge' }, callback);
```

