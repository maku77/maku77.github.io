---
title: Express で Web サーバを作成する
date: "2013-10-22"
---

Express のメインオブジェクトは Application オブジェクトと呼ばれ、以下のようにして生成します。
慣習的に変数名は `app` にします。

```javascript
var express = require('express');
var app = express();
```

Web サーバに対して GET メソッドによるリクエストが来たときの処理は、以下のように指定します。

```javascript
app.get('/', function(req, res) {
    res.send('Hello Express!');
});
```

上記では、ルートパスにアクセスしたときのレスポンスを定義していますが、Backbone.js や CodeIgniter のように、柔軟に URI ルーティング設定を行うことができます。

#### server.js

```javascript
var express = require('express');
var app = express();

app.get('/', function(req, res) {
    res.send('Hello Express!');
});

app.get('/hello/:name', function(req, res) {
    res.send('Hello ' + req.params.name);
});

app.listen(3000, function() {
    console.log('Express server started on port 3000...');
});
```

#### 実行

```
$ node server.js
Express server started on port 3000...
```

Web サーバを起動したら、`http://localhost:3000/` にアクセスして `Hello Express!` と表示されれば成功です。
`http://localhost:3000/hello/Maku` のようにアクセスすると、`Hello Maku` と表示されます。


コラム（express.createServer は古い）
----

『パーフェクト JavaScript』と『プロになるための JavaScript 入門』 の HelloWorld では、以下のように Application オブジェクトを作成していますが、

```javascript
var express = require('express');
var app = express.createServer();
```

`express.createServer` は既に deprecated API になっているので、次のように Application オブジェクトを生成するのが正解です。

```javascript
var express = require("express");
var app = express();
```

