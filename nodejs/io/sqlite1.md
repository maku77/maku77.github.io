---
title: "Node.js で SQLite を使用する (1) node-sqlite3 のインストール"
date: "2014-01-24"
---

node-sqlite3 のインストール
----

Node.js で SQLite を使用するためのライブラリとして [node-sqlite3](https://github.com/mapbox/node-sqlite3) があります。
以下のようにインストールします。

```
$ npm install sqlite3
```

node-sqlite3 の使用例
----

下記は、`dic.sqlite3` という DB ファイルから、`words` テーブルのレコードを取得するサンプルです。

```javascript
var sqlite3 = require('sqlite3');
var db = new sqlite3.Database('dic.sqlite3');

db.all('SELECT en, jp FROM words', function(err, rows) {
    if (err) {
        console.log(err);
        return;
    }
    rows.forEach(function(row) {
        console.log(row.en + ' ' + row.jp);
    });
});
```

