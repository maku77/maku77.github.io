---
title: "Node.jsメモ: Express で検索用の URL を定義する (req.query)"
url: "p/ryqsrbv/"
date: "2014-03-23"
tags: ["nodejs"]
aliases: /nodejs/express/url-for-search.html
---

Express で検索用の URL を定義する方法について説明します。
例えば、`/memos` という URL でアクセスした場合はすべてのメモを取得し、`/memos?q=keyword` とクエリ文字列を指定した場合は検索結果を取得できるようにしたいとします。
このような場合は、**`req.query`** プロパティを参照して、特定のクエリが存在するかで処理を分けることで実現できます。

```javascript
app.get('/memos', function(req, res) {
  var keyword = req.query['q'];
  if (keyword) {
    res.send('q: ' + keyword);
  } else {
    res.send('All memos');
  }
});
```

ハンドラの第３パラメータとして渡される `next` 関数を使用すれば、それぞれの処理を分離して記述することも可能です。

- 参考: [Express で next() により次のハンドラへ処理を委譲する](/p/izz3fkg/)

```javascript
function searchMemo(req, res, next) {
  var keyword = req.query['q'];
  if (keyword) {
    res.send('q: ' + keyword);
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

