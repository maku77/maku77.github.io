---
title: コールスタックを表示する
created: 2010-07-29
---

任意の場所でコールスタックを表示する
----

`Trowable` オブジェクトの `getStackTrace` メソッドを使用して、現在のコンテキストにおけるスタックトレース情報を取得することができます。

#### Test.java

```java
public class Test {
    public void method1() {
        method2();
    }

    public void method2() {
        method3();
    }

    public void method3() {
        // Show call stack.
        StackTraceElement[] elems = new Throwable().getStackTrace();
        for (int i = 0; i < elems.length; ++i) {
            System.out.println(elems.length - i + " " +
                    elems[i].getClassName() + "#" +
                    elems[i].getMethodName());
        }
    }

    public static void main(String[] args) {
        new Test().method1();
    }
}
```

#### 実行結果

```
4 Test#method3
3 Test#method2
2 Test#method1
1 Test#main
```

出力フォーマットにこだわりがないのであれば、単純に、`Trowable#printStackTrace()` を使用して標準エラー出力に情報を出力することもできます。


コールスタック表示用のユーティリティクラスを作る
----

コールスタックを表示するためのライブラリクラスとして用意しておくこともできます。
この場合は、コールスタックを表示するためのメソッド自信の情報を表示しないように、表示開始位置を 1 つずらしておく必要があります。

#### CallStackUtil.java

```java
public class CallStackUtil {
    public static void showCallStack() {
        StackTraceElement[] elems = new Throwable().getStackTrace();
        // First element represents this method itself.
        for (int i = 1; i < elems.length; ++i) {
            System.out.println(elems.length - i + " " +
                    elems[i].getClassName() + "#" +
                    elems[i].getMethodName());
        }
    }
}
```

#### 使い方

```java
// 任意の場所で
CallStackUtil.showCallStack();
```

J2SE 5.0 からは `new Throwable()` せずに、`Thread.currentThread().getStackTrace()` とする方法がよいかも。
ただし、この場合は、配列のインデックス 0 は、`Thread.getStackTrace()` になります。

```java
StackTraceElement[] elems = Thread.currentThread().getStackTrace();
```

Throwable オブジェクトのスタックトレースを String で取得する
----

下記は Android フレームワークの `Log.java` からの抜粋です。
`Throwable` オブジェクトには `printStackTrace` というメソッドがあり、スタックトレース情報を `PrintWriter` に対して出力することができます。
これを利用して、スタックトレース情報を `String` で取得しています。

#### Log.java より抜粋

```java
// import java.io.PrintWriter;
// import java.io.StringWriter;

/**
 * Handy function to get a loggable stack trace from a Throwable.
 * @param tr An exception to log
 */
public static String getStackTraceString(Throwable tr) {
    if (tr == null) {
        return "";
    }
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    tr.printStackTrace(pw);
    return sw.toString();
}
```

