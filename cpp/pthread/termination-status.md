---
title: "pthread のスレッドの終了ステータスを取得する"
date: "2010-11-07"
---

`pthread_join` で、あるスレッドの終了を待機する際に、第２引数でスレッドルーチンからの戻り値を受け取ることができます。

~~~ cpp
int pthread_join(pthread_t thread, void **value_ptr)
~~~

スレッドルーチンでは、あるデータのアドレスを `return` することで戻り値を返します。
`thread_exit()` を使用してスレッドを終了した場合も、その引数で戻り値を指定することができます。

~~~ cpp
void pthread_exit(void *value)
~~~

#### サンプルコード

~~~ cpp
#include <stdio.h>
#include <pthread.h>

static int RETURN_CODE = 100;

void *doSomething(void *arg) {
    return &RETURN_CODE;
    // pthread_exit(&RETURN_CODE);
}

int main() {
    // スレッドの作成
    pthread_t handle;
    pthread_create(&handle, NULL, doSomething, NULL);

    // スレッドの終了ステータスを取得
    void *status;
    pthread_join(handle, &status);

    if (status == PTHREAD_CANCELED) {
        printf("Thread was canceled.\n");
    } else {
        int val = *((int*) status);
        printf("Return value = %d\n", val);
    }
}
~~~

`pthread_cancel()` などで、スレッドの実行を途中でキャンセルさせられた場合は、終了ステータスとして `PTHREAD_CANCELED` という値を返すので、上記のように最初に終了ステータスが `PTHREAD_CANCELED` かどうかを調べる必要があります。

`PTHREAD_CANCELED` の値は、有効なデータアドレスや、`NULL` と被らない値になるよう実装されているので、終了ステータスが、独自データのアドレスと被ってしまう心配はありません。

