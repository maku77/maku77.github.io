---
title: "Node.js で SQLite を使用する (2) node-sqlite3 による逐次処理"
date: "2014-01-25"
---

`node-sqlite3` による各種 DB 処理は、非同期に実行されるようになっています。
例えば、以下のように SQL ステートメントを実行すると、`CREATE TABLE` と `INSERT` 処理が非同期に実行されるため、エラーが発生します。

#### 間違った例

```javascript
var sqlite3 = require('sqlite3');
var db = new sqlite3.Database(':memory:');

db.run('CREATE TABLE members (name, age)');
db.run('INSERT INTO members VALUES (?)', 'maku', 10);
```

#### 実行結果

```
Error: SQLITE_ERROR: no such table: members
```

`node-sqlite3` では、この問題に対応するため、`Database#serialize()` を用意しています。
`Database#serialize()` に渡した関数の中で `Database#run()` を実行すると、SQL ステートメントをキューイングした順番に同期実行してくれます。

#### 正しい例

```javascript
db.serialize(function() {
    db.run('CREATE TABLE members (name, age)');
    db.run('INSERT INTO members VALUES (?,?)', 'maku', 10);
});
```

