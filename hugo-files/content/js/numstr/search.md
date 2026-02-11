---
title: "JavaScript で文字列の中から文字列を検索する (String#search, RegExp#test)"
url: "p/p5nx3n9/"
date: "2018-09-11"
lastmod: "2023-12-01"
changes:
  - 2023-12-01: モダンな JavaScript の書き方に修正
tags: ["javascript"]
aliases: /js/string/search.html
---

String#search による文字列検索
----

### search の基本

__`String#search()`__ メソッドを使用すると、正規表現パターンを使用して `String` オブジェクト内の文字列を検索することができます。
文字列が見つかった場合は、その先頭のインデックスを返し、見つからなかった場合は `-1` を返します。
パラメータには `RegExp` オブジェクトを指定するか、次のように正規表現リテラルで検索パターンを指定します（単純な文字列を渡した場合は、内部で `RegExp` オブジェクトが生成されます）。

{{< code lang="js" title="例: text の中から leader という文字列を検索" >}}
const text = "A leader is a dealer in hope.";
const index = text.search(/leader/);
if (index == -1) {
    console.log("見つかりませんでした");
} else {
    console.log(`位置 ${index} に見つかりました`);
}
{{< /code >}}

{{< code title="実行結果" >}}
位置 2 に見つかりました。
{{< /code >}}

### 大文字と小文字を区別しない検索

`String#search()` メソッドのパラメータとして渡す正規表現の属性として __`i`__ を指定すると、大文字と小文字を区別しない検索を行えます。

```js
const index = text.search(/javascript/i);
```

上記のようにすると、`JavaScript` にも `javascript` にも `JAVASCRIPT` にも一致します。

### コラム: グローバル検索はない

`String#search()` メソッドは、最初に見つかった文字列のインデックスを返すため、次のように正規表現属性の `g`（グローバル検索）を指定しても無視されます（意味がありません）。

{{< code lang="js" title="検索時は正規表現の g 属性は無視される" >}}
const text = "Pen pineapple apple pen";
const index = text.search(/apple/g);
console.log(index);  //=> 8
{{< /code >}}

上記の例では、テキスト内に `apple` という文字列は 2 か所（8と14）見つかりますが、返されるインデックスは最初に見つかった文字列のインデックス 8 のみです。


RegExp#test による文字列検索
----

正規表現オブジェクトの __`RegExp#test(string)`__ メソッドを使っても、`String#search(regexp)` と同様に文字列検索を行うことができます。
ただし、こちらの場合は、見つかった位置を返すのではなく、単純に見つかったかどうかを示す真偽値 (`true`/`false`) を返します。

```js
const re = /java/i;
console.log(re.test('Javascript is not Java'));  //=> true
console.log(re.test('Python is cool'));          //=> false
console.log(re.test('Ruby is fantastic'));       //=> false
```


ユーザ入力を使った検索
----

ユーザが入力したテキストなど、文字列変数に格納された値を使って正規表現オブジェクトを作成するには、`RegExp` コンストラクタを使用します。
`RegExp` コンストラクタの第 2 引数では、正規表現属性（`g`、`i`、`gi` などのフラグ）を指定することができます。

```js
// ユーザの入力したテキストが input 変数に格納されていると仮定
// const input = "Java";

const re = new RegExp(input, "i");
if (re.test("Javascript is not Java")) {
  console.log("見つけたっ");
}
```

