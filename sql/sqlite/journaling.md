---
title: SQLite で瞬断などによるロールバック処理に備える (journaling)
date: "2012-01-16"
---

SQLite DB 書き込み中にデバイスの電源を落としたりすると、**hot journal** という状態になり、次回のデータベース読み込み時に SQLite3 のロールバック処理が走ります。

ロールバック処理は、journal ファイルを元にして DB ファイルの修復をしようとするので、データベースが WRITE モードでオープンされている必要があります。

- 参考: [Write-Ahead Logging - Read-Only Databases](http://www.sqlite.org/draft/wal.html#readonly)
- 参考: [File Locking And Concurrency In SQLite Version 3 - The Rollback Journal](http://www.sqlite.org/lockingv3.html#rollback)

ロールバック処理が走るときにデータベースが Read only でオープンされていると、`sqlite_prepare()` などを呼び出したときにエラー (`SQLITE_IOERR_LOCK`) で失敗します。
SQLite ライブラリ内部の実装を追っていくと、システムコールの `fcntl()` によるファイルの排他ロックをかけようとしてエラー (DBADF: Bad file descripter) になっています。
どうやら、ファイルを read only でオープンしているときに、排他ロックをかけようとすると `fcntl()` はエラー (`EBADF`) になるようです。

これに備えるためには、データベースをオープンするときに WRITE 権限をつけるしかなさそうです。

~~~
sqlite3_open_v2(DB_PATH, SQLITE_OPEN_READONLY, 0);
↓
sqlite3_open_v2(DB_PATH, SQLITE_OPEN_READWRITE, 0);
~~~

（内部でうまいことロールバック処理してくれればいいのに。。。）

SQLite 3.7.0 から導入される WAL (Write-Ahead Logging) を有効にすれば、このようなロックエラーに悩まされないようになるみたいです。

- 参考: [Write-Ahead Logging](http://www.sqlite.org/draft/wal.html)

