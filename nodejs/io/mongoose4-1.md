---
title: mongoose で MongoDB (4-1) DB にドキュメントを追加する
created: 2014-02-10
---

DB のコレクションにドキュメントを追加するには、`Model` インスタンスのメソッド `save()`、あるいはクラスメソッドの `create()` を使用します。

- [Model#save()](http://mongoosejs.com/docs/api.html#model_Model-save) -- そのモデルを DB に保存
- [Model.create()](http://mongoosejs.com/docs/api.html#model_Model.create) -- 指定したモデルを DB に保存

新しいモデルオブジェクトを生成し、DB のコレクション (`memos`) に追加するには以下のようにインスタンスメソッドの `save()` を使用します（ここでは、既に `Memo` という名前のモデルクラスが定義されているとします）。

```javascript
// モデルオブジェクトの作成
var m = new Memo({title: 'Title1', body: 'Body1'});

// DB に保存（コレクション 'memos' に上記のドキュメントを格納）
m.save(function (err, model) {
  if (err) {
    console.log('ERROR:', err.message);
    return;
  }
  console.log('ADDED:', model);
});
```

モデルオブジェクトを生成せずに、直接 DB のコレクションに追加するには以下のようにクラスメソッドの `create()` を使用します。

```javascript
Memo.create({ title: 'Title2' }, function (err, model) {
  if (err) {
    console.log('ERROR:', err.message);
    return;
  }
  console.log('ADDED:', model);
});
```

