---
title: テーブルを作成する (CREATE TABLE)
date: "2012-08-04"
---

`CREATE TABLE` コマンドを使用して、データベース内に新しいテーブルを作成することができます。

MySQL でテーブルを作成する例
----

~~~ sql
CREATE TABLE bookmarks(
    id INT NOT NULL AUTO_INCREMENT,
    title TEXT,
    uri TEXT,
    PRIMARY KEY (id)
);

INSERT INTO bookmarks(title, uri)
    VALUES ('Google', 'http://google.com/'),
           ('Yahoo', 'http://yahoo.com/'),
           ('CodeIgniter', 'http://codeigniter.com/');
~~~


テーブルがまだ存在しないときのみ CREATE TABLE を実行する方法
----

`CREATE TABLE` コマンドを実行するときに、追加で `IF NOT EXISTS` を指定すると、テーブルが存在しない場合だけ `CREATE TABLE` コマンドを実行してくれるようになります。

~~~ sql
CREATE TABLE IF NOT EXISTS bookmarks(
    id INT NOT NULL AUTO_INCREMENT,
    title TEXT,
    uri TEXT,
    PRIMARY KEY (id)
);
~~~

