---
title: 複数行のテキストを含んだ String を一行ずつ処理する
created: 2013-03-19
---

ネット経由で長いテキストデータを一度に取得したときなどは、テキストデータ受信後にそのテキストを一行ずつ処理したいことがあります。
String 変数に複数行に渡るテキスト（改行を含む文字列）が含まれている場合に、そのテキストを一行ずつ処理するには次のようにします。

~~~ java
// import java.io.BufferedReader;
// import java.io.StringReader;

try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
    String line = null;
    while ((line = reader.readLine()) != null) {
        System.out.println(">>> " + line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
~~~

例えば、上記の `text` 変数に `"AAA\nBBB\nCCC"` という文字列が格納されている場合、下記のように表示されます。

~~~
>>> AAA
>>> BBB
>>> CCC
~~~

