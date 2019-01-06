---
title: "pthread のスレッドを終了する"
date: "2010-11-08"
---

pthread により生成されたスレッドは、以下のような場合に終了します。

- スレッドルーチンを最後まで実行終了した場合
- スレッドルーチンを `return` で終了した場合
- 自身のスレッド内から明示的に `pthread_exit()` を呼び出した場合
- 別のスレッドから `pthread_cancel()` で終了させられた場合
- メインスレッド（プロセス）が終了した場合

明示的なスレッド終了のためのメソッドは下記のように定義されています。

~~~
void pthread_exit(void *value);
int pthread_cancel(pthread_t thread);
~~~

