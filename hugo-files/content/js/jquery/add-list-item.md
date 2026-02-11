---
title: "JavaScriptメモ: jQuery で動的にリスト項目 (li) を追加する"
url: "p/nqvvwdi/"
date: "2013-10-12"
tags: ["javascript"]
aliases: [/js/jquery/add-list-item.html]
---

下記のような `ul` 要素に、動的にリスト項目 `li` を追加するサンプルです。

{{< code lang="html" title="html（抜粋）" >}}
<ul id="myList"></ul>
{{< /code >}}

{{< code lang="javascript" title="JavaScript（抜粋）" >}}
const $myList = $('#myList');
$myList.append('<li>Item 1</li>');
$myList.append('<li>Item 2</li>');
$myList.append('<li>Item 3</li>');
{{< /code >}}

次のようにして、`li` 要素の jQuery オブジェクトを作ってから `append` する方法もあります。

```javascript
const $myList = $('#myList');
$myList.append($('<li/>').text('Item 1'));
$myList.append($('<li/>').text('Item 2'));
$myList.append($('<li/>').text('Item 3'));
```
