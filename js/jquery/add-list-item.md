---
title: "jQuery で動的にリスト項目 (li) を追加する"
date: "2013-10-12"
---

下記のような `ul` 要素に、動的にリスト項目 `li` を追加するサンプルです。

#### html（抜粋）

~~~ html
<ul id="myList"></ul>
~~~

#### JavaScript（抜粋）

~~~ javascript
var $myList = $('#myList');
$myList.append('<li>Item 1</li>');
$myList.append('<li>Item 2</li>');
$myList.append('<li>Item 3</li>');
~~~

次のようにして、`li` 要素の jQuery オブジェクトを作ってから `append` する方法もあります。

~~~ javascript
var $myList = $('#myList');
$myList.append($('<li/>').text('Item 1'));
$myList.append($('<li/>').text('Item 2'));
$myList.append($('<li/>').text('Item 3'));
~~~

