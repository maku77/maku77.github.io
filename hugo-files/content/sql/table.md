---
title: "SQLメモ: SQL によるテーブル操作の基本（作成、削除、変更、構成の確認）"
url: "p/x3ym9a4/"
date: "2012-08-04"
tags: ["sql"]
aliases:
  - /sql/create-table.html
  - /sql/drop-table.html
  - /sql/rename-table.html
  - /sql/describe-table.html
  - /sql/show-create-table.html
---

テーブルを作成する (`CREATE TABLE`)
----

SQL の **`CREATE TABLE`** コマンドを使用して、データベース内に新しいテーブルを作成することができます。

{{< code lang="sql" title="MySQL で bookmarks テーブルを作成する例" >}}
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
{{< /code >}}

`CREATE TABLE` コマンドを実行するときに、追加で **`IF NOT EXISTS`** を指定すると、テーブルが存在しない場合だけ `CREATE TABLE` コマンドを実行してくれるようになります。

{{< code lang="sql" title="bookmarks テーブルが存在しない場合だけ作成する" >}}
CREATE TABLE IF NOT EXISTS bookmarks(
    id INT NOT NULL AUTO_INCREMENT,
    title TEXT,
    uri TEXT,
    PRIMARY KEY (id)
);
{{< /code >}}


テーブルを削除する (`DROP TABLE`)
----

**`DROP TABLE`** コマンドを使用して、データベース内のテーブルを削除することができます。

{{< code lang="sql" title="my_table テーブルを削除する" >}}
DROP TABLE my_table;
{{< /code >}}

このコマンドはテーブルとテーブル内のデータをすべて削除するため、注意して実行してください。


テーブル名を変更する (`ALTER TABLE` ～ `RENAME TO`)
----

データベースのテーブル名は、**`ALTER TABLE`** コマンドを使用して変更することができます。

### 

{{< code lang="sql" title="テーブル tbl_old を tbl_new という名前に変更（SQLite、MySQL の例）" >}}
ALTER TABLE tbl_old RENAME TO tbl_new;
{{< /code >}}


テーブルの構成を確認する (`DESCRIBE`)
----

既存のテーブルの構成を確認するには、**`DESCRIBE`** コマンドを使用します。

{{< code lang="mysql" title="bookmarks テーブルの構成を確認する" >}}
mysql> DESCRIBE bookmarks;
+-------+---------+------+-----+---------+----------------+
| Field | Type    | Null | Key | Default | Extra          |
+-------+---------+------+-----+---------+----------------+
| id    | int(11) | NO   | PRI | NULL    | auto_increment |
| title | text    | YES  |     | NULL    |                |
| uri   | text    | YES  |     | NULL    |                |
+-------+---------+------+-----+---------+----------------+
3 rows in set (0.01 sec)
{{< /code >}}

上記のテーブルは以下のように作成したものです。

```sql
CREATE TABLE bookmarks(
    id INT NOT NULL AUTO_INCREMENT,
    title TEXT,
    uri TEXT,
    PRIMARY KEY (id)
);
```

テーブルを作成したときのコマンドを表示する (`SHOW CREATE TABLE`)
----

**`SHOW CREATE TABLE テーブル名`** と実行すると、指定したテーブルの作成に使われた `CREATE TABLE` コマンドを表示することができます。
厳密にはテーブル作成時に使ったコマンドとは同一にはなりませんが、出力されたコマンドを実行すれば、同等のテーブルを作成することができます。

```mysql
mysql> SHOW CREATE TABLE bookmarks;
CREATE TABLE `bookmarks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text,
  `uri` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf-8
```

注）MySQL の場合はテーブル形式で表示されるので、実際の出力は上記とは少々異なります。

