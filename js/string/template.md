---
title: "テンプレートを使ってテキストを生成する (Underscore.js)"
date: "2013-06-05"
---

Underscore.js の template 関数を使う方法
----

Underscore.js の `_.template()` を使用すると、テンプレートテキストを利用した文字列生成を行うことができます。
`_.template()` の第一引数には、プレースホルダを含むテンプレートテキストを指定します。
テンプレートテキスト内に、

```
<%= プロパティ名 =>
```

という文字列（プレースホルダ）を含めておくと、第二引数で渡したオブジェクトのプロパティ値がそこに展開されます。

```html
<script src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.4/underscore-min.js"></script>

<script>
var template = 'Hello <%= name %>';
var text = _.template(template, { name: 'Makkuma' });  //=> 'Hello Makkuma'
</script>
```

テキストの展開時に、同時に HTML エスケープしたい場合は、`<%= ... %>` の代わりに、`<%- ... %>` を使用します。
Web サイトの HTML 要素を動的に構築するようなケースではこちらを使用するとよいでしょう。

```javascript
var template = 'Note: <%- value %>';
var data = { value: '<em> means emphasize' };
var text = _.template(template, data);  //=> 'Note: &lt;em&gt; means emphasize'
```

### テンプレートテキストを事前コンパイルして高速化

同じテンプレートテキストを何度も使用する場合は、第二引数を省略して `_.template()` を呼び出すことで、テンプレートオブジェクトを保持しておくことができます。

```javascript
var tmpl = _.template('Hello <%= name %>');
var a = tmpl({ name: 'AAA' });  //=> 'Hello AAA'
var b = tmpl({ name: 'BBB' });  //=> 'Hello BBB'
var c = tmpl({ name: 'CCC' });  //=> 'Hello CCC'
```

