---
title: jQuery でテーブルに動的にレコード (tr) を追加する
date: "2013-10-11"
---

jQuery を使用して、下記のような `table` 要素に、動的にレコード（`tr` 要素）を追加するサンプルです。

#### HTML（抜粋）

~~~ html
<table id="myTable"></table>
~~~

#### JavaScript（抜粋）

~~~ javascript
// テーブルにレコードを追加
$("#myTable").append($("<tr/>")
    .append($("<th/>").text("New th"))
    .append($("<td/>").text("New td1"))
    .append($("<td/>").text("New td2"))
);
~~~

逆に、下記のようにすれば、テーブル内のレコードをすべて削除することができます。

~~~ javascript
$("#myTable tr").remove();
~~~

