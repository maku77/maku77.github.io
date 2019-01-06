---
title: "pthread で mutex による排他制御を行う"
date: "2010-11-08"
---

pthread によるスレッドにおいて、mutex オブジェクトを使用してスレッド間の排他制御を行うには、主に以下のようなメソッドを使用します。

~~~ cpp
int pthread_mutex_init(pthread_mutex_t *mutex, const pthread_mutexattr_t *attr);
int pthread_mutex_lock(pthread_mutex_t *mutex);
int pthread_mutex_unlock(pthread_mutex_t *mutex);
~~~

まずは、`pthread_mutex_init()` を使用して `pthread_mutex_t` オブジェクトを初期化します。

~~~ cpp
static pthraad_mutex_t myMutex;

int main() {
    ...
    pthread_mutex_init(&myMutex, NULL);
    ...
}
~~~

そして、排他制御したい処理を、`pthread_mutex_lock()` と `pthread_mutex_unlock()` で囲みます。

~~~ cpp
void* doSomething(void* arg) {
    ...

    pthread_mutex_lock(&myMutex);
    // この間でグローバル変数や共有リソースへ排他アクセス
    pthread_mutex_unlock(&myMutex);

    ...
}
~~~

