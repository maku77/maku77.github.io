---
title: Boolean 型で synchronized するのは NG
created: 2014-06-16
---

下記のように、`synchronized` ブロックのパラメータで Boolean 型のオブジェクトを指定するのは間違いです。

~~~ java
// Boolean isFoo = ...;

synchronized (isFoo) {
}
~~~

Boolean は実質的には `TRUE`、`FALSE` の 2 オブジェクトしか存在しないので、`synchronized` によるロック対象が想定よりも広がってしまいます。

下記のテストコードでは、`isFoo` と `isBar` という 2 つの Boolean 型変数を作成していますが、実際には同一のインスタンス `Boolean.TRUE` を参照していることが分かります。

~~~ java
Boolean isFoo = 1 == 1;
Boolean isBar = 2 == 2;
System.out.println(isFoo == isBar);         //=> true
System.out.println(isFoo == Boolean.TRUE);  //=> true
System.out.println(isBar == Boolean.TRUE);  //=> true
~~~

つまり、下記のような 2 つの `synchronized` ブロックは、意図せず互いに排他処理されてしまうことになります。

~~~ java
public void task1() {
    synchronized (isFoo) {
        // ... isFoo への排他アクセスを意図 ...
    }
}

public void task2() {
    synchronized (isBar) {
        // ... isBar への排他アクセスを意図 ...
    }
}
~~~

排他制御の対象が Boolean 型オブジェクトの場合は、`synchronized` パラメータにそのオブジェクトをそのまま渡すのでなく、別のロック用オブジェクト（Object 型で OK）を用意して `synchronized` 処理を記述するようにしましょう。

