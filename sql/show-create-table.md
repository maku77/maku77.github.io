---
title: "テーブルを作成したときのコマンドを表示する (SHOW CREATE TABLE)"
date: "2012-08-17"
---

`SHOW CREATE TABLE テーブル名` と実行すると、指定したテーブルの作成に使われた `CREATE TABLE` コマンドを表示することができます。
厳密にはテーブル作成時に使ったコマンドとは同一にはなりませんが、出力されたコマンドを実行すれば、同等のテーブルを作成することができます。

~~~
mysql> SHOW CREATE TABLE bookmarks;
CREATE TABLE `bookmarks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text,
  `uri` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf-8
~~~

注）MySQL の場合、テーブル形式で表示されるので、実際の出力は上記とは少々異なります。

- 参考: [テーブルの構成を確認する (DESCRIBE)](describe-table.html)

