---
title: "文字列から正規表現パターンに一致する部分を取り出す (String#match, RegExp#exec)"
date: "2018-09-11"
---

`String` オブジェクトの中で正規表現を利用できるメソッドとしては、[search](search)、[replace](replace)、[split](split) などがありますが、中でも強力なのは **`match`** メソッドです。

`String#match(regexp)` メソッドを使用すると、指定した正規表現パターンに一致する部分を配列オブジェクトとして取得することができます。
一致する部分がひとつも見つからない場合は `null` を返します。
`match` メソッドは、パラメータとして渡す正規表現パターンで `g` フラグを指定した場合と指定しない場合で振る舞いが変化します。


match の正規表現パターンに g フラグを指定したとき
----

`String#match(regexp)` の正規表現パターンで、`g` フラグを付加すると、戻り値はパターンに一致した部分文字列の配列になります。

#### 例: 数値部分だけを抽出して配列で取得する

~~~ javascript
var text = 'ABC 123 DEF 456 GHI 789 123ABC';
var arr = text.match(/\b\d+\b/g);
if (arr != null) {
  console.log(arr);  //=> [ '123', '456', '789' ]
}
~~~


match の正規表現パターンに g フラグを指定しなかったとき
----

`String#match(regexp)` の正規表現パターンで `g` フラグを付加しない場合、`RegExp#exec(string)` を単発で呼び出した場合と同様の振る舞いをします。
具体的には、戻り値の配列の先頭には、パターンに一致した部分の全体が格納され、残りの配列要素（インデックス1以降）には、`(` と `)` でグルーピングされたパターンに一致した部分文字列が順番に格納されます。

これを利用すると、特定のフォーマットに従った文字列から各パートの文字列を抽出するということが簡単に実現できます。
次の例では、URL 形式の文字列から、プロトコル、ホスト、パスを抽出しています。

~~~ javascript
var re = /(\w+):\/\/([\w.]+)\/(\S*)/;
var text = 'Visit my website at https://maku77.github.io/js';
var matches = text.match(re);
if (matches != null) {
  var url = matches[0];       //=> 'https://maku77.github.io/js'
  var protocol = matches[1];  //=> 'https'
  var host = matches[2];      //=> 'maku77.github.io'
  var path = matches[3];      //=> 'js'
}
~~~

実は、`match` メソッドの戻り値として返されるオブジェクトには、`index` プロパティと `input` プロパティも含まれています。
これらには、正規表現に（最初に）一致した位置と、入力として使ったテキスト全体が含まれています。
下記は、`match` メソッドの戻り値を `console.log(matches)` で出力した結果です。

~~~ javascript
[ 'https://maku77.github.io/js',
  'https',
  'maku77.github.io',
  'js',
  index: 20,
  input: 'Visit my website at https://maku77.github.io/js' ]
~~~

`index` プロパティの値が 20 ということは、検索された URL が 20 の位置から始まっていることを示しています。
`input` プロパティには、検索対象となったテキストへの参照が含まれているのがわかります。

`String#match(regexp)` を使用したこのようなパターン抽出は非常に便利ですが、1つのテキスト内に一致する部分が複数含まれているような場合にはうまく抽出できないことに注意してください。
なぜなら、`String#match(regexp)` は、最初に見つかった部分の結果だけを抽出して返すからです（`String#search()` が最初に見つかった部分の位置を返すのと同様です）。
先に述べたように、正規表現パターンに `g` フラグを付けて検索すれば、パターンに一致する文字列をまとめて配列として取得できますが、ここで説明したような特定のフォーマットに一致する文字列の各パート（`(` と `)` でグルーピングした部分）を抽出するということはできません。
この問題を解決するには、次に説明する正規表現オブジェクトの `RegExp#exec()` メソッドを使用する必要があります。


RegExp オブジェクトを使用したパターン抽出（最強！）
----

`String#match(regexp)`（正規表現の `g` フラグなし）のような特定のフォーマットに従った部分文字列の抽出を、1つの検索対象テキストの中で繰り返し実行（グローバル検索）したい場合は、`RegExp#exec(string)` メソッドを使用する必要があります。
両者の使用方法はほぼ同様ですが、呼び出しの関係が下記のように入れ替わることに注意してください。

~~~
正規表現オブジェクト.exec(検索対象テキスト)
~~~

ここでは、下記のような検索対象テキストの中から、URL のホスト名、パス部分を繰り返し抽出するようなケースを考えてみます（2つのURLからそれぞれホスト名とパスを抽出します）。

~~~
Visit my website at https://example.com/file1 and https://example.co.jp/file2
~~~

`RegExp#exe(string)` の戻り値は、`String#match(regexp)` の（`g` オプションなしで呼び出した場合の）戻り値と同様の配列で、最初の要素としてパターンに一致した部分の全体が格納され、残りの配列要素（インデックス1以降）には、`(` と `)` でグルーピングされたパターンに一致した部分文字列が順番に格納されます。
検索パターンに一致する文字列が見つからなかった場合は、`null` を返します。

`String#match(regexp)` と異なるのは、`RegExp#exec(string)` は検索結果を返す度に、自分自身の `lastIndex` プロパティを更新し、次の検索はその位置から検索を始めるという点です。
この仕組みにより、繰り返し `exec` メソッドを呼び出すことでパターンに一致する部分をすべて検索することが可能になっています。


~~~ javascript
var re = /(\w+):\/\/([\w.]+)\/(\S*)/g;  // gフラグを忘れない！
var text = 'Visit my website at https://example.com/file1 and https://example.co.jp/file2';
var matches;
while ((matches = re.exec(text)) != null) {
  var protocol = matches[1];
  var host = matches[2];
  var path = matches[3];
  console.log('host = ' + host);
  console.log('path = ' + path);
  console.log('lastIndex = ' + re.lastIndex);
  console.log('----------------');
}
~~~

#### 実行結果

~~~
host = example.com
path = file1
lastIndex = 45
----------------
host = example.co.jp
path = file2
lastIndex = 77
----------------
~~~

検索結果が返される度に、次の検索開始位置を示す `RegExp#lastIndex` の値が更新されていることに注目してください。

このように、1つの検索対象テキストに対して複数回の検索を実行する場合は、必ず**正規表現の `g` フラグ（グローバル検索）を ON にしておく**必要があります。
これを忘れると、`RegExp#exec()` メソッドを実行したときに、`RegExp` オブジェクトの `lastIndex` プロパティが更新されず、毎回先頭からの検索になっていまいます（結果的に上記のような while ループは無限ループになってしまう）。

<div class="note">
<code>RegExp#exec()</code> による検索が最後まで進むと（戻り値で <code>null</code> が返されるまで実行されると）、<code>lastIndex</code> の値は自動的に 0 にリセットされます。
そのため、通常は複数の検索対象テキストに対して、同じ <code>RegExp</code> オブジェクトを使って検索を続けることができます。
ただし、検索を途中で中断して別のテキストを検索したい場合は、明示的に <code>lastIndex</code> の値を 0 にリセットする必要があります。
</div>

`RegExp#exec()` メソッドの振る舞いは若干ややこしいところがありますが、これを使いこなせるようになれば、正規表現を利用した文字列抽出のタスクはほとんどカバーできるようになります。

