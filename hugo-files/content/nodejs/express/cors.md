---
title: "Node.jsメモ: Express で CORS 設定を行う（クロスドメインでのアクセスを許可する）"
url: "p/unsuyqg/"
date: "2013-12-01"
tags: ["nodejs"]
aliases: /nodejs/express/cors.html
---

Express で特定のリクエスト時にクロスドメイン通信を許すには、下記のように特定のリクエストに対する `response` で **`Access-Control-Allow-Origin`** ヘッダーを追加します。

```js
const express = require('express');
const app = express();

app.get('/books', function (request, response) {
  response.header('Access-Control-Allow-Origin', '*');
  response.json(books);
});
```

アプリケーション全体で CORS 設定を行うには、次のように **`app.use()`** を使ってすべてのレスポンスに適用するようにします。

```js
// CORS settings.
const allowCrossDomain = function(req, res, next) {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'X-Requested-With');
  next();
}

app.use(allowCrossDomain);
```

特定のドメインからのアクセスだけを許可するには、以下のようにします。

{{< code lang="js" title="example.com ドメインのサイトからの JavaScript 通信を許可" >}}
res.header('Access-Control-Allow-Origin', 'https://example.com');
{{< /code >}}

特定のサブドメインからのアクセスだけを許可するには、以下のようにします。
`Access-Control-Allow-Origin` ヘッダーに指定できるドメインは 1 つだけなので、プログラム内で動的にサブドメイン名を設定する必要があります。

```js
// CORS settings.
const allowCrossDomain = function(req, res, next) {
  if (req.headers.origin.endsWith('.example.com')) {
    res.header('Access-Control-Allow-Origin', req.headers.origin);
    res.header('Access-Control-Allow-Headers', 'X-Requested-With');
  }
  next();
}

app.use(allowCrossDomain);
```

