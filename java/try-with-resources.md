---
title: Java7 の try with resources でストリームの close を自動的に行う
date: "2014-10-07"
---

Java 6 の時代は、何らかのストリームに対して I/O 処理を行った後は、`try ~ finally` を利用して、自力で `close()` 処理を行う必要がありました。
下記のコードはこの典型的な例ですが、半分以上が `close()` のための記述になってしまっています。

#### Java 6 までの記述方法

```java
OutputStream out = null;
try {
    out = new FileOutputStream("output.txt");
    out.write(1);
    out.write(2);
} catch (IOException e) {
    System.err.println(e.getMessage());
} finally {
    if (out != null) {
        try {
            out.close();
        } catch (IOException e) {
            // Ignore
        }
    }
}
```

Java 7 には、この `close()` 処理を自動化するための **try with resources** の構文が用意されています。
下記のように、本質的な部分に集中してコーディングすることができるようになりました。

#### Java 7 の try with resources を使った記述方法

```java
try (OutputStream out = new FileOutputStream("output.txt")) {
    out.write(1);
    out.write(2);
} catch (IOException e) {
    System.err.println(e.getMessage());
}
```

`OutputStream` クラスに限らず、`Closeable` インタフェースを実装するオブジェクトであれば、この構文を使用して自動で `close()` 処理を行うことができます。
例えば、リレーショナルデータベースのレコードを走査するための `Cursor` オブジェクトの `close()` 処理も、この仕組みを使ってシンプルに記述することができます。

