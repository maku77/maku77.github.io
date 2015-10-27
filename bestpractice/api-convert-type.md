---
title: 型変換用メソッドは受け取り側クラスに作る
created: 2003-08-04
---

あるアプリケーションドメイン内のクラスにおいて、データ変換用のメソッドを用意する場合は、自分自身のデータを他のデータ型に変換するメソッドを作るのではなく、他のデータを自分自身の型に変換するメソッドを用意すると全体の構造がすっきりします。

```java
public class A {
    public static A convertFrom(B b) {
        ...
    }
    // コンストラクタで他のデータ型から変換する方法もあり
}
```

利点は、変換元のクラスに変換先クラスの知識を持たせないでよいことと、以下のように一貫した呼び方で、いろんなオブジェクトからの変換を行えることです。

```java
A a = A.convertFrom(b);
A a = A.convertFrom(c);
A a = A.convertFrom(d);
```

次のように変換元のクラスにメソッドがたくさん増えるのはあまり格好良くないです。

```java
B b = a.createB();
C c = a.createC();
D d = a.createD();
```

ただ、これは変換先の型への依存関係をどちらに持たせるかの問題なので、後者の方法しかとれない場合もあります。

変換メソッドのいい例は Java の ```Integer``` クラスなどです。```Integer.valueOf()``` メソッドは、引数で受け取ったデータから、```Integer``` オブジェクトを生成します（ちなみに、プリミティブな ```int``` 型が欲しいときは ```Integer.parseInt()``` の方を使います）。

```java
Integer a = Integer.valueOf(100);
Integer b = Integer.valueOf("100"); 
```
