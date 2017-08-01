---
title: MySQL でデータベースのユーザを作成／削除する (CREATE USER, DROP USER)
created: 2005-04-03
---

MySQL のユーザを作成する (CREATE USER)
----

MySQL をインストールした直後は、パスワードなしの `root` ユーザしか登録されていません。
通常は、アプリケーション用に作成されたデータベースには、そのデータベース専用のユーザを使ってアクセスすることになります。
あるデータベースを扱うためのユーザを作成するには、`CREATE USER` コマンドでユーザを作成し、`GRANT` コマンドでそのデータベースへのアクセス権限を与えます。

~~~
$ mysql -u root -p
mysql> CREATE USER 'myname'@'localhost' IDENTIFIED BY 'mypass';
mysql> GRANT ALL ON mydb.* TO 'myname'@'localhost';
~~~

上記のようにすると、次のようなユーザが作成されます。

* ユーザ名: `myname`（ホスト `localhost` からのアクセス）
* パスワード: `mypass`
* 接続可能なデータベース: `mydb`
* 操作可能なテーブル: すべて (`*`)
* 権限: すべて (`ALL`)

上記の例では、`CREATE USER` と `GRANT` を別々に実行していますが、次のようにしてユーザ作成と参照権限の付加を同時に行ってしまうこともできます。

~~~
mysql> GRANT ALL ON mydb.* TO 'myname'@'localhost' IDENTIFIED BY 'mypass';
~~~


MySQL のユーザを削除する (DROP USER)
----

MySQL のユーザを削除するには、`DROP USER` コマンドを使用します。

~~~
$ mysql -u root -p
mysql> DROP USER myname;
~~~

