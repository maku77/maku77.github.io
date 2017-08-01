---
title: (FAQ) SQLite で書き込みエラーが出た場合の対処
created: 2015-05-06
---

SQLite でデータベースファイルに書き込みを行おうとしたときに、下記のようなエラーが発生することがあります。

~~~
Error: attempt to write a readonly database （DB ファイルに書き込み権限がない？）
Error: unable to open database file （DB ファイルのあるディレクトリに書き込み権限がない？）
~~~

このような場合は、データベースファイル自体のパーミッションや、データベースファイルのあるディレクトリのパーミッションを確認します。
もし、下記のように実行してアクセスできれば、パーミッションが問題である可能性が高いです。

~~~
$ chmod 777 /your/db/dir
$ chmod 777 /your/db/dir/sample.db
~~~

実際には、パーミッションごと緩めてしまうのはセキュリティ上望ましくないので、`chgrp` を使ってデータベースにアクセスできるグループを適切に設定してやります。
例えば、Web アプリケーションから SQLite データベースにアクセスしているのであれば、Apache などの HTTP デーモンがどのグループ ID で実行されているのかを調べます（通常は `httpd.conf` ファイルで設定されています）。

#### httpd.conf

~~~
<IfModule unixd_module>
User daemon
Group daemon
</IfModule>
~~~

上記の例では、実効グループ ID が `daemon` となるので、データベースファイルの所有ユーザを `daemon` に設定してやります。

~~~
$ chmod 775 /your/db/dir
$ chmod 775 /your/db/dir/sample.db
$ sudo chgrp /your/db/dir
$ sudo chgrp /your/db/dir/sample.db
~~~

