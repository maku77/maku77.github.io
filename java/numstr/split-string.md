---
title: 文字列をデリミタで分割する (String.split, Pattern.split)
created: 2012-09-18
---

Pattern オブジェクトの以下のメソッドを使用すると、渡された文字列を、デリミタ文字列（正規表現パターン）を使って分割することができます。

~~~
String[] Pattern#split(CharSequence input);
~~~

あるいは、String オブジェクトの `split` メソッドでも同じことができます。
この場合は、デリミタとして使用する正規表現パターンの方をパラメータで渡すことになります。

~~~
String[] String#split(String regex)
String[] String#split(String regex, int limit)
~~~

文字列をスペースで分割する
----

ここでは分割前に、`String#trim()` を使用して前後の空白を取り除いています。

~~~ java
String s = "  AAA  BBB CCC  ";
String[] arr = s.trim().split("\\s+");

for (int i = 0; i < arr.length; ++i) {
    System.out.println(arr[i]);
}
~~~

#### 実行結果

~~~
AAA
BBB
CCC
~~~

文字列を連続したコロン、あるいはスペースで分割する
----

デリミタとして使用する文字列は、正規表現で指定するので、複数の文字の組み合わせをデリミタとして指定することができます。
ここでは、コロン (`:`) やスペースの連なりを、ひとつのデリミタとして処理する例を示します。

~~~ java
// import java.util.regex.Pattern;

String input = "one:two: three   four  ::  five  ";
Pattern p = Pattern.compile("[:\\s]+");
String[] arr = p.split(input);

for (String s : arr) {
    System.out.println(s);
}
~~~

#### 実行結果

~~~
one
two
three
four
five
~~~

`:` という文字も、`  ::  ` という文字列も、**ひとつの**デリミタとして扱われています。

参考
----

* [CSV 形式の文字列を配列に分割する](split-csv.html)

