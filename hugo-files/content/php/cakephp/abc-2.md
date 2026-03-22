---
title: "PHPメモ: CakePHP 入門 (2) データベースの設定"
url: "/p/kacjhwz/"
date: "2012-05-03"
tags: ["php"]
aliases: [/php/cakephp/abc-2.html]
---

ここでは SQLite3 を使って、タスクを管理するアプリケーションを作ってみます。

SQLite3 データベース (task.db) の作成
----

まず、ドキュメントルート（`index.php` があるディレクトリ）に以下のように SQLite3 のデータベースファイルを作成します。

```console
$ cd public_html
$ sqlite3 task.db

sqlite> CREATE TABLE tasks (
   ...>   id INTEGER PRIMARY KEY AUTOINCREMENT,
   ...>   title TEXT NOT NULL,
   ...>   body TEXT NOT NULL,
   ...>   created DATETIME DEFAULT NULL,
   ...>   modified DATETIME DEFAULT NULL
   ...> );

sqlite> INSERT INTO tasks(title, body, created)
   ...>   VALUES('Title1', 'Body1', datetime());

sqlite> INSERT INTO tasks(title, body, created)
   ...>   VALUES('Title2', 'Body2', datetime());
```

作成した `task.db` は以下のディレクトリに置きます。

```
＜DocumentRoot＞/app/webroot
```


/app/Config/database.php の作成
----

上記で作成した SQLite3 データベースファイルの `task.db` を使用するために、`/app/Config/database.php.default` を参考にして、`database.php` を作成します。

```php
<?php
class DATABASE_CONFIG {
    public $default = array(
        'datasource' => 'Database/Sqlite',
        'database' => 'task.db',
    );
}
```

CakePHP 2.X では、`datasource` を上記のように指定します。
CakePHP 1.X の頃と指定方法が異なるので注意してください。


接続確認
----

`http://localhost/` をブラウザで開いて、

```
Cake is able to connect to the database.
```

のように表示されれば成功です。

逆に、SQLite3 のデータベースファイルが存在しないような場合は、以下のような警告が表示されます。

```
Cake is NOT able to connect to the database.
```


CakePHP 入門記事一覧
----

- [CakePHP 入門 (1) セットアップ](/p/sq3v2ss/)
- CakePHP 入門 (2) データベースの設定
- [CakePHP 入門 (3) CakePHP アプリの URL の仕組み](/p/ijxrod2/)
- [CakePHP 入門 (4) Controller、View、Model を作成する](/p/38ty6s5/)
- [CakePHP 入門 (5) 個別のレコードを表示する](/p/mnwyenz/)
- [CakePHP 入門 (6) ヘルパーを使用してリンクを生成する](/p/bueomu6/)
- [CakePHP 入門 (7) レコードを追加できるようにする](/p/apv3m29/)
- [CakePHP 入門 (8) レコードを編集できるようにする](/p/5qnrd2w/)
- [CakePHP 入門 (9) レコードを削除できるようにする](/p/gyeajmq/)

