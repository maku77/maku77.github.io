---
title: "pthread でスレッドを生成する"
date: "2010-11-07"
---

pthread で新しいスレッドを生成するには、`pthread_create` を使用します。

~~~ cpp
int pthread_create(
        pthread_t *thread,
        const pthread_attr_t *attr,
        void *(*start_routine)(void *),
        void *arg)
~~~

各パラメータは下記のような意味を持っています。

- `thread` -- 作成したスレッドのハンドルを格納するバッファを指定する。
- `attr` -- スレッド属性を指定する。デフォルト属性でよい場合は NULL を指定する。
- `start_routine` -- 新しいスレッドのエントリポイント。
- `arg` -- 新しいスレッドに渡す引数。必要ない場合は NULL を渡せばよい。

`pthread_create` は、呼び出しに成功すると 0 を返します。

#### サンプルプログラム

~~~ cpp
#include <stdio.h>
#include <pthread.h>

// 新しいスレッドで実行されるタスク
void *doSomething(void* pArg) {
    int *pVal = (int*) pArg;
    printf("worker thread [%d]\n", *pVal);
    *pVal = 200;
}

int main() {
    pthread_t handle;  // Thread handle.
    int data = 100;

    pthread_create(&handle, NULL, doSomething, &data);
    pthread_join(handle, NULL);

    printf("main thread [%d]\n", data);
}
~~~

上記の例では `pthread_create` で新しいスレッドを生成し、その後、メインスレッド上で `pthread_join` を呼ぶことによって、生成したスレッドが終了するまで待機しています。
この処理を入れておかないと、`main` ルーチンを抜けてすぐにプロセス自体が終了してしまうので、作成したスレッドも終了してしまいます。

