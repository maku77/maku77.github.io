---
title: jar コマンドで JAR ライブラリを作成する
created: 2012-09-27
---

JAR ライブラリの作成
----

ここでは以下のような Java ファイルから、`mylib.jar` というライブラリを作成してみます。

#### MathUtil.java

```java
package my.lib;

public class MathUtil {
    public static int add(int a, int b) {
        return a + b;
    }
}
```

上記のコードをコンパイルし、以下のようなディレクトリに置きます。

```
my/lib/MathUtil.class
```

クラスファイルをアーカイブして、JAR ライブラリを作成するには、`jar cvf` コマンドを使用します。
以下のようにすると、`my` ディレクトリ以下のファイル群から `mylib.jar` が作成されます。

```
D:\> jar cvf mylib.jar my
added manifest
adding: my/(in = 0) (out= 0)(stored 0%)
adding: my/lib/(in = 0) (out= 0)(stored 0%)
adding: my/lib/MathUtil.class(in = 253) (out= 198)(deflated 21%)
```

作成された JAR ファイル内のファイルリストを表示するには、`jar tvf` コマンドを使用します。
自動的に `META-INF/MANIFEST.MF` というマニフェストファイルが追加されているのが分かります。

```
D:\> jar tvf mylib.jar
     0 Thu Sep 27 10:55:18 JST 2012 META-INF/
    68 Thu Sep 27 10:55:18 JST 2012 META-INF/MANIFEST.MF
     0 Thu Sep 27 10:52:56 JST 2012 my/
     0 Thu Sep 27 10:52:56 JST 2012 my/lib/
   253 Thu Sep 27 10:47:46 JST 2012 my/lib/MathUtil.class
```

別のプログラムから JAR ライブラリを使用する
----

#### Main.java

```java
import my.lib.MathUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println(MathUtil.add(100, 200));
    }
}
```

JAR ライブラリを参照するプログラムをコンパイルするとき、実行するときは、JAR ライブラリへのクラスパスを通しておく必要があります。
クラスパスは、`java` コマンドや `javac` コマンドの実行時に `-cp` オプションで指定するか、`CLASSPATH` 環境変数で指定しておきます。

#### コンパイル時に JAR を参照する

```
D:\> javac -cp mylib.jar Main.java
```

#### 実行時に JAR を参照する

```
D:\> java -cp mylib.jar;. Main
```

複数のクラスパスを指定する場合の区切り文字は、Windows では `;`、Linux 系 OS では `:` になることに注意してください。

クラスパスの指定には、ワイルドカード `*` が使用できます。
ワイルドカードは、すべての JAR ファイルにマッチします。
例えば、ホームディレクトリの `myjars` ディレクトリ内に JAR ファイルを置いておく場合は、以下のように環境変数を設定すればよいでしょう。

```
D:\> set CLASSPATH=%CLASSPATH%;%HOME%/myjars/*
D:\> javac Main.java
```

