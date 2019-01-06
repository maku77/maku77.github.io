---
title: "Lock と Condition による条件ごとのスレッド待機"
date: "2014-08-20"
---

従来の Object#wait によるスレッド待機
----

`Object#wait()`、`Object#notifyAll()` のスレッド間通信の仕組みでは、ロック (`synchronized`) に使用するオブジェクトと、ウェイトセットに入る (`wait`) ためのオブジェクトが同一であるという条件がありました。

#### スレッド A

```java
synchronized (obj) {
    obj.wait();  // ウェイトセットに入り、他のスレッドから通知があるまで待機
    ...
}
```

#### スレッド B

```java
synchronized (obj) {
    obj.notifyAll()  // 他のスレッドに、待機状態から抜けていいよと通知する
}
// スレッド B がここまで来てロックを解放すると、スレッド A が動き出す
```

Condition クラスによるスレッド待機
----

Java 5.0 (Tiger) で導入された、`java.util.concurrent.locks.Lock` と、`Condition` クラスを使用すると、一つのロック (`Lock`) の中で、異なる種類の条件 (`Condition`) による通知が可能になります。
まず、`Lock` インタフェースによるロックは以下のような流れになります。

```java
// private Lock myLock = new ReentrantLock();

myLock.lock();
try {
    ...排他制御が必要な処理...
} finally {
    myLock.unlock();
}
```

これは、従来の `synchronized` と同様のロックを提供します（`Lock` インタフェースには、より柔軟なロック方法を提供する API があります）。
アンロックは、必ず `finally` ブロックで行い、ロック中に `Exception` が発生した場合でもアンロックされることを保証しなければいけません。

スレッド間の通知を行うための `Condition` オブジェクトを生成するには、`Lock#newCondition()` を使用します。

```java
private Lock myLock = new ReentrantLock();
private condition1 = myLock.newCondition();
private condition2 = myLock.newCondition();
```

`Lock` オブジェクトから `Condition` オブジェクトを生成していることからも分かるように、その `Lock` オブジェクトのロック中にだけ、その `Condition` オブジェクトの `wait()`、`notifyAll()` などの呼び出しが可能です。

実際の使用例は、API ドキュメントのサンプルが分かりやすいでしょう。
このサンプルでは、バッファクラスへの `put()` と `take()` を同じオブジェクトでロックし、その中で、バッファ空である通知、バッファが満杯である通知を同時に使用しています。

- http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/Condition.html

```java
class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();  //★バッファに空きができるまで待機
            items[putptr] = x;
            if (++putptr == items.length)
                putptr = 0;
            ++count;
            notEmpty.signal();  //★バッファに何らかのデータがあるよと通知
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();  //★バッファに何らかのデータが入るまで待機
            Object x = items[takeptr];
            if (++takeptr == items.length)
                takeptr = 0;
            --count;
            notFull.signal();  //★バッファは満杯ではないよと通知
            return x;
        } finally {
            lock.unlock();
        }
    }
}
```

