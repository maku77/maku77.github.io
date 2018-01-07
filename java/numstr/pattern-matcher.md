---
title: 文字列の一部が正規表現に一致するか調べ、一致した部分をグループごとに抜き出す (Pattern.matcher)
date: "2005-05-25"
---

正規表現パターン（`Pattern` オブジェクト）の、`Pattern#matcher(str)` メソッドを使用すると、その正規表現が、渡した文字列の中に存在するかを調べることができます。
また、正規表現に実際に一致した部分文字列を抽出することもできます。
正規表現パターンの中で括弧を使ってグルーピングしておくと、それらのパターンに一致した部分文字列を個別に抽出することができます。

パターンに一致する部分を抜き出す
----

`Pattern#matcher(str)` を使用すると、マッチング結果を取得するための `Matcher` オブジェクトを取得することができます。
`Matcher.find()` は、パターンに一致する部分文字列が見つかるたびに `true` を返します。

~~~ java
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// 正規表現パターンを作成
Pattern p = Pattern.compile("(B+(C+)(D+))(E+)");

// マッチング
Matcher m = p.matcher("AAABBBCCCDDDEEEFFF");

// 先頭からマッチングを繰り返す（ここでは一回しかマッチしない）
while (m.find()) {
    // マッチした部分をグループごとに抜き出す
    for (int i = 0; i < m.groupCount(); i++) {
        System.out.println("(" + i + ") = " + m.group(i));
    }
}
~~~

#### 実行結果

~~~
(0) = BBBCCCDDDEEE
(1) = BBBCCCDDD
(2) = CCC
(3) = DDD
~~~

* `Matcher#group(0)` は、正規表現パターンに一致した部分文字列全体を返します。
* `Matcher#group(N)` は、N 番目の左括弧で囲まれた部分のパターンに一致した部分文字列を返します（N は１から始まります）。

パターンに一致する部分を繰り返し抜き出す
----

以下の例では、`<` と `>` で囲まれている部分を順番に抜き出しています。
最短一致でマッチングさせるために、`.+` ではなく `.+?` というパターンを使用することに注意してください（ここを間違えると、文字列全体に一致してしまいます）。
パターンに一致する文字列が見つかるごとに `Matcher#find()` メソッドは `true` を返すため、下記のように while ループで処理することによって、パターンに一致する部分をすべて抽出することができます。

~~~ java
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

String input = "<one> AAA <two> BBB <three>";
Pattern p = Pattern.compile("<(.+?)>");
Matcher m = p.matcher(input);
while (m.find()) {
    System.out.println(m.group(0));  // 括弧を含めて抽出（例: <one>）
    System.out.println(m.group(1));  // 括弧を除いて抽出（例: one）
}
~~~

#### 実行結果

~~~
<one>
one
<two>
two
<three>
three
~~~

`Matcher#group(0)` は、パターン全体に一致する部分を表すので、`<` と `>` を除いた部分を取得するには、上記のように、パターン文字列内で括弧（`(` と `)`）を使って抽出部分をグルーピングしておき、`Matcher#group(1)` で取得する必要があることに注意してください。
