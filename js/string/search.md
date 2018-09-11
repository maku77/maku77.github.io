---
title: "文字列の中から文字列を検索する (String#search, RegExp#test)"
date: "2018-09-11"
---


String#search による文字列検索
----

### search の基本

`String#search(regexp)` メソッドを使用すると、正規表現パターンを使用して `String` オブジェクト内の文字列を検索することができます。
文字列が見つかった場合は、その先頭のインデックスを返し、見つからなかった場合は -1 を返します。
パラメータには `RegExp` オブジェクトを指定するか、次のように正規表現リテラルで検索パターンを指定します（単純な文字列を渡した場合は、内部で `RegExp` オブジェクトが生成されます）。

#### 例: text の中から leader という文字列を検索

~~~ javascript
var text = 'A leader is a dealer in hope.';
var index = text.search(/leader/);
if (index == -1) {
    console.log('見つかりませんでした');
} else {
    console.log('位置(' + index + ')に見つかりました');
}
~~~

#### 実行結果

~~~
位置(2)に見つかりました。
~~~


### 大文字と小文字を区別しない検索

`String#search(regexp)` メソッドのパラメータとして渡す正規表現の属性として **`i`** を指定すると、大文字と小文字を区別しない検索を行えます。

~~~ javascript
var index = text.search(/javascript/i);
~~~

上記のようにすると、`JavaScript` にも `javascript` にも `JAVASCRIPT` にも一致します。


### コラム: グローバル検索はない

`String#search(regexp)` は、最初に見つかった文字列のインデックスを返すため、次のように正規表現属性の `g`（グローバル検索）を指定しても無視されます（意味がない）。

#### 正規表現の g 属性は無視される

~~~ javascript
var text = 'Pen pineapple apple pen';
var index = text.search(/apple/g);
console.log(index);  //=> 8
~~~

上記の例では、テキスト内に `apple` という文字列は2ヶ所（8と14）見つかりますが、返されるインデックスは最初に見つかった文字列のインデックス（8）のみです。


RegExp#test による文字列検索
----

正規表現オブジェクトの `RegExp#test(string)` メソッドを使っても、`String#search(regexp)` と同様に文字列検索を行うことができます。
ただし、こちらの場合は、見つかった位置を返すのではなく、単純に見つかったかどうかを示す真偽値 (`true`/`false`) を返します。

~~~ javascript
var re = /java/i;
console.log(re.test('Javascript is not Java'));  //=> true
console.log(re.test('Python is cool'));          //=> false
console.log(re.test('Ruby is fantastic'));       //=> false
~~~


ユーザ入力を使った検索
----

ユーザが入力したテキストなど、文字列変数に格納された値を使って正規表現オブジェクトを作成するには、`RegExp` コンストラクタを使用します。
`RegExp` コンストラクタの第2パラメータでは、正規表現属性（`'i'`、`'g'`、`'gi'` などのフラグ）を指定することができます。

~~~ javascript
// ユーザの入力したテキストが input 変数に格納されていると仮定
var re = new RegExp(input, 'i');
if (re.test('Javascript is not Java')) {
  // 見つけたっ
}
~~~

