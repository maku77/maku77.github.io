---
title: mongoose で MongoDB (2) データベースへ接続する
created: 2014-02-10
---

下記は、`mongoose` を使用して MongoDB のデータベースに接続する例です。

#### sample.js

```javascript
var mongoose = require('mongoose');

// 接続後のコールバック
mongoose.connection.once('open', function (e) {
  console.log('connected');
});

// エラー発生時のコールバック
mongoose.connection.on('error', function (err) {
  console.log('ERROR:', err.message);
});

mongoose.connect('mongodb://localhost/testdb');
```

`mongoose.connect()` により MongoDB へ接続し、指定したデータベースにアクセスできるようになります。
データベースへ接続するときの接続パスは、以下のように指定します。

```
mongodb://user:pass@host:port/dbname
```

上記のサンプルコードでは、接続時とエラー発生時のイベントハンドラを設定しています。
`mongod` などが起動していない場合などのはエラーが発生し、以下のように表示されます。

```
$ node sample.js
ERROR: failed to connect to [localhost:27017]
```

