---
title: "JavaScriptメモ: jQuery でテーブルに動的にレコード (tr) を追加する"
url: "p/fcsowho/"
date: "2013-10-11"
tags: ["javascript"]
aliases: [/js/jquery/add-table-record.html]
---

jQuery を使用して、下記のような `table` 要素に、動的にレコード（`tr` 要素）を追加するサンプルです。

{{< code lang="html" title="HTML（抜粋）" >}}
<table id="myTable"></table>
{{< /code >}}

{{< code lang="javascript" title="JavaScript（抜粋）" >}}
// テーブルにレコードを追加
$("#myTable").append($("<tr/>")
    .append($("<th/>").text("New th"))
    .append($("<td/>").text("New td1"))
    .append($("<td/>").text("New td2"))
);
{{< /code >}}

逆に、下記のようにすれば、テーブル内のレコードをすべて削除することができます。

```javascript
$("#myTable tr").remove();
```
