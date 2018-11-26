---
title: "シングルトンクラスの Lazy Loading イディオム"
date: "2018-11-26"
---

よくあるシングルトン実装の例
----

下記の `MyLibrary` クラスは、典型的なシングルトン実装の例です。

#### MyLibrary.java

~~~ java
public class MyLibrary {
    private static final MyLibrary instance = new MyLibrary();

    private MyLibrary() {
        System.out.println("MyLibrary constructor");
    }

    public static MyLibrary getInstance() {
        System.out.println("MyLibrary getInstance()");
        return instance;
    }

    public static void hello() {
        System.out.println("MyLibrary hello()");
    }
}
~~~

`MyLibrary` クラスは 1 つの static メソッド (`hello`) を備えており、このメソッドを呼び出すと `MyLibrary` のシングルトンインスタンスが生成されてしまいます（`getInstance()` を呼び出さなくてもインスタンスが生成されてしまいます）。

次のテストコードを実行してみると、インスタンス生成のタイミングがよくわかります。

#### Main.java

~~~ java
public class Main {
    public static void main(String... args) {
        MyLibrary.hello();
        MyLibrary lib = MyLibrary.getInstance();
    }
}
~~~

#### 実行結果

~~~
MyLibrary constructor
MyLibrary hello()
MyLibrary getInstance()
~~~

static メソッドの `hello()` の呼び出しに先立って、コンストラクタが呼び出されていることが分かります。
でも、本来シングルトンインスタンス自体は `getInstance()` が呼び出されるまでは必要ないものです。


Lazy Loading により getInstance でシングルトン生成する
----

次のような Lazy Loading イディオムを使用することで、`getInstance()` メソッドを呼び出した時点で初めてシングルトンインスタンスを生成する、という振る舞いを実現することができます。

#### MyLibrary2.java

~~~ java
public class MyLibrary2 {
    private static class Singleton {
        static final MyLibrary2 instance = new MyLibrary2();
    }

    private MyLibrary2() {
        System.out.println("MyLibrary2 constructor");
    }

    public static MyLibrary2 getInstance() {
        System.out.println("MyLibrary2 getInstance()");
        return Singleton.instance;  // ここでシングルトンインスタンスが生成される
    }

    public static void hello() {
        System.out.println("MyLibrary2 hello()");
    }
}
~~~

`MyLibrary` クラスとの違いは、`MyLibrary2` クラスのインナークラスとして `Singleton` を用意し、その中で `MyLibrary2` のシングルトンインスタンスを保持しているところです。
こうすることで、`MyLibrary2` のインスタンスは `Singleton` クラスが参照されるときにインスタンス化されるようになります。
実際に `Singleton` クラスが使用されるのは、`MyLibrary2` クラスの `getInstance()` メソッドの中だけなので、`getInstance()` を実行したときに初めて `MyLibrary2` クラスのインスタンスが生成されることが保証されます。

次の `Main` クラスは先ほどと同様のテストですが、`MyLibrary` を `MyLibrary2` に置き換えています。

#### Main.java

~~~ java
public class Main {
    public static void main(String... args) {
        MyLibrary2.hello();
        MyLibrary2 lib2 = MyLibrary2.getInstance();
    }
}
~~~

#### 実行結果

~~~
MyLibrary2 hello()
MyLibrary2 getInstance()
MyLibrary2 constructor
~~~

`MyLibrary` の場合とは異なり、`MyLibrary2` のインスタンス生成は、`getInstance()` メソッドが呼び出されたときに行われていることが分かります。

