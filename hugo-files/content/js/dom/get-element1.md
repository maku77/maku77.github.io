---
title: "JavaScript で HTML の DOM 要素を取得する (1) タグ名、クラス名、ID を検索"
url: "p/on7omgt/"
date: "2018-01-06"
lastmod: "2023-12-01"
tags: ["javascript"]
changes:
  - 2023-12-01: モダンな JS の記述方法に修正
aliases: /js/dom/get-element1.html
---

タグ名で要素を取得する (getElementsByTagName)
----

__`document.getElementsByTagName()`__ 関数を使用すると、指定したタグ名を持つ要素を取得することができます。
同じタグ名を持つ要素は複数存在する可能性があるので、戻り値は配列になります。

{{< code lang="html" title="例: p 要素をすべて取得する" hl_lines="6" >}}
<p>こんにちは</p>
<p>おやすみ</p>

<script>
window.onload = function() {
  const elems = document.getElementsByTagName("p");
  for (const e of elems) {
    console.log(e.innerText);
  }
};
</script>
{{< /code >}}

{{< code title="実行結果" >}}
こんにちは
おやすみ
{{< /code >}}


クラス名で要素を取得する (getElementsByClassName)
----

__`document.getElementsByClassName()`__ 関数を使用すると、指定したクラス名が `class` 属性に含まれている要素を取得することができます。
そのような要素は複数存在する可能性があるので、戻り値は配列になります。

{{< code lang="html" titie="例: class 属性に foo が含まれている要素をすべて取得する" hl_lines="7" >}}
<div class="foo">AAA</div>
<div class="foo bar">BBB</div>
<div class="foo bar baz">CCC</div>

<script>
window.onload = function() {
  const elems = document.getElementsByClassName("foo");
  for (const e of elems) {
    console.log(e.innerText);
  }
};
</script>
{{< /code >}}

{{< code title="実行結果" >}}
AAA
BBB
CCC
{{< /code >}}


ID で要素を取得する (getElementById)
----

__`document.getElementById()`__ 関数を使用すると、指定した ID を `id` 属性に持つ要素を取得することができます。
指定した ID を持つ要素が見つからない場合は `null` を返します。

{{< code lang="html" title="例: id 属性に message が含まれている要素を取得する" hl_lines="5" >}}
<span id="message">ばよえーん</span>

<script>
window.onload = function() {
  const elem = document.getElementById("message");
  if (elem) {
    console.log(elem.innerText);  //=> ばよえーん
  }
};
</script>
{{< /code >}}

特定の ID を持つ要素は高々ひとつしか存在することができないので、`getElementsByTagName` や `getElementsByClassName` とは異なり、`getElementById` は単一の要素を返すことに注意してください。


子要素からのみ検索する
----

`document` の代わりに、特定の親要素に対して前述のメソッドを呼び出すようにすれば、その子要素だけを検索対象とすることができます。
下記の例では、まず ID で要素を検索し、次にタグ名による検索を行なっています。

{{< code lang="html" title="子要素の検索" hl_lines="9 10" >}}
<ul id="my-data">
  <li>AAA</li>
  <li>BBB</li>
  <li>CCC</li>
</ul>

<script>
window.onload = function() {
  const data = document.getElementById("my-data");
  const elems = data.getElementsByTagName("li");
  for (const e of elems) {
    console.log(e.innerText);
  }
};
</script>
{{< /code >}}

{{< code title="実行結果" >}}
AAA
BBB
CCC
{{< /code >}}


セレクタ API で要素を取得する (querySelector, querySelectorAll)
----

CSS のセレクタ形式（`#foo` や `.bar` など）で要素を指定して取得したいときは、__`document.querySelector()`__ 関数や __`document.querySelectorAll()`__ 関数を使用します。
前者は最初に見つかった要素、後者は見つかった全ての要素を返します。

{{< code lang="js" title="例: id 属性が sample の要素を取得" hl_lines="2" >}}
window.onload = function() {
  const elem = document.querySelector("#sample");
  console.log(elem);
};
{{< /code >}}

{{< code lang="js" title="例: class 属性に sample を持つ要素をすべて取得" hl_lines="2" >}}
window.onload = function() {
  const elems = document.querySelectorAll(".sample");
  console.log(elems);
};
{{< /code >}}

{{< code lang="js" title="例: div 要素で、かつ、class 属性に note を持つ要素をすべて取得" hl_lines="2" >}}
window.onload = function() {
  const elems = document.querySelectorAll("div.note");
  console.log(elems);
};
{{< /code >}}

{{< code lang="js" title="例: li 要素で、偶数番目のものをすべて取得" hl_lines="2" >}}
window.onload = function() {
  const elems = document.querySelectorAll("li:nth-child(even)");
  console.log(elems);
};
{{< /code >}}

{{% note title="jQuery と同じ？" %}}
jQuery が登場した当初は、このようなセレクタ API が使えることがひとつのウリでした。
現在は JavaScript の機能として `querySelectAll()` が使えるようになっているので、セレクタ API だけのために jQuery を使用する必要はなくなりました。
{{% /code %}}

