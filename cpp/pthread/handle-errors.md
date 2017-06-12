---
title: pthread 関数のエラー処理を行う
created: 2010-11-08
---

ほとんどの `pthread` 関数は、呼び出しに成功すると 0 を返します。
0 以外の値が返された場合には、適切なエラー処理を行う必要があります。

使用しているシステムが、`strerror()` のようなエラーコードを文字列表現に変換する関数をサポートしていれば、下記のようにエラーメッセージを表示することができます。

~~~ cpp
int ret = pthread_create(...);
if (ret != 0) {
    fprintf(stderr, "Error: pthread_create, %s\n", strerror(ret));
    exit(-1);
}
~~~

