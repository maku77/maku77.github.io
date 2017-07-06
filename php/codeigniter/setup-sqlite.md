---
title: CodeIgniter- SQLite3 データベースの接続設定を行う
created: 2012-07-07
---

（以下の手順で SQLite3 を使用するには、CodeIgniter 2.1.1 以上が必要です）

CodeIgniter で SQLite3 データベースへの接続設定を行うには、`application/config/database.php` ファイルの中で以下のように設定します。
★のついている部分が SQLite 用に設定しなければいけない部分です。
データベースファイルは、`application/sample.db` というパスに作成されます。

`hostname` にデータベースのパスを、`dbdriver` には `pdo` を指定するのがポイントです。

#### application/config/database.php

~~~ php
// ...
$active_group = 'default';

$db['default']['hostname'] = 'sqlite:' . APPPATH . 'sample.db';  // ★
$db['default']['username'] = '';
$db['default']['password'] = '';
$db['default']['database'] = '';
$db['default']['dbdriver'] = 'pdo';  // ★
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

`$active_group` に指定した接続グループ名の設定は、コントローラの中で

~~~ php
$this->load->database();
~~~

とパラメータなしでデータベースに接続した時に使用されます。


参考サイト
----

- [Using CodeIgniter 2 with SQLite 3 databases](http://khromov.wordpress.com/2012/06/22/using-codeigniter-2-1-with-sqlite-3-databases/)

