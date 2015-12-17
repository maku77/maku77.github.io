---
title: static ブロックが実行されるタイミング
created: 2010-10-26
---

```java
public class MyLibrary {
    static {
        System.out.println("MyLibrary was loaded");
    }

    public static void test() {
        System.out.println("test");
    }
}
```

上記のような `MyLibrary` の `static` ブロックが実行されるのは、実際に最初に `MyLibrary.test()` メソッドが呼び出されたときです。

例えば、下記のようなケースでは static ブロックは実行されません。

- `MyLibrary` を `import` したとき。
- `test()` を呼び出しているメソッドを呼んだが、条件分岐などにより `test()` 自体は呼ばれなかった時。

