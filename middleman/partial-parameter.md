---
title: パーシャルにパラメータを設定する
created: 2015-12-07
---

パーシャル機能を使って、別ファイルの内容を取り込む場合に、パーシャルに対して任意のデータを渡すことができます。
この機能を使うことで、パーシャル内の一部だけ異なるコンテンツを生成することができます。

パーシャルに対してデータを渡したい場合は、下記のように、`:locals` パラメータで渡したいデータを指定します。
このケースでは、`key1` という名前の、`value1` という値が入ったデータを渡しています。

#### source/hello.html.erb

```erb
<%= partial 'includes/my_partial', :locals => { :key1 => 'value1' }  %>
```

パーシャルファイル内では、渡されたデータを下記のように参照できます。


#### source/includes/_my_partial.erb

```erb
渡された値は <%= key1 %> です。
```

使用例
----

例えば、下記のような `amazon` パーシャルを作成しておくと、指定した ASIN（Amazon 商品の ID）に対する Amazon のリンクを簡単に埋め込めるようになります。
パーシャルは、レイアウトファイル内だけでなく、任意のテンプレートファイル内（コンテンツ用ファイル）から使用できます。

#### source/includes/_amazon.erb
```erb
<a href="http://www.amazon.co.jp/dp/<%= asin %>/">Link to Amazon</a>
```

任意のコンテンツから、下記のように Amazon へのリンクを張れるようになります。

```erb
<%= partial 'includes/amazon', :locals => { :asin => 'B00XL0BD7U'} %>
```

