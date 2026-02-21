---
title: "SQLメモ: (FAQ) SQLite で書き込みエラーが出た場合の対処"
url: "p/49nwche/"
date: "2015-05-06"
tags: ["sql"]
aliases: /sql/sqlite/write-error.html
---

SQLite でデータベースファイルへの書き込みを行おうとしたときに、以下のようなエラーが発生することがあります。

```text
Error: attempt to write a readonly database （DB ファイルに書き込み権限がない？）
Error: unable to open database file （DB ファイルのあるディレクトリに書き込み権限がない？）
```

このような場合は、データベースファイル自体のパーミッションや、データベースファイルのあるディレクトリのパーミッションを確認します。
もし、以下のように実行してアクセスできれば、パーミッションが問題である可能性が高いです。

```console
$ chmod 777 /your/db/dir
$ chmod 777 /your/db/dir/sample.db
```

実際には、パーミッションを全開にしてしまうのはセキュリティ上望ましくないので、`chgrp` を使ってデータベースにアクセスできるグループを適切に設定します。
例えば、Web アプリケーションから SQLite データベースにアクセスしているのであれば、Apache などの HTTP デーモンがどのグループ ID で実行されているのかを調べます（通常は `httpd.conf` ファイルで設定されています）。

{{< code lang="xml" title="httpd.conf" >}}
<IfModule unixd_module>
User daemon
Group daemon
</IfModule>
{{< /code >}}

上記の例では実効グループ ID が `daemon` となるので、データベースファイルのグループを `daemon` に設定します。

```console
$ chmod 775 /your/db/dir
$ chmod 775 /your/db/dir/sample.db
$ sudo chgrp daemon /your/db/dir
$ sudo chgrp daemon /your/db/dir/sample.db
```
