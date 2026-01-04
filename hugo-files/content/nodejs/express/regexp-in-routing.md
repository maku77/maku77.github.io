---
title: "Node.jsメモ: Express のルート定義の URL のパラメータで数字のみを受け付けるようにする"
url: "p/f2ow3kr/"
date: "2014-09-22"
tags: ["nodejs"]
aliases: /nodejs/express/regexp-in-routing.html
---

Express の `Application` オブジェクトのルート定義で指定するパラメータには、正規表現パターンを指定することが可能です。

```javascript
app.get('/memos/:id([0-9]+)', function (request, response) {
  res.json({
    your_request_id: req.params.id
  });
});
```

上記のように設定すると、`/memos/` に続く部分（`id:` パラメータ）が数字のみの場合にマッチします。`[0-9]+` は1文字以上の数字を意味する正規表現です。

```
http://localhost/memos/123    // { "your_request_id" : "123" }
http://localhost/memos/xyz    // Cannot GET /memos/xyz
```

