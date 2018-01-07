---
title: mongoose で MongoDB (4-4) DB からドキュメントを削除する
date: "2014-02-11"
---

DB のコレクションからドキュメントを削除するには、以下のようなクラスメソッド、あるいはインスタンスメソッドを使用します。

- [Model.remove()](http://mongoosejs.com/docs/api.html#model_Model.remove) -- 条件に一致するドキュメントを DB からすべて削除
- [Model.findOneAndRemove()](http://mongoosejs.com/docs/api.html#model_Model.findOneAndRemove) -- 条件に一致するドキュメントを 1 つだけ削除
- [Model#remove()](http://mongoosejs.com/docs/api.html#model_Model-remove) -- そのモデルが表すドキュメントを削除

#### 例: title フィールドに 'Hoge' を含むドキュメントをすべて削除

```javascript
Memo.remove({ title: /Hoge/ }, function (err, count) {
    if (err) {
        console.log('ERROR:', err.message);
        return;
    }
    console.log('REMOVED:', count);
});
```

