---
title: "Node.jsメモ: node-sqlite3 で SQLite データベースを扱う"
url: "p/oxxpf3h/"
date: "2014-01-24"
tags: ["nodejs"]
aliases:
  - /nodejs/io/sqlite1.html
  - /nodejs/io/sqlite2.html
  - /nodejs/io/sqlite3.html
---

node-sqlite3 のインストール
----

Node.js で SQLite を使用するためのライブラリとして [`node-sqlite3`](https://github.com/mapbox/node-sqlite3) があります。
以下のようにインストールします。

```
$ npm install sqlite3
```


node-sqlite3 の使用例
----

下記は、`dic.sqlite3` という DB ファイルから、`words` テーブルのレコードを取得するサンプルです。

```js
import sqlite3 from 'sqlite3';

const db = new sqlite3.Database('dic.sqlite3');

db.all('SELECT en, jp FROM words', (err, rows) => {
  if (err) {
    console.log(err);
    return;
  }
  rows.forEach((row) => {
    console.log(`${row.en} ${row.jp}`);
  });
});
```


node-sqlite3 による逐次処理"
----

`node-sqlite3` による各種 DB 処理は、非同期に実行されるようになっています。
例えば、以下のように SQL ステートメントを実行すると、`CREATE TABLE` と `INSERT` 処理が非同期に実行されるため、エラーが発生します。

{{< code lang="js" title="間違った例" >}}
import sqlite3 from 'sqlite3';

const db = new sqlite3.Database(':memory:');

db.run('CREATE TABLE members (name, age)');
db.run('INSERT INTO members VALUES (?)', 'maku', 10);
{{< /code >}}

{{< code title="実行結果" >}}
Error: SQLITE_ERROR: no such table: members
{{< /code >}}

`node-sqlite3` では、この問題に対応するため、`Database#serialize()` を用意しています。
`Database#serialize()` に渡した関数の中で `Database#run()` を実行すると、SQL ステートメントをキューイングした順番に同期実行してくれます。

{{< code lang="js" title="正しい例" >}}
db.serialize(() => {
  db.run('CREATE TABLE members (name, age)');
  db.run('INSERT INTO members VALUES (?,?)', 'maku', 10);
});
{{< /code >}}


SELECT クエリを実行する
----

`SELECT` ステートメントを実行して結果を取得するには、`sqlite3.Database` オブジェクトの以下のメソッドを使用します。

* **`Database#get()`** ... 1 つだけ結果を取得
* **`Database#all()`** ... 全ての結果を一度に取得（コールバックは 1 回呼び出される）
* **`Database#each()`** ... 全ての結果を順番に取得（コールバックが複数回呼び出される）

いずれもコールバック関数で結果を受け取りますが、メソッドごとにコールバックで渡されるパラメータが異なります。

{{< code lang="js" title="Database#get() で 1 つだけ結果を取得する" >}}
db.get('SELECT name, age FROM members', (err, row) => {
  if (err) {
    throw err;
  }
  console.log(`${row.name} ${row.age}`);
});
{{< /code >}}

{{< code lang="js" title="Database#all() で全ての結果を一度に取得する" >}}
db.all('SELECT name, age FROM members', (err, rows) => {
  if (err) {
    throw err;
  }
  rows.forEach((row) => {
    console.log(`${row.name} ${row.age}`);
  });
});
{{< /code >}}

{{< code lang="js" title="Database#each() で全ての結果を順番に取得する" >}}
db.each('SELECT name, age FROM members', (err, row) => {
  if (err) {
    throw err;
  }
  console.log(`${row.name} ${row.age}`);
});
{{< /code >}}

