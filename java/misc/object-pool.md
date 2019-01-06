---
title: "オブジェクトプールを実現するためのクラスを実装する"
date: "2012-03-01"
---

短命のオブジェクトをたくさん作るようなプログラムは、GC (Garbage Collection) が頻繁に発生してしまいます。
このようなケースでは、オブジェクトプールの仕組みを利用して、インスタンス化したオブジェクトを使いまわすことでパフォーマンスの低下を防ぐことができます。
下記の ObjectPool クラスは、オブジェクトプールを（ほんの少しだけ）簡単に実現するためのクラスです。

#### ObjectPool.java

~~~ java
package net.memoja.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Pool of objects so that they can be reused without calling the {@code new} method.
 * In general this is held as a static field of a class.
 *
 * <pre>
 * {@code // Usage example:
 * public class MyClass {
 *     private static final ObjectPool<MyClass> sPool = new ObjectPool<>();
 *
 *     // Object should be instantiated by obtain().
 *     private MyClass() {}
 *
 *     public static MyClass obtain(int value) {
 *         MyClass obj = sPool.obtain();
 *         if (obj == null) {
 *             obj = new MyClass();
 *         }
 *         obj.value = value;
 *         return obj;
 *     }
 *
 *     public void recycle() {
 *         this.value = 0;
 *         sPool.recycle(this);
 *     }
 * }
 * </pre>
 *
 * @param <T> Object type to be pooled in the pool.
 */
public class ObjectPool<T> {
    // List for pooling objects.
    private final List<T> pool;

    /**
     * Create an instance of pool.
     */
    public ObjectPool() {
        pool = new ArrayList<>();
    }

    /**
     * Obtain an object from the pool.
     *
     * @return A pooled object. Returns null if the pool is empty.
     */
    public final synchronized T obtain() {
        if (!pool.isEmpty()) {
            T obj = pool.get(0);
            pool.remove(0);
            return obj;
        }
        return null;
    }

    /**
     * Return the instance to the pool. You MUST NOT touch the instance after calling this function,
     * it has effectively been freed.
     *
     * @param object The object to be pooled.
     */
    public synchronized void recycle(T object) {
        pool.add(object);
    }

    /**
     * Return the number of the pooled objects.
     *
     * @return The number of objects in the pool.
     */
    public synchronized int size() {
        return pool.size();
    }
}
~~~

次の MyClass クラスは、ObjectPool を使用して、オブジェクトをプールする（使いまわす）ように実装しています。

#### MyClass.java

~~~ java
import net.memoja.util.ObjectPool;

public class MyClass {
    // プールオブジェクトはクラスフィールドで保持。
    private static final ObjectPool<MyClass> sPool = new ObjectPool<>();
    private int value1;
    private int value2;

    // プール対象のオブジェクトは、コンストラクタは直接呼び出さない。
    private MyClass() {
    }

    // コンストラクタの代わりにこっちを使ってインスタンスを取得する。
    public static final MyClass obtain(int value1, int value2) {
        MyClass obj = sPool.obtain();
        if (obj == null) {
            obj = new MyClass();
        }
        obj.value1 = value1;
        obj.value2 = value2;
        return obj;
    }

    // プールにオブジェクトを返却。次回の obtain() で使いまわされる。
    public final void recycle() {
        this.value1 = 0;
        this.value2 = 0;
        sPool.recycle(this);
    }

    @Override
    public String toString() {
        return value1 + ", " + value2 + " (PoolSize=" + sPool.size() + ")";
    }
}
~~~

MyClass を使用するクライアントは下記のように実装することで、余計なインスタンス化を防ぐことができます。

#### 使用例

~~~ java
public class Main {
    public static void main(String[] args) {
        // オブジェクトを 3 つ取得（ここではプールは空なので内部で new される）
        MyClass obj1 = MyClass.obtain(1, 1);
        System.out.println(obj1);
        MyClass obj2 = MyClass.obtain(2, 2);
        System.out.println(obj2);
        MyClass obj3 = MyClass.obtain(3, 3);
        System.out.println(obj3);

        // 必要なくなったらプールに戻す
        obj1.recycle();
        obj2.recycle();
        obj3.recycle();

        // もう一度オブジェクトを 3 つ取得（ここではプールされたオブジェクトが使いまわされる）
        MyClass obj4 = MyClass.obtain(4, 4);
        System.out.println(obj4);
        MyClass obj5 = MyClass.obtain(5, 5);
        System.out.println(obj5);
        MyClass obj6 = MyClass.obtain(6, 6);
        System.out.println(obj6);
    }
}
~~~

