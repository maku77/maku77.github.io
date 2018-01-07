---
title: "Node.js で SQLite を使用する (3) SELECT 文を実行する"
date: "2014-01-24"
---

`SELECT` ステートメントを実行して結果を取得するには、`sqlite3.Database` オブジェクトの以下のメソッドを使用します。

* `Database#get()` -- 1つだけ結果を取得
* `Database#all()` -- 全ての結果を一度に取得（コールバックは 1 回呼び出される）
* `Database#each()` -- 全ての結果を順番に取得（コールバックが複数回呼び出される）

いずれもコールバック関数で結果を受け取りますが、メソッドごとにコールバックで渡されるパラメータが異なります。

Database#get() で 1 つだけ結果を取得する
----

```javascript
db.get('SELECT name, age FROM members', function(err, row) {
    if (err) {
        throw err;
    }
    console.log(row.name + ' ' + row.age);
});
```

Database#all() で全ての結果を一度に取得する
----

```javascript
db.all('SELECT name, age FROM members', function(err, rows) {
    if (err) {
        throw err;
    }
    rows.forEach(function (row) {
        console.log(row.name + ' ' + row.age);
    });
});
```

Database#each() で全ての結果を順番に取得する
----

```javascript
db.each('SELECT name, age FROM members', function(err, row) {
    if (err) {
        throw err;
    }
    console.log(row.name + ' ' + row.age);
});
```

