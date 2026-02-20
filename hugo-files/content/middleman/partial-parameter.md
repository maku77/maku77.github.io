---
title: "Middlemanメモ: パーシャルにパラメータを設定する"
url: "p/evdn5z3/"
date: "2015-12-07"
tags: ["middleman"]
aliases: /middleman/partial-parameter.html
---

パーシャル機能を使って別ファイルの内容を取り込む場合に、パーシャルに対して任意のデータを渡すことができます。
この機能を使うことで、パーシャル内の一部だけ異なるコンテンツを生成することができます。

パーシャルに対してデータを渡したい場合は、下記のように、`:locals` パラメータで渡したいデータを指定します。
このケースでは、`key1` という名前の、`value1` という値が入ったデータを渡しています。

{{< code lang="erb" title="source/hello.html.erb" >}}
<%= partial 'includes/my_partial', :locals => { :key1 => 'value1' }  %>
{{< /code >}}

パーシャルファイル内では、渡されたデータを下記のように参照できます。

{{< code lang="erb" title="source/includes/_my_partial.erb" >}}
渡された値は <%= key1 %> です。
{{< /code >}}

使用例
----

例えば、下記のような `amazon` パーシャルを作成しておくと、指定した ASIN（Amazon 商品の ID）に対する Amazon のリンクを簡単に埋め込めるようになります。
パーシャルは、レイアウトファイル内だけでなく、任意のテンプレートファイル内（コンテンツ用ファイル）から使用できます。

{{< code lang="erb" title="source/includes/_amazon.erb" >}}
<a href="http://www.amazon.co.jp/dp/<%= asin %>/">Link to Amazon</a>
{{< /code >}}

任意のコンテンツから、下記のように Amazon へのリンクを張れるようになります。

```erb
<%= partial 'includes/amazon', :locals => { :asin => 'B00XL0BD7U'} %>
```
