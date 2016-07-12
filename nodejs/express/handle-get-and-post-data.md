---
title: "GET/POST メソッドで送られてきたデータを取得する"
created: 2013-10-29
---

HTTP の GET/POST メソッドで送られてきたデータは、`Request` オブジェクトの以下のプロパティを使って取得することができます。

* `req.query['name']`  -- GET メソッドの場合
* `req.body['name']`  -- POST メソッドの場合

上記の代わりに `req.param()` メソッドを使用すると、GET/POST の区別無しにパラメータ取得することができますが、できれば GET/POST は明示的に区別して取得する方がよいでしょう（URI の部分文字列を取得する `req.params` プロパティと混同しないようにしてください）。

GET メソッドにおけるデータ受信の例
----

次のサンプルでは、`public/memo.html` のフォームから、`/memo/create` という URI に対して GET でデータを送ります。

#### public/memo.html（GET でデータを送るフォーム）

```html
<!DOCTYPE html>
<meta charset="UTF-8">
<html>
    <b>New Memo</b>
    <form method="get" action="/memo/create">
        <label for="title">Title</label>
        <input type="text" id="title" name="memo[title]" size="50"></input><br>
        <label for="body">Body</label>
        <input type="text" id="body" name="memo[body]" size="50"></input><br>
        <button type="submit">Submit</button>
    </form>
</html>
```

#### app.js

```javascript
var express = require('express');
var app = express();

app.use(express.static('./public'));  // for publishing public/memo.html

app.get('/memo/create', function(req, res) {
    var memo = req.query['memo'];
    var s = 'Title = ' + memo['title'] + '<br>' +
            'Body = ' + memo['body'];
    res.send(s);
});

app.listen(3000, function() {
    console.log('Listening on port 3000');
});
```

テストするときは、オブジェクトを `JSON.stringify()` で文字列に出力した方が手っ取り早いかもですね。


POST メソッドにおけるデータ受信の場合
----

POST メソッドで送られたデータを `req.body` プロパティで参照するには、`bodyParser` ミドルウェアを設定しておく必要があります。

#### public/memo.html（POST でデータを送るフォーム）

```html
<!DOCTYPE html>
<meta charset="UTF-8">
<html>
    <b>New Memo</b>
    <form method="post" action="/memo/create">
        <label for="title">Title</label>
        <input type="text" id="title" name="memo[title]" size="50"></input><br>
        <label for="body">Body</label>
        <input type="text" id="body" name="memo[body]" size="50"></input><br>
        <button type="submit">Submit</button>
    </form>
</html>
```

#### app.js

```javascript
var express = require('express');
var app = express();

app.use(express.bodyParser());  // Handle POST messages
app.use(express.static('./public'));

app.post('/memo/create', function(req, res) {
    var memo = req.body['memo'];
    var s = 'Title = ' + memo['title'] + '<br>' +
            'Body = ' + memo['body'];
    res.send(s);
});

app.listen(3000, function() {
    console.log('Listening on port 3000');
});
```

ここでは、フォームから送られたデータを `req.query['name']` や `req.body['name']` のように取得しましたが、GET/POST で JSON データなどを直接送ってきた場合は、そのオブジェクトを `req.query` や `req.body` で直接取得することができます。
例えば、jQuery の `$.post()` で以下のように JSON データを送ったとします。

```javascript
var data = {title:'Title1', body:'Body1'};
$.post('/memos/create', data, function() {}, 'JSON');
```

このようにクライアントから送られたデータは、以下のように取得できます。このケースでも `bodyParser` ミドルウェアは必要です。

```javascript
app.use(express.bodyParser());
app.post('/memos/create', function(req, res) {
    var data = req.body;
    ...
}
```

