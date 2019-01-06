---
title: "スレッドで発生した Uncaught Exception（未捕捉例外）をハンドルする"
date: "2014-06-25"
---

`Thread` オブジェクトに対して、`Thread.UncaughtExceptionHandler` オブジェクトを設定しておくと、そのスレッド内で発生した未捕捉例外を処理することができます（`NullPointerException` などの `RuntimeException` をまとめて捕捉できるということ）。

```java
public class Main {
    // UncaughtExceptionHandler の実装
    private static class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println("Uncaught Exception:");
            System.err.println(t.getName());   // Thread ID 1
            System.err.println(e.toString());  // java.lang.IllegalArgumentException
            System.err.println(e.getStackTrace()[0].getLineNumber());  // 28
            System.err.println(e.getStackTrace()[0].getFileName());    // Main.java
        }
    }

    // テスト
    public static void main(final String[] args) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                throw new IllegalArgumentException();  // 故意の例外発生
            }
        }, "Thread ID 1");
        thread.setUncaughtExceptionHandler(new MyExceptionHandler());
        thread.start();
    }
}
```

上記の例では、特定のスレッドに対してハンドラを設定していますが、全てのスレッドに対するデフォルトのハンドラを設定することもできます。
その場合は、`Thread` クラスに `static` メソッドとして用意されているハンドラ設定メソッドを使用します。

```java
Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler());
```

