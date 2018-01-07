---
title: pthread のスレッドハンドルを比較する
date: "2010-11-08"
---

pthread のスレッドのハンドル (`pthread_t`) を比較するには、`pthread_equal()` を使用します。

~~~ cpp
int pthread_equal(pthread_t t1, pthread_t t2);
~~~

`pthread_t` は構造体として実装される可能性もあるので、`=` 演算子ではなく、必ず `pthread_equal()` を使って比較する必要があります。

