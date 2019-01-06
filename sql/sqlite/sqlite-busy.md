---
title: "SQLite のロックによる SQLITE_BUSY に備える"
date: "2012-01-15"
---

SQLite のデータベースに対して、複数のプロセス、スレッドから同時にアクセスする可能性があるとします。

他のプロセスがトランザクション処理中で排他ロック (EXCLUSIVE LOCK) がかかっていると、SELECT や UPDATE の実行に失敗します。
具体的には **SQLITE_BUSY** というコードが返って来ます。

このコードが返ってきた場合は、適当な時間だけ wait してリトライするようにします。
あるいは、以下のようなユーティリティ関数を使うこともできます。

- `sqlite3_busy_timeout()` -- 対象となる API 呼び出しが BUSY だった場合に、指定したミリ秒だけ内部的に粘るようにする。それでもダメなら `SQLITE_BUSY` を返すようにする。 → [API ドキュメント](http://www.sqlite.org/c3ref/busy_timeout.html)
- `sqlite3_busy_handler()` -- 対象となる API 呼び出しが BUSY だった場合に、指定したコールバックハンドラを呼び出すようにする。 → [API ドキュメント](http://www.sqlite.org/c3ref/busy_handler.html)

簡単なのは、`sqlite3_busy_timeout()` でタイムアウト時間を３秒くらいに設定しまう方法でしょうか。
これで、複数プロセスからの書き込みがあるデータベースに対しても、`SQLITE_BUSY` で問題がでることはほとんどなくなるはずです。

#### 設定例

~~~ cpp
// Set the default busy handler to retry for 1000ms so that we can omit retry code.
sqlite_busy_timeout(db, 3000 /* ms */);
~~~

ちなみに、１つのデータベースに対して複数プロセスからの同時アクセスがある場合でも、読み込みアクセスだけであれば、排他ロックではなく共有ロック（SHARED LOCK）がかけられるため、BUSY 状態にはなりません。

