---
title: 文字列の先頭と末尾の空白を削除する
created: 2013-07-12
---

JavaScript には、標準では文字列の前後の空白を削除する `trim()` メソッドのようなものは用意されていないため、何らかのライブラリで提供されているものを使用するか、自分で作成する必要があります。


jQuery の trim() を使う方法
----

jQuery には、ユーティリティ関数 `jQuery.trim()` が用意されています。

```javascript
$.trim('   ABC ');  //=> 'ABC'
```


自力で trim() 関数を実装する方法
----

下記の `trim()` 関数は、渡された文字列の先頭、末尾から空白、タブ、改行を削除して返します。

```javascript
function trim(str) {
  return str.replace(/^\s+|\s+$/g, '');
}
```

下記のように使用できます。

```javascript
var s = trim('  ABC ');  //=> 'ABC'
```

