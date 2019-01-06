---
title: "DEBUG フラグでログ出力を ON/OFF するときは、呼び出し側で if 分岐すること"
date: "2014-08-25"
---

Java でデバッグ用のログ出力を、デバッグフラグの値に応じて ON/OFF するときは、以下のように**呼び出し側のコード**にデバッグフラグのチェックコードを記述する必要があります。

~~~ java
public class GoodClass {
    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        String hello = "Hello";
        if (DEBUG) {
            System.out.println(hello + "World");
        }
    }
}
~~~

こうすることによって、定数 `DEBUG` が `false` になっているときに、ログ出力の呼び出しコードが削除されます。
よくある間違いは、デバッグ出力用クラスなどを作って、そのメソッドの中でデバッグフラグをチェックするようなコードです。

~~~ java
// 間違った実装方法
public class BadClass {
    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        String hello = "Hello";
        log(hello + "World");
    }

    private static void log(String message) {
        if (DEBUG) {
            System.out.println(message);
        }
    }
}
~~~

上記のようにすると、`log()` メソッドの中の処理はコンパイル時に削除されますが、呼び出し部分のコードは削除されません。
さらに悪いことに、呼び出し部分では、コストのかかる `StringBuilder` インスタンスの生成などが行われます。
このようなコードが増えると、GC（ガーベジコレクション）を発生させる原因にもなり、アプリケーションの速度はどんどん遅くなっていきます。
上記の 2 つのコードが実際にどんな感じでコンパイルされるかは、`javap` で逆アセンブルするとよく分かります。

#### GoodClass の逆アセンブル結果

~~~
$ javap -c GoodClass
Compiled from "GoodClass.java"
public class GoodClass {
  public GoodClass();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: ldc           #2                  // String Hello
       2: astore_1
       3: return
}
~~~

#### BadClass の逆アセンブル結果

~~~
$ javap -c BadClass
Compiled from "BadClass.java"
public class BadClass {
  public BadClass();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: ldc           #2                  // String Hello
       2: astore_1
       3: new           #3                  // class java/lang/StringBuilder
       6: dup
       7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
      10: aload_1
      11: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      14: ldc           #6                  // String World
      16: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      19: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      22: invokestatic  #8                  // Method log:(Ljava/lang/String;)V
      25: return
}
~~~

Java には C/C++ のようなプリプロセッサを使って、簡単にデバッグ用のログコードを取り除く方法がないため、若干面倒ですが、呼び出し側のコードでデバッグフラグのチェックを行うのが基本です。

将来的には、JIT コンパイラが賢くなれば、無駄なメソッド呼び出しコードを削除しながら実行とかしてくれるようになるかもしれません。
ただ、それを実現するには、呼び出し部分のコードが副作用を与えていないかを確認しないといけないので、なかなか難しいかと思います。
他には、プリプロセッサ系のツールを使えば C/C++ と同様に、呼出しコードの完全削除を実現できますけど、こちらもプロジェクトによっては導入しづらいですね。

