---
title: "JavaScriptメモ: 文字列の先頭と末尾の空白を削除する (String#trim)"
url: "p/hpvmzdu/"
date: "2013-07-12"
tags: ["javascript"]
lastmod: "2019-12-20"
aliases: /js/string/trim.html
---

ECMAScript 2015 以降では、`String` クラスの `trim()` メソッドを使って、文字列の先頭と末尾にある余計なスペース（空白やタブ、改行など）をすべて取り除くことができます。


JavaScript (ECMAScript 2015) の `trim()` を使う方法
----

```javascript
'   ABC '.trim();  //=> 'ABC'
```


jQuery の `trim()` を使う方法
----

jQuery には、ユーティリティ関数 `jQuery.trim()` が用意されていますが、ECMAScript 2015 で `String` クラスに `trim()` が標準装備されたので、もう出番はないでしょう。

```javascript
$.trim('   ABC ');  //=> 'ABC'
```


自力で `trim()` 関数を実装する方法
----

下記の `trim()` 関数は、渡された文字列の先頭、末尾から空白、タブ、改行を削除して返します。

```javascript
function trim(str) {
  return str.replace(/^\s+|\s+$/g, '');
}
```

下記のように使用できます。

```javascript
const s = trim('  ABC ');  //=> 'ABC'
```
