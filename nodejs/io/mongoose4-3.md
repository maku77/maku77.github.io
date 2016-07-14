---
title: mongoose で MongoDB (4-3) DB のドキュメントを更新する
created: 2014-02-11
---

DB のコレクションに格納されているドキュメントの内容を更新するには、以下のようなインスタンスメソッド、あるいはクラスメソッドを使用します。

- `Model#save()` -- そのモデルが表すドキュメントを更新
- `Model.update()` -- 条件に一致するドキュメントのフィールドをすべて更新
- `Model.findByIdAndUpdate()` -- 指定した ID のドキュメントのフィールドを更新

`save()` メソッドは、まだ DB に存在しないモデルに対して実行すると、ドキュメントの追加になりますが、既に存在するモデルに対して実行すると、ドキュメントの更新になります。

#### 例: title フィールドに 'Hoge' を含むドキュメントを 1 つ更新

```javascript
Memo.findOne({title: /Hoge/}, function (err, model) {
    if (err) {
        console.log(err.message);
        return;
    }
    if (!model) {
        console.log('Not found');
        return;
    }

    // 内容を更新
    model.title = 'Title Modified';
    model.save(function (err, model) {
        if (err) {
            console.log(err.message);
            return;
        }
        console.log('UPDATED:', model);
    });
});
```

