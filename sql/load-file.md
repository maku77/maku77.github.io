---
title: SQL ファイルを読み込んで実行する
date: "2012-07-07"
---

SQL 文を記述したテキストファイルを読み込んで実行するには以下のようにします。

SQLite の場合
----

~~~
$ sqlite3 sample.db < input.sql
~~~

あるいは、sqlite コマンドの `.read` を使っても同じことができます。

~~~
$ sqlite3 sample.db
sqlite> .read input.sql
~~~

#### input.sql

~~~ sql
CREATE TABLE books(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT
);
INSERT INTO books(title) VALUES('Title1');
INSERT INTO books(title) VALUES('Title2');
INSERT INTO books(title) VALUES('Title3');
~~~

以下のように、実際にデータベースファイルが生成されていることを確認できます。

~~~
$ sqlite3 sample.db
sqlite> SELECT * FROM books;
1|Title1
2|Title2
3|Title3
~~~

