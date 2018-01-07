---
title: CodeIgniter - MySQL データベースの接続設定を行う
date: "2012-07-14"
---

CodeIgniter の MySQL データベースの接続設定は、`application/config/database.php` ファイルの中で下記のように行います。
★のついている部分が MySQL 用に設定しなければいけない部分です。

#### application/config/database.php

~~~ php
$db['default']['hostname'] = 'localhost';      //★
$db['default']['username'] = 'test_user';      //★
$db['default']['password'] = 'test_password';  //★
$db['default']['database'] = 'test_db';        //★
$db['default']['dbdriver'] = 'mysql';          //★
$db['default']['dbprefix'] = '';
$db['default']['pconnect'] = TRUE;
$db['default']['db_debug'] = TRUE;
$db['default']['cache_on'] = FALSE;
$db['default']['cachedir'] = '';
$db['default']['char_set'] = 'utf8';
$db['default']['dbcollat'] = 'utf8_general_ci';
$db['default']['swap_pre'] = '';
$db['default']['autoinit'] = TRUE;
$db['default']['stricton'] = FALSE;
~~~

上記の設定は、MySQL で以下のようなユーザ、データベースが作成済みであることを前提としています。

~~~
データベース名: test_db
        ユーザ: test_user
    パスワード: test_password
~~~

まだ作成していない場合は、例えば以下のように作成すればよいでしょう。

~~~
$ mysql -u root
mysql> GRANT ALL PRIVILEGES ON test_db.* TO test_user@localhost IDENTIFIED BY 'test_password';
mysql> CREATE DATABASE test_db;
~~~

参考までに、テスト用のテーブルは、以下のように感じで作成できます。

#### bookmarks.sql（テスト用テーブルの作成）

~~~ sql
CREATE TABLE bookmarks(
    id INT NOT NULL AUTO_INCREMENT,
    title TEXT,
    uri TEXT,
    PRIMARY KEY (id)
);

INSERT INTO bookmarks(title, uri) VALUES ('Title1', 'title1.com'),
                                         ('Title2', 'title2.com'),
                                         ('Title3', 'title3.com');
~~~

~~~
$ mysql -u test_user -p test_db < bookmarks.sql
Enter password: test_password
~~~

