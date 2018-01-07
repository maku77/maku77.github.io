---
title: "HTML の DOM 要素を取得する (1) タグ名、クラス名、ID を検索"
date: "2018-01-06"
---

タグ名で要素を取得する (getElementsByTagName)
----

`document.getElementsByTagName` 関数を使用すると、指定したタグ名を持つ要素を取得することができます。
同じタグ名を持つ要素は複数存在する可能性があるので、戻り値は配列になります。

#### 例: p 要素をすべて取得する

~~~ javascript
window.onload = function() {
  var elems = document.getElementsByTagName('p')
  console.log(elems);
};
~~~


クラス名で要素を取得する (getElementsByClassName)
----

`document.getElementsByClassName` 関数を使用すると、指定したクラス名が `class` 属性に含まれている要素を取得することができます。
そのような要素は複数存在する可能性があるので、戻り値は配列になります。

#### 例: class 属性に sample が含まれている要素をすべて取得する

~~~ javascript
window.onload = function() {
  var elems = document.getElementsByClassName('hoge')
  console.log(elems);
};
~~~


ID で要素を取得する (getElementById)
----

`document.getElementById` 関数を使用すると、指定した ID を `id` 属性に持つ要素を取得することができます。
指定した ID を持つ要素が見つからない場合は `null` を返します。

#### 例: id 属性に sample が含まれている要素を取得する

~~~ javascript
window.onload = function() {
  var elem = document.getElementById('sample');
  console.log(elem);
};
~~~

特定の ID を持つ要素は高々１つしか存在することができないので、`getElementsByTagName` や `getElementsByClassName` とは異なり、`getElementById` は単一の要素を返すことに注意してください。


子要素からのみ検索する
----

`document` の代わりに、特定の要素に対して上記のメソッドを呼び出すようにすれば、その子要素だけを検索対象とすることができます。
下記の例では、まず ID で要素を検索し、次にタグ名による検索を行なっています。

~~~ html
<ul id="sample-data">
  <li>AAA
  <li>BBB
  <li>CCC
</ul>

<script>
window.onload = function() {
  var data = document.getElementById('sample-data');
  var elems = data.getElementsByTagName('li');
  console.log(elems);
};
</script>
~~~


セレクタ API で要素を取得する (querySelector, querySelectorAll)
----

CSS のセレクタ形式で要素を指定して取得したいときは、`document.querySelector` 関数や `document.querySelectorAll` 関数を使用します。
前者は最初に見つかった要素、後者は見つかった全ての要素を返します。

#### 例: id 属性が sample の要素を取得

~~~ javascript
window.onload = function() {
  var elem = document.querySelector('#sample');
  console.log(elem);
};
~~~

#### 例: class 属性に sample を持つ要素をすべて取得

~~~ javascript
window.onload = function() {
  var elems = document.querySelectorAll('.sample');
  console.log(elems);
};
~~~

#### 例: div 要素で、かつ、class 属性に note を持つ要素をすべて取得

~~~ javascript
window.onload = function() {
  var elems = document.querySelectorAll('div.note');
  console.log(elems);
};
~~~

#### 例: li 要素で、偶数番目のものをすべて取得

~~~ javascript
window.onload = function() {
  var elems = document.querySelectorAll('li:nth-child(even)')
  console.log(elems);
};
~~~

<div class="note">
jQuery が登場した当初は、このようなセレクタ API が使えることが一つの売りでしたが、現在は JavaScript の機能として <code>querySelectAll</code> が使えるようになっています。セレクタ API だけのために jQuery を使用する必要はなくなりました。
</div>

