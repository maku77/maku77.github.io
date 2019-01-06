---
title: "テーブルの構成を確認する (DESCRIBE)"
date: "2012-08-17"
---

任意のテーブルの構成を確認するには、`DESCRIBE` コマンドを使用します。

#### 例: bookmarks テーブルの構成を確認する

~~~ sql
mysql> DESCRIBE bookmarks;
+-------+---------+------+-----+---------+----------------+
| Field | Type    | Null | Key | Default | Extra          |
+-------+---------+------+-----+---------+----------------+
| id    | int(11) | NO   | PRI | NULL    | auto_increment |
| title | text    | YES  |     | NULL    |                |
| uri   | text    | YES  |     | NULL    |                |
+-------+---------+------+-----+---------+----------------+
3 rows in set (0.01 sec)
~~~

上記のテーブルは以下のように作成したものです。

~~~ sql
CREATE TABLE bookmarks(
    id INT NOT NULL AUTO_INCREMENT,
    title TEXT,
    uri TEXT,
    PRIMARY KEY (id)
);
~~~

- 参考: [テーブルを作成したときのコマンドを表示する (SHOW CREATE TABLE)](show-create-table.html)

