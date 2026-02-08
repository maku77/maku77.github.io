---
title: "JavaScriptメモ: テンプレートを使ってテキストを生成する (Underscore.js)"
url: "p/gamk5vt/"
date: "2013-06-05"
tags: ["javascript"]
aliases: /js/string/template.html
---

Underscore.js の template 関数を使う方法
----

Underscore.js の `_.template()` を使用すると、テンプレートテキストを利用した文字列生成を行うことができます。
`_.template()` の第一引数には、プレースホルダを含むテンプレートテキストを指定します。
テンプレートテキスト内に、

```
<%= プロパティ名 %>
```

という文字列（プレースホルダ）を含めておくと、第二引数で渡したオブジェクトのプロパティ値がそこに展開されます。

```html
<script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.4/underscore-min.js"></script>

<script>
const template = 'Hello <%= name %>';
const text = _.template(template, { name: 'Makkuma' });  //=> 'Hello Makkuma'
</script>
```

テキストの展開時に、同時に HTML エスケープしたい場合は、`<%= ... %>` の代わりに、`<%- ... %>` を使用します。
Web サイトの HTML 要素を動的に構築するようなケースではこちらを使用するとよいでしょう。

```javascript
const template = 'Note: <%- value %>';
const data = { value: '<em> means emphasize' };
const text = _.template(template, data);  //=> 'Note: &lt;em&gt; means emphasize'
```

### テンプレートテキストを事前コンパイルして高速化

同じテンプレートテキストを何度も使用する場合は、第二引数を省略して `_.template()` を呼び出すことで、テンプレートオブジェクトを保持しておくことができます。

```javascript
const tmpl = _.template('Hello <%= name %>');
const a = tmpl({ name: 'AAA' });  //=> 'Hello AAA'
const b = tmpl({ name: 'BBB' });  //=> 'Hello BBB'
const c = tmpl({ name: 'CCC' });  //=> 'Hello CCC'
```
