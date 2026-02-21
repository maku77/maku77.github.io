---
title: "SQLメモ: SQLite でワンライナー実行する"
url: "p/v48e2fn/"
date: "2011-08-17"
tags: ["sql"]
aliases: /sql/sqlite/oneliner.html
---

`sqlite3` コマンドを実行するときに、データベースファイル名に続けて実行したい SQLite コマンドを指定することができます。

{{< code lang="console" title="例: データベース内のテーブル一覧を表示する" >}}
$ sqlite3 settings.db '.tables'
android_metadata   bookmarks    system
bluetooth_devices  secure
{{< /code >}}

SQL 文もワンライナーで実行できます。

{{< code lang="console" title="例: データベース内の secure テーブルの情報を表示する" >}}
$ sqlite3 settings.db 'SELECT * FROM secure'
...
{{< /code >}}
