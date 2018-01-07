---
title: RESTful API として検索用の URL を定義する
date: "2014-03-23"
---

例えば、`/memos` という URL でアクセスした場合はすべてのメモを取得し、`/memos?search=keyword` とクエリ文字列を指定した場合は検索結果を取得できるようにしたいとします。
このような場合は、`req.query` プロパティを参照して、特定のクエリが存在するかで処理を分けることで実現できます。

```javascript
app.get('/memos', function(req, res) {
    var keyword = req.query['search'];
    if (keyword) {
        res.send('Search: ' + keyword);
    } else {
        res.send('All memos');
    }
});
```

ハンドラの第３パラメータとして渡される `next` 関数を使用すれば、それぞれの処理を分離して記述することも可能です。

```javascript
function searchMemo(req, res, next) {
    var keyword = req.query['search'];
    if (keyword) {
        res.send('Search: ' + keyword);
    } else {
        next();
    }
}

function getAllMemos(req, res) {
    res.send('All memos');
}

app.get('/memos', searchMemo);
app.get('/memos', getAllMemos);
```

最後のルート設定部分は、以下のように 1 行にまとめて記述することもできます。

```javascript
app.get('/memos', searchMemo, getAllMemos);
```

