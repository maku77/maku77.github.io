---
title: "JavaScriptメモ: URL エンコード／デコードを行う (encodeURI, encodeURIComponent)"
url: "p/p5m3yb2/"
date: "2018-12-03"
description: "ユーザ入力に基づいて URI を構成する場合は、URI として不正な文字が含まれないようにパーセントエンコーディング (percent-encoding) を行う必要があります。"
tags: ["javascript"]
aliases: [/js/web/encode-uri.html]
---

URI の構成と予約文字
----

URI は下記のような構成をしています ([RFC3986](https://tools.ietf.org/html/rfc3986))。

{{< image src="img-001.png" >}}
{{% private %}}
- {{< file src="encode-uri.pptx" >}}
{{% /private %}}

各パートの区切り文字として、次のような予約文字 (reserved characters) が定義されており、例えば query 部分のデータとしてこれらの文字を含む場合は、適切にパーセントエンコーディングしてやる必要があります。

{{< code title="予約文字 (reserved characters)" >}}
reserved    = gen-delims / sub-delims
gen-delims  = ":" / "/" / "?" / "#" / "[" / "]" / "@"
sub-delims  = "!" / "$" / "&" / "'" / "(" / ")" / "*" / "+" / "," / ";" / "="
{{< /code >}}

sub-delims に分類された文字をエンコードすべきかは、作成するシステムによりますが、少なくとも `&` や `=` などは、query 文字列のキー＆バリューの区切りに使用されるのでパーセントエンコーディングの必要があるでしょう。

一方で、URI の各パート内にそのまま含められる文字として、次のような非予約文字 (nonreserved characters) も定義されています。

{{< code title="非予約文字 (nonreserved characters)" >}}
unreserved  = ALPHA / DIGIT / "-" / "." / "_" / "~"
{{< /code >}}

これらは、URI のどのパートにおいてもそのまま使用できる文字です。
これらの文字もパーセントエンコーディングで表すことはできますが（例: `A`→`%41`）、無意味なエンコーディングは避けた方がよいでしょう（RFC3986 においても、そのようなパーセントエンコーディングは行うべきではないとされています）。


JavaScript による URI エンコーディング
----

JavaScript には、URI のパーセントエンコード（およびデコード）を行うための関数として下記のものが用意されています。

- [encodeURI](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/encodeURI)
    - URI 全体を表す文字列の中の、マルチバイト文字などをパーセントエンコードします。URI を構成するセパレータ文字などは正しく使用している前提で動作するため、`/` や `?` などの予約文字はエスケープされません。
- [decodeURI](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/decodeURI)
    - `encodeURI` によってエンコードされた文字列を元に戻します。
- [encodeURIComponent](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/encodeURIComponent)
    - 下記の文字を除くすべての文字をパーセントエンコードします: アルファベット、10進数字、`-`、`_`、`.`、`!`、`~`、`*`、`(`、`)`。
- [decodeURIComponent](https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/decodeURIComponent)
    - `encodeURIComponent` によってエンコードされた文字列を元に戻します。

下記の表は、変換前の文字 (ch) が、`encodeURI` 関数と `encodeURIComponent` 関数によってどのようにエンコードされるかを示しています（{{< file src="encode-uri.js.txt" caption="表の生成に使用したスクリプト" >}}）。

| ch | encodeURI(ch) | encodeURIComponent(ch) |
| ---- | ---- | ---- |
| `あ` | `%E3%81%82` | `%E3%81%82` |
| ` ` (スペース) | `%20` | `%20` |
| `A` | `A` | `A` |
| `;` | `;` | `%3B` |
| `,` | `,` | `%2C` |
| `/` | `/` | `%2F` |
| `?` | `?` | `%3F` |
| `:` | `:` | `%3A` |
| `@` | `@` | `%40` |
| `&` | `&` | `%26` |
| `=` | `=` | `%3D` |
| `+` | `+` | `%2B` |
| `$` | `$` | `%24` |
| `#` | `#` | `%23` |
| `-` | `-` | `-` |
| `_` | `_` | `_` |
| `.` | `.` | `.` |
| `!` | `!` | `!` |
| `~` | `~` | `~` |
| `*` | `*` | `*` |
| `'` | `'` | `'` |
| `(` | `(` | `(` |
| `)` | `)` | `)` |
| `[` | `%5B` | `%5B` |
| `]` | `%5D` | `%5D` |

URI を構成する際に、パーセントエンコーディングが必要になる主なケースは、query 部分の値としてユーザ入力値を使用するケースでしょう。
例えば、ユーザが Web サイトのフォーム上で `Nuts&Milk` のような値を入力した場合、その値を query 部分の値として扱うには、`&` を `%26` にエスケープしなければいけません。

```javascript
const userInput = 'Nuts&Milk';
console.log(encodeURIComponent(userInput));  //=> 'Nuts%26Milk'
```

query 部分の文字列として、`key1=val1&key2=val2&key3=val3` といった 3 対のキー＆バリューで構成したい場合は、`val1`、`val2`、`val3` のそれぞれの値を `encodeURIComponent` でエスケープしてから結合しなければならないことに注意してください。
3 対のキー＆バリューを `&` や `=` で結合した後で、まとめて `encodeURIComponent` で変換してしまうと、セパレータ文字として扱うべき `&` や `=` までエスケープされてしまいます。

JavaScript でユーザ入力を含む query 文字列を構成する場合、下記のようなユーティリティ関数を使用できます。

```javascript
// キー＆バリューのオブジェクトから query 文字列を作成する
function buildQuery(params) {
  const esc = encodeURIComponent;
  return Object.keys(params).map(k => {
    return esc(k) + '=' + esc(params[k]);
  }).join('&');
}
```

ECMAScript 2017 の `Object.entries()` を使用すると、下記のように記述できます。

```javascript
function buildQuery(params) {
  return Object.entries(params)
    .map(pair => pair.map(encodeURIComponent).join('='))
    .join('&');
}
```


{{< code lang="javascript" title="使用例" >}}
const params = {
  key1: 'a&b',  // ユーザー入力で構成されていると仮定
  key2: 'c&d'
};

const query = buildQuery(params);
console.log(query);  //=> 'key1=a%26b&key2=c%26d'
{{< /code >}}


Node.js の場合
----

Node.js では、組み込みの [querystring モジュール](https://nodejs.org/api/querystring.html) を使ってクエリ文字列を構築することができます。

```javascript
const querystring = require('querystring');

const query = querystring.stringify({ key1: 'a&b', key2: 'c&d' });
console.log(query);  //=> 'key1=a%26b&key2=c%26d'
```

