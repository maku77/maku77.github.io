---
title: Express のルート定義の URL のパラメータで数字のみを受け付けるようにする
created: 2014-09-22
---

`Application` オブジェクトのルート定義で指定するパラメータには、正規表現パターンを指定することが可能です。

```javascript
app.get('/memos/:id([0-9]+)', function (request, response) {
    res.json({
        title: 'ID=' + req.params.id
    });
});
```

上記のように設定すれば、このルート定義は `:id` パラメータの部分に数字だけを受け付けるようになります。

```
http://localhost/memos/123    // { "title" : "ID=123" }
http://localhost/memos/xyz    // Cannot GET /memos/xyz
```

