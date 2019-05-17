---
title: "mongoose と Express で RESTful Web API を作成する"
date: "2014-02-11"
---

以下のような HTTP リクエストをハンドルする RESTful Web API を、Express（Web サーバー）と mongoose（MongoDB）を使って作成するサンプルです。

- `GET http://localhost/memos` ... 全てのメモを取得
- `GET http://localhost/memos/<id>` ... 指定した ID のメモを取得
- `POST http://localhost/memos` ... メモを作成
- `PUT http://localhost/memos/<id>` ... メモの更新
- `DELETE http://localhost/memos/<id>` ... メモを削除

下記のように Web サーバを起動することで、REST API の呼び出しに応答するようになります。

```
$ node app
Listening on port 5000
```

#### app.js

```js
var memos = require('./routes/memos');
var express = require('express');
var app = express();

// Setup middlewares
app.use(express.bodyParser());  // Handle POST messages

// RESTful CRUD APIs
app.post('/memos', memos.createMemo);     // [C]REATE
app.get('/memos', memos.readAllMemos);    // [R]EAD
app.get('/memos/:id', memos.readMemo);    // [R]EAD
app.put('/memos/:id', memos.updateMemo);  // [U]PDATE
app.del('/memos/:id', memos.deleteMemo);  // [D]ELETE

// Start a server
app.listen(5000, function() {
  console.log('Listening on port 5000');
});
```

#### routes/memos.js

```js
/**
 * A route definition for /memos.
 */
var memodb = require('../memodb');

// CREATE
exports.createMemo = function (req, res) {
  var memo = req.body;
  memodb.Memo.create(memo, function (err) {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json({message: 'A new memo has been created'});
  });
};

// READ (all)
exports.readAllMemos = function (req, res) {
  memodb.Memo.find(function (err, memos) {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json(memos);
  });
};

// READ (one)
exports.readMemo = function (req, res) {
  var id = req.params.id;
  memodb.Memo.findById(id, function (err, memo) {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json(memo);
  });
};

// UPDATE
exports.updateMemo = function (req, res) {
  var id = req.params.id;
  var memo = req.body;
  memodb.Memo.findByIdAndUpdate(id, memo, function (err) {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json({message: 'The memo has been updated'});
  });
};

// DELETE
exports.deleteMemo = function (req, res) {
  var id = req.params.id;
  memodb.Memo.findOneAndRemove({_id: id}, function (err) {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json({message: 'The memo has been deleted'});
  });
};
```

#### memodb.js

```js
var mongoose = require('mongoose');
var DB_URL = 'mongodb://localhost/memodb';

// Create models.
var MemoSchema = new mongoose.Schema({
  title: String,
  body: String,
  created: { type: Date, default: Date.now }
});

var Memo = mongoose.model('Memo', MemoSchema);

// Error handlers.
mongoose.connection.on('error', function (err) {
  console.log('ERROR: Could not connect to MongoDB');
  console.log('ERROR:', err.message);
  process.exit(1);
});

// Connect to the DB.
mongoose.connect(DB_URL);

// Create sample data if there is no data in DB.
Memo.count(function (err, count) {
  if (count > 0) {
    return;
  }
  Memo.create({title: 'Title 1'}, function() {});
  Memo.create({title: 'Title 2'}, function() {});
  Memo.create({title: 'Title 3'}, function() {});
});

// Export interfaces.
exports.Memo = Memo;
```

