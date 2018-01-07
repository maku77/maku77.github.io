---
title: 文字列の結合には StringBuffer ではなく StringBuilder を使用する
date: "2014-07-02"
---

StringBuffer と StringBuilder
----

Java 1.4 までの時代は、文字列の結合には、String の結合操作（`+` 演算子）よりも、java.lang.StringBuilder の `append()` を使用すべし、と言われてきました。
immutable（不変）な String を使うよりも、StringBuilder を使ったほうが、内部的な String のコンストラクタ呼び出し回数を減らせるからです。

Java 1.5 (Tiger) からは、StringBuffer と同様のインタフェースを提供し、同期化されない **java.lang.StringBuilder** が追加されました。
StringBuffer に比べ、スレッドセーフではありませんが、文字列の修正（`append` や `insert`）はより高速に動作します。
ほとんどのケースでは、単独の文字列変数をスレッドセーフに設計することはないため、StringBuffer よりも StringBuilder を使うべきです。

実際に 1 秒間に何回結合を行えるかを計測すると、以下のような感じの結果になります。

~~~
String: 96
StringBuffer: 782
StringBuilder: 9036
~~~

String よりも StringBuffer の方が 10 倍程度、String よりも StringBuilder の方が 100 倍程度高速です。
以下は計測に使用したコードです（`System.nanoTime()` などの実行時間は誤差と考えます）。


文字列結合のパフォーマンス比較 (String/StringBuffer/StringBuilder)
----

~~~ java
public class Main {
    private final static long NANO_FOR_1SEC = 1000 * 1000;
    private final static String INIT_CHARS = "AAAAAAAAAA";
    private final static String APPEND_CHARS = "BBBBBBBBBB";

    private static void concatString() {
        final long endTime = System.nanoTime() + NANO_FOR_1SEC;
        int count = 0;
        String s = INIT_CHARS;
        while (System.nanoTime() <= endTime) {
            s += APPEND_CHARS;
            ++count;
        }
        System.out.println("String: " + count);
    }

    private static void concatStringBuffer() {
        final long endTime = System.nanoTime() + NANO_FOR_1SEC;
        int count = 0;
        StringBuffer buf = new StringBuffer(INIT_CHARS);
        while (System.nanoTime() <= endTime) {
            buf.append(APPEND_CHARS);
            ++count;
        }
        System.out.println("StringBuffer: " + count);
    }

    private static void concatStringBuilder() {
        final long endTime = System.nanoTime() + NANO_FOR_1SEC;
        int count = 0;
        StringBuilder builder = new StringBuilder(INIT_CHARS);
        while (System.nanoTime() <= endTime) {
            builder.append(APPEND_CHARS);
            ++count;
        }
        System.out.println("StringBuilder: " + count);
    }

    public static void main(String args[]) {
        concatString();
        concatStringBuffer();
        concatStringBuilder();
    }
}
~~~

