---
title: "Express サーバの HTTP アクセスログを表示する (express.logger() / morgan)"
created: 2014-09-22
---

Express の HTTP サーバログを表示するには、Logger 系のミドルウェアを使用します。
Application オブジェクトの `use()` メソッドで、以下のように Logger 系ミドルウェアを設定するだけです。
Express 3 では、Express に logger ミドルウェアが組み込まれていましたが、Express 4 からは、独立したミドルウェア (**morgan**) をロードする必要があります。

```javascript
// Express 3 まで（Express オブジェクトからミドルウェア生成可能）
app.use(express.logger());

// Express 4 以降（npm install morgan しておく）
var morgan = require('morgan');
app.use(morgan('combined'));
```

これにより、HTTP アクセスがあったときに、下記のような感じでターミナルにログ出力されるようになります。

```
127.0.0.1 - - [Sun, 21 Sep 2014 13:58:13 GMT] "GET / HTTP/1.1" 200 13 “-"
  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.122 Safari/537.36"
127.0.0.1 - - [Sun, 21 Sep 2014 13:58:13 GMT] "GET /favicon.ico HTTP/1.1" 404 - “-"
  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.122 Safari/537.36"
```

#### app.js

```javascript
var express = require('express');
var morgan = require('morgan');
var app = express();

app.use(morgan('combined'));
app.get('/', function (request, response) {
  response.send('Hello Express');
});

app.listen(3000);
```

上記では、組み込み定義のログフォーマット `combined` を指定していますが、フォーマットは自由に指定することができます。

```javascript
app.use(morgan(':method :url :status'));
```

#### 出力例

```
GET / 304
GET /hoge 404
```

ターミナルではなく、ファイルにログ出力することもできます。
詳しくは、morgan の Web サイトを参照してください。

- [expressjs/morgan](https://github.com/expressjs/morgan)

