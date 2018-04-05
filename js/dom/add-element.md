---
title: "HTML の DOM ツリーに要素を追加する"
date: "2012-12-12"
---

Element インタフェースの innerHTML プロパティを使う場合
----

**innerHTML** プロパティは、もともと IE の独自拡張プロパティでしたが、今はほとんどのブラウザで使用できるようになっています。
ただし、仕様が不明確なところがあり（2012年現在）、ブラウザごとに若干の振る舞いの差があるようです（input 要素の value 属性を設定する／しないなど）。

~~~ html
<div id="parent"></div>

<script>
var parent = document.getElementById('parent');
parent.innerHTML = '<p>Hello</p>';
</script>
~~~


DOM インタフェースを使う場合
----

DOM インタフェースを使用してテキストノードを追加するには、**createTextNode** でテキストノードを作成し、親ノードの **appendChild** で追加します。

~~~ html
<div id="parent"></div>

<script>
var parent = document.getElementById('parent');
var text = document.createTextNode('Hello');
parent.appendChild(text);
</script>
~~~

テキストノードではなく、任意の要素（p 要素など）を作成したいときは、createTextNode の代わりに **createElement** を使用します。
以下の例では、`<p>Hello</p>` を既存の div 要素以下に追加しています。

~~~ html
<div id="parent"></div>

<script>
var parent = document.getElementById('parent');
var p = document.createElement('p');
p.appendChild(document.createTextNode('Hello'));
parent.appendChild(p);
</script>
~~~


jQuery の場合
----

jQuery では、セレクタで選択した要素に対して、**text()** 関数か **html()** 関数でテキストあるいは、HTML を追加できます。

~~~ html
<div id="parent"></div>

<script>
$('div#parent').text('<b>Hello</b>');    // &lt;b&gt;Hello&lt;/b&gt; が追加される
$('div#parent').html('<b>Hello</b>');
</script>
~~~

