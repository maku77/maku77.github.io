---
title: Express で CORS の設定を行う（クロスドメインでの Ajax を許可）
date: "2013-12-01"
---

特定のリクエスト時にクロスドメイン通信を許すには、下記のように特定のリクエストに対する `response` で `Access-Control-Allow-Origin` ヘッダを追加します。

```javascript
var express = require('express');
var app = express();

app.get('/books', function (request, response) {
  response.header('Access-Control-Allow-Origin', '*');
  response.json(books);
});
```

アプリケーション全体で CORS 設定を行うには以下のように `app.use()` を利用します。

```javascript
// CORS settings.
var allowCrossDomain = function(req, res, next) {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'X-Requested-With');
  next();
}
app.use(allowCrossDomain);
```

特定のドメインからのアクセスだけを許可するには、以下のようにします。

```javascript
res.header('Access-Control-Allow-Origin', 'http://example.com');
```

特定のサブドメインからのアクセスだけを許可するには、以下のようにします。
`Access-Control-Allow-Origin` ヘッダに指定できるドメインは 1 つだけなので、プログラム内で動的にサブドメイン名を設定する必要があります。

```javascript
// CORS settings.
var allowCrossDomain = function(req, res, next) {
  if (req.headers.origin.endsWith('.example.com')) {
    res.header('Access-Control-Allow-Origin', req.headers.origin);
    res.header('Access-Control-Allow-Headers', 'X-Requested-With');
  }
  next();
}
app.use(allowCrossDomain);

// In case String#endsWith is not defined.
if (!String.prototype.endsWith) {
    Object.defineProperty(String.prototype, 'endsWith', {
        enumerable: false,
        configurable: false,
        writable: false,
        value: function (searchString, position) {
            position = position || this.length;
            position = position - searchString.length;
            return this.lastIndexOf(searchString) === position;
        }
    });
}
```

