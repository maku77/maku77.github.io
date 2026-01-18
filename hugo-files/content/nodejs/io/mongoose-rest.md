---
title: "Node.jsメモ: mongoose と Express で RESTful Web API を作成する"
url: "p/ttx5vvz/"
date: "2014-02-11"
tags: ["nodejs"]
aliases: /nodejs/io/mongoose-rest.html
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

{{< code lang="javascript" title="app.js" >}}
import express from 'express';
import memos from './routes/memos.js';

const app = express();

// Setup middlewares
app.use(express.json());  // Handle POST messages

// RESTful CRUD APIs
app.post('/memos', memos.createMemo);     // [C]REATE
app.get('/memos', memos.readAllMemos);    // [R]EAD
app.get('/memos/:id', memos.readMemo);    // [R]EAD
app.put('/memos/:id', memos.updateMemo);  // [U]PDATE
app.delete('/memos/:id', memos.deleteMemo);  // [D]ELETE

// Start a server
app.listen(5000, () => {
  console.log('Listening on port 5000');
});
{{< /code >}}

{{< code lang="javascript" title="routes/memos.js" >}}
/**
 * A route definition for /memos.
 */
import memodb from '../memodb.js';

// CREATE
export const createMemo = (req, res) => {
  const memo = req.body;
  memodb.Memo.create(memo, (err) => {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json({message: 'A new memo has been created'});
  });
};

// READ (all)
export const readAllMemos = (req, res) => {
  memodb.Memo.find((err, memos) => {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json(memos);
  });
};

// READ (one)
export const readMemo = (req, res) => {
  const id = req.params.id;
  memodb.Memo.findById(id, (err, memo) => {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json(memo);
  });
};

// UPDATE
export const updateMemo = (req, res) => {
  const id = req.params.id;
  const memo = req.body;
  memodb.Memo.findByIdAndUpdate(id, memo, (err) => {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json({message: 'The memo has been updated'});
  });
};

// DELETE
export const deleteMemo = (req, res) => {
  const id = req.params.id;
  memodb.Memo.findOneAndRemove({_id: id}, (err) => {
    if (err) {
      res.json({error: err.message});
      return;
    }
    res.json({message: 'The memo has been deleted'});
  });
};
{{< /code >}}

{{< code lang="javascript" title="memodb.js" >}}
import mongoose from 'mongoose';

const DB_URL = 'mongodb://localhost/memodb';

// Create models.
const MemoSchema = new mongoose.Schema({
  title: String,
  body: String,
  created: { type: Date, default: Date.now }
});

const Memo = mongoose.model('Memo', MemoSchema);

// Error handlers.
mongoose.connection.on('error', (err) => {
  console.log('ERROR: Could not connect to MongoDB');
  console.log(`ERROR: ${err.message}`);
  process.exit(1);
});

// Connect to the DB.
mongoose.connect(DB_URL);

// Create sample data if there is no data in DB.
Memo.count((err, count) => {
  if (count > 0) {
    return;
  }
  Memo.create({title: 'Title 1'}, () => {});
  Memo.create({title: 'Title 2'}, () => {});
  Memo.create({title: 'Title 3'}, () => {});
});

// Export interfaces.
export default { Memo };
{{< /code >}}

