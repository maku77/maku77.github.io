---
title: Java のオブジェクトロックは再入可能であることを理解する
created: 2014-06-19
---

Java ではスレッドのセマフォとして使用するオブジェクトに同じオブジェクトを指定した場合、その `synchronized` ブロックは再入可能となります。
簡単に言うと、同一スレッド内で、同一の `synchronized` ブロックに入ろうとしたときに、そこでデッドロックしたりはしないということです。

下記のサンプルコードでは、同じロックオブジェクト (`lock`) を使用した `synchronized` ブロックが入れ子構成になっていますが、二段目の `synchronized` ブロックが問題なく実行されます。

```java
public class SafeLock {
    private Object lock = new Object();

    public void method() {
        synchronized (lock) {
            synchronized (lock) {
                System.out.println("OK");
            }
        }
    }
}
```

メソッド呼び出しが入れ子になっていても同様に再入可能です。

```java
public class SafeLock {
    private Object lock = new Object();

    public void method1() {
        synchronized (lock) {
            method2();
        }
    }

    public void method2() {
        synchronized (lock) {
            System.out.println("OK");
        }
    }
}
```

