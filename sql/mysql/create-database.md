---
title: MySQL で新しいデータベースを作成する (CREATE DATABASE)
created: 2012-07-14
---

ユーザ testuser から操作可能なデータベース testdb を作成する
----

まずは、ユーザ `testuser` に、データベース `testdb` を操作できるように権限を与えます。
この権限には、`testdb` というデータベース自体を作成する権限も含まれています。

~~~
$ mysql -u root
mysql> GRANT ALL PRIVILEGES ON testdb.* TO testuser@localhost IDENTIFIED BY 'testpass';
~~~

次に、`testuser` で MySQL に接続し直して、データベース `testdb` を実際に作成します。

~~~
$ mysql -u testuser -p
Enter password: testpass
mysql> CREATE DATABASE testdb;
~~~

これで、次回からは `mysql` コマンドで `testdb` データベースに直接接続できるようになります。

~~~
$ mysql -u testuser -p testdb
Enter password: ********
~~~

上記の例では、`Enter password:` プロンプトからパスワードを入力していますが、`mysql` コマンドのパラメータで直接パスワードを指定してしまうには、以下のように `-p` オプションの直後に空白を入れずに指定します。

~~~
$ mysql -u testuser -ptestpass testdb
~~~

以下のように `alias` 設定を行っておくと、簡単にデータベースに接続できるようになって楽かもしれません。

~~~
$ alias testdb='mysql -u testuser -ptestpass testdb'
~~~

