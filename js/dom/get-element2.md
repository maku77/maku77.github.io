---
title: "HTML の DOM 要素を取得する (2) 親要素、子要素、兄弟要素を検索"
created: 2018-01-06
---

ある HTML 要素の親要素、子要素、兄弟要素を取得するには、下記のようなプロパティを使用します。

| 取得する要素 | 従来の API（テキストノードを含む） | 新しい API（テキストノードを含まない） |
| ---- | ---- | ---- |
| 最初の子要素 | firstChild | firstElementChild |
| 最後の子要素 | lastChild | lastElementChild |
| すべての子要素 | childNodes | children |
| 次の兄弟要素 | nextSibling | nextElementSibling |
| 前の兄弟要素 | previousSibling | previousElementSibling |

従来からある API と、新しい API があり、新しい API の方を使用すると、テキストノードを除いた要素のみを検索対象にしてくれます。
例えば、次のような構成の HTML があったとします。

~~~ html
<div id="main">
  <h2>タイトル1</h2>
  <h2>タイトル2</h2>
  <h2>タイトル3</h2>
</div>
~~~

div 要素の子要素は、3 つの h2 要素だけに見えますが、実際には改行文字やインデントのスペースがあるため、3 つのテキストノードが含まれています。
よって div 要素の子要素は次のようになります。

~~~
div
  +-- テキスト要素（改行＋インデント）
  +-- h2 要素
  +-- テキスト要素（改行＋インデント）
  +-- h2 要素
  +-- テキスト要素（改行＋インデント）
  +-- h2 要素
  +-- テキスト要素（改行）
~~~

div 要素に対して、従来からある `childNodes` プロパティを使用すると、上記のようなテキストノードを含む子要素の配列を取得することができます。

~~~ javascript
window.onload = function() {
  var main = document.querySelector('#main')
  var elems = main.childNodes;
  console.log(elems);  // [text, h2, text, h2, text, h2, text]
};
~~~

新しい API である `children` プロパティを使用すると、テキストノードを除いた子要素の配列を取得することができます。

~~~ javascript
window.onload = function() {
  var main = document.querySelector('#main')
  var elems = main.children;
  console.log(elems);  // [h2, h2, h2]
};
~~~

