---
title: "Node.jsメモ: Express で Basic 認証を有効にする (basicAuth)"
url: "p/mv6umxp/"
date: "2013-10-29"
tags: ["nodejs"]
aliases: /nodejs/express/basic-authentication.html
---

Express に標準で付属している **`basicAuth`** ミドルウェアを使用すると、簡単に Basic 認証をかけることができます。
次の例では、サイト全体に Basic 認証をかけています（ユーザー名: `user`、パスワード: `pass`）。

{{< code lang="js" title="app.js" >}}
const express = require('express');
const app = express();

app.use(express.basicAuth('user', 'pass'));

app.get('/', function(req, res) {
    res.send('Private');
});

app.listen(3000, function() {
    console.log('Listening on port 3000');
});
{{< /code >}}

特定のパス以下にだけ Basic 認証をかけるには、`app.get()` や `app.post()` でルーティング設定するときに `basicAuth` ミドルウェアのオブジェクトを渡します。
下記の例では、`/private` という URI でアクセスしたときに Basic 認証がかかります。

```js
const auth = express.basicAuth('user', 'pass');

// ここには Basic 認証をかけない
app.get('/', function(req, res) {
    res.send('Public information');
});

// ここに Basic 認証をかける
app.get('/private', auth, function(req, res) {
    res.send('Private information');
});
```

