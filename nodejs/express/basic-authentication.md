---
title: Express で Basic 認証を有効にする
date: "2013-10-29"
---

Express に標準で付属している、`basicAuth` ミドルウェアを使用すると、簡単に Basic 認証をかけることができます。

#### app.js

```javascript
var express = require('express');
var app = express();

app.use(express.basicAuth('user', 'pass'));

app.get('/', function(req, res) {
    res.send('Private');
});

app.listen(3000, function() {
    console.log('Listening on port 3000');
});
```

特定のパス以下にだけ Basic 認証をかけるには、ルーティング設定で `basicAuth` ミドルウェアのオブジェクトを渡します。
下記の例では、`/private` という URI でアクセスしたときに Basic 認証がかかります。

```javascript
var auth = express.basicAuth('user', 'pass');
app.get('/', function(req, res) {
    res.send('Public information');
});
app.get('/private', auth, function(req, res) {
    res.send('Private information');
});
```

