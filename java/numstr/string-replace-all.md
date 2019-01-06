---
title: "文字列の正規表現に一致した部分を置き換える (String.replaceAll)"
date: "2005-05-25"
---

文字列置換のためのメソッド
----

正規表現に一致した部分を、別の文字列で置換したいときは `String` クラスの下記のメソッドを使用します。

- `String#replaceFirst(String regex, String replacement)`<br>
  最初に `regex` に一致した部分だけを `replacement` に置換する。
- `String#replaceAll(String regex, String replacement)`<br>
  `regex` に一致する部分をすべて `replacement` に置換する。

あるいは、`Pattern#matcher()` で取得した `Matcher` オブジェクトを使用しても同様の置換を行えます。

- `Matcher#replaceFirst(String replacement)`
- `Matcher#replaceAll(String replacement)`

String#replaceAll による置換の例
----

下記の例では、数値だけで構成されている単語を、`[Number]` という文字列に置換しています。

~~~ java
String input = "ABC 123 DEF 456";
String result = input.replaceAll("\\b[0-9]+\\b", "[Number]");
System.out.println(result);
~~~

#### 実行結果

~~~
ABC [Number] DEF [Number]
~~~

パターンに一致した部分を、置換後の文字列に含めたい場合は、`$0` を使います。

~~~ java
String input = "ABC 123 DEF 456";
String result = input.replaceAll("\\b[0-9]+\\b", "[$0]");
System.out.println(result);
~~~

#### 実行結果

~~~
ABC [123] DEF [456]
~~~

`$0` は正規表現にマッチした文字列全体を表し、`$1`, `$2`, `$3` は、それぞれ１番目、２番目、３番目の左括弧で囲まれた部分のパターンに一致した部分文字列を表します。


Matcher#replaceAll による置換の例
----

次の例では、`Matcher#replaceAll` メソッドを使用し、`<` と `>` によって囲まれている文字列（`<` と `>` を含む）を `%%%` に置換しています。

~~~ java
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

String input = "<one> AAA <two> BBB <three>";
Pattern p = Pattern.compile("<(.+?)>");
Matcher m = p.matcher(input);
String result = m.replaceAll("%%%");
System.out.println(result);
~~~

#### 実行結果

~~~
%%% AAA %%% BBB %%%
~~~

パターンに一致した部分を、置換後の文字列に含めたい場合は、`$0`、`$1`、`$2` のようにグループ番号を指定した参照を行います。
`$0` は一致した部分全体を含んでいます。
括弧（`(` と `)`）で囲まれた部分パターンに一致する文字列を参照するときは、`$1`、`$2`、`$3` のように１以降のインデックスを指定します。

この仕組みは、Wiki のようなフォーマットから、HTML フォーマットに変換するときなどに使用できます。
下記の例では、`{1.jpg}` という文字列を、`<img src="1.jpg">` という文字列に変換しています。

~~~ java
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

String input = "AAA {1.jpg} BBB {2.png} CCC";
Pattern p = Pattern.compile("\\{(.+?)\\}");
Matcher m = p.matcher(input);
String result = m.replaceAll("<img src=\"$1\">");
System.out.println(result);
~~~

#### 実行結果

~~~
AAA <img src="1.jpg"> BBB <img src="2.png"> CCC
~~~

正規表現パターン内で中括弧（`{` や `}`）を使用するときは、`\\{` や `\\}` のようにバックスラッシュ２つでエスケープする必要があることに注意してください。


本格的な置換クラスのサンプル
----

下記の ImagePattern クラスは、下記のような Wiki っぽい文字列を img タグに変換するためのユーティリティクラスです。

~~~
置換前: 【image1.jpg|画像1】
置換後: <img src='file:image/image1.jpg' alt='画像1' />
~~~

#### ImagePattern.java

~~~ java
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImagePattern {
    private static final String IMAGE_PATTERN = "【([a-zA-Z0-9._-]+?)\\|(.+?)】";
    private static final String IMAGE_REPLACEMENT = "<img src='file:image/$1' alt='$2' />";
    private static final Pattern pattern = Pattern.compile(IMAGE_PATTERN);

    public static String replace(String input) {
        Matcher m = pattern.matcher(input);
        return m.replaceAll(IMAGE_REPLACEMENT);
    }

    // Test.
    public static void main(String[] args) {
        String s = replace("AAA 【test-image.jpg|Image 1】 BBB 【2.png】 CCC");
        System.out.println(s);
    }
}
~~~

