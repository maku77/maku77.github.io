---
title: "JavaScript で文字列を置換する (String#replace, String#replaceAll)"
url: "p/8pnuzk4/"
date: "2012-01-20"
lastmod: "2023-12-01"
changes:
  - 2023-12-01: モダンな JavaScript の書き方に修正
tags: ["javascript", "regexp"]
aliases: /js/string/replace.html
---

replace と replaceAll の基本
----

__`String#replace()`__ メソッドを使用すると、`String` オブジェクト内のパターンに一致する文字列を置換することができます。

{{< code lang="js" title="ABC を XXX に置換" >}}
const text = 'ABC 123 ABC 123';
const s = text.replace('ABC', 'XXX');  //=> 'XXX 123 ABC 123'
{{< /code >}}

`replace()` メソッドは、自分自身のオブジェクトの内容を変更しないため、置換結果は戻り値として受け取る必要があることに注意してください。

また、デフォルトでは上記のように、__最初に見つかった文字列だけ__ が置換されます。
パターンが複数箇所に一致した場合に、すべて置換するには、次のように __`String#replaceAll()`__ メソッドを使用します（後述の正規表現パターンを使用する方法もあります）。

{{< code lang="js" title="すべての ABC を XXX に置換" >}}
const text = 'ABC 123 ABC 123';
const s = text.replaceAll('ABC', 'XXX');  //=> 'XXX 123 ABC 123'
{{< /code >}}


正規表現パターンに一致する文字列を全て置換する
----

`String#replace()` メソッドの第 1 引数には、正規表現パターンを渡すこともできます。
正規表現属性の __`g`__（グローバル検索）を付ければ、パターンに一致した部分すべてを一括置換することができます（`replaceAll()` ではなく、`replace()` ですべて置換できます）。

{{< code lang="js" title="例: すべての ABC を XXX に置換" >}}
const text = 'ABC 123 ABC 123';
const s = text.replace(/ABC/g, 'XXX');  //=> 'XXX 123 XXX 123'
{{< /code >}}

正規表現属性の __`i`__ を付けると、大文字と小文字を区別せずに一致させることができます。
正規表現属性は複数組み合わせて使用することができます。

{{< code lang="js" title="例: 大文字と小文字が混在するものを「JavaScript」という表現に統一" >}}
const text = `javascript is a versatile language, and understanding
JaVaScRiPt is essential for modern web development.`;
const s = text.replace(/javascript/gi, 'JavaScript');
{{< /code >}}


正規表現パターンに一致した部分を置換後の文字列で使用する
----

正規表現を使用した置換が強力なのは、パターンに一致した実際の文字列を、置換後の文字列の中で参照することができることです。
正規表現パターンにマッチした部分全体は、__`$&`__ で参照することができます（`$0` ではないことに注意）。

{{< code lang="js" title="例: 数値を [ と ] で囲む" >}}
const re = /\d+/g;
const text = 'ABC 123 DEF 456';
const s = text.replace(re, '[$&]');  //=> 'ABC [123] DEF [456]'
{{< /code >}}

正規表現の中のパターンを `(` と `)` でグルーピングしておくと、その中で実際に一致した部分を __`$1`__、__`$2`__、__`$3`__ というキーワードを使って参照することができます（Perl と同様の記法です）。

{{< code lang="js" title="例: 姓と名を入れ替える" >}}
const re = /(\w+)\s(\w+)/;
const text = 'John Smith';
const s = text.replace(re, '$2, $1');  //=> 'Smith, John'
{{< /code >}}

