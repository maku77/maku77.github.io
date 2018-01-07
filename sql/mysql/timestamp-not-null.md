---
title: MySQL の TIMESTAMP NOT NULL な時刻データの振る舞い
date: "2012-07-18"
---

`TIMESTAMP` 型のカラムを `NOT NULL` 修飾子を付けて定義すると、そこには必ず何らかの時刻データが格納されることが保証されます。
例えば、下記のようにテーブルを定義したとします。

~~~
mysql> CREATE TABLE date_tbl(created_date TIMESTAMP NOT NULL);
~~~

このような `NOT NULL` な `TIMESTAMP` フィールドに対して、曖昧な日時指定でレコードを追加しようとするとどのような値として格納されるのでしょうか？
下記はそのまとめです。


### 日付だけ指定すると深夜 0 時になる

~~~
mysql> INSERT INTO date_tbl(created_date) VALUES('2012-01-01');
mysql> SELECT * FROM date_tbl;
+---------------------+
| created_date        |
+---------------------+
| 2012-01-01 00:00:00 |
+---------------------+
~~~

### 時刻だけ指定すると 0000-00-00 00:00:00 になる

~~~
mysql> INSERT INTO date_tbl(created_date) VALUES('15:30:00');
mysql> SELECT * FROM date_tbl;
+---------------------+
| created_date        |
+---------------------+
| 0000-00-00 00:00:00 |
+---------------------+
~~~

### NULL を入れようとすると現在時刻が入る

~~~
mysql> INSERT INTO date_tbl(created_date) VALUES(NULL);
mysql> SELECT * FROM date_tbl;
+---------------------+
| created_date        |
+---------------------+
| 2012-07-18 00:27:16 |
+---------------------+
~~~

### 不正なフォーマットで入力すると 0000-00-00 00:00:00 になる

~~~
mysql> INSERT INTO date_tbl(created_date) VALUES('hogehoge');
mysql> SELECT * FROM date_tbl;
+---------------------+
| created_date        |
+---------------------+
| 0000-00-00 00:00:00 |
+---------------------+
~~~

